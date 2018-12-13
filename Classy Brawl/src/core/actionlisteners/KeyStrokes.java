package core.actionlisteners;

import core.entities.windows.Inventory;
import core.gfx.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStrokes implements KeyListener {
	public static int movespeed = 3;
	
	public static int[] move = new int[4];
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case 65:move[0] = movespeed; break;
		case 87:move[1] = movespeed; break;
		case 68:move[2] = -movespeed; break;
		case 83:move[3] = -movespeed; break;
		case 73:Inventory.display(!Inventory.display);break;
		case 39:Inventory.shift(1);break;
		case 37:Inventory.shift(-1);break;
		//case 69:Inventory.use();break;
		case 32:Player.useHandItem();break;
		default:return;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		
		case 65:move[0] = 0; break;
		case 87:move[1] = 0; break;
		case 68:move[2] = 0; break;
		case 83:move[3] = 0; break;
		}
		
	}
}
