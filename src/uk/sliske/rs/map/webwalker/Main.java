package uk.sliske.rs.map.webwalker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import uk.sliske.util.IO.WebIO;
import uk.sliske.util.graphics.Window;

public class Main {

	
	public static void main(String... args) {
		// for (int i = 0; i < 10; i++)
		// testSet(i + 1);

		BufferedImage image;
		try {
			image = WebIO.loadBImageFromWeb("https://sliske.uk/img/captures/728355426ab26e1.png");
			
			
			Map map = new Map(image);
			int width = image.getWidth();
			int height = image.getHeight();
			long t = System.currentTimeMillis();
			final List<Node> nodes = map.findPath(map.get(0, 0), map.get(width - 1, height - 1));

			long e = System.currentTimeMillis();
			new Window("path", "Pathfinding", image.getWidth(), image.getHeight() + 30) {
				private static final long	serialVersionUID	= 1L;

				public void paint(Graphics g) {
					g.drawImage(image, 0, 0, null);
					Node last = null;
					Graphics2D g2 = (Graphics2D) g;
				    g2.setStroke(new BasicStroke(3));
					g.setColor(Color.red);
					for(Node n : nodes){
						if(last != null){
							g2.drawLine(last.x, last.y, n.x, n.y);							
						}
						last = n;
					}
				}
				
			};

			System.out.printf("%7d nodes : path found in %d ms\n", width * height, e - t);
			
			

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// test(4500, 4500, 3);
		// testSet(3);

	}
	
	static void paint(Graphics g){
		
	}

	static void testSet(int ratio) {
		System.out.println("testing with a blocked:walkable tile ratio of 1:" + ratio);
		test(10, 10, ratio);
		test(50, 50, ratio);
		test(100, 100, ratio);
		test(250, 250, ratio);
		test(500, 500, ratio);
		test(1000, 1000, ratio);
		test(1500, 1500, ratio);
		test(1750, 1750, ratio);
		test(2000, 2000, ratio);
		test(3000, 3000, ratio);
		test(3000, 3333, ratio);
		test(3500, 3500, ratio);
		System.out.println();
	}

	static void test(int width, int height) {
		test(width, height, 1);
	}

	static void test(int width, int height, int ratio) {
		Map map = new Map(width, height, ratio);
		long t = System.currentTimeMillis();
		map.findPath(map.get(0, 0), map.get(width - 1, height - 1));

		long e = System.currentTimeMillis();

		System.out.printf("%7d nodes : path found in %d ms\n", width * height, e - t);
	}

}
