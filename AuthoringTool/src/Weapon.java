
import java.awt.*;
import javax.swing.*;

public class Weapon extends JLabel{
	private Image img;
	private String type;
	private String imagePath;
	public Weapon(ImageIcon icon,String imagePath) {
		img=icon.getImage();
		type="null";
		this.imagePath=imagePath;
	}
	public String getImagePath() {
		return imagePath;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img,0,0,getWidth(),getHeight(),null);
	}
	public String getType() {
		return type;
	}
	public void setInfor(int x,int y,int w,int h) {
		this.setSize(w,h);
		this.setLocation(x,y);
	}
	
	public void setType(String type) {
		this.type=type;
	}
}
