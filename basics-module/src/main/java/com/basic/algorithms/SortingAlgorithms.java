package com.basic.algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import com.basic.algorithms.operations.SortOperations;
import com.common.model.ListNode;
import com.common.utilities.Utils;

/*
 * Sorting Algorithms:
 *   - Bubble Sort
 *   - Selection Sort
 *   - Insertion Sort
 *   - Merge Sort
 *   - Quick Sort 
 *   - Heap Sort
 *   - Counting Sort
 *   - Bucket Sort
 *   - Radix Sort
 *   - Tim Sort - Derived from merge sort and insertion sort 
 * 
 * Sorting Algorithm Categories:
 *  Decrease and Conquer: Selection, Insertion Sort
 *  Divide and Conquer: Quick, Merge Sort
 *  Transform and Conquer: Heap Sort
 *  
 * Stable Vs Unstable Sorting:
 * A stable sort maintains the relative order of records with equal keys (values).Eg: Bubble Sort, Insertion Sort, Merge Sort
 * An unstable sort does not guarantee the relative order of equal elements. Eg: Quick Sort, Heap Sort
 * When Does Stability Matter? When sorting complex data with multiple fields (e.g., sort by last name, then first name).
 * 
 * Practice Sorting Algorithm here - https://leetcode.com/problems/sort-an-array/
 * 
 */
public class SortingAlgorithms implements SortOperations {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		int[] a = null;
		SortingAlgorithms sort = new SortingAlgorithms();
		do {
			System.out.println("Sorting Algorithms:");
			System.out.println("1.Bubble Sort");
			System.out.println("2.Selection Sort");
			System.out.println("3.Insertion Sort");
			System.out.println("4.Merge Sort");
			System.out.println("5.Merge Sort for Linked List");
			System.out.println("6.Quick Sort");
			System.out.println("7.Heap Sort");
			System.out.println("8.Counting Sort");
			System.out.println("9.Radix Sort");
			System.out.println("10.Bucket Sort");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {
			case 1:
				a = sort.insert(in);
				// sort.bubbleSort(a);
				sort.bubbleSortRecursive(a);
				break;
			case 2:
				a = sort.insert(in);
				sort.selectionSort(a);
				break;
			case 3:
				a = sort.insert(in);
				// sort.insertionSort(a.length, a);
				sort.insertionSortRecursive(a);
				break;
			case 4:
				a = sort.insert(in);
				sort.mergeSort1(a);
				//sort.mergeSort2(a); //using helper array
				break;
			case 5:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				ListNode head = null;
				for (int i = 0; i < t; i++)
					head = sort.insertLL(head, in.nextInt());
				head = sort.mergeSort(head);
				sort.displayLL(head);
				break;
			case 6:
				a = sort.insert(in);
				sort.quickSort(a);
				break;
			case 7:
				a = sort.insert(in);
				sort.heapSort(a);
				break;
			case 8:
				char arr[] = { 'g', 'e', 'e', 'k', 's', 'f', 'o', 'r', 'g', 'e', 'e', 'k', 's', 'a', 'z' };
				countingSort(arr);
				System.out.println("Sorted Array:");
				for (int i = 0; i < arr.length; i++)
					System.out.print(arr[i]);
				System.out.println();
			case 9:
				a = sort.insert(in);
				sort.radixSort(a);
				break;
			case 10:
				float[] arr2 = sort.insertFloat(in);
				sort.bucketSort(arr2);
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;
			}
			if (a != null) {
				System.out.println("\nDisplay:");
				sort.display(a);
			}
			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}

	// Bubble sort - start
	public void bubbleSort(int[] a) {
		boolean swapFlag;
		int count = 0;
		if (a.length == 1) return;

		for (int i = 1; i < a.length; i++) {
			swapFlag = false;
			count++;
			for (int j = 0; j < a.length - i; j++) {
				if (a[j] > a[j + 1]) {
					Utils.swap(a, j, j + 1);
					swapFlag = true;
				}
			}
			System.out.println("Iteration Count: " + count);
			if (!swapFlag) {
				System.out.println("No swapping, i.e array sorted");
				break;
			}
		}
	}

