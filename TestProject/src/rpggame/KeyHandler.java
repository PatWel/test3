package rpggame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class KeyHandler implements KeyListener {

		public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, peerPressed, testPressed, attackPressed;
	public enum CommandLevel {CMD_LEVEL_1, CMD_LEVEL_2, CMD_LEVEL_3};
	public CommandLevel cmdLevel = CommandLevel.CMD_LEVEL_1;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//////////////////////////////////////////////////////////////////
		// TODO: Handle custom key bindings
		//////////////////////////////////////////////////////////////////
		
		//Attack Response 
		
		
			if (code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if (code == KeyEvent.VK_LEFT) {
				leftPressed = true;
				 
			}			
			if (code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}			
			if (code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}			
			if (code == KeyEvent.VK_SPACE) {
				spacePressed = true;
			}			
			
			if (code == KeyEvent.VK_P) {
				peerPressed = true;
			}
			
			// Debugging Unit testing
			if (code == KeyEvent.VK_T) {
				testPressed = true;
			}
	
			if (code == KeyEvent.VK_A) {
				//cmdLevel = CommandLevel.CMD_LEVEL_2;
				attackPressed = true;
			}
			if (code == KeyEvent.VK_ESCAPE) {
				//cmdLevel = CommandLevel.CMD_LEVEL_1;
				//attackPressed = true;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		//System.out.println("Released: " +code);
		
		if (code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_LEFT) {
			leftPressed = false;
			
		}			
		if (code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}			
		if (code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}			
		if (code == KeyEvent.VK_P) {
			peerPressed = false;
		}			
		if (code == KeyEvent.VK_A) {
			attackPressed = false;
		}			
		// Debugging Unit testing
		if (code == KeyEvent.VK_T) {
			testPressed = false;
		}
			
	}

}
