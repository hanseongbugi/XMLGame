import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SoundPanel extends JPanel{
	private String[] music= {"background.wav","background2.wav"};
	private JRadioButton []musicRadio=new JRadioButton[2];
	private String selectSound=null;
	private boolean isSelected=false;
	public SoundPanel() {
		ButtonGroup g=new ButtonGroup();
		for(int i=0;i<musicRadio.length;i++) {
			musicRadio[i]=new JRadioButton(music[i]);
			g.add(musicRadio[i]);
			add(musicRadio[i]);
			musicRadio[i].addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.DESELECTED)return;
					if(musicRadio[0].isSelected()) {
						selectSound=music[0];
						isSelected=true;
					}
					else {
						selectSound=music[1];
						isSelected=true;
					}
				}
			});
		}
	}
	public void setSound(String setSound) {
		selectSound=setSound;
		isSelected=true;
		for(int i=0;i<music.length;i++) {
			if(music[i].equals(setSound)) {
				musicRadio[i].setSelected(true);
			}
		}
	}
	public String getSound() {
		return selectSound;
	}
	public boolean soundSelected() {
		return isSelected;
	}

}