	public void bubbleSortRecursive(int[] a) {
		bubbleSortAlg(a, a.length);
	}

	private void bubbleSortAlg(int[] a, int n) {
		if (n == 1) return;

		/*for (int j = 0; j < n - 1; j++) {
			if (a[j] > a[j + 1]) {
				a = swap(a, j, j + 1);
			}
		}*/

		compare(a, 0, n - 1); // Bubble sort using only recursive function; without iteration
		bubbleSortAlg(a, n - 1);
	}

	void compare(int[] a, int l, int r) {
		if (l >= r) return;
		if (a[l] > a[l + 1]) Utils.swap(a, l, l + 1);
		compare(a, l + 1, r);
	}
	// Bubble sort - end

	// Selection - start
	public void selectionSort(int[] a) {
		int minIndex;
		for (int i = 0; i < a.length; i++) {
			minIndex = i;
			for (int j = i; j < a.length; j++) {
				if (a[j] < a[minIndex]) minIndex = j;
			}
			Utils.swap(a, minIndex, i);
			Utils.swap(a, minIndex, i);
		}
	}
	// Selection - end

	/* Pancake Sort Algorithm: 
	 *    The idea is to do something similar to Selection Sort. We one by one place maximum element at the end and reduce the size of
	 *  current array by one.
	 *  Following are the detailed steps. Let given array be arr[] and size of array be n.
	 *      1) Start from current size equal to n-1 and reduce current size by one while itï¿½s greater than 0. Let the current size be
	 *         curr_size. Do following for every curr_size
	 *           a) Find index of the maximum element in arr[0..curr_szie-1]. Let the index be ï¿½miï¿½
	 *           b) Call flip(arr, mi) 
	 *           c) Call flip(arr, curr_size-1)
	 */
	public void pancakeSort(int[] arr) {
		int n = arr.length;
		for (int i = n - 1; i > 0; i--) {
			int maxIndex = findMax(arr, i);
			if (maxIndex == i) continue;
			flip(arr, maxIndex);
			flip(arr, i);
		}
	}

	public int findMax(int[] arr, int end) {
		int i = 1, maxIndex = 0;
		while (i <= end) {
			if (arr[i] > arr[maxIndex]) maxIndex = i;
			i++;
		}
		return maxIndex;
	}

	public void flip(int[] arr, int k) {
		int i = 0;
		while (i < k) {
			Utils.swap(arr, i, k);
			i++;
			k--;
		}
	}

	// Insertion - start
	/*public void insertionSort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			for (int j = 0; j < i; j++) {
				if (a[i] < a[j])
					swap(a, i, j);
			}
			display(a);
			System.out.println();
		}
	}*/

	// There is no swap in the Insertion sort.
	public void insertionSort(int[] a) {
		int j, lastElement;
		for (int i = 1; i < a.length; i++) {
			lastElement = a[i];
			for (j = i; j > 0 && a[j - 1] > lastElement; j--) {
				a[j] = a[j - 1];
			}
			a[j] = lastElement;
		}
	}

	public void insertionSortRecursive(int[] a) {
		insertionSortAlg(a, a.length - 1);
	}

	private void insertionSortAlg(int[] a, int n) {
		if (n == 0) return;

		insertionSortAlg(a, n - 1);

		int j;
		int lastElement = a[n];
		for (j = n; j > 0 && a[j - 1] > lastElement; j--) {
			a[j] = a[j - 1];
		}
		a[j] = lastElement;
	}

	@Override
	public void insertionSort(ListNode head) {
		// TODO Auto-generated method stub
	}
	// Insertion - end

	// Merge sort - start
	/*
	 * Merge sort is a divide-and-conquer algorithm based on the idea of breaking down a list into several sub-lists until each sublist
	 * consists of a single element and merging those sublists in a manner that results into a sorted list.
	 * Time Complexity: O(n log n) (worst, average, and best case)
	 * Space Complexity: O(n) (not in-place)
	 * Stable: Yes (Means =)
	 */
	@Override
	public void mergeSort(int[] a) {
		//Approach 1: 
		mergeSort1(a);

		//Approach 2:
		mergeSort2(a);

	}

