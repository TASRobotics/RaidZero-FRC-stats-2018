import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ReadObject {	
    @SuppressWarnings("resource")
    
    // method to deserialize database if it exists in the directory
	public StatsDatabase2018 deserializeCompetition(String filename) {
        StatsDatabase2018 database = null;
        FileInputStream f_in = null;
        ObjectInputStream o_in = null;        
        try { 
        	// set database
            f_in = new FileInputStream(filename);
            o_in = new ObjectInputStream(f_in);
            database = (StatsDatabase2018) o_in.readObject();
        } catch (Exception e){
            return null;
        }     
        return database;        
    }
}
