package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.common.model.Cell;
import com.common.model.TreeNode;

/*
 * Kth Element Vs K Elements:
 *  Kth Element: Find kth element from array, list or 2D array. Eg: smallest, largest etc
 *  K Elements: Find k elements from array, list or 2D array. Eg: Top K elements, K closest elements etc
 * 
 * This Pattern covers get k elements from linear and non linear data structures. It can be
 *  1. Top K Elements
 *  	 - Top K  Frequent Elements
 *  2. Last K Elements
 *  3. Mid  K Elements(Any where in the mid position) 
 *     - K Closest Elements from given target
 */
public class KElementsPattern {

	/********************** 2.Find top K frequent elements *************************/
	/* Top K Frequent Elements:
	 * Find top k (or most frequent) numbers in a stream:
	 *  Given an array of n numbers. Your task is to read numbers from the array and keep at-most K numbers at the top (According 
	 *  to their decreasing frequency) every time a new number is read
	 */
	public void topKFrequentElements(int[] nums, int k) {
		topKFrequentElements1(nums, k);
		topKFrequentElements21(nums, k);
		topKFrequentElements22(nums, k);
		topKFrequentElements3(nums, k);
	}

	/* Top K Frequent Words:
	 * Given a non-empty list of words, return the k most frequent elements.
	 * Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word 
	 * with the lower alphabetical order comes first.
	 * Example 1:
	 * 	Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
	 * 	Output: ["i", "love"]
	 * 	Explanation: "i" and "love" are the two most frequent words.
	 * 	Note that "i" comes before "love" due to a lower alphabetical order.
	 */
	public void topKFrequentWords(String[] words, int k) {
		topKFrequentWords1(words, k);
		topKFrequentWords2(words, k);
	}

	/* Rearrange String K Distance Apart:
	 * Given a non-empty string str and an integer k, rearrange the string such that the same characters are at least
	 * distance k from each other. All input strings are given in lowercase letters. If it is not possible to rearrange
	 * the string, return an empty string "".
	 * Example1: str = "aabbcc", k = 3; Answer: "abcabc"; The same letters are at least distance 3 from each other.
	 * Example2: str = "aaabc", k = 3 Answer: ""; It is not possible to rearrange the string. -> This is not working, check this
	 */
	//Time Complexity: O(nlogn); Here n is String length
	public String rearrangeString(String str, int k) {
		if (k == 0) return str;

		// initialize the counter for each character
		final HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			map.put(c, map.getOrDefault(c, 0) + 1);
		}

		// Max Heap: sort the chars by frequency
		PriorityQueue<Character> queue = new PriorityQueue<>(
				(c1, c2) -> map.get(c1) != map.get(c2) ? map.get(c2) - map.get(c1) : c1.compareTo(c2));

		for (char c : map.keySet())
			queue.offer(c);

		// or queue.addAll(map.keySet());

		StringBuilder sb = new StringBuilder();
		int len = str.length();
		while (!queue.isEmpty()) {
			//Waiting Queue is used to process the chars in the next iteration, if char count is more one
			List<Character> waitingQueue = new LinkedList<>();
			for (int i = 0; i < Math.min(k, len); i++) {
				//It is not possible to rearrange the string.
				if (queue.isEmpty()) return "";

				char c = queue.poll();
				sb.append(String.valueOf(c));

				map.put(c, map.get(c) - 1);
				//if char count is greater than one, then add into the waiting queue to process in next iteration
				if (map.get(c) > 0) waitingQueue.add(c);

				/*	if (map.get(c) == 1) {
						map.remove(c);
					} else { 
						map.put(c, map.get(c) - 1);
						waitingQueue.add(c);
					}*/
				len--;
			}
			queue.addAll(waitingQueue);
		}

