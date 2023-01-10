import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ghost {
	
	private static final int GHOST_SPEED = 2;
	
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;

	private BufferedImage image;
	private int x;
	private int y;

	private int direction;

	public Ghost(int startX, int startY) {
		// Load the ghost image
		try {
			image = ImageIO.read(new File("ghost.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the initial position of the ghost
		x = startX;
		y = startY;

		// Set the initial direction of the ghost
		direction = (int) (Math.random() * 4);
		y = Math.max(0, Math.min(startY * 2 - 1, y));

		direction = (int) (Math.random() * 4);
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void update(long deltaTime) {
		// Update the position of the ghost based on its current direction
		if (direction == UP) {
			y -= GHOST_SPEED;
		} else if (direction == DOWN) {
			y += GHOST_SPEED;
		} else if (direction == LEFT) {
			x -= GHOST_SPEED;
		} else if (direction == RIGHT) {
			x += GHOST_SPEED;
		}
	}
}
