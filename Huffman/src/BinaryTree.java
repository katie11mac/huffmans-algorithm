/**
 * 
 * BinaryTree.java create BinaryTree objects, which stores 
 * 	- a root Binary Node
 * 	- the weight of the tree
 * 
 * Based on the following sources ... 
 * 		- CS 201 Textbook: Data Structures and Algorithms (Mark Allen Weiss) 
 * 		- https://www.geeksforgeeks.org/binary-tree-set-1-introduction/ 
 * 
 * @author kmacalintal
 *
 */


public class BinaryTree implements Comparable<BinaryTree>{
	
	BinaryNode root; // Root of Binary Tree
	Double weight; // Weight of Binary Tree
	  
    // --------------------- CONSTRUCTORS ----------------------
	BinaryTree(BinaryNode key){
		root = key;
	}
	  
	BinaryTree(BinaryNode key, Double weight){
		root = key;
		weight = this.weight; 
	}
	  
	BinaryTree(Double weight){
		this.weight = weight;
	}
	
	// -------------- SETTERS / GETTERS FOR WEIGHT --------------
	 
	public void setWeight(Double weight) {
	    this.weight = weight; 
	}
	    
	public Double getWeight() {
    	return this.weight; 
    }
	
    // --------------------- SPECIAL METHODS ---------------------
	
	@Override
	public int compareTo(BinaryTree o) {
		// TODO Auto-generated method stub
		return this.weight.compareTo(o.weight);
	}    

	
	// ------------------------ PRINTING ------------------------ 
	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 * 
	 * Based on CS 201 Textbook: Data Structures and Algorithms (Mark Allen Weiss)
	 */
	public boolean isEmpty(){
		return root == null;
	}
	    
	/**
	 * Print the tree contents through an in order traversal.
	 * 
	 * Based on CS 201 Textbook: Data Structures and Algorithms (Mark Allen Weiss)
	 */
	public void printTree(){
		if( isEmpty( ) )
			System.out.println( "Empty tree" );
		else
			printTree( root );
	}
	    
	/**
	 * Internal method to print a subtree through an in order traversal.
	 * @param t the node that roots the subtree.
	 * 
	 * Based on CS 201 Textbook: Data Structures and Algorithms (Mark Allen Weiss)
	 */
	private void printTree( BinaryNode t ){
		if( t != null ){
	            printTree( t.left );
	            System.out.println( t.element );
	            printTree( t.right );
		}
	}
	
	
	/**
	 * Print the tree contents through a pre-order traversal.
	 * 
	 * Based on https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
	 */
	public void printPostOrder( ){
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
        	printPostorder( root );
    }

	/**
	 * Internal method to print a subtree through a pre-order traversal.
	 * @param node the node that roots the subtree.
	 * 
	 * Based on https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder
	 */
	private void printPostorder(BinaryNode node){
        if (node == null)
            return;
 
     // now deal with the node
        if(node.left == null && node.right == null) {
        	System.out.print(node.element + " \n");
        }
        // first recur on left subtree
        printPostorder(node.left); 
        
        // then recur on right subtree
        printPostorder(node.right);
    }
	

}
