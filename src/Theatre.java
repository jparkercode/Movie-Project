import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Theatre extends Controller2 {
	public static ArrayList<String> location = new ArrayList<>();
	public static ArrayList<String> showcasenames = new ArrayList<>();
	public static ArrayList<String> State = new ArrayList<>(); //Regal
	public static ArrayList<String> states = new ArrayList<>();
	public static ArrayList<String> statesfull= new ArrayList<>();
	public static ArrayList<String> city= new ArrayList<>();
	public static String type;
	public static String locationin;
	public static int curid;
	public static int state;
	
	/**
	 * Scrapes Theatres based on specific Theatre Type with
	 * hardcoded urls and there Respective Ids
	 * @param type Theatre Type 
	 * @return Theatre List
	 */
	public static ArrayList<String> scraperTheaters(String type) throws IOException{
		Movie.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Movie.date = new Date();
		String url = "";
		//Scrape AMC Locations

		
		if(type.equals("AMC")) {
	
		
			
		Document doc = Jsoup.connect("https://www.amctheatres.com/movie-theatres/states/"+statesfull.get(state).toLowerCase()).get();
		Elements places = doc.getElementsByClass("Link-text Headline--h3");
		System.out.println(statesfull.get(state));
		boolean firstplace = false;
		boolean lastplace = false;
		for(Element place:places) {

			if((!(place.ownText()==null)||!(place.ownText().length()==0))) {//&&firstplace&&!lastplace
			location.add(place.ownText());
			System.out.println(place.ownText());
			}
		}
			
		}
		//Scrape Regal Locations
		else if(type.equals("REGAL")) {
			url = "https://www.regmovies.com/static/en/us/theatre-list";
			Document doc = Jsoup.connect(url).get();
			Elements places = doc.getElementsByTag("a");
			String[] html = doc.html().split("\"");
			boolean firstplace = false;
			boolean lastplace = false;
			for(int x = 2;x<html.length;x++) {
				if(html[x-2].equals("externalCode")&&html[x-1].equals(" :   ")) {
					Movie.ids.add(Integer.valueOf(html[x]));
				}
				if(html[x-2].equals("name")&&html[x-1].equals(" :   ")) {
					location.add(html[x]);
				}
				if(html[x-2].equals("state")&&html[x-1].equals(" :   ")) {
					State.add(html[x]);
				}
				
			}
			System.out.println(State.size());
			System.out.println(Movie.ids.size());
			System.out.println(location.size());
		}
		//Scrape Showcase Locations
		else if(type.equals("SHOWCASE")) {
		//program-list-title
			url = "https://www.showcasecinemas.com/theaters";
			Document doc = Jsoup.connect(url).get();
			Elements places = doc.getElementsByClass("program-list-title");
			//System.out.println(doc.html());
			String[] htmls = doc.html().split("\"");
			System.out.println(htmls.length);
			boolean firstplace = false;
			boolean lastplace = false;
			for(int x=2;x<htmls.length;x++) {
		
				
				//Movie id
				if(htmls[x].matches(":\\d+,")) {
					String s = htmls[x].replaceAll(":", "");
					s = s.replaceAll(",", "");
					if(!(Movie.ids.contains(s))) {
					Movie.ids.add(Integer.valueOf(s));
					
					}
				}
				//Location
				if(htmls[x-1].matches(":")&&htmls[x-2].matches("CinemaName")) {
				
				location.add(htmls[x]);
				System.out.println(htmls[x]);}

				}
			System.out.println(location.size());
		}
	
		return null;
	}
	

}
