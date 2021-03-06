package com.test.TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int XSPEED = 6;
	public static final int YSPEED = 6;
	
	public static final int WIDTH = 10;
	public static final int HEIGTH = 10;
	
	int x ,y ;
	Direction dir = null ;
	private boolean good;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();	
	private static Image[] missileImages = null;
	private static Map<String,Image> imgs = new HashMap<String,Image>();
	static{
		missileImages = new Image[]{
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif"))
		};		
		imgs.put("L", missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);		
	}
	
	public boolean isGood() {
		return good;
	}

	private boolean live = true;
	private TankClient tc;
	
	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y,Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;	
	}
	
	public Missile(int x,int y,Direction dir,TankClient tc){
		this(x,y,dir);
		this.tc = tc;
	}
	
	public Missile(int x,int y,Direction dir,TankClient tc,boolean good){
		this(x,y,dir,tc);
		this.good = good;
	}
	
	public void draw (Graphics g){
		if(!live){
			tc.missiles.remove(this);
			return;
		}
/*		
		Color c = g.getColor();
		if (this.isGood()) g.setColor(Color.CYAN);
		else g.setColor(Color.YELLOW);		
		g.fillOval(x, y, WIDTH, HEIGTH);
		g.setColor(c);
		*/
		
		switch(this.dir){
		case L:
			g.drawImage(imgs.get("L"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x, y+HEIGTH/2);
			break;
		case LU:
			g.drawImage(imgs.get("LU"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x, y);
			break;
		case U:
			g.drawImage(imgs.get("U"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x+HEIGTH/2, y);
			break;
		case RU:
			g.drawImage(imgs.get("RU"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x+Tank.WIDTH, y);
			break;
		case R:
			g.drawImage(imgs.get("R"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x+Tank.WIDTH, y+HEIGTH/2);
			break;
		case RD:
			g.drawImage(imgs.get("RD"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x+Tank.WIDTH, y+HEIGTH);
			break;
		case D:
			g.drawImage(imgs.get("D"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x+Tank.WIDTH/2, y+HEIGTH);
			break;
		case LD:
			g.drawImage(imgs.get("LD"),x,y,null);
			//g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGTH/2, x, y+HEIGTH);
			break;
		case STOP:
			break;
	}	
		
		move();
	}
	
	void move(){
		switch(this.dir){
			case L:
				x-= XSPEED;
				break;
			case LU:
				x -= XSPEED;
				y -= YSPEED;
				break;
			case U:
				y -= YSPEED;
				break;
			case RU:
				x += XSPEED;
				y -= YSPEED;
				break;
			case R:
				x += XSPEED;
				break;
			case RD:
				x += XSPEED;
				y += YSPEED;
				break;
			case D:
				y += YSPEED;
				break;
			case LD:
				x -= XSPEED;
				y += YSPEED;
				break;
		}
		
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGTH){
			live = false;
		}
	}

	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGTH);
	}
	
	public boolean hitTank(Tank t){
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive()&&this.good != t.isGood()){
			t.setLife(t.getLife()-20);
			if(t.isGood()&&t.getLife()<=0) t.setLive(false);
			else if (!t.isGood()) t.setLive(false);
			
			this.live = false;
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i= 0 ;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}		
		return false;
	}
	
	public boolean collidesWithWall(Wall w){
		if(this.live && this.getRect().intersects(w.getRect())&&w.isLive()){
			this.live = false;
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}	
		return true;
	}
	
}
