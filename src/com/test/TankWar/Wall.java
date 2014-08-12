package com.test.TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Wall {
	private int x,y,width,heigth;
	private boolean canPass = false;
	private boolean canDestory = false;
	private boolean live = true;
	private TankClient tc = null;
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeigth(){
		return heigth;
	}
	
	public boolean isLive() {
		return live;
	}

	public  Wall(int x,int y,int width,int heigth){
		this.x=x;
		this.y=y;
		this.width = width;
		this.heigth = heigth;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,width,heigth);
	}
	
	public void draw (Graphics g){
		
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, heigth);
		g.setColor(c);
	}
}
