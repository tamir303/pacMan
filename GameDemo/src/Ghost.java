import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ghost extends Character {
	
	public static int static_ghost_Id = 0;
	private int ghost_Id;
	private GhostStateMachineInterface ghostStateMachine;

	public Ghost(int startX, int startY, float speed) {
		// Load the Ghost image
		try {
			ghost_Id = ++static_ghost_Id;
			String ghostImage = String.format("ghost%d.png", ghost_Id);
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
	
	public void setGhostStateMachine(GhostStateMachineInterface gsm) {
		this.ghostStateMachine = gsm;
	}
	
	public void update(boolean wallHit, DIRECTION nextDirection, long deltaTime)
	{
		this.direction = ghostStateMachine.nextState(wallHit, nextDirection).getValue();
		super.update(deltaTime);	
	}
	
	public int getGhostId() {
		return this.ghost_Id;
	}
}
