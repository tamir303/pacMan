import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import javax.imageio.ImageIO;

public class EndGame extends GameState {
	boolean active;
	JFrame frame;
	
	public void enter(Object memento) {
		active = true;
	}
	
	public void processKeyReleased(int aKeyCode) {
		if (aKeyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
		
		active = false;
	}
	
	public boolean isActive() { return active; }
	
	public String next() {
		return "Welcome";
	}
	
	public void render(GameFrameBuffer aGameFrameBuffer) {

		Graphics g = aGameFrameBuffer.graphics();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("pac-man-game-over.jpg"));
			g.drawImage(image, 0, 0, aGameFrameBuffer.getWidth(), aGameFrameBuffer.getHeight(), null);
		} catch (IOException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, aGameFrameBuffer.getWidth(), aGameFrameBuffer.getHeight());
		}
	}   

}