		return sb.toString();
	}

	/* Task Scheduler: Given a char array representing tasks CPU need to do. It contains capital letters A to Z where
	 * different letters represent different tasks. Tasks could be done without original order. Each task could be done
	 * in one interval. For each interval, CPU could finish one task or just be idle. However, there is a non-negative
	 * cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing
	 * different tasks or just be idle. You need to return the least number of intervals the CPU will take to finish all
	 * the given tasks. 
	 * Example: Input: tasks = ["A","A","A","B","B","B"], n = 2 Output: 8 
	 * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
	 */
	// Approach1:
	public int leastInterval11(char[] tasks, int n) {
		int[] cnt = new int[26];
		for (char c : tasks) {
			cnt[c - 'A']++;
		}
		int maxChar = 0, maxCharCnt = 0;
		for (int i = 0; i < 26; i++) {
			if (cnt[i] == maxChar) {
				maxCharCnt++;
			} else if (cnt[i] > maxChar) {
				maxChar = cnt[i];
				maxCharCnt = 1;
			}
		}
		int minimum = (maxChar - 1) * (n + 1) + maxCharCnt;
		return (tasks.length > minimum) ? tasks.length : minimum;
	}

	/*
	Steps:
	First count the number of occurrences of each element.
	Let the max frequency seen be M for element E. //maxFreq -> M
	Run through the frequency dictionary and for every element which has frequency == M, add 1 cycle to result. //maxFreqCount
	We can schedule the first M-1 occurrences of E, each E will be followed by at least N CPU cycles in between successive schedules of E
	Total CPU cycles after scheduling M-1 occurrences of E = (M-1) * (N + 1) // 1 comes for the CPU cycle for E itself
	Now schedule the final round of tasks. We will need at least 1 CPU cycle of the last occurrence of E. If there are multiple tasks with frequency M, they will all need 1 more cycle.
	If we have more number of tasks than the max slots we need as computed above we will return the length of the tasks array as we need at least those many CPU cycles.
	 */
	public int leastInterval12(char[] tasks, int n) {
		int[] cnt = new int[26];
		int maxFreq = 0;
		for (char c : tasks) {
			//First count the number of occurrences of each element.
			cnt[c - 'A']++;
			//Find max frequency of element in the tasks
			maxFreq = Math.max(maxFreq, cnt[c - 'A']);
		}

		//Find no of times max freq in cnt[] array
		int maxFreqCount = 0;
		for (int i = 0; i < 26; i++) {
			if (cnt[i] == maxFreq) maxFreqCount++;
		}

		int result = (maxFreq - 1) * (n + 1) + maxFreqCount;

		//return Math.max(result, tasks.length);

		return (tasks.length > result) ? tasks.length : result;
	}

	// Approach-2
	// Java PriorityQueue solution - Similar problem Rearrange string K distance apart
	public int leastInterval2(char[] tasks, int n) {
		if (tasks == null || tasks.length == 0) return -1;
		// build map to sum the amount of each task
		HashMap<Character, Integer> map = new HashMap<>();
		for (char ch : tasks)
			map.put(ch, map.getOrDefault(ch, 0) + 1);

		// build queue, sort from descending
		PriorityQueue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>(
				(a, b) -> (b.getValue() - a.getValue()));
		queue.addAll(map.entrySet());

		int cnt = 0;
		// when queue is not empty, there are remaining tasks
		while (!queue.isEmpty()) {
			// for each interval
			int interval = n + 1;
			// list used to update queue
			List<Map.Entry<Character, Integer>> list = new ArrayList<>();

			// fill the intervals with the next high freq task
			while (interval > 0 && !queue.isEmpty()) {
				Map.Entry<Character, Integer> entry = queue.poll();
				entry.setValue(entry.getValue() - 1);
				list.add(entry);
				// interval shrinks
				interval--;
				// one slot is taken
				cnt++;
			}

			// update the value in the map
			for (Map.Entry<Character, Integer> entry : list) {
				// when there is left task
				if (entry.getValue() > 0) queue.offer(entry);
			}
			// job done
			if (queue.isEmpty()) break;
			// if interval is > 0, then the machine can only be idle
			cnt += interval;
		}
		return cnt;
	}

	/*
	 * Top K Frequent Elements:
	 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
	 * Example 1: Input: nums = [1,1,1,2,2,3], k = 2; Output: [1,2]
	 * Example 2: Input: nums = [1], k = 1; Output: [1]
	 * 
	 * Solution:
	 * 	Approach1: Brute Force — Frequency Map + Full Sort; Time: O(nlogn), Space: O(n)
	 *  Approach2: Using Hashmap & Priority Queue(Heap); Time: O(nlogk), Space: O(n)
	 *  Approach3: Using Hashmap & Bucket Sort(Optimal Solution); Time: O(n), Space:)(n)
	 */

	// Approach1: Brute Force — Frequency Map + Full Sort; Time: O(nlogn), Space: O(n)
	public int[] topKFrequentElements1(int[] nums, int k) {
		// 1.Build a frequency map - O(n)
		Map<Integer, Integer> freq = new HashMap<>();
		for (int num : nums)
			freq.put(num, freq.getOrDefault(num, 0) + 1);

		// 2.Sort the entries by frequency.
		List<Map.Entry<Integer, Integer>> list = new ArrayList<>(freq.entrySet());
		list.sort((a, b) -> b.getValue() - a.getValue());

		// 3.Populate the top k frequent elements
		// 3.1. Using Inbuilt function - return list.subList(0, k);
		// 3.2. Manually populate the result 
		int[] result = new int[k];
		for (int i = 0; i < k; i++) {
			result[i] = list.get(i).getKey();
		}

		return result;
	}

	// Approach2: Using Hashmap & Priority Queue(Heap); Time: O(nlogk), Space: O(n)
	public List<Integer> topKFrequentElements21(int[] nums, int k) {
		int n = nums.length;
		if (n == 0 || k == 0) return null;

		// 1.Build a frequency map - O(n)
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++)
			map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);

		if (map.size() < k) return null;

		// 2.Create a min Heap based on freq of elements from Map. Time-O(nlogk)
		PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b)); // Sorting based on map's value
		for (Integer key : map.keySet()) {
			queue.add(key);
			if (queue.size() > k) queue.poll(); //If heap size > k, remove the least frequent.
		}

		// 3.Populate the top k frequent elements from Min Heap
		List<Integer> result = new ArrayList<>();
		while (!queue.isEmpty() && result.size() < k) {
			result.add(queue.poll());
		}
		Collections.reverse(result); //Sort if requested in any specific

		return result;
	}

	// Approach2: Same solution with minor data type changes - HashMap + Priority Queue(Heap)
	public int[] topKFrequentElements22(int[] nums, int k) {
		int[] result = new int[k];
		int n = nums.length;

		if (n == 0) return result;

		// 1.Build a frequency map
		Map<Integer, Integer> map = new HashMap<>(); // Key - Element; Value - Count
		for (int i = 0; i < n; i++) {
			map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
		}

		if (map.size() < k) return null;

		// 2.Create a min Heap based on freq of elements from Map. Time-O(nlogk)
		PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			queue.add(entry);
			if (queue.size() > k) queue.poll(); //If heap size > k, remove the least frequent.
		}

		// 3.Populate the top k frequent elements from Min Heap
		for (int i = k - 1; i >= 0; --i) {
			result[i] = queue.poll().getKey();
		}

		return result;
	}

	// Approach3: Using Hashmap & Bucket Sort; Time: O(n), Space:)(n)
	public int[] topKFrequentElements3(int[] nums, int k) {
		int[] result = new int[k];
		if (nums.length == 0) return result;

		// 1.Build a frequency map
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++)
			map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);

		// 2.Find max freq/count in map's value 
		int max = 0;
		for (Map.Entry<Integer, Integer> entry : map.entrySet())
			max = Math.max(max, entry.getValue());

		// 3.Bucket Sorting - To sort the elements based on the frequency/count
		ArrayList<Integer>[] buckets = new ArrayList[max + 1];
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (buckets[entry.getValue()] == null) buckets[entry.getValue()] = new ArrayList<>();
			buckets[entry.getValue()].add(entry.getKey());
		}

		// 4.Populate the top k elements from Sorted Buckets
		int index = 0;
		for (int i = max; i > 0 && index < k; i--) {
			if (buckets[i] != null && buckets[i].size() > 0) {
				for (int num : buckets[i]) {
					if (index >= k) break;
					result[index++] = num;
				}
			}
		}

		// 3.Populate the top k frequent elements(in List format) from Sorted Buckets
		/*
		List<Integer> result = new ArrayList<Integer>();
		for (int i = max; i >= 1 && result.size() < k; i--) { // Start from max value
			if (buckets[i] != null && buckets[i].size() > 0) {
				// If there is more than one element in the same count
				for (int a : buckets[i]) {
					if (result.size() == k) // if size==k, stop
						break;
					result.add(a);
				}
			}
		}
		*/

		return result;
	}

	/*
	 * Top K Frequent Words: 
	 * Given an array of strings words and an integer k, return the k most frequent strings.
	 * Return the answer sorted by the frequency from highest to lowest. Sort the words with the same frequency by their lexicographical order.
	 * Example 1: 
	 * 	Input: words = ["i","love","leetcode","i","love","coding"], k = 2
	 * 	Output: ["i","love"]
	 * Example 2:
	 * 	Input: words = ["the","day","is","sunny","the","the","the","sunny","is","is"], k = 4
	 * 	Output: ["the","is","sunny","day"]
	 */
	// Approach1: Brute Force — Frequency Map + Full Sort; Time: O(nlogn), Space: O(n)
	public List<String> topKFrequentWords1(String[] words, int k) {
		// 1. Build the freq map
		Map<String, Integer> map = new HashMap();
		for (String word : words) {
			map.put(word, map.getOrDefault(word, 0) + 1);
		}
		List<String> list = new ArrayList(map.keySet());
		// 2. Sort the list -  Desc order based on count and Asc order the words if count equals
		Collections.sort(list, (a, b) -> map.get(a).equals(map.get(b)) ? a.compareTo(b) : map.get(b) - map.get(a));

		return list.subList(0, k);
	}

	// Approach2: Using Hashmap & Priority Queue(Heap); Time: O(nlogk), Space: O(n)
	public List<String> topKFrequentWords2(String[] words, int k) {
		// 1. Build the freq map
		Map<String, Integer> map = new HashMap<>();
		for (String word : words)
			map.put(word, map.getOrDefault(word, 0) + 1);

		// 2. Build Priority Queue(Min Binary Heap) elements based on freq of words in Map
		Queue<String> queue = new PriorityQueue<>((a, b) -> {
			int cmp = map.get(a) - map.get(b); // Asc freq
			if (cmp == 0) return b.compareTo(a); // Desc lex
			return cmp;
		});
		for (String key : map.keySet()) {
			queue.add(key);
			if (queue.size() > k) queue.poll();
		}

		// 3.Populate the top k frequent elements
		List<String> result = new ArrayList<>();
		while (!queue.isEmpty()) {
			result.add(queue.poll());
		}
		Collections.reverse(result);
		return result;
	}

	// Approach3: Using Hashmap & Bucket Sort; Time:O(n + m log m), Space:O(n)
	public List<String> topKFrequentWords3(String[] words, int k) {
		// 1. Build the freq map
		Map<String, Integer> map = new HashMap<>();
		for (String word : words)
			map.put(word, map.getOrDefault(word, 0) + 1);

		// 2. Find max frequency 
		int max = 0;
		for (String key : map.keySet()) {
			max = Math.max(max, map.get(key));
		}

		// 3. Bucket Sorting
		ArrayList<String>[] buckets = new ArrayList[max + 1];
		for (String key : map.keySet()) {
			int val = map.get(key);
			if (buckets[val] == null) buckets[val] = new ArrayList<>();
			buckets[val].add(key);
		}

		// 3.Populate the top k frequent elements
		List<String> result = new ArrayList<>();
		for (int i = max; i > 0 && result.size() < k; i--) {
			if (buckets[i] != null) {
				Collections.sort(buckets[i]); // Lexical order within same frequency - O(m log m)
				for (String word : buckets[i]) {
					if (result.size() >= k) break;
					result.add(word);
				}
			}
		}

		return result;
	}

	/*
	 *  Sort Characters By Frequency:
	 *  Given a string, sort it in decreasing order based on the frequency of characters.
	 *  Example 1:	Input: "tree";  Output: "eert"
	 *  Explanation: 'e' appears twice while 'r' and 't' both appear once.
	 *  So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
	 */
	// Using Priority Queue
	public String frequencySort1(String s) {
		int n = s.length();
		if (n <= 1) return s;

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int count = map.getOrDefault(s.charAt(i), 0);
			map.put(s.charAt(i), count + 1);
		}

		PriorityQueue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
		queue.addAll(map.entrySet());

		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Map.Entry<Character, Integer> entry = queue.poll();
			for (int i = 0; i < (int) entry.getValue(); i++)
				sb.append(entry.getKey());
		}

		return sb.toString();
	}

	public String frequencySort2(String s) {
		int n = s.length();
		if (n <= 1) return s;

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int count = map.getOrDefault(s.charAt(i), 0);
			map.put(s.charAt(i), count + 1);
		}

		List<Character>[] bucket = new ArrayList[s.length() + 1];
		for (char ch : map.keySet()) {
			int freq = map.get(ch);
			if (bucket[freq] == null) bucket[freq] = new ArrayList<>();
			bucket[freq].add(ch);
		}

		StringBuilder sb = new StringBuilder();
		for (int freq = s.length(); freq > 0; freq--) {
			if (bucket[freq] != null) {
				for (char ch : bucket[freq])
					for (int i = 0; i < freq; i++)
						sb.append(ch);
			}
		}
		return sb.toString();
	}

	/********************** 2.K elements/K Closest Elements Problems *************************/

	//K Closest Points to Origin â€“ Solved using Kth element pattern
	/*
	 * 'K' Closest Points to the Origin: 
	 *  We have a list of points on the plane.  Find the K closest points to the origin (0, 0). (Here, the distance between two 
	 *  points on a plane is the Euclidean distance.)
	 *  You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
	 *  
	 *  Example 1: Input: points = [[1,3],[-2,2]], K = 1; Output: [[-2,2]]
	 *  Explanation: The distance between (1, 3) and the origin is sqrt(10).
	 *  			 The distance between (-2, 2) and the origin is sqrt(8).
	 *  Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin. We only want the closest K = 1 points from the origin, so the
	 *  answer is just [[-2,2]].
	 */

	/* Note: Euclidean Distance Formula: Sqrt((x2-x1)^2 + (y2-y1)^2). But in this problem distance calculates from origin(0,0). 
	 * Simplified formula formula will be: sqrt((x2)^2 + (y2)^2)    
	 */

	//Sort:  O(NlogN),
	public int[][] kClosest1(int[][] points, int K) {
		//Sort in ascending order based on each distance from origin(0,0).
		Arrays.sort(points, (p1, p2) -> (getDistance(p1) - getDistance(p2)));
		return Arrays.copyOfRange(points, 0, K);
	}

	//Using Heap: Time: O(nlogk), space: O(k)
	public int[][] kClosest2(int[][] points, int k) {
		//Sort in descending order
		PriorityQueue<int[]> queue = new PriorityQueue<>((p1, p2) -> (getDistance(p2)) - getDistance(p1));

		for (int[] point : points) {
			queue.add(point);
			if (queue.size() > k) queue.poll();
		}
		int[][] result = new int[k][2];

		for (int i = 0; i < k; i++)
			result[i] = queue.poll();
		return result;
	}

	/* Approach3: Using QuickSelect: Avg Time complexity: O(N) & Worst Case: O(N^2)
	 * Explanation: Ideally, in first iteration it will run n times, in the second iteration I will n/2 times, in the third iteration I will run n/4.... , therefore
	 * sum(n + n/2 + n/4 + ...) = O(n), here I have to do logN iterations.
	 * But in worst case, sum(n + n - 1 + n - 2 +.... ) = O(n^2), here I have to do N iterations
	 */
	public int[][] kClosest3(int[][] points, int K) {
		int len = points.length, l = 0, r = len - 1;
		while (l <= r) {
			int mid = helper(points, l, r);
			if (mid == K - 1) break;
			if (mid < K) {
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return Arrays.copyOfRange(points, 0, K);
	}

	private int helper(int[][] A, int l, int r) {
		int[] pivot = A[r];
		int i = l, j = l;
		while (j < r) {
			if (compare(A[j], pivot) < 0) {
				swap(A, i, j);
				i++;
			}
			j++;
		}
		swap(A, i, r);

		return i;
	}

	private int compare(int[] p1, int[] p2) {
		return getDistance(p1) - getDistance(p2);
	}

	//Distance calculated from origin (0,0):
	//(x2)^2 + (y2)^2 calculation; sqrt has been ignored
	private int getDistance(int[] point) {
		return point[0] * point[0] + point[1] * point[1];
	}

	private void swap(int[][] A, int i, int j) {
		int[] temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	/* Find K Pairs with Smallest Sums: - Solved using both Kth element approach and K-way merge approach
	 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
	 * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
	 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
	 * 	Example 1: Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3	Output: [[1,2],[1,4],[1,6]]
	 * 	Explanation: The first 3 pairs are returned from the sequence:
	 *          [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
	 */
	// Approach1: Brute Force using Binary Min Heap
	public List<int[]> kSmallestPairs1(int[] nums1, int[] nums2, int k) {
		List<int[]> result = new ArrayList<>();
		if (nums1.length == 0 || nums2.length == 0 || k == 0) return result;
		PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[2] - b[2]));
		for (int i = 0; i < nums1.length; i++)
			for (int j = 0; j < nums2.length; j++)
				minHeap.add(new int[] { nums1[i], nums2[j], nums1[i] + nums2[j] });

		while (k-- > 0 && minHeap.size() > 0) {
			int[] data = minHeap.poll();
			result.add(new int[] { data[0], data[1] });
		}
		return result;
	}

	// Approach2: Better Approach: Kth element approach
	public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
		PriorityQueue<Cell> pq = new PriorityQueue<Cell>((a, b) -> a.data - b.data);
		int m = nums1.length, n = nums2.length;
		List<int[]> res = new ArrayList<int[]>();
		if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) return res;

		for (int j = 0; j <= n - 1; j++)
			pq.offer(new Cell(0, j, nums1[0] + nums2[j]));

		for (int i = 0; i < Math.min(k, m * n); i++) {
			Cell t = pq.poll();
			res.add(new int[] { nums1[t.i], nums2[t.j] });
			if (t.i == m - 1) continue;
			pq.offer(new Cell(t.i + 1, t.j, nums1[t.i + 1] + nums2[t.j]));
		}
		return res;
	}

	// Approach3: Efficient Approach: K-way merge approach
	// Ref:https://leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/84551/simple-Java-O(KlogK)-solution-with-explanation
	public List<int[]> kSmallestPairs3(int[] nums1, int[] nums2, int k) {
		List<int[]> res = new ArrayList();
		if (nums1.length == 0 || nums2.length == 0 || k == 0) return res;
		// Heap -- n[0] x, n[1] y
		PriorityQueue<int[]> minIndexHeap = new PriorityQueue<>(
				(a, b) -> nums1[a[0]] + nums2[a[1]] - nums1[b[0]] - nums2[b[1]]);
		minIndexHeap.offer(new int[] { 0, 0 });
		int len1 = nums1.length, len2 = nums2.length;
		for (int i = 0; i < k && !minIndexHeap.isEmpty(); i++) {
			int[] min = minIndexHeap.poll();
			res.add(new int[] { nums1[min[0]], nums2[min[1]] });

			if (min[1] != len2 - 1) minIndexHeap.offer(new int[] { min[0], min[1] + 1 });

			if (min[1] == 0 && min[0] != len1 - 1) minIndexHeap.offer(new int[] { min[0] + 1, 0 });
		}
		return res;
	}

	ClosestNumberPatterns closestNumberPatterns;

	//Find K closest elements - Binary Search
	public void kclosestElements(int[] arr, int k, int x) {
		closestNumberPatterns.findKClosestElements1(arr, k, x);
		closestNumberPatterns.findKClosestElements2(arr, k, x);
		closestNumberPatterns.findKClosestElements3(arr, k, x);
	}

	//Closest Binary Search Tree Value II/Find K closest values in BST
	public void kClosestValuesInBST(TreeNode root, double target, int k) {
		closestNumberPatterns.closestKValues1(root, target, k);
		closestNumberPatterns.closestKValues2(root, target, k);
	}

}
