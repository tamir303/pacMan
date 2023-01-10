import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PacMan extends Character {

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

		direction = DIRECTION.STOP.getValue();
		this.speed = speed;
	}
}
