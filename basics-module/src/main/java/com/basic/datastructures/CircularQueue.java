package com.basic.datastructures;

import java.util.Arrays;
import java.util.Scanner;

import com.basic.datastructures.operations.QueueOperations;
import com.common.model.ListNode;

/* Circular Queue"
 * Circular Queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle and the last 
 * position is connected back to the first position to make a circle. It is also called ‘Ring Buffer’.
 * Why Circular Queue?
 * In a normal Queue, we can insert elements until queue becomes full. But once queue becomes full, we can not insert the next element even 
 * if there is a space in front of queue.
 * Note: In case of linked list implementation, a queue can be easily implemented without being circular. However, in the case of array 
 * implementation, we need a circular queue to save space.
 * 
 * Circular Queue can be implemented using,
 * 	1.Array - Concentrate on array implementation
 * 	2.SLL 
 * 	3.DLL 
 */
public class CircularQueue {
	public static void main(String[] args) {
		System.out.println("Linked List Operations:");
		System.out.println("1. Circular Queue Array Impl");

		System.out.println("\n2. Circular Queue Singly Linked List Impl");

		System.out.println("\n3. Circular Queue Doubly Linked List Impl");
	}
}

/*
 * Applications:
 * 	Memory Management: The unused memory locations in the case of ordinary queues can be utilized in circular queues.
 * 	Traffic system: In computer controlled traffic system, circular queues are used to switch on the traffic lights one
 *	by one repeatedly as per the time set. 
 * 	CPU Scheduling: Operating systems often maintain a queue of processes that are ready to execute or that are waiting
 * 	for a particular event to occur.
 */

class CircularQueueArrayImpl implements QueueOperations {
	private int front, rear, size, capacity;
	private int[] queue;

	public CircularQueueArrayImpl(int k) {
		rear = -1;
		front = 0;
		size = 0;
		capacity = k;
		queue = new int[k];
		Arrays.fill(queue, -1);
	}

	@Override
	public void add(int data) {
		if (isFull()) return;

		rear = (rear + 1) % capacity;
		queue[rear] = data;
		size++;
	}

	@Override
	public int poll() {
		if (isEmpty()) return -1;

		int element = peek();
		front = (front + 1) % capacity;
		size--;
		return element;
	}

	@Override
	public int peek() {
		return isEmpty() ? -1 : queue[front];
	}

	public int getRear() {
		return isEmpty() ? -1 : queue[rear];
	}

