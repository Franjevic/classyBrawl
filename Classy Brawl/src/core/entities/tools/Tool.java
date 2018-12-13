package core.entities.tools;

import java.awt.Image;
import java.util.Random;

import core.gfx.Player;

public class Tool {
	
	public final static Tool basicAxe = new Tool(20, Style.Axe);
	public final static Tool handTool = new Tool(1, Style.noStyle);
	public final static Tool basicPick = new Tool(20, Style.Pickaxe);
	protected Random random = new Random();
	protected int level;
	private Style style;
	protected Image sprite;
	
	public Tool(int level, Style style) {
		this.level = level;
		this.style = style;
		
	}
	public void tick() {
		
	}

	public void useTool() {
		UseHandler.useHandler.add(new ToolUse(Player.direction, level, this.style));
	}

}
