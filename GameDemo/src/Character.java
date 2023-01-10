import java.awt.image.BufferedImage;

public abstract class Character {
	protected BufferedImage image;
	protected float x, y, speed, step;
	protected int direction;

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
		// Update the position of Character based on its current direction
		this.step = (float) (speed * 0.01f * deltaTime / 2);

		if (direction == DIRECTION.UP.getValue()) {
			y -= step;
		} else if (direction == DIRECTION.DOWN.getValue()) {
			y += step;
		} else if (direction == DIRECTION.LEFT.getValue()) {
			x -= step;
		} else if (direction == DIRECTION.RIGHT.getValue()) {
			x += step;
		}
	}
}
