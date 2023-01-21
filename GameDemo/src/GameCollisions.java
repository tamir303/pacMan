
public final class GameCollisions {

		private PacManGame game;
		
		public GameCollisions(PacManGame pmg) {
			game = pmg;
		}
		
		// COLLISION

		public boolean detect_WALL_Collision(Character c) {
			int x = (int) c.getX(), y = (int) c.getY();
			if (x == 0 || x == game.map.getMAZE_WIDTH() - 1) return true;
			if (y == 0 || y == game.map.getMAZE_HEIGHT() - 1) return true;
			
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
			return game.map.getMap()[(int) dy][(int) dx] == game.map.getMark_WALL();
		}

		public boolean detect_CANDY_Collision() {
			int x = (int) game.pacMan.getX(), y = (int) game.pacMan.getY();
			if (game.map.getMap()[y][x] == game.map.getMark_CANDY()) {
				// remove candy from list
				game.candys.remove(game.candys.stream().filter(candy -> 
				candy.getX() == x && candy.getY() == y).findAny().orElse(null));
				return true;
			}
			return false;
		}

		public boolean detect_GHOST_Collision() {
			float x = game.pacMan.getX(), y = game.pacMan.getY();
			return game.ghosts.stream().filter(ghost -> 
			Math.abs(ghost.getX() - x) < 1 && Math.abs(ghost.getY() - y) < 1).findAny().isPresent();
		}
}
