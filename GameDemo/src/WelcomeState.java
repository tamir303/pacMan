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
		return "Play";
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
		g.fillRect(80, 275, 80, 40);
		g.fillRect(280, 275, 80, 40);
		g.fillRect(480, 275, 80, 40);
		String chooseLevel = "please choose your level";
		int textWidth = g.getFontMetrics().stringWidth(chooseLevel);
		g.setColor(Color.white);
		g.drawString(chooseLevel, (aGameFrameBuffer.getWidth()/2-textWidth/2), aGameFrameBuffer.getHeight()/2);
		g.setColor(Color.BLACK);
		g.drawString("easy", 105,300 );
		g.drawString("medium", 300,300 );
		g.drawString("Hard", 505,300 );


		
	}
}
