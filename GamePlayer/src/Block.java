import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Block extends JLabel implements Runnable{
	private Image img;
	private int life;
	private int delay;
	private String action;
	private boolean hidenAction=true;
	private boolean back=true;
	private Thread th;
	private SynchronizedObject obj;
	public Block(int x, int y, int w, int h,int life,String action,int delay, ImageIcon icon,SynchronizedObject obj) {
		this.setBounds(x,y,w,h);
		this.life=life;
		this.action=action;
		this.delay=delay;
		this.obj=obj;
		img = icon.getImage();
		th=new Thread(this);
	}
	public Thread getBlockThread() {
		return th;
	}
	public boolean isInfiniteBlock() {
		if(life==1000)return true;
		else return false;
	}
	//블록이 총알에 맞으면 호출
	public boolean bump(int damage) {
		life-=damage;
		if(life<=0)return false;
		else return true;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				//System.out.println("run");
				if(!action.equals("null")) {
					
					int x=this.getX();
					if(action.equals("hiden")) {
						if(hidenAction)hidenAction=false;
						else hidenAction=true;
						setVisible(hidenAction);
					}
					else if(action.equals("shake")) {
						if(back)
							x+=10;
						else
							x-=10;
						if(x>=getParent().getWidth()-this.getWidth()) back=false;
					
						if(x<=0) back=true;
					
					}
				
				
					this.setLocation(x, getY()); //범위안이면 이동
					
				}
				obj.pauseThread();
				Thread.sleep(delay);
				
			}catch(InterruptedException e) {
				return;
			}
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}