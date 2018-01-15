
public class Match {
	int match_number;
	Robot[] participants;
	int[][] data;
	
	public Match(int n, int[] t) {
		match_number = n;
		participants = new Robot[6];
		data = new int[6][7];
		
		//fill in team numbers
		for(int i = 0; i < 6; i++) data[i][0] = t[i];
	}
	
	public void inputData(int[] d) { 
		//team#, A scale, A switch, T switch, T scale, exchange zone, climb
		for(int i = 0; i < 6; i++) if(data[i][0] == d[0]) data[i] = d;
	}

}
