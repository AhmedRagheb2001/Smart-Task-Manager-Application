package Smart;

import javafx.application.Application;
import javafx.scene.Scene;
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
public class LogIn extends Application
{
	public static void main (String [] args)
	{
		System.out.println("Hello world");
		launch(args);
	}
	
	public void start (Stage primaryStage)
	{
		Label Name = new Label("Enter your username : ");
		Label Password = new Label ("Enter your password : ");
		
		TextField Field1 =new TextField ();
		PasswordField Field2 = new PasswordField ();
		
		Field1.setPromptText("Your login name");
		Field2.setPromptText("Minimum 8 characters");
		
		Button SignIn = new Button ("Sign in");
		//It will go to the dash board page
		SignIn.setOnAction(e->
		{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("You have successed log in");
				alert.showAndWait();
				Dashboard dashboard = new Dashboard ();
				BorderPane newDashboardRoot = dashboard.CreateDashboardPage();
				Scene dashboardScene = new Scene (newDashboardRoot,500,300);
				primaryStage.setScene(dashboardScene);
				primaryStage.setTitle("Dashboard Page");
				
				
		});
		
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
		
		Image icon = new Image ("icon.png");
		Scene scene1 = new Scene (gridpane ,400,300);
		scene1.getStylesheets().add("myStyle.css");
		
		
		primaryStage.setScene(scene1);
		primaryStage.setTitle("Smart Task Mangement Application");
		primaryStage.getIcons().add(icon);
		primaryStage.show();
		
		
	}
	
}