	public boolean isFull() {
		return size == capacity;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
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
	public void print() {
		if (isEmpty()) {
			System.out.println("Queue is empty!");
		} else if (front <= rear) { // Normal Case: Print from front to rear
			for (int i = front; i <= rear; i++)
				System.out.print(queue[i] + " ");
		} else { // Rear moved to place front side and front is placed in rear side
			for (int i = front; i < queue.length; i++)
				System.out.print(queue[i] + " ");
			for (int i = 0; i <= rear; i++)
				System.out.print(queue[i] + " ");
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		CircularQueueArrayImpl queue = new CircularQueueArrayImpl(6);
		do {
			System.out.println("Circular Queue Operations:");
			System.out.println("1.Enqueue");
			System.out.println("2.Dequeue");
			System.out.println("3.Front");
			System.out.print("Enter option:");
			switch (in.nextInt()) {
			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					queue.add(in.nextInt());
				}
				System.out.println("Elements are inserted!");
				break;
			case 2:
				int data = queue.poll();
				if (data != -1) System.out.println("Dequeued element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 3:
				data = queue.peek();
				if (data != -1) System.out.println("Front element is: " + data);
				else System.out.println("Queue is empty");
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;

			}

			System.out.println("\nDisplay:");
			queue.print();
			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}
}

class CircularQueueArrayImpl2 implements QueueOperations {

	int[] queue;
	int front;
	int rear;
	int maxSize;

	public CircularQueueArrayImpl2(int size) {
		this.maxSize = size;
		this.queue = new int[maxSize];
		Arrays.fill(queue, -1);
		this.front = -1;
		this.rear = -1;
	}

	@Override
	public void add(int data) {
		if (isFull()) {
			System.out.println("Queue is Full!");
		} else if (isEmpty()) { // When Queue is empty; both front & rear will be increased zero.
			front++;
			rear++;
		} else if (front > 0 && rear == maxSize - 1) {
			// When rear is maxSize and front>0 -> Meaning there is some space in front
			rear = 0;
		} else { // Normal case: Simply insert the data in the rear end
			rear++;
		}
		queue[rear] = data;
	}

	@Override
	public int poll() {
		if (isEmpty()) return -1;

		int element = queue[front];
		queue[front] = -1;
		if (front == rear) { // If both are same, only one data present in the queue
			front = rear = -1;
		} else if (front == maxSize - 1 && rear >= 0) {
			// front is in last index and rear >0; Meaning: There are elements in the Front
			front = 0;
		} else {
			front++; // Normal case: Simply increase the front count
		}

		return element;
	}

	@Override
	public int peek() {
		return isEmpty() ? -1 : queue[front];
	}

	public int getRear() {
		return isEmpty() ? -1 : queue[rear];
	}

	@Override
	public boolean isEmpty() {
		return front == -1 && rear == -1;
	}

	public boolean isFull() {
		return (front == 0 && rear == maxSize - 1) || (front == rear + 1);
	}

	@Override
	public void print() {
		if (isEmpty()) {
			System.out.println("Queue is empty!");
		} else if (front <= rear) { // Normal Case: Print from front to rear
			for (int i = front; i <= rear; i++)
				System.out.print(queue[i] + " ");
		} else { // Rear moved to place front side and front is placed in rear side
			for (int i = front; i < maxSize; i++)
				System.out.print(queue[i] + " ");
			for (int i = 0; i <= rear; i++)
				System.out.print(queue[i] + " ");
		}
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

class CircularQueueSLLImpl implements QueueOperations {
	ListNode front, rear;

	@Override
	public void add(int data) {
		ListNode node = new ListNode(data);
		if (isEmpty()) {
			front = rear = node;
			rear.next = front;
		} else {
			node.next = rear.next; //or node.next = front;
			rear.next = node;
			rear = node;
		}
	}

	@Override
	public int poll() {
		if (!isEmpty()) return -1;
		int element = front.data;
		if (front == rear) {
			front = rear = null;
		} else {
			front = front.next;
			rear.next = front;
		}
		return element;
	}

	@Override
	public int peek() {
		return isEmpty() ? -1 : front.data;
	}

	@Override
	public boolean isEmpty() {
		return (front == null && rear == null);
	}

	@Override
	public void print() {
		if (isEmpty()) return;
		ListNode temp = front;
		do {
			System.out.print(temp.data + " ");
			temp = temp.next;
		} while (temp != front);
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

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		CircularQueueSLLImpl queue = new CircularQueueSLLImpl();
		do {
			System.out.println("Circular Queue Operations:");
			System.out.println("1.Enqueue");
			System.out.println("2.Dequeue");
			System.out.println("3.Front");
			System.out.print("Enter option:");
			switch (in.nextInt()) {
			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					queue.add(in.nextInt());
				}
				System.out.println("Elements are inserted!");
				break;
			case 2:
				int data = queue.poll();
				if (data != -1) System.out.println("Dequeued element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 3:
				data = queue.peek();
				if (data != -1) System.out.println("Front element is: " + data);
				else System.out.println("Queue is empty");
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;
			}

			System.out.println("\nDisplay:");
			queue.print();
			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}

}

class CircularQueueDLLImpl {
	ListNode front, rear;

	/* 
	 * Circular Queue can be implemented using DLL, but it doesn't give much improvement when comparing to SLL.
	 * Because Circular Queue implementation using SLL gives O(1) time for all the operations.
	 */

}