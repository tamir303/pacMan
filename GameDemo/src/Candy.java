import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Candy {
	
	private BufferedImage image;
	private int x,y;
	
	public Candy(int startX, int startY) {
		// Load the Pac-Man image
		try {
			image = ImageIO.read(new File("candy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the initial position of Pac-Man
		x = startX;
		y = startY;
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
}
