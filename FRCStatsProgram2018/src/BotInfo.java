import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class BotInfo extends JFrame {

	private Competition competition;
	private Robot robot;
	private JPanel contentPane;

	private JTable avgStatsTable;
	private String teamName;

	public BotInfo(Competition competition, String t) {
		this.competition = competition;
		robot = competition.getBot(t);

		init();

		System.out.println("good");
		setVisible(true);
	}

	private void init() {

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 960);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new LineBorder(Color.ORANGE));

		avgStatsTable = new JTable();

		avgStatsTable.addComponentListener(new ComponentListener() { // Resizes table to fit

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent e) {
				avgStatsTable.setRowHeight(avgStatsTable.getHeight() / avgStatsTable.getRowCount());
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});

		JLabel robotImage = new JLabel("No Image Available :(");
		robotImage.setPreferredSize(new Dimension(350, 275));

		robotImage.setHorizontalAlignment(SwingConstants.CENTER);

		avgStatsTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		avgStatsTable.setEnabled(false);
		avgStatsTable.setRowSelectionAllowed(false);
		avgStatsTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		avgStatsTable.setForeground(Color.BLACK);
		avgStatsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		avgStatsTable
				.setModel(new DefaultTableModel(
						new Object[][] { { "<html><b>Average Stats</b></html>", "<html><b>Values</b></html>" },
								{ "Auto Scale", robot.avg_a_scale }, { "Auto Switch", robot.avg_a_switch },
								{ "Teleop Scale", robot.avg_t_scale }, { "Teleop Switch", robot.avg_t_switch },
								{ "Exchange Zone", robot.avg_e_z }, { "Climb", robot.avg_c },
								{ "Portal", robot.portal }, { "Floor Pickup", robot.floor } },
						new String[] { "Average ", "Values" }));

		JLabel lblTeamName = new JLabel("<html>Team Name:" + teamName + "</html>");

		lblTeamName.setVerticalAlignment(SwingConstants.TOP);
		lblTeamName.setFont(new Font("Tahoma", Font.BOLD, 29));
		lblTeamName.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup().addContainerGap()
						.addComponent(avgStatsTable, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE).addGap(53)
						.addComponent(lblTeamName, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGap(46)));
		gl_topPanel.setVerticalGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup().addGap(26)
						.addComponent(lblTeamName, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE).addContainerGap())
				.addComponent(avgStatsTable, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 335,
						GroupLayout.PREFERRED_SIZE));
		topPanel.setLayout(gl_topPanel);

		if (robot.photo != null)
			robotImage.setIcon(new ImageIcon(robot.photo));

		JPanel matchPanel = new JPanel();

		matchPanel.setBorder(new LineBorder(Color.ORANGE, 1, true));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(robotImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE))
				.addComponent(matchPanel, GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 337,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(robotImage, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 337,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(matchPanel, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)));

			
		JTextArea textArea = new JTextArea();
		
		
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout gl_matchPanel = new GroupLayout(matchPanel);
		gl_matchPanel.setHorizontalGroup(gl_matchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_matchPanel.createSequentialGroup().addContainerGap()
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE).addContainerGap()));
		gl_matchPanel.setVerticalGroup(gl_matchPanel.createParallelGroup(Alignment.LEADING).addComponent(scroll,
				GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE));

		matchPanel.setLayout(gl_matchPanel);

		

		System.out.println("robot data size" + robot.data.size());

		teamName = robot.name;

		lblTeamName.setText("<html>" + teamName + "</html>");
		textArea.append(robot.returnData());

		/**
		 * for (int x = 0; x < robot.data.size(); x++) {
		 * 
		 * for (int y = 0; x < robot.data.get(x).length; y++) {
		 * 
		 * textArea.append("\n");
		 * 
		 * switch (y) { case 0: textArea.append("Match:" + x + 1); break;
		 * 
		 * case 1: textArea.append("Auto Scale: " + robot.data.get(x)[y]); break; case
		 * 2: textArea.append("Auto Switch: " + robot.data.get(x)[y]);
		 * 
		 * break; case 3: textArea.append("Teleop Scale: " + robot.data.get(x)[y]);
		 * 
		 * break; case 4: textArea.append("Teleop Switch: " + robot.data.get(x)[y]);
		 * 
		 * break; case 5: textArea.append("Exchange Zone: " + robot.data.get(x)[y]);
		 * 
		 * break; case 6: textArea.append("Climb: " + robot.data.get(x)[y]);
		 * 
		 * break; case 7: textArea.append("Portal: " + robot.data.get(x)[y]);
		 * 
		 * break; case 8: textArea.append("Floor: " + robot.data.get(x)[y]);
		 * 
		 * break; case 9: textArea.append("Notes: " + robot.data.get(x)[y]);
		 * 
		 * break;
		 * 
		 * }
		 * 
		 * } }
		 **/

		contentPane.setLayout(gl_contentPane);

	}

}
