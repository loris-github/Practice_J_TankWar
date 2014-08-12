package com.test.TankWar;
import java.awt.*;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
	int x,y;
	TankClient tc;
	private boolean live = true;
	
	int step = 0;
	public static boolean init = false;
	
	//int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = {
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	
	public Explode (int x,int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		
		if(!init){
			
			for(int i = 0; i<imgs.length;i++){
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		
		if(!live){
			tc.explodes.remove(this);
			return;
		} 

		
		if(step == imgs.length){
			live = false;
			step = 0;
			return;
		}
		
		/*
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x,y,diameter[step],diameter[step]);
		step++;
		*/
		
		g.drawImage(imgs[step], x, y, null);
		
		step ++;
		
	}
	
}
