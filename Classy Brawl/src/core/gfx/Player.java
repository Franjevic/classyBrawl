package core.gfx;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import core.Runner;
import core.actionlisteners.KeyStrokes;
import core.entities.tools.Tool;

public class Player {

	public final static int[] loc = new int[2];
	private Image costume = Passets.dplayer0;
	public static Rectangle playerHB = new Rectangle(48, 60);
	private static Tool inHand = Tool.basicPick;
	private boolean moving = false;
	public static int direction = 0;
	private static boolean canMove = true;

	public Player() {
		loc[0] = (Runner.WIDTH / 2) - (64 / 2);
		loc[1] = (Runner.HEIGHT / 2) - (64 / 2);
		playerHB.setLocation((Runner.WIDTH / 2) - playerHB.width / 2, (Runner.HEIGHT / 2) - playerHB.height / 2);
	}

	public static void useHandItem() {
		inHand.useTool();
	}

	public final void render(Graphics2D g) {
		g.drawImage(costume, loc[0], loc[1], 64, 64, null);
	}

	public static void setTool(Tool tool) {
		inHand = tool;
	}

	public final void tick() {
		moving = false;
		if (canMove) {
			for (int i = 0; i < KeyStrokes.move.length; i++) {
				if (KeyStrokes.move[i] != 0)
					moving = true;
			}
			changeDirection();
			if (moving)
				if (direction == 0) {
					if (Runner.ticks % 12 == 0.0) {
						if (costume == Passets.dplayer0) {
							costume = Passets.dplayer1;
						} else
							costume = Passets.dplayer0;
					}
				} else if (direction == 1) {
					if (Runner.ticks % 15 == 0.0) {
						if (costume == Passets.lplayer0)
							costume = Passets.lplayer1;
						else
							costume = Passets.lplayer0;
					}
				} else if (direction == 2) {
					if (Runner.ticks % 15 == 0.0) {
						if (costume == Passets.uplayer0)
							costume = Passets.uplayer1;
						else {
							costume = Passets.uplayer0;
						}
					}
				} else if (direction == 3) {
					if (Runner.ticks % 15 == 0.0) {
						if (costume == Passets.rplayer0) {
							costume = Passets.rplayer1;
						} else
							costume = Passets.rplayer0;
					}
				}
		}
	}

	private void changeDirection() {
		if (direction == 1 && KeyStrokes.move[0] != 0) {
			return;
		} else if (direction == 0 && KeyStrokes.move[3] != 0) {
			return;
		} else if (direction == 3 && KeyStrokes.move[2] != 0) {
			return;
		} else if (direction == 2 && KeyStrokes.move[1] != 0) {
			return;
		}
		if (direction != 0 && KeyStrokes.move[3] != 0) {
			direction = 0;
			costume = Passets.dplayer0;
		} else if (direction != 1 && KeyStrokes.move[0] != 0) {
			direction = 1;
			costume = Passets.lplayer0;
		} else if (direction != 2 && KeyStrokes.move[1] != 0) {
			direction = 2;
			costume = Passets.uplayer0;
		} else if (direction != 3 && KeyStrokes.move[2] != 0) {
			direction = 3;
			costume = Passets.rplayer0;
		}

	}

	public static void stopMovement() {
		canMove = false;
	}

	public static void startMovement() {
		canMove = true;
	}

	public static boolean canMove() {
		return canMove;
	}
}

class Passets {
	public static Image dplayer0 = Sprites.tile(0, 1, 2, 2);
	public static Image dplayer1 = Sprites.tile(2, 1, 2, 2);
	public static Image uplayer0 = Sprites.tile(0, 3, 2, 2);
	public static Image uplayer1 = Sprites.tile(2, 3, 2, 2);
	public static Image rplayer0 = Sprites.tile(0, 5, 2, 2);
	public static Image rplayer1 = Sprites.tile(2, 5, 2, 2);
	public static Image lplayer0 = Sprites.tile(0, 7, 2, 2);
	public static Image lplayer1 = Sprites.tile(2, 7, 2, 2);
}
