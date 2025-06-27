package com.problems.patterns.ds;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.common.model.ListNode;

/*
 * Floyd's cycle-finding algorithm is a pointer algorithm that uses only two pointers, which move through the sequence at different speeds. 
 * The idea is to move fast pointer twice as quickly as the slow pointer and the distance between them increases by 1 at each step. If at
 * some point both meet, we found a cycle in the list, else we will reach the end of the list and no cycle is present. It is also 
 * called as "Tortoise and Hare" algorithm.
 */
public class FastAndSlowPtrPatterns {

	InPlaceReversalLLPatterns reversalLLPatterns = new InPlaceReversalLLPatterns();

	// LinkedList Cycle: 
	//Approach1: Using HashSet; Time: O(n), Space: O(n)
	public boolean hasCycle1(ListNode head) {
		ListNode curr = head;
		HashSet<ListNode> set = new HashSet<>();
		while (curr != null) {
			if (!set.add(curr)) return true;
			curr = curr.next;
		}
		return false;
	}

	//Approach2: Floyd's Algorithm or Tortoise & Hare Algorithm
	//Time: O(n), Space: O(1)
	public boolean hasCycle2(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
			if (slowPtr == fastPtr) return true;
		}
		return false;
	}

	/* Start of LinkedList Cycle/Linked List Cycle II: Linked List Cycle II: Given a linked list, return the node
	 * where the cycle begins. If there is no cycle, return null. */
	//Approach1: Using HashSet; Time: O(n), Space: O(n)
	public ListNode detectCycle1(ListNode head) {
		ListNode curr = head;
		HashSet<ListNode> set = new HashSet<>();
		while (curr != null) {
			if (!set.add(curr)) return curr;
			curr = curr.next;
		}
		return null;
	}

	/* Approach2: Floyd Algorithm and Counter Approach; Time: O(n), Space: O(1)
	 *  n = x + l; 
	 *  where n - no of nodes in the list
	 *        l - length of the loop/cycle
	 *        x - no of nodes from head to starting point of the node.
	 *        
	 *  1. Check if there is any loop in the list 
	 *  2. Find the length of the loop(l)
	 *  3. Move the slowPtr from head to l(loop length) times, remaining will be x.
	 *  4. Now if we move fastPtr from head and slowPtr with one jump, then both will meet at starting point.
	 *  5. After that return starting point of the loop  
	 */
	public ListNode detectCycle2(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		boolean flag = false;
		//1. Check if there is any loop in the list 
		while (fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
			if (slowPtr == fastPtr) {
				flag = true;
				break;
			}
		}

		if (!flag) return null;

		//2. Find the length of the loop(l)
		int len = 0;
		do {
			slowPtr = slowPtr.next;
			len++;
		} while (slowPtr != fastPtr);

		//3. Move the slowPtr from head to l(loop length) times, remaining will be x.
		slowPtr = head;
		while (len-- > 0) {
			slowPtr = slowPtr.next;
		}

		//4. Now if we move fastPtr from head and slowPtr with one jump, then both will meet at starting point.
		fastPtr = head;
		while (slowPtr != fastPtr) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next;
		}

		return slowPtr; // or fastPtr
	}

	/* Approach3: using Floyd Algorithm ; Time: O(n), Space: O(1)
	 * Why this is logic is working? refer the document in below,
	 *  HardDisk: \1.Coding Interview\1.DS & Algorithms\Algorithms Proof od
	 *  src/main/resources
	 */
	public ListNode detectCycle3(ListNode head) {
		if (head == null || head.next == null) return null;
		ListNode slow = head, fast = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				fast = head;
				while (slow != fast) {
					slow = slow.next;
					fast = fast.next;
				}
				return fast;
			}
		}
		return null;
	}

	// Remove loop in Linked List:
	//Approach1: Using HashSet; Time: O(n), Space: O(n)
	public boolean removeLoop1(ListNode head) {
		ListNode curr = head;
		HashSet<ListNode> set = new HashSet<>();
		while (curr != null) {
			if (set.contains(curr.next)) {
				curr.next = null;
				return true;
			}
			set.add(curr);
			curr = curr.next;
		}
		return false;
	}

	/* Approach2: Floyd Algorithm and Counter Approach; 
	 *  n = x + l; 
	 *  where n - no of nodes in the list
	 *        l - length of the loop/cycle
	 *        x - no of nodes from head to starting point of the node.
	 *        
	 *  1. Check if there is any loop in the list 
	 *  2. Find the length of the loop(l)
	 *  3. Move the slowPtr from head to l(loop length)-1 times, remaining will be x.
	 *  4. Now if we move fastPtr from head and slowPtr with one jump, then both will meet at starting point.
	 *  5. After that we assign null to break/remove the loop
	 *  
	 *  Note: There is a slight modification from detect cycle logic, this modification to set null before
	 *  the starting point of the loop
	 */
	public boolean removeLoop2(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		//1. Check if there is any loop in the list 
		while (slowPtr != fastPtr) {
			if (fastPtr == null || fastPtr.next == null) return false;
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}

		//2. Find the length of the loop(l)
		int len = 0;
		do {
			slowPtr = slowPtr.next;
			len++;
		} while (slowPtr != fastPtr);

		//3. Move the slowPtr from head to l(loop length)-1 times, remaining will be x.
		slowPtr = head;
		while (len-- > 1) {
			slowPtr = slowPtr.next;
		}

		//4. Now if we move fastPtr from head and slowPtr with one jump, then both will meet at starting point.
		fastPtr = head;
		while (slowPtr.next != fastPtr) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next;
		}
		slowPtr.next = null;

		return true;
	}

	/* Approach3: using Floyd Algorithm ; Time: O(n), Space: O(1)
	 * Why this is logic is working? refer the document in below,
	 *  HardDisk: \1.Coding Interview\1.DS & Algorithms\Algorithms Proof 
	 *  src/main/resources
	 *  
	 *  Note: There is a slight modification from detect cycle logic, this modification to set null before
	 *  the starting point of the loop
	 */
	public boolean removeLoop3(ListNode head) {
		ListNode slow = head, fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				remove(head, slow);
				return true;
			}
		}

		return false;
	}

	private void remove(ListNode head, ListNode slow) {
		//if fast and slow pointer meet at first position.
		if (slow == head) {
			while (slow.next != head) {
				slow = slow.next;
			}
		} else { //if slow and fast pointer meet at other positions
			ListNode fast = head;
			while (slow.next != fast.next) {
				slow = slow.next;
				fast = fast.next;
			}
		}
		slow.next = null;
	}

	// Happy Number (medium)
	/* Write an algorithm to determine if a number is "happy". A happy number is a number defined by the following process:
	 * Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the
	 * process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
	 * Those numbers for which this process ends in 1 are happy numbers.
	 */
	/* Solution: Using floyd'd algorithm, we can find whether square number equals 1 (where it will stay), or it loops
	 * endlessly in a cycle which does not include 1.
	 * This solution helps to skip the logic(square sum), if there is any cycle.
	 */
	public boolean isHappy1(int n) {
		if (n == 0) return false;

		int slow = n, fast = n;
		do {
			slow = squareSum(slow);
			fast = squareSum(squareSum(fast));
		} while (slow != fast);

		return (slow == 1);
	}

	private int squareSum(int n) {
		int sum = 0, digit = 0;
		while (n > 0) {
			digit = n % 10;
			sum += (digit * digit); // Square
			n = n / 10;
		}
		return sum;
	}

	/* Using HashSet: The idea is to use one hash set to record sum of every digit square of every number occurred.
	 * Once the current sum is already available(duplicate) in set, return false; once the current sum equals 1, return true;
	 */
	public boolean isHappy2(int n) {
		Set<Integer> set = new HashSet<Integer>();
		while (set.add(n)) {
			int squareSum = squareSum(n);
			if (squareSum == 1) return true;
			n = squareSum;
		}
		return false;
	}

	// Find the Duplicate Number (easy)
	// Floyd's Tortoise and Hare; Time Complexity:O(n); Space Complexity: O(1)
	public int findDuplicate8(int[] nums) {
		int tortoise = 0;
		int hare = 0;
		// Find the intersection point of the two runners.
		do {
			tortoise = nums[tortoise]; // slowPtr
			hare = nums[nums[hare]]; // fastPtr
		} while (tortoise != hare);

		// Find the "entrance" to the cycle.
		int start = 0;
		while (tortoise != start) {
			start = nums[start];
			tortoise = nums[tortoise];
		}
		return tortoise;
	}

	/* Middle of the LinkedList (easy)
	 * 
	 * Approach 1: Brute Force — Count Nodes First, Then Iterate Again
	 * Approach 2: Optimal — Fast and Slow Pointers
	 */

	//Approach 1: Brute Force — Count Nodes First, Then Iterate Again
	// Time: O(n), Space: O(1). But two passes
	public ListNode middleNode1(ListNode head) {
		int count = 0;
		ListNode temp = head;
		while (temp != null) {
			count++;
			temp = temp.next;
		}

		int mid = count / 2;
		temp = head;
		for (int i = 0; i < mid; i++) {
			temp = temp.next;
		}

		return temp;
	}

	// Approach 2: Optimal — Fast and Slow Pointers: Time: O(n), Space: O(1)
	// Note: For getting second mid element in the even size of LL; i.e. fastPtr.next != null
	public ListNode middleNode21(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		return slowPtr;
	}

	// Approach 2: Optimal — Fast and Slow Pointers: Time: O(n), Space: O(1)
	// Note: For getting first mid element in the even size of LLl i.e. fastPtr.next.next != null
	public ListNode middleNode22(ListNode head) {
		if (head == null) return null;
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr.next != null && fastPtr.next.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		return slowPtr;
	}

	// Palindrome Linked List:
	public boolean isPalindrome1(ListNode head) {
		Stack<ListNode> stack = new Stack<>();
		ListNode curr = head;
		while (curr != null) {
			stack.push(curr);
			curr = curr.next;
		}
		curr = head;
		while (curr != null) {
			if (curr.data != stack.pop().data) return false;
			curr = curr.next;
		}
		return true;
	}

	public boolean isPalindrome2(ListNode head) {
		if (head == null || head.next == null) return true;
		ListNode slowPtr = head, fastPtr = head, prevNode = null;
		while (fastPtr != null && fastPtr.next != null) {
			prevNode = slowPtr;
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		ListNode midNode = null;
		if (fastPtr != null) {
			midNode = slowPtr;
			slowPtr = slowPtr.next;
		}
		prevNode.next = null;
		ListNode secondHalf = slowPtr;
		secondHalf = reversalLLPatterns.reverseList1(secondHalf);
		boolean isPalindrome = compare(head, secondHalf);

		//Rearrange the List
		secondHalf = reversalLLPatterns.reverseList1(secondHalf);
		if (midNode != null) {
			prevNode.next = midNode;
			midNode.next = slowPtr;
		} else {
			prevNode.next = slowPtr;
		}
		return isPalindrome;
	}

	public boolean compare(ListNode node1, ListNode node2) {
		if (node1 == null && node2 == null) return true;
		if (node1 == null || node2 == null) return false;
		return ((node1.data == node2.data) && compare(node1.next, node2.next));
	}

	//TODO: Cycle in a Circular Array (hard) -> Refer "Circular Array Loop" problems in leetcode
}
