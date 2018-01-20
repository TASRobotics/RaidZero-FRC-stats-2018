import java.io.Serializable;

public class Match implements Serializable{
	int match_number;
	Robot[] participants;
	String[][] data;
	
	public Match(int n, int[] t) {
		match_number = n;
		participants = new Robot[6];
		data = new String[6][10];
		
		//fill in team numbers
		for(int i = 0; i < 6; i++) data[i][0] = t[i]+"";
	}
	
	public void inputData(String[] d) {
		//team#, A scale, A switch, T switch, T scale, exchange zone, climb, portal, floor, notes
		for(int i = 0; i < 6; i++) {
			if(data[i][0].equals(d[0])) {
				data[i] = d;
				break;
			}	
		}
	}
	
	//return the data in a good-looking format
	public String returnData() {
		String s = "Match#: " + match_number + "\n\n";
		for(int i = 0; i < 6; i++){
			s += "Team#: " + data[i][0] + "\n";
			s += "AScale#: " + data[i][1] + "\n";
			s += "ASwitch#: " + data[i][2] + "\n";
			s += "TScale#: " + data[i][3] + "\n";
			s += "TSwitch#: " + data[i][4] + "\n";
			s += "Exchange Zone: " + data[i][5] + "\n";
			s += "Climb: " + data[i][6] + "\n";
			s += "Portal: " + data[i][7] + "\n";
			s += "Floor: " + data[i][8] + "\n";
			s += "Notes: " + data[i][9] + "\n\n";
		}
		return s;
	}
}
