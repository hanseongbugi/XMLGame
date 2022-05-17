import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class ToolPanel extends JPanel{
	private ImageIcon icon=null;
	private Image img=null;
	private String imagePath;
	private Vector<Block> blockV=new Vector(30);
	private Vector<Item> itemV=new Vector(30);
	private Vector<Weapon> weaponV=new Vector(30);
	
	
	public ToolPanel(int w,int h) {
		setLayout(null);
	}
	public ToolPanel(Node gamePanelNode) {
		setLayout(null);
		Node bgNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BG);
		imagePath=bgNode.getTextContent();
		icon = new ImageIcon(bgNode.getTextContent());
		img=icon.getImage();
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
				
				Block block=new Block(icon,XMLReader.getAttr(node, "img"));
				block.setInfor(x,y,w,h);
				block.setAction(action);
				block.setDelay(delay);
				block.setLife(life);
				block.addMouseListener(new InformationAction());
				blockV.add(block);
				this.add(block);
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
				Item item=new Item(icon,XMLReader.getAttr(node, "img"));
				item.setInfor(x, y, w, h);
				item.setAction(action);
				item.setType(type);
				item.setLife(life);
				item.setDelay(delay);
				item.addMouseListener(new InformationAction());
				itemV.add(item);
				this.add(item);
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
				String type= XMLReader.getAttr(player,"type");
				
				
				ImageIcon icon = new ImageIcon(XMLReader.getAttr(player, "img"));
				Weapon weapon = new Weapon(icon,XMLReader.getAttr(player, "img"));
				weapon.setInfor(x,y,w,h);
				weapon.setType(type);
				weapon.addMouseListener(new InformationAction());
				weaponV.add(weapon);
				this.add(weapon);	
			}
		}	
	}
	public void setImage(ImageIcon icon,String imageFath) {
		this.icon=icon;
		this.imagePath=imageFath;
		img=this.icon.getImage();
		//setLayout(null);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(img!=null)
			g.drawImage(img,0,0,getWidth(),getHeight(),null);
		
	}
	public String getImagePath() {
		return imagePath;
	}
	public void addBlockLocation(Block block) {
		int x=block.getX();
		int y=block.getY();
		if(x<0||x>getWidth()) {
			this.remove(block);
			return;
		}
		if(y<0||y>(getHeight()-block.getHeight()+30)) {
			this.remove(block);
			return;
		}
		blockV.add(block);
		block.addMouseListener(new InformationAction());
	}
	public void addItemLocation(Item item) {
		int x=item.getX();
		int y=item.getY();
		if(x<0||x>getWidth()) {
			this.remove(item);
			//System.out.println("remove");
			return;
		}
		if(y<0||y>(getHeight()-item.getHeight()+30)) {
			this.remove(item);
			//System.out.println("remove");
			return;
		}
		itemV.add(item);
		item.addMouseListener(new InformationAction());
	}
	public void addWeaponLocation(Weapon weapon) {
		int x=weapon.getX();
		int y=weapon.getY();
		if(x<0||x>getWidth()) {
			this.remove(weapon);
			return;
		}
		if(y<0||y>(getHeight()-weapon.getHeight()+30)) {
			this.remove(weapon);
			return;
		}
		weaponV.add(weapon);
		weapon.addMouseListener(new InformationAction());
	}
	public void removeWeapon(Weapon weapon) {
		weaponV.remove(weapon);
	}
	public void removeItem(Item item) {
		itemV.remove(item);
	}
	public void removeBlock(Block block) {
		blockV.remove(block);
	}
	public Vector<Block> getBlocks(){
		return blockV;
	}
	public Vector<Item> getItems(){
		return itemV;
	}
	public Vector<Weapon> getWeapons(){
		return weaponV;
	}
	public void removeAllObject() {
		blockV.removeAllElements();
		weaponV.removeAllElements();
		itemV.removeAllElements();
	}
	
	class InformationAction extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON3) {
				JLabel label=(JLabel)e.getSource();
				InformationPanel infor=new InformationPanel(label,ToolPanel.this);
				JOptionPane.showMessageDialog(ToolPanel.this,infor,"Information",JOptionPane.INFORMATION_MESSAGE);
				infor.changePanelInformation();
			}
		}
	}
}
