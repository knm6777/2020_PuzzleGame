import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;
class PuzzleVec{
	private int num;	//원래 퍼즐 번호
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
	public int num;//원래퍼즐번호
	public int getNum() {
		return num;
	}
}
public class GamePanel extends JPanel{
	private JLabel pan;
	private JLabelN ex[] = new JLabelN[16];
	private ImageIcon puzIcon;	//생성후엔  tmp 역할
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
		
		puzIcon = new ImageIcon(puzImg); //Image로 ImageIcon 생성
		a = new BufferedImage(puzImg.getWidth(null), puzImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		w = a.getWidth()/wn;
		h = a.getHeight()/hn;
		
		cropImage(a, wn, hn);
		for(int k=0; k<v.size(); k++) {
			System.out.print(v.get(k).getNum()+" ");
		}
		System.out.println();
		//vv=v;//벡터 복사본
		Collections.shuffle(v);
		
		for(int k=0; k<v.size(); k++) {
			System.out.print(v.get(k).getNum()+" ");
		}
		
		System.out.println();
		//벡터 순서 섞기
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
		
		//배열 랜덤하게 만듬
        setVisible(true); 
	}
	
	public void cropImage(BufferedImage a, int wn, int hn) {
	    Graphics2D bGr = a.createGraphics();
	    bGr.drawImage(puzImg, 0, 0, null);
	    bGr.dispose();
	    
		int n = 0;	//퍼즐 넘버
		for(int i=0; i<wn; i++) {
			for(int j=0; j<hn; j++) {
				crop = a.getSubimage(i*w, j*h, w, h);
				puzIcon = new ImageIcon(crop);
				temp = new Point(100+j*w, 70+i*h);	
				tmp = new PuzzleVec(n, crop, temp);
				n++;
				v.add(tmp);	//벡터에 요소 추가
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
			int n = lab.getNum();//현재 마우스가 잡고있는 라벨의 고유번호

			int j=0;
			while(j<v.size()) {
				if(v.get(j).getNum()==n)//
					break;
				j++;
			}
			
			int i=0;
			while(i<v.size()) {
				tt = v.get(i);//생각해보려는 벡터의 i번째 요소
				temp = tt.getPoint();	//가야하는곳
				
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