	public void mergeSort1(int[] a) {
		divide(a, 0, a.length - 1);
	}

	private void divide(int[] a, int l, int r) {
		if (l >= r) return;

		int m = (l + r) / 2;
		divide(a, l, m);
		divide(a, m + 1, r);
		// Merge sub arrays, used 2 arrays
		merge(a, l, m, r);

	}

	private void merge(int[] a, int l, int m, int r) {
		/*int leftSize = m - l + 1; // left to middle
		int rightSize = r - m; // middle+1 to rightIndex
		int[] left = new int[leftSize];
		int[] right = new int[rightSize];
		// Copy the elements index from l to m into left[] array
		for (int i = 0; i < leftSize; i++)
			left[i] = a[l + i];
		// Copy the elements index from m+1 to r into right[] array
		for (int i = 0; i < rightSize; i++)
			right[i] = a[m + 1 + i];
		*/
		//or
		int[] left = Arrays.copyOfRange(a, l, m + 1); //Copy index from l to m
		int[] right = Arrays.copyOfRange(a, m + 1, r + 1); //Copy index from m+1 to r

		int leftIndex = 0, rightIndex = 0, curr = l;
		// Merge the elements in asc order
		while (leftIndex < left.length && rightIndex < right.length)
			a[curr++] = (left[leftIndex] <= right[rightIndex]) ? left[leftIndex++] : right[rightIndex++];

		// Copy the remaining left side elements into array
		while (leftIndex < left.length) a[curr++] = left[leftIndex++];

		// The right half doesn't need to be copied because it's already there.
		// Copy the remaining right side elements into array
		/*while (rightIndex < rightSize)
			a[curr++] = right[rightIndex++];*/

	}

	//Similar to above but created static helper array once and created in first method
	public void mergeSort2(int[] a) {
		int[] helper = new int[a.length];
		divide2(a, 0, a.length - 1, helper);
	}

	private void divide2(int[] a, int l, int r, int[] helper) {
		if (l >= r) return;

		int m = (l + r) / 2;
		divide2(a, l, m, helper);
		divide2(a, m + 1, r, helper);
		// Merge sub arrays, used 1 array
		merge2(a, l, m, r, helper);

	}

	private void merge2(int[] a, int l, int m, int h, int[] helper) {
		for (int i = l; i <= h; i++)
			helper[i] = a[i];

		//l - left subarray start index, m - left subarray end index
		//rs - right subarray start index, h - right subarray end index  
		int curr = l, rs = m + 1;

		while (l <= m && rs <= h) {
			a[curr++] = (helper[l] <= helper[rs]) ? helper[l++] : helper[rs++];
		}

		// Copy the remaining left side elements into array
		while (l <= m) a[curr++] = helper[l++];

		// The right half doesn't need to be copied because it's already there.
		// Copy the remaining right side elements into array
		/*while (right <= r)
			a[curr++] = helper[right++];*/
	}

	/*
	 * Merge Sort: Merge sort for Linked List data structure
	 */
	@Override
	public ListNode mergeSort(ListNode head) {
		return divideLL(head);
	}

	private ListNode divideLL(ListNode head) {
		if (head == null || head.next == null) return head;

		ListNode middle = middleNode(head);
		ListNode middleNext = middle.next;
		middle.next = null;

		ListNode left = divideLL(head);
		ListNode right = divideLL(middleNext);

		return mergeLL(left, right);
	}

	private ListNode middleNode(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr != null && fastPtr.next != null && fastPtr.next.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		return slowPtr;
	}

	private ListNode mergeLL(ListNode left, ListNode right) {
		ListNode result;
		if (left == null) return right;
		if (right == null) return left;

		if (left.data < right.data) {
			result = left;
			result.next = mergeLL(left.next, right);
		} else {
			result = right;
			result.next = mergeLL(left, right.next);
		}
		return result;
	}

