import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class Block extends JLabel{
	private Image img;
	//private int imageNum;
	private String action="null";
	private int delay=1000;
	private int life=1000;
	private String imagePath=null;
	public Block(ImageIcon icon,String imagePath) {
		img=icon.getImage();
		this.imagePath=imagePath;
		//this.imageNum=imageNum;
	}
	//public int getImageNum() {
	//	return imageNum;
	//}
	public void changeImage(ImageIcon icon,String imagePath) {
		img=icon.getImage();
		this.imagePath=imagePath;
		this.getParent().repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img,0,0,getWidth(),getHeight(),null);
	}
	public String getAction() {
		return action;
	}
	public int getDelay() {
		return delay;
	}
	public int getLife() {
		return life;
	}
	public void setInfor(int x,int y,int w,int h) {
		this.setSize(w,h);
		this.setLocation(x,y);
	}
	public void setAction(String action) {
		this.action=action;
	}
	public void setDelay(int delay) {
		this.delay=delay;
	}
	public void setLife(int life) {
		this.life=life;
	}
	public String getImagePath() {
		String[] s=imagePath.split("\\\\");
		if(s.length>1) {
			imagePath=s[s.length-1];
		}
		return imagePath;
	}
}

