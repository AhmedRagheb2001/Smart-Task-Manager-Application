package Smart;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Dashboard {
	
	
	
	public BorderPane CreateDashboardPage()
	{	
		BorderPane root = new BorderPane ();
		//Here we create the table to display the task information
		TableView <Task>table = new TableView<Task>();
		
		TableColumn<Task,String> nameColumn = new TableColumn <>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Task,String> descriptionColumn = new TableColumn<> ("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		TableColumn<Task,Integer> deadlineColumn = new TableColumn <>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadLine"));
		
		table.getColumns().add(nameColumn);
		table.getColumns().add(descriptionColumn);
		table.getColumns().add(deadlineColumn);
		
		Label label1 = new Label ("Hi");
		Label label2 = new Label ("world");
		
		VBox vbox = new VBox (10,label1,label2);
		vbox.setPrefWidth(200);
		vbox.setPrefHeight(150);
		root.setCenter(table);
		root.setLeft(vbox);
		return root;
	}
		

}
