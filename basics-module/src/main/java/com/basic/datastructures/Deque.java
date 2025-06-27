package com.basic.datastructures;

import java.util.Arrays;
import java.util.Scanner;

import com.basic.datastructures.operations.DequeOperations;
import com.common.model.ListNode;

/*  
 * Deque(Double Ended Queue):
 * 	Deque can act as both stack(push/pop) & queue(enqueue/dequeue)
 * 	A linear collection that supports element insertion and removal at both ends. The name deque is short for "double ended queue" 
 * 	and is usually pronounced "deck". Most Deque implementations place no fixed limits on the number of elements they may contain, 
 * 	but this interface supports capacity-restricted deques as well as those with no fixed size limit.
 * 	Stack: use addFirst(push), removeFirst(pop) operation
 *  Queue: use addLast(enqueue), removeFirst(dequeue) operation
 * Deque Impl using Circular Array & Doubly Linked List
 */
public class Deque {

}

class DequeArrayImpl implements DequeOperations {
	int[] deque;
	int front, rear, size, capacity;

	public DequeArrayImpl(int maxSize) {
		deque = new int[maxSize];
		front = 0;
		rear = -1;
		size = 0;
		capacity = maxSize;
	}

	@Override
	public void addFirst(int data) {
		if (isFull()) return;
		front = (front - 1 + capacity) % capacity;
		deque[front] = data;
		size++;
	}

	@Override
	public void addLast(int data) {
		if (isFull()) return;
		rear = (rear + 1) % capacity;
		deque[rear] = data;
		size++;
	}

	@Override
	public int removeFirst() {
		if (isEmpty()) return -1;
		int element = getFirst();
		front = (front + 1) % capacity;
		size--;
		return element;
	}

	@Override
	public int removeLast() {
		if (isEmpty()) return -1;
		int element = getLast();
		rear = (rear - 1 + capacity) % capacity;
		size--;
		return element;
	}

	@Override
	public int getFirst() {
		//front is initialized with 0, so there is no chance of wrong index and answer
		// Eg: If one element is added in the last, then call the getFirst(). It suppose to return rear element from 0th index
		return isEmpty() ? -1 : deque[front];
	}

	@Override
	public int getLast() {
		//rear is initialized with -1, so there is a chance of -ve index and index out of bound exception
		// Eg: If one element is added in the front, then call the getLast(). It suppose to return front element from Capacity-1 th index
		return isEmpty() ? -1 : deque[(rear + capacity) % capacity];

	}

	@Override
	public void print() {
		if (isEmpty()) return;
		for (int i = 0; i < capacity; i++)
			System.out.print(deque[i] + " ");
	}

	@Override
	public boolean isEmpty() {
		return front == -1 && rear == -1 ? true : false;
	}

