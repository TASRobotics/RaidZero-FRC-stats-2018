import java.io.Serializable;

public class Match implements Serializable {
	
	int match_number;
	String[][] data;

	// constructor
	public Match(int n, int[] t) {
		match_number = n;
		data = new String[6][11];
		// fill in team numbers
		for (int i = 0; i < 6; i++) data[i][0] = t[i] + "";
	}

	public void inputData(String[] d) {
		System.out.println("trying to input data for match " + d[0]);
		// team#, A scale, A switch, A cross, T switch R, T scale, T switch B, exchange zone, climb, floor, notes
		if(d[10].length() < 3 || (d[10].length()>=3 && !d[10].startsWith("N/A"))) {
			for (int i = 0; i < 6; i++) {
				if (data[i][0].equals(d[0])) {
					data[i] = d; break;
				}
			}
		}
	}

	// return the data in a good-looking format
	public String returnData() {
		String s = "Match#: " + match_number + "\n\n";
		for (int i = 0; i < 6; i++) {
			s += (i <= 2) ? "RED" + "\n" : "BLUE" + "\n";
			s += "Team#: " + data[i][0] + "\n";
			s += "AScale#: " + data[i][1] + "\n";
			s += "ASwitch#: " + data[i][2] + "\n";
			s += "ACross: " + data[i][3] + "\n";
			s += "TScale#: " + data[i][5] + "\n";
			s += "TSwitchR#: " + data[i][4] + "\n";
			s += "TSwitchB#: " + data[i][6] + "\n";
			s += "Exchange Zone: " + data[i][7] + "\n";
			s += "Climb: " + data[i][8] + "\n";
			s += "Floor: " + data[i][9] + "\n";
			s += "Notes: " + data[i][10] + "\n\n";
		}
		return s;
	}
	
	// method to check if robot participated in that match
	public boolean botExists(String teamNum) {
		for(int j = 0; j < data.length; j++) 
			if(data[j][0].equals(teamNum))
				return true;		
		return false;
	}
}
