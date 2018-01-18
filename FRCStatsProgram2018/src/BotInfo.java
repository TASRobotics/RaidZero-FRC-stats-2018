import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

public class BotInfo extends JFrame {

    private Competition competition;
    private Robot robot;
    private JPanel contentPane;

    public BotInfo(Competition competition, Robot robot) {
	this.competition = competition;
	this.robot = robot;

	init();
    }

    private void init() {

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(0, 0, 1080, 760);
	setLocationRelativeTo(null);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);

	JLabel robotImage = new JLabel();
	robotImage.setIcon(new ImageIcon(robot.photo));
	
	robotImage.setHorizontalAlignment(SwingConstants.CENTER);
	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
			.addComponent(robotImage, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE).addGap(717)));
	gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
			.addComponent(robotImage, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE).addGap(427)));
	contentPane.setLayout(gl_contentPane);

	setVisible(true);
    }

}
