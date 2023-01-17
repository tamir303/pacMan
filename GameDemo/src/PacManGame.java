import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PacManGame extends GameState {

	boolean active;

	protected int SCORE;
	protected int PAC_LIFE;

	protected final int PAC_MAN_SPEED = 1;
	protected final int GHOST_SPEED = 1;
	protected final int GAME_LIFES = 3;

	protected Map map;
	protected PacMan pacMan;
	protected List<Ghost> ghosts;
	protected List<Candy> candys;
	
	private GameCollisions collisions;
	private GameGraphics graphics;

	@Override
	public void enter(Object memento) {
		active = true;
		int numberOfGhosts = 3;

		map = new Map();
		initPacMan();
		initCandys();
		initScoreLife();
		initGhosts(numberOfGhosts);
		collisions = new GameCollisions(this);
		graphics = new GameGraphics(this);
		playMusic();
	}

	@Override
	public void input() {
		GameInput.instance().processInput(this);
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void update(long deltaTime) {
		// Update the position of Pac-Man based on the current direction
		if (!collisions.detect_WALL_Collision(pacMan)) {
			pacMan.update(deltaTime);
			if (collisions.detect_CANDY_Collision())
				SCORE = candys.size();
		}
		
		if (collisions.detect_GHOST_Collision()) {
			if (--PAC_LIFE == 0)
				active = false; // GAME OVER
			else
				pacMan.moveToStartPosition(map.getPacStart());
		}
	
		ghosts.stream().forEach(ghost -> 
		ghost.update(collisions.detect_WALL_Collision(ghost), ghostHint(ghost), canMoveTo(ghost) ,deltaTime));
	}

	@Override
	public void processKeyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			pacMan.setDirection(DIRECTION.UP.getValue());
			break;
		case KeyEvent.VK_DOWN:
			pacMan.setDirection(DIRECTION.DOWN.getValue());
			break;
		case KeyEvent.VK_LEFT:
			pacMan.setDirection(DIRECTION.LEFT.getValue());
			break;
		case KeyEvent.VK_RIGHT:
			pacMan.setDirection(DIRECTION.RIGHT.getValue());
			break;
		default:
			System.out.println("Key not bonded");
		}
	}

	@Override
	public void render(GameFrameBuffer frameBuffer) {
		Graphics2D g = frameBuffer.graphics();
		// Clear the screen
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
		graphics.paintGame(frameBuffer);
	}

	// INITS

	private void initGhosts(int numOfGhosts) {
		this.ghosts = new ArrayList<Ghost>();
		Iterator<AtomicIntegerArray> it = this.map.getGhostsStart().iterator();
		while (it.hasNext() && --numOfGhosts >= 0) {
			AtomicIntegerArray loc = it.next();
			ghosts.add(new Ghost(loc.get(1), loc.get(0), GHOST_SPEED));
		}
		ghosts.stream().forEach(ghost -> {
			if (ghost.getGhostId() == 1)
				ghost.setGhostStateMachine(new RandomMachine(DIRECTION.DOWN));
			else
				ghost.setGhostStateMachine(new RandomMachine(DIRECTION.RIGHT));
		});
	}

	private void initScoreLife() {
		this.SCORE = candys.size();
		this.PAC_LIFE = GAME_LIFES;
	}

	private void initCandys() {
		Iterator<AtomicIntegerArray> it = this.map.getInitialCandysXY().iterator();
		this.candys = new ArrayList<Candy>();
		while (it.hasNext()) {
			AtomicIntegerArray loc = it.next();
			candys.add(new Candy(loc.get(1), loc.get(0)));
		}
	}

	private void initPacMan() {
		this.pacMan = new PacMan(map.getPacStart().get(0), map.getPacStart().get(1), PAC_MAN_SPEED);
	}
	
	private void playMusic() {
	    try {
	    	AudioInputStream music = AudioSystem.getAudioInputStream(new File("music.wav"));
	    	Clip clip = AudioSystem.getClip();
	    	clip.open(music);
	    	clip.start();
	    	clip.loop(Clip.LOOP_CONTINUOUSLY);
	    }
	    catch (Exception e) {
	    	System.out.println("MUSIC FILE NOT FOUND");
	    }
	}
	
	// UTILS

	private DIRECTION ghostHint(Ghost ghost) {
		// returns where the ghost should go to catch pac-man
		int horizon = (int) Math.signum(ghost.getY() - pacMan.getY());
		int vertical = (int) Math.signum(ghost.getX() - pacMan.getX());
		switch (horizon) {
		case 1: // below
			return DIRECTION.UP;
		case -1: // above
			return DIRECTION.DOWN;
		default: // same hight
			if (vertical  == 1) // pac is on left
				return DIRECTION.LEFT;
			return DIRECTION.RIGHT;
		}
	}
	
	private boolean[] canMoveTo(Ghost ghost) {
		boolean[] CMT = {false, false, false, false, false};
		if ((int) ghost.getY() > 0)
			CMT[0] = map.getMap()[(int) ghost.getY() - 1][(int) ghost.getX()] != 1;
		if ((int) ghost.getY() < map.getMAZE_HEIGHT() - 1)
			CMT[1] = map.getMap()[(int) ghost.getY() + 1][(int) ghost.getX()] != 1;
		if ((int) ghost.getX() > 0)
			CMT[2] = map.getMap()[(int) ghost.getY()][(int) ghost.getX() - 1] != 1;
		if ((int) ghost.getX() < map.getMAZE_WIDTH() - 1)
			CMT[3] = map.getMap()[(int) ghost.getY()][(int) ghost.getX() + 1] != 1;
		return CMT;
	}
}
