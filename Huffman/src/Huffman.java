/**
 * Author: Katie Macalintal 
 * Implements Huffman's binary encoding algorithm 
 */


import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;



public class Huffman {

	public static void main(String[] args) throws IOException{

		//Runs encode
		String message = "Hello World!"; 
		String encodedMessage = encode(message);
		
		System.out.println("Message: " + message + "\nEncoded Message: " + encodedMessage); 
		
		String message1 = "HHHHHH"; 
		String encodedMessage1 = encode(message1);
		
		System.out.println("Message: " + message1 + "\nEncoded Message: " + encodedMessage1); 
	}

	/**
	 * encode 
	 * Uses Huffman's algorithm to encode message into binary 
	 * and prints a dictionary that shares what each character is encoded to 
	 * 
	 * @param message - String containing desired message to encode 
	 * @return String - encoded message 
	 */
	public static String encode(String message){

		System.out.println(); 
		// Create an alphabet based on the message and assign a probability to each character
		Hashtable<Character, Double> alphabet = getAlphabet(message); 
		
		if (alphabet.size() == 1) {
			return ("*cannot encode a message with only one distinct character*"); 
		}
		
		// Run Huffman's algorithm, which returns the binary tree that should be used to encode
		BinaryTree huffmanTree = huffmansAlgorithm(alphabet); 
//		System.out.println("HUFFMANS"); 
//		huffmanTree.printTree();
//		System.out.println(); 
		
		// encodingsDict is a Hashtable that will store 
		//	the character as the key and its binary encoding as its value 
		Hashtable<Character, String> encodingsDict = new Hashtable<Character,String>(); 
		for(Character key : alphabet.keySet()) {
			encodingsDict.put(key, ""); 
		}
		
//		System.out.println(encodingsDict); 
		
		// Fill the encodingsDict Hashtable with the appropriate binary codes 
		encodingsDict = getEncodingsDict(huffmanTree.root, "", encodingsDict); 
		
		// Print out the dictionary that shares the binary code that each character receives 
		System.out.println("DICTIONARY: "); 
		for(Character key : encodingsDict.keySet()) {
			System.out.println(key + " " + encodingsDict.get(key));  
		}
		System.out.println(); 
		
//		huffmanTree.printPostOrder();
		
		// Encode the message 
		String encodedMessage = ""; 
		for(int i = 0; i < message.length(); i ++) {
			encodedMessage += encodingsDict.get(message.charAt(i)); 
		}
		
		return (encodedMessage);
	}

	/**
	 * getEncodingsDict 
	 * 
	 * Traverse a tree so that the characters stored at each leaf will receive a binary encoding
	 * 
	 * @param node - BinaryNode holding a MessageChar 
	 * @param encoding - String that will contain a character's binary encoding 
	 * @param encodingsDict - Hashtable where the key is the character to encode and the value is its binary encoding 
	 * @return Hashtable<Character, String> - Hashtable where the key is the character to encode and the value is its binary encoding 
	 */
	public static Hashtable<Character, String> getEncodingsDict(BinaryNode<MessageChar> node, String encoding, Hashtable<Character, String> encodingsDict) {
        
		// Very similar to a pre-order tree traversal, which processes parents and then children 
		// 		Based on https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/

		if (node == null)
            return encodingsDict;
 
		// First "deal" with the node as in assign an encoding to the character it holds 
		// 		Only want to do this if the node is a leaf since that's where each character in the message will be stored 
        if(node.left == null && node.right == null) {
//        	System.out.print(node.element + " " + encoding +  " \n");
        	encodingsDict.replace(node.element.getCharacter(), encoding); 
        	node.element.setEncoding(encoding); // Not completely necessary 
//        	System.out.println(node.element + "\n"); 
        }
        
        // Then recur on left subtree
        // Every time travel down a left branch you want to add a 0 to the encoding 
        encoding += '0'; 
        getEncodingsDict(node.left, encoding, encodingsDict); 
        
        // Want to get rid of the 0 we added from processing the left child 
        encoding = encoding.substring(0, encoding.length()-1); 
        
        // Finally recur on right subtree
        // Every time travel down a right branch you want to add a 1 to the encoding 
        encoding += '1'; 
        getEncodingsDict(node.right, encoding, encodingsDict);
     
        return encodingsDict; 
    }
	
	/**
	 * huffmansAlgorithm 
	 * Run Huffman's algorithm on a set of characters with probabilities 
	 * 
	 * @param alphabet - Hashtable with a character as its key and the probability of it appearing
	 * @return BinaryTree where the characters in our alphabet are found at leaves in the tree 
	 */
	public static BinaryTree huffmansAlgorithm(Hashtable<Character, Double> alphabet){
//		System.out.println("**********RUNNING HUFFMAN'S ALGORITHM**************\n\n"); 
		PriorityQueue<BinaryTree> minHeap = getInitialMinHeap(alphabet); 
		minHeap = editMinHeap(minHeap); 
//		System.out.println("\n\n**********DONE RUNNING HUFFMAN'S ALGORITHM**************"); 
		// Removing the smallest element in the minHeap will give us the final tree of characters 
		return minHeap.poll(); 
	}
	
