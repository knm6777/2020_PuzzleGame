import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AssistPanel extends JPanel{
	private JLabel picture;
	private int time = 0;
	private JLabel timer = new JLabel(Integer.toString(time));
	private TimeThread th = new TimeThread();
	public AssistPanel(ImageIcon puzzleImg) {
		setLayout(null);
		timer.setLocation(100, 1000);
		timer.setSize(100, 100);
		add(timer);
		
		setBackground(Color.pink);
		
		Image b = puzzleImg.getImage();
		Image bb = b.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
		puzzleImg = new ImageIcon(bb);
		picture = new JLabel(puzzleImg);
		picture.setLocation(40, 40);
		picture.setSize(300,300);
		add(picture);

		th.start();
	}
	class TimeThread extends Thread{
		public void run() {
			while(true) {
				time++;
				try {
					Thread.sleep(1000);
					timer = new JLabel(Integer.toString(time));
					add(timer);
				}catch(InterruptedException e) {return;}
			}
		}
	}
}
