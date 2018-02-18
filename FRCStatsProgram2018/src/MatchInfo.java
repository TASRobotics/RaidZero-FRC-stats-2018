import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.*;

public class MatchInfo extends JFrame {
	
	Competition competition;
	int match_number;

	// constructor
	public MatchInfo(Competition c, int m) {
		super("Match " + m);
		competition = c;
		match_number = m;
		setBackground(Color.WHITE);
		setSize(800, 500);
		setVisible(true);
		paint();
	}

	public void paint() {
		try {
			draw();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// draw the info on the screen
	public void draw() {
		setSize(getWidth(), Toolkit.getDefaultToolkit().getScreenSize().height);
		setLocationRelativeTo(null);

		Match match = competition.getMatch(match_number - 1);
		String final_string = convertToFormat(match.returnData());

		JEditorPane text_area = new JEditorPane("text/html", "");
		text_area.setFont(new Font("Arial", Font.PLAIN, 20));
		text_area.setText(final_string);
		text_area.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		text_area.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(text_area);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setContentPane(contentPane);
	}
	
	// method to convert output string into desired format
	public String convertToFormat(String s) {
		String output = "";	
		for (int i = 0; i < s.length(); i++) {
			try {
				if (s.substring(i, i+3).equals("RED")) {
					output += s.substring(0, i);
					output += "<br/><br/><font color=\"red\"><b>" + s.substring(i, i+3) + "</b></font>";
					s = s.substring(i+3);
					i = 0;
				}else if (s.substring(i, i+4).equals("BLUE")) {
					output += s.substring(0, i);
					output += "<br/><br/><font color=\"blue\"><b>" + s.substring(i, i+4) + "</b></font>";
					s = s.substring(i+4);
					i = 0;
				}else if (s.substring(i, i+6).equals("Team#:")) {
					output += s.substring(0, i);
					output += "<br/><b>" + s.substring(i, i+6) + "</b>";
					s = s.substring(i+6);
					i = 0;
				}else if (s.substring(i, i+8).equals("AScale#:") 
						|| s.substring(i, i+9).equals("ASwitch#:")
						|| s.substring(i, i+7).equals("ACross:")
						|| s.substring(i, i+8).equals("TScale#:")
						|| s.substring(i, i+9).equals("TSwitch#:")) {
					output += s.substring(0, i);
					output += "<br/><b>" + s.substring(i, i+1) + "</b>";
					s = s.substring(i+1);
					i = 0;
				}else if (s.substring(i, i+6).equals("Climb:")
						|| s.substring(i, i+6).equals("Floor:")
						|| s.substring(i, i+6).equals("Notes:")
						|| s.substring(i, i+14).equals("Exchange Zone:")) {
					output += s.substring(0, i);
					output += "<br/>";
					s = s.substring(i);
					i = 0;
				}
			}catch(Exception e) {}
		}
		output += s;
		return output;
	}
}
