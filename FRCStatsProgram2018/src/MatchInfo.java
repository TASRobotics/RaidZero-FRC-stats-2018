import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.*;

public class MatchInfo extends JFrame {
	Competition competition;
	int match_number;
	
	public MatchInfo(Competition c, int m) {
		super("Match " + m);
		competition = c;
		match_number = m;
		setBackground(Color.WHITE);
        setSize(800,500);
        setBackground(Color.BLACK);
        setVisible(true);
        paint();
	}
	
    public void paint(){
        try{
            draw();
        }catch(Exception e){
        	System.out.print(e);
        }
    }
    
    //draw the info on the screen
    public void draw() {
    	
    	setSize(getWidth(), Toolkit.getDefaultToolkit().getScreenSize().height);

    	setLocationRelativeTo(null);
    	
       Match match = competition.getMatch(match_number-1);
       String s = match.returnData(); 
       final JTextArea textArea = new JTextArea();
       textArea.setText(s);
       textArea.setEditable(false);
       textArea.setFont(new Font("Arial",Font.BOLD, 20));    
       JScrollPane scrollPane = new JScrollPane(textArea);      
       JPanel contentPane = new JPanel();
       contentPane.setLayout(new BorderLayout());
       contentPane.add(scrollPane, BorderLayout.CENTER);
       setContentPane(contentPane);
    }
}