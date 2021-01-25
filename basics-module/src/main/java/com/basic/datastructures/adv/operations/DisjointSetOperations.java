package com.basic.datastructures.adv.operations;

public interface DisjointSetOperations {

	/* Find & path compression logic:
	 * 	 1.Find: Find(x) follows the chain of parent pointers from x up the tree until it reaches a root element, whose parent is itself.
	 *     This root element is the representative member of the set to which x belongs, and may be x itself.
	 * 	 2.Path compression: Path compression flattens the structure of the tree by making every node point to the root whenever Find is
	 *     used on it. This is valid, since each element visited on the way to a root is part of the same set. The resulting flatter tree
	 *     speeds up future operations not only on these elements, but also on those referencing them.
	 */
	// Recursive Approach
	public int findParentRecursive(int node);

	// Iterative Approach
	public int findParentIterative(int node);

	public void createNode(int node);

	public boolean union(int n1, int n2);

	/*
	 * Union by rank always attaches the shorter tree to the root of the taller tree. Thus, the resulting tree is no taller than the 
	 * originals unless they were of equal height, in which case the resulting tree is taller by one node.
	 */
	public boolean unionByRank(int n1, int n2);

	/*
	 * Union by size always attaches the tree with fewer elements to the root of the tree having more elements.
	 */
	public boolean unionBySize(int n1, int n2);

}
