package com.test.TankWar;
import java.awt.*;
public class Blood {
	int x,y,w,h;
	TankClient tc;
	int step = 0;
	boolean live = true;
	
	private int[][] pos={
			{350,300},{360,300},{375,275},{400,200},{360,270},{365,290},{340,280}
	};
	
	public Blood(){
		x = pos[0][0];
		y = pos[0][1];
		w = h = 15;
	}

	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
	
	public void draw (Graphics g){
		if(this.live){
			Color c = g.getColor();
			g.setColor(Color.MAGENTA);
			g.fillRect(x,y,w,h);
			g.setColor(c);		
			move();			
		}
	}
	
	private void move(){
		step ++;
		if(step == pos.length) step = 0;
		
		else	
			x = pos[step][0];
			y = pos[step][1];
		
	}
}
