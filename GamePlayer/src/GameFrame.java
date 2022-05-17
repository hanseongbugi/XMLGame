import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import java.awt.*;
import javax.swing.filechooser.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GameFrame extends JFrame{
	private JFileChooser fileChooser=new JFileChooser("D:\\동계 학습 프로젝트\\XMLFile");
	private String filePath=null;
	private Container c=null;
	private GamePanel gamePanel=null;
	private ScorePanel scorePanel=null;
	private SoundPanel soundPanel=null;
	private SynchronizedObject obj=null;
	public GameFrame() {
		super("블록 깨기 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fileChooser.setFileFilter(new FileNameExtensionFilter("xml","xml"));
		makeManuBar();
		setSize(300,300);
		c=getContentPane();
		
		setResizable(false);
		setVisible(true);
	}
	private void makeManuBar() {
		JMenuBar m=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenu game=new JMenu("Game");
		JMenuItem open=new JMenuItem("open");
		JMenuItem start=new JMenuItem("start");
		JMenuItem pause=new JMenuItem("pause");
		JMenuItem exit=new JMenuItem("exit");
		file.add(open);
		game.add(start);
		game.add(pause);
		game.add(exit);
		m.add(file);
		m.add(game);
		open.addActionListener(new ManuAction());
		start.addActionListener(new ManuAction());
		pause.addActionListener(new ManuAction());
		exit.addActionListener(new ManuAction());
		setJMenuBar(m);
	}
	private void makeGame(String filePath) {
		XMLReader xml=new XMLReader(filePath);
		Node gameNode=xml.getBlockGameElement();
		Node sizeNode= XMLReader.getNode(gameNode, XMLReader.E_SIZE);
		String w=XMLReader.getAttr(sizeNode, "w");
		String h=XMLReader.getAttr(sizeNode, "h");
		c.removeAll();
		GameFrame.this.setSize(Integer.parseInt(w)+200,Integer.parseInt(h)+60);
		scorePanel=new ScorePanel();
		obj=new SynchronizedObject();
		gamePanel=new GamePanel(xml.getGamePanelElement(),scorePanel,obj);
		soundPanel=new SoundPanel(gamePanel);
		obj.getGamePanel(gamePanel);
		
		
		JSplitPane jp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		js.setTopComponent(scorePanel);
		js.setBottomComponent(soundPanel);
		js.setDividerLocation(GameFrame.this.getHeight()/2);
		js.setEnabled(false);
		
		jp.setLeftComponent(gamePanel);
		jp.setRightComponent(js);
		jp.setDividerLocation(GameFrame.this.getWidth()-200);
		jp.setEnabled(false);
		c.add(jp);
		c.revalidate();
		c.repaint();
	}
	class ManuAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem m=(JMenuItem)e.getSource();
			String type=m.getText();
			if(type.equals("open")) {
				int ret=fileChooser.showOpenDialog(null);
				if(ret!=JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
					return;
				}
				filePath=fileChooser.getSelectedFile().getPath();
				if(soundPanel!=null)soundPanel.clearSound();
				makeGame(filePath);
			}
			else if(type.equals("start")) {
				//스레드 시작
				if(gamePanel==null)return;
				if(!gamePanel.isGaming()) {
					gamePanel.changeStatus(true);
					obj.pauseThread();
					return;
				}
				if(gamePanel.isGaming()) {
					soundPanel.clearSound();
					makeGame(filePath);
				}
				
				Vector<Block> vB=gamePanel.getBlocks();
				if(vB!=null) {
				for(int i=0;i<vB.size();i++) {
					Thread th=vB.get(i).getBlockThread();
					th.start();
				}
				}
				Vector<Item> vI=gamePanel.getItems();
				if(vI!=null) {
					for(int i=0;i<vI.size();i++) {
						Thread th=vI.get(i).getItemThread();
						th.start();
					}
				}
				Vector<Weapon> vW=gamePanel.getWeapons();
				if(vW!=null) {
				for(int i=0;i<vW.size();i++) {
						gamePanel.addMouseListener(new BoundsAction(vW.get(i)));
				}
				}
			}
			else if(type.equals("pause")) {
				//wait-notify를 사용하여 일시정지 구현
				//obj.pauseThread();
				gamePanel.changeStatus(false);
			}
			else if(type.equals("exit")) {
				System.exit(1); //종료
			}
			else {
				return;
			}
		}
	}
}



