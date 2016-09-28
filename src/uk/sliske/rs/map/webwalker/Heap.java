package uk.sliske.rs.map.webwalker;

public class Heap {
	private Node[]		data;
	private final int	maxSize;
	private int			itemCount;

	public Heap(int maxSize) {
		this.maxSize = maxSize;
		data = new Node[maxSize];
		this.itemCount = 0;
	}

	public void put(Node item) {
		item.heapIndex = itemCount;
		data[itemCount] = item;
		sortUp(item);
		itemCount++;
	}

	public boolean isEmpty() {
		return itemCount <= 0;
	}

	public boolean contains(Node n) {
		if (n.heapIndex < 0 || n.heapIndex >= itemCount)
			return false;
		return data[n.heapIndex].equals(n);
	}

	private void sortUp(Node item) {
		int parentIndex = (item.heapIndex - 1) / 2;

		while (true) {
			Node parentItem = data[parentIndex];
			if (item.compareTo(parentItem) > 0) {
				swap(item, parentItem);
			} else {
				break;
			}

			parentIndex = (item.heapIndex - 1) / 2;
		}
	}

	public Node pop() {
		int t = 0;
		for (int i = 0; i < itemCount; i++) {
			if (data[i].fCost() < data[t].fCost()) {
				t = i;
				continue;
			}
		}
		Node res = data[t];

		swap(res, data[itemCount - 1]);

		itemCount--;

//		 Node res = data[0];
//		 itemCount--;
//		 data[0] = data[itemCount];
//		 data[0].heapIndex = 0;
//		 sortDown(data[0]);

		return res;
	}

	private void sortDown(Node item) {
		while (true) {
			int childIndexLeft = item.heapIndex * 2 + 1;
			int childIndexRight = item.heapIndex * 2 + 2;
			int swapIndex = 0;

			if (childIndexLeft < itemCount) {
				swapIndex = childIndexLeft;

				if (childIndexRight < itemCount) {
					if (data[childIndexLeft].compareTo(data[childIndexRight]) < 0) {
						swapIndex = childIndexRight;
					}
				}

				if (item.compareTo(data[swapIndex]) < 0) {
					swap(item, data[swapIndex]);
				} else {
					return;
				}

			} else {
				return;
			}

		}
	}

	private void swap(Node itemA, Node itemB) {
		data[itemA.heapIndex] = itemB;
		data[itemB.heapIndex] = itemA;
		int itemAIndex = itemA.heapIndex;
		itemA.heapIndex = itemB.heapIndex;
		itemB.heapIndex = itemAIndex;
	}

}
