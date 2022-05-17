import java.awt.*;
import javax.swing.*;

import org.w3c.dom.Node;
public class ChoicePanel extends JPanel{
	private JTabbedPane pane=new JTabbedPane();
	private ToolPanel toolPanel;
	private BlockPanel blockPanel;
	private ItemPanel itemPanel;
	private WeaponPanel weaponPanel;
	private BackgroundPanel backgroundPanel;
	private SoundPanel soundPanel;
	public ChoicePanel(ToolPanel toolPanel) {
		this.toolPanel=toolPanel;
		blockPanel=new BlockPanel(toolPanel);
		itemPanel=new ItemPanel(toolPanel);
		weaponPanel=new WeaponPanel(toolPanel);
		backgroundPanel=new BackgroundPanel(toolPanel);
		soundPanel=new SoundPanel();
		
		setLayout(new BorderLayout());
		pane.addTab("Block",blockPanel);
		pane.addTab("Item", itemPanel);
		pane.addTab("Weapon",weaponPanel);
		pane.addTab("Background",backgroundPanel);
		pane.addTab("Sound",soundPanel);
		add(pane,BorderLayout.CENTER);
	}
	public ChoicePanel(ToolPanel toolPanel,Node gamePanelNode) {
		this.toolPanel=toolPanel;
		Node soundNode=XMLReader.getNode(gamePanelNode, XMLReader.E_SOUND);
		String sound=soundNode.getTextContent();
		Node bgNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BG);
		String background=bgNode.getTextContent();
		blockPanel=new BlockPanel(toolPanel);
		itemPanel=new ItemPanel(toolPanel);
		weaponPanel=new WeaponPanel(toolPanel);
		backgroundPanel=new BackgroundPanel(toolPanel);
		soundPanel=new SoundPanel();
		soundPanel.setSound(sound);
		backgroundPanel.setBackground(background);
		setLayout(new BorderLayout());
		pane.addTab("Block",blockPanel);
		pane.addTab("Item", itemPanel);
		pane.addTab("Weapon",weaponPanel);
		pane.addTab("Background",backgroundPanel);
		pane.addTab("Sound",soundPanel);
		add(pane,BorderLayout.CENTER);
	}
	public SoundPanel getSoundPanel(){
		return soundPanel;
	}
	public boolean soundCheck() {
		return soundPanel.soundSelected();
	}
	public boolean backgroundCheck() {
		return backgroundPanel.backgroundSelected();
	}
	
}
