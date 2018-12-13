package core.entities;

import java.awt.Graphics;
import java.awt.Image;

import core.entities.resources.Resource;
import core.entities.workstations.WorkStation;
import core.gfx.Text;

public class InventorySlot {
	
	public boolean full;

	private Resource resource;
	
	private boolean isStation = false;

	private int amount = 0;

	private int maxCount = 0;

	public boolean filled = false;

	private Image text;

	private WorkStation station;

	public void render(Graphics g, int[] loc, int[] dimension) {
		g.fillRect(loc[0], loc[1], dimension[0], dimension[1]);
		
		if(filled && !isStation) {
			if(resource != null) {
				g.drawImage(resource.sprite,loc[0],loc[1],dimension[0],dimension[1],null);
				g.drawImage(text, loc[0], loc[1] + 30, null);
			}
		}
		else if(isStation) {
			g.drawImage(station.sprite, loc[0], loc[1],32,32, null);
		}
		
	}
	
	public void makeStation(WorkStation station) {
		this.station = station;
		this.isStation = true;
	}
	
	public String getType() {
		return resource.name;
	}

	public boolean canPut() {
		return amount<maxCount;
	}
	
	private void createRatioImage() {
		this.text = Text.text(amount + "/" + maxCount, 8, 0);
	}

	public void addConcurrent(Resource res) {
		amount++;
		if(amount == maxCount)
			full = true;
		createRatioImage();
	}
	
	public void putNew(Resource res) {
		filled = true;
		this.resource = res;
		amount++;
		maxCount = res.maxHold;
		createRatioImage();
	}

}
