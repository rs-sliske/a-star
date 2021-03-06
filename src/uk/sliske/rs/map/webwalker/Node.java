package uk.sliske.rs.map.webwalker;

public class Node {

	private Node		parent;
	private int			h;
	private int			g;

	public final int	x;
	public final int	y;

	int					heapIndex;
	boolean				walkable;

	public Node(int x, int y, boolean walkable) {
		this.x = x;
		this.y = y;
		this.walkable = walkable;
		g= 0x8ff00000;
	}

	public int hCost() {
		return h;
	}

	public void hCost(int h) {
		this.h = h;
	}

	public int gCost() {
		return g;
	}

	public void gCost(int g) {
		this.g = g;
	}

	public int fCost() {
		return g + h;
	}

	public void parent(Node parent) {
		this.parent = parent;
	}

	public Node parent() {
		return parent;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Node))
			return false;
		Node o = (Node) other;
		return o.x == this.x && o.y == this.y;
	}

	public String toString() {
		return x + " : " + y;
	}

	public int hashCode() {
		return x << 16 | y;

	}

}
