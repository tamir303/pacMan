import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ghost extends Character {
	
	public static int ghost_Id = 1;

	public Ghost(int startX, int startY, float speed) {
		// Load the Ghost image
		try {
			String ghostImage = String.format("ghost%d.png", ghost_Id);
			ghost_Id++;
			image = ImageIO.read(new File(ghostImage));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the initial position of the ghost
		x = startX;
		y = startY;

		direction = DIRECTION.STOP.getValue();
		this.speed = speed;
	}
}
