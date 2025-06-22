package com.problems.patterns.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.function.Supplier;

import com.common.model.Cell;
import com.common.model.ListNode;
import com.common.model.TreeNode;
import com.problems.patterns.crossdomains.KElementsPattern;
import com.problems.patterns.crossdomains.KthElementPatterns;

public class HeapPatterns {

	/********************* 1.K-way merge/Merge K data sets *************************/
	// Merge K sorted linked lists;
	/* Linear Merge Algorithm: Merge the List one by one 
	 * Time Complexity: O(Nk) where N = nk -> total no of nodes; k = no of linked list; n = no of elements in the list
	 */
	public ListNode mergeKSortedLinkedList1(ListNode[] lists) {
		int k = lists.length;
		if (k == 0) return null;

		ListNode result = null;
		for (int i = 0; i < k; i++)
			result = merge(result, lists[i]);
		return result;
	}

	public ListNode merge(ListNode head1, ListNode head2) {
		if (head1 == null) return head2;
		if (head2 == null) return head1;

		ListNode result = null;
		if (head1.data < head2.data) {
			result = head1;
			result.next = merge(head1.next, head2);
		} else {
			result = head2;
			result.next = merge(head1, head2.next);
		}
		return result;
	}

	// Using Min Heap: O(NLogk); where N = nk; k = no of linked list; n = no of elements in the list
	public ListNode mergeKSortedLinkedList2(ListNode[] nodes, int k) {
		if (k == 0) return null;
		if (k == 1) return nodes[0];
		PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.data - o2.data);
		for (int i = 0; i < k; i++)
			if (nodes[i] != null) queue.add(nodes[i]);

