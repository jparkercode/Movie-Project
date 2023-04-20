import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaFx extends Application implements Initializable{
	@FXML
	Button myButton;
	
	@FXML
	Stage stage;
	Stage stage2;
	Scene scene1;
	Scene scene2;
	FXMLLoader loader; //Full Scope loader
	static String[] primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage; 
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("moviefxml.fxml"));
		p = loader.load();
		

	
		
		stage.setScene(new Scene(p));
		
		
		stage.show();
		stage = primaryStage; 
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		
		
		// TODO Auto-generated method stub
		
	}
	public void sceneSelector(String scene) throws IOException {
		 Parent pane = FXMLLoader.load(getClass().getResource("select movie.fxml"));

		//   Scene scene1 = new Scene( pane );
		   stage.getScene().setRoot(pane);;
	}
	
	//First Button onAction Method
	public void button1Click(ActionEvent event) throws IOException{
		Theatre t = new Theatre();
		
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();

		
	
			
	
	}
	//Button: AMC on click method
	public void amc(ActionEvent event) throws IOException{
		Theatre.type = "AMC";
		Controller2.src = "636021834358437385-amc-la";
		//Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
	}
	public void cinemark(ActionEvent event) throws IOException{
		Controller2.src = "cinemark1";
		Theatre.type = "CINEMARK";
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
		
	}
	//Button: Patriot on click method
	public void patriot(ActionEvent event) throws IOException{
		Controller2.src = "patriot";
		Theatre.type = "PATRIOT";
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
		
	}
	//Button: marcus on click method
	public void marcus(ActionEvent event) throws IOException{
		Controller2.src = "marcus";
		Theatre.type = "BLACKSTONE";
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
			
		}
	//Button: Regal on click method
	public void regal(ActionEvent event) throws IOException{
		Controller2.src = "Regal+Cinemas+Movie+Theatre";
		Theatre.type = "REGAL";
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
		
	}
	//Button: Showcase on click method
	public void showcase(ActionEvent event) throws IOException{
		Controller2.src = "showcase";
		Theatre.type = "SHOWCASE";
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
	}

}
