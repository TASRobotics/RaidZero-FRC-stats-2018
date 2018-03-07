import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.commons.lang3.StringUtils;

//import com.sun.org.apache.bcel.internal.generic.NEW;

public class BotInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private Robot robot;
	private JPanel contentPane;
	private JTable avgStatsTable;
	private String teamName;
	private Competition competition;
	int max;

	// constructor parameters: Competition competition, String team number
	public BotInfo(Competition comp, String t) {
		super(comp.getBot(t).name);
		robot = comp.getBot(t);
		teamName = robot.name;
		max = 24;
		competition = comp;
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

		// spot for photo
		JLabel robotImage = new JLabel(""); // default
		robotImage.setPreferredSize(new Dimension(1500, 1500));
		robotImage.setHorizontalAlignment(SwingConstants.CENTER);

		// fill in table for averages
		avgStatsTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		avgStatsTable.setEnabled(false);
		avgStatsTable.setRowSelectionAllowed(false);
		avgStatsTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		avgStatsTable.setForeground(Color.BLACK);
		avgStatsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		avgStatsTable.setFont(new Font("Tahoma", Font.BOLD, 18));
		avgStatsTable.setModel(new DefaultTableModel(new Object[][] {
				{ "<html><b>Average Stats</b></html>", "<html><b>Values</b></html>" },
				{ "Auto Scale", robot.avg_a_scale }, { "Auto Switch", robot.avg_a_switch },
				{ "Auto Cross Line", robot.avg_a_cross }, { "Teleop Scale", robot.avg_t_scale },
				{ "Max Teleop Scale", robot.max_t_scale }, { "Teleop Switch R", robot.avg_t_switch_r }, {"Teleop Switch B", robot.avg_t_switch_b},
				{ "Max Teleop Switch", robot.max_t_switch }, { "Exchange Zone", robot.avg_e_z },
				{ "Max Exchange Zone", robot.max_e_z }, { "Climb", robot.avg_c }, { "Floor Pickup", robot.floor } },
				new String[] { "Average ", "Values" }));

		// team name
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
		if(robot.photo != null){
	    	BufferedImage img = robot.photo;
	    	Image newImg = img;
	    	if(img.getHeight()>300){
	    		int newWid = img.getWidth()*300/img.getHeight();
				newImg =  img.getScaledInstance(newWid, 300, Image.SCALE_SMOOTH);;
			}
			if(img.getWidth()>300){
				int newHt = img.getHeight()*300/img.getWidth();
				newImg = img.getScaledInstance(300, newHt, Image.SCALE_SMOOTH);
			}    	   
	    	robotImage.setIcon(new ImageIcon(newImg)); 
	    }

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

		// columns for the table
		String[] columnNames = { "", "", "" };

		int index = 0;
		String[] arrData = splitMatchString(convertToFormat(robot.returnData()));

		// put in values for table
		Object[][] data = new Object[max / 3][3];
		for (int i = 0; i < max / 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.println(arrData[index] + "Index: " + index);
				data[i][j] = "<html>"+arrData[index] + "</html>";
				index++;
			}
		}

		// set up the table
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);

		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(315);
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(400);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		table.getColumnModel().getColumn(2).setPreferredWidth(400);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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

	// method to split the string into multiple "match" substring
	public String[] splitMatchString(String s1) {

		String[] output = new String[max];
		int matches = StringUtils.countMatches(s1, "<b>Match#:");
		if(matches == 0){
			for(int i = 0; i < max; i++) output[i] = "";
			return output;
		}else if(matches == 1) {
			output[0] = s1;
			for(int i = 1; i < max; i++) output[i] = "";
			return output;
		}else {
			int index = 0;
			for(index = 0; index < matches-1; index++) {
				System.out.println("index: " + index);
				output[index] = s1.substring(0, 1);
				s1 = s1.substring(1);
				output[index] += s1.substring(0, s1.indexOf("<br/><br/><b>Match#:"));
				System.out.println("ss: " + output[index]);
				s1 = s1.substring(s1.indexOf("<br/><br/><b>Match#:"));
			}
			output[index] = s1;
			index++;
			while(index < max-1) {
				output[index] = "";
				index++;
			}
		}
		return output;
	}

	// method to convert output string into desired format
	public String convertToFormat(String s1) {

		String output = "";
		for (int i = 0; i < s1.length(); i++) {
			try {
				if (s1.substring(i, i + 7).equals("Match#:")) {
					output += s1.substring(0, i);	
					output += "<br/><br/><b>" + s1.substring(i, i + 7) + "</b>";
					
					
					
					int match_no = Integer.parseInt(s1.substring(i+7, s1.indexOf("AScale#:")).trim());
					if(teamName.startsWith(competition.matches.get(match_no-1).data[0][0])
							|| teamName.startsWith(competition.matches.get(match_no-1).data[1][0])
							|| teamName.startsWith(competition.matches.get(match_no-1).data[2][0])) {
						output += "<font color=\"red\"><b>RED</b></font>";
					}else {
						output += "<font color=\"blue\"><b>BLUE</b></font>";
					}	
					
					
					
					s1 = s1.substring(i + 7);
					i = 0;
				} else if (s1.substring(i, i + 8).equals("AScale#:") || s1.substring(i, i + 9).equals("ASwitch#:")
						|| s1.substring(i, i + 7).equals("ACross:") || s1.substring(i, i + 8).equals("TScale#:")
						|| s1.substring(i, i + 10).equals("TSwitchR#:")
						|| s1.substring(i, i+ 10).equals("TSwitchB#:")) {
					output += s1.substring(0, i);
					output += "<br/><b>" + s1.substring(i, i + 1) + "</b>";
					s1 = s1.substring(i + 1);
					i = 0;
				} else if (s1.substring(i, i + 6).equals("Climb:") || s1.substring(i, i + 6).equals("Floor:")
						|| s1.substring(i, i + 6).equals("Notes:")
						|| s1.substring(i, i + 14).equals("Exchange Zone:")) {
					output += s1.substring(0, i);
					output += "<br/>";
					s1 = s1.substring(i);
					i = 0;
				}
			} catch (Exception e) {
			}
		}
		output += s1;
		return output;
	}
	
}