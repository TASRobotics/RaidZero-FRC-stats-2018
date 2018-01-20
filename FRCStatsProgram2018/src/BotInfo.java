import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BotInfo extends JFrame {
	Competition competition;
	Robot robot;
	
	
	public BotInfo(Competition c, String t) {
		competition = c;
		robot = competition.getBot(t);
		//TOTO waiting for spencer's code
		JOptionPane.showMessageDialog(null, "Waiting for Spencer's Code." , "Error", JOptionPane.INFORMATION_MESSAGE);
	}
}
