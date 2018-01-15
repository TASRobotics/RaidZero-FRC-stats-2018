import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ContinueProcessingData extends TimerTask implements Serializable
{
	Competition competition;
    String directory_path;
    
    public ContinueProcessingData(Competition comp){
    	competition = comp;
        directory_path = comp.inputDir;
    }
    
    //method to fetch data from local directory, called /5 sec in runner
    public void run(){
    	
        //photos
        File directory = new File(directory_path + "\\Robot Photos");
        BufferedImage img = null;
        try{
        	if(directory.list().length>0){
    			for(String fileName: directory.list()){
    				img = ImageIO.read(new File(directory_path + "\\Robot Photos\\" + fileName));
    				String team = fileName.substring(0,fileName.indexOf("."));
    				if(competition.botExists(team)){
    					//set photo
	    				try{
	    					competition.getBot(team).photo = img;
	    				}catch(Exception e) {
	    					JOptionPane.showMessageDialog(null, "Cannot set photo.");
	    				}
	    				
	    				//move photo to storage and delete file
	    				File file = new File(directory_path + "\\Robot Photos\\" + fileName); 
	    				File source = file;
		                File dest = new File(directory_path + "\\Bot Photo Storage");
//		                try {
//		                    FileUtils.copyFileToDirectory(source, dest);
//		                } catch (IOException e) {
//		                    System.out.println("Error in copying file to storage.");
//		                }
		                System.out.println(file.delete());                 
    				}
    			}
    		}
        }catch(Exception e){
        	System.out.println("Error in reading image.");
        }
    	
    	//Red
        boolean checkRed = new File(directory_path + "\\Red", "Red.csv").exists();
        String csv_filepath;
        if(checkRed){ //if red.csv exists
            csv_filepath = directory_path + "\\Red" + "\\Red.csv";
              
        }
        
        boolean checkBlue = new File(directory_path + "\\Blue", "Blue.csv").exists();
        //check if a file named "Blue.csv" exists
        if(checkBlue){
            csv_filepath = directory_path + "\\Blue" + "\\Blue.csv";
  
        }
    }
}
