package com.basic.datastructures;

import com.basic.datastructures.operations.DLLOperations;
import com.basic.datastructures.operations.SLLOperations;
import com.common.model.ListNode;

/*
 * Like arrays, Linked List is a linear data structure. Unlike arrays, linked list elements are not stored at a contiguous location;
 * the elements are linked using pointers.
 * Why Linked List?
 * Arrays can be used to store linear data of similar types, but arrays have the following limitations.
 * 1) The size of the arrays is fixed: So we must know the upper limit on the number of elements in advance. Also, generally, the 
 *    allocated memory is equal to the upper limit irrespective of the usage.
 * 2) Inserting a new element in an array of elements is expensive because the room has to be created for the new elements and to 
 *    create room existing elements have to be shifted.
 * Adv of Linked List:
 * 	1) Dynamic size
 * 	2) Ease of insertion/deletion
 * Disadv of Linked List:
 * 	1) Random access is not allowed. We have to access elements sequentially starting from the first node. So we cannot do binary
 *     search with linked lists efficiently with its default implementation.
 *  2) Extra memory space for a pointer is required with each element of the list.
 *  3) Not cache friendly. Since array elements are contiguous locations, there is locality of reference which is not there in case 
 *     of linked lists.
 * Linked List Implementation: 
 *   1. Singly Linked List
 *   2. Doubly Linked List
 *   3. Circular Singly Linked List
 *   
 *  Note: Consider below three positions for all the LL operations: 
 *  	     1.First; 2.Last 3.Middle(Anywhere between first & last);
 *  
 *  Tips for index based traverse from first node to given node(K)
 *  	Case 1: Move to prev to the Kth node
 *  			i. Using Incrementer: count = 1; while(++count < K)
 *  			ii.Using Decrementer: while(--k > 1)
 *  	Case 2: Move to the Kth node
 *    			i. Using Incrementer: count = 1; while(count++ < K)
 *  			ii.Using Decrementer: while(k-- > 1)
 *  	Case 3: Move to next to the Kth node
 *	 			i. Using Incrementer: count = 1; while(count++ <= K)
 *  			ii.Using Decrementer: while(k-- >= 1)
 */
public class LinkedList {
	public static void main(String[] args) {
		System.out.println("Linked List Operations:");
		LinkedList ob = new LinkedList();

		System.out.println("1. Singly Linked List");
		ob.testSinglyLinkedList();

		System.out.println("\n2. Doubly Linked List");
		//ob.testDoublyLinkedList();

		System.out.println("\n3. Circular Singly Linked List");
		//ob.testCircularSLinkedList();
	}

	public void testSinglyLinkedList() {
		SinglyLinkedList list = new SinglyLinkedList();
		System.out.println("Add:");
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(5);
		list.add(7);
		System.out.println("\nDisplay:");
		list.print();
		System.out.println("\nInsert:");
		list.insert(1, 4);
		list.insert(2, 8);
		System.out.println("\nDisplay:");
		list.print();
		System.out.println("\nDelete: " + list.remove(1));
		System.out.println("Find: " + list.contains(3));
	}

	public void testDoublyLinkedList() {
		DoublyLinkedList list = new DoublyLinkedList();
		System.out.println("Insert");
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println("Display:");
		list.print();
		System.out.println("\nInsert After:");
		list.insertAfter(1, 11);
		list.insertAfter(2, 22);
		list.insertAfter(3, 33);
		list.print();
		System.out.println("\nInsert Before:");
		list.insertBefore(1, 111);
		list.insertBefore(2, 222);
		list.insertBefore(33, 333);
		list.print();
		System.out.println("\nInsert:");
		list.insert(1, -1);
		list.insert(3, -2);
		list.insert(11, -3);
		list.print();
		System.out.println("\nPrint Reverse:");
		list.printReverse();
		System.out.println("\nDelete: ");
		list.remove(22);
		list.print();
		System.out.println("\nFind: " + list.contains(3));
	}

