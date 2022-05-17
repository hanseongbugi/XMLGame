import java.awt.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


public class Weapon extends JLabel implements Runnable{
	private Image img;
	private int damage;
	private String type;
	private int x;
	private int y;
	private int route;
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	private SynchronizedObject obj;
	private Vector<Block>blocks =null;
	private Vector<Item>items=null;
	private boolean isBump=false;
	private int oldWidth;
	private int oldHeight;
	private int defaultDelay=150;
	private int delay;
	private Clip clip;
	public Weapon(int x, int y, int w, int h,int damage,String type, 
			ImageIcon icon,GamePanel gamePanel,ScorePanel scorePanel,SynchronizedObject obj) {
		this.setBounds(x,y,w,h);
		this.damage=damage;
		this.type=type;
		this.obj=obj;
		this.gamePanel=gamePanel;
		this.scorePanel=scorePanel;
		img = icon.getImage();
		loadAudio("effect.wav");
	}
	public void setPoint(int x,int y) {
		this.x=x; 
		this.y=y;
	}
	public String getType() {
		return type;
	}
	private void sizeType() {
		if(!isBump)return;
		setSize(getWidth()+10,getHeight()+10);
		isBump=false;
	}
	private void fastType() {
		if(!isBump)return;
		delay-=10;
		if(delay<=0)delay=10;
		isBump=false;	
	}
	private void loadAudio(String pathName) {
		try {
			clip=AudioSystem.getClip();
			File audioFile=new File(pathName);
			AudioInputStream audioStream=AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
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
	@Override
	public void run() {
		blocks=gamePanel.getBlocks(); //블록 목록 얻기
		items=gamePanel.getItems(); //item 목록 얻기
		oldWidth=getWidth(); //공의 원래width
		oldHeight=getHeight(); //공의 원래 height
		delay=defaultDelay; //delay가 변화할 수 있다.
		route=10;
		boolean site;
		int beforeX=this.getX();
		int beforeY=this.getY();
		int xPos=getX();
		double yPos=getY(); 
		double radius=(((y-beforeY)*10)/(x-beforeX)); //각도식
		
		if(x>=((gamePanel.getWidth()/2)+40)) { //오른쪽
			site=true;
			if(radius==0)radius=-0.1;
		}
		else { //왼쪽
			site=false;
			if(route>0)
				route*=-1;
			if(radius==0)radius=0.1;
		}
		while(true) {
			try {

				if(site) { //오른쪽일때
					xPos+=route;
					yPos+=radius;
					if(xPos>=getParent().getWidth()-40) { //끝에 다으면
						route*=-1;
						isBump=true;
					}
					if(xPos<=0) { //끝에 다으면
						route*=-1;
						isBump=true;
						}
					}
				else { //왼쪽
					xPos+=route;
					yPos-=radius;
					if(xPos<=0) {
						route*=-1;
						isBump=true;
					}
					if(xPos>=getParent().getWidth()-40) {
						route*=-1;
						isBump=true;
					}
				}
				setLocation(xPos,(int)yPos); //이동
				
				//블록이나 아이템에 공이 다았는지 확인
				for(int i=0;i<blocks.size();i++) {
					if(blockHit(i,xPos,(int)yPos,this)) {
						isBump=true;
						//route=-1;
						radius*=-1;
						clip.setFramePosition(0);
						clip.start();
						Block b=blocks.get(i);
						if(!b.isInfiniteBlock()) {
						if(!b.bump(damage)) {
							b.getBlockThread().interrupt();
							gamePanel.remove(b);
							scorePanel.setScore();
							blocks.remove(i);
							gamePanel.getParent().repaint();
							}
						}
					}
				}
				for(int i=0;i<items.size();i++) {
					if(itemHit(i,xPos,(int)yPos,this)) {
						radius*=-1;
						isBump=true;
						clip.setFramePosition(0);
						clip.start();
						Item item=items.get(i);
						if(!item.bump(damage)) {
							//System.out.println("itemBump");
							item.getItemThread().interrupt();
							gamePanel.remove(item);
							if(item.getItemType().equals("score"))scorePanel.increaseScoreValue();
							else if(item.getItemType().equals("damage"))damage+=1;
							items.remove(i);
							gamePanel.getParent().repaint();
						}
					}
				}
				if(type.equals("size"))sizeType();
				else if(type.equals("fast"))fastType();
				
				if(gamePanel.gameOver()) {
					scorePanel.totalScore();
					return;
				}
				
				if(yPos<=0||yPos >= (gamePanel.getHeight()-this.getHeight())+20) {
					setLocation(beforeX,beforeY);
					if(type.equals("size"))setSize(oldWidth,oldHeight);
					return;
				}
				obj.pauseThread();
				Thread.sleep(delay);
			}catch(InterruptedException e) {
				return;
			}
		}
		
	}
	private boolean blockHit(int i,int xPos,int yPos,JComponent cm) {
		if(blockTargetContains(i,xPos,yPos)||
				blockTargetContains(i,xPos+cm.getWidth()-1,yPos+cm.getHeight()-1)||
				blockTargetContains(i,xPos,yPos+cm.getHeight()-1))
			return true;
		else
			return false;
	}
	private boolean blockTargetContains(int i,int x,int y) {
		Block b=blocks.get(i);
		if((b.getX()<=x)&&(b.getX()+b.getWidth()-1>=x)&&
				((b.getY()<=y)&&(b.getY()+b.getHeight()-1>=y))) {
			return true;
		}
		else
			return false;
	}
	
	private boolean itemHit(int i,int xPos,int yPos,JComponent cm) {
		if(itemTargetContains(i,xPos,yPos)||
				itemTargetContains(i,xPos+cm.getWidth()-1,yPos+cm.getHeight()-1)||
				itemTargetContains(i,xPos,yPos+cm.getHeight()-1))
			return true;
		else
			return false;
	}
	private boolean itemTargetContains(int i,int x,int y) {
		Item item=items.get(i);
		if((item.getX()<=x)&&(item.getX()+item.getWidth()-1>=x)&&
				((item.getY()<=y)&&(item.getY()+item.getHeight()-1>=y))) {
			return true;
		}
		else
			return false;
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

class BoundsAction extends MouseAdapter{
	private Weapon weapon=null;
	public BoundsAction(Weapon weapon) {
		this.weapon=weapon;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		weapon.setPoint(e.getX(),e.getY());
		Thread th=new Thread(weapon);
		th.start();
	}
}
