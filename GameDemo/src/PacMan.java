import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PacMan {

	private BufferedImage image;

	enum e_direction {
		UP, DOWN, LEFT, RIGHT, STOP
	}

	private float x, y, speed, step;
	private int direction;

	public PacMan(int startX, int startY, float speed) {
		// Load the Pac-Man image
		try {
			image = ImageIO.read(new File("pacman.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the initial position of Pac-Man
		x = startX;
		y = startY;

		direction = e_direction.STOP.ordinal();
		this.speed = speed;
	}

	public BufferedImage getImage() {
		return image;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return this.direction;
	}
	
	public float getStep() {
		return this.step;
	}

	public void update(long deltaTime) {
		// Update the position of Pac-Man based on its current direction
		this.step = (float) (speed * 0.01f * deltaTime / 2);

		if (direction == e_direction.UP.ordinal()) {
			y -= step;
		} else if (direction == e_direction.DOWN.ordinal()) {
			y += step;
		} else if (direction == e_direction.LEFT.ordinal()) {
			x -= step;
		} else if (direction == e_direction.RIGHT.ordinal()) {
			x += step;
		}
	}
}
