package core.gfx;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprites {

	private static BufferedImage ss;

	public Sprites(BufferedImage ss) {
		Sprites.ss = ss;
	}

	public static Image tile(int x, int y, int w, int h) {
		x *= 8; y *= 8; w *= 8; h *= 8;
		return ss.getSubimage(x, y, w, h);
	}

	public static Image rot(Image image, int degree) {
		BufferedImage aret = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TRANSLUCENT);
		double inRads = (((double) degree) / 180) * Math.PI;
		AffineTransform at = new AffineTransform();
		at.translate(aret.getWidth() / 2, aret.getHeight() / 2);
		at.rotate(inRads);
		at.translate(-aret.getWidth()/2, -aret.getHeight()/2);		
		Graphics2D g = (Graphics2D)aret.getGraphics();
		g.drawImage(image,at,null);
		return aret;
	}

}
