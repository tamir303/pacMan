import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;

public class PacManGame extends GameState {

	boolean active;

	private int score;
	private int lifes;

	public final int PAC_MAN_SPEED = 1;
	public final int GHOST_SPEED = 1;
	public final int GAME_LIFES = 3;

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
		if (!detect_WALL_Collision()) {
			pacMan.update(deltaTime);
			if (detect_CANDY_Collision())
				score = candys.size();
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

	// INITS

	private void initGhosts(int numOfGhosts) {
		this.ghosts = new ArrayList<Ghost>();
		Iterator<AtomicIntegerArray> it = this.map.getGhostsStart().iterator();
		while (it.hasNext() && --numOfGhosts >= 0) {
			AtomicIntegerArray loc = it.next();
			ghosts.add(new Ghost(loc.get(1), loc.get(0), GHOST_SPEED));
		}
	}

	private void initScoreLife() {
		this.score = candys.size();
		this.lifes = GAME_LIFES;
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

	private void paintScoreLife(GameFrameBuffer fb) {
		String scoreText = String.format("Score: %d", score);
		String lifeText = String.format("Life: %d", lifes);
		int scoreTextWidth = fb.graphics().getFontMetrics().stringWidth(scoreText);
		int lifeTextWidth = fb.graphics().getFontMetrics().stringWidth(lifeText);
		fb.graphics().setColor(Color.white);
		fb.graphics().drawString(scoreText, (fb.getWidth() - scoreTextWidth) / 2 - 30, 15);
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

	private boolean detect_WALL_Collision() {
		float step = pacMan.getStep(), dx = 0, dy = 0;
		if (pacMan.getDirection() == DIRECTION.UP.getValue()) {
			dy = -step;
		} else if (pacMan.getDirection() == DIRECTION.DOWN.getValue()) {
			dy = step;
		} else if (pacMan.getDirection() == DIRECTION.LEFT.getValue()) {
			dx = -step;
		} else if (pacMan.getDirection() == DIRECTION.RIGHT.getValue()) {
			dx = step;
		}

		dx += pacMan.getX();
		dy += pacMan.getY();
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

	private void detect_GHOST_Collision() {

	}
}
