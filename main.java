
//Ivan Li Huffman Encoding

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class main {

	public static PrintWriter out1;
	public static PrintWriter out2;
	public static PrintWriter out3;

	public static void main(String[] args) throws Exception {

		File in1 = new File(args[0]);
		out1 = new PrintWriter(args[1]);
		out2 = new PrintWriter(args[2]);
		out3 = new PrintWriter(args[3]);

		HuffmanLinkedList hll = new HuffmanLinkedList(in1);
		HuffmanBinaryTree myTree = new HuffmanBinaryTree(hll);

		out1.close();
		out2.close();
		out3.close();

	}

}

class listBinTreeNode {

	String chStr;
	int prob;

	listBinTreeNode next;
	listBinTreeNode left;
	listBinTreeNode right;

	public void printNode(listBinTreeNode T) {
			main.out3.print(T.chStr + " " + T.prob + " ");
		if (T.next != null)
			main.out3.print(T.next.chStr + " ");
		if (T.left != null)
			main.out3.print(T.left.chStr + " ");
		if (T.right != null)
			main.out3.print(T.right.chStr + " ");

	}

	listBinTreeNode(listBinTreeNode in) {
		chStr = in.chStr;
		prob = in.prob;
	}

	listBinTreeNode(String ch, int num) {
		chStr = ch;
		prob = num;
	}

	listBinTreeNode() {
		chStr = "null";
		prob = 0;
		// "node constructor for dummy\n";
	}

}

class HuffmanLinkedList {

	listBinTreeNode listHead;
	listBinTreeNode oldListHead;

	HuffmanLinkedList(File inFile) throws FileNotFoundException {
		main.out3.print("Debug File \n\n");

		main.out3.print("Unsorted List: ");

		listBinTreeNode dummy2 = new listBinTreeNode();
		oldListHead = dummy2;

		listBinTreeNode dummy = new listBinTreeNode();
		listHead = dummy;

		String ch;
		int num;

		Scanner scan = new Scanner(inFile);
		String tempCh = null;
		int tempPr = 0;

		while (scan.hasNext()) {

			tempCh = scan.next();
			tempPr = Integer.parseInt(scan.next());
			main.out3.print(tempCh + tempPr + " ");

			listBinTreeNode newNode = new listBinTreeNode(tempCh, tempPr);
			newNode.printNode(newNode);
			listInsert(newNode);

		}

		oldListHead.next = listHead.next;
	}

	void printList() {
		listBinTreeNode index = listHead;
		main.out3.print("\n\nSorted List: ");

		main.out3.print("listHead -->(dummy, 0, " + index.next.chStr + ")-->");

		while (index != null) {

			if (index.prob != 0 && index.next != null) // ignore dummy node
				main.out3.print("(" + index.chStr + ", " + index.prob + ", " + index.next.chStr + ")-->");

			else if (index.next == null)
				main.out3.print("(" + index.chStr + ", " + index.prob + ")-->null");

			index = index.next;

		}

		main.out3.print("\n\n");
	}

	void listInsert(listBinTreeNode T) {
		listBinTreeNode spotNode = Spot(T.prob);
		T.next = spotNode.next;
		spotNode.next = T;
	}

	listBinTreeNode Spot(int findInt) {

		listBinTreeNode temp = listHead;

		while (temp.next != null && temp.next.prob < findInt) {
			temp = temp.next;
		}

		return temp;
	}

}

class HuffmanBinaryTree {

	HuffmanBinaryTree(HuffmanLinkedList myList) throws FileNotFoundException {

		//
		myList.printList();

		myList.listHead = myList.listHead.next; // skip over dummy node

		while (myList.listHead.next != null) {

			listBinTreeNode newNode = new listBinTreeNode();

			newNode.chStr = myList.listHead.chStr + myList.listHead.next.chStr;
			newNode.prob = myList.listHead.prob + myList.listHead.next.prob;
			newNode.left = myList.listHead;
			newNode.right = myList.listHead.next;

			printNode(newNode);
			myList.listInsert(newNode);
			myList.listHead = myList.listHead.next.next;

		}

		Root = myList.listHead;
		main.out1.print("Huffman Code Pairs: \n\n");

		constructCharCode(Root, "");

		main.out2.print("preOrder Traversal:\n\n");
		preOrder(Root);

	}

	listBinTreeNode Root;

	public void constructCharCode(listBinTreeNode T, String code) throws FileNotFoundException {

		if (T == null)
			return;

		else if (T.left == null && T.right == null) {
			main.out1.print(T.chStr + " " + code + "\n");
		} else {
			constructCharCode(T.left, code + "0");
			constructCharCode(T.right, code + "1");
		}

	}

	void preOrder(listBinTreeNode T) throws FileNotFoundException {


		if (T == null)
			return;
		else {
			printNode2(T); // to outfile2 // argv[3]
			preOrder(T.left);
			preOrder(T.right);
		}
	}

	void printNode2(listBinTreeNode T) throws FileNotFoundException {

		main.out2.print("chStr: ");
		main.out2.print(T.chStr + " ");
		main.out2.print("Probability: " + T.prob + " ");
		if (T.next != null) {
			main.out2.print("Next Char: ");
			main.out2.print(T.next.chStr + " ");
		}
		if (T.left != null) {
			main.out2.print("Left Char: ");
			main.out2.print(T.left.chStr + " ");
		}
		if (T.right != null) {
			main.out2.print("Right Char: ");
			main.out2.print(T.right.chStr + " ");
		}

		main.out2.print("\n");

	}

	void printNode(listBinTreeNode T) throws FileNotFoundException {

		main.out3.print("chStr: ");
		main.out3.print(T.chStr + " ");
		main.out3.print("Probability: " + T.prob + " ");
		if (T.next != null) {
			main.out3.print("Next Char: ");
			main.out3.print(T.next.chStr + " ");
		}
		if (T.left != null) {
			main.out3.print("Left Char: ");
			main.out3.print(T.left.chStr + " ");
		}
		if (T.right != null) {
			main.out3.print("Right Char: ");
			main.out3.print(T.right.chStr + " ");
		}

		main.out3.print("\n");
	}

}
