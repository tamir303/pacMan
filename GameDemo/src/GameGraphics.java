import java.awt.Color;
import java.awt.Graphics2D;

public final class GameGraphics {

	private PacManGame game;
	
	public GameGraphics(PacManGame pmg) {
		game = pmg;
	}
	
	// GRAPHICS

	public void paintGame(GameFrameBuffer frameBuffer) {
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

	public void paintScoreLife(GameFrameBuffer fb) {
		String scoreText = String.format("Candys left: %d", game.SCORE);
		String lifeText = String.format("Life: %d", game.PAC_LIFE);
		int scoreTextWidth = fb.graphics().getFontMetrics().stringWidth(scoreText);
		int lifeTextWidth = fb.graphics().getFontMetrics().stringWidth(lifeText);
		fb.graphics().setColor(Color.white);
		fb.graphics().drawString(scoreText, (fb.getWidth() - scoreTextWidth) / 2 - 50, 15);
		fb.graphics().drawString(lifeText, (fb.getWidth() - lifeTextWidth) / 2 + 30, 15);
	}

	public void paintGhost(Graphics2D g) {
		int TILE_SIZE = game.map.getTILE_SIZE();
		game.ghosts.stream().forEach(ghost -> g.drawImage(ghost.getImage(), (int) ghost.getX() * TILE_SIZE,
				(int) ghost.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
	}

	public void paintCandys(Graphics2D g) {
		int TILE_SIZE = game.map.getTILE_SIZE();
		game.candys.stream().forEach(candy -> g.drawImage(candy.getImage(), candy.getX() * TILE_SIZE,
				candy.getY() * TILE_SIZE, TILE_SIZE / 2, TILE_SIZE / 2, null));
	}

	public void paintPacMan(Graphics2D g) {
		int TILE_SIZE = game.map.getTILE_SIZE();
		g.drawImage(game.pacMan.getImage(), (int) game.pacMan.getX() * TILE_SIZE, (int) game.pacMan.getY() * TILE_SIZE,
				TILE_SIZE, TILE_SIZE, null);
	}

	public void paintMap(Graphics2D g) {
		int TILE_SIZE = game.map.getTILE_SIZE();
		for (int i = 0; i < game.map.getMAZE_WIDTH(); i++) {
			for (int j = 0; j < game.map.getMAZE_HEIGHT(); j++) {
				if (game.map.getMap()[j][i] == game.map.getMark_WALL()) {
					g.setColor(game.map.getColor_WALL());
					g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
		}
	}		
}
