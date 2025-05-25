package Smart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.net.*;
import java.io.*;
import java.util.Set;

public class ClientHandler implements Runnable {
    
    private Socket socket = null;
    private Set<String> usernames;
    private Set<Task> tasks;
    private String username;
    public ClientHandler(Socket clientSocket, Set<String> names, Set<Task> clientTasks) {
        this.socket = clientSocket;
        this.usernames = names;
        this.tasks = clientTasks;
    }

    @Override
    public void run() {
        try (Connection conn = Database.getConnection()) { // Open connection once at the start
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String line = null;

            while (true) {
                try {
                    line = (String) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    System.err.println("Connection reset, client might have disconnected: " + e.getMessage());
                    break;
                }

                if (line.startsWith("Login : ")) {
                    LoginHandler(out, line, usernames, tasks, conn);
                } else if (line.startsWith("Create : ")) {
                    try {
                        CreateHandler(in, tasks, conn);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (line.startsWith("Edit : ")) {
                    try {
                        EditHandler(in, tasks, conn);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (line.startsWith("Save : ")) {
                    saveHandler(tasks);
                } else if (line.startsWith("Delete : ")) {
                    try {
                        DeleteHandler(in, tasks, conn);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (line.startsWith("End : ")) {
                    LogoutHandler(out,usernames);
                } else if (line.startsWith("Retrieve : ")) {
                    out.writeObject(tasks);
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public void LoginHandler(ObjectOutputStream out, String line, Set<String> usernames, Set<Task> tasks, Connection conn) throws IOException {
        this.username = line.substring(8).trim();
        synchronized (usernames) {
            if (usernames.contains(username)) {
                out.writeObject("Denied");
            } else {
                out.writeObject("OK");
                usernames.add(username);
                System.out.println("Connection is established");
                System.out.printf("Login by : %s%n", username);
                getAllTasksFromDatabase(tasks, conn);
            }
        }
        out.flush();
    }
    public void LogoutHandler (ObjectOutputStream out,Set<String> usernames) throws IOException
    {
    	usernames.remove(this.username);
    	out.writeObject("OK");
    	out.flush();
    	System.out.printf("Logout by : %s%n", username);
    }

     public void CreateHandler(ObjectInputStream input, Set<Task> tasks, Connection conn) throws ClassNotFoundException, IOException {
        try {
            synchronized (tasks) {
                Task task = (Task) input.readObject();
                tasks.add(task);
                System.out.printf("Task with name : %s is added by : %s%n", task.getName(),username);

                // Save task to database
                try (PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO tasks (name, description, deadline, status, priority) VALUES (?, ?, ?, ?, ?)")) {
                    stmt.setString(1, task.getName());
                    stmt.setString(2, task.getDescription());
                    stmt.setString(3, task.getDeadLine().toString()); // Convert LocalDate to String
                    stmt.setString(4, task.getStatus().toString());  // Store enum value as string
                    stmt.setString(5, task.getPriority().toString()); // Store enum value as string
                    stmt.executeUpdate();
                    System.out.printf("Task with name : %s is inserted into database.%n",task.getName());
                } catch (SQLException e) {
                    System.err.println("Database error while inserting task:");
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while handling task creation:");
            e.printStackTrace();
        }
    }

    public void EditHandler(ObjectInputStream input, Set<Task> tasks, Connection conn) throws ClassNotFoundException, IOException {
        Task editTask = (Task) input.readObject();
        Task taskToRemove = null;
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.getName().equals(editTask.getName())) {
                    taskToRemove = task;
                    break;
                }
            }
            if (taskToRemove != null) {
                tasks.remove(taskToRemove);
                tasks.add(editTask);
            }
        }

        // Update task in the database
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE tasks SET name = ?, description = ?, deadline = ?, status = ?, priority = ? WHERE name = ?")) {
            stmt.setString(1, editTask.getName()); // Set the new name (at position 1)
            stmt.setString(2, editTask.getDescription()); // Set the description (at position 2)
            stmt.setString(3, editTask.getDeadLine().toString()); // Set the deadline (at position 3)
            stmt.setString(4, editTask.getStatus().toString()); // Set the status (at position 4)
            stmt.setString(5, editTask.getPriority().toString()); // Set the priority (at position 5)
            stmt.setString(6, editTask.getName()); // Set the original name for the WHERE clause (position 6)

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.printf("Task with name : %s is updated in database by : %s%n",editTask.getName(),username);
            } else {
                System.out.println("Task not found in database (not updated).");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update task in database:");
            e.printStackTrace();
        }
    }

    public void DeleteHandler(ObjectInputStream input, Set<Task> tasks, Connection conn) throws ClassNotFoundException, IOException {
        Task removedTask = (Task) input.readObject();
        Task taskToRemove = null;
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.getName().equals(removedTask.getName())) {
                    taskToRemove = task;
                    break;
                }
            }
            if (taskToRemove != null) {
                tasks.remove(taskToRemove);
            }
        }

        // Delete task from the database
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM tasks WHERE name = ?")) {
            stmt.setString(1, removedTask.getName());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.printf("Task with name : %s deleted from database by : %s%n",removedTask.getName(),username);
            } else {
                System.out.println("Task not found in database (not deleted).");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete task from database:");
            e.printStackTrace();
        }
    }

    public void saveHandler(Set<Task> tasks) {
        System.out.println("Name\tDescription\tDeadLine\tStatus\tPriority");
        for (Task task : tasks) {
            System.out.printf("The name of the Task is : %s\t", task.getName());
            System.out.printf("The description of the Task is : %s\t", task.getDescription());
            System.out.printf("The deadline of the Task is : %s\t", task.getDeadLine());
            System.out.printf("The status of the Task is : %s\t", task.getStatus());
            System.out.printf("The priority of the Task is : %s\t", task.getPriority());
        }
    }

    public void getAllTasksFromDatabase(Set<Task> tasks, Connection conn) {
        String sql = "SELECT name, description, deadline, status, priority FROM tasks";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                LocalDate deadline = LocalDate.parse(rs.getString("deadline"));
                String status = rs.getString("status");
                String priority = rs.getString("priority");

                Task task = new Task(name, description, deadline, Task.Status.valueOf(status), Task.Priority.valueOf(priority));
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks from the database:");
            e.printStackTrace();
        }
    }
    public void ResearchHandler (ObjectOutputStream out ,Set<Task> tasks,String Line)
    {
    	try
    	{
    		
	    	String name = Line.substring(9).trim();
			for(Task task : tasks)
			{
				if(task.getName().equals(name))
				{
					
						out.writeObject(task);
						break;
					
				}
				else
				{
					out.writeObject(null);
				}
			}
    	}
		catch (IOException e) {
		
		e.printStackTrace();
	}
    
  }
}
