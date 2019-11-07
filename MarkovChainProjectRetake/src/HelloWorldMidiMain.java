
//Sydney Levy 
//Class: HelloWorldMidiMain
//Description: 
//Project 2 

import processing.core.*;

import java.util.*;

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;
import java.util.Random; 


import java.io.UnsupportedEncodingException;
import java.net.*;
// Eric's contact:
// email: hongyum@smu.edu
// 214-676-6096

//import javax.sound.midi.*;

//make sure this class name matches your file name, if not fix.
public class HelloWorldMidiMain extends PApplet {

	MelodyPlayer player; // play a midi sequence
	MelodyPlayer player2; // play a midi sequence

	MidiFileToNotes midiNotes; // read a midi file
	MidiFileToNotes midiNotes2; // read a midi file

	// initializing melody generator objects for unit tests
	MelodyGenerator<Integer> pitchGenerator = new MelodyGenerator<Integer>();
	MelodyGenerator<Double> rhythmGenerator = new MelodyGenerator<Double>();

	// melody generator objects for playing generated song
	MelodyGenerator<Integer> gardelPitchGenerator = new MelodyGenerator<Integer>();
	MelodyGenerator<Double> gardelRhythmGenerator = new MelodyGenerator<Double>();
	
	MarkovChain<Integer> markovGardelPitch = new MarkovChain();
	MarkovChain<Double> markovGardelRhythm = new MarkovChain();


	MarkovChain<Integer> markovPitchGenerator = new MarkovChain<Integer>();
	MarkovChain<Double> markovRhythmGenerator = new MarkovChain<Double>();

	MelodyGenerator<Integer> randomProbPitch = new MelodyGenerator();
	MelodyGenerator<Double> randomProbRhythm = new MelodyGenerator();
	

	MelodyGenerator<Integer> pitchProb = new MelodyGenerator();
	MelodyGenerator<Double> rhythmProb = new MelodyGenerator();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("HelloWorldMidiMain"); // change this to match above class & file name

	}

	// setting the window size to 300x300
	public void settings() {
		size(600, 600);

	}

	// doing all the setup stuff
	public void setup() {
		background(255, 230, 232);
		// fill(120, 50, 240);

		// returns a url
		String filePath1 = getPath("mid/MaryHadALittleLamb.mid");
		String filePath2 = getPath("mid/gardel_por.mid");

		// playMidiFile(filePath1);
		


		midiNotes = new MidiFileToNotes(filePath1); // creates a new MidiFileToNotes -- reminder -- ALL objects in Java
													// must
													// be created with "new". Note how every object is a pointer or
													// reference. Every. single. one.
		midiNotes2 = new MidiFileToNotes(filePath2); // creates a new MidiFileToNotes -- reminder -- ALL objects in Java

//		// which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		// creating two sets of midinotes and player to play more than one song
		midiNotes.setWhichLine(0);
		midiNotes2.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);
		player.setup();
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());

		player2 = new MelodyPlayer(this, 100.0f);
