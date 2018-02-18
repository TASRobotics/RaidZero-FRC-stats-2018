import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

public class WriteObject {	
	// method to save database
    public void serializeDatabase(StatsDatabase2018 database, String fileName) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(database);
            oos.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in writing object: " + e, "Error", JOptionPane.INFORMATION_MESSAGE);  
        }
    } 
}
