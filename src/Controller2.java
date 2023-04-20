import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * Choose a Specific Theatre within theatre type
 */
public class Controller2 extends JavaFx implements Initializable {
	public static String src;
	@FXML
	Pane pane;
	@FXML
	ImageView img;
	boolean prescreen = false;
	public void start() {
		//Regal Search PreScreen
		if(Theatre.type.equals("REGAL")||Theatre.type.equals("AMC")) {
		    
			if(prescreen) {
				prescreen = false;
			}
			else {
			prescreen = true;
			}
		}
	    File file = new File("imgs/"+src+".jpg");
	    Image image = new Image(file.toURI().toString());
		img.setImage(image);
		if(!(prescreen)){
			
		//Buttons for scraped locations
		Button[] lab = new Button[Theatre.location.size()];
		int x = 0;
		int valueX = 0;
		int offsetX = 0;
		int valueY = 0;
		
		int greatest = 0;
		//Get offset for buttons
		for(String a:Theatre.location) {
			offsetX = a.length()*7;
			if(offsetX>greatest) {
				greatest=offsetX;
			}
		}
		offsetX = greatest;
		//Format buttons
		for(String a:Theatre.location) {
			
			lab[x] = new Button(a);
			lab[x].setLayoutX(valueX);
			lab[x].setLayoutY(valueY);
			pane.getChildren().add(lab[x]);
			x++;
			valueY += 50;
			
			if(valueY>750) {
				valueX += offsetX;
				valueY = 0;
			}
		}
		 int y = 0;
		 //Button on click events to advance Scene to Movies with built urls
		for(Button a:lab) {
			final int xx = y;
			a.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
						
						String s = a.getText();
						s= s.replaceAll(" ", "-");
						if(Theatre.type.equals("AMC")) {
						String link = "https://www.amctheatres.com/movie-theatres/"+Theatre.city.get(Theatre.state).toLowerCase()+"/"+s.replace("?null", "");
						//link = link + "/showtimes/all/"+Movie.dateFormat.format(Movie.date.getTime())+"/"+s+"/all";
						Movie.url = link; 
						Theatre.locationin = Theatre.location.get(xx);
						}
						else if(Theatre.type.equals("SHOWCASE")) {
						
							String link = "https://www.showcasecinemas.com/Umbraco/Api/MovieApi/PaginatedNowShowing?cinemaId="+Movie.ids.get(xx)+"&liteVersion=false&expandAttributes=true&splitByAttributes=true&days=1&startDate="+Movie.dateFormat.format(Movie.date.getTime());
							Movie.url = link; //Passed to Movie Scraper
							Theatre.locationin = Theatre.location.get(xx);
							Theatre.curid = xx;
						}
						else if(Theatre.type.equals("REGAL")) {
					
							Theatre.curid = xx;
							Theatre.locationin = Theatre.location.get(xx);
							String link = "https://www.regmovies.com/us/data-api-service/v1/10110/trailers/byCinemaId/"+Movie.ids.get(xx);
							Movie.url = link;
						}
						
						JavaFx a = new JavaFx();
						loader = new FXMLLoader(getClass().getResource("select movie.fxml"));
						Pane p = null;
						try {
							p = loader.load();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Theatre Unavailable");
							alert.setHeaderText("Unable to Locate theatre api");
							alert.setContentText("Try another date");

							alert.showAndWait();
							e.printStackTrace();
						}
						
						//Parent tableview = FXMLLoader.load(getClass().getResource("new.fxml"));
						//Scene tableviewScene = new Scene(tableview);
						
						Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
						window.setScene(new Scene(p));
						window.show();
						
			
				
				}
			});

			y++;
		}
	}
		//Regal and AMC Search Prescreen
		else {
			TextArea t = new TextArea();
			Button b = new Button("Submit");
			Label l = new Label("Enter State Initial:");
			l.setTextFill(Color.WHITE);
			l.setFont(Font.font("Cambria", 24));
			l.setStyle("-fx-border-color: white;");
			l.setLayoutX(425);
			l.setLayoutY(550);
			t.setLayoutX(620);
			t.setLayoutY(550);
			t.setMaxHeight(10);
			t.setMaxWidth(30);
			b.setLayoutX(675);
			b.setLayoutY(550);
			pane.getChildren().addAll(l,b,t);
			
			b.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if(Theatre.type.equals("AMC")) {
						Document doc1 = null;
						try {
							doc1 = Jsoup.connect("https://www.memory-improvement-tips.com/list-of-states-capitals.html").get();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							
							e1.printStackTrace();
						}
						Elements stateslocal = doc1.getElementsByTag("p");
						for(Element state:stateslocal) {
							if(state.wholeText().contains("(")) {
								String stateedit = state.wholeText().split(" ")[1];
								stateedit = stateedit.replace("(", "");
								stateedit = stateedit.replace(")", "");
							Theatre.states.add(stateedit);
							Theatre.statesfull.add(state.wholeText().split(" ")[0]);
							Theatre.city.add(state.wholeText().split(" ")[3]);
							
							}
						}
						Theatre.state=Theatre.states.indexOf(t.getText().toUpperCase());
						int x = Theatre.state;
						try {
							Theatre.scraperTheaters("AMC");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
					ArrayList<String> newarr = new ArrayList<>();
					ArrayList<Integer> newarrid = new ArrayList<>();
					ArrayList<Integer> index = new ArrayList<>();
					int x=0;
					for(String s:Theatre.State) {
						if(s.matches(t.getText().toUpperCase())) {
							index.add(x);
						}
						
						x++;
					}
					for(Integer i:index) {
						newarr.add(Theatre.location.get(i));
						newarrid.add(Movie.ids.get(i));
					}
					Theatre.location = newarr;
					Movie.ids = newarrid;
					}
					pane.getChildren().removeAll(l,b,t);
					start();
				
				}});
			
		}
	}
	//Go Back to previous screen and clear theatre data
	public void back(ActionEvent event) throws IOException{
		Theatre.location.clear();
		Theatre.State.clear();
		Movie.ids.clear();
		
		Pane p = null;
		
		loader = new FXMLLoader(getClass().getResource("moviefxml.fxml"));
		p = loader.load();
	
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(new Scene(p));
		window.show();
	
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		start();
	}
}

