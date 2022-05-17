import java.util.Vector;

public class SynchronizedObject {
	//private boolean gameStatus=true;
	private GamePanel gamePanel=null;
	
	public void getGamePanel(GamePanel gamePanel) {
		this.gamePanel=gamePanel;
	}
	public synchronized void pauseThread() {
		if(!gamePanel.isGaming()) {
			try {
				wait();
				
			}catch(Exception e) {return;}
		}
		notifyAll();
		//gameStatus=true;
	}
}
