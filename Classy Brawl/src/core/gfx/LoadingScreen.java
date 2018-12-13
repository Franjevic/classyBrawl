package core.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import core.Runner;

public class LoadingScreen {

	private BufferedImage loadbar;
	private Rectangle barOutline;
	private Rectangle bar;
	private int percent = 0;

	public LoadingScreen() {
		loadbar = new BufferedImage(256, 64, BufferedImage.TRANSLUCENT);
		barOutline = new Rectangle(256, 16);
		barOutline.setLocation(Runner.WIDTH / 2 - (barOutline.width / 2), Runner.HEIGHT / 2 - (barOutline.height / 2));
		bar = new Rectangle();
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		createLoad();
		g.drawImage(loadbar, Runner.WIDTH / 2 - loadbar.getWidth() / 2, Runner.HEIGHT / 2 - loadbar.getHeight() / 2,
				null);
		((Graphics2D) g).draw(barOutline);
		g.setColor(Color.cyan);
		((Graphics2D) g).fill(bar);
	}

	private void createLoad() {
		double a = percent;
		a /= 100;
		int w = (int) (barOutline.width * (a));
		bar.setSize(w, 15);
		bar.setLocation(barOutline.x + 1, barOutline.y + 1);
	}

	public void addPercent(int i) {
		percent += i;
	}

}
