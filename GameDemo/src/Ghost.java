import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ghost extends Character {
	
	public static int ghost_Id = 1;
	private GhostStateMachineInterface ghostStateMachine;

	public Ghost(int startX, int startY, float speed, GhostStateMachineInterface gsm ) {
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
		this.ghostStateMachine = gsm;
	}
	
	public void update(boolean wallHit, DIRECTION nextDirection, long deltaTime)
	{
		this.direction = ghostStateMachine.nextState(wallHit, nextDirection).getValue();
		super.update(deltaTime);	
	}
}
