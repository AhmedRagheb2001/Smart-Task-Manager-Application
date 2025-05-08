package Smart;

import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import java.net.*;
import java.io.*;

public class DashBoard extends Application {
	private ObservableList <Task> data =FXCollections.observableArrayList();
	private Socket socket;
	private String username;
    public DashBoard(Socket socket,String name) {
		// TODO Auto-generated constructor stub
    	this.username= name;
    	this.socket=socket;
	}

	@Override
    public void start(Stage primaryStage) {
        
    	//Now This is for the Dash board page
		
    			BorderPane root = new BorderPane ();
    			
    			//Here we create the table to display the task information
    			
    			TableView <Task>table = new TableView<Task>();
    			table.getStyleClass().add("table-style");
    			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    			
    			TableColumn<Task,String> nameColumn = new TableColumn <>("Name");
    			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    			
    			TableColumn<Task,String> descriptionColumn = new TableColumn<> ("Description");
    			descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    			
    			TableColumn<Task,LocalDate> deadlineColumn = new TableColumn <>("Deadline");
    			deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadLine"));
    			
    			TableColumn <Task,Task.Status> statusColumn = new TableColumn<>("Status");
    			statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    			
    			TableColumn<Task,Task.Priority> priorityColumn = new TableColumn<>("Priority");
    			priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
    			
    			table.setItems(data);
    			
    			table.getColumns().add(nameColumn);
    			table.getColumns().add(descriptionColumn);
    			table.getColumns().add(deadlineColumn);
    			table.getColumns().add(statusColumn);
    			table.getColumns().add(priorityColumn);
    			
    			//This is the left region
    			
    			Label label1 = new Label ("Welcome");
    			label1.getStyleClass().add("label-style");
    			Label label2 = new Label (username);  			
    			label2.getStyleClass().add("label-style");
    			
    			VBox vbox = new VBox (10,label1,label2);
    			vbox.setMinWidth(120);
    			vbox.setPrefHeight(150);
    			vbox.setAlignment(Pos.CENTER);
    			
    			//Now we will create the top region , which the Menu bar 
    			
    			MenuBar menubar = new MenuBar();
    			
    			Menu FileMenu = new Menu ("File");
    			Menu EditMenu = new Menu ("Edit");
    			Menu AboutMenu = new Menu ("About");
    			
    			//we will attach the menu bar with the drop down menus
    			menubar.getMenus().add(FileMenu);
    			menubar.getMenus().add(EditMenu);
    			menubar.getMenus().add(AboutMenu);
    			
    			//Now we will create the menu items for File Menu 
    			
    			MenuItem createItem = new MenuItem ("Create");
    			createItem.setOnAction(e->
    			{
    				root.setBottom(createForm());
    			});
    			
    			
    			MenuItem searchItem = new MenuItem ("Search");
    			searchItem.setOnAction(e->
    			{
    				root.setBottom(SearchForm());
    			});
    			
    			//Now these are the menu items for Edit Menu
    			
    			MenuItem editItem = new MenuItem("Edit");
    			editItem.setOnAction(e->
    			{
    				root.setBottom(editForm(table));
    			});
    			MenuItem deleteItem = new MenuItem ("Delete");
    			deleteItem.setOnAction(e->
    			{
    				deleteSelectedTask(table);
    			});
    			Menu sortItem = new Menu ("Sort");
    			Menu filterItem = new Menu("Filter");
    			
    			//Now these are the items in the sort sub Menu
    			MenuItem AscendingItem = new MenuItem("Ascending");
    			MenuItem DescendingItem = new MenuItem("Descending");
    			
    			
    			//These are the items in the filter sub menu
    			MenuItem completeItem = new MenuItem("Completed");
    			MenuItem overdueItem = new MenuItem("Overdue");
    			
    			// This is the item of the About Menu
    			MenuItem aboutItem = new MenuItem ("About");
    			aboutItem.setOnAction(e->
    			{
    				About about= new About();
    				about.start(primaryStage);
    			});
    			
    			// Now we will map these menu items to File menu
    			FileMenu.getItems().addAll(createItem,searchItem);
    			
    			//Now we will map the items of the Edit Menu
    			EditMenu.getItems().addAll(editItem,deleteItem,sortItem,filterItem);
    			
    			//Now we will map the items of the Sort sub Menu
    			sortItem.getItems().addAll(AscendingItem,DescendingItem);
    			
    			//Now we will map the items of the filter sub menu
    			filterItem.getItems().addAll(completeItem,overdueItem);
    			//Now we map the about menu item
    			AboutMenu.getItems().add(aboutItem);
    			//Now we will create the right origin 
    			
    			Button SignUp = new Button ("Sign Up");
    			SignUp.setOnAction(e->
    			{
    				SignUp signup = new SignUp();
    				signup.start(primaryStage);
    			});
    			
    			SignUp.getStyleClass().add("button-style");
    			
    			Button LogOut = new Button ("Log Out");
    			LogOut.getStyleClass().add("button-style");
    			
    			VBox vbox2 = new VBox(10,SignUp,LogOut);
    			vbox2.setAlignment(Pos.CENTER);
    			vbox2.setMinWidth(100);
    			vbox2.setPrefHeight(150);
    			
    			 
    			
    			root.setCenter(table);
    			root.setLeft(vbox);
    			root.setTop(menubar);
    			root.setRight(vbox2);
    			
    			Scene DashboardScene = new Scene (root,800,600);
    			DashboardScene.getStylesheets().add("Dashboard.css");
    			
    			LogOut.setOnAction(e->
    			{
    				LogIn login = new LogIn();
    				login.start(primaryStage);
    				
    			});
    			
    	primaryStage.setTitle("Smart Task Mangement Application");
        primaryStage.setScene(DashboardScene);
        primaryStage.show();
    }
    
