import javax.sound.sampled.*;
import javax.swing.*;

import org.w3c.dom.Node;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SoundPanel extends JPanel{
	private Clip clip=null;
	private JButton play=new JButton("play");
	private JButton stop=new JButton("stop");
	private String path;
	public SoundPanel(GamePanel gamePanel) {
		setBackground(Color.GRAY);
		add(play);
		add(stop);
		SoundAction action=new SoundAction();
		play.addActionListener(action);
		stop.addActionListener(action);
		loadAudio(gamePanel.getSoundPath());
		
	}
	public void clearSound() {
		if(clip==null)return;
		clip.stop();
		clip=null;
	}
	
	private void loadAudio(String pathName) {
		try {
			clip=AudioSystem.getClip();
			File audioFile=new File(pathName);
			AudioInputStream audioStream=AudioSystem.getAudioInputStream(audioFile);
			
			clip.open(audioStream);
			clip.start();
		}
		catch(LineUnavailableException e) {
			return;
		}
		catch(UnsupportedAudioFileException e) {
			return;
		}
		catch(IOException e) {
			return;
		}
	}
	class SoundAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton bt=(JButton)e.getSource();
			String text=bt.getText();
			if(text.equals("play")) {
				clip.setFramePosition(0);
				clip.start();
			}
			else {
				clip.stop();
			}
		}
	}
}

