import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;


public class WeaponPanel extends JPanel{
	private ImageIcon icon=new ImageIcon("boundsball.png");
	private ToolPanel toolPanel;
	private Weapon targetWeapon=null;
	public WeaponPanel(ToolPanel toolPanel) {
		this.toolPanel=toolPanel;
		setLayout(null);
	
		Weapon weapon=new Weapon(icon,"boundsball.png");
		weapon.setSize(90,90);
		weapon.setLocation(0,0);
		add(weapon);
		weapon.addMouseListener(new WeaponAction());
		weapon.addMouseMotionListener(new WeaponAction());

	}
	class WeaponAction implements MouseListener,MouseMotionListener{
		@Override
		public void mousePressed(MouseEvent e) {
			Weapon weapon=(Weapon)e.getSource();
			
			targetWeapon= new Weapon(icon,"boundsball.png");
			targetWeapon.setSize(weapon.getWidth(),weapon.getHeight());
			targetWeapon.setLocation(weapon.getX(),weapon.getY());
			toolPanel.add(targetWeapon);
			toolPanel.revalidate();
			toolPanel.repaint();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			toolPanel.addWeaponLocation(targetWeapon);
			targetWeapon=null;
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override 
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(targetWeapon==null)return;
			int x=e.getX();
			int y=e.getY();
			targetWeapon.setLocation(e.getX(),e.getY());
			//System.out.println("move");
			toolPanel.revalidate();
			toolPanel.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}
	}
}
