package core.gfx;

import javax.swing.JFrame;
import core.Runner;

public class InitializeWindow {
	public static JFrame frame;
	public InitializeWindow(Runner runner){
		frame = new JFrame();
		frame.add(runner);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
