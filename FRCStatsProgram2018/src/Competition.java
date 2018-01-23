import java.io.Serializable;
import java.util.ArrayList;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
		scrape();
	}
	
	//if robot with name exists, return true
	public boolean botExists(String t) {
		for (Robot bot: robots) {
			System.out.println(bot.name.substring(0, bot.name.indexOf(" ")));
			if(bot.name.substring(0, bot.name.indexOf(" ")).equals(t)) 
				return true;
		}
		return false;
	}
	
	//if robot with name exists, return the Robot
	public Robot getBot(String t) {
		for(Robot bot: robots) 
			if(bot.name.substring(0, bot.name.indexOf(" ")).equals(t)) 
				return bot;
		return null;
	}

	//if match exists, return the match
	public Match getMatch(int match_number) {
		try {
			return matches.get(match_number);
		}
		catch (Exception Andrew) {
			System.out.print("Match does not exist." + match_number);
			return new Match(1, new int[6]);
		}
	}
	
	//method for scraping from blue alliance
	public void scrape() {
		//event page
    	final JDialog waitDialog = new JDialog(new javax.swing.JFrame(), "Loading.");    
        waitDialog.setVisible(true);
    	Document mainDocument = null;
    	try {
			mainDocument = Jsoup.connect(url).get();
			
			//scrape teams
			Document teams_page = null;
			try {
				teams_page = Jsoup.connect(url + "#teams").get();
            	Elements teams = teams_page.select("div.team-name");            		
            	for(int i = 0; i < teams.size(); i++) robots.add(new Robot(teams.get(i).text()));          		
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Error in scraping teams.");
			}
			
			//find number of qualification matches	
			int qualsSize = 0;
			for(int i = 150; i >= 0; i--){
				Elements quals = mainDocument.select("a:contains(Quals " + i + ")");
				if(quals.size() > 0){
					qualsSize = i; break;
				}
			}
			System.out.println("quals size: " + qualsSize);
			
			//scrape matches
			Document results_page = null;
			try {
				results_page = Jsoup.connect(url + "#results").get();
            	Elements match_bots = results_page.select("td[colspan^=2] > a[href^=/team/]");
            	System.out.println("match bots size: " + match_bots.size());
            	for(int i = 1; i < qualsSize+1; i++) {
            		int[] bots = new int[6];
            		for(int j = 0; j < 6; j++) bots[j] = Integer.parseInt(match_bots.remove(0).text());            		
            		matches.add(new Match(i, bots));
            	}            		            		
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Error in scraping matches.");
			}	
			
    	}catch(IOException e){
    		JOptionPane.showMessageDialog(null, "Error in fetching main page.");
		} 
    	
    	waitDialog.dispose();
    	JOptionPane.showMessageDialog(null, "Synced."); 	
	}
}