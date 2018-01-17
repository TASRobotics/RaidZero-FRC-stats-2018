import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Robot implements Serializable {
	String name;
	transient BufferedImage photo;
	ArrayList<int[]> data;
	double avg_a_scale;
	double avg_a_switch;
	double avg_t_scale;
	double avg_t_switch;
	double avg_e_z;
	double avg_c;

	public Robot(String t) {
		name = t;
		photo = null;
		avg_a_scale = avg_a_switch = avg_t_scale = avg_t_switch = avg_e_z = avg_c = 0;
		data = new ArrayList<int[]>();
	}

	public void inputData(int[] d) {
		// match#, A scale, A switch, T scale, T switch, exchange zone, climb
		data.add(d);

		// update averages
		double sum_a_scale = 0, sum_a_switch = 0, sum_t_scale = 0, sum_t_switch = 0, sum_e_z = 0, sum_c = 0;
		for (int[] i : data) {
			sum_a_scale += i[1];
			sum_a_switch += i[2];
			sum_t_scale += i[3];
			sum_t_switch += i[4];
			sum_e_z += i[5];
			sum_c += i[6];
		}
		avg_a_scale = sum_a_scale / data.size();
		avg_a_switch = sum_a_switch / data.size();
		avg_t_scale = sum_t_scale / data.size();
		avg_t_switch = sum_t_switch / data.size();
		avg_e_z = sum_e_z / data.size();
		avg_c = sum_c / data.size();
	}
}
