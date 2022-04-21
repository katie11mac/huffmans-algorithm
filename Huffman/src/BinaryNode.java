/**
 * BinaryNode.java creates BinaryNode objects, which store an object and points to a left and right BinaryNode.
 * 
 *  Based on the following sources ... 
 * 		- Generally >> CS 201 Textbook: Data Structures and Algorithms (Mark Allen Weiss) 
 * 		- Implementing comparator >> Based on https://stackoverflow.com/questions/8206432/using-compareto-in-binary-search-tree-program 
 *
 * @author kmacalintal
 *
 * @param <Object>
 */


public class BinaryNode<Object extends Comparable<? super Object>> implements Comparable<BinaryNode<Object>>{

	Object element;            // The data in the node
	BinaryNode<Object> left;   // Left child
    BinaryNode<Object> right;  // Right child
    	
    
    // --------------------- CONSTRUCTORS ----------------------
    BinaryNode( Object theElement ){
    	this( theElement, null, null );
    }

    BinaryNode( Object theElement, BinaryNode<Object> lt, BinaryNode<Object> rt ){
    	element  = theElement;
    	left     = lt;
    	right    = rt;
    }
    
    
    // --------------------- SPECIAL METHODS ---------------------
    @Override 
    public String toString(){
		return (" BINARY NODE (Element: " + element + " || Left: " + left + " || right: " + right + ")" );
	}
    
	@Override
	public int compareTo(BinaryNode o) {
		return element.compareTo((Object) o.element);
	}
   
}