		ListNode dummy = new ListNode(0);
		ListNode curr = dummy;
		while (!queue.isEmpty()) {
			ListNode top = queue.poll();
			// Add next val in the queue
			if (top.next != null) queue.add(top.next);
			curr.next = top;
			curr = curr.next;
		}
		return dummy.next;
	}

	// Merge k sorted arrays:
	/* 
	 * 1.BruteForce Approach: A simple solution is to create an output array of size n*k and one by one copy all
	 * arrays to it. Finally, sort the output array using any O(nLogn) sorting algorithm.
	 * This approach takes O(NlogN) time, where N=nk, k - no of arrays; n - no of elements in each array
	 */
	public int[] mergeKSortedArrays1(int[][] arr) {
		int k = arr.length, n = arr[0].length;
		// Assuming equal size array
		int[] output = new int[n * k];
		int index = 0;
		// Copy all the elements in ouput array
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < n; j++) {
				output[index++] = arr[i][j];
			}
		}
		// Sort
		Arrays.sort(output);
		System.out.println("After Merge: " + Arrays.toString(output));
		return output;
	}

	// Merge Sorted Arrays using PriorityQueue;
	// Time Complexity: O(Nlogk) where N=nk, k - no of arrays; n - no of elements in each array
	public int[] mergeKSortedArrays2(int[][] arr) {
		int size = 0;
		PriorityQueue<Cell> queue = new PriorityQueue<>((a, b) -> a.data - b.data);
		for (int i = 0; i < arr.length; i++) {
			queue.add(new Cell(i, 0, arr[i][0]));
			// To count the no elements, if it diff size array; otherwise directly calculate from input
			size += arr[i].length;
		}

		int[] result = new int[size];
		int index = 0;
		while (!queue.isEmpty()) {
			Cell curr = queue.poll();
			result[index++] = curr.data;
			if (curr.j + 1 < arr[curr.i].length) {
				queue.add(new Cell(curr.i, curr.j + 1, arr[curr.i][curr.j + 1]));
			}
		}

		System.out.println("After merge:" + Arrays.toString(result));
		return result;
	}

	/*
	 * Shortest Range in K sorted lists/Smallest range/Minimize the absolute difference
	 */
	/* Smallest Range:
	 * You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number
	 * from each of the k lists. We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a ==
	 * d-c. 
	 * Example 1: Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]] 
	 * Output: [20,24] 
	 * Explanation: List 1: [4, 10, 15, 24,26], 24 is in range [20,24]. List 2: [0, 9, 12, 20], 20 is in range [20,24]. 
	 * List 3: [5, 18, 22, 30], 22 is in range [20,24].	 
	 */
	// TODO: Modify this to use Container to hold the indices
	// Apply Merge K List Algorithm
	public int[] smallestRange(List<List<Integer>> nums) {
		int[] result = new int[2];
		if (nums.size() == 0) return result;

		PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]); // i,j,val
		int max = Integer.MIN_VALUE, minRange = Integer.MAX_VALUE;

		// Set the first value in entire list
		for (int i = 0; i < nums.size(); i++) {
			max = Math.max(max, nums.get(i).get(0));
			queue.add(new int[] { i, 0, nums.get(i).get(0) });
		}

		while (queue.size() == nums.size()) {
			int[] curr = queue.poll();
			int i = curr[0], j = curr[1], min = curr[2];

			// update the result
			if (max - min < minRange) {
				result[0] = min;
				result[1] = max;
				minRange = max - min;
			}

			// Check next value from the top element
			j++;

			if (nums.get(i) != null && j >= nums.get(i).size()) continue;

			int nextVal = nums.get(i).get(j);
			max = Math.max(max, nextVal);

			queue.add(new int[] { i, j, nextVal });
		}

		return result;
	}

	/*
	 * Minimize the absolute difference: 
	 * Given three sorted arrays A, B and Cof not necessarily same sizes.
	 * Calculate the minimum absolute difference between the maximum and minimum number from the triplet a, b, c such that a, b, c
	 * belongs arrays A, B, C respectively.i.e. minimize | max(a,b,c) - min(a,b,c) |.
	 * Example :Input: A : [ 1, 4, 5, 8, 10 ], B : [ 6, 9, 15 ], C : [ 2, 3, 6, 6 ]
	 * 			Output: 1
	 */
	public static int minAbsoluteDiff(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
		int diff = Integer.MAX_VALUE;
		int i = 0, j = 0, k = 0;
		int p = A.size(), q = B.size(), r = C.size();

		while (i < p && j < q && k < r) {
			int maximum = Math.max(A.get(i), Math.max(B.get(j), C.get(k)));
			int minimum = Math.min(A.get(i), Math.min(B.get(j), C.get(k)));

			if (maximum - minimum < diff) {
				diff = maximum - minimum;
			}

			if (diff == 0) break;

			if (A.get(i) == minimum) i++;
			else if (B.get(j) == minimum) j++;
			else k++;
		}

		return diff;
	}

	/********************* 2.Kth Element Pattern *************************/
	KthElementPatterns kthElementPatterns;

	public void kthSmallestElement(int[] a, int k) {
		kthElementPatterns.kthSmallestElementInArray1(a, k);
		kthElementPatterns.kthSmallestElementInArray2(a, k);
		kthElementPatterns.kthSmallestElementInArray3(a, k);
		kthElementPatterns.kthSmallestElementInArray4(a, k);
	}

	/********************* 3.Top K Elements Pattern *************************/
	KElementsPattern kElementsPatterns;

	public void topKFrequentElements(int[] nums, int k) {
		kElementsPatterns.topKFrequentElements1(nums, k);
		kElementsPatterns.topKFrequentElements21(nums, k);
		kElementsPatterns.topKFrequentElements22(nums, k);
		kElementsPatterns.topKFrequentElements3(nums, k);
	}

	public void rearrangeString(String str, int k) {
		kElementsPatterns.rearrangeString(str, k);
	}

	public void taskScheduler(char[] tasks, int n) {
		kElementsPatterns.leastInterval11(tasks, n);
		kElementsPatterns.leastInterval12(tasks, n);
		kElementsPatterns.leastInterval2(tasks, n);
	}

	/********************* 4.Two Heaps *************************/

	/*Find Median from Data Stream:
	 * Median is the middle value in an ordered list of elements. If the size of the list is even, there is no middle value.
	 * So the median is the mean of the two middle value.
	 * 
	 * Given that integers are read from a data stream. Find median of elements read so far in efficient way.
	 * Approaches:
	 *    1.Simple Sorting: Store the numbers in a resize-able container. Every time you need to output the median, sort the 
	 *      container and output the median. Time: O(nlogn), Space:O(n)
	 *    2.Insertion Sort & Binary Search: Keeping our input container always sorted. BS is used to find the correct place to 
	 *      insert the incoming number. Time O(logn)+O(n) - O(n); Space:O(n)
	 *    3.Using Two Heaps: Time O(logn); Space:O(n)
	 *    4.Using Balanced BST: Time O(logn); Space:O(n); But little complex to build the Balanced-BST  
	 *         - Try to use using multiset-TreeSet/TreeMap
	 */
	/* Using Two Heap:
	 *  Here we only need a consistent way to access the median elements. Keeping the entire input sorted is not a requirement.
	 *  Heap has direct access to the maximal/minimal elements in a group.If we could maintain two heaps in the following way:
	 *   - A max-heap to store the smaller half of the input numbers
	 *   - A min-heap to store the larger half of the input numbers
	 *  This gives access to median values in the input: they comprise the top of the heaps!
	 *  Time O(logn); Space:O(n)
	 */
	PriorityQueue<Integer> lower; // lower/first half of elements & it uses Max Heap
	PriorityQueue<Integer> upper; // upper/second half of elements & it uses Min Heap

	public void findMedianInStream3(int[] a) {
		int n = a.length;
		if (n == 0) return;
		lower = new PriorityQueue<>(Collections.reverseOrder());
		upper = new PriorityQueue<>();
		for (int i = 0; i < n; i++) {
			addNum(a[i]);
			System.out.print(findMedian() + " ");
		}
	}

	public void addNum(int num) {
		// Here first data should be in upper part, to maintain upper > lower
		if (!lower.isEmpty() && num < lower.peek()) lower.add(num);
		else upper.add(num);

		balanceHeap();
	}

	// If size is odd, Upper should have one elements more than Lower
	private void balanceHeap() {
		if (lower.size() > upper.size()) upper.add(lower.poll());

		if (upper.size() - lower.size() > 1) lower.add(upper.poll());
	}

	// Returns the median of current data stream
	public double findMedian() {
		return lower.size() != upper.size() ? (double) upper.peek()
				: ((double) lower.peek() + (double) upper.peek()) * 0.5;

		//To avoid Overflow: This will work, if the input range is within one data type range. Eg: int, long etc.
		/*return lower.size() != upper.size() ?  upper.peek()
				: (lower.peek() + (upper.peek() - lower.peek()) * 0.5);*/
	}

	//Remove from Heap takes O(n) time 
	public void removeNum(int num) {
		if (!lower.isEmpty() && num <= lower.peek()) lower.remove(num);
		else upper.remove(num);
		balanceHeap();
	}

	/* Sliding Window Median:
	 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the
	 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one
	 * position. Your job is to output the median array for each window in the original array.
	 */
	/*
	 * Solution:
	 * Use two Heaps to store numbers. lower for numbers smaller than current median, upper for numbers bigger than and 
	 * equal to current median. A small trick I used is always make size of upper equal (when there are even numbers) 
	 * or 1 element more (when there are odd numbers) than the size of lower. Then it will become very easy to calculate 
	 * current median.
	 * Keep adding number from the right side of the sliding window and remove number from left side of the sliding window.
	 * And keep adding current median to the result.
	 * Time Complexity: O(nk); Heap takes k times to remove the element
	 */
	public double[] medianSlidingWindow1(int[] nums, int k) {
		int n = nums.length;
		if (n == 0) return new double[0];

		lower = new PriorityQueue<>(Collections.reverseOrder());
		upper = new PriorityQueue<>();
		double[] result = new double[n - k + 1];

		for (int i = 0; i < n; i++) {
			addNum(nums[i]);

			if (i >= k - 1) {
				result[i - k + 1] = findMedian(); // or result[index++]
				removeNum(nums[i - k + 1]);
			}
		}

		return result;
	}

	/* Approach2: Using Treeset
	 *  However instead of using two priority queue's we use two Tree Sets as we want O(logk) for remove(element).
	 *  Priority Queue would have been O(k) for remove(element) giving us an overall time complexity of O(nk) 
	 *  instead of O(nlogk).
	 */
	TreeSet<Integer> left = null;
	TreeSet<Integer> right = null;

	public double[] medianSlidingWindow2(int[] nums, int k) {
		// Should add index in the set to handle duplicate elements
		Comparator<Integer> comparator = (i, j) -> nums[i] != nums[j] ? Integer.compare(nums[i], nums[j]) : i - j;
		left = new TreeSet<>(comparator.reversed());
		right = new TreeSet<>(comparator);

		int n = nums.length;
		double[] result = new double[n - k + 1];

		for (int i = 0; i < n; i++) {
			addNum(nums, i);

			if (i >= k - 1) {
				result[i - k + 1] = findMedian(nums); //result[index++] 
				removeNum(nums, i - k + 1);
			}

		}
		return result;
	}

	public void addNum(int[] nums, int i) {
		if (!left.isEmpty() && nums[i] < nums[left.first()]) left.add(i);
		else right.add(i);
		balanceTreeSet();
	}

	public void removeNum(int[] nums, int i) {
		if (!left.isEmpty() && nums[i] <= nums[left.first()]) left.remove(i);
		else right.remove(i);
		balanceTreeSet();
	}

	public void balanceTreeSet() {
		if (left.size() > right.size()) right.add(left.pollFirst());
		if (right.size() - left.size() > 1) left.add(right.pollFirst());
	}

	public double findMedian(int[] nums) {
		return left.size() != right.size() ? (double) nums[right.first()]
				: ((double) nums[left.first()] + (double) nums[right.first()]) * 0.5;

		//To avoid Overflow: This will work, if the input range is within one data type range. Eg: int, long etc.
		/*return left.size() != right.size() ? nums[right.first()]
				: nums[left.first()] + (nums[right.first()] - nums[left.first()]) / 2;*/
	}

	// Using Lambda Expression
	public double[] medianSlidingWindow3(int[] nums, int k) {
		// Should add index in the set to handle duplicate elements
		Comparator<Integer> comparator = (i, j) -> nums[i] != nums[j] ? Integer.compare(nums[i], nums[j]) : i - j;
		TreeSet<Integer> left = new TreeSet<>(comparator.reversed());
		TreeSet<Integer> right = new TreeSet<>(comparator);

		// Function to find the median
		Supplier<Double> median = (k % 2 == 0) ? () -> ((double) nums[left.first()] + nums[right.first()]) / 2
				: () -> (double) nums[right.first()];

		// Function to balance lefts size and rights size (if not equal then right will be larger by one)
		Runnable balance = () -> {
			if (left.size() > right.size()) right.add(left.pollFirst());
			if (right.size() - left.size() > 1) left.add(right.pollFirst());
		};

		double[] result = new double[nums.length - k + 1];

		for (int i = 0, j = 0; i <= nums.length; i++) {
			if (i >= k) {
				result[j++] = median.get();
				// Remove the elements
				if (!left.isEmpty() && nums[i - k] <= nums[left.first()]) left.remove(i - k);
				else right.remove(i - k);
				balance.run();
			}

			if (i >= nums.length) continue;

			// Add the elements
			if (!left.isEmpty() && nums[i] < nums[left.first()]) left.add(i);
			else right.add(i);
			balance.run();
		}

		return result;
	}

	/*************************** Misc ************************************/
	/* Check Binary Heap Tree(Tree data structure):
	 It should be a complete tree (i.e. all levels except last should be full).
	Every node�s value should be greater than or equal to its child node (considering max-heap).*/
	public boolean isBinaryHeap(TreeNode root) {
		if (root == null) return true;
		// Count no of nodes
		int count = sizeOfBinaryTree(root);

		// Check both Complete binary tree property, Max Binary Heap Property
		return isCompleteProperty(root, 0, count) && isMaxBinaryHeap(root);
	}// Size of a BT - Recursive Approach

	public int sizeOfBinaryTree(TreeNode root) {
		if (root == null) return 0;
		return 1 + sizeOfBinaryTree(root.left) + sizeOfBinaryTree(root.right);
	}

	// Check Complete property
	private boolean isCompleteProperty(TreeNode root, int index, int count) {
		if (root == null) return true;

		if (index >= count) return false;

		return isCompleteProperty(root.left, (2 * index) + 1, count)
				&& isCompleteProperty(root.right, (2 * index) + 2, count);
	}

	// Check Max Binary Heap Property
	private boolean isMaxBinaryHeap(TreeNode root) {
		if (root.left == null && root.right == null) return true;

		if (root.right == null) return (root.val > root.left.val);

		return (root.val >= root.left.val && root.val >= root.right.val) && isMaxBinaryHeap(root.left)
				&& isMaxBinaryHeap(root.right);
	}

	public static void main(String[] args) {
		HeapPatterns ob = new HeapPatterns();
		//System.out.println(ob.rearrangeString("aaabc", 3));
	}
}