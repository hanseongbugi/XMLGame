import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class SizePanel extends JPanel{
	private int width=0;
	private int height=0;
	private JTextField wField=new JTextField(10);
	private JTextField hField=new JTextField(10);
	public SizePanel() {
		setLayout(new GridLayout(3,2));
		add(new JLabel("게임 Size 입력"));
		add(new JLabel(" "));
		add(new JLabel("Width  : "));
		add(wField);
		add(new JLabel("Height : "));
		add(hField);
		wField.addActionListener(new PanelAction());
		hField.addActionListener(new PanelAction());
	}
	public void setSizeInfor(int w,int h) {
		width=w;
		height=h;
	}
	public int getW() {
		return width;
	}
	
	public int getH() {
		return height;
	}
	public void setPanelSize() {
		if(wField.getText().equals(""))return;
		if(hField.getText().equals(""))return;
		width=Integer.parseInt(wField.getText());
		wField.setText("");
		height=Integer.parseInt(hField.getText());
		hField.setText("");
	}
	class PanelAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField jt=(JTextField)e.getSource();
			if(jt==wField) {
				width=Integer.parseInt(jt.getText());
			}
			else
				height=Integer.parseInt(jt.getText());
		}
	}

}
