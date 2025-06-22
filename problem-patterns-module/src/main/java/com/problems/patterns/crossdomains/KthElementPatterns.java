package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.common.model.Cell;
import com.common.model.TreeNode;
import com.common.utilities.Utils;
import com.problems.patterns.ds.MatrixPatterns;

/* 
 * Kth Element Vs K Elements:
 *  Kth Element: Find kth element from array, list or 2D array. Eg: smallest, largest etc
 *  K Elements: Find k elements from array, list or 2D array. Eg: Top K elements, K closest elements etc
 *  
 *  
 * This class covers finding the Kth element and solved using 
 *   1. Search Kth element approach: Heap/BS/QuickSelect
 *   2. K-way merge approach: Heap
 */

public class KthElementPatterns {
	MatrixPatterns matrixPatterns = new MatrixPatterns();

	/********************* Search Kth element *************************/

	/*
	 * Kth Largest Element in an Array:
	 * Given an integer array nums and an integer k, return the kth largest element in the array. Note that it is the kth largest element 
	 * in the sorted order, not the kth distinct element.
	 * Can you solve it without sorting?
	 * 	Example 1:
	 * 		Input: nums = [3,2,1,5,6,4], k = 2, Output: 5
	 *  Example 2:
	 *  	Input: nums = [3,2,3,1,2,4,5,5,6], k = 4, Output: 4
	 */

	//1.Using Sorting: Time: O(nlogn), Space: O(1) (in-place sort)
	public int findKthLargest1(int[] nums, int k) {
		int n = nums.length;
		if (n == 0 || n < k) return 0;

		Arrays.sort(nums);
		return nums[n - k];
	}

	/* 2.Partition or Quick Select: Time: O(n) avg & O(n²) worst, Space: O(1) (in-place)
	  - The partition subroutine of quicksort can also be used to solve this problem. 
	  - In partition, we divide the array into elements>=pivot pivot elements<=pivot
	  - Then, according to the index of pivot, we will know whther the kth largest element is to the left 
	    or right of pivot or just itself.
	  - In average, this algorithm reduces the size of the problem by approximately one half after each partition, 
	    giving the recurrence T(n) = T(n/2) + O(n) with O(n) being the time for partition. 
	  - The solution is T(n) = O(n), which means we have found an average linear-time solution. However, in the
	    worst case, the recurrence will become T(n) = T(n - 1) + O(n) and T(n) = O(n^2).
	*/
	public int findKthLargest2(int[] nums, int k) {
		if (nums.length == 0 || k == 0) return 0;

		int l = 0, r = nums.length - 1;

		while (l <= r) {
			int index = partition2(nums, l, r);

			if (index == k - 1) return nums[index];
			else if (index < k - 1) l = index + 1;
			else r = index - 1;
		}

		return -1;
	}

	//Reverse Partition for Largest Element;
	private int partition2(int[] a, int left, int right) {
		if (left == right) return left;
		int pivot = a[right];
		int i = left, j = left;
		while (j < right) {
			if (a[j] > pivot) {
				swap(a, i, j);
				i++;
			}
			j++;
		}
		swap(a, i, right); // swap pivot element at the end
		return i;
	}

