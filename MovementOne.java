import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class MovementOne {

	public static ArrayList<Note> generate(int lowestNote, int highestNote, int lowEndOctave){

		ArrayList<Note> mvmtOne = new ArrayList<>();

		int keyType = Note.getKeyType(lowEndOctave-4);

		mvmtOne.add(new Note("\\time 1/8 \n \\tempo Freely \n \\once \\hide Staff.TimeSignature \n \\override Score.BarLine.stencil = ##f \n   \\override Score.BarNumber.break-visibility = ##(#f #f #f)" ));
		mvmtOne.add(new Note("\n\n\\key "+Note.stringFromMidiNumber(lowEndOctave-4,0)+" \\major" ));
		
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,"["));
		mvmtOne.add(new Note(keyType,lowEndOctave+10,2,"]"));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,"["));
		mvmtOne.add(new Note(keyType,lowEndOctave+10,2,"]"));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,"["));
		mvmtOne.add(new Note(keyType,lowEndOctave+10,2,"]"));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,"["));
		mvmtOne.add(new Note(keyType,lowEndOctave+10,2,"]"));
		int prevPitch = 62;
		ArrayList<Integer> notes = new ArrayList<>();
		for(int progress = 0; progress < 6; progress++){
			notes.clear();
			notes.add(lowEndOctave+8);
			notes.add(lowEndOctave+10);
			notes.add(lowEndOctave+12);
			if(progress > 0){
				notes.add(0,lowEndOctave+7);
			}
			if(progress > 1){
				notes.add(0,lowEndOctave+5);
			}
			if(progress > 2){
				notes.add(0,lowEndOctave+3);
			}
			if(progress > 3){
				notes.add(0,lowEndOctave+1);
			}
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			//adds both up and down	
			possible.addAll(genPerms(notes,true));
			possible.addAll(genPerms(notes,false));

			ArrayList<Note> newMaterial = noodle(keyType,30 - 2*progress,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
			
		}
		int finishPitch = notes.indexOf(prevPitch);
		for(int i = finishPitch-1; i > -1; i --){
			mvmtOne.add(new Note(keyType,notes.get(i),2,""));
		}		

		//Fill in from note down to F#
		//20 noodles down in the bass
		int randomNum = ThreadLocalRandom.current().nextInt(4, 10);
		mvmtOne.add(new Note(keyType,lowestNote,2,"["));
		for(int i = 0; i < randomNum; i++){
			mvmtOne.add(new Note(keyType,lowestNote,2,""));
		}
		mvmtOne.add(new Note(keyType,lowestNote,2,"]"));

		randomNum = ThreadLocalRandom.current().nextInt(2, 6);
		for(int i = 0; i < randomNum; i++){
			mvmtOne.add(new Note(keyType,lowestNote,2," ["));
			mvmtOne.add(new Note(keyType,lowestNote+1,2,"]"));

		}
		prevPitch = lowestNote+1;

		{
			notes.clear();
			notes.add(lowestNote);
			notes.add(lowestNote+1);
			notes.add(lowestNote+3);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			possible.addAll(genPerms(notes,false));
			ArrayList<Note> newMaterial = noodle(keyType,15,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}

		//10 noodles low mid again (f - b)
		{
			notes.clear();
			notes.add(lowEndOctave+1);
			notes.add(lowEndOctave+3);
			notes.add(lowEndOctave+5);
			notes.add(lowEndOctave+7);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			ArrayList<Note> newMaterial = noodle(keyType,10,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}


		//30 noodles in bass with A as well

		for(int progress = 0; progress < 5; progress ++){
			notes.clear();
			notes.add(lowestNote);
			notes.add(lowestNote+1);
			notes.add(lowestNote+3);
			if(progress>0){
				notes.add(lowestNote+5);
			}
			if(progress>1){
				notes.add(lowestNote+7);		
			}
			if(progress>2){
				notes.add(lowestNote+8);
			}
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			if(progress > 3){
				possible.addAll(genPerms(notes,false));
			}
			ArrayList<Note> newMaterial = noodle(keyType,7 + 3 * progress,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}


		//15 noodles high mid again (b - e)
		{
			notes.clear();
			notes.add(lowEndOctave+7);
			notes.add(lowEndOctave+8);
			notes.add(lowEndOctave+10);
			notes.add(lowEndOctave+12);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			ArrayList<Note> newMaterial = noodle(keyType,20,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}

		//HIGH NOODLES!!!!
		for(int progress = 0; progress < 6; progress++){
			notes.clear();
			notes.add(highestNote-4);
			notes.add(highestNote-2);
			notes.add(highestNote);
			if(progress > 0){
				notes.add(0,highestNote-5);
			}
			if(progress > 1){
				notes.add(0,highestNote-7);
			}
			if(progress > 2){
				notes.add(0,highestNote-9);
			}
			if(progress > 3){
				notes.add(0,highestNote-11);
			}
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			//adds both up and down	
			possible.addAll(genPermsFromRoot(notes,false));

			ArrayList<Note> newMaterial = noodle(keyType,10 - progress,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
			
		}
		finishPitch = notes.indexOf(prevPitch);
		for(int i = finishPitch-1; i > -1; i --){
			mvmtOne.add(new Note(keyType,notes.get(i),2,""));
		}		
		{
			notes.clear();
			notes.add(lowEndOctave+8);
			notes.add(lowEndOctave+10);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			possible.addAll(genPerms(notes,false));
			ArrayList<Note> newMaterial = noodle(keyType,4,lowEndOctave+10,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}
		{
			notes.clear();
			notes.add(lowEndOctave+7);
			notes.add(lowEndOctave+8);
			notes.add(lowEndOctave+10);
			notes.add(lowEndOctave+12);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			ArrayList<Note> newMaterial = noodle(keyType,15,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}
		{
			notes.clear();
			notes.add(lowEndOctave+10);
			notes.add(lowEndOctave+12);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			possible.addAll(genPerms(notes,false));
			ArrayList<Note> newMaterial = noodle(keyType,4,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}
		{
			notes.clear();
			notes.add(lowEndOctave+8);
			notes.add(lowEndOctave+10);
			ArrayList<ArrayList<Integer>> possible = new ArrayList<>();
			possible.addAll(genPerms(notes,true));
			possible.addAll(genPerms(notes,false));
			ArrayList<Note> newMaterial = noodle(keyType,10,prevPitch,possible);
			mvmtOne.addAll(newMaterial);
			prevPitch = newMaterial.get(newMaterial.size()-1).midi;
		}
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,"["));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,""));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,""));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,""));
		while(mvmtOne.size()%8 != 0){
			mvmtOne.add(new Note(keyType,lowEndOctave+8,2,""));
		}
		mvmtOne.add(new Note(keyType,lowEndOctave+8,2,"]"));
		mvmtOne.add(new Note(keyType,lowEndOctave+8,16,""));
		mvmtOne.add(new Note("\n \\override Score.BarLine.stencil = ##t \n"));
		mvmtOne.add(new Note("\\bar \"|.\""));
		return mvmtOne;
	}

	private static ArrayList<Note> noodle(int keyType, int numbNoodles, int prevPitch, ArrayList<ArrayList<Integer>> possible){
		ArrayList<Note> ret = new ArrayList();
		for(int i = 0; i < numbNoodles; i ++){
			int randomNum;
			ArrayList<Integer> doing;
			do{
				randomNum = ThreadLocalRandom.current().nextInt(0, possible.size());
				doing = possible.get(randomNum);
			}while(prevPitch == doing.get(0));
			for(int j = 0; j < doing.size(); j++){
				String info = "";
				if(j == 0){
					info = " [ ";
				}else if(j==doing.size()-1){
					info = " ] ";
				}
				ret.add(new Note(keyType,doing.get(j),2,info));
			}
			prevPitch = doing.get(doing.size()-1);		
		}
		return ret;
	}

	private static ArrayList<ArrayList<Integer>> genPermsFromRoot(ArrayList<Integer> notes, boolean goingUp){
		ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
		for(int length = 2; length <= notes.size(); length ++){
			int start = 0;
			if(!goingUp){
				start = notes.size();
			}
			ArrayList<Integer> segment = new ArrayList<>();
			for(int i = 0; i < length; i ++){
				if(goingUp){
					segment.add(notes.get(i));
				}else{
					segment.add(notes.get(start-i-1));
				}
			}
			ret.add(segment);
		}
		return ret;
	}


	private static ArrayList<ArrayList<Integer>> genPerms(ArrayList<Integer> notes, boolean goingUp){
		ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
		for(int length = 2; length <= notes.size(); length ++){
			for(int start = 0; start + length <= notes.size(); start++){
				ArrayList<Integer> segment = new ArrayList<>();
				for(int i = 0; i < length; i ++){
					if(goingUp){
						segment.add(notes.get(start+i));
					}else{
						segment.add(notes.get(start+length-i-1));
					}
				}
				ret.add(segment);
			}
		}
		return ret;
	}

}