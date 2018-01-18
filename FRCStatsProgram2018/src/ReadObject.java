import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

public class ReadObject {
	
    @SuppressWarnings("resource")
	public StatsDatabase2018 deserializeCompetition(String filename) {
        StatsDatabase2018 database = null;
        FileInputStream f_in = null;
        ObjectInputStream o_in = null;
        
        try { 
            f_in = new FileInputStream(filename);
            o_in = new ObjectInputStream(f_in);
            database = (StatsDatabase2018) o_in.readObject();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Database does not exist.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }     
        return database;        
    }
}