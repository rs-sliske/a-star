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

	public boolean put(Node item) {
		if (itemCount >= maxSize) {
			return false;
		}

		data[itemCount] = item;
		item.heapIndex = itemCount;
		itemCount++;

		sortUp(item);

		return true;
	}
	
	public boolean isEmpty(){
		return itemCount <= 0;
	}
	
	public boolean contains(Node n){
		if(n.heapIndex < 0 || n.heapIndex >= itemCount)
			return false;
		return data[n.heapIndex].equals(n);
	}

	private void sortUp(Node item) {
		while (true) {
			int parentIndex = (item.heapIndex - 1) / 2;
			if(parentIndex <= 0)
				break;
			Node parentItem = data[parentIndex];
			if (item.compareTo(parentItem) < 0) {
//				System.out.printf("swapping node at %s with node at %s\n", item, parentItem);
				swap(item, parentItem);
			} else {
				break;
			}
		}
	}

	public Node pop() {
		Node res = data[0];

		itemCount--;
		data[0] = data[itemCount];

		sortDown(data[0]);

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

	private void swap(Node first, Node second) {
		data[first.heapIndex] = second;
		data[second.heapIndex] = first;
		int temp = first.heapIndex;
		first.heapIndex = second.heapIndex;
		second.heapIndex = temp;
	}

}
