import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

public class PacManGame extends GameState implements PacManInterace {

	private enum e_direction {
		UP, DOWN, LEFT, RIGHT
	}

	boolean active;

	private final int MAZE_WIDTH = 27;
	private final int MAZE_HEIGHT = 20;
	private final int TILE_SIZE = 24;

	private final int EMPTY = 0;
	private final int WALL = 1;
	private final int CANDY = 2;
	
	private int SCORE = 0;

	public static final float PAC_MAN_SPEED = 1;

	private int[][] mat_map = { 
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 1, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 1 },
			{ 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1 },
			{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 2, 0, 0, 1 },
			{ 1, 0, 0, 1, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 1, 0, 2, 1 },
			{ 1, 0, 2, 0, 2, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
			{ 1, 0, 2, 0, 0, 2, 0, 0, 2, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 2, 0, 0, 1, 1, 0, 0, 0, 2, 0, 1, 1, 1, 0, 0, 2, 0, 0, 1, 1, 0, 0, 0, 2, 1 },
			{ 1, 0, 0, 0, 0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	//private BSPTree tree_map;

	private PacMan pacMan;
	private List<Ghost> ghosts;

	@Override
	public void enter(Object memento) {
		active = true;
		// Initialize Pac-Man, ghosts and BSPTree
		//tree_map = new BSPTree(mat_map);
		pacMan = new PacMan(MAZE_WIDTH / 2, MAZE_HEIGHT / 2 - 2, PAC_MAN_SPEED);
		// TODO: initialize ghosts
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

	public boolean detect_WALL_Collision() { 
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
		
		return mat_map[(int)dy][(int)dx] == WALL;
	}
	public void detect_CANDY_Collision() {
		if (this.mat_map[(int) pacMan.getY()][(int) pacMan.getX()] == CANDY)
			this.SCORE +=1;
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
		//drawMapTree(g, this.tree_map.root); // TODO: MAKE BSP WORK
		drawMap(g, this.mat_map);

		// Draw Pac-Man and the ghosts
		g.drawImage(pacMan.getImage(),(int) pacMan.getX() * TILE_SIZE, (int)pacMan.getY() * TILE_SIZE, TILE_SIZE,
				TILE_SIZE, null);

//		for (Ghost ghost : ghosts) {
//			g.drawImage(ghost.getImage(), ghost.getX() * TILE_SIZE, ghost.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE,
//					null);
	}

	private void drawMapTree(Graphics2D g, Node node) {
		if (node == null)
			return;
		
		// If this node is a leaf node, draw the tile it represents
		if (node.left == null && node.right == null) {
			if (node.value == BSPTree.TILE_WALL) {
				// Draw a wall tile
				g.setColor(Color.BLUE);
				g.fillRect(node.x1 * TILE_SIZE, node.y1 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			} else {
				// Draw an empty space
				g.setColor(Color.BLACK);
				g.fillRect(node.x1 * TILE_SIZE, node.y1 * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		} else {
			// This node is an internal node, so draw its children
			drawMapTree(g, node.left);
			drawMapTree(g, node.right);
		}
	}

	public void drawMap(Graphics2D g, int[][] map) {
		for (int i = 0; i < MAZE_WIDTH; i++) {
			for (int j = 0; j < MAZE_HEIGHT; j++) {
				if (map[j][i] == WALL) {
					g.setColor(Color.BLUE);
					g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);	
				}
				else if(map[j][i] == CANDY) {
					g.setColor(Color.RED);
					g.fillOval(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2);
				}
			}
		}
	}
}
