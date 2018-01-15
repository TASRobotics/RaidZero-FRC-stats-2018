import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

public class WriteObject {
    public void serializeCompetition(Competition competition, String fileName) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(competition);
            oos.flush();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex + "Error in writing object.");  
        }
    }
}