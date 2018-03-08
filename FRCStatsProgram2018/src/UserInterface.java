import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserInterface extends JFrame implements MouseListener {

	Competition competition;
	private int mouseX, mouseY, mouseButton;
	private boolean isFirstRun;
	private BufferedImage raidZero;

	// constructor
	public UserInterface(Competition comp) {
		super("Raid Zero FRC 2018");

		// set up background and mouse listener
		try {
			raidZero = ImageIO.read(new File("meme.jpg"));
		} catch (Exception e) {
		}
		setBackground(Color.WHITE);
		competition = comp;
		mouseX = mouseY = 0;
		mouseButton = 0;
		isFirstRun = true;
		setSize(1600, 1000);
		setBackground(Color.BLACK);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);

		// set icon
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(getClass().getClassLoader().getResource("Raid Zero.png"));
		} catch (Exception e) {
		}
		if (icon != null)
			setIconImage(icon);
	}

	// method to detect mouse clicks
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseButton = e.getButton();
		repaint();
	}

	public void paint(Graphics window) {
		if (isFirstRun) {
			// set up user interface
			window.setColor(new Color(0, 0, 70)); // blue
			window.fillRect(0, 0, 2000, 1200);
			window.drawImage(raidZero, 0, 0, 1600, 1000, this);
			window.setColor(new Color(50, 50, 50)); // black
			for (int i = 100; i < 800; i += 200)
				window.fillRect(i, 750, 180, 180);
			window.setColor(new Color(220, 220, 220)); // white
			window.setFont(new Font("Arial", Font.BOLD, 30));
			window.drawString("Data", 110, 800);
			window.drawString("Table", 110, 850);
			window.drawString("Search", 310, 800);
			window.drawString("Robot", 310, 850);
			window.drawString("Search", 510, 800);
			window.drawString("Match", 510, 850);
			window.drawString("Settings", 710, 800);
			window.drawString(competition.name, 100, 100);
			window.setColor(new Color(30, 30, 30));
			window.drawString("created by garigarikun", 1200, 980);
			isFirstRun = false;
		}
		draw(window);
		setLocationRelativeTo(null); // center frame
	}

	public void draw(Graphics window) {
		if (mouseButton == MouseEvent.BUTTON1) { // left mouse button pressed
			if (mouseX > 100 && mouseX < 280 && mouseY > 750 && mouseY < 930) {
				// data table
				new DataTable(competition);
			} else if (mouseX > 300 && mouseX < 480 && mouseY > 750 && mouseY < 930) {
				// search robot
				String teamNumber = JOptionPane.showInputDialog("Enter team #: ");
				if (competition.botExists(teamNumber)) {
					try {
						new BotInfo(competition, teamNumber);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Cannot create bot info window: " + e, "Error",
								JOptionPane.INFORMATION_MESSAGE);
					}
					new DataChart(competition, teamNumber);

				} else {
					JOptionPane.showMessageDialog(null, "Robot does not exist.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (mouseX > 500 && mouseX < 680 && mouseY > 750 && mouseY < 930) {
				// search match
				try {
					int matchNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter match #: "));
					if (matchNumber <= competition.matches.size()) {
						new MatchInfo(competition, matchNumber);
					} else {
						JOptionPane.showMessageDialog(null, "Cannot create match info window.", "Error",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Match does not exist: " + e, "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (mouseX > 700 && mouseX < 880 && mouseY > 750 && mouseY < 930) {
				// settings
				JFrame settings_frame = new JFrame("Settings");
				settings_frame.setLayout(new BorderLayout());
				settings_frame.setSize(800, 100);
				settings_frame.setVisible(true);
				settings_frame.addMouseListener(this);
				settings_frame.setLocationRelativeTo(null);
				settings_frame.setResizable(false);
				JPanel settings_panel = new JPanel();
				settings_panel.add(new JLabel("Input Directory: "));
				JLabel directory = new JLabel(competition.inputDir);
				settings_panel.add(directory);

				// change input directory
				JButton button_change = new JButton("Change");
				button_change.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// file chooser
						JFileChooser chooser = new JFileChooser();
						chooser.setCurrentDirectory(new java.io.File(competition.inputDir));
						chooser.setDialogTitle("Select Input Directory");
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						chooser.setAcceptAllFileFilterUsed(false);
						int result = chooser.showOpenDialog(settings_panel);
						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = chooser.getSelectedFile();
							directory.setText(selectedFile.getAbsolutePath());
							competition.inputDir = directory.getText();
						}
					}
				});
				settings_panel.add(button_change);

				// add panel to frame
				settings_frame.add(settings_panel);
				settings_panel.setVisible(true);
			}
		}
	}

	// unused implemented methods
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
