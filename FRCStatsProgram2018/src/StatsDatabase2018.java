import java.io.Serializable;
import java.util.ArrayList;

public class StatsDatabase2018 implements Serializable {
	
	ArrayList<Competition> competitions = new ArrayList<Competition>();
	
	public void addCompetition(Competition c) {
		competitions.add(c);
	}
    
	public Competition getCompetition(String name) {
		for (Competition comp: competitions) if (comp.name.equals(name)) return comp;		
		return null;
	} 
}
