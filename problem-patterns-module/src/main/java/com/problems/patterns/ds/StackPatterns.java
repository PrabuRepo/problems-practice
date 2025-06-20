package com.problems.patterns.ds;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.common.utilities.Utils;

/*
 * Note: In all the problems, generally use stack.peek(). It means prev value in the array/string
 */
public class StackPatterns {

	/********************** Parse the expression/String *******************/
	// Evaluate Post Fix Expression or Polish Notation -> Here input is string
	public void evaluatePosfixExpression(String expr) {
		Stack<Integer> stack = new Stack<>();

		// Scan the given expression and do following for every scanned element.
		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			// If the element is a number, push it into the stack
			if (Character.isDigit(ch)) {
				stack.push(Character.getNumericValue(ch));
				// stack.push(Integer.valueOf(ch - '0'));
			} else {
				/* If the element is a operator, pop operands for the operator from stack. Evaluate the operator 
				 * and push the result back to the stack*/
				int result = arithmeticOperation(ch, stack.pop(), stack.pop());
				stack.push(result);
			}
		}
		System.out.println("Value:" + stack.pop());
	}

	// Evaluate Reverse Polish/Postfix Notation -> Here input is string array
	public int evalRPN1(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
		String str = null;
		for (int i = 0; i < tokens.length; i++) {
			str = tokens[i];
			if (str.equals("/") || str.equals("-") || str.equals("*") || str.equals("+")) {
				stack.push(arithmeticOperation(str.charAt(0), stack.pop(), stack.pop()));
			} else {
				stack.push(Integer.valueOf(str));
			}
		}
		return (!stack.isEmpty() && stack.size() == 1) ? stack.pop() : 0;
	}

	//Using Recursion
	int i;

	public int evalRPN2(String[] tokens) {
		i = tokens.length - 1;
		return eval(tokens);
	}

	int eval(String[] ss) {
		switch (ss[i--]) {
		case "+":
			return eval(ss) + eval(ss);
		case "-":
			return -eval(ss) + eval(ss);
		case "*":
			return eval(ss) * eval(ss);
		case "/":
			return (int) (1.0 / eval(ss) * eval(ss));
		default:
			return Integer.valueOf(ss[i + 1]);
		}
	}

	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		char ch;
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (ch == '(' || ch == '{' || ch == '[') {
				stack.push(ch);
			} else {
				if (!stack.isEmpty() && ((ch == ')' && stack.peek() == '(') || (ch == ']' && stack.peek() == '[')
						|| (ch == '}' && stack.peek() == '{')))
					stack.pop();
				else return false;
			}
		}

		return stack.isEmpty();
	}

	//Evaluate infix expression/Basic Calculator I, II, III
	/*Evaluate Infix Expression:
	 *  - Basic Calculator I
	 *  - Basic Calculator II
	 *  - Basic Calculator III
	 */

	/* Basic Calculator I: Implement a basic calculator to evaluate a simple expression string. The expression string may
	 * contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
	 * Input: "1 + 1" Output: 2 
	 * Input: " 2-1 + 2 " Output: 3
	 */
	public int calculator1(String s) {
		int n = s.length(), sign = 1, result = 0;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (Character.isDigit(ch)) {
				int num = (int) ch - '0';
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) { // Collect all the digits from the input
					num = num * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				result += num * sign;
			} else if (ch == '+') {
				sign = 1;
			} else if (ch == '-') {
				sign = -1;
			} else if (ch == '(') {
				stack.push(result);
				stack.push(sign);
				result = 0;
				sign = 1;
			} else if (ch == ')') {
				result = result * stack.pop() + stack.pop(); // 1st pops the sign, 2nd pops the prev calculation
			}
		}
		return result;
	}

	/*
	 * Basic Calculator II:
	 * Implement a basic calculator to evaluate a simple expression string. The expression string contains only non-negative
	 * integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.
	 * Input: "3+2*2" Output: 7 
	 * Input: " 3/2 " Output: 1 
	 * Input: " 3+5 / 2 " Output: 5
	 */

	// Approach1: Using Stack
	public int calculator21(String s) {
		int n = s.length(), num = 0;
		Stack<Integer> stack = new Stack<>();
		char oper = '+';
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == ' ') {
				continue;
			} else if (Character.isDigit(ch)) {
				num = (int) ch - '0';
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) { // Collect all the digits from the input
					num = num * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				if (oper == '+') stack.push(num);
				else if (oper == '-') stack.push(-num);
				else if (oper == '*') stack.push(stack.pop() * num);
				else if (oper == '/') stack.push(stack.pop() / num);
			} else {
				oper = ch;
			}
		}

		int result = 0;
		while (!stack.isEmpty()) result += stack.pop();
		return result;
	}

	// Approach2: Without using Stack
	public int calculator22(String s) {
		int n = s.length(), result = 0, num = 0, prevVal = 0;
		char oper = '+';
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == ' ') {
				continue;
			} else if (Character.isDigit(ch)) {
				num = (int) ch - '0';
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) { // Collect all the digits from the input
					num = num * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				if (oper == '+') {
					result += prevVal;
					prevVal = num;
				} else if (oper == '-') {
					result += prevVal;
					prevVal = -num;
				} else if (oper == '*') {
					prevVal = prevVal * num;
				} else if (oper == '/') {
					prevVal = prevVal / num;
				}
			} else {
				oper = ch;
			}
		}

		return result + prevVal;
	}

	/*Basic Calculator III:
	 * Implement a basic calculator to evaluate a simple expression string. The expression string may contain open ( and
	 * closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces . The expression string
	 * contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The
	 * integer division should truncate toward zero.
	 * "1 + 1" = 2
	 * " 6-4 / 2 " = 4
	 * "2*(5+5*2)/3+(6/2+8)" = 21
	 */
	public int calculator31(String s) {
		Stack<Character> operStack = new Stack<>(); //Operator Stack
		Stack<Integer> valStack = new Stack<>(); //Operand Stack

		// s = s.trim().replaceAll("[ ]+", ""); //Check this RegEx
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == ' ') {
				continue;
			} else if (Character.isDigit(ch)) {
				int num = (int) ch - '0'; //or Character.getNumericValue(s.charAt(i)); 

				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) {
					num = num * 10 + Character.getNumericValue(s.charAt(i + 1));
					i++;
				}
				valStack.push(num);

				/* This calculation throws exception for the inputs 10-2147483648, 0-2147483648
				int start = i;
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) i++;
				valStack.push(Integer.valueOf(s.substring(start, i + 1)));*/
			} else {
				if (operStack.isEmpty() || ch == '(') {
					operStack.push(ch);
				} else if (ch == ')') {
					while (operStack.peek() != '(') {
						// Get the values from operStack & valStack, perform arithmetic operation and store the result back in valStack
						valStack.push(arithmeticOperation(operStack.pop(), valStack.pop(), valStack.pop()));
					}
					operStack.pop();
				} else { //If ch is operator, process it
					char top = operStack.peek();
					if (top != '(' && getPrecedence(top) >= getPrecedence(ch)) {
						valStack.push(arithmeticOperation(operStack.pop(), valStack.pop(), valStack.pop()));
					}
					operStack.push(ch);
				}
			}
		}

		while (!operStack.isEmpty()) {
			valStack.push(arithmeticOperation(operStack.pop(), valStack.pop(), valStack.pop()));
		}

		return valStack.isEmpty() ? 0 : valStack.pop();
	}

	//Simplify Path
	public String simplifyPath(String path) {
		Stack<String> stack = new Stack<>();

		for (String str : path.split("/")) {
			if (!stack.isEmpty() && str.equals("..")) stack.pop();
			else if (!str.equals("") && !str.equals(".") && !str.equals("..")) stack.push(str);
		}

		StringBuilder result = new StringBuilder();
		for (String str : stack)
			result.append("/").append(str);

		return stack.isEmpty() ? "/" : result.toString();
	}

	/********************** Monotonic Stack problems *******************/
	/*
	 * A monotonic stack is a stack that maintains elements in either strictly increasing or decreasing order. 
	 * Monotonic Increasing Stack: It keeps elements in an increasing order. Top has the smallest, and each new element is larger than the previous.
	 * Monotonic Decreasing Stack: It keeps elements in an decreasing order. Top has the largest, and each new element is smaller than the previous.
	 * 
	 * Note: Can store indices or value in the stack. Store indices in the stack for range/position-based logic.
	 * 
	 * Why Use Monotonic Stacks? 
	 * They're useful when:
	 * 		You want to find the next greater/smaller element
	 * 		You need to process elements in relative order
	 * 		You care about first obstacle/block/limit in left/right directions
	 * 
	 * Common Problems Solved with Monotonic Stacks:
	 * 		Next Greater / Smaller Element
	 * 		Histogram & Ranges
	 * 		Temperature / Sliding Window / Array Trends
	 */
	//monotonous increasing stack: elements in the monotonous increase stack keeps an increasing order.
	public void monotonicIncreasingStack(int[] arr) {
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.empty() && arr[i] < stack.peek()) {
				stack.pop();
			}
			stack.push(arr[i]);
		}
	}

	//monotonous decreasing stack: elements in the monotonous decrease stack keeps an decreasing order.
	public void monotonicDecreasingStack(int[] arr) {
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.empty() && arr[i] > stack.peek()) {
				stack.pop();
			}
			stack.push(arr[i]);
		}
	}

	//Next Greater Element I
	// Approach1: BruteForce Approach -> Time Complexity: O(n^2)
	public static void nextGreaterElementI1(int[] a) {
		int next;
		for (int i = 0; i < a.length; i++) {
			next = -1;
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] < a[j]) {
					next = a[j];
					break;
				}
			}
			System.out.println(a[i] + " -- " + next);
		}
	}

	// Approach2: Traverse from Left to Right, stack keeps the data Monotonically decreasing order. Time: O(n), Space: O(n)
	public int[] nextGreaterElementI2(int[] nums1, int[] nums2) {
		int[] result = new int[nums1.length];

		Stack<Integer> stack = new Stack<>();
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums2.length; i++) {
			while (!stack.isEmpty() && nums2[i] > stack.peek()) {
				map.put(stack.pop(), nums2[i]);
			}
			stack.push(nums2[i]);
		}

		for (int i = 0; i < nums1.length; i++) {
			result[i] = map.get(nums1[i]) != null ? map.get(nums1[i]) : -1;
		}

		return result;
	}

	// Approach3: Traverse from right to left, stack keeps the data Monotonically increasing order. Time: O(n), Space: O(n)
	public int[] nextGreaterElementI3(int[] nums1, int[] nums2) {
		int[] result = new int[nums1.length];

		Stack<Integer> stack = new Stack<>();
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = nums2.length - 1; i >= 0; i--) {
			while (!stack.isEmpty() && nums2[i] >= stack.peek()) {
				stack.pop();
			}
			if (!stack.isEmpty()) {
				map.put(nums2[i], stack.peek());
			}
			stack.push(nums2[i]);
		}

		for (int i = 0; i < nums1.length; i++) {
			result[i] = map.get(nums1[i]) != null ? map.get(nums1[i]) : -1;
		}

		return result;
	}

	//Next Greater Element II: Time-O(n), Space-O(n)
	//Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element.
	//Bruteforce Approach:
	public int[] nextGreaterElements(int[] nums) {
		int[] res = new int[nums.length];
		int[] doublenums = new int[nums.length * 2];
		System.arraycopy(nums, 0, doublenums, 0, nums.length);
		System.arraycopy(nums, 0, doublenums, nums.length, nums.length);
		for (int i = 0; i < nums.length; i++) {
			res[i] = -1;
			for (int j = i + 1; j < doublenums.length; j++) {
				if (doublenums[j] > doublenums[i]) {
					res[i] = doublenums[j];
					break;
				}
			}
		}
		return res;
	}

	/* Approach2: Traverse from right to left, stack keeps the data Monotonically increasing order and traverse twice to simulate a circular behavior.
	 * Note: 
	 * In Next Greater Element II, each element may need to look ahead in a circular manner. That is, after reaching the end of the array, you might 
	 * need to wrap around and continue from the beginning.
	 * Why Iterate Backward from 2n - 1 to 0?
	 * 	- To simulate a circular behavior
	 *  - After first iteration, Since traversal is backward, first element will be top of the stack and keeps the element in the increase sequence from 0 to n-1.
	 *  - Second iteration, it uses data in stack to check the next greater element from 0 to n-1  
	 *  
	 *  Why Iterate Backward from 2n - 1 to 0 in Circular Array Problems?
	 *  	- To simulate circular behavior efficiently
	 *  	- First half of the loop (i = 2n-1 to n): Preloads the stack with elements from the end to the beginning of the array. This builds up a monotonic 
	 *        stack with potential "future" greater elements for the actual positions in the array. 
	 *      - Second half (i = n-1 to 0): Now that the stack contains possible greater elements (including wrapped-around ones), we compute the result for
	 *        each index from 0 to n-1 by using the top of the stack.
	 */
	public int[] nextGreaterElementsII2(int[] nums) {
		if (nums.length == 0) return new int[0];

		Stack<Integer> stack = new Stack<>();
		int n = nums.length;
		//1.First add the elements in the stack
		for (int i = n - 1; i >= 0; i--) {
			stack.push(nums[i]);
		}

		//Then iterate from backend and find the next greatest element
		int[] result = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && nums[i] >= stack.peek()) {
				stack.pop();
			}
			result[i] = stack.isEmpty() ? -1 : stack.peek();
			stack.push(nums[i]);
		}

		return result;
	}

	// Slight modification of above approach. First the element from bottom to top in the stack, then process it
	public int[] nextGreaterElementsII3(int[] nums) {
		if (nums.length == 0) return new int[0];

		Stack<Integer> stack = new Stack<>();
		int n = nums.length;
		//1.First add the elements in the stack
		for (int i = n - 1; i >= 0; i--) {
			stack.push(nums[i]);
		}

		//Then iterate from backend and find the next greatest element
		int[] result = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && nums[i] >= stack.peek()) {
				stack.pop();
			}
			result[i] = stack.isEmpty() ? -1 : stack.peek();
			stack.push(nums[i]);
		}

		return result;
	}

	//Online Stock Span
	//Using Monotonic Decreasing Stack
	public void stockSpan(int[] prices) {
		//0-price, 1-consecutive days count
		Stack<int[]> stack = new Stack<>();
		for (int i = 0; i < prices.length; i++) {
			int count = 1;
			while (!stack.isEmpty() && prices[i] >= stack.peek()[0]) {
				count += stack.pop()[1];
			}
			stack.push(new int[] { prices[i], count });
			System.out.println(i + " - " + count);
		}
	}

	// Largest Rectangle in Histogram
	// Approach1: BruteForce Approach: Time Complexity-O(n^2)
	public int largestRectangleArea1(int[] h) {
		int max = 0, width;
		int minHeight;

		for (int i = 0; i < h.length; i++) {
			width = 1;
			minHeight = Integer.MAX_VALUE;
			for (int j = i; j >= 0; j--, width++) {
				minHeight = Math.min(minHeight, h[j]);
				max = Math.max(max, width * minHeight);
			}
		}
		return max;
	}

	// Using Divide & Conquer Algorithm:
	/*
	 * worst case time complexity of this algorithm becomes O(n^2). In worst case, we always have (n-1) elements in one side and 0 
	 * elements in other side and if the finding minimum takes O(n) time, we get the recurrence similar to worst case of Quick Sort. 
	 * How to find the minimum efficiently? Range Minimum Query using Segment Tree can be used for this. We build segment tree of the
	 * given histogram heights. Once the segment tree is built, all range minimum queries take O(Logn) time.
	 * 
	 * Overall Time = Time to build Segment Tree + Time to recursively find maximum area; 
	 * Time to build segment tree is O(n). Let the time to recursively find max area be T(n). It can be written as following.
	 * T(n) = O(Logn) + T(n-1)
	 */
	public int largestRectangleArea2(int[] heights) {
		if (heights.length == 0) return 0;
		return largestRectangleArea2(heights, 0, heights.length - 1);
	}

	public int largestRectangleArea2(int[] heights, int l, int h) {
		if (l > h) return 0;
		if (l == h) return heights[l];

		// Rewrite this using Range Minimum query
		int m = findMin(heights, l, h);

		return Utils.max(largestRectangleArea2(heights, l, m - 1), largestRectangleArea2(heights, m + 1, h),
				(h - l + 1) * heights[m]);
	}

	private int findMin(int[] a, int l, int h) {
		int minIndex = l;
		for (int i = l + 1; i <= h; i++)
			if (a[i] < a[minIndex]) minIndex = i;

		return minIndex;
	}

	// Using Stack -> using Monotonic increasing stack 
	public int largestRectangleArea3(int[] heights) {
		if (heights == null || heights.length == 0) return 0;

		Stack<Integer> stack = new Stack<>();
		int maxArea = 0, n = heights.length;
		for (int i = 0; i <= n; i++) {
			while (!stack.isEmpty() && (i == n || heights[i] <= heights[stack.peek()])) {
				int h = heights[stack.pop()];
				int w = stack.isEmpty() ? i : i - stack.peek() - 1;
				maxArea = Math.max(maxArea, h * w);
			}
			stack.push(i);
		}
		return maxArea;
	}

	/*
	 * 4.Trapping Rain Water -using Monotonic decreasing stack 
	 * 	Time: (O(n)
	 *  Space: O(n)
	 */
	public int trappingRainWater(int[] height) {
		if (height.length <= 1) return 0;

		Stack<Integer> stack = new Stack<>();
		int n = height.length, water = 0;

		for (int i = 0; i < n; i++) {
			// When we find a higher bar than the one at the top of the stack, it means we may have found a right boundary for trapping water.
			while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
				int top = stack.pop(); // Pop the top (this is the bottom of the valley). 
				if (stack.isEmpty()) break;
				// Left Boundary is new top(peek) element at the stack, right boundary is current value, previous top gives the bottom of the valley.
				int boundedHeight = Math.min(height[stack.peek()], height[i]) - height[top];
				int width = i - stack.peek() - 1;
				water += boundedHeight * width;
			}
			stack.push(i);
		}

		return water;
	}

	/********************** Stack design problems *******************/

	/********************************** Util Methods **************************/
	private int arithmeticOperation(char ch, int b, int a) {
		int result = 0;
		switch (ch) {
		case '+':
			result = a + b;
			break;
		case '-':
			result = a - b;
			break;
		case '*':
			result = a * b;
			break;
		case '/':
			result = a / b;
			break;
		case '^':
			result = (int) Math.pow(a, b);
			break;
		}
		return result;
	}

	private long arithmeticOperation(char ch, long b, long a) {
		long result = 0;
		switch (ch) {
		case '+':
			result = a + b;
			break;
		case '-':
			result = a - b;
			break;
		case '*':
			result = a * b;
			break;
		case '/':
			result = a / b;
			break;
		}
		return result;
	}

	private int getPrecedence(char op) {
		if (op == '+' || op == '-') return 1;
		else if (op == '*' || op == '/') return 2;
		else if (op == '%') return 3;
		else if (op == '^') return 4;
		return -1;
	}

	public static void main(String[] args) {
		StackPatterns ob = new StackPatterns();
		//10-2147483648, 0-2147483648
		System.out.println("Output: " + ob.calculator31("10-2147483648"));
	}
}