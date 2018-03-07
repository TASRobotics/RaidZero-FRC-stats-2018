import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Runner {
	
	static StatsDatabase2018 database;
	static String directory_path;
	
    public static void main(String[]args) throws IOException{
    	//open the database, if does not exist, then create new database
    	ReadObject obj = new ReadObject();
    	if(obj.deserializeCompetition("StatsDatabase2018.data") == null) {
    		System.out.println("hi");
    		database = new StatsDatabase2018();
    	}else {
    		System.out.println("bye");
    		database = obj.deserializeCompetition("StatsDatabase2018.data");
    	}
    	
    	// option dialog	 
        String[] buttons = {"New", "Open"};
        int new0_open1 = JOptionPane.showOptionDialog(null, 
        		"Create new competition or open existing competition.", "Select", 
        		JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons[1]);
        
        if (new0_open1 == 0) { 
        	// setup for new competition
        	JFrame setupFrame = new JFrame("Setup for New Competition");
        	setupFrame.setSize(500, 165);
        	setupFrame.setLayout(new BorderLayout());
        	setupFrame.setLocationRelativeTo(null); // center frame
        	setupFrame.setResizable(false);      	
        	JPanel setupPanel = new JPanel();
        	
        	// competition name
            setupPanel.add(new JLabel("Comp. Name: "));
            JTextField comp_name = new JTextField(30);
            setupPanel.add(comp_name);
            
            // blue alliance URL
            setupPanel.add(new JLabel("\nBlue Alliance URL: "));
            JTextField url = new JTextField(30);
            setupPanel.add(url);
            
            // choose input directory
            setupPanel.add(new JLabel("Input Directory: "));          
            JLabel directory = new JLabel("");
            setupPanel.add(directory);
            JButton button_choose = new JButton("Choose");
            button_choose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					// file chooser
					JFileChooser chooser = new JFileChooser();
				    chooser.setCurrentDirectory(new java.io.File("user.home"));
				    chooser.setDialogTitle("Select Input Directory");
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    chooser.setAcceptAllFileFilterUsed(false); //disable the "All files" option
				    int result = chooser.showOpenDialog(setupPanel);
				    if (result == JFileChooser.APPROVE_OPTION) {
				    	File selectedFile = chooser.getSelectedFile();
				    	directory.setText(selectedFile.getAbsolutePath());
				    	directory_path = directory.getText();
				    }
				}
            });
            setupPanel.add(button_choose);
            
            // OK button
            JButton button_ok = new JButton("OK");
            button_ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					if(directory.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Choose Directory", "Message", JOptionPane.INFORMATION_MESSAGE);
					}else{
						// create new competition and add to database
						Competition comp = new Competition(comp_name.getText(), url.getText(), directory.getText());
						database.addCompetition(comp);
				        setupFrame.dispose();
				        runTheProgram(comp);
					}
				}
            });            
            setupPanel.add(button_ok);
            
            //add panel to frame
            setupFrame.add(setupPanel);
            setupFrame.setVisible(true);  
            
        } else if (new0_open1 == 1) { 
        	// open previously saved competition
        	if(database.competitions.size() == 0) {
        		JOptionPane.showMessageDialog(null, "No previous competitions.", "Error", JOptionPane.ERROR_MESSAGE);
        		System.exit(0);
        	}else {
        		// select existing competition
	        	JFrame chooseCompFrame = new JFrame("Select Competition");
	        	chooseCompFrame.setSize(500, 150);
	        	chooseCompFrame.setLayout(new BorderLayout());
	        	chooseCompFrame.setLocationRelativeTo(null); //this will center frame
	        	chooseCompFrame.setResizable(false);
	        	
	        	// drop down menu
	        	JPanel panel = new JPanel();
	        	String[] competitions = new String[database.competitions.size()];
	        	for(int i = 0; i < competitions.length; i++)
	        		competitions[i] = database.competitions.get(i).name; 
	        	JComboBox<String> menu = new JComboBox<String>(competitions);
	        	menu.setSelectedIndex(competitions.length-1);
	        	panel.add(menu);
	        	
	        	// OK button
	        	JButton button_ok = new JButton("OK");
	        	button_ok.addActionListener(e-> {
					chooseCompFrame.dispose();
					runTheProgram(database.getCompetition((String)menu.getSelectedItem()));	
	        	});
	        	panel.add(button_ok);
	        	
	        	//add menu to frame
	        	chooseCompFrame.add(panel);
	        	chooseCompFrame.setVisible(true);
        	}
        }
    }
    
    public static void runTheProgram(Competition competition){
    	// open a user interface for that competition
        UserInterface ui = new UserInterface(competition);
        ui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // add a timer to continuously read in data
        Timer timer = new Timer();     
        timer.schedule(new ContinueProcessingData(competition), 0, 2000); //retrieve files every 5 sec 
        
        // save to database when window is closed
        ui.addWindowListener(new java.awt.event.WindowAdapter() {
        	@Override
        	public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        		String fileName = "StatsDatabase2018.data";
                WriteObject obj = new WriteObject();
                try{
                	obj.serializeDatabase(database, fileName);
                    System.exit(0);
                }catch(Exception e){
                	JOptionPane.showMessageDialog(null, "Error in writing object." + e);  
                }
                System.exit(0);            	
            }
        });
    }
}
