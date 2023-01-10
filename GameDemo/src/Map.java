import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;

public class Map {
	
	private final int MAZE_WIDTH = 27;
	private final int MAZE_HEIGHT = 20;
	
	private final Color Color_EMPTY = Color.BLACK;
	private final Color Color_WALL = Color.BLUE;
	private final Color Color_CANDY = Color.RED;
	
	private final int Mark_EMPTY = 0;
	private final int Mark_WALL = 1;
	private final int Mark_CANDY = 2;
	
	final int TILE_SIZE = 24;
	
	private final AtomicIntegerArray pacStartLocation = new AtomicIntegerArray(new int[] 
			{1, MAZE_HEIGHT - 2});
	
	private final AtomicIntegerArray ghostOneStartLoc = new AtomicIntegerArray(new int[] 
			{1, 1});
	
	private final AtomicIntegerArray ghostTwoStartLoc = new AtomicIntegerArray(new int[] 
			{1, MAZE_WIDTH - 2});
	
	private final AtomicIntegerArray ghostThreeStartLoc = new AtomicIntegerArray(new int[] 
			{6, MAZE_WIDTH - 9});
	
	private final AtomicIntegerArray ghostFourStartLoc = new AtomicIntegerArray(new int[] 
			{6 , 8});
	
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
	
	public int[][] getMap() {
		return mat_map;
	}
	
	public ArrayList<AtomicIntegerArray> getGhostsStart() {
		return new ArrayList<AtomicIntegerArray>(Arrays.asList(ghostOneStartLoc, 
				ghostTwoStartLoc, ghostThreeStartLoc, ghostFourStartLoc));
	}
	
	public AtomicIntegerArray getPacStart() {
		return pacStartLocation;
	}
	
	public Color getColor_EMPTY() {
		return Color_EMPTY;
	}

	public Color getColor_WALL() {
		return Color_WALL;
	}

	public Color getColor_CANDY() {
		return Color_CANDY;
	}

	public int getMAZE_WIDTH() {
		return MAZE_WIDTH;
	}

	public int getMAZE_HEIGHT() {
		return MAZE_HEIGHT;
	}

	public int getTILE_SIZE() {
		return TILE_SIZE;
	}

	public int getMark_EMPTY() {
		return Mark_EMPTY;
	}

	public int getMark_WALL() {
		return Mark_WALL;
	}

	public int getMark_CANDY() {
		return Mark_CANDY;
	}
	
	public ArrayList<AtomicIntegerArray> getInitialCandysXY() {
		ArrayList<AtomicIntegerArray> listOfCandysLocations = new ArrayList<AtomicIntegerArray>();
		for (int i = 0; i < getMAZE_WIDTH(); i++)
			for (int j = 0; j < getMAZE_HEIGHT(); j++) 
				if (mat_map[j][i] == getMark_CANDY())
					listOfCandysLocations.add(new AtomicIntegerArray(new int[] {j,i}));
		return listOfCandysLocations;
	}
}
