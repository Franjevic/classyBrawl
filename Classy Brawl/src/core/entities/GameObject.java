package core.entities;

import java.awt.Graphics2D;

public abstract class GameObject {
	
	protected int[] loc;
	
	public GameObject(int[] loc) {
		this.loc = loc;
	}
	
	public abstract void render(Graphics2D g);
	
	public abstract void tick();

}
