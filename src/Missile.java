import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Missile {
	public static final int XSPEED = 6;
	public static final int YSPEED = 6;
	
	public static final int WIDTH = 10;
	public static final int HEIGTH = 10;
	
	int x ,y ;
	Tank.Direction dir = null ;
	private boolean good;
	private boolean live = true;
	private TankClient tc;
	
	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y,Tank.Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;	
	}
	
	public Missile(int x,int y,Tank.Direction dir,TankClient tc){
		this(x,y,dir);
		this.tc = tc;
	}
	
	public Missile(int x,int y,Tank.Direction dir,TankClient tc,boolean good){
		this(x,y,dir,tc);
		this.good = good;
	}
	
	public void draw (Graphics g){
		if(!live){
			tc.missiles.remove(this);
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, WIDTH, HEIGTH);
		g.setColor(c);
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
			t.setLive(false);
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
	
	
}
