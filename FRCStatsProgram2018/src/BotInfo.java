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

	private Robot robot;
	private JPanel contentPane;

	private JTable avgStatsTable;
	private String teamName;

	public BotInfo(Competition competition, String t) {
		robot = competition.getBot(t);
		init();
		setVisible(true);
	}

	private void init() {

		setResizable(false);
		setBounds(0, 0, 1280, 960);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new LineBorder(Color.ORANGE));

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

		JLabel robotImage = new JLabel("No Image Available :(");
		robotImage.setPreferredSize(new Dimension(350, 275));

		robotImage.setHorizontalAlignment(SwingConstants.CENTER);

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
						{ "Teleop Scale", robot.avg_t_scale }, { "Max Teleop Scale", robot.max_t_scale },
						{ "Teleop Switch", robot.avg_t_switch },{ "Max Teleop Switch", robot.max_t_switch },						
						{ "Exchange Zone", robot.avg_e_z }, { "Max Exchange Zone", robot.max_e_z }, 
						{ "Climb", robot.avg_c }, { "Portal", robot.portal },
						{ "Floor Pickup", robot.floor }},
				new String[] { "Average ", "Values" }));

		JLabel lblTeamName = new JLabel("<html>Team Name:" + teamName + "</html>");

		lblTeamName.setVerticalAlignment(SwingConstants.TOP);
		lblTeamName.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTeamName.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup().addContainerGap().addComponent(avgStatsTable,
						GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)));
		gl_topPanel.setVerticalGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
						.addComponent(avgStatsTable, GroupLayout.PREFERRED_SIZE, 391, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(25, Short.MAX_VALUE)));
		topPanel.setLayout(gl_topPanel);

		if (robot.photo != null)
			robotImage.setIcon(new ImageIcon(robot.photo));

		JPanel matchPanel = new JPanel();

		matchPanel.setBorder(new LineBorder(Color.ORANGE, 1, true));
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

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);

		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		textArea.setFont(new Font("Tahoma", Font.PLAIN, 28));
		GroupLayout gl_matchPanel = new GroupLayout(matchPanel);
		gl_matchPanel.setHorizontalGroup(gl_matchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_matchPanel.createSequentialGroup().addContainerGap()
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE).addContainerGap()));
		gl_matchPanel.setVerticalGroup(gl_matchPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_matchPanel.createSequentialGroup().addContainerGap()
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE).addContainerGap()));
		matchPanel.setLayout(gl_matchPanel);

		System.out.println("robot data size" + robot.data.size());

		teamName = robot.name;

		lblTeamName.setText("<html>" + teamName + "</html>");
		textArea.append(robot.returnData());

		contentPane.setLayout(gl_contentPane);

	}

}
