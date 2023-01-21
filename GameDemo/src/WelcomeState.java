import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class WelcomeState extends GameState {

	boolean active;
	String next = "Play", difficulty;
	HashMap<String, Integer> diff_to_health;
	
	public void enter(Object memento) {
		active = true;
		setDifficulty ();
	}
	
	private void setDifficulty() {
		diff_to_health = new HashMap<String, Integer>();	
		diff_to_health.put("Easy", 3);
		diff_to_health.put("Medium", 2);
		diff_to_health.put("Hard", 1);
	}

	@Override
	public void processKeyReleased(int aKeyCode) {
		switch (aKeyCode) {
		case KeyEvent.VK_E:
			difficulty = "Easy";
			active = false;
			break;
		case KeyEvent.VK_M:
			difficulty = "Medium";
			active = false;
			break;
		case KeyEvent.VK_H:
			difficulty = "Hard";
			active = false;
			break;
		default:
			System.out.println("KEY NOT BONDED");
			break;
		}
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
	
	@Override
	public Object memento() {
		return diff_to_health.get(difficulty);
	}
}
