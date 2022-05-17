import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
public class InformationPanel extends JPanel{
	private JTextField xField=new JTextField(10);
	private JTextField yField=new JTextField(10);
	private JTextField wField=new JTextField(10);
	private JTextField hField=new JTextField(10);
	private JTextField lifeField=new JTextField(10);
	private JTextField delayField=new JTextField(10);
	private JLabel label;
	private String [] types= {"null","size","fast"};
	private String[] itemTypes= {"null","score","damage"};
	private String [] actions= {"null","hidden","shake"};
	private JComboBox<String> typeCombo=new JComboBox(types);
	private JComboBox<String> actionCombo=new JComboBox(actions);
	private JComboBox<String> itemTypeCombo=new JComboBox(itemTypes);
	
	private ToolPanel toolPanel;
	private JFileChooser fileChooser=new JFileChooser();
	private JButton delete=new JButton("Delete");
	private JButton image=new JButton("Image");
	private int col=5;	
	public InformationPanel(JLabel label,ToolPanel toolPanel) {
		//setSize(200,200);
		SettingAction settingAction=new SettingAction();
		ComboAction cAction=new ComboAction();
		fileChooser.setFileFilter(new FileNameExtensionFilter("JPG & PNG & GIF 확장자","jpg","png","gif"));
		this.label=label;
		this.toolPanel=toolPanel;
		add(new JLabel("XPos   : "));
		xField.setText(Integer.toString(label.getX()));
		add(xField);
		add(new JLabel("YPos   : "));
		yField.setText(Integer.toString(label.getY()));
		add(yField);
		add(new JLabel("Width  : "));
		wField.setText(Integer.toString(label.getWidth()));
		add(wField);
		add(new JLabel("Height : "));
		hField.setText(Integer.toString(label.getHeight()));
		add(hField);
		
		if(label instanceof Weapon) {
			Weapon weapon=(Weapon)label;
			add(new JLabel("Type  : "));
			typeCombo.setSelectedItem(weapon.getType());
			add(typeCombo);
			typeCombo.addActionListener(cAction);
			col+=1;
		}
		else if(label instanceof Block) {
			Block block=(Block)label;
			add(new JLabel("Life  : "));
			lifeField.setText(Integer.toString(block.getLife()));
			add(lifeField);
			add(new JLabel("Action  : "));
			actionCombo.setSelectedItem(block.getAction());
			add(actionCombo);
			add(new JLabel("Delay  : "));
			delayField.setText(Integer.toString(block.getDelay()));
			add(delayField);
			actionCombo.addActionListener(cAction);
			lifeField.addActionListener(settingAction);
			delayField.addActionListener(settingAction);
			col+=3;
		}
		else if(label instanceof Item) {
			Item item=(Item)label;
			add(new JLabel("Life  : "));
			lifeField.setText(Integer.toString(item.getLife()));
			add(lifeField);
			add(new JLabel("Type  : "));
			itemTypeCombo.setSelectedItem(item.getType());
			add(itemTypeCombo);
			add(new JLabel("Action  : "));
			actionCombo.setSelectedItem(item.getAction());
			add(actionCombo);
			add(new JLabel("Delay  : "));
			delayField.setText(Integer.toString(item.getDelay()));
			add(delayField);
			lifeField.addActionListener(settingAction);
			itemTypeCombo.addActionListener(cAction);
			actionCombo.addActionListener(cAction);
			delayField.addActionListener(settingAction);
			col+=4;
		}
		if(!(label instanceof Weapon)) {
			add(new JLabel("이미지 : "));
			add(image);
			col+=1;
		}
		
		add(new JLabel("삭제 "));
		add(delete);
		
		setLayout(new GridLayout(col,2,2,10));
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container c=label.getParent();
				if(label instanceof Weapon)toolPanel.removeWeapon((Weapon)label);
				else if(label instanceof Block)toolPanel.removeBlock((Block)label);
				else toolPanel.removeItem((Item)label);
				c.remove(label);
				c.repaint();
			}
		});
		image.addActionListener(new ImageAction());
		xField.addActionListener(settingAction);
		yField.addActionListener(settingAction);
		wField.addActionListener(settingAction);
		hField.addActionListener(settingAction);
	}
	class ImageAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(label instanceof Block)
				fileChooser.setCurrentDirectory(new File("D:\\동계 학습 프로젝트\\샘플 코드\\AuthoringTool\\block"));
			else if(label instanceof Item)
				fileChooser.setCurrentDirectory(new File("D:\\동계 학습 프로젝트\\샘플 코드\\AuthoringTool\\item"));
			
			int ret=fileChooser.showOpenDialog(null);
			if(ret!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath=fileChooser.getSelectedFile().getPath();
			ImageIcon icon=new ImageIcon(filePath);
			if(label instanceof Block) {
				Block block=(Block)label;
				block.changeImage(icon,filePath);
			}
			else if(label instanceof Item) {
				Item item=(Item)label;
				item.changeImage(icon,filePath);
				}
		
			}
	}
	class ComboAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> jb=(JComboBox<String>)e.getSource();
			if(jb==actionCombo) {
				if(label instanceof Block) {
					Block block=(Block)label;
					block.setAction(actions[jb.getSelectedIndex()]);
				}
				else if(label instanceof Item) {
					Item item=(Item)label;
					item.setAction(actions[jb.getSelectedIndex()]);
				}
			}
			else if(jb==typeCombo){
				Weapon weapon=(Weapon)label;
				weapon.setType(types[jb.getSelectedIndex()]);
			}
			else if(jb==itemTypeCombo) {
				Item item=(Item)label;
				item.setType(itemTypes[jb.getSelectedIndex()]);
			}
		}
		
	}
	class SettingAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField jt=(JTextField)e.getSource();
			if(jt==xField) {
				label.setLocation(Integer.parseInt(jt.getText()),label.getY());
			}
			else if(jt==yField) {
				label.setLocation(label.getX(),Integer.parseInt(jt.getText()));
			}
			else if(jt==wField) {
				label.setSize(Integer.parseInt(jt.getText()),label.getHeight());
			}	
			else if(jt==hField){
				label.setSize(label.getWidth(),Integer.parseInt(jt.getText()));
			}	
			else if(jt==lifeField){
				if(label instanceof Block) {
					Block block=(Block)label;
					block.setLife(Integer.parseInt(jt.getText()));
				}
				else if(label instanceof Item) {
					Item item=(Item)label;
					item.setLife(Integer.parseInt(jt.getText()));
				}
			}
			else if(jt==delayField){
				if(label instanceof Block) {
					Block block=(Block)label;
					block.setDelay(Integer.parseInt(jt.getText()));
				}
				else if(label instanceof Item) {
					Item item=(Item)label;
					item.setDelay(Integer.parseInt(jt.getText()));
				}
			}
		
		}
	}
	public void changePanelInformation() {
		label.setLocation(Integer.parseInt(xField.getText()),Integer.parseInt(yField.getText()));
		label.setSize(Integer.parseInt(wField.getText()),Integer.parseInt(hField.getText()));
		if(label instanceof Weapon) {
			Weapon weapon=(Weapon)label;
			weapon.setType(types[typeCombo.getSelectedIndex()]);
		}
		else if(label instanceof Block) {
			Block block=(Block)label;
			block.setLife(Integer.parseInt(lifeField.getText()));
			block.setAction(actions[actionCombo.getSelectedIndex()]);
			block.setDelay(Integer.parseInt(delayField.getText()));
		}
		else if(label instanceof Item) {
			Item item=(Item)label;
			item.setLife(Integer.parseInt(lifeField.getText()));
			item.setAction(actions[actionCombo.getSelectedIndex()]);
			item.setDelay(Integer.parseInt(delayField.getText()));
			item.setType(itemTypes[itemTypeCombo.getSelectedIndex()]);
		}
	}
}
