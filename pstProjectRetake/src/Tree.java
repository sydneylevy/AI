import java.util.*;

public class Tree <T> {
	Node<T> root = new Node<T>(new ArrayList<T>()); 
	int L; 
	
	public void setL(int i) {
		L = i;
	}
	
	public void train(ArrayList<T> input) {
		for(int i = 0; i < L; i++) { 
			for(int j = 0; j <input.size()-i; j++) {
				ArrayList<T> curSequence = new ArrayList<T> (input.subList(j, j+i));
				Node newNodes = new Node(curSequence); 
				root.addNode(newNodes);
			}
		}
	}
	
	void print() {
		root.print();
	}
}
