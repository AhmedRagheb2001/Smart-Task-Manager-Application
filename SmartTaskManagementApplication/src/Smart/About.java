package Smart;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
public class About extends Application{
	@Override
	public void start(Stage primaryStage)
	{
		Label label1 = new Label ("This project is done by BAU university students : Ahmed Ragheb & Kamsi");
		
		TextArea textArea = new TextArea();
		textArea.setPrefHeight(60);
		textArea.setPrefWidth(150);
		
		Label label2 = new Label ("Enter any Suggestion : ");
		Label label3 = new Label ("");
		
		Button submit = new Button ("Submit");
		submit.setOnAction(e->
		{
			label3.setText("Thanks for your suggestion");
		});
		
		HBox hbox = new HBox (10,label2,textArea);
		hbox.setAlignment(Pos.CENTER);
		
		VBox vbox = new VBox (20,label1,hbox,submit,label3);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene (vbox,500,300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
