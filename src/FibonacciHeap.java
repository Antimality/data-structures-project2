/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap {
	public HeapNode min;
	private HeapNode first;
	private int h_size;
	private int t_num;
	private int numLinks;
	private int numCuts;

	/**
	 *
	 * Constructor to initialize an empty heap.
	 *
	 */
	public FibonacciHeap() {
		min = null;
		first = null;
		h_size = 0;
		t_num = 0;
		numLinks = 0;
		numCuts = 0;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) {
		HeapNode node = new HeapNode(key, info);

		if (first == null) {
			// The heap is empty - create a new heap with a single node.
			min = node;
			first = node;
			t_num = 1;
		} else {
			// Add the new node to the root list.
			addToRootList(node);
			// Update min pointer
			if (node.key < min.key) {
				min = node;
			}
		}
		h_size++;

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
		min = min.next;
		detatchNode(min);

		if (min == min.next) {
			// The heap is empty
			min = null;
			first = null;
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
		HeapNode[] buckets = new HeapNode[t_num];
		HeapNode current = first;
		do {
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

			current = current.next;
		} while (current != first);
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
		removeFromLinkedList(child);
		// Attatch child to parent
		if (parent.child == null) {
			parent.child = child;
			child.next = child;
			child.prev = child;
		} else {
			addToLinkedList(parent.child, child);
		}

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
		if (x.parent == null|| x.parent.key<= x.key) { 
			// The decrease is legal with the heap logic 
			return;
		}
		else  {
			cascadeCut(x);
		}
	}
	/**
	 * 
	 * Handle the cutting process.
	 *
	 */
	private void cascadeCut(HeapNode x) {
		if (x.parent == null) 
			//Terminate cascade when we reach root
			return;
		HeapNode parent = x.parent;
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
		addToRootList(x);
		// The mark of a new tree root is always false
		x.mark = false; 
		if (x.parent.mark == false && x.parent.parent != null) {
			// We mark only non roots
			x.parent.mark = true;
			return;
		}
		else {
			cascadeCut(parent);
		}
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
		cascadeCut(x);
		if (x.child != null) {
			// Add the children of x to the root list.
			HeapNode current = x.child;
			do {
				current.parent = null;
				numCuts++;
				addToRootList(current);
				current = current.next;
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
		if (heap2.first == null) {
			// Do nothing
		} else if (first == null) {
			first = heap2.first;
			min = heap2.min;
		} else {
			// Concatinate root lists
			first.prev.next = heap2.first;
			heap2.first.prev.next = first;
			// Update min pointer
			if (heap2.min.key < min.key) {
				min = heap2.min;
			}
		}

		// Update counters
		h_size += heap2.h_size;
		t_num += heap2.t_num;
		numLinks += heap2.numLinks;
		numCuts += heap2.numCuts;
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return h_size;
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees() {
		return t_num;
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
		addToLinkedList(node, first);
		t_num++;
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
	}
}