	public boolean isFull() {
		return (size == capacity);
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

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		char ch;
		DequeArrayImpl queue = new DequeArrayImpl(6);
		do {
			System.out.println("Double Ended Queue Operations:");
			System.out.println("1.Insert Front");
			System.out.println("2.Insert Rear");
			System.out.println("3.Delete Front");
			System.out.println("4.Delete Rear");
			System.out.println("5.Front element");
			System.out.println("6.Rear element");
			System.out.print("Enter option:");
			switch (in.nextInt()) {
			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					queue.addFirst(in.nextInt());
				}
				System.out.println("Elements are inserted in front!");
				break;
			case 2:
				System.out.println("Enter no of elements to be inserted:");
				t = in.nextInt();
				while (t-- > 0) {
					queue.addLast(in.nextInt());
				}
				System.out.println("Elements are inserted in rear!");
				break;
			case 3:
				int data = queue.removeFirst();
				if (data != -1) System.out.println("Dequeued front element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 4:
				data = queue.removeLast();
				if (data != -1) System.out.println("Dequeued rear element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 5:
				data = queue.getFirst();
				if (data != -1) System.out.println("Front element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 6:
				data = queue.getLast();
				if (data != -1) System.out.println("Rear element is: " + data);
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

class DequeArrayImpl2 implements DequeOperations {
	int[] queue;
	int front, rear;
	int maxSize;
	int currSize;

	public DequeArrayImpl2(int maxSize) {
		queue = new int[maxSize];
		Arrays.fill(queue, -1);
		this.maxSize = maxSize;
		this.front = -1;
		this.rear = -1;
		currSize = 0;
	}

	@Override
	public void addFirst(int data) {
		if (isFull2()) return;
		if (isEmpty()) { // When queue is empty
			front++;
			rear++;
		} else if (front == 0) {// Only if one element inserted already
			front = maxSize - 1;
		} else { // Normal case: decrement the front size and insert the element
			front--;
		}
		queue[front] = data;
		// currSize++;
	}

	@Override
	public void addLast(int data) {
		if (isFull2()) return;
		if (isEmpty()) {// When queue is empty
			front++;
			rear++;
		} else if (rear == maxSize - 1) {// rear reached the max size index
			rear = 0;
		} else { // Normal case: Increment the rear size and insert the data
			rear++;
		}
		queue[rear] = data;
		// currSize++;
	}

	@Override
	public int removeFirst() {
		if (isEmpty()) return -1;
		int element = queue[front];
		queue[front] = -1;
		if (front == rear) { // If both are same, only one data present in the queue
			front = -1;
			rear = -1;
		} else if (front == maxSize - 1) {// front is in last index, move front to first index to point to the front
											// or rear element
			front = 0;
		} else { // Normal Case: Increment the front to delete the element
			front++;
		}
		// currSize--;
		return element;
	}

	@Override
	public int removeLast() {
		if (isEmpty()) return -1;
		int element = queue[rear];
		queue[rear] = -1;
		if (front == rear) {// If both are same, only one data present in the queue
			front = -1;
			rear = -1;
		} else if (rear == 0) {// If rear is zero, move rear to last index to point to the front element
			rear = maxSize - 1;
		} else {// Normal Case: decrement the front to delete the element
			rear--;
		}
		// currSize--;
		return element;
	}

	@Override
	public int getFirst() {
		return isEmpty() ? -1 : queue[front];
	}

	@Override
	public int getLast() {
		return isEmpty() ? -1 : queue[rear];
	}

	@Override
	public void print() {
		if (isEmpty()) return;
		for (int i = 0; i < maxSize; i++)
			System.out.print(queue[i] + " ");
	}

	@Override
	public boolean isEmpty() {
		return front == -1 && rear == -1 ? true : false;
	}

	public boolean isFull1() {
		return (currSize == maxSize);
	}

	// without using currSize
	public boolean isFull2() {
		return ((front == rear + 1) || (front == 0 && rear == maxSize - 1));
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

class DequeSLLImpl {
	ListNode front, rear;
	/* addFirst, addLast & removeFirst operations are easy to implement in SLL, but removeLast is difficult 
	 * to implement using SLL.
	 * It is possible to implement removeLast using SLL, but it will take O(n) time. We have to traverse from
	 * front pointer to till prev node of rear end to delete the last node.
	 */
}

class DequeDLLImpl implements DequeOperations {
	ListNode front, rear;

	@Override
	public void addFirst(int data) {
		ListNode newNode = new ListNode(data);
		if (isEmpty()) {
			front = rear = newNode;
		} else {
			newNode.next = front;
			front.prev = newNode;
			front = newNode;
		}
	}

	@Override
	public void addLast(int data) {
		ListNode newNode = new ListNode(data);
		if (isEmpty()) {
			front = rear = newNode;
		} else {
			rear.next = newNode;
			newNode.prev = rear;
			rear = newNode;
		}
	}

	@Override
	public int removeFirst() {
		if (isEmpty()) return -1;
		int element = -1;
		element = front.data;
		if (front == rear) {
			front = rear = null;
		} else {
			front = front.next;
			front.prev = null;
		}
		return element;
	}

	@Override
	public int removeLast() {
		if (isEmpty()) return -1;
		int element = -1;
		element = rear.data;
		if (front == rear) {
			front = rear = null;
		} else {
			rear = rear.prev;
			rear.next = null;
		}
		return element;
	}

	@Override
	public int getFirst() {
		return isEmpty() ? -1 : front.data;
	}

	@Override
	public int getLast() {
		return isEmpty() ? -1 : rear.data;
	}

	@Override
	public void print() {
		if (isEmpty()) return;
		ListNode temp = front;
		while (temp != null) {
			System.out.print(temp.data + " ");
			temp = temp.next;
		}
	}

	@Override
	public boolean isEmpty() {
		return (front == null && rear == null);
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

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		DequeDLLImpl queue = new DequeDLLImpl();
		do {
			System.out.println("Double Ended Queue Operations:");
			System.out.println("1.Insert Front");
			System.out.println("2.Insert Rear");
			System.out.println("3.Delete Front");
			System.out.println("4.Delete Rear");
			System.out.println("5.Front element");
			System.out.println("6.Rear element");
			System.out.print("Enter option:");
			switch (in.nextInt()) {
			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					queue.addFirst(in.nextInt());
				}
				System.out.println("Elements are inserted in front!");
				break;
			case 2:
				System.out.println("Enter no of elements to be inserted:");
				t = in.nextInt();
				while (t-- > 0) {
					queue.addLast(in.nextInt());
				}
				System.out.println("Elements are inserted in rear!");
				break;
			case 3:
				int data = queue.removeFirst();
				if (data != -1) System.out.println("Dequeued front element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 4:
				data = queue.removeLast();
				if (data != -1) System.out.println("Dequeued rear element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 5:
				data = queue.getFirst();
				if (data != -1) System.out.println("Front element is: " + data);
				else System.out.println("Queue is empty");
				break;
			case 6:
				data = queue.getLast();
				if (data != -1) System.out.println("Rear element is: " + data);
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
