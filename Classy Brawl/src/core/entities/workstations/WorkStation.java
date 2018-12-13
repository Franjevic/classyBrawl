package core.entities.workstations;

import java.awt.Graphics2D;
import java.awt.Image;

import core.entities.windows.Inventory;
import core.gfx.Sprites;

public class WorkStation {
	
	
	public Image sprite;
	public String name;
	public int[] loc;
	public static final int MAX_HOLD = 1;
	public boolean grounded;
	
	public WorkStation(String name, int[] loc, int[] dimensions, boolean putGround) {
		this.name = name;
		this.loc = loc;
		this.sprite = Sprites.tile(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);
		doTile(putGround);
		WorkStations.stations.add(this);
	}

	private void doTile(boolean grounded) {
		if(grounded) {
			grounded = true;
			Inventory.setStation();
		}
		else {
			Inventory.obtainStation(this);
		}
	}

	public void render(Graphics2D g) {
		if(grounded) {
			g.drawImage(sprite, loc[0], loc[1], 64, 64, null);
		}
	}

	
	public void tick() {
		animation();
	}
	
	

	private void animation() {
		// TODO Auto-generated method stub
		
	}

	public void translate(int[] d) {
		this.loc[0]+=d[0];
		this.loc[1]+=d[1];
	}

}
