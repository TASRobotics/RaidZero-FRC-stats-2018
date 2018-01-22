import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

public class ContinueProcessingData extends TimerTask implements Serializable {
	Competition competition;
	String directory_path;

	public ContinueProcessingData(Competition comp) {
		competition = comp;
		directory_path = competition.inputDir;
	}

	// method to fetch data from local directory, called /5 seconds in runner
	public void run() {

		// photos
		File directory = new File(directory_path);
		BufferedImage img = null;
		try {
			if (directory.list().length > 0) {
				for (String fileName : directory.list()) {

					System.out.println(directory_path + "\\" + fileName);

					img = ImageIO.read(new File(directory_path + "\\" + fileName));

					if (img == null)
						continue;

					System.out.println("hey");

					String team = fileName.substring(0, fileName.indexOf("."));
					if (competition.botExists(team)) {
						// set photo
						try {
							competition.getBot(team).photo = img;
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Cannot set photo.");
						}

						// move photo to storage and delete file
						File file = new File(directory_path + "\\" + fileName), source = file;
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
		} catch (Exception e) {
			System.out.println("Error in reading image.");
		}

		System.out.println("dir path: " + directory_path);

		// if the a file called Match.csv exists, set the file path to it
		boolean check = new File(directory_path + "\\Match.csv").exists();
		String csv_filepath = "";
		if (check)
			csv_filepath = directory_path + "\\Match.csv";

		// if the CSV file path is not empty, read in the file
		if (!csv_filepath.isEmpty()) {
			try {
				File file = new File(csv_filepath);
				Scanner input = new Scanner(file);
				ArrayList<String[]> data = new ArrayList<String[]>();

				while (input.hasNextLine()) {
					String row = input.nextLine();
					String[] values = new String[4];
					for (int i = 0; i < row.split(",").length; i++)
						values[i] = row.split(",")[i];
					data.add(values);
				}
				input.close();
				
				String error = "";
				
				//check rows and columns
	        	if(data.size() > 12 || data.get(0).length != 4) 
	        		error = "Error. Please type in an available cell";
	        		        	
	        	//check team numbers
	        	if(!competition.matches.get(Integer.parseInt(data.get(0)[1]
	        			.substring(0, data.get(0)[1].indexOf("-")))-1).botExists(data.get(2)[2]))
	        		error = "Error. Invalide Team Number";
	        	if(!competition.matches.get(Integer.parseInt(data.get(0)[1]
	        			.substring(0, data.get(0)[1].indexOf("-")))-1).botExists(data.get(2)[3])) 
	        		error = "Error. Invalid Team Number";
   	
	        	//check if input is a number
	        	for(int j = 3; j < 11; j++)
	        		if(!isNumber(data.get(j)[2]) || !isNumber(data.get(j)[3]))
	        			error = "Error. Invalid input.";
	        	
	        	//check if match no is valid
	        	for(int i = 0; i < data.get(0)[1].length(); i++) {
	        		if(isNumber(data.get(0)[1].charAt(i)+"")){
	        			continue;
	        		}else {
	        			if(data.get(0)[1].charAt(i) != '-') {
	        				error = "Error. Invalid Match number.";
	        				break;
	        			}
	        		}
	        	}
	        	
	        	
	        	if(error.isEmpty()) {
					// input match data
					for (int i = 2; i < 4; i++) {
						String[] match_data = new String[10];
						for (int j = 0; j < 10; j++)
							match_data[j] = data.get(j + 2)[i];
						competition.matches
								.get(Integer.parseInt(data.get(0)[1].substring(0, data.get(0)[1].indexOf("-"))) - 1)
								.inputData(match_data);
					}
	
					// input robot data
					for (int i = 2; i < 4; i++) {
						String[] bot_data = new String[10];
						bot_data[0] = data.get(0)[1];
						for (int j = 1; j < 10; j++)
							bot_data[j] = data.get(j + 2)[i];
						competition.getBot(data.get(2)[i]).inputData(bot_data);
					}
	
					// transfer file to another directory for storage
					File csv_file = new File(csv_filepath);
					File source = csv_file;
					File dest = new File(directory_path + "\\FRC Match File Storage\\" + data.get(0)[1] + ".csv");
					try {
						FileUtils.copyFileToDirectory(source, dest);
					} catch (Exception e) {
						System.out.println("Error in copying file to storage.");
					}
					System.out.println(csv_file.delete());
	        	}	        
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Error in reading excel file. Please check.");
			}
		}
	}
	
	//method to check if number is numeric
	public boolean isNumber(String num) {
		try {
	   		Integer.parseInt(num);
	   		return true;
	   	}catch(Exception e){
	  		return false;
	   	}
	}
}
