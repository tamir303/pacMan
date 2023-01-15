import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class PacManGame extends GameState {

	boolean active;

	private int SCORE;
	private int PAC_LIFE;

	private final int PAC_MAN_SPEED = 1;
	private final int GHOST_SPEED = 1;
	private final int GAME_LIFES = 3;

	private Map map;
	private PacMan pacMan;
	private List<Ghost> ghosts;
	private List<Candy> candys;

	@Override
	public void enter(Object memento) {
		active = true;
		int numberOfGhosts = 3; // TODO: Find a way to pass numbers from welcome state

		map = new Map();
		initPacMan();
		initCandys();
		initScoreLife();
		initGhosts(numberOfGhosts);
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
		// TODO: add ghosts, ghosts collision and update lifes
		if (!detect_WALL_Collision(pacMan)) {
			
			ghosts.stream().forEach(ghost -> 
			ghost.update(detect_WALL_Collision(ghost), ghostHint(ghost), deltaTime));
			
			pacMan.update(deltaTime);
			
			if (detect_CANDY_Collision())
				SCORE = candys.size();
			if (detect_GHOST_Collision()) {
				PAC_LIFE--;
				if (PAC_LIFE == 0) // GAME OVER
					active = false;
				else 
					pacMan.moveToStartPosition(map.getPacStart());
			}
		}
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
		paintGame(frameBuffer);
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
				ghost.setGhostStateMachine(new SmartMachine(DIRECTION.DOWN));
			else
				ghost.setGhostStateMachine(new RandomMachine(DIRECTION.UP));
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

	// GRAPHICS

	private void paintGame(GameFrameBuffer frameBuffer) {
		Graphics2D g = frameBuffer.graphics();
		// Draw the map
		paintMap(g);
		// Draw Candys
		paintCandys(g);
		// Draw Pac-Man
		paintPacMan(g);
		// Draw Ghots
		paintGhost(g);
		// Draw Score
		paintScoreLife(frameBuffer);		
	}

	private void paintScoreLife(GameFrameBuffer fb) {
		String scoreText = String.format("Candys left: %d", SCORE);
		String lifeText = String.format("Life: %d", PAC_LIFE);
		int scoreTextWidth = fb.graphics().getFontMetrics().stringWidth(scoreText);
		int lifeTextWidth = fb.graphics().getFontMetrics().stringWidth(lifeText);
		fb.graphics().setColor(Color.white);
		fb.graphics().drawString(scoreText, (fb.getWidth() - scoreTextWidth) / 2 - 50, 15);
		fb.graphics().drawString(lifeText, (fb.getWidth() - lifeTextWidth) / 2 + 30, 15);
	}

	private void paintGhost(Graphics2D g) {
		int TILE_SIZE = map.getTILE_SIZE();
		ghosts.stream().forEach(ghost -> g.drawImage(ghost.getImage(), (int) ghost.getX() * TILE_SIZE,
				(int) ghost.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
	}

	private void paintCandys(Graphics2D g) {
		int TILE_SIZE = map.getTILE_SIZE();
		candys.stream().forEach(candy -> g.drawImage(candy.getImage(), candy.getX() * TILE_SIZE,
				candy.getY() * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2, null));
	}

	private void paintPacMan(Graphics2D g) {
		int TILE_SIZE = map.getTILE_SIZE();
		g.drawImage(pacMan.getImage(), (int) pacMan.getX() * TILE_SIZE, (int) pacMan.getY() * TILE_SIZE,
				TILE_SIZE, TILE_SIZE, null);
	}

	private void paintMap(Graphics2D g) {
		int TILE_SIZE = map.getTILE_SIZE();
		for (int i = 0; i < map.getMAZE_WIDTH(); i++) {
			for (int j = 0; j < map.getMAZE_HEIGHT(); j++) {
				if (map.getMap()[j][i] == map.getMark_WALL()) {
					g.setColor(map.getColor_WALL());
					g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
		}
	}	

	// COLLISION

	private boolean detect_WALL_Collision(Character c) {
		float step = c.getStep(), dx = 0, dy = 0;
		if (c.getDirection() == DIRECTION.UP.getValue()) {
			dy = -step;
		} else if (c.getDirection() == DIRECTION.DOWN.getValue()) {
			dy = step;
		} else if (c.getDirection() == DIRECTION.LEFT.getValue()) {
			dx = -step;
		} else if (c.getDirection() == DIRECTION.RIGHT.getValue()) {
			dx = step;
		}

		dx += c.getX();
		dy += c.getY();
		// return if next step will cause collision with a wall
		return map.getMap()[(int) dy][(int) dx] == map.getMark_WALL();
	}

	private boolean detect_CANDY_Collision() {
		int x = (int) pacMan.getX(), y = (int) pacMan.getY();
		if (map.getMap()[y][x] == map.getMark_CANDY()) {
			// remove candy from list
			candys.remove(candys.stream().filter(candy -> 
			candy.getX() == x && candy.getY() == y).findAny().orElse(null));
			return true;
		}
		return false;
	}

	private boolean detect_GHOST_Collision() {
		int x = (int) pacMan.getX(), y = (int) pacMan.getY();
		return ghosts.stream().filter(ghost -> 
		(int)ghost.getX() == x && (int)ghost.getY() == y).findAny().isPresent();
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
}
