import java.awt.*;
import java.util.Vector;

import javax.imageio.ImageTypeSpecifier;
import javax.swing.*;

import java.awt.event.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GamePanel extends JPanel{
	private ImageIcon bgImg;
	private Vector<Block> blocks=new Vector<Block>(20);
	private Vector<Weapon> weapons=new Vector<Weapon>(5);
	private Vector<Item> items=new Vector<Item>(5);
	private ScorePanel scorePanel;
	private boolean gameStatus=true;
	private String sound;
	private SynchronizedObject obj;
	private int infiniteBlock=0;
	public GamePanel(Node gamePanelNode,ScorePanel scorePanel,SynchronizedObject obj) {
		setLayout(null);
		this.scorePanel=scorePanel;
		Node bgNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BG);
		bgImg = new ImageIcon(bgNode.getTextContent());
		Node soundNode=XMLReader.getNode(gamePanelNode, XMLReader.E_SOUND);
		sound=soundNode.getTextContent();
		// read <Fish><Obj>s from the XML parse tree, make Food objects, and add them to the FishBowl panel. 
		Node blockNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BLOCK);
		NodeList nodeList = blockNode.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			// found!!, <Obj> tag
			if(node.getNodeName().equals(XMLReader.E_OBJ)) {
				int x = Integer.parseInt(XMLReader.getAttr(node, "x"));
				int y = Integer.parseInt(XMLReader.getAttr(node, "y"));
				int w = Integer.parseInt(XMLReader.getAttr(node, "w"));
				int h = Integer.parseInt(XMLReader.getAttr(node, "h"));
				int life= Integer.parseInt(XMLReader.getAttr(node,"life"));
				String action=XMLReader.getAttr(node, "action");
				int delay= Integer.parseInt(XMLReader.getAttr(node, "delay"));
				
				ImageIcon icon = new ImageIcon(XMLReader.getAttr(node, "img"));
				if(life==1000)infiniteBlock+=1;
				Block block=new Block(x,y,w,h,life,action,delay,icon,obj);
				blocks.add(block); //º¤ÅÍ¿¡ Ãß°¡
				add(block);
			}
		}
		Node itemNode = XMLReader.getNode(gamePanelNode, XMLReader.E_ITEM);
		NodeList itemList=itemNode.getChildNodes();
		for(int i=0;i<itemList.getLength();i++) {
			Node node = itemList.item(i);
			if(node.getNodeType()!=Node.ELEMENT_NODE)
				continue;
			if(node.getNodeName().equals(XMLReader.E_OBJ)) {
				int x=Integer.parseInt(XMLReader.getAttr(node, "x"));
				int y=Integer.parseInt(XMLReader.getAttr(node, "y"));
				int w = Integer.parseInt(XMLReader.getAttr(node, "w"));
				int h = Integer.parseInt(XMLReader.getAttr(node, "h"));
				int life= Integer.parseInt(XMLReader.getAttr(node,"life"));
				String type=XMLReader.getAttr(node, "type");
				String action =XMLReader.getAttr(node,"action");
				
				int delay=Integer.parseInt(XMLReader.getAttr(node, "delay"));
				ImageIcon icon=new ImageIcon(XMLReader.getAttr(node, "img"));
				
				Item item=new Item(x,y,w,h,life,type,action,delay,icon,obj);
				items.add(item);
				add(item);
			}
		}
		Node playerNode = XMLReader.getNode(gamePanelNode, XMLReader.E_PLAYER);
		NodeList playerList=playerNode.getChildNodes();
		for(int i=0;i<playerList.getLength();i++) {
			Node player = playerList.item(i);
			if(player.getNodeType()!=Node.ELEMENT_NODE)
				continue;
			if(player.getNodeName().equals(XMLReader.E_WEAPON)) {
				int x=Integer.parseInt(XMLReader.getAttr(player, "x"));
				int y=Integer.parseInt(XMLReader.getAttr(player, "y"));
				int w = Integer.parseInt(XMLReader.getAttr(player, "w"));
				int h = Integer.parseInt(XMLReader.getAttr(player, "h"));
				int damage=Integer.parseInt(XMLReader.getAttr(player, "damage"));
				String type= XMLReader.getAttr(player,"type");
				
				
				ImageIcon icon = new ImageIcon(XMLReader.getAttr(player, "img"));
				Weapon weapon = new Weapon(x,y,w,h,damage,type,icon,this,scorePanel,obj);
				weapons.add(weapon);
				add(weapon);
				
				
				
			}
		}
		
	}
	public boolean gameOver() {
		if((blocks.size()-infiniteBlock)==0&&items.size()==0) {
			return true;
		}
		return false;
	}
	public String getSoundPath() {
		return sound;
	}
	public boolean isGaming() {
		return gameStatus;
	}
	public void changeStatus(boolean status) {
		gameStatus=status;
	}
	public Vector<Block> getBlocks(){
		return blocks;
	}
	public Vector<Weapon> getWeapons(){
		return weapons;
	}
	public Vector<Item> getItems(){
		return items;
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
}