	/**
	 * editMinHeap
	 *
	 * Remove trees with the smallest weight from the heap, merge them, and reinsert them back 
	 * into the heap until there is only 1 tree left in the heap. 
	 * This is the second part of Huffman's algorithm. 
	 * 
	 * @param minHeap - minimum heap that holds trees with weights 
	 * @return PriorityQueue<BinaryTree> - minimum heap with one only tree
	 */
	public static PriorityQueue<BinaryTree> editMinHeap(PriorityQueue<BinaryTree> minHeap){
//		System.out.println("**********EDITING THE MINIMUM HEAP**************"); 
		while (minHeap.size() > 1) {
//			System.out.println("\n ----------------------------------------------- "); 
//			System.out.println("HEAP SIZE: " + minHeap.size()); 
			
			// Remove the 2 trees with the smallest probabilities from the heap 
			BinaryTree tree1 = minHeap.poll(); 
			BinaryTree tree2 = minHeap.poll(); 
			
			// Calculate the new weight of the two trees we are about to merge 
			double newTreeWeight = tree1.getWeight() + tree2.getWeight(); 
			
//			System.out.print("Merging {");
//			tree1.printTree();
//			System.out.print("} AND {"); 
//			tree2.printTree(); 
//			System.out.print("}"); 
			
			// Create a new MessageChar that will be held in our new node for the root of our new tree
			MessageChar mc = new MessageChar(newTreeWeight); 
			BinaryNode<MessageChar> newNode = new BinaryNode(mc, tree1.root, tree2.root); // Makes the root point to the roots of the two trees merging 
			
			// Create the new merged tree and assign it's appropriate weight 
			BinaryTree newTree = new BinaryTree(newNode); 
			newTree.setWeight(newNode.element.getProb());

			
//			System.out.println("\nNEW TREE (weight: " + newTree.getWeight());
//			newTree.printTree();
			
			// Reinsert the merged tree into the min heap 
			minHeap.add(newTree); 
			
		}
//		System.out.println("\n\nFINAL MIN HEAP"); 
//		minHeap.peek().printTree();
//		System.out.println("**********DONE EDITING THE MINIMUM HEAP**************"); 

		return minHeap; 
	}
	

	/**
	 * getInitialMinHeap 
	 * 
	 * Initialize our minimum heap data structure, create a tree for each character with it's 
	 * weight as the probability of it appearing, and insert it into the minimum heap. 
	 * This is the first part of Huffman's algorithm. 
	 * 
	 * @param alphabet - Hashtable containing all the characters in the message and their probabilities of appearing
	 * @return PriorityQueue<BinaryTree> - Minimum heap containing all of the characters and their probabilities 
	 * 			stored in Binary Nodes, which are stored in trees
	 */
	public static PriorityQueue<BinaryTree> getInitialMinHeap(Hashtable<Character, Double> alphabet){
		
//		System.out.println("**********FINDING THE MINIMUM HEAP**************"); 
		
		// Creating custom comparator based on https://howtodoinjava.com/java/collections/java-priorityqueue/
		Comparator<BinaryTree> treeSorter = Comparator.comparing(BinaryTree::getWeight);
		PriorityQueue<BinaryTree> minHeap = new PriorityQueue<>( treeSorter ); 
				
		
		// Iteration based on https://www.geeksforgeeks.org/how-to-iterate-through-hashtable-in-java
		for(Character key : alphabet.keySet()) {
			
//			System.out.println("");
//			System.out.println(key + " " + alphabet.get(key)); 
			
			// Create a MessageChar object for each unique character in the message 
			MessageChar mc = new MessageChar(key, alphabet.get(key)); 
			// Have each BinaryNode contain a MessageChar 
			BinaryNode<MessageChar> charBinNode = new BinaryNode(mc); 
			// Have each BinaryNode become its on Binary Tree 
			BinaryTree charTree = new BinaryTree(charBinNode); 
			//charTree.insert(charBinNode); // use this line if you switch back to BST 
			
			// Associate the weight of the tree with the probability of the MessageChar appearing 
			charTree.setWeight(mc.getProb());
			
			
//			System.out.println("MessageChar >> " + mc + " \nNode >> "+  charBinNode + ""); 
//			System.out.print("TREE >> ");
//			charTree.printTree(); 
			
			// Add each newly created tree to the minHeap 
			minHeap.add(charTree); 		
		}

//		System.out.println("**********DONE FINDING MINHEAP********** \n"); 
		
		return minHeap; 
		
	}
	
	
	/**
	 * getAlphabet
	 * Find all the characters and the probability of each of them appearing based on a given message 
	 * 
	 * @param message - String to encode 
	 * @return - Hashtable<Character, Double> where each entry corresponds to 
	 * 			 a unique character in the message and its calculated probability of appearing 
	 */
	public static Hashtable<Character, Double> getAlphabet(String message){
		
//		System.out.println("\n**********RUNNING getAlphabet**********"); 
		
		Hashtable<Character, Double> alphabet = new Hashtable<Character, Double>();
		
		// Add each unique character in the message to the HashTable 
		// with their initial values corresponding to their number of appearances 
		for(int i = 0; i < message.length(); i++) {
			if (alphabet.containsKey(message.charAt(i))){
				alphabet.replace(message.charAt(i), alphabet.get(message.charAt(i)) + 1.0); 
			} else {
				alphabet.put(message.charAt(i), 1.0); 
			}
		}
		
				
		// Iterate through our completed Hashtable to calculate the probability of each letter 
		// Note: This is not a completely necessary step as the number of appearances denotes a similar idea 
		//     Iteration based on https://www.geeksforgeeks.org/how-to-iterate-through-hashtable-in-java/
		for(Character key : alphabet.keySet()) {
			alphabet.put(key, (double) (alphabet.get(key) / message.length()));
		}
		
//		System.out.println("alphabet: " + alphabet); 
//		System.out.println("**********getAlphabet DONE**********\n"); 
		
		return alphabet; 
		
	}

	

}