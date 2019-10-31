import java.util.*;

public class Node<T> {
	ArrayList<T> tokenSequence = new ArrayList<T>(); // sequence at this node
	ArrayList<Node<T>> children = new ArrayList<Node<T>>();
	double nodeProb = 0.0;
	int count = 1;

	Node() {
	}

	Node(ArrayList<T> input) {
		tokenSequence = input; // use constructor
	}

	public void setProb(double val)
	{
		this.nodeProb = val;
	}
	public ArrayList<T> getToken() {
		return tokenSequence;
	}

	public ArrayList<Node<T>> getChildren() {
		return children;
	}

	boolean addNode(Node<T> node) { // adds a child node... will only add child node if the input contains "this"

		boolean found = false; // whether the node has been added or not

		ArrayList<T> nodeToken = node.getToken();
		if (tokenSequence.equals(nodeToken)) {
			found = true;
			count++;
		} else if (amIaSuffix(nodeToken) || tokenSequence.size() == 0) {
			int index = 0;
			while (!found && index < children.size()) {
				found = children.get(index).addNode(node);
				index++;
			}
			if (!found) {
				children.add(node);
				found = true;
			}
		}
		return found;
	}

	void printSpaces(int numSpaces) { // prints the tokenSequnece, then prints its children
		for (int i = 0; i < numSpaces; i++) {
			System.out.print(" ");
		}
		System.out.println(tokenSequence);

		for (int i = 0; i < children.size(); i++) {
			children.get(i).printSpaces(numSpaces + 1);
		}
	}

	void print() {
		System.out.println(tokenSequence);

		for (int i = 0; i < children.size(); i++) {
			children.get(i).printSpaces(1);
		}
	}

	boolean amIaSuffix(ArrayList<T> input) { // is the given node/s tokenSequence a suffix of the input
		ArrayList<T> newSuffix = new ArrayList<T>(
				input.subList(input.size() - this.tokenSequence.size(), input.size()));
		return this.tokenSequence.equals(newSuffix);
	}

	
}
