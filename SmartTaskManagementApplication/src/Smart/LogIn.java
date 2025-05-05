package Smart;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.control.PasswordField;

public class LogIn extends Application {
	
	public static void main (String [] args)
	{
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
		Button SignUp = new Button ("Sign out");
		
		GridPane gridpane = new GridPane ();
		
		gridpane.add(Name, 0, 0);
		gridpane.add(Password, 0, 1);
		gridpane.add(Field1, 1, 0);
		gridpane.add(Field2, 1, 1);
		gridpane.add(SignIn, 0, 2);
		gridpane.add(SignUp, 1, 2);
		
		gridpane.setPadding(new Insets (30));
		gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(20);
		gridpane.setVgap(20);
		
		
		SignIn.setOnAction(e->
		{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("You have successed log in");
				alert.showAndWait();
				
				DashBoard dash = new DashBoard();
				dash.start(primaryStage );
				
				
		});
		
		SignUp.setOnAction(e->
		{
			SignUp signup = new SignUp();
			signup.start(primaryStage);
		});
		
		
		//To add the icon to the Application
		Image icon = new Image ("icon.png");
		
		primaryStage.getIcons().add(icon);
		
		Scene scene = new Scene(gridpane, 500 ,300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Smart Task Manager Application");
		primaryStage.show();
	}

}
