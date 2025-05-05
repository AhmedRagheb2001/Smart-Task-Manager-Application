package Smart;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;    
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
public class SignUp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label title = new Label("Welcome to the Signup Page");

        // InputS
        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();

        Label passLabel = new Label("Enter your password:");
        PasswordField passField = new PasswordField();

        Label confirmLabel = new Label("Confirm your password:");
        PasswordField confirmField = new PasswordField();

        // Buttons
        Button backBtn = new Button("Sign In");
        Button signUpBtn = new Button("Sign Up");
        
        
        //Here is a grid pane "I think it is better"
        GridPane gridpane = new GridPane();
        
        gridpane.add(nameLabel, 0, 0);
        gridpane.add(nameField, 1, 0);
        gridpane.add(passLabel, 0, 1);
        gridpane.add(passField, 1, 1);
        gridpane.add(confirmLabel, 0, 2);
        gridpane.add(confirmField, 1, 2);
        gridpane.add(backBtn, 0, 3);
        gridpane.add(signUpBtn, 1, 3);
        
        gridpane.setPadding(new Insets(10));
        gridpane.setAlignment(Pos.CENTER);
        // Layout
        VBox layout = new VBox(20,title,gridpane);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // Sign Up button action
        signUpBtn.setOnAction(e -> {
            String name = nameField.getText();
            String pass = passField.getText();
            String confirm = confirmField.getText();

            if (name.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                showAlert("Please fill in all fields.");
            } else if (!pass.equals(confirm)) {
                showAlert("Passwords do not match.");
            } else {
                showAlert("Sign up successful!");
                DashBoard dash = new DashBoard();
                dash.start(primaryStage);
            }
        });

        // Back button action
        backBtn.setOnAction(e -> {
            showAlert("Going back to Sign In screen...");
            LogIn login = new LogIn();
            login.start(primaryStage);
        });

        // Show window
        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   
}