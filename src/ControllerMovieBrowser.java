import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;

import org.jsoup.nodes.Document;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ControllerMovieBrowser extends JavaFx implements Initializable{
	public static String url;
	public static int labelnum;
	@FXML
	WebView web;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	
		url = Movie.showtimeclick.get(labelnum);
		System.out.println("url: "+url);
		WebEngine webload = web.getEngine();
		
		webload.setJavaScriptEnabled(true);
		webload.setUserAgent("Safari/537.36");
		webload.load(url);
		  
	
	
		
	
	}

}

