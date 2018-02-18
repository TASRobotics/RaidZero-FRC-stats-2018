import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class BotInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private Robot robot;
	private JPanel contentPane;
	private JTable avgStatsTable;
	private String teamName;

	// constructor parameters: Competition competition, String team number
	public BotInfo(Competition competition, String t) {
		super(competition.getBot(t).name);
		robot = competition.getBot(t);
		teamName = robot.name;
		init();
		setVisible(true);
	}
	
	// initialize the frame
	private void init() {

		// setup frame
		setResizable(false);
		setBounds(0, 0, 1280, 960);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// initialize top panel
		JPanel topPanel = new JPanel();

		// table to display average statistics
		avgStatsTable = new JTable();
		avgStatsTable.setEnabled(false);
		avgStatsTable.addComponentListener(new ComponentListener() { // Resizes table to fit
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent e) {
				avgStatsTable.setRowHeight(avgStatsTable.getHeight() / avgStatsTable.getRowCount());
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});

		// spot for photo
		JLabel robotImage = new JLabel("No Image Available :("); // default
		robotImage.setPreferredSize(new Dimension(350, 275));
		robotImage.setHorizontalAlignment(SwingConstants.CENTER);

		// fill in table for averages
		avgStatsTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		avgStatsTable.setEnabled(false);
		avgStatsTable.setRowSelectionAllowed(false);
		avgStatsTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		avgStatsTable.setForeground(Color.BLACK);
		avgStatsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		avgStatsTable.setFont(new Font("Tahoma", Font.BOLD, 18));
		avgStatsTable.setModel(new DefaultTableModel(
				new Object[][] { { "<html><b>Average Stats</b></html>", "<html><b>Values</b></html>" },
						{ "Auto Scale", robot.avg_a_scale }, { "Auto Switch", robot.avg_a_switch }, 
						{"Auto Cross Line", robot.avg_a_cross},
						{ "Teleop Scale", robot.avg_t_scale }, { "Max Teleop Scale", robot.max_t_scale },
						{ "Teleop Switch", robot.avg_t_switch },{ "Max Teleop Switch", robot.max_t_switch },						
						{ "Exchange Zone", robot.avg_e_z }, { "Max Exchange Zone", robot.max_e_z }, 
						{ "Climb", robot.avg_c }, { "Floor Pickup", robot.floor }},
				new String[] { "Average ", "Values" }));

		//team name
		JLabel lblTeamName = new JLabel("<html>Team Name:" + teamName + "</html>");
		lblTeamName.setVerticalAlignment(SwingConstants.TOP);
		lblTeamName.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTeamName.setHorizontalAlignment(SwingConstants.CENTER);

		// group layout for averages table
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup().addContainerGap().addComponent(avgStatsTable,
						GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)));
		gl_topPanel.setVerticalGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
						.addComponent(avgStatsTable, GroupLayout.PREFERRED_SIZE, 391, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(25, Short.MAX_VALUE)));
		topPanel.setLayout(gl_topPanel);

		// fill in photo if exists
		if (robot.photo != null) robotImage.setIcon(new ImageIcon(robot.photo));

		// bottom panel
		JPanel matchPanel = new JPanel();
		
		// group layout image and team name
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(32)
								.addComponent(lblTeamName, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE).addGap(28))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(robotImage, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(matchPanel, GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(17).addComponent(topPanel,
								GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(lblTeamName))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(90).addComponent(robotImage,
								GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
				.addGap(12).addComponent(matchPanel, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)));

		// add text area for match info and scroll pane
		JEditorPane text_area = new JEditorPane("text/html", "");
		text_area.setFont(new Font("Tahoma", Font.PLAIN, 28));
		text_area.setText(convertToFormat(robot.returnData()));
		text_area.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		text_area.setEditable(false);
		JScrollPane scroll = new JScrollPane(text_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// group layout for match panel
		GroupLayout gl_matchPanel = new GroupLayout(matchPanel);
		gl_matchPanel.setHorizontalGroup(gl_matchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_matchPanel.createSequentialGroup().addContainerGap()
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE).addContainerGap()));
		gl_matchPanel.setVerticalGroup(gl_matchPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_matchPanel.createSequentialGroup().addContainerGap()
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE).addContainerGap()));
		matchPanel.setLayout(gl_matchPanel);

		// set team name
		lblTeamName.setText("<html>" + teamName + "</html>");
		
		contentPane.setLayout(gl_contentPane);
	}
	
	// method to convert output string into desired format
	public String convertToFormat(String s) {
		String output = "";	
		for (int i = 0; i < s.length(); i++) {
			try {
				if (s.substring(i, i+7).equals("Match#:")) {
					output += s.substring(0, i);
					output += "<br/><br/><b>" + s.substring(i, i+7) + "</b>";
					s = s.substring(i+7);
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