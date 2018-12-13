package core.gfx;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Text {

	private static BufferedImage SS;

	private static final char[] CHARS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'/', '.', '?', '!', '(', ')', '[' };

	public Text(BufferedImage img) {
		SS = img;
	}

	/**
	 * Method to convert words into a picture that can be displayed on a graphical interface
	 * @param maximumLength
	 *            This is measured in pixels.
	 * @param word
	 *            Is the string word that would like to put into image.
	 * @param scale
	 *            Is the desired scaling for the letters.
	 * @author Alec Franjevic.
	 * @version 1.1
	 */
	public static Image text(String word, int scale, int maximumLength) {
		int w = maximumLength * scale, h = scale, length = word.length();
		if (w == 0)
			w = scale * length + 1;
		word = word.toLowerCase();
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = word.charAt(i);
		}
		int x = 0;
		for (int i = 0; i < length; i++) {
			if (x + scale >= w) {
				x = 0;
				h += scale;
			}
			x += scale;
		}
		int y = 0;
		BufferedImage sentence = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
		Graphics g = sentence.getGraphics();
		x = y = 0;
		for (int i = 0; i < length; i++, x += scale) {
			if (x + scale >= sentence.getWidth()) {
				y += scale;
				x = 0;
			}
			if (chars[i] != ' ') {
				g.drawImage(getLetter(chars[i]), x, y, scale, scale, null);
			}

		}

		return sentence;
	}

	public static Image getLetter(char c) {
		BufferedImage img = new BufferedImage(8, 8, BufferedImage.TRANSLUCENT);
		Graphics g = img.getGraphics();
		int index = 0;
		for (index = 0; index < CHARS.length; index++) {
			if (CHARS[index] == c) {
				break;
			}
		}
		g.drawImage(SS.getSubimage(index * 8, 0, 8, 8), 0, 0, null);
		return img;
	}

}
