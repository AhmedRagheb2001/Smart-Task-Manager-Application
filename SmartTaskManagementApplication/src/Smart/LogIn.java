package Smart;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.io.*;
import java.io.IOException;
import java.net.*;
public class LogIn extends Application {
	 private Socket socket;
	 private ObjectOutputStream out;
	 private ObjectInputStream in;
	
	public static void main (String [] args)
	{
		launch(args);
	}
	public void start (Stage primaryStage)
	{
		
		//First we will start with the sign in page 
		try {
				socket = new Socket("localhost", 8003);
				out = new ObjectOutputStream(socket.getOutputStream());
			    in = new ObjectInputStream(socket.getInputStream());
	            
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
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
		
		
		SignIn.setOnAction(e -> {
		    String name = Field1.getText();
		    String password = Field2.getText();

		    if (name.isEmpty() || password.isEmpty()) {
		        showAlert("Please fill in all fields.");
		    } else if (password.length() < 8) {
		        showAlert("The password should be at least 8 characters.");
		    } else {
		    	
		    	new Thread(()->
		    	{
		    		try
			    	{

			            out.writeObject("Login : " + name);
			            String response = null;
						try {
							response = (String)in.readObject();
						} catch (ClassNotFoundException e1) {
							
							e1.printStackTrace();
						}

						if ("OK".equalsIgnoreCase(response)) {
						    Platform.runLater(() -> {
						        Alert alert = new Alert(AlertType.INFORMATION);
						        alert.setContentText("You have successfully logged in.");
						        alert.showAndWait();

						        DashBoard dash = new DashBoard(socket, name, in, out);
						        dash.start(primaryStage);
						    });
						} else {
						    Platform.runLater(() -> showAlert("Username already in use. Try another one."));
						}
			    	}
			    	catch(IOException ex)
			    	{
			    		ex.printStackTrace();
			    	}
		    	}).start();

		        
		    }
		});

		
		SignUp.setOnAction(e->
		{
			String name = Field1.getText();
			SignUp signup = new SignUp(socket,name,in,out);
			signup.start(primaryStage);
		});
		
		
		//To add the icon to the Application
		Image icon = new Image ("icon.png");
		
		primaryStage.getIcons().add(icon);
		
		Scene scene = new Scene(gridpane, 500 ,300);
		scene.getStylesheets().add("Style.css");

		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Smart Task Manager Application");
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}