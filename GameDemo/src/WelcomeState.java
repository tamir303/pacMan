import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WelcomeState extends GameState {

	boolean active;
	String next = "Welcome";
	
	public void enter(Object memento) {
		active = true;
	}
	
	public void processKeyReleased(int aKeyCode) {
		if (aKeyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
		
		active = false;
	}
	@Override
	public void processKeyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_E:
			next = "Easy";
			break;
		case KeyEvent.VK_M:
			next = "Medium";
			break;
		case KeyEvent.VK_H:
			next = "Hard";
			break;
		default:
			next = "Welcome";
			break;
		}
	}
	public boolean isActive() { return active; }
	
	public String next() {
		return next;
	}

	public void render(GameFrameBuffer aGameFrameBuffer) {
		Graphics g = aGameFrameBuffer.graphics();
		
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("homePage.png"));
			g.drawImage(image, 0, 0, aGameFrameBuffer.getWidth(), aGameFrameBuffer.getHeight(), null);
		} catch (IOException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, aGameFrameBuffer.getWidth(), aGameFrameBuffer.getHeight());
		}
		
		g.setColor(Color.YELLOW);
		g.fillRect(120, 250, 400, 100);
		String chooseLevel = "please choose your level";
		int textWidth = g.getFontMetrics().stringWidth(chooseLevel);
		g.setColor(Color.white);
		g.drawString(chooseLevel, (aGameFrameBuffer.getWidth()/2-textWidth/2), aGameFrameBuffer.getHeight()/2);
		g.setColor(Color.BLACK);
		g.drawString("E - Easy", 150,300 );
		g.drawString("M - Medium", 300,300 );
		g.drawString("H - Hard", 450,300 );

		
	}
}