//		gardelPitchGenerator.train(midiNotes2.getPitchArray());
//		gardelRhythmGenerator.train(midiNotes2.getRhythmArray());
//		gardelPitchGenerator.get_prob();
//		gardelRhythmGenerator.get_prob();
//		player2.setup();
//		player2.setMelody(gardelPitchGenerator.generate(20));
//		player2.setRhythm(gardelRhythmGenerator.generate(20));
		markovGardelPitch.train(midiNotes2.getPitchArray());
		markovGardelRhythm.train(midiNotes2.getRhythmArray());
		markovGardelPitch.get_prob();
		markovGardelRhythm.get_prob();
		
		randomProbPitch.train(midiNotes2.getPitchArray());
		randomProbPitch.get_prob();
		randomProbRhythm.train(midiNotes2.getRhythmArray());
		randomProbRhythm.get_prob();
		int randIndexPitch = (int) randomProbPitch.generate();
		Double randIndexRhythm = (Double) randomProbRhythm.generate();
		
		player2.setup();
		player2.setMelody(markovGardelPitch.generate(randIndexPitch, 20));
		player2.setRhythm(markovGardelRhythm.generate(randIndexRhythm, 20));

		pitchProb.train(midiNotes.getPitchArray());
		pitchProb.get_prob();
		rhythmProb.train(midiNotes.getRhythmArray());
		rhythmProb.get_prob();

	}

	public void draw() {
		// whether is time for a note onset
		// generating melody
		//player2.play(); // play each note in the sequence -- the player will
		// determine

		gui();

	}

	// this finds the absolute path of a file
	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	// this function is not currently called. you may call this from setup() if you
	// want to test
	// this just plays the midi file -- all of it via your software synth. You will
	// not use this function in upcoming projects
	// but it could be a good debug tool.
	void playMidiFile(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

	// this starts & restarts the melody.
	public void keyPressed() {
		if (key == ' ') {
			player.reset();
			println("Melody started!");
		}
		if (key == '1') {
			Proj2unitTest1();
		}
		if (key == '2') {
			Proj2unitTest2();
		}
		if (key == '3') {
			Proj2unitTest3();
		}
	}

	public void gui() {
		fill(254, 127, 156);
		rect(100, 50, 400, 100);
		rect(100, 250, 400, 100);
		rect(100, 450, 400, 100);
		fill(0);
		textSize(32);
		text("Press 1 for Unit Test 1", 130, 110);
		text("Press 2 for Unit Test 1", 130, 310);
		text("Press 3 for Unit Test 1", 130, 510);
	}

	public void Proj1unitTest1() {
		// prints out probabilities of pitches and melodies
		System.out.println("Unit Test 1");
		System.out.println("Pitches");
		pitchGenerator.train(midiNotes.getPitchArray());
		pitchGenerator.get_prob();
		pitchGenerator.print_train();
		System.out.println("Rhythm");
		rhythmGenerator.train(midiNotes.getRhythmArray());
		rhythmGenerator.get_prob();
		rhythmGenerator.print_train();
	}

	public void Proj1unitTest2() {
		// generates 20 pitches and 20 rhythms
		int num = 20;
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
		rhythmGenerator.get_prob();
		pitchGenerator.get_prob();
		System.out.println("Unit Test 2");
		pitchGenerator.generate(num);
		System.out.println("generated pitches are " + pitchGenerator.generate(20));
		rhythmGenerator.generate(num);
		System.out.println("generated rhythms are " + rhythmGenerator.generate(20));
	}

	public void Proj1unitTest3() {
		// takes in generated notes and then fins the probability of those notes
		int num = 20;

		MelodyGenerator<Integer> pitchGenerator3 = new MelodyGenerator<Integer>();
		MelodyGenerator<Double> rhythmGenerator3 = new MelodyGenerator<Double>();
		ArrayList<Integer> songPitches = new ArrayList<Integer>();
		ArrayList<Double> songRhythm = new ArrayList<Double>();
		System.out.println("Unit Test 3");
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
		rhythmGenerator.get_prob();
		pitchGenerator.get_prob();
		for (int i = 0; i < 10000; i++) {
			songPitches = pitchGenerator.generate(num);
			songRhythm = rhythmGenerator.generate(num);
			System.out.println(songPitches);
			System.out.println(songRhythm);
			pitchGenerator3.train(songPitches);
			rhythmGenerator3.train(songRhythm);
			pitchGenerator3.get_prob();
			rhythmGenerator3.get_prob();
			pitchGenerator3.print_train();
			rhythmGenerator3.print_train();
		}
	}

	public void Proj2unitTest1() {
		// prints out probabilities of pitches and melodies
		System.out.println("Unit Test 1");
		System.out.println("Pitches");
		System.out.println("Transition Table");
		markovPitchGenerator.train(midiNotes.getPitchArray());
		markovPitchGenerator.get_prob();
		markovPitchGenerator.print_train();
		System.out.println("Rhythm");
		System.out.println("Transition Table");
		markovRhythmGenerator.train(midiNotes.getRhythmArray());
		markovRhythmGenerator.get_prob();
		markovRhythmGenerator.print_train();
	}

	public void Proj2unitTest2() {
		// prints out probabilities of pitches and melodies
		int num = 20;
		int indexPitch = (int) pitchProb.generate();
		Double indexRhythm = (Double) rhythmProb.generate();

		System.out.println("Unit Test 2");
		markovPitchGenerator.train(midiNotes.getPitchArray());
		markovRhythmGenerator.train(midiNotes.getRhythmArray());
		markovPitchGenerator.get_prob();
		markovRhythmGenerator.get_prob();
		// markovPitchGenerator.print_train();
		// markovRhythmGenerator.print_train();
		System.out.println(indexPitch);
		System.out.println(indexRhythm);

		System.out.println("generated pitches are " + markovPitchGenerator.generate(indexPitch, 20));
		System.out.println("generated rhythms are " + markovRhythmGenerator.generate(indexRhythm, 20));
	}

	public void Proj2unitTest3() {
		int num = 20;

		MarkovChain<Integer> markovPitchGenerator3 = new MarkovChain<Integer>();
		MarkovChain<Double> markovRhythmGenerator3 = new MarkovChain<Double>();
		ArrayList<Integer> songPitches = new ArrayList<Integer>();
		ArrayList<Double> songRhythm = new ArrayList<Double>();
		



		
		
		System.out.println("Unit Test 3");
		markovPitchGenerator.train(midiNotes.getPitchArray());
		markovRhythmGenerator.train(midiNotes.getRhythmArray());
		markovRhythmGenerator.get_prob();
		markovPitchGenerator.get_prob();
		
		Random rand1 = new Random(); 
		Random rand2 = new Random(); 

		int indexPitch = midiNotes.getPitchArray().get(rand1.nextInt(midiNotes.getPitchArray().size()));
		Double indexRhythm = midiNotes.getRhythmArray().get(rand2.nextInt(midiNotes.getRhythmArray().size()));

		for (int j = 0; j < 10000; j++) {
			songPitches = markovPitchGenerator.generate(indexPitch, num);
			songRhythm = markovRhythmGenerator.generate(indexRhythm, num);

			markovPitchGenerator3.train(songPitches);
			markovRhythmGenerator3.train(songRhythm);
			markovPitchGenerator3.get_prob();
			markovRhythmGenerator3.get_prob();
		}
		System.out.println(songPitches);
		System.out.println(songRhythm);
		System.out.println("Pitches");
		markovPitchGenerator3.print_train();
		System.out.println("Rhythms");
		markovRhythmGenerator3.print_train();
	}
}
