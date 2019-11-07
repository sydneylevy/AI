//Sydney Levy 
//Project 1- Probability Generator
//9-13-2019

import java.util.ArrayList;
import java.util.Set;

public class MelodyGenerator<T> {
	private T t;
	private ArrayList<T> unique = new ArrayList<T>();
	private int i;
	private int j;
	private ArrayList<Integer> instance = new ArrayList<Integer>();
	private ArrayList<Float> probability = new ArrayList<Float>();
	ArrayList<T> outputNotes = new ArrayList<T>();
	float sum = 0;

	public MelodyGenerator() {	}

	public T get() {
		return t;
	}

	void train(ArrayList<T> temp) {
		// assign temp arraylist to be inputted
		for (i = 0; i < temp.size(); i++) {
			int current = unique.indexOf(temp.get(i));
			// set temp array = to arraylist with unique values
			// correct the count
			if (current == -1) {
				unique.add(temp.get(i));
				instance.add(1);
			} else {
				instance.set(current, instance.get(current) + 1); //sets value of instance
			}
		}

		// find probablity of each note
		for (int i = 0; i < instance.size(); i++) {
			sum = instance.get(i) + sum;
			// System.out.println(sum);
		}
		for (int i = 0; i < instance.size(); i++) {
			probability.add(instance.get(i) / sum);
		}
	}

	public void print_train() {
		// prints probability
		for (int i = 0; i < instance.size(); i++) {
			System.out.println("Token: " + unique.get(i) + " probability: " + probability.get(i));
		}
	}

	public T generate() {
		// code to generate notes
		int index = 0;
		boolean found = false;
		float total = 0;
		float randIndex = (float) Math.random(); 
		// looks at probability of each note and normalizes them to find each new note
		while (!found && index < probability.size() - 1) {
			total = total + probability.get(index);
			found = randIndex <= total;
			index++;
		}
		if (found)
			return unique.get(index - 1);
		else
			return unique.get(index);
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
