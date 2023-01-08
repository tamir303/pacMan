public class BSPTree {
    // The root node of the tree
    Node root;
    
    // Constants representing different types of tiles on the map
    public static final int TILE_EMPTY = 0;
    public static final int TILE_WALL = 1;
    
    private int borderX,borderY;
    
    // Constructor to build the tree from the map
    public BSPTree(int[][] map) {
    	this.borderX = map[0].length;
    	this.borderY = map.length;
        root = buildTree(map, 0, 0, map[0].length, map.length);
    }
    
    private Node buildTree(int[][] pacManMap, int x1, int y1, int x2, int y2) {
    	
    	if (x1 < 0 || x1 >= borderX || y1 < 0 || y1 >= borderY)
    		return null;
    	
        Node node = new Node(x1, y1, x2, y2, pacManMap[y1][x1]);

        if (x1 == x2 && y1 == y2) {
            return node;
        }

        if (y1 != y2) {
            int midY = (y1 + y2) / 2;
            node.left = buildTree(pacManMap, x1, y1, x2, midY);
            node.right = buildTree(pacManMap, x1, midY + 1, x2, y2);
        } else {
            int midX = (x1 + x2) / 2;
            node.left = buildTree(pacManMap, x1, y1, midX, y2);
            node.right = buildTree(pacManMap, midX + 1, y1, x2, y2);
        }

        return node;
    }
    
}