	private void swap(int[] a, int i, int j) {
		if (i == j) return;
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	// 3.Heap Approach-1(Using Max Binary Heap): Time: O(n log n), Space: O(n)
	public int findKthLargest3(int[] nums, int k) {
		if (nums.length == 0) return -1;

		Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

		for (int num : nums) // O(nlogn) time
			maxHeap.add(num);

		for (int i = 1; i < k; i++) // O(klogn) time
			maxHeap.remove(); // or maxHeap.poll();

		return !maxHeap.isEmpty() ? maxHeap.peek() : -1;
	}

	// 4.Heap Approach-2(Using Min Binary Heap): Time: O(n log k), Space: O(k)
	public int findKthLargest4(int[] nums, int k) {
		if (nums.length == 0) return -1;

		Queue<Integer> minHeap = new PriorityQueue<>();

		//Time: O(nlogK)
		for (int num : nums) {
			minHeap.add(num);
			if (minHeap.size() > k) {
				minHeap.poll(); // or  minHeap.remove();
			}
		}

		return !minHeap.isEmpty() ? minHeap.peek() : -1;
	}

	// Problem1: Kth Smallest Element in Unsorted Array
	/* Find the kth Smallest element in an unsorted array. Note that it is the kth largest element in the sorted order,
	 * not the kth distinct element.
	 * Example 1: Input: [3,2,1,5,6,4] and k = 2; Output: 2
	 */
	//1.Using Sorting: Time: O(nlogn), Space: O(1) (in-place sort)
	public int kthSmallestElementInArray1(int[] a, int k) {
		Arrays.sort(a);
		return a[k - 1];
	}

	// 2.Partition or Quick Select: Time: O(n) avg & O(n²) worst, Space: O(1) (in-place)
	/*
	 * Using Quick sort Partitioning or Quick Select: Expect Linear Time complexity: O(n) 
	 * Kth Smallest/Largest Element in Unsorted Array -  
	 *   Partition or Quick Select: The partition subroutine of quicksort can also be used to solve this problem. 
	 *  In partition, we divide the array into elements>=pivot pivot elements<=pivot. Then, according to the index of pivot,
	 *  we will know whther the kth largest element is to the left or right of pivot or just itself. In average, this
	 *  algorithm reduces the size of the problem by approximately one half after each partition, giving the 
	 *  recurrence T(n) = T(n/2) + O(n) with O(n) being the time for partition. The solution is T(n) = O(n), which means 
	 *  we have found an average linear-time solution. However, in the worst case, the recurrence will become 
	 *  T(n) = T(n - 1) + O(n) and T(n) = O(n^2).
	 */
	public int kthSmallestElementInArray2(int[] nums, int k) {
		if (nums.length == 0 || k == 0) return 0;

		int l = 0, r = nums.length - 1;

		while (l <= r) {
			// Here 'm' is the partition index to split the array into two parts
			int m = partition(nums, l, r);

			if (k - 1 == m) return nums[m];
			else if (k - 1 < m) r = m - 1;
			else l = m + 1;
		}

		return -1;
	}

	/* Partition:
	 * Left side elements are less than pivotIndex(i) and right side elements are greater than pivotIndex(i)
	 * Use Partition to find the kth Smallest Element; 
	 */
	public int partition(int[] a, int left, int right) {
		int i = left, j = left, pivot = a[right];
		while (j < right) {
			if (a[j] < pivot) {
				Utils.swap(a, i, j);
				i++;
			}
			j++;
		}
		Utils.swap(a, i, right);
		return i;
	}

	// 3.Heap Approach-1(Using Min Binary Heap): Time: O(n log n), Space: O(n)
	public int kthSmallestElementInArray3(int[] arr, int k) {
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) // O(nlogn)
			queue.add(arr[i]);

		for (int i = 0; i < k - 1; i++) // O(klogn) time
			queue.remove();

		return queue.peek();
	}

	// 4.Heap Approach-2(Using Max Binary Heap): Time: O(n log k), Space: O(k)
	public int kthSmallestElementInArray4(int[] arr, int k) {
		PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
		for (int i = 0; i < arr.length; i++) {// O(nlogk) times
			queue.add(arr[i]);
			if (queue.size() > k) queue.poll();

			//or below logic saves few add/remove operations, but both with O(nlogk) time
			/*if (queue.isEmpty() || queue.size() < k) {
				queue.add(arr[i]);
			} else if (arr[i] < queue.peek()) {
				queue.remove();
				queue.add(arr[i]);
			}*/
		}
		return queue.peek();
	}

	/*
	 * Kth Largest Element in a Stream:
	 * 	Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the sorted order, 
	 * not the kth distinct element. Your KthLargest class will have a constructor which accepts an integer k and an integer 
	 * array nums, which contains initial elements from the stream. For each call to the method KthLargest.add, return the 
	 * element representing the kth largest element in the stream.
	 * int k = 3; int[] arr = [4,5,8,2]; 
	 * KthLargest kthLargest = new KthLargest(3, arr);
	 * 	kthLargest.add(3);   // returns 4
	 * 	kthLargest.add(5);   // returns 5
	 */
	PriorityQueue<Integer> queue;
	int k;

	public void init(int k, int[] nums) {
		this.queue = new PriorityQueue<>();
		this.k = k;
		for (int i = 0; i < nums.length; i++) {
			add(nums[i]);
		}
	}

	public int add(int val) {
		queue.add(val);
		if (queue.size() > k) queue.poll();

		return queue.size() < k ? -1 : queue.peek();
	}

	//	Kth Smallest/Largest Element in a BST 
	/*
	 * Kth Smallest Element in a BST:
	 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
	 * Note: You may assume k is always valid, 1 <= k <= BST's total elements.
	 * Example 1: Input: root = [3,1,4,null,2], k = 1
	 * 
	 * Solution: 
	 * 	Approach1: 
	 * 		i. Inorder traversal - Recursive
	 * 		ii.Inorder traversal Modification - Recursive
	 * 		iii.Inorder traversal - Iterative
	 * 	Approach2: 
	 * 		Using Priority Queue
	 */
	// Approach1: i.Using Inorder Traversal: Time:O(n), Space:O(n) 
	public int kthSmallest11(TreeNode root, int k) {
		if (root == null || k == 0) return 0;
		ArrayList<Integer> list = new ArrayList<>();
		kthSmallest(root, list);
		return list.get(k - 1);
	}

	public void kthSmallest(TreeNode root, ArrayList<Integer> list) {
		if (root == null) return;
		kthSmallest(root.left, list);
		list.add(root.val);
		kthSmallest(root.right, list);
	}

	// ii.Inorder traversal Modification: Time:O(k), Space:O(h), h==n for unbalanced BST, h=logn for balanced BST
	public int kthSmallest12(TreeNode root, int k) {
		int[] result = new int[1];
		int[] count = new int[1];
		traverse(root, k, count, result);
		return result[0];
	}

