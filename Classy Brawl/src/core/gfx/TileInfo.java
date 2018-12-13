package core.gfx;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.InputMismatchException;
import java.util.Random;

import core.entities.Damage;
import core.entities.resources.Resource;
import core.entities.resources.ResourceDrop;
import core.entities.resources.Resources;
import core.entities.tools.Style;

public class TileInfo {

	public static final int TILESIZE = 64;
	public BufferedImage tile;
	private boolean[] prints;
	private final int length = 4;
	private Random random = new Random();
	private int[] location;
	public boolean accessable = true;
	public Rectangle hitBox = null;
	private int typeHealth = 0;
	String type = "";
	public int[] index;
	private Style weakness;
	

	public TileInfo(int[] loc, boolean[] info, int[] index) {
		new Assets();
		location = loc;
		this.index = index;
		if (info.length != length)
			throw new InputMismatchException();
		else
			prints = info;
		formTile(false);
	}

	public int size() {
		return this.length;
	}

	private void formTile(boolean pass) {
		tile = new BufferedImage(TILESIZE, TILESIZE, BufferedImage.TRANSLUCENT);
		Graphics g = tile.getGraphics();
		if(!pass) {
			Assets.ground = createGroundTexture();
		}
		if (prints[0]) {
			g.drawImage(Assets.ground, 0, 0, TILESIZE, TILESIZE, null);
		}
		if (prints[1]) {
			g.drawImage(Assets.rock, random.nextInt(TILESIZE / 2), random.nextInt(TILESIZE / 2), TILESIZE / 2,
					TILESIZE / 2, null);
		}
		if (prints[2]) {
			accessable = false;
			hitBox = new Rectangle(location[0], location[1], TILESIZE - 1, TILESIZE - 1);
			g.drawImage(Assets.tree, 0, 0, TILESIZE, TILESIZE, null);
			prints[3] = false;
			typeHealth = 50;
			type = "wood";
			weakness = Style.Axe;
		}
		if (prints[3]) {
			g.drawImage(Assets.ores, 0, 0, TILESIZE, TILESIZE, null);
			accessable = false;
			hitBox = new Rectangle(location[0], location[1], TILESIZE, TILESIZE);
			typeHealth = 200;
			type = "metal";
		}
		
	}

	private BufferedImage createGroundTexture() {
		BufferedImage temp = new BufferedImage(TILESIZE, TILESIZE, BufferedImage.TRANSLUCENT);
		WritableRaster r = temp.getRaster();
		int w = 2;
		for (int x = 0; x < TILESIZE; x += w) {
			for (int y = 0; y < TILESIZE; y += w) {
				double[] a = new double[] { Math.random() * 30, 80 + Math.random() * 80, Math.random() * 40, 255 };
				int[][] pixels = new int[w][w];
				for (int b = 0; b < pixels.length; b++) {
					for (int c = 0; c < pixels[b].length; c++) {
						r.setPixel(x + b, y + c, a);
					}
				}
			}
		}
		temp.setData(r);

		return temp;
	}

	public int[] getLoc() {
		return location;
	}

	public void translate(int[] i) {
		location[0] += i[0];
		location[1] += i[1];
		if (!accessable)
			hitBox.translate(i[0], i[1]);
	}

	public boolean contains(int i, int j) {
		return ((i >= location[0] && i <= location[0] + 64) && (j >= location[1] && j <= location[1] + 64));
	}

	public boolean intersects(Rectangle r) {
		if (hitBox != null)
			return hitBox.intersects(r);
		else
			return false;
	}
	
	public void doHurt(int damage, Style style) {
		if(weakness == style) {
			damage *= 1.5;
		}
		else {
			damage/= 4;
		}
		typeHealth -= damage;
		
		Damage.newDamage(this, damage);
		
		
		if(typeHealth <= 0) {
			for(int i = 0; i < 1 + random.nextInt(100); i++) {
				if(prints[2] && i % 3 == 0)
					Resources.resourceDrops.add(new ResourceDrop(Resource.wood, location,location[1]+32));
				else if(prints[3] && i % 7 == 0)
					Resources.resourceDrops.add(new ResourceDrop(Resource.stoneOre, location, location[1]+32));
			}
			prints[2] = prints[3] = false;
			accessable = true;
			hitBox = null;
			formTile(true);
		}
	}

}

class Assets {
	public static Image ground = null;
	public static Image rock = Sprites.tile(2, 0, 1, 1);
	public static Image tree = Sprites.tile(4, 0, 2, 2);
	public static Image ores = Sprites.tile(4, 4, 2, 2);
	public static Image smack = Sprites.tile(4, 6, 2, 2);
}
