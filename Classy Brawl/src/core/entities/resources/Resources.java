package core.entities.resources;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Resources{
	
	public static ArrayList<ResourceDrop> resourceDrops = new ArrayList<ResourceDrop>();
	
	public static void translate(int[]i) {
		for(int a = 0; a < resourceDrops.size(); a++) {
			resourceDrops.get(a).translate(i);
		}
	}

	public void render(Graphics2D g) {
		for(int i = 0; i < resourceDrops.size(); i++) {
			resourceDrops.get(i).render(g);
		}
	}

	public void tick() {
		for(int i = 0;  i < resourceDrops.size(); i++) {
			resourceDrops.get(i).tick();
		}
	}

}
