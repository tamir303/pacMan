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

		this.speed = speed;
	}
	
	public void setGhostStateMachine(DIRECTION init) {
		this.ghostStateMachine = new RandomMachine(init);
	}
	
	public void update(boolean wallHit, boolean[] canMoveTo ,long deltaTime) {
		this.direction = ghostStateMachine.nextState(wallHit, canMoveTo).getValue();
		super.update(deltaTime);	
	}
	
	public int getGhostId() {
		return this.ghost_Id;
	}
}
