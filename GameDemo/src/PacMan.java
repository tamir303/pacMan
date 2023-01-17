import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicIntegerArray;

import javax.imageio.ImageIO;

public class PacMan extends Character {

	public PacMan(int startX, int startY, float speed) {
		// Load the Pac-Man image
		try {
			image = ImageIO.read(new File("pacman_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the initial position of Pac-Man
		x = startX;
		y = startY;

		direction = DIRECTION.STOP.getValue();
		this.speed = speed;
	}
	
	public void setDirection(int direction) {
		String image_name = "pacman_" + DIRECTION.values()[direction].toString().toLowerCase() + ".png";
		
		try {
			this.image = ImageIO.read(new File(image_name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.setDirection(direction);
	}

	public void moveToStartPosition(AtomicIntegerArray atomicIntegerArray) {
		direction = DIRECTION.STOP.getValue();
		x = atomicIntegerArray.get(0);
		y = atomicIntegerArray.get(1);
	}
}
