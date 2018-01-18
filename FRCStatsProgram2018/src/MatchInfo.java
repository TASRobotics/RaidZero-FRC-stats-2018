import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class MatchInfo extends JFrame {
	Competition competition;
	int match_number;
	
	public MatchInfo(Competition c, int m) {
		super("Match " + m);
		competition = c;
		match_number = m;
		setBackground(Color.WHITE);
        //set up the Frame
        setSize(800,500);
        setBackground(Color.BLACK);
        setVisible(true);
        paint();
	}
	
    public void paint(){
        try{
            draw();
        }catch(Exception exception){
        	System.out.print(exception);
            System.out.println("ERROR");
        }
    }
    
    public void draw() {
       Match match = competition.getMatch(match_number);
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
