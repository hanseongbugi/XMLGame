import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BackgroundPanel extends JPanel{
	private ImageIcon []oldback= {new ImageIcon("background1.jpg"),
			new ImageIcon("background2.jpg")};
	private ImageIcon []back=new ImageIcon[oldback.length];
	private JRadioButton []backRadio=new JRadioButton[2];
	private JPanel radioPanel=new JPanel();
	private ToolPanel toolPanel;
	private boolean isSelected=false;
	public BackgroundPanel(ToolPanel toolPanel) {
		//setSize(300,300);
		//setBackground(Color.GRAY);
		this.toolPanel=toolPanel;
		ButtonGroup g=new ButtonGroup();
		
		for(int i=0;i<oldback.length;i++) {
			Image img=oldback[i].getImage();
			Image changeImg=img.getScaledInstance(80,80,Image.SCALE_SMOOTH);
			//ImageIcon changeIcon=new ImageIcon(changeImg);
			back[i]=new ImageIcon(changeImg);
		}
		for(int i=0;i<backRadio.length;i++) {
			backRadio[i]=new JRadioButton(back[i]);
			g.add(backRadio[i]);
			backRadio[i].addItemListener(new RadioListener());
			add(backRadio[i]);
		}
	
		//add(radioPanel);
	
	}
	public boolean backgroundSelected() {
		return isSelected;
	}
	public void setBackground(String setBackground) {
		isSelected=true;
		for(int i=0;i<back.length;i++) {
			if(back[i].equals(setBackground)) {
				backRadio[i].setSelected(true);
			}
		}
	}
	class RadioListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.DESELECTED)return;
			if(backRadio[0].isSelected()) {
				toolPanel.setImage(oldback[0],"background1.jpg");
				isSelected=true;
			}
			else {
				toolPanel.setImage(oldback[1],"background2.jpg");
				isSelected=true;
			}
			toolPanel.repaint();
		}
	}
}
