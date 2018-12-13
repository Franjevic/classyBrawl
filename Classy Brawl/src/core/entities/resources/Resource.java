package core.entities.resources;

import java.awt.Image;

import core.gfx.Sprites;

public class Resource {
	
	
	public static Resource wood = new Resource("wood",new int[] {5,3},36);
	public static Resource stoneOre = new Resource("stone", new int[] {6,1},20);
	
	public Image sprite;
	public String name;
	public int maxHold;

	public Resource(String name, int[] sprite, int maxHold) {
		this.name = name;
		this.sprite = Sprites.tile(sprite[0], sprite[1], 1, 1);
		this.maxHold = maxHold;
	}
}
