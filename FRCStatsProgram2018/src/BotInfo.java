import javax.swing.JFrame;

public class BotInfo extends JFrame {
	Competition competition;
	Robot robot;
	
	
	public BotInfo(Competition c, String t) {
		competition = c;
		robot = competition.getBot(t);
	}
}
