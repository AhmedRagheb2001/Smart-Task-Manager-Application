package Smart;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
public class UI extends Application
{
	public static void main (String [] args)
	{
		System.out.println("Hello world");
		launch(args);
	}
	
	public void start (Stage primaryStage)
	{
		
		//First we will start with the sign in page 
		
		Label Name = new Label("Enter your username : ");
		Label Password = new Label ("Enter your password : ");
		
		TextField Field1 =new TextField ();
		PasswordField Field2 = new PasswordField ();
		
		Field1.setPromptText("Your login name");
		Field2.setPromptText("Minimum 8 characters");
		
		Button SignIn = new Button ("Sign in");
		Button SignOut = new Button ("Sign out");
		
		GridPane gridpane = new GridPane ();
		
		gridpane.add(Name, 0, 0);
		gridpane.add(Password, 0, 1);
		gridpane.add(Field1, 1, 0);
		gridpane.add(Field2, 1, 1);
		gridpane.add(SignIn, 0, 2);
		gridpane.add(SignOut, 1, 2);
		
		gridpane.setPadding(new Insets (30));
		gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(20);
		gridpane.setVgap(20);
		
		//To add the icon to the Application
		Image icon = new Image ("icon.png");
		
		Scene SignInScene = new Scene (gridpane ,400,300);
		SignInScene.getStylesheets().add("Sign.css");
		
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
		
		TableColumn<Task,Integer> deadlineColumn = new TableColumn <>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadLine"));
		
		table.getColumns().add(nameColumn);
		table.getColumns().add(descriptionColumn);
		table.getColumns().add(deadlineColumn);
		
		//This is the left region
		
		Label label1 = new Label ("Welcome");
		label1.getStyleClass().add("label-style");
		Label label2 = new Label ("Admin");
		label2.getStyleClass().add("label-style");
		
		VBox vbox = new VBox (10,label1,label2);
		vbox.setPrefWidth(300);
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
		MenuItem searchItem = new MenuItem ("Search");
		
		//Now these are the menu items for Edit Menu
		
		MenuItem editItem = new MenuItem("Edit");
		MenuItem deleteItem = new MenuItem ("Delete");
		Menu sortItem = new Menu ("Sort");
		
		
		//Now these are the items in the sort sub Menu
		MenuItem AscendingItem = new MenuItem("Ascending");
		MenuItem DescendingItem = new MenuItem("Descending");
		
		
		// Now we will map these menu items to File menu
		FileMenu.getItems().addAll(createItem,searchItem);
		
		//Now we will map the items of the Edit Menu
		EditMenu.getItems().addAll(editItem,deleteItem,sortItem);
		
		//Now we will map the items of the Sort sub Menu
		sortItem.getItems().addAll(AscendingItem,DescendingItem);
		
		root.setCenter(table);
		root.setLeft(vbox);
		root.setTop(menubar);
		
		Scene DashboardScene = new Scene (root,500,300);
		DashboardScene.getStylesheets().add("Dashboard.css");
		
		//This is the action event for the signIn button in sign in page(it will go to the dash board page)
		SignIn.setOnAction(e->
		{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("You have successed log in");
				alert.showAndWait();
				
				primaryStage.setScene(DashboardScene);
				
				
		});
		
		
		primaryStage.setScene(SignInScene);
		primaryStage.setTitle("Smart Task Mangement Application");
		primaryStage.getIcons().add(icon);
		primaryStage.show();
		
		
	}
}


