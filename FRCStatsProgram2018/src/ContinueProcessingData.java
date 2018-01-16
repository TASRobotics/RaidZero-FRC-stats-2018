import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class ContinueProcessingData extends TimerTask implements Serializable
{
	Competition competition;
    String directory_path;
    
    public ContinueProcessingData(Competition comp){
    	competition = comp;
        directory_path = comp.inputDir;
    }
    
    //method to fetch data from local directory, called /5 seconds in runner
    public void run(){
    	
        //photos
    	System.out.println(directory_path);
        File directory = new File(directory_path);
        BufferedImage img = null;
        try{
        	if(directory.list().length>0){
    			for(String fileName: directory.list()){
    				img = ImageIO.read(new File(fileName));
    				String team = fileName.substring(0,fileName.indexOf("."));
    				if(competition.botExists(team)){
    					//set photo
	    				try{
	    					competition.getBot(team).photo = img;
	    				}catch(Exception e) {
	    					JOptionPane.showMessageDialog(null, "Cannot set photo.");
	    				}
	    				
	    				//move photo to storage and delete file
	    				File file = new File(fileName), source = file;
	    				File dest = new File(directory_path + "\\Bot Photo Storage");
		                try {
		                    FileUtils.copyFileToDirectory(source, dest);
		                } catch (Exception e) {
		                    System.out.println("Error in copying file to storage.");
		                }
		                System.out.println(file.delete());                
    				}
    			}
    		}
        }catch(Exception e){
        	System.out.println("Error in reading image.");
        }
        
        boolean checkRed = new File(directory_path + "Red.csv").exists(); //Red
        boolean checkBlue = new File(directory_path + "Blue.csv").exists(); //Blue
        String csv_filepath = "";
        
        if(!csv_filepath.isEmpty()) {
        	String blue_red = "";
	        if(checkRed) { //if red.csv exists
	            csv_filepath = directory_path + "\\Red.csv"; blue_red = "Red";
	        }else if(checkBlue) { //if blue.csv exists
	            csv_filepath = directory_path + "\\Blue.csv"; blue_red = "Blue";
	        }
	        
	        try {
	        	File file = new File(csv_filepath);
	        	Scanner input = new Scanner(file);
	        	ArrayList<String[]> data = new ArrayList<String[]>();
	        	while (input.hasNext()) {
	        		String row = input.next(); 
	        		String[] values = row.split(",");
	        		data.add(values);
	        	}
	        	input.close();
	        	
	        	//input match data
	        	for(int i = 2; i < 5; i++) {
	        		int[] match_data = new int[7];
	        		for(int j = 0; j < 7; j++) 
	        			match_data[j] = Integer.parseInt(data.get(j+2)[i]);   		
	        		competition.matches.get(Integer.parseInt(data.get(0)[1])).inputData(match_data);	        		
	        	}
	        	
	        	//input robot data
	        	for(int i = 2; i < 5; i++) {
	        		int[] bot_data = new int[7];
	        		bot_data[0] = Integer.parseInt(data.get(0)[1]);
	        		for(int j = 1; j < 7; j++)
	        			bot_data[j] = Integer.parseInt(data.get(j+2)[i]);
	        		competition.getBot(data.get(2)[i]).inputData(bot_data);
	        	}
	        	
	        	//transfer file to another directory for storage
	        	File csv_file = new File(csv_filepath);
                File source = csv_file;
                File dest = new File(directory_path + "\\FRC Match File Storage\\"
                		+ blue_red + " " + data.get(0)[1] + ".csv");
                try {
                    FileUtils.copyFileToDirectory(source, dest);
                } catch (Exception e) {
                    System.out.println("Error in copying file to storage.");
                }
                System.out.println(csv_file.delete());     
	        }catch(Exception e){
	        	JOptionPane.showMessageDialog(null, "Error in reading excel file. Please check.");
	        }
        }
    }
}
