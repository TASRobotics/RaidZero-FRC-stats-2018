import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataTable extends JFrame {
	
    Competition competition; 
    private boolean columnHeaderClicked;
    
    public DataTable(Competition comp) {
        super("Data Table");
        competition = comp;
        setBackground(Color.WHITE);
        setSize(1600,1000);
        setLocationRelativeTo(null);
        setVisible(true);
        columnHeaderClicked = false;
        
        // columns for the table
        String[] columnNames = {"Team", "Avg A Scale", "Avg A Switch", "Avg A Cross Line", "Avg T Scale", "Max T Scale",
        		"Avg T Switch R", "Avg T Switch B", "Max T Switch", "Avg EZ", "Max EZ", "Avg Climb", "Floor"};    
        
        // put in values for table
        Object[][] data = new Object[competition.robots.size()][13];
        for(int i = 0; i < competition.robots.size(); i++){          
            data[i][0] = "<html>" + competition.robots.get(i).name + "</html>";
            data[i][1] = competition.robots.get(i).avg_a_scale;
            data[i][2] = competition.robots.get(i).avg_a_switch;
            data[i][3] = competition.robots.get(i).avg_a_cross;
            data[i][4] = competition.robots.get(i).avg_t_scale;
            data[i][5] = competition.robots.get(i).max_t_scale;
            data[i][6] = competition.robots.get(i).avg_t_switch_r;
            data[i][7] = competition.robots.get(i).avg_t_switch_b;
            data[i][8] = competition.robots.get(i).max_t_switch;
            data[i][9] = competition.robots.get(i).avg_e_z;
            data[i][10] = competition.robots.get(i).max_e_z;
            data[i][11] = competition.robots.get(i).avg_c;
            data[i][12] = competition.robots.get(i).floor;
        }    
        
        // set up the table
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setFont(new Font("Tahoma", Font.PLAIN, 20));
        table.setRowHeight(70);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);        
        table.getColumnModel().getColumn(0).setPreferredWidth(400);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        // add add the row numbers
        JTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());

        // display
	    JPanel contentPane = new JPanel();
	    contentPane.setLayout(new BorderLayout());
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    setContentPane(contentPane);
	    
	    // generate robot info if robot is clicked
	    table.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = table.rowAtPoint(evt.getPoint());
	            int col = table.columnAtPoint(evt.getPoint());
	            if (row == -1 && col >= 0)
	                columnHeaderClicked = true;
	            if (row >= 0 && col == 0 && !columnHeaderClicked) {
	            	String clicked_name = (String) data[row][0];
	                new BotInfo(competition, clicked_name.substring(6, clicked_name.indexOf(" ")));      
	            }
	        }
	    });
	}
}