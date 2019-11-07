
/*
 * c2017-2019 Courtney Brown 
 * 
 * Class: H
 * Description: Demonstration of MIDI file manipulations, etc. & 'MelodyPlayer' sequencer
 * 
 */

import processing.core.*;

import java.util.*;

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

//import javax.sound.midi.*;

			//make sure this class name matches your file name, if not fix.
public class pstMain extends PApplet {

	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; //read a midi file
	
	Tree<Character> tree1 = new Tree<Character>(4, 0.1);
	Tree<Character> tree5 = new Tree<Character>(4, 0.15);
	Tree<Character> tree3 = new Tree<Character>(4, 0.1);
	Tree<Character> tree7 = new Tree<Character>(4, 0.1);
	Tree<Character> tree6 = new Tree<Character>(4, 0.15);
	Tree<Character> tree8 = new Tree<Character>(4, 0.15);

	
	Tree<Integer> tree4 = new Tree<Integer>(4, 0.1);
	Tree<Integer> tree2 = new Tree<Integer>(4, 0.15);


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("pstMain"); //change this to match above class & file name 

	}

	//setting the window size to 300x300
	public void settings() {
		
		size(600, 800);

	}

	//doing all the setup stuff
	public void setup() {
		background(255, 230, 232);

		// returns a url
		String filePath = getPath("mid/MaryHadALittleLamb.mid");
		// playMidiFile(filePath);

		midiNotes = new MidiFileToNotes(filePath); //creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
													//be created with "new". Note how every object is a pointer or reference. Every. single. one.


//		// which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);

		player.setup();
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
	

	}

	public void draw() {
		//player.play(); //play each note in the sequence -- the player will determine whether is time for a note onset
		gui();

	}

	//this finds the absolute path of a file
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

	//this function is not currently called. you may call this from setup() if you want to test
	//this just plays the midi file -- all of it via your software synth. You will not use this function in upcoming projects
	//but it could be a good debug tool.
	void playMidiFile(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

	
	public void gui() {
		fill(254, 127, 156);
		rect(100, 50, 400, 100);
		rect(100, 250, 400, 100);
		rect(100, 450, 400, 100);
		rect(100, 650, 400, 100);
		fill(0);
		textSize(18);
		text("Press 1 & 5 for ABRACADABRA", 120, 110);
		text("Press 2 & 6 for ACADAACBDA", 120, 310);
		text("Press 3 & 7 for ABCCCDAADCDAABCADAD", 120, 510);
		text("Press 4 & 8 for Mary Had A Little Lamb", 120, 710);

	}
	
	//this starts & restarts the melody.
	public void keyPressed() {
		if (key == ' ') {
			player.reset();
			println("Melody started!");
		}
		if (key == '1') {
			Character[] abra = {'a', 'b', 'r', 'a', 'c', 'a', 'd', 'a', 'b', 'r', 'a'} ; 
			ArrayList <Character> data1 = new ArrayList<Character> (Arrays.asList(abra));
			tree1.train(data1);
			System.out.println("ABRACADABRA");
			tree1.print();
			}
		if (key == '2') {
			Character[] acad = {'a', 'c', 'a', 'd', 'a', 'a', 'c', 'b', 'd', 'a'} ; 
			ArrayList <Character> data2 = new ArrayList<Character> (Arrays.asList(acad));
			tree3.train(data2);
			System.out.println("ACADAACBDA");
			tree3.print();
			}
		if (key == '3') {
			Character[] abcc = {'a', 'b', 'c', 'c', 'c', 'd', 'a', 'a', 'd', 'c', 'd', 'a', 'a', 'b', 'c', 'a', 'd', 'a', 'd'};
			ArrayList <Character> data3 = new ArrayList<Character> (Arrays.asList(abcc));
			tree5.train(data3);
			System.out.println("ABCCCDAADCDAABCADAD");
			tree5.print();
		}
		if (key == '4') {
			tree2.train(midiNotes.getPitchArray());
			System.out.println("Mary Had A Little Lamb");
			tree2.print();
		}
		
		if (key == '5') {
			Character[] abra = {'a', 'b', 'r', 'a', 'c', 'a', 'd', 'a', 'b', 'r', 'a'} ; 
			ArrayList <Character> data1 = new ArrayList<Character> (Arrays.asList(abra));
			tree6.train(data1);
			System.out.println("ABRACADABRA");
			tree6.print();
			}
		if (key == '6') {
			Character[] acad = {'a', 'c', 'a', 'd', 'a', 'a', 'c', 'b', 'd', 'a'} ; 
			ArrayList <Character> data2 = new ArrayList<Character> (Arrays.asList(acad));
			tree7.train(data2);
			System.out.println("ACADAACBDA");
			tree7.print();
			}
		if (key == '7') {
			Character[] abcc = {'a', 'b', 'c', 'c', 'c', 'd', 'a', 'a', 'd', 'c', 'd', 'a', 'a', 'b', 'c', 'a', 'd', 'a', 'd'};
			ArrayList <Character> data3 = new ArrayList<Character> (Arrays.asList(abcc));
			tree8.train(data3);
			System.out.println("ABCCCDAADCDAABCADAD");
			tree8.print();
		}
		if (key == '8') {
			tree4.train(midiNotes.getPitchArray());
			System.out.println("Mary Had A Little Lamb");
			tree4.print();
		}
	}
	

}
