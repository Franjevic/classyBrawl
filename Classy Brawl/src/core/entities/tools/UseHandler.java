package core.entities.tools;

import java.awt.Graphics;
import java.util.ArrayList;

public class UseHandler {
	
	public static ArrayList<ToolUse> useHandler = new ArrayList<ToolUse>();
	
	public void tick() {
		for(int i = 0; i < useHandler.size(); i++) {
			useHandler.get(i).tick();
		}
	}
	public static void translate(int d[]) {
		for(int i = 0; i < useHandler.size(); i++) {
 			useHandler.get(i).translate(d[0], d[1]);
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < useHandler.size(); i++) {
			useHandler.get(i).render(g);
		}
	}

	public static void removeUse(ToolUse toolUse) {
		useHandler.remove(toolUse);
	}

}
