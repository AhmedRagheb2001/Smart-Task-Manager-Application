package Smart;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.Collections;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Set<String> usernames;
    private final Set<Task> tasks;
    private String currentUser; // Store the username of the logged-in user

    public ClientHandler(Socket clientSocket, Set<String> names, Set<Task> taskList) {
        this.socket = clientSocket;
        this.usernames = Collections.synchronizedSet(names);
        this.tasks = Collections.synchronizedSet(taskList);
        this.currentUser = null; // Initially, the user is not logged in
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split(" ", 2); // Split command from arguments
                String command = parts[0].toUpperCase();

                switch (command) {
                    case "LOGIN":
                        handleLogin(parts, out);
                        break;
                    case "CREATE":
                        handleCreate(parts, out);
                        break;
                    case "EDIT":
                        handleEdit(parts, out);
                        break;
                    case "DELETE":
                        handleDelete(parts, out);
                        break;
                    default:
                        out.println("Unknown command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogin(String[] parts, PrintWriter out) {
        if (parts.length < 2) {
            out.println("Usage: LOGIN username");
            return;
        }

        String username = parts[1];
        if (usernames.contains(username)) {
            out.println("Denied");
        } else {
            usernames.add(username);
            currentUser = username;
            out.println("OK");
            System.out.println(username + " logged in.");
        }
    }

    private void handleCreate(String[] parts, PrintWriter out) {
        if (currentUser == null) {
            out.println("You must log in first.");
            return;
        }
        if (parts.length < 2) {
            out.println("Usage: CREATE taskName description deadline priority");
            return;
        }

        // Example: CREATE task1 Finish homework 2025-05-10 HIGH
        String[] taskParts = parts[1].split(" ", 4);
        if (taskParts.length < 4) {
            out.println("Invalid format. Usage: CREATE taskName description deadline priority");
            return;
        }

        String name = taskParts[0];
        String description = taskParts[1];
        LocalDate deadline;
        Task.Priority priority;

        try {
            deadline = LocalDate.parse(taskParts[2]);
            priority = Task.Priority.valueOf(taskParts[3].toUpperCase());
        } catch (DateTimeParseException | IllegalArgumentException e) {
            out.println("Invalid date or priority. Make sure the date is in yyyy-MM-dd format and priority is LOW, MEDIUM, or HIGH.");
            return;
        }

        Task newTask = new Task(name, description, deadline, Task.Status.PENDING, priority, currentUser);
        tasks.add(newTask);
        out.println("Task created: " + name);
        System.out.println("Task created: " + newTask);
    }

    private void handleEdit(String[] parts, PrintWriter out) {
        if (currentUser == null) {
            out.println("You must log in first.");
            return;
        }
        if (parts.length < 2) {
            out.println("Usage: EDIT taskName newDescription newDeadline newPriority");
            return;
        }

        // Example: EDIT task1 New description 2025-06-01 HIGH
        String[] taskParts = parts[1].split(" ", 4);
        if (taskParts.length < 4) {
            out.println("Invalid format. Usage: EDIT taskName newDescription newDeadline newPriority");
            return;
        }

        String name = taskParts[0];
        String newDescription = taskParts[1];
        LocalDate newDeadline;
        Task.Priority newPriority;

        try {
            newDeadline = LocalDate.parse(taskParts[2]);
            newPriority = Task.Priority.valueOf(taskParts[3].toUpperCase());
        } catch (DateTimeParseException | IllegalArgumentException e) {
            out.println("Invalid date or priority. Make sure the date is in yyyy-MM-dd format and priority is LOW, MEDIUM, or HIGH.");
            return;
        }

        synchronized (tasks) {
            boolean found = false;
            for (Task task : tasks) {
                if (task.getName().equals(name) && task.getOwner().equals(currentUser)) {
                    // Modify the task
                    tasks.remove(task);
                    task.setDescription(newDescription);
                    task.setDeadLine(newDeadline);
                    task.setPriority(newPriority);
                    tasks.add(task);
                    out.println("Task edited: " + name);
                    found = true;
                    break;
                }
            }
            if (!found) {
                out.println("Task not found or you do not own it.");
            }
        }
    }

    private void handleDelete(String[] parts, PrintWriter out) {
        if (currentUser == null) {
            out.println("You must log in first.");
            return;
        }
        if (parts.length < 2) {
            out.println("Usage: DELETE taskName");
            return;
        }

        String name = parts[1];

        synchronized (tasks) {
            boolean removed = tasks.removeIf(task -> task.getName().equals(name) && task.getOwner().equals(currentUser));
            if (removed) {
                out.println("Task deleted: " + name);
            } else {
                out.println("Task not found or you do not own it.");
            }
        }
    }
}
