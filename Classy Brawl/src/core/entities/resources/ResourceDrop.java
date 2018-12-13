package core.entities.resources;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import core.entities.windows.Inventory;
import core.gfx.Player;

public class ResourceDrop {

	Resource res;
	double[] loc = new double[2];
	double[] moveVec = new double[2];
	private static Random random = new Random();
	private boolean bouncing = true;
	private int minY;
	private double initialForce;
	private int deathTime = 600, count = 0;
	private Rectangle hitBox = new Rectangle();
	
	public ResourceDrop(Resource r, int[] loc, int minY) {
		res = r;
		this.loc[0] = loc[0];
		this.loc[1] = loc[1];
		moveVec[0] = -1 + random.nextInt(3);
		moveVec[1] = -5 - random.nextInt(9);
		initialForce = moveVec[1];
		this.minY = minY;
		hitBox = new Rectangle(loc[0], loc[1], 32, 32);

	}

	public void translate(int[] i) {
		loc[0] += i[0];
		loc[1] += i[1];
		minY += i[1];
	}

	public void render(Graphics2D g) {
		g.drawImage(res.sprite, (int) loc[0], (int) loc[1], 32, 32, null);
	}

	private void playerCollisionCheck() {
		if (this.hitBox.intersects(Player.playerHB)) {
			onPlayerCollision();
		}
	}

	private void onPlayerCollision() {
		Inventory.doPickUp(res);
		Resources.resourceDrops.remove(this);
	}

	public void tick() {
		hitBox.setLocation((int)loc[0], (int)loc[1]);
		count++;
		if (count >= deathTime) {
			Resources.resourceDrops.remove(this);
		}

		if (bouncing) {
			loc[0] += moveVec[0];
			loc[1] += moveVec[1];
			moveVec[1]++;
			if (loc[1] >= minY) {
				minY += 5;
				initialForce *= .7;
				moveVec[1] = initialForce;
				if (moveVec[1] >= -1) {
					bouncing = false;
					moveVec[0] = 0;
					moveVec[1] = 0;
				}
			}
		}
		else {
			playerCollisionCheck();
		}
	}

}
