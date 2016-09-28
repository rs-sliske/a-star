package uk.sliske.rs.map.webwalker;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Map {

	private Node[]		nodes;
	private final int	width;
	private final int	height;

	public Map(int width, int height, int ratio) {
		this.width = width;
		this.height = height;
		nodes = new Node[width * height];
		Random rand = new Random();

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i % width, i / width, rand.nextInt(ratio) != 0);
		}
		nodes[0].walkable = true;
		nodes[nodes.length - 1].walkable = true;

	}

	public Map(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		nodes = new Node[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int p = image.getRGB(x, y);
				int r = (p & 0xff0000) >> 16;
//				int g = (p & 0xff00) >> 8;
//				float fg = (float) g;
//				float w = 1 - (fg / 256);
//				System.out.printf("p:%x\tr:%x\tg:%x\n", p, r, g);
				nodes[x + y * width] = new Node(x, y, r != 0x00);
			}
		
			continue;
		}
	}

	public Node get(int x, int y) {
		if (x >= width || y >= height || x < 0 || y < 0)
			return null;
		return nodes[x + y * width];
	}

	public HashSet<Node> neighbours(Node check) {
		int x = check.x;
		int y = check.y;

		HashSet<Node> res = new HashSet<Node>();

		Node left = get(x - 1, y);
		Node right = get(x + 1, y);
		Node top = get(x, y - 1);
		Node bottom = get(x, y + 1);

		res.add(left);
		res.add(right);
		res.add(top);
		res.add(bottom);

		if (left != null && left.walkable) {
			res.add(get(x - 1, y - 1));
			res.add(get(x - 1, y + 1));
		}

		if (right != null && right.walkable) {
			res.add(get(x + 1, y - 1));
			res.add(get(x + 1, y + 1));
		}

		if (top != null && top.walkable) {
			res.add(get(x - 1, y - 1));
			res.add(get(x + 1, y - 1));
		}

		if (bottom != null && bottom.walkable) {
			res.add(get(x - 1, y + 1));
			res.add(get(x + 1, y + 1));
		}

		res.remove(null);

		return res;

	}

	private int distanceBetween(Node start, Node end) {
		int dx = Math.abs(start.x - end.x);
		int dy = Math.abs(start.y - end.y);

		int min = Math.min(dx, dy);
		int max = Math.max(dx, dy);

		return (min * 14) + ((max-min)  * 10);
	}

	public List<Node> findPath(Node start, Node end) {
				
		for (Node n : nodes) {
			n.hCost(distanceBetween(n, end));
		}

		HashSet<Node> closed = new HashSet<>();
		Queue<Node> open = new PriorityQueue<>((int)Math.sqrt(nodes.length), new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if(o1.fCost() != o2.fCost())
					return Integer.compare(o1.fCost(), o2.fCost());
				
				return Integer.compare(o1.hCost(), o2.hCost());
			}
		}) ;

		open.add(start);

		while (!open.isEmpty()) {
			Node current = open.poll();
			if (current.equals(end))
				return retracePath(start, current);

			 System.out.printf("checking -> %s : %d\n", current, current.fCost());

			closed.add(current);

			for (Node n : neighbours(current)) {
				if (!n.walkable || closed.contains(n)) {
					continue;
				}

				int gscore = current.gCost() + distanceBetween(current, n);
				if (!open.contains(n)){
					open.add(n);
				}else{
					if (gscore >= n.gCost())
						continue;
				}
				n.parent(current);
				n.gCost(gscore);
			}
		}

		return null;
	}

	private List<Node> retracePath(Node start, Node end) {
		Stack<Node> temp = new Stack<>();

		Node current = end;
		while (!current.equals(start)) {
			temp.add(current);
			current = current.parent();
		}

		Node last = null;
		ArrayList<Node> res = new ArrayList<>();
		for (Node n : temp) {
			if (last == null || distanceBetween(n, last) >= 1) {
				res.add(n);
				last = n;
			}
		}
		res.add(start);
		return res;
	}

	public Node[] nodes() {
		return nodes;
	}
}
