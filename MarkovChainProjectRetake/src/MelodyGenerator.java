
//Sydney Levy 
//Class: MelodyGenerator (Probability generator)
//Description: 
//Project 2 
//Project 1- Probability Generator
//9-13-2019

import java.util.ArrayList;
import java.util.Set;

public class MelodyGenerator<T> {
	private T t;
	public ArrayList<T> unique = new ArrayList<T>();
	protected ArrayList<Integer> instance = new ArrayList<Integer>();
	private ArrayList<Float> probability = new ArrayList<Float>();
	ArrayList<T> outputNotes = new ArrayList<T>();
	float sum = 0;

	public MelodyGenerator() {
	}

	public T get() {
		return t;
	}

	public ArrayList<T> getUnique() {
		return this.unique;
	}
	
	public void setProb(ArrayList<Float> x) {
		probability  = x;
	}

	void train(ArrayList<T> temp) {
		// assign temp arraylist to be inputed
		for (int i = 0; i < temp.size(); i++) {
			int current = unique.indexOf(temp.get(i));
			// set temp array = to arraylist with unique values
			// correct the count
			if (current == -1) {
				unique.add(temp.get(i));
				instance.add(1);
			} else {
				instance.set(current, instance.get(current) + 1); // sets value of instance
			}
		}
	}

	public ArrayList<Float> get_prob() {
		// find probablity of each note
		sum = 0;
		for (int i = 0; i < instance.size(); i++) {
			sum = instance.get(i) + sum;
			// System.out.println(sum);
		}
		if (sum == 0) {
			for (int i = 0; i < unique.size(); i++) {
				probability.set(i, (float) 0);
			}
		} else {
			probability.clear();
			for (int i = 0; i < instance.size(); i++) {
				probability.add(instance.get(i) / sum);
			}
		}
		return probability;
	}

	public void print_train() {
		// prints probability
		for (int i = 0; i < instance.size(); i++) {
			System.out.println("Token: " + unique.get(i) + " probability: " + probability.get(i));
		}
	}

	public T generate(ArrayList inputProb) {
		// code to generate notes
		int index = 0;
		boolean found = false;
		float total = 0;
		float randToken = (float) Math.random();
		// looks at probability of each note and normalizes them to find each new note
		while (!found && index < inputProb.size() - 1) {
			float temp = (float) inputProb.get(index);
			total = total + temp;
			found = randToken <= total;
			index++;

		}
		if (found)
			return unique.get(index - 1);
		else
			return unique.get(index);
	}
	
	public T generate() {
		return generate(probability);
	}

	public ArrayList<T> generate(int N) {
		// generate number of notes based on input N
		outputNotes.clear();
		for (int i = 0; i < N; i++) {
			outputNotes.add(generate());
		}
		return outputNotes;
	}

}
