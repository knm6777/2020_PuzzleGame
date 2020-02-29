import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PuzzleFrame extends JFrame{
	private ImageIcon puzzleImg = new ImageIcon("gonyang2.jpg");
	private GamePanel gamePanel = new GamePanel(puzzleImg);
	private AssistPanel assistPanel = new AssistPanel(puzzleImg);

	public PuzzleFrame() {
		super("puzzle game");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		splitPane();
		Container c = getContentPane();
		c.add(makeToolBar(), BorderLayout.NORTH);
		setVisible(true);
	}
	public void splitPane() {
		JSplitPane pane1 = new JSplitPane();
		Container c = getContentPane();
		c.add(pane1, BorderLayout.CENTER);
		
		pane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		pane1.setDividerLocation(800);
		pane1.setEnabled(false);
		pane1.setLeftComponent(gamePanel);
		pane1.setRightComponent(assistPanel);
	}
	public JToolBar makeToolBar() {
		JToolBar tb = new JToolBar();
		JButton start = new JButton("ÁßÁö");
		tb.add(start);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		return tb;
	}
}
