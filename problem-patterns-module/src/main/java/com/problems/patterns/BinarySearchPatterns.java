
package com.problems.patterns;

import java.util.Arrays;

/*
 * Binary Search Patterns:
 * 
 * Note: 
 *  If target is mid element(t==a[m]) return mid index
 *  If target presents left side(t<a[m]), h = m-1
 *  If target presents right side(a[m]<t), l = m+1
 * 
 * 	Mostly l=m+1, h=m -> This condition allows to move the index
 *  But l=m, h=m-1 -> This condition doesnt move and loop runs without exit. 
 *  
 * Overflow condition: 
 *   m = l + (h-l)/2
 */
public class BinarySearchPatterns {

	// Search Insert Position
	public int searchInsert(int[] nums, int target) {
		int l = 0, h = nums.length - 1, m = 0;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (target == nums[m]) return m;
			else if (target < nums[m]) h = m - 1;
			else l = m + 1;
		}
		return l;
	}

	public int searchInsert2(int[] a, int target) {
		//Here h = a.length(size of the array). not a.length-1.
		int l = 0, h = a.length, m = 0;
		while (l < h) {
			m = l + (h - l) / 2;
			if (target > a[m]) {
				l = m + 1;
			} else {
				h = m;
			}
		}
		return l;
	}

	// Search for a Range/Find First and Last Position of Element in Sorted Array/Total Occurrences Of K In A Sorted Array
	// Approach1:
	public int[] searchRange1(int[] nums, int target) {
		int left = findIndex(nums, target, true);
		if (left == -1) return new int[] { -1, -1 };

		int right = findIndex(nums, target, false);
		return new int[] { left, right };
	}

	private int findIndex(int[] a, int x, boolean left) {
		int l = 0, h = a.length - 1, m = 0, index = -1;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (x == a[m]) {
				index = m;
				if (left) {
					h = m - 1;
				} else {
					l = m + 1;
				}
			} else if (x < a[m]) {
				h = m - 1;
			} else {
				l = m + 1;
			}
		}

		return index;
	}

	public int[] searchRange2(int[] nums, int target) {
		int[] range = { -1, -1 };
		int leftIndex = binarySearch(nums, target, true);

		if (leftIndex == nums.length || nums[leftIndex] != target) return range;

		// Modification: use rightIndex-1
		int rightIndex = binarySearch(nums, target, false) - 1;
		range[0] = leftIndex;
		range[1] = rightIndex;

		return range;
	}

	public int binarySearch(int[] nums, int x, boolean left) {
		// Modification: use h = nums.length
		int l = 0, h = nums.length, m = 0;
		while (l < h) {
			m = l + (h - l) / 2;
			// Modification
			if (x == nums[m]) {
				if (left) h = m;
				else l = m + 1;
			} else if (x < nums[m]) {
				h = m;
			} else {
				l = m + 1;
			}
		}
		return l;
	}

	// Find Minimum in Rotated Sorted Array
	public int findMinRotatedSortedArray1(int[] nums) {
		int l = 0, h = nums.length - 1, m = 0;

		while (l < h) {
			m = l + (h - l) / 2;

			//Assign l=m+1, only when mid element is greater than left or right. Eg: a[m] > a[h] or a[m] > a[l]
			if (nums[m] > nums[h]) l = m + 1;
			else h = m;
		}
		return nums[h]; // or nums[l]
	}

	// Find Minimum in Rotated Sorted Array II
	public int findMinRotatedSortedArray2(int[] nums) {
		int l = 0, h = nums.length - 1, m = 0;

		while (l < h) {
			m = l + (h - l) / 2;

			if (nums[m] > nums[h]) l = m + 1;
			else if (nums[m] < nums[h]) h = m;
			else h--;
		}
		return nums[h]; // or nums[l]
	}

	// Search in Rotated Sorted Array
	public int searchRotatedSortedArray1(int[] nums, int target) {
		int l = 0, h = nums.length - 1, m = 0;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (target == nums[m]) return m;

			//Here choose the sorted part and search the element.
			if (nums[m] < nums[h]) { // right part is sorted
				if (target > nums[m] && target <= nums[h]) { // Check target between m to h, if so l = m+1
					l = m + 1;
				} else { // Reverse of if condition
					h = m - 1;
				}
			} else { // left part is sorted
				if (target >= nums[l] && target < nums[m]) { // Check target between l to m, if so h = m-1
					h = m - 1;
				} else { // Reverse of if condition
					l = m + 1;
				}
			}
		}
		return -1;
	}

	// Search in Rotated Sorted Array II
	public boolean searchRotatedSortedArray2(int[] nums, int target) {
		int l = 0, h = nums.length - 1, m = 0;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (target == nums[m]) return true;

			if (nums[m] < nums[h]) {
				if (target > nums[m] && target <= nums[h]) { // Check target between m to h, if so l = m+1
					l = m + 1;
				} else { // Reverse of if condition
					h = m - 1;
				}
			} else if (nums[m] > nums[h]) {
				if (target >= nums[l] && target < nums[m]) { // Check target between l to m, if so h = m-1
					h = m - 1;
				} else { // Reverse of if condition
					l = m + 1;
				}
			} else {
				h--;
			}
		}
		return false;
	}

	/******** BS Problems - Good to have ***************/

	/*
	 * Single Element in a Sorted Array:
	 * Given a sorted array consisting of only integers where every element appears twice except
	 * for one element which appears once. Find this single element that appears only once.
	 * Expected Time Complexity: O(logn)
	 */
	public int singleNonDuplicate1(int[] nums) {
		int l = 0, h = nums.length - 1;
		while (l < h) {
			int m = (l + h) / 2;
			// Non duplicate present in second half
			if ((m % 2 == 0 && nums[m] == nums[m + 1]) || (m % 2 == 1 && nums[m - 1] == nums[m])) l = m + 1;
			else h = m; // Non duplicate present in first half
		}

		return nums[l];
	}

	// Another Binary Search Approach
	/*My solution using binary search. lo and hi are not regular index, but the pair index here. 
	 * Basically you want to find the first even-index number not followed by the same number.*/
	public int singleNonDuplicate2(int[] nums) {
		// binary search
		int n = nums.length, lo = 0, hi = n / 2;
		while (lo < hi) {
			int m = (lo + hi) / 2;
			if (nums[2 * m] != nums[2 * m + 1]) hi = m;
			else lo = m + 1;
		}
		return nums[2 * lo];
	}

	// Binary Search Approach: O(nlogn)
	// Note: If data is already sorted, binary search will be efficient approach
	public int missingNumber(int[] nums) {
		Arrays.sort(nums);
		int l = 0, h = nums.length, m = 0;
		while (l < h) {
			m = (l + h) / 2;
			// Modification: Compare mth index with m element.
			if (m < nums[m]) h = m;
			else l = m + 1;
		}
		return l;
	}

	// Find the Duplicate Number
	// Approach4: Binary Search - Time:O(nlogn)
	public int findDuplicate2(int[] nums) {
		int low = 1, high = nums.length - 1;
		while (low <= high) {
			int mid = (int) (low + (high - low) * 0.5);
			int cnt = 0;
			for (int a : nums) {
				if (a <= mid) ++cnt;
			}
			if (cnt <= mid) low = mid + 1;
			else high = mid - 1;
		}
		return low;
	}

	/* Find Peak Element:
	 * 	Here is an observation: Any element in the array can lead us to a peak. Look at any element in the array. It's slope
	 *  is either tilted one way or the other. If we follow the slope up, we will eventually reach a peak element. There are 
	 *  a couple exceptions of course - if an element is a peak or a valley. If it's a peak, we've found a result. 
	 *  If it's a valley (opposite of peak), we can go either direction - there's a peak on either side.
	 *  
	 *  So if we sample any element of the array, we can confidently say that there is a peak element either on it's left, 
	 *  or it's right. This smells of binary search.
	 *  We look at an element a[mid],
	 *  	If the element is sloping up towards the left, we only consider left of mid. i.e h=m-1
	 *  	If sloping up towards right, we only consider right of mid. i.e l=m+1
	 *  	If it's a valley, we can go either direction. i.e l=m+1 or h=m-1
	 *  	If it's a peak, we return the result. 
	 */
	public int findPeakElement(int[] nums) {
		int l = 0, h = nums.length - 1, m = 0;
		while (l <= h) {
			m = l + (h - l) / 2;
			int left = m > 0 ? nums[m - 1] : Integer.MIN_VALUE;
			int right = m < nums.length - 1 ? nums[m + 1] : Integer.MIN_VALUE;

			if (left > nums[m]) { //Sloping up left and Valley
				h = m - 1;
			} else if (nums[m] < right) {//Sloping up right
				l = m + 1;
			} else { //Peak
				return m;
			}
		}
		return 0;
	}

}