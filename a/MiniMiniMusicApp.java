import javax.sound.midi.*;

public class MiniMiniMusicApp {

	public static void main(String[] args) {
		MiniMiniMusicApp mini=new MiniMiniMusicApp();
		mini.play();
	}
	public void play() {
		try{
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			
			Sequence seq=new Sequence(Sequence.PPQ, 4);
			
			Track track=seq.createTrack();
			
			ShortMessage c=new ShortMessage();
			c.setMessage(192,1,126,0);
			MidiEvent changeInstrument=new MidiEvent(c, 1);
			track.add(changeInstrument);
			
			ShortMessage a=new ShortMessage();
			a.setMessage(144,1,60,100);
			MidiEvent noteOn=new MidiEvent(a, 1);
			track.add(noteOn);
			
			ShortMessage b=new ShortMessage();
			b.setMessage(128,1,44,100);
			MidiEvent noteOff=new MidiEvent(b, 10);
			track.add(noteOff);
			
			player.setSequence(seq);
			player.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
