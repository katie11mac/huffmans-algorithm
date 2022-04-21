/**
 * MessageChar.java creates MessageChar objects, which stores
 * 	- a character 
 * 	- the probability of that character appearing 
 * 	- the binary encoding of that character 
 * 
 * @author kmacalintal
 *
 */
public class MessageChar implements Comparable<MessageChar>{

	private char character; 
	private Double prob; 
	private String encoding; 
	
	
	// --------------------- CONSTRUCTORS ----------------------
	public MessageChar() {
		
	}
	
	public MessageChar(char character, double prob) {
		this.character = character; 
		this.prob = prob; 
	}
	
	public MessageChar(double prob) {
		this.prob = prob; 
	}
	
	// ------------------------ SETTERS -------------------------
	public void setCharacter(char c) {
		this.character = c;
	}
	
	public void setProb(double p) {
		this.prob = p; 
	}
	
	public void setEncoding(String e) {
		this.encoding = e; 
	}
	
	
	// ------------------------ GETTERS --------------------------
	public char getCharacter() {
		return character; 
	}
	
	public double getProb() {
		return prob; 
	}
	
	public String getEncoding() {
		return encoding; 
	}
	
	// --------------------- SPECIAL METHODS ---------------------
	@Override
	public String toString(){
		return ("[Character: " + character + " || Probability: " + prob + " + Encoding: " + encoding + "]");
	}

	@Override
	public int compareTo(MessageChar mc) {
		return this.prob.compareTo(mc.prob); 
	}
	
}
