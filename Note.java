import java.util.ArrayList;


public class Note {


	//0 = sharps and flats
	//1 = sharps
	//2 = flats
	//3 = double fucking sharps
	static String[] titleNames = {"C","Db","D","Eb","E","F","F#","G","Ab","A","Bb","B"};
	static String[][] noteNames = {{"c","cis","d","ees","e","f","fis","g","aes","a","bes","b"},
	{"c","cis","d","dis","e","f","fis","g","gis","a","ais","b"},
	{"c","des","d","ees","e","f","ges","g","aes","a","bes","b"},
	{"bis","cis","d","dis","e","eis","fis","g","gis","a","ais","b"}};
	static int[] keyTypes = {0,2,1,2,1,2,3,1,2,1,2,1};
	int midi;
	public String lilypond;
	int duration;//in 16th notes
	String pitch;

	public static int dur(ArrayList<Note> notes){
		int sum = 0;
		for(int i = 0; i < notes.size(); i++){
			sum += notes.get(i).duration;
		}
		return sum;
	}

	public static int getKeyType(int key){
		return keyTypes[key%12];
	}

	public static String stringFromMidiNumber(int value,int keyType){
		return noteNames[keyType][value%12];
	}

	public static String title(int value){
		return titleNames[value%12];
	}

	public static String toString(ArrayList<Note> notes){
		String ret = "";
		for(Note n : notes){
			ret = ret + n;
		}
		return ret;
	}

	public Note(int keyType, int pitchValue, int dur, String message){
		midi = pitchValue;
		if(dur>16 || dur <1){
			dur = 16;
			System.out.println("invalid NOTE duration, using whole note");
		}
		duration = dur;
		if(midi == -1){
			pitch = "r";
		}else if(midi < -1){
			System.out.println("BAD MIDI NUMBER!");
			System.out.println("Pitch: "+midi);
			
		}else{
			pitch = noteNames[keyType][midi%12];
			int octave = midi/12;
			//fucking b# man
			if(midi%12==0 && keyType == 3){
				octave--;
			}
	 		octave -= 4;
	 		while(octave!=0){
	 			if(octave>0){
	 				pitch+="'";
	 				octave--;
	 			}else{
	 				pitch+=",";
	 				octave++;
	 			}
	 		}
		}
 		String[] lengths = {" error ","16","8","8.","4",
 			"4 ~"+pitch+"16","4.","4..","2",
 			"2 ~"+pitch+"16","2 ~"+pitch+"8","2 ~"+pitch+"8.","2.",
 			"2. ~"+pitch+"16","2..","2...","1"};
 		lilypond =pitch+lengths[duration];
 		if(message!=null){
			lilypond = lilypond + message;
 		}
	}
	
	public Note(String message){
		lilypond = message;
	}

	public String toString(){
		return lilypond+" ";
	}
	public int dur(){
		return duration;
	}
}