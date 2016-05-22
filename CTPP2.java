import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CTPP2 extends JPanel{

	int width, height;
	int size = 0;
	
	int sOption = 0;
	double[] vals = new double[]{0, 1, 0, 0};
	//double fric = 0, rest = 1, grav = 0, eloss = 0;
	
	int option=0;
	
	Color copt;
	
	ArrayList<CTBall2> Balls = new ArrayList<CTBall2>();
	
	Image dbImage;
	Graphics dbg;
	
	public CTPP2(){
		width = 800;
		height = 800;
		
		init();
	}
	
	public CTPP2(int width, int height){
		this.width = width;
		this.height = height;
		
		init();
	}
	
	public void init(){
		for(int i=0;i<size;i++){
			addBall(1);
		}
	}
	
	public void addBall(){
		int radius;
		if(sOption==0){
			radius = (int) (Math.random()*50+5);
		}else{
			radius = sOption;
		}
		int xb = (int) (Math.random()*width-radius*2)+radius, yb = (int) (Math.random()*height-radius*2)+radius;
		double xv = Math.random()*10-5, yv = Math.random()*10-5;
		
		CTBall2 b = new CTBall2(xb, yb, radius, xv, yv, 0, 50, width, height, radius, 1-vals[0], vals[1], vals[2], vals[3]);
		Balls.add(b);
	}
	
	public void addBall(int i){
		if(i==1){
			int radius = 5;
			int xb = (int) (Math.random()*width-radius*2)+radius, yb = (int) (Math.random()*height-radius*2)+radius;
			double xv = Math.random()*10-5, yv = Math.random()*10-5;
			
			CTBall2 b = new CTBall2(xb, yb, radius, xv, yv, 0, 0, width, height, 800);
			Balls.add(b);
		}
	}
	
	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.black);
		
		Font f = new Font("Calibri", 0, 12);
		g.setFont(new Font("Calibri", 0, 12));
		
		g.fillRect(0, 0, width, 50);
		String[] s = new String[]{"       Size", "  Friction", "  Restitution", "   Gravity", "Energy Loss"};
		
		for(int i=0;i<5;i++){
			if(option==i){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.white);
			}
			g.drawString(s[i], i*75+15, 15);
			g.setColor(Color.lightGray);
			if(i==0){
				if(sOption==0){
					g.drawString("Random", 25+i*75, 40);
				}else{
					g.drawString(sOption+"", 42+i*75, 40);
				}
			}else{
				g.drawString((int) (vals[i-1]*100)+"%", 37+i*75, 40);
			}
		}
		
		g.setColor(Color.lightGray);
		g.drawString("To Add", 427, 22+1);
		g.setColor(Color.red);
		g.drawString("Spacebar", 422, 10+1);
		
		g.setColor(Color.lightGray);
		g.drawString("To Delete", 421, 45+1);
		g.setColor(Color.red);
		g.drawString("Backspace", 419, 33+1);
		
		for(int i=0;i<size;i++){
			CTBall2 b = Balls.get(i);
			int rgb = 255 - (int) ((b.mass-5)*4);
			Color c = new Color(rgb, rgb, rgb);
			g.setColor(c);
			
			g.fillOval((int) (b.x-b.radius), (int) (b.y-b.radius), (int) b.radius*2, (int) b.radius*2);
		}
	}
	
	public void update(Graphics g){
		if (dbImage == null) 
		{
		dbImage = createImage (this.getSize().width, this.getSize().height); 
		dbg = dbImage.getGraphics (); 
		} 

		dbg.setColor (getBackground ()); 
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height); 

		dbg.setColor (getForeground()); 
		paint (dbg); 

		g.drawImage (dbImage, 0, 0, this); 
	}
	
	public void forever(){
		CTBall2[] bs = new CTBall2[Balls.size()];
		for(int i=0;i<size;i++){
			CTBall2 s = Balls.get(i);
			CTBall2 b = new CTBall2(s.x, s.y, s.radius, s.xvel, s.yvel, s.xmin, s.ymin, s.xmax, s.ymax);
			bs[i] = b;
		}
		for(int i=0;i<size;i++){
			CTBall2 b1 = Balls.get(i);
			for(int j=0;j<size;j++){
				if(i!=j){
					b1.collision(bs[j]);
				}
			}
		}
		for(int i=0;i<size;i++){
			CTBall2 b = Balls.get(i);
			b.move();
		}
	}
	
	public void simChange(int o){
		for(int i=0;i<size;i++){
			if(o==1){
				Balls.get(i).fric = vals[o-1];
			}else if(o==2){
				Balls.get(i).rest = vals[o-1];
			}else if(o==3){
				Balls.get(i).acc = vals[o-1];
			}else if(o==4){
				Balls.get(i).eloss = vals[o-1];
			}
		}
	}
	
	public void check(double t){
		System.out.println(t);
	}
	
	public void check(String s){
		System.out.println(s);
	}
	
	public void keyDown(int k){
		if(k==32){
			size++;
			addBall();
		}
		if(k==8){
			size--;
			Balls.remove(size);
		}
		if(k==38){
			if(option==0){
				if(sOption!=55){
					if(sOption==0){
						sOption=10;
					}else{
						sOption++;
					}
				}
			}else{
				if(((int) (vals[option-1]*100))!=100){
					vals[option-1]+=0.01;
					//simChange(option);
				}
			}
		}
		if(k==40){
			if(option==0){
				if(sOption!=0){
					if(sOption==10){
						sOption=0;
					}else{
						sOption--;
					}
				}
			}else{
				if(((int) (vals[option-1]*100))!=0){
					vals[option-1]-=0.01;
					//simChange(option);
				}
			}
		}
		if(k==37){
			if(option!=0){
				option--;
			}
		}
		if(k==39){
			if(option!=4){
				option++;
			}
		}
	}
	
}
