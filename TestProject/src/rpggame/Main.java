package rpggame;

import java.awt.BorderLayout;

import javax.swing.JFrame;


public class Main {

	// GAME STATE
	enum GameState{
		STATE_WORLD,
		STATE_COMBAT
	}
	
	public GameState gameState = GameState.STATE_WORLD;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window  = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("RPG 1");

		//window.setLayout(BorderLayout);
		
		GamePanel gamePanel = new GamePanel();
		
		window.add(gamePanel, BorderLayout.CENTER);
		window.pack();
		
		MessagePanel messagePanel = new MessagePanel();
		window.add(messagePanel, BorderLayout.LINE_END);

		//window.setLayout(null);
		//
		window.pack();

		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.mPanel = messagePanel;
		gamePanel.StartGameThread();
	}

}
