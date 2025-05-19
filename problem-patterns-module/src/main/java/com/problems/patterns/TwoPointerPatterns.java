package com.problems.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.problems.patterns.crossdomains.ClosestNumberPatterns;
import com.problems.patterns.ds.StackPatterns;

public class TwoPointerPatterns {

	/*************************** Sorted: Two Pointer ************************/
	// Two Sum: Given an array of integers, return indices of the two numbers such that they add up to a specific target.
	// 1. Brute force approach: Time Complexity:(n^2)
	public int[] twoSum1(int[] nums, int target) {
		int[] result = new int[] { 1, 2 };
		for (int i = 0; i < nums.length - 1; i++) {
			result[0] = i;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					result[1] = j;
					//return result;
					return new int[] { i, j };
				}
			}
		}
		return result;
	}

	// 2.Two Ptr Approach: Time Complexity: O(n) for sorted array; o(nlogn) for unsorted array;
	// This works only if array is sorted
	public int[] twoSum2(int[] nums, int target) {
		int[] result = new int[2];
		int l = 0, h = nums.length - 1, currSum = 0;
		while (l < h) {
			currSum = nums[l] + nums[h];
			if (currSum == target) {
				result[0] = l;
				result[1] = h;
				break;
			} else if (currSum > target) {
				h--;
			} else {
				l++;
			}
		}
		return result;
	}

	// 3.Two Ptr Approach: Time Complexity: O(nlogn) for both sorted and unsorted array;
	public int[] twoSum3(int[] arr, int k) {
		int n = arr.length;
		int[] result = new int[2];

		Arrays.sort(arr);
		for (int i = 0; i < n - 1; i++) {
			int index = Arrays.binarySearch(arr, i + 1, n, k - arr[i]);
			if (index >= 0) {
				result[0] = i;
				result[1] = index;
				break;
			}
		}
		return result;
	}

	// Approach4: Using Hashing: Time complexity : O(n) 
	//for sorted & unsorted
	public int[] twoSum4(int[] nums, int target) {
		int[] result = new int[2];
		Arrays.fill(result, -1);
		Map<Integer, Integer> map = new HashMap<>(); // Value, Index
		for (int i = 0; i < nums.length; i++) {
			if (map.get(nums[i]) != null) {
				result[0] = map.get(nums[i]);
				result[1] = i;
				break;
			} else {
				map.put(target - nums[i], i);
			}
		}
		return result;
	}

	// Find a triplet that sum to a given value
	// 1.Brute Force Approach: Time Complexity-O(n^3)
	public boolean tripletSum1(int[] a, int sum) {
		int n = a.length;
		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 1; j < n - 1; j++) {
				for (int k = j + 1; k < n; k++) {
					if (a[i] + a[j] + a[k] == sum) {
						System.out.println("Triplet is: " + a[i] + ", " + a[j] + ", " + a[k]);
						return true;
					}
				}
			}
		}
		return false;
	}

	// 2. Using sorting and two ptr approach - Time Complexity: O(nlogn) + O(n^2) => O(n^2)
	public boolean tripletSum2(int[] a, int sum) {
		int n = a.length;

		// 1.Sort the given array
		Arrays.sort(a);

		// 2. Take element one by one from 0th the index
		for (int i = 0; i < n - 2; i++) {

			// 3. Find remaining two elements using two ptr alg
			int l = i + 1, h = n - 1;
			while (l < h) {
				int currVal = a[i] + a[l] + a[h];
				if (currVal == sum) {
					return true;
				} else if (currVal > sum) {
					h--;
				} else {
					l++;
				}
			}
		}

		return false;
	}

	// 3. Using Hash Based approach - Time Complexity: O(n^2)
	public boolean tripletSum3(int[] a, int sum) {
		int n = a.length;

		// 1. Take element one by one from 0th the index
		for (int i = 0; i < n - 2; i++) {
			// 3. Find remaining two elements using hash DS
			//if (sumPresent2(a, i + 1, n - 1, a[i], sum - a[i])) return true;
			HashSet<Integer> set = new HashSet<>();
			for (int j = i + 1; j <= n - 1; i++) {
				if (set.contains(sum - a[i] - a[j])) {
					return true;
				} else {
					set.add(a[j]);
				}
			}
		}

		return false;
	}

	/*
	 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
	 * Notice that the solution set must not contain duplicate triplets.
	 * Example 1:
	 * Input: nums = [-1,0,1,2,-1,-4], Output: [[-1,-1,2],[-1,0,1]]
	 */
	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> triplets = new ArrayList<>();
		if (nums.length < 3) return triplets;

		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 2; i++) {
			if (i > 0 && nums[i] == nums[i - 1]) continue;

			int l = i + 1, h = nums.length - 1;
			while (l < h) {
				int sum = nums[i] + nums[l] + nums[h];
				if (sum == 0) {
					triplets.add(Arrays.asList(nums[i], nums[l], nums[h]));
					while (l < h && nums[l] == nums[l + 1]) l++;
					while (l < h && nums[h] == nums[h - 1]) h--;
					l++;
					h--;
				} else if (sum < 0) {
					l++;
				} else {
					h--;
				}
			}
		}

		return triplets;
	}

	ClosestNumberPatterns closestNumberPatterns = new ClosestNumberPatterns();

	/* 3Sum Closest:
	 * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest
	 * to target. Return the sum of the three integers. You may assume that each input would have exactly one 
	 */
	public int threeSumClosest(int[] nums, int target) {
		return closestNumberPatterns.threeSumClosest(nums, target);
	}

	/******************* UnSorted: Forward & Reverse Traversals ****************/

	/*Trapping rain water:
	 * Height of water = Minimum(tallest left hand bar & tallest right hand bar) - height of current bar 
	 */
	// Approach1 : Time Complexity: O(n) and takes Auxiliary Space: O(n)
	public int trappingRainWater1(int[] a) {
		int n = a.length;
		int[] tallestLeftArr = new int[n];
		int[] tallestRightArr = new int[n];

		// Find the tallest bar from the left side
		tallestLeftArr[0] = a[0];
		for (int i = 1; i < n; i++)
			tallestLeftArr[i] = Math.max(tallestLeftArr[i - 1], a[i]);

		// Find the tallest bar from the right side
		tallestRightArr[n - 1] = a[n - 1];
		for (int i = n - 2; i >= 0; i--)
			tallestRightArr[i] = Math.max(tallestRightArr[i + 1], a[i]);

		// Find minimum from tallestLeftArr & tallestRightAr and then subtract with current bar
		int sum = 0;
		for (int i = 0; i < n; i++)
			sum += (Math.min(tallestLeftArr[i], tallestRightArr[i]) - a[i]);

		return sum;
	}

	// Its similar to approach1 without any additional space
	// Time Complexity: O(n) without Auxiliary Space
	public int trappingRainWater2(int[] a) {
		int sum = 0;
		int l = 0, h = a.length - 1;
		int tallestLeft = 0, tallestRight = 0;
		while (l < h) {
			// Find the tallest bar from the left side & right side and execute corresponding block
			if (a[l] < a[h]) {
				// If current index is less than tallest left bar then add in the sum, otherwise keep updating the
				// tallestLeft
				if (a[l] > tallestLeft) tallestLeft = a[l];
				else sum += tallestLeft - a[l];

				l++;
			} else {
				// If current index is less than tallest right bar then add in the sum, otherwise keep updating the
				// tallestRight
				if (a[h] > tallestRight) tallestRight = a[h]; // Update the max right
				else sum += tallestRight - a[h];

				h--;
			}
		}
		return sum;
	}

	//Using Stack - Monotonic Stack Pattern
	public int trap(int[] height) {
		StackPatterns ob = new StackPatterns();
		return ob.trappingRainWater(height);
	}

	/* Container With Most Water: 
	 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical
	 * lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together
	 * with x-axis forms a container, such that the container contains the most water.
	 * 
	 * Solution:
	 * 	Area = height * width;
	 * 	height = Min(height[l], height[h]);
	 * 	width = difference b/w l and h. (h-l);
	 */
	public int maxArea(int[] height) {
		if (height.length <= 1) return 0;

		int l = 0, h = height.length - 1, max = 0, minHeight = 0;
		while (l < h) {
			minHeight = Math.min(height[l], height[h]);
			max = Math.max(max, minHeight * (h - l));
			if (height[l] < height[h]) l++;
			else h--;
		}
		return max;
	}

	public static void main(String[] args) {
		TwoPointerPatterns ob = new TwoPointerPatterns();
		int[] nums = { 1, 3, 5, 7, 8, 12, 15, 19 };
		System.out.println(Arrays.toString(ob.twoSum2(nums, 31)));
		System.out.println(Arrays.toString(ob.twoSum3(nums, 31)));

	}
}