import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;
public class ControllerMovieSelect extends JavaFx implements Initializable {
	@FXML
	Pane pane;
	
	@FXML
	WebView web1;
	@FXML
	Label displayDay;
	@FXML
	Label specificTheatre;
	int page2Start;
	/**
	 * Displays WebScraped Movies
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)  {
		// TODO Auto-generated method stub
		
		displayDay.setText(Movie.dateFormat.format(Movie.date.getTime())); //Current Time
		String loc = Theatre.locationin;
		specificTheatre.setText(loc);
		
		try {
			
			Movie.scraperMovies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		ImageView[] img = new ImageView[Movie.urls.size()-page2Start-1];
		Rectangle[] r = new Rectangle[Movie.urls.size()-page2Start-1];
		Label[] lab = new Label[Movie.movielist.size()-page2Start-1];
		
		
		
		int x = page2Start;
		int valueX = 0;
		int offsetX = 0;
		int valueY = 0;
		
		int greatest = 200;

		offsetX = greatest;
		for(String a:Movie.urls) {
			if(x==Movie.urls.size()-1) {
				break;
			}
			
			Image image = new Image(a);
			img[x] = new ImageView();
			img[x].setImage(image);
			img[x].setPreserveRatio(false);
			img[x].maxHeight(300);
			img[x].maxWidth(200);
			img[x].setFitWidth(150);
			img[x].setFitHeight(200);
			r[x] = new Rectangle();
			r[x].setWidth(151);
			r[x].setHeight(201);
			r[x].setLayoutX(valueX);
			r[x].setLayoutY(valueY);
			 r[x].setArcHeight(15);
			 r[x].setArcWidth(15);
			 r[x].setFill(Color.TRANSPARENT);
			r[x].setStroke(Color.RED);
			r[x].toFront();
			img[x].setLayoutX(valueX);
			img[x].setLayoutY(valueY);
			lab[x] = new Label(Movie.movielist.get(x));
			lab[x].setLayoutX(valueX);
			lab[x].setLayoutY(valueY+200);
			lab[x].setTextFill(Color.WHITE);
			lab[x].setFont(Font.font("Cambria", 16));
			lab[x].toFront();
			img[x].toFront();
			int z = x;
			
			pane.getChildren().addAll(r[x],img[x],lab[x]);
			img[x].toFront();
			x++;
			valueY += 250;
	
			if(valueY>700) {
				valueX += offsetX;
				valueY = 0;
			}
			
		}
		int z = 0;
		for(ImageView a:img) {
			int xa = z;
			if(z==Movie.urls.size()-page2Start-1) {
				break;
			}
			a.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					//Scrape Showtimes
					ControllerShowtimes.imgUrl = Movie.urls.get(xa);
					try {
						if(Theatre.type.equals("CINEMARK")) {
							Movie.scraperShowtimes(Movie.url);
							Movie.movienum = xa;
						}
						else {
						Movie.scraperShowtimes(Movie.showtimeurls.get(xa));
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//GOTO SHOWTIMES
					loader = new FXMLLoader(getClass().getResource("showtimes.fxml"));
					Pane p = null;
					try {
						p = loader.load();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("No Showtimes Found");
						alert.setHeaderText("Unable to Locate any Showtimes for Today");
						alert.setContentText("Try another date");

						alert.showAndWait();
						e.printStackTrace();
					}
					
		
					
					Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
					window.setScene(new Scene(p));
					window.show();
				}	
				});
		z++;
		}
	}

	
	public void back(ActionEvent event) throws IOException{
		Movie.movielist.clear();
		Movie.showtimeurls.clear();
		Movie.showtimeclick.clear();
		Movie.showtimes.clear();
		Movie.urls.clear();
		Movie.ids.clear();
		Theatre.location.clear();
		Theatre.State.clear();
		Theatre.scraperTheaters(Theatre.type);
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("locations.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
	}	
	/**
	 * Modifies Current Date for webScraping
	 * and Resets Scraped data and Scene
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void addDay(ActionEvent event) {
		Movie.date.setDate(Movie.date.getDate()+1);
		Movie.movielist.clear();
		Movie.showtimeurls.clear();
		Movie.showtimeclick.clear();
		Movie.showtimes.clear();
		Movie.urls.clear();
		displayDay.setText(Movie.dateFormat.format(Movie.date.getTime()));
		if(Theatre.type.equals("SHOWCASE")) {
		 String link = "https://www.showcasecinemas.com/Umbraco/Api/MovieApi/PaginatedNowShowing?cinemaId="+Movie.ids.get(Theatre.curid)+"&liteVersion=false&expandAttributes=true&splitByAttributes=true&days=1&startDate="+Movie.dateFormat.format(Movie.date.getTime());
			Movie.url = link;
			}
		Pane p = null;
		loader = new FXMLLoader(getClass().getResource("select movie.fxml"));
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
	/**
	 * Modifies Current Date for webScraping
	 * and Resets Scraped data and Scene
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void subDay(ActionEvent event) {
		Movie.date.setDate(Movie.date.getDate()-1);
		Movie.movielist.clear();
		Movie.showtimeurls.clear();
		Movie.showtimeclick.clear();
		Movie.showtimes.clear();
		Movie.urls.clear();
		displayDay.setText(Movie.dateFormat.format(Movie.date.getTime()));
		String link = "https://www.showcasecinemas.com/Umbraco/Api/MovieApi/PaginatedNowShowing?cinemaId="+Movie.ids.get(Theatre.curid)+"&liteVersion=false&expandAttributes=true&splitByAttributes=true&days=1&startDate="+Movie.dateFormat.format(Movie.date.getTime());
		Movie.url = link;
		Pane p = null;
		loader = new FXMLLoader(getClass().getResource("select movie.fxml"));
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
	
}
