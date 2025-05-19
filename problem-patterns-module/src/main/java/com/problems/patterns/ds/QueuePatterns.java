package com.problems.patterns.ds;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class QueuePatterns {

	/********************* Queue Simple Problems ****************/

	//Circular tour/Gas Station - Circular Queue & Greed Alg
	/* There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
	 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). 
	 * You begin the journey with an empty tank at one of the gas stations.
	 * Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.
	 * 
	 * Note: gas[i] -> Amount of gas in the gas station;
	 *       cost[i] -> Cost of gas required to travel from ith station to i+1th station
	 */
	public int canCompleteCircuit1(int[] gas, int[] cost) {
		int n = gas.length, tank = 0;
		//1.Find the sum of differences b/w gas and cost_required.
		for (int i = 0; i < n; i++)
			tank += gas[i] - cost[i];

		//2.If difference is less than zero, you cant travel around the circuit.
		if (tank < 0) return -1;

		//3.Find the gas station's starting index
		int rem = 0, start = 0;
		for (int i = 0; i < n; i++) {
			rem += gas[i] - cost[i];
			if (rem < 0) {
				start = i + 1;
				rem = 0;
			}
		}
		return start;
	}

	//Using Circular Array/Queue
	public int canCompleteCircuit(int[] gas, int[] cost) {
		int n = gas.length, front = 0, rear = 1;
		int rem = gas[front] - cost[front];
		if (n == 1) return rem < 0 ? -1 : 0;
		while (front != rear || rem < 0) {
			//Move front ptr
			while (front != rear && rem < 0) {
				rem -= gas[front] - cost[front];
				front = (front + 1) % n;
				if (front == 0) return -1;
			}
			rem += gas[rear] - cost[rear];
			rear = (rear + 1) % n;
		}
		return front;
	}

	//First Unique Character in a String
	/*
	1.Using count array
	2.Using hash map
	*/
	public int firstUniqChar1(String s) {
		if (s == null) return -1;
		int n = s.length();
		int[] countArray = new int[26];

		for (int i = 0; i < n; i++)
			countArray[s.charAt(i) - 'a']++;

		for (int i = 0; i < n; i++)
			if (countArray[s.charAt(i) - 'a'] == 1) return i;

		return -1;
	}

	public int firstUniqChar2(String s) {
		if (s == null) return -1;
		int n = s.length();
		char ch;
		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			ch = s.charAt(i);
			map.put(ch, map.getOrDefault(ch, 0) + 1);
		}

		for (int i = 0; i < n; i++) {
			if (map.get(s.charAt(i)) == 1) return i;
		}

		return -1;
	}

	/* First non-repeating(unique) character in a stream:
	 * Given a string A denoting a stream of lowercase alphabets. You have to make new string B. B is formed such that we have to find first
	 * non-repeating character each time a character is inserted to the stream and append it at the end to B. If no non-repeating character 
	 * is found then append '#' at the end of B.
	 * Eg: A = "abadbc"; output:  "aabbdd"
	 *     A = "abcabc"; output: "aaabc#"
	 */
	public String firstNonRepeatingChar1(String A) {
		if (A == null || A.length() == 0) return "";

		StringBuilder result = new StringBuilder();
		Queue<Character> queue = new LinkedList<>();
		Map<Character, Integer> map = new HashMap<>();
		//or int[] charCounts = new int[26];
		for (int i = 0; i < A.length(); i++) {
			char ch = A.charAt(i);
			map.put(ch, map.getOrDefault(ch, 0) + 1);
			if (map.get(ch) == 1) queue.add(ch);

			while (!queue.isEmpty()) {
				char top = queue.peek();
				if (map.get(top) == 1) {
					result.append(top);
					break;
				} else {
					queue.poll();
				}
			}
			if (queue.isEmpty()) result.append('#');
		}

		return result.toString();
	}

	//using Sliding Window - Two Pointer 
	public String firstNonRepeatingChar2(String s) {
		int[] charCounts = new int[26];
		int l = 0, r = 0;
		StringBuilder sb = new StringBuilder();
		while (r < s.length()) {
			charCounts[s.charAt(r) - 'a']++;

			// move the start pointer ahead either till the current index or until we see another non-repeating character
			while (l < r && charCounts[s.charAt(l) - 'a'] > 1) l++;

			if (charCounts[s.charAt(l) - 'a'] == 1) sb.append(s.charAt(l));
			else sb.append('#');
			r++;
		}
		return sb.toString();
	}

	/********************* Monotonic Queue Problems ****************/
	/*
	 * Monotonic Queue: A monotonic Queue is a data structure the elements from the front to the end is strictly either increasing or decreasing.
	 * Monotonic increasing queue: add an element e in queue and pop out the element; s.peek()>=e (violation).
	 * Monotonic decreasing queue: add an element e in queue and pop out the element; s.peek()<=e (violation).
	 * Note: Monotonic Increasing/Decreasing Queue performs exactly same thing. So we can simply use monotonic stack, because its comparatively simple.
	 */

	public void monotonicIncreasingQueue(int[] arr) {
		Deque<Integer> queue = new ArrayDeque<>(); //or new LinkedList<>();
		for (int i = 0; i < arr.length; i++) {
			while (!queue.isEmpty() && queue.getLast() >= arr[i]) {
				queue.removeLast();
			}
			queue.add(arr[i]);
		}
	}

	public void monotonicDecreasingQueue(int[] arr) {
		Deque<Integer> queue = new LinkedList<>();
		for (int i = 0; i < arr.length; i++) {
			while (!queue.isEmpty() && queue.getLast() <= arr[i]) {
				queue.removeLast();
			}
			queue.add(arr[i]);
		}
	}

	/* Sliding Window Maximum/Maximum of all subarrays of size k:
	 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the
	 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one
	 * position. Return the max sliding window.
	 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3; Output: [3,3,5,5,6,7] 
	 * 
	 * Solution:
	 * 	1. BruteForce: O(nk)
	 *  2. Balanced BST(TreeMap): O(nlogk)
	 *  3. Sliding Window: O(n)
	 */
	public int[] maxSlidingWindow(int[] nums, int k) {
		if (nums.length == 0 || k > nums.length) return new int[0];

		int n = nums.length;
		int[] result = new int[n - k + 1];
		//Deque to store the index of the elements; If we store the element, we can't find the elements position and window size has size K. 
		Deque<Integer> deque = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			//Keep removing the smaller element from the last in deque
			while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) deque.removeLast(); //peekLast or getLast

			deque.addLast(i);

			if (i >= k - 1) {
				result[i - k + 1] = nums[deque.peekFirst()]; //or result[index++] = nums[deque.peekFirst()]; peekFirst == getFirst

				//If 'i' reaches the size k, then Remove the top element 
				if (i - deque.peekFirst() == k - 1) deque.removeFirst(); //removeFirst or poll
			}
		}

		return result;
	}

	/*
	 * Min Max Riddle:
	 * You will be given a list of integers arr and a single integer k. You must create an array of length k
	 *  from elements of arr such that its unfairness is minimized. Call that array subArr. 
	 *  Unfairness of an array is calculated as
	 *  	max(subArr) - min(subArr);
	 *  	Where:
	 *  		- max denotes the largest integer in subArr
	 *   		- min denotes the smallest integer in subArr*/
	public int maxMin(int k, int[] nums) {
		if (nums.length == 0 || k > nums.length) return 0;
		Arrays.sort(nums);

		int n = nums.length, maxDiff = Integer.MAX_VALUE;
		// Queue to store the index of the elements; Max element index should be
		// retained in the deque front.
		Deque<Integer> minDeque = new LinkedList<>();
		Deque<Integer> maxDeque = new LinkedList<>();

		for (int i = 0; i < n; i++) {

			// Keep removing the smaller element from the last in deque
			while (!minDeque.isEmpty() && nums[i] < nums[minDeque.peekLast()]) minDeque.removeLast();
			while (!maxDeque.isEmpty() && nums[i] > nums[maxDeque.peekLast()]) maxDeque.removeLast();

			minDeque.addLast(i);
			maxDeque.addLast(i);

			if (i >= k - 1) {
				maxDiff = Math.min(maxDiff, nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()]);

				// If 'i' reaches the size k, then Remove the top element
				if (i - minDeque.peekFirst() == k) minDeque.removeFirst();
				if (i - maxDeque.peekFirst() == k) maxDeque.removeFirst();
			}
		}
		return maxDiff;
	}

	//Shortest Subarray with Sum at Least K
	public int shortestSubarray(int[] A, int K) {
		int N = A.length, res = N + 1;
		int[] B = new int[N + 1];
		for (int i = 0; i < N; i++)
			B[i + 1] = B[i] + A[i];
		Deque<Integer> d = new ArrayDeque<>();
		for (int i = 0; i <= N; i++) {
			while (d.size() > 0 && B[i] - B[d.getFirst()] >= K) {
				res = Math.min(res, i - d.pollFirst());
			}
			while (d.size() > 0 && B[i] <= B[d.getLast()]) d.pollLast();
			d.addLast(i);
		}
		return res <= N ? res : -1;
	}
}