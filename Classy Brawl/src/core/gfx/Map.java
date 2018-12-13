package core.gfx;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import core.Runner;
import core.actionlisteners.KeyStrokes;
import core.entities.Damage;
import core.entities.resources.Resources;
import core.entities.tools.UseHandler;
import core.entities.workstations.WorkStations;

public class Map {

	public static int mapDimension = 64;
	public static TileInfo[][] tiles = new TileInfo[mapDimension][mapDimension];
	private static int[] totalTranslate = new int[2];
	public static TileInfo currentTile;

	public Map() {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles.length; y++) {
				tiles[x][y] = new TileInfo(
						new int[] { x * TileInfo.TILESIZE, y * TileInfo.TILESIZE }, new boolean[] { true,
								Math.random() * 5 > 4.2, Math.random() * 10 > 8.5, Math.random() * 100 > 99.8 },
						new int[] { x, y });
			}
		}
	}

	public void render(Graphics2D g) {
		for (int x = totalTranslate[0] / TileInfo.TILESIZE; x < tiles.length; x++) {
			if (tiles[x][0].getLoc()[0] >= Runner.WIDTH + TileInfo.TILESIZE) {
				break;
			}
			for (int y = totalTranslate[1] / TileInfo.TILESIZE; y < tiles.length; y++) {
				g.drawImage(tiles[x][y].tile, tiles[x][y].getLoc()[0], tiles[x][y].getLoc()[1], null);
				//if(tiles[x][y].accessable == false) {
				//g.draw(tiles[x][y].hitBox);
				//}		
				if (tiles[x][y].getLoc()[1] > Runner.HEIGHT + TileInfo.TILESIZE)
					break;
			}
		}
	}

	public static void translate() {
		if (Player.canMove()) {
			int[] i = new int[] { KeyStrokes.move[0] + KeyStrokes.move[2], KeyStrokes.move[1] + KeyStrokes.move[3] };
			if (totalTranslate[0] <= 0 && Math.signum(i[0]) == 1.0)
				return;
			if (totalTranslate[1] <= 0 && Math.signum(i[1]) == 1.0)
				return;
			if (totalTranslate[0] >= mapDimension * TileInfo.TILESIZE + 300 - Runner.WIDTH && Math.signum(i[0]) == -1.0)
				return;
			if (totalTranslate[1] >= mapDimension * TileInfo.TILESIZE - (Runner.HEIGHT + 10)
					&& Math.signum(i[1]) == -1.0)
				return;
			int[] a = new int[] { i[0], 0 };
			int[] b = new int[] { 0, i[1] };

			if (canMove(-a[0], -a[1])) {
				totalTranslate[0] += -a[0];
				totalTranslate[1] += -a[1];
				translateAll(a);
				for (int x = 0; x < tiles.length; x++) {
					for (int y = 0; y < tiles[x].length; y++) {
						tiles[x][y].translate(a);
					}
				}
			}
			if (canMove(-b[0], -b[1])) {
				totalTranslate[0] += -b[0];
				totalTranslate[1] += -b[1];
				translateAll(b);
				for (int x = 0; x < tiles.length; x++) {
					for (int y = 0; y < tiles[x].length; y++) {
						tiles[x][y].translate(b);

					}
				}
			}
		}
	}

	private static void translateAll(int[] delta) {
		UseHandler.translate(delta);
		Resources.translate(delta);
		Damage.translate(delta);
		WorkStations.translate(delta);
	}

	public void tick() {
		currentTile();
		translate();
	}

	private static final void currentTile() {
		int[] point = new int[] { Runner.WIDTH / 2, Runner.HEIGHT / 2 };
		for (int x = (totalTranslate[0] / TileInfo.TILESIZE); x < tiles.length; x++) {
			if (tiles[x][0].getLoc()[0] >= Runner.WIDTH + TileInfo.TILESIZE) {
				break;
			}
			for (int y = (totalTranslate[1] / TileInfo.TILESIZE); y < tiles.length; y++) {
				if (tiles[x][y].contains(point[0], point[1])) {
					currentTile = tiles[x][y];
				}
				if (tiles[x][y].getLoc()[1] > Runner.HEIGHT + TileInfo.TILESIZE)
					break;
			}
		}
	}

	public static final TileInfo calcTileCollision(Rectangle check) {
		TileInfo tileFound = null;
		for (int x = (totalTranslate[0] / TileInfo.TILESIZE); x < tiles.length; x++) {
			if (tiles[x][0].getLoc()[0] >= Runner.WIDTH + TileInfo.TILESIZE) {
				break;
			}
			for (int y = (totalTranslate[1] / TileInfo.TILESIZE); y < tiles.length; y++) {
				if (tiles[x][y].intersects(check)) {
					tileFound = tiles[x][y];
				}
				if (tiles[x][y].getLoc()[1] > Runner.HEIGHT + TileInfo.TILESIZE)
					break;
			}
		}
		return tileFound;
	}

	private static final boolean canMove(int dx, int dy) {
		TileInfo checkTile = null;
		Rectangle recCheck = new Rectangle(Player.playerHB);
		recCheck.translate(dx, dy);
		for (int x = (totalTranslate[0] / TileInfo.TILESIZE); x < tiles.length; x++) {
			if (tiles[x][0].getLoc()[0] >= Runner.WIDTH + TileInfo.TILESIZE) {
				break;
			}
			for (int y = (totalTranslate[1] / TileInfo.TILESIZE); y < tiles.length; y++) {
				if (!tiles[x][y].accessable && tiles[x][y].intersects(recCheck)) {
					checkTile = tiles[x][y];
				}
				if (tiles[x][y].getLoc()[1] > Runner.HEIGHT + TileInfo.TILESIZE)
					break;
			}
		}
		if (checkTile != null && !checkTile.accessable) {
			return false;
		} else
			return true;
	}

}
