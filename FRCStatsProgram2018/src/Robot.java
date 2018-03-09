import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Robot implements Serializable {
	
	String name;
    transient BufferedImage photo;
    ArrayList<String[]> data;
    double avg_a_scale;
    double avg_a_switch;
    double avg_a_cross;
    double avg_t_scale;
    double avg_t_switch_r;
    double avg_t_switch_b;
    double avg_e_z;
    double avg_c;
    boolean floor;
    int max_t_scale;
    int max_t_switch;
    int max_e_z;
    
    // constructor
    public Robot(String t){
        name = t;
        photo = null;
        avg_a_scale = avg_a_switch = avg_a_cross =avg_t_scale = avg_t_switch_r = avg_t_switch_b = avg_e_z = avg_c = 0;
        max_t_scale = max_t_switch = max_e_z = 0;
        floor = false;
        data = new ArrayList<String[]>();
    }
     
    // input data for one match
    public void inputData(String[] d){
    	// match#, A scale, A switch, A cross, T switch R, T scale, T switch B, exchange zone, climb, floor, notes
    	boolean match_data_exist = false;	
    	
    	// if match exists, replace match data
    	for(int i = 0; i < data.size(); i++) {
    		if(data.get(i)[0].equals(d[0])) {
    			data.set(i, d); 
    			match_data_exist = true; break;
    			
    		}
    	}
    	
    	// if match does not exist, add match data (also sort by match number)
    	if(!match_data_exist) {
    		if(data.size() == 0) {
    			data.add(d);
    		} else {
    			boolean meow = true; 
    			//the cat reminds you to add data in the end if you don't find a place to insert the current data
	    		for(int i = 0; i < data.size(); i++) {
	    			if(Integer.parseInt(data.get(i)[0]) > Integer.parseInt(d[0])) {
	    				data.add(i,d); 
	    				meow = false; break;
	    			}
	    		}
	    		if(meow) data.add(d);
    		}
    	}
    	
    	//update max
    	if(Integer.parseInt(d[5]) > max_t_scale) max_t_scale = Integer.parseInt(d[5]);
    	if(Integer.parseInt(d[4]) + Integer.parseInt(d[6]) > max_t_switch) max_t_switch = Integer.parseInt(d[4]) + Integer.parseInt(d[6]);
    	if(Integer.parseInt(d[7]) > max_e_z) max_e_z = Integer.parseInt(d[7]);
    	
    	//update averages
    	double sum_a_scale = 0, sum_a_switch = 0, sum_a_cross = 0, sum_t_scale = 0, 
    			sum_t_switch_r = 0, sum_t_switch_b = 0, sum_e_z = 0, sum_c = 0, 
    			c_attempt = 0, a_switch_attempt = 0, a_scale_attempt = 0;
    	for(String[] i: data) {
    		if(Integer.parseInt(i[1]) > 0) sum_a_scale += 1;
    		if(Integer.parseInt(i[1]) > 0 || Integer.parseInt(i[1]) == -1) a_scale_attempt += 1;
    		
    		if(Integer.parseInt(i[2]) > 0) sum_a_switch += 1;
    		if(Integer.parseInt(i[2]) > 0 || Integer.parseInt(i[2]) == -1) a_switch_attempt += 1;
    		
    		sum_a_cross += Integer.parseInt(i[3]);
    		sum_t_switch_r += Integer.parseInt(i[4]);
    		sum_t_scale += Integer.parseInt(i[5]); 
    		sum_t_switch_b += Integer.parseInt(i[6]);
    		sum_e_z += Integer.parseInt(i[7]);
    		
    		if(Integer.parseInt(i[8]) == 1) sum_c += 1;
    		if(Integer.parseInt(i[8]) == 1 || Integer.parseInt(i[8]) == -1) c_attempt += 1;
    		
    		if(Integer.parseInt(i[9]) == 1) floor = true;
    	}
    	System.out.println("data size: " + data.size());
    	if(a_scale_attempt != 0) avg_a_scale = sum_a_scale/a_scale_attempt; else avg_a_scale = 0;
    	if(a_switch_attempt != 0) avg_a_switch = sum_a_switch/a_switch_attempt; else avg_a_switch = 0;
    	avg_a_cross = sum_a_cross/data.size();
    	avg_t_switch_r = sum_t_switch_r/data.size();
    	avg_t_scale = sum_t_scale/data.size();
    	avg_t_switch_b = sum_t_switch_b/data.size();
    	avg_e_z = sum_e_z/data.size();
    	if(c_attempt != 0) avg_c = sum_c/c_attempt; else avg_c = 0;
    }
    
    // return the averages in a good-looking format
    public String returnAvg() {
    	String s = "Team " + name + "\n";
		s += "Avg AScale#: " + avg_a_scale + "\n";
		s += "Avg ASwitch#: " + avg_a_switch + "\n";
		s += "Avg ACross: " + avg_a_cross + "\n";
		s += "Avg TScale#: " + avg_t_scale + "\n";
		s += "Avg TSwitchR#: " + avg_t_switch_r + "\n";
		s += "Avg TSwitchB#:" + avg_t_switch_b + "\n";
		s += "Avg Exchange Zone: " + avg_e_z + "\n";
		s += "Avg Climb: " + avg_c + "\n";
		s += "Floor: " + floor + "\n\n";
		return s;    	
    }
    
    // return match data in a good-looking format
    public String returnData() {  	
    	String s = "";
		for(int i = 0; i < data.size(); i++) {
			s += "Match#: " + data.get(i)[0] + "\n";
			s += "AScale#: " + data.get(i)[1] + "\n";
			s += "ASwitch#: " + data.get(i)[2] + "\n";
			s += "ACross: " + data.get(i)[3] + "\n";
			s += "TScale#: " + data.get(i)[5] + "\n";
			s += "TSwitchR#: " + data.get(i)[4] + "\n";
			s += "TSwitchB#: " + data.get(i)[6] + "\n";
			s += "Exchange Zone: " + data.get(i)[7] + "\n";
			s += "Climb: " + data.get(i)[8] + "\n";
			s += "Floor: " + data.get(i)[9] + "\n";
			s += "Notes: " + data.get(i)[10] + "\n\n";
		}
		return s;
    }
}