	//This insert linked list method is used to test the merge sort
	public ListNode insertLL(ListNode head, int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
		} else {
			ListNode temp = head;
			while (temp.next != null) temp = temp.next;
			temp.next = newNode;
		}
		return head;
	}

	public void displayLL(ListNode head) {
		if (head != null) {
			while (head != null) {
				System.out.print(head.data + " ");
				head = head.next;
			}
		}
	}
	// Merge sort - end

	// Quick sort - start
	/*
	 * Quick sort is based on the divide-and-conquer approach based on the idea of choosing one element as a pivot element and 
	 * partitioning the array around it such that: Left side of pivot contains all the elements that are less than the pivot element
	 * Right side contains all elements greater than the pivot
	 * Quick Sort is fast because that algorithm is randomized.
	 * 
	 * Time Complexity: Best/Average: O(n log n), Worst: O(n^2) (if pivot choice is poor) 
	 * Space Complexity: O(log n) (due to recursion)
	 * 
	 * 
	 * Pivot Element Choice: Last Element Vs Random Vs Median
	 * 	1.Last Element:
	 * 		- Always picks the last element as pivot. Easy to implement, but poor with sorted or reverse-sorted data.
	 * 		- Best For Simple examples, learning purposes.
	 *		- Worst Case Time Complexity: O(n^2)
	 * 	2.Random:
	 * 		- Picks a random index in the range [low, high] as pivot. Reduces chances of worst-case. Performs well in practice.
	 * 		- Best For General/random input.
	 *		- Avg Time Complexity: O(n log n). Minimizes the chance of hitting worst-case.
	 * 	3.Median:
	 * 		- Uses the median of first, middle, and last elements as the pivot.	More balanced partitions; good for improving performance and stability.
	 * 		- Best for Partially sorted or patterned arrays
	 * 		- Avg Time Complexity: O(n log n). Minimizes the chance of hitting worst-case.
	 *  Why Pivot Choice Matters?
	 *  	- The efficiency of Quick Sort depends heavily on how balanced the partitioning is.
	 *  	- A bad pivot (always smallest or largest element) leads to unbalanced partitions, and thus O(n²) time.
	 */
	public void quickSort(int[] a) {
		qSort(a, 0, a.length - 1);
	}

	private void qSort(int[] a, int left, int right) {
		if (left >= right) return;

		// Option1: Last Element
		int mid = partition(a, left, right);

		// Option2: Random
		// int mid = randomPartition(a, left, right);

		// Option3: Median
		//int mid = medianPartition(a, left, right);

		qSort(a, left, mid - 1);
		qSort(a, mid + 1, right);
	}

	private int randomPartition(int[] arr, int left, int right) {
		Random random = new Random();
		int pivotIndex = random.nextInt(right - left + 1) + left;
		Utils.swap(arr, right, pivotIndex);
		return partition(arr, left, right);
	}

	private int medianPartition(int[] arr, int low, int high) {
		int mid = low + (high - low) / 2;

		// Sort low, mid, high to find median
		if (arr[low] > arr[mid]) Utils.swap(arr, low, mid);
		if (arr[low] > arr[high]) Utils.swap(arr, low, high);
		if (arr[mid] > arr[high]) Utils.swap(arr, mid, high);

		Utils.swap(arr, mid, high); // Use median as pivot
		return partition(arr, low, high);
	}

	private int partition(int[] a, int left, int right) {
		int pivot = a[right];
		int i = left, j = left;
		while (j < right) {
			if (a[j] < pivot) {
				Utils.swap(a, i, j);
				i++;
			}
			j++;
		}
		Utils.swap(a, i, right); // swap pivot element at the end
		return i;
	}
	// Quick sort - end

	/* Heap sort - start
	 * O(n) time for buildHeap and O(n log n) to remove each node in order, so the complexity is O(n log n).
	 * Time Complexity: O(n log n) (worst, average, and best case)
	 * Space Complexity: Space Complexity: O(1) (Iterative Approach in-place), Space Complexity: O(log n) (due to recursion)
	 * Stable: No
	 * Asc Order - Builds a max heap(MaxHeapify) and repeatedly extracts the maximum element., 
	 * Desc Order - Builds a min heap(MinHeapify) and repeatedly extracts the minimum element.
	 * 
	 */
	public void heapSort(int[] a) {
		int size = a.length;
		// Build heapify the given array - O(n) time for buildHeap
		for (int i = (size / 2) - 1; i >= 0; i--)
			maxHeapify(a, i, size);

		// One by one extract an element from heap
		for (int i = size - 1; i >= 0; i--) { // Time complexity: maxheapify for n times, its o(nlog(n))
			Utils.swap(a, 0, i); // Move max element (current root) to end
			maxHeapify(a, 0, i); // call max heapify on the reduced heap
			//maxHeapifyIterative(a, 0, i);
		}
	}

	// Heapify/Shift Down:
	// Time complexity for maxheapify is o(log(n))
	private void maxHeapify(int[] a, int startIndex, int size) {
		int leftIndex = 2 * startIndex + 1; // left child
		int rightIndex = 2 * startIndex + 2; // right child
		int parentIndex = startIndex; // Assume parent is the largest

		if (leftIndex < size && a[leftIndex] > a[parentIndex]) // Check left child with parent
			parentIndex = leftIndex;

		if (rightIndex < size && a[rightIndex] > a[parentIndex]) // Check right child with parent
			parentIndex = rightIndex;

		if (parentIndex != startIndex) {
			Utils.swap(a, parentIndex, startIndex); // Swap and continue heapifying
			maxHeapify(a, parentIndex, size); // Move down to the child node
		}
	}

	public static void maxHeapifyIterative(int[] a, int startIndex, int size) {
		while (true) {
			int leftIndex = 2 * startIndex + 1; // left child
			int rightIndex = 2 * startIndex + 2; // right child
			int parentIndex = startIndex; // Assume parent is the largest

			if (leftIndex < size && a[leftIndex] > a[parentIndex]) // Check left child with parent
				parentIndex = leftIndex;

			if (rightIndex < size && a[rightIndex] > a[parentIndex]) // Check right child with parent
				parentIndex = rightIndex;

			if (parentIndex != startIndex) {
				Utils.swap(a, parentIndex, startIndex); // Swap and continue heapifying
				startIndex = parentIndex; // Move down to the child node
			} else {
				break; // Heap property is satisfied
			}
		}
	}

	// Heap sort - end
	public int[] insert(Scanner in) {
		System.out.println("Enter no of elements to be inserted:");
		int t = in.nextInt();
		int[] a = new int[t];
		for (int i = 0; i < t; i++)
			a[i] = in.nextInt();
		return a;
	}

	public float[] insertFloat(Scanner in) {
		System.out.println("Enter no of elements to be inserted(Float):");
		int t = in.nextInt();
		float[] a = new float[t];
		for (int i = 0; i < t; i++)
			a[i] = in.nextFloat();
		return a;
	}

	public void display(int[] a) {
		Arrays.stream(a).forEach(ele -> System.out.print(ele + " "));
	}

	/*
	 * The counting sort is used if you just need to sort a list of integers. Rather than using a comparison, you create an integer array
	 *  whose index range covers the entire range of values in your array to sort. Each time a value occurs in the original array, you 
	 *  increment the counter at that index. At the end, run through your counting array, printing the value of each non-zero valued index
	 *  that number of times.
	 *  
	 *  Counting sort is a linear time sorting algorithm that sort in O(n+k) time when elements are in range from 1 to k.
	 *  The array arr[] is traversed in n time and the resulting sorted array is also computed in O(n) time.  count array is traversed 
	 *  in O(k) time. Therefore, the overall time complexity of counting sort algorithm is O(n+k).	
	 */
	public void countingSort(int[] arr, int k) {
		int[] countArray = new int[k]; // Here you can find the max vaue initialize the array using that value
		int count;
		// Count the elements int the array and set into new array; O(n) time
		for (int i = 0; i < arr.length; i++)
			countArray[arr[i]]++;

		// Arrange elements based on count array; O(k) time
		int index = 0;
		for (int i = 0; i < k; i++) {
			count = countArray[i];
			while (count-- > 0) arr[index++] = countArray[i];
		}

	}

	static char[] countingSort(char[] arr) {
		int[] countArray = new int[256];
		int count;
		for (int i = 0; i < arr.length; i++)
			countArray[arr[i]]++;

		int index = 0;
		for (int i = 0; i < countArray.length; i++) {
			count = countArray[i];
			while (count-- > 0) arr[index++] = (char) i;

		}
		return arr;
	}

	/*
	 * The lower bound for Comparison based sorting algorithm (Merge Sort, Heap Sort, Quick-Sort .. etc) is O(nLogn), 
	 * i.e., they cannot do better than nLogn.
	 * Counting sort is a linear time sorting algorithm that sort in O(n+k) time when elements are in range from 1 to k.
	 * What if the elements are in range from 1 to n2? We canï¿½t use counting sort because counting sort will take O(n2) which
	 * is worse than comparison based sorting algorithms. 
	 * Can we sort such an array in linear time? Radix Sort is the answer. 
	 * The idea of Radix Sort is to do digit by digit sort starting from least significant digit to most significant digit. 
	 * Radix sort uses counting sort as a subroutine to sort.
	 * 
	 * Time Complexity((n+b) * logb(n)) = O(nlogb(n)); where b- base; Value of b here is 10
	 */
	public void radixSort(int[] arr) {
		// Find the max value from the array
		int max = findMax(arr);

		for (int digit = 1; max / digit > 0; digit *= 10) { // O(logb(n) times
			countSort(arr, digit); // Counting sort takes O(n+b) time
			System.out.println("Counting sort (digits from LSB) : " + Arrays.toString(arr));
		}
	}

	// Count sort for radix sort
	void countSort(int arr[], int digit) {
		int[] freq = new int[10]; // Frequency Array capacity always be 10 for the values 0 to 9;
		int n = arr.length;
		int[] temp = new int[n];

		// Count the array values
		for (int i = 0; i < n; i++)
			freq[(arr[i] / digit) % 10]++;

		// Change freq[i] so that freq[i] now contains actual position of this digit in temp[] array
		for (int i = 1; i < 10; i++)
			freq[i] += freq[i - 1];

		// Arrange the element based on the index in the freq[] array
		for (int i = n - 1; i >= 0; i--) {
			int index = (arr[i] / digit) % 10;
			temp[freq[index] - 1] = arr[i];
			freq[index]--;
		}

		// Finally copy the value from temp[] to arr[]
		for (int i = 0; i < n; i++)
			arr[i] = temp[i];
	}

	public int findMax(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++)
			max = Math.max(max, arr[i]);
		return max;
	}

	/*
	 * Bucket sort is a comparison sort algorithm that operates on elements by dividing them into different buckets and then
	 * sorting these buckets individually. Each bucket is sorted individually using a separate sorting algorithm or by
	 * applying the bucket sort algorithm recursively. Bucket sort is mainly useful when the input is uniformly distributed
	 * over a range. 
	 * For example, consider the following problem. Sort a large set of floating point numbers which are in
	 * range from 0.0 to 1.0 and are uniformly distributed across the range. How do we sort the numbers efficiently?
	 * The average time complexity for Bucket Sort is O(n + k). The worst time complexity is O(n^2). 
	 * The space complexity for Bucket Sort is O(n+k).
	 */
	@Override
	public void bucketSort(float[] arr) {
		int n = arr.length;
		LinkedList<Float>[] buckets = new LinkedList[n];

		// Insert the elements into the bucket; where n is size of the array
		for (int i = 0; i < n; i++) {
			int key = (int) (arr[i] * n);
			if (buckets[key] == null) buckets[key] = new LinkedList<>();
			buckets[key].add(arr[i]);
		}

		// Sort individual buckets
		for (int i = 0; i < n; i++)
			if (buckets[i] != null) Collections.sort(buckets[i]);

		// Set all the elements back into the arr
		int curr = 0;
		for (int i = 0; i < n; i++) {
			if (buckets[i] == null) continue;

			for (Float val : buckets[i]) {
				arr[curr++] = val;
			}
		}

		System.out.println("After Sorting: ");
		for (int i = 0; i < n; i++)
			System.out.print(arr[i] + " ");
	}

}
