package core.gfx;

import java.awt.Graphics;
import java.awt.Image;

import core.Runner;

public class NeedsFocus {
	
	private static Image text = Text.text("Click To Focus", 32, 0);
	
	public void render(Graphics g) {
		g.drawImage(text,Runner.WIDTH/2 - text.getWidth(null) / 2, Runner.HEIGHT / 2 - text.getHeight(null) / 2, null);
	}
}
