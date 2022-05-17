import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BlockPanel extends JPanel{
	private ImageIcon blockIcon=new ImageIcon("block/block1.png");
	
	private Block block;
	private Block targetBlock=null;
	//private boolean isSelected=false;
	private ToolPanel toolPanel;
	public BlockPanel(ToolPanel toolPanel) {
		this.toolPanel=toolPanel;
		setLayout(null);
		
		block=new Block(blockIcon,"D:\\동계 학습 프로젝트\\샘플 코드\\AuthoringTool\\block\\block1.png");
		block.setSize(50,50);
		block.setLocation(70,0);
		add(block);
		block.addMouseListener(new BlockAction());
		block.addMouseMotionListener(new BlockAction());

	}
	
	class BlockAction implements MouseListener,MouseMotionListener{
		@Override
		public void mousePressed(MouseEvent e) {
			Block block=(Block)e.getSource();
			targetBlock =new Block(blockIcon,"D:\\동계 학습 프로젝트\\샘플 코드\\AuthoringTool\\block\\block1.png");
	
			targetBlock.setLocation(block.getX(),block.getY());
			targetBlock.setSize(block.getWidth(),block.getHeight());
			toolPanel.add(targetBlock);
			toolPanel.revalidate();
			toolPanel.repaint();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			toolPanel.addBlockLocation(targetBlock);
			targetBlock=null;
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override 
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(targetBlock==null)return;
			
			int x=e.getX();
			int y=e.getY();
			targetBlock.setLocation(e.getX(),e.getY());
			
			toolPanel.revalidate();
			toolPanel.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}
	}
}
