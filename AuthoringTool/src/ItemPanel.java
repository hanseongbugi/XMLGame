import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemPanel extends JPanel{
	private Item targetItem=null;
	private ToolPanel toolPanel;
	private ImageIcon itemIcon=new ImageIcon("item\\star.png");
	
	private Item item;
	
	
	public ItemPanel(ToolPanel toolPanel) {
		setLayout(null);
		this.toolPanel=toolPanel;
		
		item=new Item(itemIcon,"D:\\동계 학습 프로젝트\\샘플 코드\\AuthoringTool\\item\\star.png");
		item.setSize(40,40);
		item.setLocation(70,0);
			
		item.addMouseListener(new ItemAction());
		item.addMouseMotionListener(new ItemAction());
		add(item);
		
	}
	class ItemAction implements MouseListener,MouseMotionListener{
		@Override
		public void mousePressed(MouseEvent e) {
			Item item=(Item)e.getSource();
			targetItem= new Item(itemIcon,"item\\star.png");
			targetItem.setSize(item.getWidth(),item.getHeight());
			targetItem.setLocation(item.getX(),item.getY());
			toolPanel.add(targetItem);
			toolPanel.revalidate();
			toolPanel.repaint();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			toolPanel.addItemLocation(targetItem);
			targetItem=null;
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override 
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(targetItem==null)return;
			int x=e.getX();
			int y=e.getY();
			targetItem.setLocation(e.getX(),e.getY());
			//System.out.println("move");
			toolPanel.revalidate();
			toolPanel.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}
	}
}
