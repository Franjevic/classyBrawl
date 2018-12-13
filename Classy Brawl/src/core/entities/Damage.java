package core.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import core.gfx.Sprites;
import core.gfx.Text;
import core.gfx.TileInfo;

public class Damage {

	private TileInfo tile;
	private Image dmg;
	private static Random random = new Random();
	private int[] vector = new int[2];
	private int startVal;
	private int minY;
	private boolean doBounce;
	private int[] loc = new int[2];
	private Image impact = Sprites.tile(4, 6, 2, 2);	
	private int count = 0;	
	public static ArrayList<Damage> damages = new ArrayList<Damage>();
	
	public void translateDamage(int[] d) {
		loc[0] += d[0];
		loc[1] += d[1];
		minY += d[1];
	}
	public static void translate(int[] d) {
		for(int i = 0; i < damages.size(); i++) {
			damages.get(i).translateDamage(d);
		}
	}

	public Damage(TileInfo tile, int damage) {
		this.tile = tile;
		dmg = Text.text("" + damage, 32, 0);
		vector[0] = -2 + random.nextInt(5);
		startVal = vector[1] = -4 - random.nextInt(12);
		minY = tile.getLoc()[1] + TileInfo.TILESIZE;
		doBounce = true;
		loc[0] = tile.getLoc()[0];
		loc[1] = tile.getLoc()[1];
	}
	
	public static void render(Graphics2D g) {
		for(int i = 0; i < damages.size(); i++) {
			damages.get(i).renderDamage(g);
		}
	}
	
	public void renderDamage(Graphics2D g) {
		g.drawImage(dmg,loc[0],loc[1],16,16,null);
		if(count < 20) {
			g.drawImage(impact,tile.getLoc()[0],tile.getLoc()[1],64,64,null);
		}
	}
	public static void tick() {
		for(int i = 0; i < damages.size(); i++) {
			damages.get(i).tickDamage();
		}
	}
	
	public void tickDamage() {
		count++;
		if(!doBounce) {
			Damage.damages.remove(this);
		}
		else{
			
			loc[0]+=vector[0];
			loc[1]+=vector[1];
			vector[1]++;
			if(loc[1] >= minY) {
				startVal *= .9;
				vector[1] = (int)startVal;
				if(!significant(startVal,0)) {
					vector[0] = 0;
					vector[1] = 0;
					doBounce = false;
				}
			}
			
		}
	}

	private boolean significant(double val, int i) {
		if(Math.abs(val - i) < 5.0) {
			return false;
		}
		return true;
	}

	public static void newDamage(TileInfo tile, int damage) {
		damages.add(new Damage(tile,damage));
	}
}
