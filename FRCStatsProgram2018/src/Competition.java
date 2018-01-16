import java.io.Serializable;
import java.util.ArrayList;

public class Competition implements Serializable {
	String name;
	String inputDir;
	String url;
	ArrayList<Match> matches;
	ArrayList<Robot> robots;
	
	public Competition(String n, String u, String d) {
		name = n;
		url = u;
		inputDir = d;
		robots = new ArrayList<Robot>();
		matches = new ArrayList<Match>();
		setup();
	}
	
	public void setup() {
		//TODO web scrape robots		
		//TODO web scrape match schedule
	}
	
	public boolean botExists(String t) {
		for (Robot bot: robots) if(bot.name.equals(t)) return true;
		return false;
	}
	
	public Robot getBot(String t) {
		for(Robot bot: robots) if(bot.name.equals(t)) return bot;
		return null;
	}
}
