public class Node {
    // The left and right children of this node
    Node left;
    Node right;
    
    // The tile represented by this node (e.g. TILE_EMPTY or TILE_WALL)
    int value;
    
    // The position and size of the node
    int x1, y1, x2, y2;
    
    // Constructor for a leaf node (representing a single tile)
    public Node(int x1, int y1, int x2, int y2, int value) {
        this.value = value;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}
