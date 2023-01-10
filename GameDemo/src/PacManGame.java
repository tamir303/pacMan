import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class PacManGame extends GameState{

	boolean active;

	private enum e_direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	private int SCORE = 0;

	public final int PAC_MAN_SPEED = 1;
	public final int GHOST_SPEED = 1;

	private Map map;
	private PacMan pacMan;
	private List<Ghost> ghosts;
	private HashMap<AtomicIntegerArray, Candy> candys;

	@Override
	public void enter(Object memento) {
		active = true;
		map = new Map();
		pacMan = new PacMan(map.getPacStartX(), map.getPacStartY(), PAC_MAN_SPEED);
		initCandys();
		// TODO: initialize ghosts
	}

	private void initCandys() {
		Iterator<AtomicIntegerArray> it = this.map.getInitialCandysXY().iterator();
		this.candys = new HashMap<AtomicIntegerArray, Candy>();
		while(it.hasNext()) {
			AtomicIntegerArray loc = it.next();
			candys.put(loc, new Candy(loc.get(1), loc.get(0)));
		}
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
		if(!detect_WALL_Collision()) { // TODO: MAKE COLLISION HANDLER
			pacMan.update(deltaTime);
			detect_CANDY_Collision();
		}
	}

	@Override
	public void processKeyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_UP) {
			pacMan.setDirection(e_direction.UP.ordinal());
		} else if (keyCode == KeyEvent.VK_DOWN) {
			pacMan.setDirection(e_direction.DOWN.ordinal());
		} else if (keyCode == KeyEvent.VK_LEFT) {
			pacMan.setDirection(e_direction.LEFT.ordinal());
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			pacMan.setDirection(e_direction.RIGHT.ordinal());
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
		//paintGhost(g);
	}
	
	private void paintGhost(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	private void paintCandys(Graphics2D g) {
		int TILE_SIZE = map.getTILE_SIZE();
		candys.values().stream().forEach(candy -> g.drawImage(candy.getImage(), candy.getX() * TILE_SIZE,
				candy.getY() * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2, null));
		
	}
	
	private void paintPacMan(Graphics2D g) {
		int TILE_SIZE = map.getTILE_SIZE();
		g.drawImage(pacMan.getImage(),(int) pacMan.getX() * TILE_SIZE
				, (int)pacMan.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
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
	
	private boolean detect_WALL_Collision() { 
		float step = pacMan.getStep(), dx = 0, dy =0;
		if (pacMan.getDirection() == e_direction.UP.ordinal()) {
			dy = -step;
		} else if (pacMan.getDirection() == e_direction.DOWN.ordinal()) {
			dy = step;
		} else if (pacMan.getDirection() == e_direction.LEFT.ordinal()) {
			dx = -step;
		} else if (pacMan.getDirection() == e_direction.RIGHT.ordinal()) {
			dx = step;
		}
		
		dx += pacMan.getX(); dy += pacMan.getY();
		return map.getMap()[(int)dy][(int)dx] == map.getMark_WALL();
	}
	
	private void detect_CANDY_Collision() {
		if (map.getMap()[(int) pacMan.getY()][(int) pacMan.getX()] == map.getMark_CANDY()) {
			this.SCORE +=1;
		}
	}
	
	private void detect_GHOST_Collision() {}
}
