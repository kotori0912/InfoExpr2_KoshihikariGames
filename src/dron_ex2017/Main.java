package dron_ex2017;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

			  JFrame frame = new JFrame("DronEx!");
			  frame.add( new DronEx() );
			  frame.pack();
			  frame.setVisible(true);

			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
    }
}
