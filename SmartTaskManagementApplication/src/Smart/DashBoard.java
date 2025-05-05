package Smart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
public class DashBoard extends Application {

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
    			MenuItem deleteItem = new MenuItem ("Delete");
    			Menu sortItem = new Menu ("Sort");
    			
    			
    			//Now these are the items in the sort sub Menu
    			MenuItem AscendingItem = new MenuItem("Ascending");
    			MenuItem DescendingItem = new MenuItem("Descending");
    			
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
    			EditMenu.getItems().addAll(editItem,deleteItem,sortItem);
    			
    			//Now we will map the items of the Sort sub Menu
    			sortItem.getItems().addAll(AscendingItem,DescendingItem);
    			
    			//Now we map the about menu item
    			AboutMenu.getItems().add(aboutItem);
    			//Now we will create the right origin 
    			
    			Button SignUp = new Button ("Sign Up");
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
    			
    			Scene DashboardScene = new Scene (root,500,300);
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
		TextField field1 = new TextField();
		
		Label descriptionLabel = new Label("Enter the description of the task : ");
		TextField field2 = new TextField();
		
		Label deadlineLabel = new Label("Enter the deadline of the task : ");
		TextField field3 = new TextField ();
		
		Button createButton = new Button ("Create");
		createButton.setOnAction(e->
		{
			String name;
			String description;
			String deadLine;
			
			name =field1.getText();
			description = field2.getText();
			deadLine = field3.getText();
			
		});
		
		GridPane gridpane = new GridPane();
		
		gridpane.add(nameLabel, 0, 0);
		gridpane.add(field1, 1, 0);
		
		gridpane.add(descriptionLabel, 0, 1);
		gridpane.add(field2, 1, 1);
		
		gridpane.add(deadlineLabel, 0, 2);
		gridpane.add(field3, 1, 2);
		
		gridpane.setPadding(new Insets(10));
		gridpane.setAlignment(Pos.CENTER);
		
		VBox createForm = new VBox(20 ,gridpane,createButton);
		createForm.setPadding(new Insets(10));
		createForm.setAlignment(Pos.CENTER);
		
		return createForm;
	}
    
    //This is the functionality of the Search Menu Item
    public VBox SearchForm()
    {
    	Label nameLabel = new Label ("Enter the name of the task : ");
    	TextField Field = new TextField ();
    	
    	Label label1 = new Label();
    	Label label2 = new Label();
    	Label label3 = new Label();
    	
    	Button searchButton = new Button ("Search");
    	searchButton.setOnAction(e->
    	{
    		// here we will do it later
    	});
    	HBox hbox = new HBox (10,nameLabel,Field,searchButton);
    	hbox.setPadding(new Insets(10));
    	hbox.setAlignment(Pos.CENTER);
    	
    	VBox vbox = new VBox (20,hbox,label1,label2,label3);
    	vbox.setAlignment(Pos.CENTER);
    	vbox.setPadding(new Insets(10));
    	
    	return vbox;
    }
    public static void main (String [] args)
    {
    	launch(args);
    }
   
   
    
}
