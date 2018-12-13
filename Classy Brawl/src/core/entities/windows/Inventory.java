package core.entities.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import core.Runner;
import core.entities.InventorySlot;
import core.entities.resources.Resource;
import core.entities.workstations.WorkStation;
import core.gfx.Player;
import core.gfx.Text;

public class Inventory {

	public static boolean display = false;

	private static InventorySlot[] slots = new InventorySlot[24];
	private static InventorySlot station = new InventorySlot();
	private Image label;
	private Image toUse;
	private Image wsl;

	static int selected = 0;

	public Inventory() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new InventorySlot();
		}
		label = Text.text("Inventory", 24, 0);
		toUse = Text.text("press e to use", 16, 8);
		wsl = Text.text("Station", 16, 0);
	}

	public static void display(boolean a) {
		display = a;
		if(a) {
			Player.stopMovement();
		}
		else {
			Player.startMovement();
		}
	}

	public static void shift(int direction) {
		if(display) {
			selected = (int) Runner.clamp(selected, selected + direction, 0, slots.length - 1);
		}
	}
	
	public static void setStation() {
		
	}

	public void render(Graphics g) {

		g.setColor(Color.gray);
		if (display) {
			g.drawImage(label, Runner.WIDTH / 2 - label.getWidth(null) / 2, Runner.HEIGHT / 2 - (label.getHeight(null) + 42), null);
			g.drawImage(toUse, Runner.WIDTH / 2 - toUse.getWidth(null) / 2, Runner.HEIGHT / 2 + 30, null);
			for (int i = 0; i < slots.length; i++) {
				slots[i].render(g, new int[] { (Runner.WIDTH / 2 - 16 + 100 * (i - selected)), Runner.HEIGHT / 2 - 16 },
						new int[] { 32, 32 });
			}
			g.drawImage(wsl, Runner.WIDTH/2 - wsl.getWidth(null)/2, Runner.HEIGHT / 5 - 20, null);
			station.render(g, new int[] {Runner.WIDTH/2 - 16,Runner.HEIGHT/5}, new int[] {32,32});
		}
	}
	
	public static void tick() {
		Player.stopMovement();
	}

	public static void doPickUp(Resource resource) {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i].filled && slots[i].getType().equals(resource.name) && !slots[i].full) {
				slots[i].addConcurrent(resource);
				break;
			}
			else if(!slots[i].filled) {
				slots[i].putNew(resource);
				break;
			}
		}
	}

	public static void obtainStation(WorkStation ws) {
		station.makeStation(ws);
		
	}

}
