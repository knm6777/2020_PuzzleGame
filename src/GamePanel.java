import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;
class PuzzleVec{
	private int num;	//���� ���� ��ȣ
	private BufferedImage img;
	public Point cur;
	private ImageIcon a;
	public PuzzleVec(int num, BufferedImage img, Point cur) {
		this.num = num;
		this.img = img;
		this.cur = cur;
	}
	public int getNum() {
		return this.num;
	}
	public ImageIcon getIcon() {
		a = new ImageIcon(this.img);
		return a;
	}
	public Point getPoint() {
		return this.cur;
	}
}
class JLabelN extends JLabel{
	public int num;//���������ȣ
	public int getNum() {
		return num;
	}
}
public class GamePanel extends JPanel{
	private JLabel pan;
	private JLabelN ex[] = new JLabelN[16];
	private ImageIcon puzIcon;	//�����Ŀ�  tmp ����
	private Image puzImg;
	private BufferedImage a;
	private BufferedImage crop;
	private Vector<PuzzleVec> v = new Vector<>();
	private Vector<PuzzleVec> vv;

	private PuzzleVec tmp;
	private Point temp;
	private int wn = 4; 
	private int hn = 4;
	private int w;
	private int h;
 	public GamePanel(ImageIcon puzzleImg) {
		setLayout(null);
		setBackground(Color.white);
		puzIcon = puzzleImg;	//Icon
		puzImg = puzIcon.getImage();
		puzImg = puzImg.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH);
		
		puzIcon = new ImageIcon(puzImg); //Image�� ImageIcon ����
		a = new BufferedImage(puzImg.getWidth(null), puzImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		w = a.getWidth()/wn;
		h = a.getHeight()/hn;
		
		cropImage(a, wn, hn);
		for(int k=0; k<v.size(); k++) {
			System.out.print(v.get(k).getNum()+" ");
		}
		System.out.println();
		//vv=v;//���� ���纻
		Collections.shuffle(v);
		
		for(int k=0; k<v.size(); k++) {
			System.out.print(v.get(k).getNum()+" ");
		}
		
		System.out.println();
		//���� ���� ����
		for(int i=0; i<v.size(); i++) {
			tmp = v.get(i);
			ex[i] = new JLabelN();
			//ex[i].setIcon(tmp.getIcon());
			ex[i].setFont(new Font("gothic", Font.ITALIC, 20));
			ex[i].setLocation(vv.get(i).getPoint());
			ex[i].setSize(w, h);
			ex[i].num = tmp.getNum();
			ex[i].setText(Integer.toString(ex[i].num));

			add(ex[i]);
			
			//Collections.swap(v,0,4);
			ex[i].addMouseListener(new MyMouseListener());
			ex[i].addMouseMotionListener(new MyMouseMotionListener());
			
		}
		
		//�迭 �����ϰ� ����
        setVisible(true); 
	}
	
	public void cropImage(BufferedImage a, int wn, int hn) {
	    Graphics2D bGr = a.createGraphics();
	    bGr.drawImage(puzImg, 0, 0, null);
	    bGr.dispose();
	    
		int n = 0;	//���� �ѹ�
		for(int i=0; i<wn; i++) {
			for(int j=0; j<hn; j++) {
				crop = a.getSubimage(i*w, j*h, w, h);
				puzIcon = new ImageIcon(crop);
				temp = new Point(100+j*w, 70+i*h);	
				tmp = new PuzzleVec(n, crop, temp);
				n++;
				v.add(tmp);	//���Ϳ� ��� �߰�
			}
		}
		vv = new Vector<>(v);
	}	
	class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			JLabelN lab = (JLabelN)e.getSource();
			lab.setLocation(lab.getX()-lab.getWidth()/2+e.getX(), lab.getY()-lab.getHeight()/2+e.getY());
			
			revalidate();
			repaint();
			lab.repaint();
		}
		public void mouseReleased(MouseEvent e) {
			JLabelN lab = (JLabelN)e.getSource();
			PuzzleVec tt;
			//System.out.println(e.getX()+","+e.getY());
			int n = lab.getNum();//���� ���콺�� ����ִ� ���� ������ȣ

			int j=0;
			while(j<v.size()) {
				if(v.get(j).getNum()==n)//
					break;
				j++;
			}
			
			int i=0;
			while(i<v.size()) {
				tt = v.get(i);//�����غ����� ������ i��° ���
				temp = tt.getPoint();	//�����ϴ°�
				
				if((temp.x<e.getX()+lab.getLocation().x && temp.x+w>e.getX()+lab.getLocation().x)&&
						(temp.y<e.getY()+lab.getLocation().y &&temp.y+h>e.getY()+lab.getLocation().y )) {
					lab.setLocation(temp.x, temp.y);
					ex[i].setLocation(ex[j].getLocation());
					for(int k=0; k<v.size(); k++) {
						System.out.print(v.get(k).getNum()+" ");
					}
					System.out.println();

					Collections.swap(v,i,j);
					
					for(int k=0; k<v.size(); k++) {
						System.out.print(v.get(k).getNum()+" ");
					}
					System.out.println();

					break;
				}
				i++;
			}
			revalidate();
			repaint();
			lab.repaint();
			//revalidate();
		}
	}
	class MyMouseMotionListener extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent e) {
			JLabelN lab = (JLabelN)e.getSource();
			lab.setLocation(lab.getX()-lab.getWidth()/2+e.getX(), lab.getY()-lab.getHeight()/2+e.getY());
			
			revalidate();
			repaint();
			lab.repaint();
		}
	}
}