package net.mgsx.pd.midi;

import java.io.IOException;

import org.puredata.core.PdBase;

import com.badlogic.gdx.files.FileHandle;
import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

public class DefaultMidiMusic implements MidiMusic
{
	private MidiProcessor sequencer;
	public MidiFile mfile; // XXX
	
	public DefaultMidiMusic(FileHandle file) {
		try {
			mfile = new MidiFile(file.read());
			sequencer = new MidiProcessor(mfile);
			sequencer.registerEventListener(new MidiEventListener() {
				
				@Override
				public void onStop(boolean finished) {
					System.out.println("onStop");
				}
				
				@Override
				public void onStart(boolean fromBeginning) {
					System.out.println("onStart");
					
				}
				
				@Override
				public void onEvent(MidiEvent event, long ms) {
					if(event instanceof NoteOn){
						NoteOn no = (NoteOn)event;
						PdBase.sendNoteOn(no.getChannel(), no.getNoteValue(), no.getVelocity());
					}
					else if(event instanceof NoteOff){
						NoteOff no = (NoteOff)event;
						PdBase.sendNoteOn(no.getChannel(), no.getNoteValue(), 0);
					}
					else if(event instanceof ProgramChange){
						ProgramChange no = (ProgramChange)event;
						PdBase.sendProgramChange(no.getChannel(), no.getProgramNumber());
					}
					
				}
			}, MidiEvent.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void play() {
		sequencer.start();
	}

	@Override
	public void pause() {
		sequencer.stop();
	}

	@Override
	public void stop() {
		sequencer.stop();
		sequencer.reset();
	}

	@Override
	public boolean isPlaying() {
		return sequencer.isRunning();
	}

	@Override
	public void setLooping(boolean isLooping) {
		// not supported yet
	}

	@Override
	public boolean isLooping() {
		// not supported yet
		return false;
	}

	@Override
	public void setVolume(float volume) {
		// not supported yet
	}

	@Override
	public float getVolume() {
		// not supported yet
		return 0;
	}

	@Override
	public void setPan(float pan, float volume) {
		// not supported yet
	}

	@Override
	public void setPosition(float position) {
		// not supported yet
	}

	@Override
	public float getPosition() {
		// not supported yet
		return 0;
	}

	@Override
	public void dispose() {
		sequencer.stop();
	}

	@Override
	public void setOnCompletionListener(OnCompletionListener listener) {
		// not supported yet
	}

}