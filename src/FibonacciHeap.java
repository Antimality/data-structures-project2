/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap {
	public HeapNode min;
	private HeapNode rootList;
	private int numNodes;
	private int numTrees;
	private int numLinks;
	private int numCuts;

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) {
		HeapNode node = new HeapNode(key, info);

		// Add the new node to the root list.
		addToRootList(node);
		// Update min pointer
		if (node.key < min.key) {
			min = node;
		}
		numNodes++;

		return node;
	}

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin() {
		return min;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() {
		if (min == null) {
			return;
		}
		detatchNode(min);

		if (rootList == null) {
			// The heap is empty
			min = null;
		} else {
			// Temporarily move min pointer to the next node in the root list
			min = min.next;
			// Consolidate will fix the min pointer
			consolidate();
		}

	}

	/**
	 * 
	 * Consolidates the root list so at most one tree has each rank.
	 * 
	 */
	private void consolidate() {
		HeapNode[] buckets = new HeapNode[numNodes];
		HeapNode current = rootList;
		do {
			HeapNode next = current.next;
			int rank = current.rank;
			HeapNode merged = current;
			// Recursively merge trees until there are no more bucket conflicts
			while (buckets[rank] != null) {
				// Merge with bucket
				merged = merge(merged, buckets[rank]);
				// Empty previous bucket
				buckets[rank++] = null;
			}
			buckets[rank] = merged;

			// TODO: What if both next and current get pulled under someone else?
			current = next.isRoot() ? next : current.next;
		} while (current != rootList);

		// Update min pointer
		for (HeapNode node : buckets) {
			if (node != null && node.key < min.key) {
				min = node;
			}
		}
	}

	/**
	 * 
	 * Merges two trees of the same rank. Returns the merged tree.
	 * 
	 */
	private HeapNode merge(HeapNode x, HeapNode y) {
		HeapNode parent = x.key <= y.key ? x : y;
		HeapNode child = x.key <= y.key ? y : x;

		// Remove child from the root list
		removeFromRootList(child);
		// Attatch child to parent
		if (parent.child == null) {
			parent.child = child;
			child.next = child;
			child.prev = child;
		} else {
			addToLinkedList(child, parent.child);
			if (child.rank > parent.child.rank)
				parent.child = child;
		}
		child.parent = parent;

		parent.rank++;
		numLinks++;
		return parent;
	}

	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap.
	 * 
	 */
	public void decreaseKey(HeapNode x, int diff) {
		x.key = x.key - diff;
		if (x.isRoot() || x.parent.key <= x.key) {
			// The decrease is legal with the heap logic
			return;
		} else {
			cascadeCut(x);
		}
	}

	/**
	 * 
	 * Handle the cutting process.
	 *
	 */
	private void cascadeCut(HeapNode x, boolean delete) {
		if (x.isRoot())
			// Terminate cascade when we reach root
			return;
		HeapNode parent = x.parent;
		// Detach x from its parent
		if (parent.child == x) {
			if (x.next == x) {
				// Only child
				parent.child = null;
			} else {
				// Connect parent to sibling
				parent.child = x.next;
			}
		}
		numCuts++;
		x.parent.rank--;
		x.parent = null;

		// Detach node from its siblings
		removeFromLinkedList(x);

		if (!delete) {
			addToRootList(x);
			// The mark of a new tree root is always false
			x.mark = false;
		}

		// Mark or cascade
		if (parent.mark == false && !parent.isRoot()) {
			// We mark only non roots
			parent.mark = true;
			return;
		} else {
			cascadeCut(parent);
		}
	}

	private void cascadeCut(HeapNode x) {
		cascadeCut(x, false);
	}

	/**
	 * 
	 * Delete the x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		if (x == min) {
			deleteMin();
		} else {
			detatchNode(x);
		}
	}

	/**
	 * 
	 * Detaches a node from its parent and siblings. Returns the child of the node.
	 * 
	 */
	private void detatchNode(HeapNode x) {
		numNodes--;
		if (x.isRoot()) {
			// x is a root
			removeFromRootList(x);
		} else {
			cascadeCut(x, true);
		}
		if (x.child != null) {
			// Add the children of x to the root list.
			HeapNode current = x.child;
			do {
				HeapNode next = current.next;
				current.parent = null;
				numCuts++;
				// removeFromLinkedList(current);
				addToRootList(current);
				current = next;
			} while (current != x.child);
		}
	}

	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks() {
		return numLinks;
	}

	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts() {
		return numCuts;
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		if (heap2.rootList == null) {
			// Do nothing
		} else if (rootList == null) {
			rootList = heap2.rootList;
			min = heap2.min;
		} else {
			HeapNode first1 = rootList;
			HeapNode last1 = rootList.prev;
			HeapNode first2 = heap2.rootList;
			HeapNode last2 = heap2.rootList.prev;
			// Concatinate root lists
			last1.next = first2;
			first1.prev = last2;
			last2.next = first1;
			first2.prev = last1;
			// Update min pointer
			if (heap2.min.key < min.key) {
				min = heap2.min;
			}
		}

		// Update counters
		numNodes += heap2.numNodes;
		numTrees += heap2.numTrees;
		numLinks += heap2.numLinks;
		numCuts += heap2.numCuts;
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return numNodes;
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees() {
		return numTrees;
	}

	/**
	 * 
	 * Add a node to a linked list.
	 * 
	 */
	private void addToLinkedList(HeapNode node, HeapNode list) {
		// Add the new node to the root list.
		node.next = list;
		node.prev = list.prev;
		list.prev.next = node;
		list.prev = node;
	}

	/**
	 * 
	 * Add a node to the root list.
	 * 
	 */
	private void addToRootList(HeapNode node) {
		if (rootList == null) {
			// The heap is empty - create a new heap with a single node.
			min = node;
			rootList = node;
			node.next = node;
			node.prev = node;
		} else {
			addToLinkedList(node, rootList);
		}
		numTrees++;
	}

	/**
	 * 
	 * Remove a node from a linked list.
	 * 
	 */
	private void removeFromLinkedList(HeapNode node) {
		// Remove the node from the root list.
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	private void removeFromRootList(HeapNode node) {
		if (node == rootList) {
			rootList = node.next != node ? node.next : null;
		}
		numTrees--;
		removeFromLinkedList(node);
	}

	/**
	 * 
	 * Class implementing a node in a Fibonacci Heap.
	 * 
	 */
	public static class HeapNode {
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public boolean mark;

		public HeapNode(int key, String info) {
			this.key = key;
			this.info = info;
			child = null;
			next = this;
			prev = this;
			parent = null;
			rank = 0;
			mark = false;
		}

		private boolean isRoot() {
			return parent == null;
		}

		@Override
		public String toString() {
			return "(" + this.key + ", \"" + this.info + "\", " + this.rank + ", " + this.mark + ")";
		}
	}

	// TODO: REMOVE BEFORE SUBMISSION? (so we don't need to explain it)
	public void printHeap() {
		if (this.min == null) {
			System.out.println("The heap is empty.");
			return;
		}
		System.out.println(String.format("Fibonacci Heap w/ %d nodes & %d trees", this.numNodes, this.numTrees));

		FibonacciHeap.HeapNode start = this.rootList;
		FibonacciHeap.HeapNode current = this.rootList;
		int treeNumber = 1;

		do {
			System.out.println("Tree " + treeNumber + ":");
			printTree(current, "", true);
			current = current.next;
			treeNumber++;
		} while (current != start);
	}

	private void printTree(FibonacciHeap.HeapNode node, String prefix, boolean isLast) {
		if (node == null)
			return;

		// Print the current node as (key, "value")
		System.out.print(prefix);
		System.out.print(isLast ? "└── " : "├── ");
		System.out.println(node.toString());

		// Prepare prefix for the next level
		prefix += isLast ? "    " : "│   ";

		// Recursively print children
		if (node.child != null) {
			FibonacciHeap.HeapNode child = node.child;
			do {
				printTree(child, prefix, child.next == node.child);
				child = child.next;
			} while (child != node.child);
		}
	}

	// TODO: REMOVE BEFORE SUBMISSION
	public static void main(String[] args) {
		// FibonacciHeap heap = new FibonacciHeap();
		// heap.insert(2, "two");
		// heap.insert(3, "three");
		// HeapNode x4 = heap.insert(4, "four");
		// heap.insert(5, "five");
		// heap.insert(1, "one");
		// heap.deleteMin();
		// heap.printHeap();

		// heap.decreaseKey(x4, 3);
		// heap.printHeap();

		// FibonacciHeap heap2 = new FibonacciHeap();
		// heap2.insert(6, "six");
		// heap2.insert(7, "seven");
		// heap2.insert(8, "eight");
		// heap2.insert(9, "nine");
		// heap2.deleteMin();
		// heap2.printHeap();

		// heap.meld(heap2);
		// heap.printHeap();

		// heap.deleteMin();
		// heap.printHeap();

		// heap.insert(9, "Nine");
		// heap.insert(2, "Two");
		// heap.insert(3, "Three");
		// heap.insert(1, "One");
		// heap.insert(7, "Seven");
		// heap.insert(5, "Five");
		// heap.insert(6, "Six");
		// heap.insert(8, "Eight");
		// heap.insert(10, "Ten");
		// heap.insert(4, "Four");

		// for (int i = 1; i <= 10; i++) {
		// // heap.printHeap();
		// int min = heap.findMin().key;
		// assert min == i : "Wrong minimum";
		// System.err.println("Min: " + min);
		// heap.deleteMin();
		// }
	}
}
