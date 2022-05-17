import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Node;

import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class ToolFrame extends JFrame{
	private Container c;
	private JFileChooser fileChooser=new JFileChooser("D:\\동계 학습 프로젝트\\XMLFile");
	private String filePath=null;
	private ToolPanel toolPanel=null;
	private ChoicePanel choicePanel;
	private SizePanel sizePanel=new SizePanel();;
	private String fileName=null;
	public ToolFrame() {
		super("저작도구");
		fileChooser.setFileFilter(new FileNameExtensionFilter("xml","xml"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,900);
		setResizable(false);
		c=getContentPane();
		c.setLayout(null);
		//c.add(new ChoicePanel());
		makeMenu();
		setVisible(true);
	}
	private void makeMenu() {
		JToolBar toolBar=new JToolBar();
		JButton newFile=new JButton("New");
		JButton open=new JButton(new ImageIcon("open.jpg"));
		JButton deleteAll=new JButton(new ImageIcon("delete.png"));
		JButton save=new JButton(new ImageIcon("save.jpg"));
		deleteAll.setToolTipText("모든 오브젝트 삭제");
		newFile.setToolTipText("새로운 파일 생성");
		save.setToolTipText("파일을 저장합니다.");
		
		toolBar.add(newFile);
		
		toolBar.addSeparator();
		toolBar.add(open);
		toolBar.add(save);
		
		toolBar.addSeparator();
		toolBar.add(deleteAll);
		toolBar.setFloatable(false);
		toolBar.setSize(700,40);
		toolBar.setLocation(0,0);
		
		newFile.addActionListener(new FileMakeAction());
		open.addActionListener(new OpenAction());
		save.addActionListener(new FileAction());
		deleteAll.addActionListener(new DeleteAction());
		c.add(toolBar);
	}
	class FileAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(toolPanel==null)return;
			if(toolPanel.getBlocks().size()==0||toolPanel.getWeapons().size()==0||
					toolPanel.getItems().size()==0||!choicePanel.soundCheck()||!choicePanel.backgroundCheck()) {
				JOptionPane.showMessageDialog(ToolFrame.this,"선택하지 않은 항목이 있습니다.");
				return;
			}
			saveFile();
		}
		private void saveFile() {
			String path;
			SavePanel savePanel=new SavePanel(fileName);
			while(true) {
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),savePanel,"FileSaveMessage",JOptionPane.QUESTION_MESSAGE);
				savePanel.setFilePath();
				path=savePanel.getFilePath();
				if(path!=null) {
					path=path.trim();
					if(!path.equals(""))break;
				}
			}
			if(!path.contains(".xml"))path+=".xml";
			try {
				FileWriter fout=new FileWriter("D:\\동계 학습 프로젝트\\XMLFile\\"+path);
				fout.write("<?xml version=\"1.0\" encoding=\"euc-kr\" ?> \r\n"
						+ "<BlockGame>\r\n"
						+ "<Screen>\r\n"
						+ "	<Size w=\""+sizePanel.getW()+"\" h=\""+sizePanel.getH()+"\"></Size>\r\n"
						+ "</Screen>\r\n"); 
				fout.write("<GamePanel>\r\n"+"<Bg>"+toolPanel.getImagePath()+"</Bg>\r\n"+
						"<Sound>"+choicePanel.getSoundPanel().getSound()+"</Sound>\r\n");
				fout.write("<Block>\r\n");
				Vector<Block> bv=toolPanel.getBlocks();
				for(int i=0;i<bv.size();i++) {
					Block b=bv.get(i);
					fout.write("<Obj x=\""+b.getX()+"\" y=\""+b.getY()+"\" w=\""
				+b.getWidth()+"\" h=\""+b.getHeight()+"\" life=\""+b.getLife()+"\" action=\""+b.getAction()+
				"\" delay=\""+b.getDelay()+"\" img=\""+"block\\"+b.getImagePath()+"\"></Obj>\r\n");
				}
				fout.write("</Block>\r\n");
				fout.write("<Item>\r\n");
				Vector<Item> iv=toolPanel.getItems();
				for(int i=0;i<iv.size();i++) {
					Item it=iv.get(i);
					fout.write("<Obj x=\""+it.getX()+"\" y=\""+it.getY()+"\" w=\""
				+it.getWidth()+"\" h=\""+it.getHeight()+"\" life=\""+it.getLife()+"\" type=\""+it.getType()+"\" action=\""+it.getAction()+
				"\" delay=\""+it.getDelay()+"\" img=\""+it.getImagePath()+"\"></Obj>\r\n");
				}
				fout.write("</Item>\r\n");
				fout.write("<Player>\r\n");
				Vector<Weapon> wv=toolPanel.getWeapons();
				for(int i=0;i<wv.size();i++) {
					Weapon w=wv.get(i);
					fout.write("<Weapon x=\""+w.getX()+"\" y=\""+w.getY()+"\" w=\""
				+w.getWidth()+"\" h=\""+w.getHeight()+"\" damage=\"1\" type=\""+w.getType()+"\" img=\""+w.getImagePath()+"\"></Weapon>\r\n");
				}
				fout.write("</Player>\r\n"+"</GamePanel>\r\n"+"</BlockGame>\r\n");
				fout.close();
				JOptionPane.showMessageDialog(ToolFrame.this,"파일을 저장하였습니다.");
			}catch(IOException e) {
				return;
			}
		}
	}
	class OpenAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int ret=fileChooser.showOpenDialog(null);
			if(ret!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath=fileChooser.getSelectedFile().getPath();
			String[] path=filePath.split("\\\\");
			for(int i=0;i<path.length;i++) {
				if(path[i].contains(".xml")) {
					fileName=path[i];
				}
			}
			XMLReader xml=new XMLReader(filePath);
			Node gameNode=xml.getBlockGameElement();
			Node sizeNode= XMLReader.getNode(gameNode, XMLReader.E_SIZE);
			String w=XMLReader.getAttr(sizeNode, "w");
			String h=XMLReader.getAttr(sizeNode, "h");
			c.removeAll();
			makeMenu();
			
			sizePanel.setSizeInfor(Integer.parseInt(w),Integer.parseInt(h));
			toolPanel=new ToolPanel(xml.getGamePanelElement());
			toolPanel.setSize(Integer.parseInt(w),Integer.parseInt(h));
			toolPanel.setLocation(0,120+40);
			choicePanel=new ChoicePanel(toolPanel,xml.getGamePanelElement());
			choicePanel.setSize(Integer.parseInt(w),120);
			choicePanel.setLocation(0,40);
			
			c.add(choicePanel);
			c.add(toolPanel);
			c.revalidate();
			c.repaint();
			
		}
	}
	class DeleteAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			toolPanel.removeAllObject();
			toolPanel.removeAll();
			toolPanel.repaint();
		}
	}
	class FileMakeAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			while(true) {
				JOptionPane.showMessageDialog(ToolFrame.this,sizePanel,
							"최대 크기(W= "+ToolFrame.this.getWidth()+", H= "+(ToolFrame.this.getHeight()-200)+")",
							JOptionPane.PLAIN_MESSAGE);
				sizePanel.setPanelSize();
				if(sizePanel.getW()==0||sizePanel.getH()==0)continue;
				if(sizePanel.getW()<=ToolFrame.this.getWidth()&&sizePanel.getH()<=ToolFrame.this.getHeight())break;
			}
			c.removeAll();
			makeMenu();
			
			toolPanel=new ToolPanel(sizePanel.getW(),sizePanel.getH());
			toolPanel.setSize(sizePanel.getW(),sizePanel.getH());
			toolPanel.setLocation(0,120+40);
			choicePanel=new ChoicePanel(toolPanel);
			choicePanel.setSize(sizePanel.getW(),120);
			choicePanel.setLocation(0,40);
			c.add(choicePanel);
			
			c.add(toolPanel);
			c.revalidate();
			c.repaint();			
		}
	}
}