	public void testCircularSLinkedList() {
		CircularSLinkedList list = new CircularSLinkedList();
		System.out.println("Insert");
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println("Display:");
		list.print();
		System.out.println("\nDelete: ");
		list.remove(1);
		System.out.println("Find: " + list.contains(3));
	}
}

class SinglyLinkedList implements SLLOperations {
	ListNode head;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
		} else {
			ListNode curr = head;
			while (curr.next != null) curr = curr.next;
			curr.next = newNode;
		}
	}

	@Override
	public void set(int index, int data) {
		// Note: Index based operation is not supported by LL. So here consider index as position which starts from 1.
		//TODO: Write the logic to update the element in the given position.
	}

	@Override
	public boolean contains(int data) {
		return get(data) != null ? true : false;
	}

	@Override
	public boolean remove(int data) {
		if (isEmpty()) return false;

		if (head.data == data) {
			head = head.next;
			return true;
		} else {
			ListNode curr = head;
			while (curr != null && curr.next != null) {
				if (curr.next.data == data) {
					curr.next = curr.next.next;
					return true;
				} else {
					curr = curr.next;
				}
			}
		}
		return false;
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;
		int count = 0;
		ListNode curr = head;
		while (curr != null) {
			count++;
			curr = curr.next;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	@Override
	public void insert(int index, int data) {
		ListNode newNode = new ListNode(data);
		if (index == 1) {
			newNode.next = head;
			head = newNode;
			return;
		}

		ListNode curr = head;
		// Move to prev node
		while (--index > 1) //or count =1; (++count < index)
			curr = curr.next;

		if (curr != null) {
			newNode.next = curr.next;
			curr.next = newNode;
		}
	}

	@Override
	public ListNode get(int data) {
		if (isEmpty()) return null;

		ListNode curr = head;
		while (curr != null) {
			if (curr.data == data) return curr;
			curr = curr.next;
		}
		return null;
	}

	@Override
	public void print() {
		ListNode curr = head;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
	}
}

/*
 * A Doubly Linked List (DLL) contains an extra pointer, typically called previous pointer, together with next pointer and data which are 
 * there in singly linked list.
 * Advantages over singly linked list: 
 * 	1) A DLL can be traversed in both forward and backward direction.
 * 	2) The delete operation in DLL is more efficient if pointer to the node to be deleted is given. (In SLL, to delete a node, pointer to 
 *     the prev node is needed. To get this prev node, sometimes the list is traversed. In DLL, we can get the prev node using previous pointer.)
 * 	3) We can quickly insert a new node before a given node.
 * Disadvantages over singly linked list:
 * 	1) Every node of DLL Require extra space for an previous pointer. It is possible to implement DLL with single pointer though.
 * 	2) All operations require an extra pointer previous to be maintained. For example, in insertion, we need to modify previous pointers together
 * 	   with next pointers.
 */
class DoublyLinkedList implements DLLOperations {

	ListNode head;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
		} else {
			ListNode curr = head;
			while (curr.next != null) curr = curr.next;
			curr.next = newNode;
			newNode.prev = curr;
		}
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(int data) {
		return get(data) != null ? true : false;
	}

	@Override
	public boolean remove(int data) {
		ListNode delNode = get(data);

		if (delNode == null) return false;

		if (delNode.prev != null) delNode.prev.next = delNode.next;
		else head = head.next;

		if (delNode.next != null) delNode.next.prev = delNode.prev;

		return true;
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;
		int count = 0;
		ListNode curr = head;
		while (curr != null) {
			count++;
			curr = curr.next;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	//It may be similar to insertBefore logic
	@Override
	public void insert(int index, int data) {
		ListNode newNode = new ListNode(data);

		if (isEmpty() && index == 1) {
			head = newNode;
			return;
		}

		//Get the node based on the index
		ListNode curr = get2(index);

		if (curr == null) return;

		//Handle newNode's prev pointer & related links
		newNode.prev = curr.prev;
		if (curr.prev != null) curr.prev.next = newNode;
		else head = newNode;

		//Handle newNode's next pointer & related links
		newNode.next = curr;
		curr.prev = newNode;
	}

	@Override
	public void insertBefore(int data, int newData) {
		//Get the node based on the data
		ListNode curr = get(data);

		if (curr == null) return;

		ListNode newNode = new ListNode(newData);

		//Handle newNode's prev pointer & related links
		newNode.prev = curr.prev;
		if (curr.prev != null) curr.prev.next = newNode;
		else head = newNode;

		//Handle newNode's next pointer & related links
		newNode.next = curr;
		curr.prev = newNode;
	}

	@Override
	public void insertAfter(int data, int newData) {
		//Get the node based on the data
		ListNode curr = get(data);

		if (curr == null) return;

		ListNode newNode = new ListNode(newData);

		//Handle newNode's next pointer & related links
		newNode.next = curr.next;
		if (curr.next != null) curr.next.prev = newNode;

		//Handle newNode's prev pointer & related links
		newNode.prev = curr;
		curr.next = newNode;
	}

	@Override
	public ListNode get(int data) {
		if (isEmpty()) return null;

		ListNode curr = head;
		while (curr != null) {
			if (curr.data == data) return curr;
			curr = curr.next;
		}
		return null;
	}

	@Override
	public ListNode get2(int index) {
		if (isEmpty()) return null;

		ListNode curr = head;
		while (--index >= 1 && curr != null) {
			curr = curr.next;
		}
		return curr;
	}

	@Override
	public void print() {
		ListNode curr = head;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
	}

	@Override
	public void printReverse() {
		ListNode curr = head;
		//Move to last node
		while (curr.next != null) curr = curr.next;

		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.prev;
		}
	}
}

/*
 * Circular Linked List can be implemented using,
 * 	1.SLL
 *  2.DLL
 */
//1.Circular (Singly) Linked List Implementation:  using only one pointer 'next'
class CircularSLinkedList implements SLLOperations {
	ListNode head;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
			head.next = head;
		} else {
			ListNode curr = head;
			do {
				if (curr.next == head) {
					curr.next = newNode;
					newNode.next = head;
					break;
				} else {
					curr = curr.next;
				}
			} while (curr != head);
		}
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(int data) {
		return get(data) != null ? true : false;
	}

	@Override
	public boolean remove(int data) {
		if (isEmpty()) return false;

		ListNode curr = head;
		if (head.data == data && curr.next == head) { // Head data matches and only Head data is present in the list
			head = null;
		} else if (head.data == data) { // Head data matches and more than one data present in the list
			curr.data = curr.next.data;
			curr.next = curr.next.next;
		} else { // Data may present anywhere in the list 
			do {
				if (curr.next.data == data) {
					curr.next = curr.next.next;
					return true;
				} else {
					curr = curr.next;
				}
			} while (curr != head); // This check is required to identify the end of the list and infinite looping
		}
		return false;
	}

	@Override
	public int size() {
		if (isEmpty()) return 0;

		ListNode curr = head;
		int count = 0;
		do {
			count++;
			curr = curr.next;
		} while (curr != head);

		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	@Override
	public void insert(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public ListNode get(int data) {
		if (isEmpty()) return null;

		ListNode curr = head;
		do {
			if (curr.data == data) return curr;
			curr = curr.next;
		} while (curr != head);

		return null;
	}

	@Override
	public void print() {
		if (isEmpty()) return;

		ListNode curr = head;
		do {
			System.out.print(curr.data + " ");
			curr = curr.next;
		} while (curr != head);
	}
}

//2.Circular (Doubly) Linked List Implementation - using two pointers front, rear
class CircularDLinkedList implements DLLOperations {

	ListNode head;

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

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertBefore(int data, int newData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertAfter(int data, int newData) {
		// TODO Auto-generated method stub

	}

	@Override
	public ListNode get(int data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

	@Override
	public ListNode get2(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printReverse() {
		// TODO Auto-generated method stub

	}
}