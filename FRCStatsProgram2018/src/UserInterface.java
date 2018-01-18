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

public class UserInterface extends JFrame implements MouseListener{
	private int mouseX, mouseY;
    private int mouseButton;
    private boolean firstRun;
    Competition competition;
    private BufferedImage raidZero;
    private String filepath;

	public UserInterface(Competition comp, String s) {
		super("Raid Zero FRC 2017");
		
		filepath = s;
        setBackground(Color.WHITE);
        competition = comp;
		//Variable Setup
		mouseX = mouseY = 0;
        mouseButton = 0;
        firstRun = true;
		//Frame Setup
		setSize(1600,1000);
        setBackground(Color.BLACK);
        setVisible(true);
        
        addMouseListener(this);
	}
	public void mouseClicked(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        mouseButton = e.getButton();
        repaint();
    }
	public void paint(Graphics window ){
        if(firstRun)
        {
            //set up background
            window.setColor(new Color(34, 34, 34));
            window.fillRect(0,0,2000,1200);
            window.drawImage(raidZero, 0, 0, 1600, 1000, this);
            window.setColor(new Color(50, 50, 50));
            for(int j = 100; j < 1000 ; j += 200)
               window.fillRect(j, 750, 180, 180);            
            window.setColor(new Color(230, 230, 230));
            window.setFont(new Font("Arial", Font.BOLD, 30));
            window.drawString("Data", 110, 800);
            window.drawString("Table", 110, 850);
            window.drawString("Search", 310, 800);
            window.drawString("Robot", 310, 850);
            window.drawString("Search", 510, 800);
            window.drawString("Match", 510, 850);        
            window.drawString("Sync", 710, 800);
            window.drawString("Online", 710, 850);
            window.drawString("Sync", 910, 800);
            window.drawString("Online", 910, 850);
            window.drawString("(Local)", 910, 900);
            window.drawString(competition.getCompetitionName(), 100, 100);
            window.setColor(new Color(30, 30, 30));
            
            firstRun = false;
        }
        draw(window);
    }
	public void draw(Graphics window) {
		if(mouseButton == MouseEvent.BUTTON1) {
			
		}
	}
}
