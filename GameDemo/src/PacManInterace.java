import java.awt.Graphics2D;

public interface PacManInterace {
	boolean detect_WALL_Collision();
	void detect_CANDY_Collision();
	void drawMap(Graphics2D g, int[][] map);
}