	public void traverse(TreeNode root, int k, int[] count, int[] result) {
		if (root == null) return;
		traverse(root.left, k, count, result);
		count[0]++;
		if (count[0] == k) {
			result[0] = root.val;
			return;
		}
		traverse(root.right, k, count, result);
	}

	// iii.Inorder traversal - Iterative; Time:O(k), Space:O(h)
	public int kthSmallest13(TreeNode root, int k) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode p = root;
		int count = 0;
		while (!stack.isEmpty() || p != null) {
			if (p != null) {
				stack.push(p); // Just like recursion
				p = p.left;
			} else {
				TreeNode node = stack.pop();
				if (++count == k) return node.val;
				p = node.right;
			}
		}
		return Integer.MIN_VALUE;
	}

	// Approach2: Using Heap; Time:O(nlogk), Space:O(h)
	public int kthSmallest2(TreeNode root, int k) {
		if (root == null) return 0;
		PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
		kthSmallest(root, queue, k);
		return queue.peek();
	}

	public void kthSmallest(TreeNode root, PriorityQueue<Integer> queue, int k) {
		if (root == null) return;
		if (queue.isEmpty() || queue.size() < k || root.val < queue.peek()) {
			if (queue.size() == k) queue.remove();
			queue.add(root.val);
		}
		kthSmallest(root.left, queue, k);
		kthSmallest(root.right, queue, k);
	}

	/*********************** Kth Element using K-way merge approach *****************/
	/*
	 *  2-Dimensional Kth elements can be solved using K-way merge approach 
	 *  	- Kth Smallest Element in a Sorted Matrix
	 *  	- Kth Smallest Number in Multiplication Table
	 *  	- Kth Smallest Number in M Sorted Lists
	 */

	/* Kth Smallest Element in a Sorted Matrix: 
	 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.
	 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
	 * 	Example:
	 * 	matrix = [[ 1,  5,  9],	[10, 11, 13],[12, 13, 15]], k = 8, return 13.
	 */
	// Approach1: Using Heap K-way merge patterns: 
	//Time complexity: klogn, where n is col length; or  klogm, where m is row length
	//TODO: Revisit this, Here priority queue must be max heap?
	public int kthSmallestInMatrix1(int[][] matrix, int k) {
		if (matrix == null || matrix.length == 0) return 0;
		int m = matrix.length, n = matrix[0].length;
		if (k > m * n) return 0;

		// Priority Queue arranged based on val
		PriorityQueue<Cell> queue = new PriorityQueue<>((ob1, ob2) -> ob1.data - ob2.data);

		// Add 1st row in the matrix: TC: O(nlogn), here n is col size
		for (int j = 0; j < n; j++)
			queue.add(new Cell(0, j, matrix[0][j]));

		// Remove one by one and next row element corresponding to val; TC:O(klogn)(Heapify k times which takes O(kLogn)
		// time.)
		for (int i = 1; i < k; i++) {
			Cell cell = queue.poll();
			if (cell.i + 1 < m) {
				queue.add(new Cell(cell.i + 1, cell.j, matrix[cell.i + 1][cell.j]));
			}
		}

		return queue.peek().data;
	}

	//Using Binary Search:
	/* 1.Since we are given 1 <= k <= n^2, the kth number must exist in [lo, hi] range.
	 * 2.We use binary search to find the minimum number A, such that the count of ( numbers satisfying num <= A ) is >= k.
	 * 
	 * Time Complexity for this solution: 	
	 *   Notice that using (low < high) in while loop rather than using (low <= high) to avoid stay in the loop. 
	 *   It takes log(m * n) times to find mid, and using (m + n) times to get count in each loop, 
	 *   so time complexity is O(log (m * n) * (m + n) ). The matrix is n x n, So the time complexity is O(n log (n^2)).
	 */
	public int kthSmallestInMatrix2(int[][] matrix, int k) {
		if (matrix == null || matrix.length == 0) return 0;
		int r = matrix.length, c = matrix[0].length;
		if (k > r * c) return 0;

		int l = matrix[0][0], h = matrix[r - 1][c - 1];
		while (l < h) { //Time: log(m*n)
			int m = (l + h) / 2;
			int count = count(matrix, m); //Time: O(m+n)
			System.out.println(m + " - " + count);
			if (count < k) l = m + 1;
			else h = m;
		}
		return l;
	}

	// Reference for count no of elements less than target.
	public void searchMatrix(int[][] matrix, int target) {
		matrixPatterns.searchMatrixII(matrix, target);
	}

	//Count no of elements less than equal to target; Time:O(m+n)
	public int count(int[][] matrix, int target) {
		int m = matrix.length, n = matrix[0].length;
		int i = m - 1, j = 0, count = 0;
		while (i >= 0 && j < n) {
			if (target < matrix[i][j]) {
				i--;
			} else {
				count += i + 1;
				j++;
			}
		}
		return count;
	}
}