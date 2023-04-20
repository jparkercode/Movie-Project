import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ControllerShowtimes extends JavaFx implements Initializable {
	@FXML
	Pane pane;
	@FXML
	ImageView bckrd;
	@FXML
	Label theatreType;
	
	public static WebView web;
	public static String webhtml;
	public static String imgUrl;
	ArrayList<String> sortedTimes = new ArrayList<>();
	boolean greatest = true;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		theatreType.setText(Theatre.locationin);
		System.out.println(imgUrl);
		//Format Movie Banner
		bckrd = new ImageView(imgUrl);
		bckrd.setPreserveRatio(false);
		bckrd.maxHeight(380);
		bckrd.maxWidth(600);
		bckrd.setFitWidth(380);
		bckrd.setFitHeight(600);
		pane.getChildren().add(bckrd);

		//Array of Time Labels
		Label[] times = new Label[Movie.showtimes.size()];
		int count = 0;
		int offset = 450;
		int offsetY = 0;
		//Format Time Labels to Scene
		for(int x=0;x<times.length;x++) { // Label l:times
			final int xx = x;
			times[x] = new Label(Movie.showtimes.get(count));
			times[x].setLayoutX(offset);
			times[x].setLayoutY(20+offsetY);
			times[x].setTextFill(Color.WHITE);
			times[x].setFont(Font.font("Cambria", 24));
			offset+=150;
		if(offset>1100) {
			offset = 450;
			offsetY += 30;
		}
		
			count++;
			
			pane.getChildren().add(times[x]);
			times[x].setOnMousePressed(new EventHandler<MouseEvent>() {
			
				@Override
				public void handle(MouseEvent event) {
					ControllerMovieBrowser.labelnum = xx;
					loader = new FXMLLoader(getClass().getResource("moviebrowser.fxml"));
					Pane p = null;
					try {
						p = loader.load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
					window.setScene(new Scene(p));
					window.show();
					
					
					
				}	
				});
			
		}
	}
	/**
	 * 
	 * Resets Scraped data for previous scene
	 * and goes to the previous scene
	 * 
	 */
	public void back(ActionEvent event) throws IOException{
		Movie.movielist.clear();
		Movie.showtimeurls.clear();
		Movie.showtimeclick.clear();
		Movie.showtimes.clear();
		Movie.urls.clear();
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("select movie.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
	}	
}
