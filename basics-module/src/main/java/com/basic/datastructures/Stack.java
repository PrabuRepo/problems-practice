package com.basic.datastructures;

import java.util.Arrays;

import com.basic.datastructures.operations.StackOperations;
import com.common.model.ListNode;

/**
 * Stack Implementation using Linked List & Array
 */

public class Stack {
	public static void main(String[] args) {
		System.out.println("Stack Operations: ");
		Stack ob = new Stack();

		System.out.println("Stack - Array Impl: ");
		ob.testStackArrayImpl();

		System.out.println("Stack - LinkedList Impl: ");
		ob.testStackLinkedListImpl();
	}

	public void testStackArrayImpl() {
		StackArrayImpl stack = new StackArrayImpl(5);
		System.out.println("Stack Operations:");
		System.out.println("Push");
		stack.push(1);
		stack.push(2);
		stack.push(3);
		System.out.println("Display:");
		stack.print();
		System.out.println("\npop: " + stack.pop());
		System.out.println("peek: " + stack.peek());
	}

	public void testStackLinkedListImpl() {
		StackSLinkedListImpl stack = new StackSLinkedListImpl();
		System.out.println("Stack Operations:");
		System.out.println("Push");
		stack.push(1);
		stack.push(2);
		stack.push(3);
		System.out.println("Display:");
		stack.print();
		System.out.println("\npop: " + stack.pop());
		System.out.println("peek: " + stack.peek());
	}
}

class StackArrayImpl implements StackOperations {
	int[] stack;
	int top;
	int maxSize;

	public StackArrayImpl(int size) {
		this.maxSize = size;
		this.top = -1;
		this.stack = new int[maxSize];
		Arrays.fill(stack, -1);
	}

	@Override
	public void push(int data) {
		if (isFull()) return;
		stack[++top] = data;
	}

	@Override
	public int pop() {
		return isEmpty() ? -1 : stack[top--];
	}

	@Override
	public int peek() {
		return isEmpty() ? -1 : stack[top];
	}

	@Override
	public boolean isEmpty() {
		return top == -1;
	}

	private boolean isFull() {
		return top == maxSize - 1;
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void add(int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void print() {
		if (isEmpty()) return;

		for (int i = 0; i <= top; i++)
			System.out.print(stack[i] + " ");
	}
}

class StackSLinkedListImpl implements StackOperations {
	ListNode stack;

	@Override
	public void push(int data) {
		ListNode newNode = new ListNode(data);
		if (stack != null) {
			newNode.next = stack;
		} else {
			stack = newNode;
		}
	}

	@Override
	public int pop() {
		if (isEmpty()) return -1;

		int data = stack.data;
		stack = stack.next;
		return data;
	}

	@Override
	public int peek() {
		return isEmpty() ? -1 : stack.data;
	}

	@Override
	public boolean isEmpty() {
		return stack == null ? true : false;
	}

	@Override
	public void print() {
		if (isEmpty()) return;

		ListNode curr = stack;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
	}

	@Override
	public void add(int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}

class StackDLinkedListImpl {
	/*
	 * Stack can be implemented using DLL, but it doesnt give much improvement when comparing to SLL.
	 * Because Stack implementation using SLL gives O(1) time for all the operations.
	 */
}