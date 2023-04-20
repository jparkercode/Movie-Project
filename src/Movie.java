import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.scene.web.WebEngine;

import java.util.regex.*;
public class Movie {
public static String url;
public String Name;
public String showtime;
public Double rating;
public String photo;
public static Date date;
public static SimpleDateFormat dateFormat;

public static int movienum;
public static ArrayList<String> movielist = new ArrayList<>(); //Movie Names
public static ArrayList<String> movieratings = new ArrayList<>(); //Ratings
public static ArrayList<String> showtimeurls = new ArrayList<>(); //Showtimes for specific movie
public static ArrayList<String> showtimes = new ArrayList<>(); //Movie Times
public static ArrayList<String> showtimeclick = new ArrayList<>(); //Buy Tickets
public static ArrayList<String> urls = new ArrayList<>(); //Image urls
public static ArrayList<Integer> ids = new ArrayList<>(); //CINEMARK SPECIFIC
public static ArrayList<Integer> idsmovie = new ArrayList<>();

public static String htmlgrab(String url) throws IOException {
	String html;
	URL u = new URL(url); //replace https://www.google.com/ with your url
	InputStream in = u.openStream();
	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	StringBuilder result = new StringBuilder();
	String line;
	while((line = reader.readLine()) != null) {
	    result.append(line);
	}
	html = result.toString();
	return html;
}
public static Document jhtmlGrab(String url) throws IOException{
	Document doc = Jsoup.connect(url).get();
	
	
	return doc;
	
}

/**
 * Scrapes Showtimes based on specific Theatre Type with built url 
 * using factors such as Movie Id, Theatre Id and current Date.
 * @param url Movie Specific url 
 * @return Showtimes List
 */
public static ArrayList<String> scraperShowtimes(String url) throws IOException{

	if(Theatre.type.equals("AMC")) {
	System.out.println(url);
	Document doc = Jsoup.connect(url).get();
	Elements Showtimes = doc.getElementsByClass("Theatre-Wrapper-First No-Showtimes-First");//doc.getElementsByClass("Btn Btn--default");
	


	for(Element showtime:Showtimes) {

		Elements Showtimez = showtime.getElementsByClass("Btn Btn--default");
		for(Element showtim:Showtimez) {
			if(showtime.outerHtml().contains(url.split("/")[8])) {
			
				showtimes.add(showtim.wholeText());
				showtimeclick.add("https://www.amctheatres.com"+showtim.outerHtml().split("\"")[9]);
			
		}
	}
	}
}
else if(Theatre.type.equals("CINEMARK")) {
	Document doc = Jsoup.connect(url).get();
	Elements Showtimes = doc.getElementsByClass("showtime");
	
	for(Element showtime:Showtimes) {
		if(showtime.outerHtml().contains(String.valueOf(movienum))) {//&&((showtime.wholeText().contains("\\d+"))
		System.out.println(showtime.wholeText());
		showtimes.add(showtime.wholeText());
		}
	}

}
else if (Theatre.type.equals("SHOWCASE")) {
	
	System.out.println(url);
	String html = htmlgrab(url);
	String[] htmlarr = html.split("\"");
	for(int x=2;x<htmlarr.length;x++) {
		if(htmlarr[x-2].equals("StartTime")&&htmlarr[x-1].equals(":")) {
			showtimes.add(htmlarr[x]);
		}
	}
	
	

	
	
}
else if (Theatre.type.equals("REGAL")) {
	System.out.println(url);
	String doc = Movie.htmlgrab(url); //https://www.regmovies.com/us/data-api-service/v1/quickbook/10110/cinema-events/with-film/ho00008834/at-date/2019-04-18?attr=&ids=0690,0108,1462,0103,0682&lang=en_US
	String[] htmlarr = doc.split("\"");
	for(int x=2;x<htmlarr.length;x++) {
		if(htmlarr[x-2].equals("eventDateTime")&&htmlarr[x-1].equals(":")) {
			String time = htmlarr[x];
			time = time.split("T")[1];
			int army = Integer.valueOf(time.split(":")[0]);
			if(army>12) {
				army = army -12;
			}
			time = String.valueOf(army)+":"+time.split(":")[1]+"pm";
			showtimes.add(time);
		}
	}
	System.out.println(doc);
	
}
	
	return null;
}
/**
 * Scrapes Movie Names based on specific Theatre Type using Built urls
 * based on factors such as Theatre id and current date.
 * 
 * @return Movie List
 */
public static ArrayList<String> scraperMovies() throws IOException{
	if(Theatre.type.equals("AMC")) {
		
	Document doc = Jsoup.connect(url).get();
	System.out.println(url);
	Elements imageUrls = doc.getElementsByClass("Picture--rectangle");//doc.select("src$=.jpg");
	Elements showtimeUrls = doc.getElementsByClass("Btn Btn--primary");
	Elements movies = doc.getElementsByTag("h3");

	int z = 0;
	for(Element showtimeurl:showtimeUrls) {
		String sorig = showtimeurl.outerHtml();
		String s = sorig.split("\"")[3];
		
		
		s = "https://www.amctheatres.com"+s+"/"+sorig.split("/")[2]+"/"+dateFormat.format(date.getTime())+"/"+url.split("/")[5]+"/all";
		System.out.println(s);
		showtimeurls.add(s);
		z++;
	}
	for(Element movie:movies) {
		if((!(movie.ownText()==null)||!(movie.ownText().length()==0))&&!(movie.ownText().equals("Not finding what you're looking for?"))&&!(movie.ownText().equals("Features"))&&!(movie.ownText().equals("Amenities and Accessibility"))&&!(movie.ownText().equals("Other Policies"))&&!(movie.ownText().equals("Our Company"))&&!(movie.ownText().equals("Movies"))&&!(movie.ownText().equals("Programming"))&&!(movie.ownText().equals("More"))){//
		movielist.add(movie.ownText());
		}

	}
	int x =0;
	for(Element image:imageUrls) {

		if(x<movies.size()-8) {
		String s = image.html();//.replaceAll("<img src=\"", "");
		s = s.split("\"")[1];
		
		urls.add(s);
		x++;}
	}

	System.out.println(url);
	return movielist;
	}
	else if(Theatre.type.equals("REGAL")) {
	System.out.println(url);	//https://www.regmovies.com/us/data-api-service/v1/10110/trailers/byCinemaId/1053
		URL u = new URL(url); 
		InputStream in = u.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
		    result.append(line);
		}
		String htmls = result.toString();
		String[] html = htmls.split("\"");
		for(int x=2;x<html.length;x++) {
			if(html[x-2].equals("filmName")&&html[x-1].equals(":")) {
				if(!(movielist.contains(html[x]))){
				movielist.add(html[x]);
				System.out.println(html[x]);
				}
			}
			if(html[x-2].equals("filmId")&&html[x-1].equals(":")) {
				showtimeurls.add("https://www.regmovies.com/us/data-api-service/v1/quickbook/10110/cinema-events/with-film/"+html[x]+"/at-date/"+Movie.dateFormat.format(Movie.date.getTime())+"?attr=&ids="+Movie.ids.get(Theatre.curid)+"&lang=en_US");
			}
			if(html[x-2].equals("filmLink")&&html[x-1].equals(":")){
				
				
				
				Document doc = Jsoup.connect(html[x]).get();
				
				String htmlsss = doc.html();
				String[] htmlss = htmlsss.split("\"");
				//Image
				for(int y=0;y<htmlss.length;y++) {
					if(htmlss[y].contains("https://regalcdn.azureedge.net")&&(!(htmlss[y].contains("https://www.regmovies.com/")))){
						urls.add(htmlss[y]);
						System.out.println(htmlss[y]);
					}
				}
				
			}
		}

	}
	else if(Theatre.type.equals("CINEMARK")) {
		
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();
		Elements movies = doc.getElementsByClass("movieLink");
		Elements imgUrls = doc.getElementsByClass("movieLink");
		Elements movieids = doc.getElementsByClass("movielink");
		for(Element movieid:movieids) {
			if(movieid.outerHtml().contains("id=\"")) {
			ids.add(Integer.valueOf(movieid.outerHtml().split("\"")[5]));}
		}
		for(Integer i:ids) {
			System.out.println(i);
		}
		int y= 0;
		for(Element movie:movies) {
		
			if((!(movie.ownText()==null)||!(movie.ownText().length()==0))&&(!(movie.ownText().equals("Details"))&&((movie.ownText().matches("\\w+[\\w*|\\W*]*"))))) {
				if(y>=2) {
				movielist.add(movie.ownText());
				}
				y++;
			}
		
		}
		int x = 0;
		for(Element imgUrl:imgUrls) {
			if(Math.pow(-1, x)==1) {
			if(imgUrl.html().contains("\"")) {
			if(imgUrl.html().split("\"")[1].contains(".jpg")){
				urls.add("https://www.cinemark.com"+imgUrl.html().split("\"")[1]);
				System.out.println(imgUrl.html().split("\"")[1]);
			}
			}
			
			}
			x++;
		}
	}
	else if(Theatre.type.equals("SHOWCASE")) {
		//https://www.showcasecinemas.com/Umbraco/Api/MovieApi/PaginatedNowShowing?cinemaId=7934&liteVersion=false&expandAttributes=true&splitByAttributes=true&days=1&startDate=2019-04-17
		System.out.println(url);
		URL u = new URL(url); 
		InputStream in = u.openStream(); //Sourced Code
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
		    result.append(line);
		}
		String doc = result.toString();						//maxBodySize(0).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7").timeout(6000).get();
		String[] html = doc.split("\"");
	//Movie Name
		int y=0;
		for(int x=2;x<html.length;x++) {
			if(html[x-1].equals("FilmId")) {
				String movieid = html[x].replaceAll(":", "");
				movieid = movieid.replaceAll(",", "");
				System.out.println(movieid);
				idsmovie.add(Integer.valueOf(movieid));
				
			}
			if(html[x-2].equals("Title")&&html[x-1].equals(":")) {
				y++;
				movielist.add(html[x]);
				System.out.println(html[x]);
				//Showtime url
				String name = html[x].replaceAll("%20", "-");
				name = name.replaceAll(" ", "-");
				String showtimeurl = "https://movieapi.showcasecinemas.com/Movies/108/"+ids.get(Theatre.curid)+"/"+idsmovie.get(y-1)+"/"+Movie.dateFormat.format(Movie.date.getTime())+"?expandCinemas=true&splitByAttributes=true";
				//https://movieapi.showcasecinemas.com/Movies/108/7934/1970/2019-04-18?expandCinemas=true&splitByAttributes=true
				showtimeurls.add(showtimeurl);
				//Grabs images from bing image search "{MovieName} new movie poster"
			String connect = "https://www.bing.com/images/search?q="+html[x]+"+new+movie+poster&qs=n&form=QBILPG&sp=-1&pq="+html[x]+"+new+movie+poster&sc=6-14&sk=&cvid=B3B9C9BF43AD4245939953649312BB53";
				Document doc2 = Jsoup.connect(connect).get();
				Elements imgs = doc2.getElementsByClass("mimg");
				boolean found = false;
				for(Element img:imgs) {
						String[] html2 = img.outerHtml().split("\"");
						found = false;
						for(String html22:html2) {
							if(html22.contains("https")) {
						
						found = true;
						urls.add(html22);
						}
						}
						if(found) {
						break;}
						
					
				}
				
			
			}
		
			
		}

		
	}
	return movielist;
}

}
