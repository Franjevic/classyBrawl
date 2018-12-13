package core.entities.workstations;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class WorkStations {
	
	public static ArrayList<WorkStation> stations = new ArrayList<WorkStation>();

	public static void render(Graphics2D g) {
		for(int i = 0; i < stations.size(); i++) {
			stations.get(i).render(g);
		}
	}
	
	public static void tick() {
		for(int i = 0; i < stations.size(); i++) {
			stations.get(i).tick();
		}
	}
	
	public static void translate(int[] d) {
		for(int i = 0; i < stations.size(); i++) {
			stations.get(i).translate(d);
		}
	}
	
}
