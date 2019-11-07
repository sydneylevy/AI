import java.util.*;

public class Tree<T> {
	Node<T> root = new Node<T>();
	ArrayList<T> alphabets = new ArrayList<T>();
	int L;
	double Pmin;
	int totalInputTokens;

	public Tree() {
	}

	public Tree(int i, double j) {
		L = i;
		Pmin = j;
	}

	public void train(ArrayList<T> input) {
		totalInputTokens = input.size();

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < input.size() - i; j++) {
				ArrayList<T> curSequence = new ArrayList<T>(input.subList(j, j + i));
				Node<T> newNodes = new Node<T>(curSequence);
				// this is code for the easier way to do this
//				double totalSum = totalInputTokens - newNodes.tokenSequence.size() - 1;
//				double val = countNode(newNodes, totalInputTokens) / totalSum;
//				if (val >= Pmin) {
				root.addNode(newNodes);
//				}
			}
		}
		root.pMin(totalInputTokens, Pmin);
	}

	void print() {
		root.print();
	}

	int countNode(Node<T> node, int totalTokens) {
		int counter = 0;
		int tokenSize = node.tokenSequence.size();
		for (int i = 0; i < totalTokens - tokenSize; i++) {
			ArrayList<T> temp = new ArrayList<T>(alphabets.subList(i, i + tokenSize));
			if (temp.equals(node.tokenSequence)) {
				counter++;
			}
		}
		return counter;
	}

}
