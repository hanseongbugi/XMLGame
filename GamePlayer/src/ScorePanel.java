import java.awt.*;
import javax.swing.*;
public class ScorePanel extends JPanel{
	private int score=0;
	private int value=10;
	private JLabel scoreLabel=new JLabel(Integer.toString(score));
	private JLabel la=new JLabel("Á¡¼ö: ");
	public ScorePanel() {
		this.setBackground(Color.CYAN);
		setLayout(new BorderLayout());
		la.setFont(new Font("°íµñ",Font.ITALIC,20));
		scoreLabel.setFont(new Font("°íµñ",Font.ITALIC,30));
		add(la,BorderLayout.WEST);
		add(scoreLabel,BorderLayout.CENTER);
	}
	public void setScore() {
		score+=value;
		scoreLabel.setText(Integer.toString(score));
	}
	public void increaseScoreValue() {
		value+=10;
	}
	public void totalScore() {
		JOptionPane.showMessageDialog(null,"ÃÑ Á¡¼ö: "+score,"°ÔÀÓÁ¾·á",JOptionPane.INFORMATION_MESSAGE);
		
	}
}
