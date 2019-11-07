
//Sydney Levy 
//Class: MarkovChain
//Description: 
//Project 2 

import java.util.ArrayList;

public class MarkovChain<T> extends MelodyGenerator {
	// generate notes based on the previous notes probability of what comes next

	// initializing variables
	ArrayList<ArrayList<Integer>> transitionTable = new ArrayList();
	ArrayList<ArrayList<Float>> probTransitionTable = new ArrayList();

	MelodyGenerator<T> gen = new MelodyGenerator<T>();

//	ArrayList<T> unique = new ArrayList<T>();
	ArrayList<Integer> row;

	public MarkovChain() {
	}

	void train(ArrayList input) {
		gen.train(input);

		int lastIndex = -1;
		int tokenIndex;
		// setting transition table to have multiple rows where all indexes are = to 0
		for (int i = 0; i < input.size(); i++) {
			// System.out.println("line 26" + unique);
			tokenIndex = unique.indexOf(input.get(i));
			// if the current index is not found in unique then...
			if (tokenIndex == -1) {
				tokenIndex = unique.size(); // set token to size of unique array
				// create transition table

				ArrayList<Integer> row = new ArrayList<Integer>(); // creating a new array for rows

				// adding rows to transition table
				for (int j = 0; j < unique.size(); j++) {
					row.add(0);
				}
				transitionTable.add(row);

				// adding columns to transition table
				for (int h = 0; h < transitionTable.size(); h++) {
					transitionTable.get(h).add(0);
				}

				// add token to unique
				unique.add((T) input.get(i)); // adding token form input into unique
			}
			if (lastIndex > -1) {
				// add counts of tokens to transition table
				ArrayList<Integer> row = transitionTable.get(lastIndex); // getting correct token from transition table
				int adjuster = row.get(tokenIndex); // to find the correct column based on token index
				row.set(tokenIndex, adjuster + 1); // add token to that column
			}
			lastIndex = tokenIndex;
		}
	}

	public ArrayList<ArrayList<Float>> get_prob() {
		// refractor functions later so that get_prob() takes in a parameter
		for (int i = 0; i < transitionTable.size(); i++) {
			ArrayList<Integer> row_counts = transitionTable.get(i);
			// create probability transition table
			// creating a new array for rows
			float sum = 0;
			for (int m = 0; m < row_counts.size(); m++) {
				sum = sum + row_counts.get(m);
			}
			ArrayList<Float> rowProb = new ArrayList<Float>();

			for (int n = 0; n < row_counts.size(); n++) {
				if (sum == 0) {
						rowProb.add((float) 0);
				} else {
					float temp = row_counts.get(n) / sum;
					rowProb.add(temp);
				}
			}
			probTransitionTable.add(rowProb);
		}
		return probTransitionTable;
	}

	public void print_train() {
		System.out.println(unique);
		for (int i = 0; i < unique.size(); i++) {
			System.out.println(unique.get(i) + " : " + transitionTable.get(i));
		}
		System.out.println("Probabilities:");
		for (int i = 0; i < unique.size(); i++) {
			System.out.println(unique.get(i) + " : " + probTransitionTable.get(i));
		}
	}


	public T generate(T initToken) {

		float index = unique.indexOf(initToken); //find initial token of unique array
		ArrayList<Float> row = probTransitionTable.get((int) index);
		gen.setProb(row);
		return gen.generate(row);
	}
	

	public ArrayList<T> generate(T initToken, int N) {
		ArrayList<T> generateMult = new ArrayList<T>(); 
		for (int i = 0; i < N; i++) {
			T token = (generate(initToken));
			generateMult.add(token);
		}
		return generateMult;
	}
}
