package core.entities.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import core.Runner;
import core.gfx.Map;
import core.gfx.Sprites;
import core.gfx.TileInfo;

public class ToolUse {
	private int timeOut = 20;
	private int count = 0;
	private int[] loc = new int[2];
	private int[] loc2 = new int[2];
	private static Image use0 = Sprites.tile(4,2,1,1);
	private static Image use1 = Sprites.tile(5, 2, 1, 1);
	private int scale = 3;
	private int damage;
	private Image costume0;
	private Image costume1;
	public Rectangle hitBox;
	private Random random = new Random();
	private Style style;
	
	
	public ToolUse(int dir, int level, Style style) {
		damage = level+random.nextInt(3);
		this.style = style;
		if(dir == 0) {
			loc[0] = Runner.WIDTH/2 - use0.getWidth(null) * scale;
			loc[1] = Runner.HEIGHT/2 + 40;
			loc2[0] = Runner.WIDTH/2;
			loc2[1] = Runner.HEIGHT/2 + 40;
			hitBox = new Rectangle(loc[0],loc[1],16*scale,8*scale);
		} else if(dir == 1) {
			loc[0] = Runner.WIDTH/2 - 55;
			loc[1] = Runner.HEIGHT/2 - use0.getHeight(null)* scale;
			loc2[0] = Runner.WIDTH/2 - 55;
			loc2[1] = Runner.HEIGHT/2;
			hitBox = new Rectangle(loc[0],loc[1],8*scale,16*scale);
		} else if(dir == 2) {
			loc[0] = Runner.WIDTH/2;
			loc[1] = Runner.HEIGHT/2 - 70;
			loc2[0] = Runner.WIDTH/2 - use1.getWidth(null) * scale;
			loc2[1] = Runner.HEIGHT/2 - 70;
			hitBox = new Rectangle(loc2[0],loc2[1],16*scale,8*scale);
		} else if(dir == 3) {
			loc[0] = Runner.WIDTH/2 + 20;
			loc[1] = Runner.HEIGHT/2;
			loc2[0] = Runner.WIDTH/2 + 20;
			loc2[1] = Runner.HEIGHT/2 - use1.getHeight(null)*scale;
			hitBox = new Rectangle(loc2[0],loc2[1],8*scale,16*scale);
		}
		costume0 = Sprites.rot(use0, 90*dir);
		costume1 = Sprites.rot(use1, 90*dir);
	}
	
	public void translate(int dx, int dy) {
		loc[0] += dx;
		loc2[0] += dx;
		loc[1] += dy;
		loc2[1] += dy;
		hitBox.translate(dx, dy);
	}
	
	private void collision() {
		TileInfo foundCollisionTile = Map.calcTileCollision(hitBox);
		if(foundCollisionTile != null) {
			foundCollisionTile.doHurt(damage, style);
			UseHandler.useHandler.remove(this);
		}
	}
	
	public void tick() {
		collision();
		count++;
		if(count == timeOut) {
			UseHandler.removeUse(this);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(costume0,loc[0], loc[1], use0.getWidth(null) * scale,use0.getHeight(null) * scale,null);
		g.drawImage(costume1, loc2[0], loc2[1], use1.getWidth(null) * scale,use1.getHeight(null) * scale, null);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		//g2.draw(hitBox);
	}

}
