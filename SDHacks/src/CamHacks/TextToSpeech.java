package CamHacks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.modules.synthesis.Voice;
import marytts.signalproc.effects.AudioEffects;
import marytts.signalproc.effects.VolumeEffect;

/**
 * @author GOXR3PLUS
 *
 */
public class TextToSpeech {
	
	private AudioPlayer tts;
	private MaryInterface marytts;
	
	/**
	 * Constructor
	 */
	public TextToSpeech() {
		try {
			marytts = new LocalMaryInterface();
			
		} catch (MaryConfigurationException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	//----------------------GENERAL METHODS---------------------------------------------------//
	public static void main(String args){
		TextToSpeech tts = new TextToSpeech();
		tts.setVoice("cmu-slt-hsmm");
	//	System.out.println(tts.getAvailableVoices());
	//	VolumeEffect volumeEffect = new VolumeEffect(); //be careful with this i almost got heart attack
	//	volumeEffect.setParams("amount:0");
	//	tts.getMarytts().setAudioEffects(volumeEffect.getFullEffectAsString());// + "+" + stadiumEffect.getFullEffectAsString());
		tts.speak(args, 2.0f, false, true);
	}
	
	/**
	 * Transform text to speech
	 * 
	 * @param text
	 *            The text that will be transformed to speech
	 * @param daemon
	 *            <br>
	 *            <b>True</b> The thread that will start the text to speech Player will be a daemon Thread <br>
	 *            <b>False</b> The thread that will start the text to speech Player will be a normal non daemon Thread
	 * @param join
	 *            <br>
	 *            <b>True</b> The current Thread calling this method will wait(blocked) until the Thread which is playing the Speech finish <br>
	 *            <b>False</b> The current Thread calling this method will continue freely after calling this method
	 */
	public void speak(String text , float gainValue , boolean daemon , boolean join) {
		
		// Stop the previous player
		stopSpeaking();
		
		try (AudioInputStream audio = marytts.generateAudio(text)) {
			int W = AudioSystem.write(audio, AudioFileFormat.Type.WAVE, new File("C:\\Users\\rrreg\\Desktop\\CamHacks\\SDHacks\\audio.mp3"));

				    byte[] buffer = new byte[audio.available()];
				    audio.read(buffer);
				    File targetFile = new File("C:\\Users\\rrreg\\Desktop\\CamHacks\\SDHacks\\audio.mpg");
				    OutputStream outStream = new FileOutputStream(targetFile);
				    outStream.write(buffer);
				    SimpleAudioPlayer.get().setAudio("C:\\Users\\rrreg\\Desktop\\CamHacks\\SDHacks\\audio.mp3");
			// Player is a thread(threads can only run one time) so it can be
			// used has to be initiated every time\
//				    
//			tts = new AudioPlayer();
//			tts.setAudio(audio);
//			tts.setGain(gainValue);
//			tts.setDaemon(daemon);
//			tts.start();
//			if (join)
//				try {
//					tts.join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			System.out.println("asdasd");

		} catch (SynthesisException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Error saying phrase.", ex);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "IO Exception", ex);
		} 
	}
	
	/**
	 * Stop the MaryTTS from Speaking
	 */
	public void stopSpeaking() {
		// Stop the previous player
		if (tts != null)
			tts.cancel();
	}
	
	//----------------------GETTERS---------------------------------------------------//
	
	/**
	 * Available voices in String representation
	 * 
	 * @return The available voices for MaryTTS
	 */
	public Collection<Voice> getAvailableVoices() {
		return Voice.getAvailableVoices();
	}
	
	/**
	 * @return the marytts
	 */
	public MaryInterface getMarytts() {
		return marytts;
	}
	
	/**
	 * Return a list of available audio effects for MaryTTS
	 * 
	 * @return
	 */
	public List<Object> getAudioEffects() {
		return StreamSupport.stream(AudioEffects.getEffects().spliterator(), false).collect(Collectors.toList());
	}
	                                                                                                                       
	//----------------------SETTERS---------------------------------------------------//
	
	/**
	 * Change the default voice of the MaryTTS
	 * 
	 * @param voice
	 */
	public void setVoice(String voice) {
		marytts.setVoice(voice);
	}
	
}