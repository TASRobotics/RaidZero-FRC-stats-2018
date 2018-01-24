import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserInterface extends JFrame implements MouseListener {
	private int mouseX, mouseY;
	private int mouseButton;
	private boolean isFirstRun;
	Competition competition;
	private BufferedImage raidZero;

	public UserInterface(Competition comp) {
		super("Raid Zero FRC 2018");
		try {
			raidZero = ImageIO.read(new File("meme.jpg"));
		} catch (Exception e) {}

		setBackground(Color.WHITE);
		competition = comp;
		mouseX = mouseY = 0;
		mouseButton = 0;
		isFirstRun = true;
		setSize(1600, 1000);
		setBackground(Color.BLACK);
		setVisible(true);
		addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseButton = e.getButton();
		repaint();
	}
	

	public void paint(Graphics window) {
		if (isFirstRun) {
			// set up background
			window.setColor(new Color(34, 34, 34));
			window.fillRect(0, 0, 2000, 1200);
			window.drawImage(raidZero, 0, 0, 1600, 1000, this);
			window.setColor(new Color(50, 50, 50));
			for (int i = 100; i < 600; i += 200) window.fillRect(i, 750, 180, 180);
			window.setColor(new Color(230, 230, 230));
			window.setFont(new Font("Arial", Font.BOLD, 30));
			window.drawString("Data", 110, 800);
			window.drawString("Table", 110, 850);
			window.drawString("Search", 310, 800);
			window.drawString("Robot", 310, 850);
			window.drawString("Search", 510, 800);
			window.drawString("Match", 510, 850);
			window.drawString(competition.name, 100, 100);
			window.setColor(new Color(30, 30, 30));
			window.drawString("created by garigarikun", 1200, 980);

			isFirstRun = false;
		}
		draw(window);
		setLocationRelativeTo(null);
	}

	public void draw(Graphics window) {
		if (mouseButton == MouseEvent.BUTTON1) { // left mouse button pressed
			if (mouseX > 100 && mouseX < 280 && mouseY > 750 && mouseY < 930) {// data table
				new DataTable(competition);
			} else if (mouseX > 300 && mouseX < 480 && mouseY > 750 && mouseY < 930) {// search team #
				String teamNumber = JOptionPane.showInputDialog("Enter team #: ");
				if (competition.botExists(teamNumber)) {
					try {
						new BotInfo(competition, teamNumber);
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(null, "Error.", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Robot does not exist.", "Error",JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (mouseX > 500 && mouseX < 680 && mouseY > 750 && mouseY < 930) {// search match
				try {
					int matchNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter match #: "));
					if (matchNumber <= competition.matches.size())
						new MatchInfo(competition, matchNumber);
					else
						JOptionPane.showMessageDialog(null, "Match does not exist.", "Error", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Match does not exist.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