    // This is the functionality of the Create Menu Item
    public VBox createForm()
	{
		Label nameLabel = new Label ("Enter the name of the task : ");
		nameLabel.getStyleClass().add("label-form");
		TextField field1 = new TextField();
		
		Label descriptionLabel = new Label("Enter the description of the task : ");
		descriptionLabel.getStyleClass().add("label-form");
		TextField field2 = new TextField();
		
		Label deadlineLabel = new Label("Choose the deadline of the task : ");
		deadlineLabel.getStyleClass().add("label-form");
		DatePicker deadLinePicker = new DatePicker();
		
		Label statusLabel = new Label ("Choose the status of the task : ");
		statusLabel.getStyleClass().add("label-form");
		ComboBox<Task.Status> statusComboBox = new ComboBox<>();
		statusComboBox.getItems().setAll(Task.Status.values());
		statusComboBox.setValue(Task.Status.PENDING);
		
		Label priorityLabel =new Label("Choose the priority of the task : ");
		priorityLabel.getStyleClass().add("label-form");
		ComboBox<Task.Priority> priorityComboBox = new ComboBox<>();
		priorityComboBox.getItems().setAll(Task.Priority.values());
		priorityComboBox.setValue(Task.Priority.MEDIUM);
		
		Button createButton = new Button ("Create");
		createButton.setOnAction(e->
		{
			String name;
			String description;
			LocalDate deadLine;
			Task.Status status;
			Task.Priority priority;
			
			name =field1.getText();
			description = field2.getText();
			deadLine =deadLinePicker.getValue();
			status=statusComboBox.getValue();
			priority =priorityComboBox.getValue();
			
			if(name.isEmpty() || description.isEmpty())
			{
				showAlert("Please fill the fields");
			}
			else
			{
				Task task = new Task (name,description,deadLine,status,priority);
				
				data.add(task);
				try
				{
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					PrintWriter output = new PrintWriter(socket.getOutputStream());
					output.println("Create : ");
					out.writeObject(task);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
				
			}
			
		});
		
		HBox hbox = new HBox(10,nameLabel,field1,descriptionLabel,field2,deadlineLabel,deadLinePicker,statusLabel,statusComboBox,priorityLabel,priorityComboBox);
		hbox.setPadding(new Insets(10));
		hbox.setAlignment(Pos.CENTER);
		
		VBox createForm = new VBox(20 ,hbox,createButton);
		createForm.setPadding(new Insets(10));
		createForm.setAlignment(Pos.CENTER);
		
		return createForm;
	}
    
    //This is the functionality of the Search Menu Item
    public VBox SearchForm()
    {
    	Label nameLabel = new Label ("Enter the name of the task : ");
    	nameLabel.getStyleClass().add("label-form");
    	TextField Field = new TextField ();
    	
    	Label label1 = new Label();
    	Label label2 = new Label();
    	Label label3 = new Label();
    	Label label4 = new Label();
    	Label label5 = new Label();
    	
    	Button searchButton = new Button ("Search");
    	searchButton.setOnAction(e->
    	{
    		// here we will do it later
    	});
    	HBox hbox = new HBox (10,nameLabel,Field,searchButton);
    	hbox.setPadding(new Insets(10));
    	hbox.setAlignment(Pos.CENTER);
    	
    	VBox vbox = new VBox (20,hbox,label1,label2,label3,label4,label5);
    	vbox.setAlignment(Pos.CENTER);
    	vbox.setPadding(new Insets(10));
    	
    	return vbox;
    }
    public VBox editForm(TableView<Task> table) {
        Task selectedTask = table.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            showAlert("Please select a task to edit.");
            return new VBox(); // Return an empty form if nothing is selected
        }

        Label nameLabel = new Label("Edit the name of the task:");
        nameLabel.getStyleClass().add("label-form");
        TextField field1 = new TextField(selectedTask.getName());

        Label descriptionLabel = new Label("Edit the description of the task:");
        descriptionLabel.getStyleClass().add("label-form");
        TextField field2 = new TextField(selectedTask.getDescription());

        Label deadlineLabel = new Label("Edit the deadline of the task:");
        deadlineLabel.getStyleClass().add("label-form");
        DatePicker deadLinePicker = new DatePicker(selectedTask.getDeadLine());

        Label statusLabel = new Label("Edit the status of the task:");
        statusLabel.getStyleClass().add("label-form");
        ComboBox<Task.Status> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().setAll(Task.Status.values());
        statusComboBox.setValue(selectedTask.getStatus());

        Label priorityLabel = new Label("Edit the priority of the task:");
        priorityLabel.getStyleClass().add("label-form");
        ComboBox<Task.Priority> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().setAll(Task.Priority.values());
        priorityComboBox.setValue(selectedTask.getPriority());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            String name = field1.getText();
            String description = field2.getText();
            LocalDate deadLine = deadLinePicker.getValue();
            Task.Status status = statusComboBox.getValue();
            Task.Priority priority = priorityComboBox.getValue();

            if (name.isEmpty() || description.isEmpty()) {
                showAlert("Please fill all fields.");
            } else {
                selectedTask.setName(name);
                selectedTask.setDescription(description);
                selectedTask.setDeadLine(deadLine);
                selectedTask.setStatus(status);
                selectedTask.setPriority(priority);
                table.refresh(); // refresh table to show updated values
                showAlert("Task updated successfully.");
            }
        });

        HBox hbox = new HBox(10, nameLabel, field1, descriptionLabel, field2, deadlineLabel, deadLinePicker,
                statusLabel, statusComboBox, priorityLabel, priorityComboBox);
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.CENTER);

        VBox editForm = new VBox(20, hbox, editButton);
        editForm.setPadding(new Insets(10));
        editForm.setAlignment(Pos.CENTER);

        return editForm;
    }
    public void deleteSelectedTask(TableView<Task> table) {
        Task selectedTask = table.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            showAlert("Please select a task to delete.");
        } else {
            data.remove(selectedTask);
            table.refresh(); // Optional, but ensures UI updates immediately
            showAlert("Task deleted successfully.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   

//    ?
//   
    
}
