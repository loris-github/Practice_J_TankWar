package com.test.TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 30;
	public static final int HEIGTH = 30;
	
	private int oldX,oldY;
	private int x , y;
	private boolean good;
	private boolean live = true;
	private BloodBar bb = new BloodBar();
	private int life =100;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();	
	private static Image[] tankImages = null;
	private static Map<String,Image> imgs = new HashMap<String,Image>();
	static{
		tankImages = new Image[]{
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif"))
		};		
		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);		
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private static Random r = new Random();
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public boolean isGood() {
		return good;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	TankClient tc = null;
	//Wall w = null;
	private boolean bL = false,bU = false,bR = false,bD = false;
	//public enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	
	public Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	private int step = r.nextInt((12)+3);
	
	public Tank(int x, int y,boolean good) {
		super();
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y,boolean good, TankClient tc) {
		this(x, y,good);
		this.tc = tc;
	}
	
	public Tank(int x,int y,boolean good,TankClient tc,Direction dir){
		this(x,y,good,tc);
		this.dir = dir;
	}
	
	public void draw (Graphics g){
		if(!live) {
			tc.tanks.remove(this);
			return;
		}
/*		
		Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGTH);
		g.setColor(c);
		*/
		if(this.isGood()) bb.draw(g);
		
		switch(ptDir){
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
		oldX = x;
		oldY = y;
		
		switch(dir){
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
			case STOP:
				break;
		}
		
		if(this.dir!= Direction.STOP){
			ptDir = this.dir;
		}
		
		if(x<0) x= 0;
		if(y<30) y= 30;
	
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y+Tank.HEIGTH>TankClient.GAME_HEIGTH) y =TankClient.GAME_HEIGTH - Tank.HEIGTH;
		
		if(!good){		
			Direction[] dirs = Direction.values();		
			if(step==0){
				step=r.nextInt(12)+3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}			
			step --;
			
		if(r.nextInt(40)>38) this.fire(this);
		
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;					
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_S:
		fire(tc.myTank);
			break;
		case KeyEvent.VK_A:
		superFire(tc.myTank);
			break;
		}
		
		locateDirection();

	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;					
		case KeyEvent.VK_DOWN:
			bD = false;
			break;		
		}
		
		locateDirection();
	}
	
	void locateDirection(){
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public Missile fire(Tank t){
		if(!live) return null;
		int x = t.x+t.WIDTH/2-Missile.WIDTH/2;
		int y = t.y+t.HEIGTH/2-Missile.HEIGTH/2;
		Missile m = new Missile (x,y,t.ptDir,this.tc,t.good);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Tank t, Direction dir){
		if(!live) return null;
		int x = t.x+t.WIDTH/2-Missile.WIDTH/2;
		int y = t.y+t.HEIGTH/2-Missile.HEIGTH/2;
		Missile m = new Missile (x,y,dir,this.tc,t.good);
		tc.missiles.add(m);
		return m;
	}
	
	public void superFire(Tank t){
		Direction[] dirs = Direction.values();
		for(int i = 0 ; i<8;i++){
			fire(t,dirs[i]);
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGTH);
		}
	
	public boolean collidesWithWall(Wall w){
		if(this.getRect().intersects(w.getRect())&&this.live&&w.isLive()) {	
			int newY_Wall = y;			
			y = oldY;			
			if(this.getRect().intersects(w.getRect())){
				x = oldX;
				y = newY_Wall;
			return true;
			}
		}
		return false;	
	}
	
	public boolean collidesWithTank(java.util.List<Tank> tanks){
		for (int i = 0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			if (this!=t){
				if(this.getRect().intersects(t.getRect())&&this.live&&t.isLive()) {	
					int newY_Wall = y;			
					y = oldY;			
					if(this.getRect().intersects(t.getRect())){
						x = oldX;
						y = newY_Wall;
					return true;
					}
				}				
			}						
		}
		return false;
	}
	
	private class BloodBar{
		public void draw (Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * life/100;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	
	public void eatBlood (Blood b){
		if (this.isGood()&&b.live&&this.isLive()&&this.getRect().intersects(b.getRect())) {
			this.setLife(100);
			b.live = false;
		}
	}
}


/*		
//left of wall
if(x+Tank.WIDTH > tc.w1.getX() && y+Tank.HEIGTH>tc.w1.getY() && y+Tank.HEIGTH< tc.w1.getY()+tc.w1.getHeigth()&&(this.dir==Direction.R||this.dir==Direction.RU||this.dir==Direction.RD)) {
x = tc.w1.getX()-Tank.WIDTH;
System.out.println("left");
}

//right of wall
if(x < tc.w1.getX()+tc.w1.getWidth()&& y+Tank.HEIGTH>tc.w1.getY() && y+Tank.HEIGTH< tc.w1.getY()+tc.w1.getHeigth()&&(this.dir==Direction.L||this.dir==Direction.LU||this.dir==Direction.LD)) {
x = tc.w1.getX()+tc.w1.getWidth();
System.out.println("right");
}
//top of wall
if(y+Tank.HEIGTH > tc.w1.getY() && x+Tank.WIDTH> tc.w1.getX() && x< tc.w1.getX()+tc.w1.getWidth()&&(this.dir==Direction.D||this.dir==Direction.LD||this.dir==Direction.RD)){
y = tc.w1.getY() - Tank.HEIGTH;
System.out.println("top");
}
//bottom of wall
if(y < tc.w1.getY()+tc.w1.getHeigth() && x+Tank.WIDTH> tc.w1.getX() && x< tc.w1.getX()+tc.w1.getWidth()&&(this.dir==Direction.U||this.dir==Direction.LU||this.dir==Direction.RU)){
y = tc.w1.getY()+tc.w1.getHeigth();
System.out.println("bottom");
}



if (this.dir == Direction.L||this.dir == Direction.R){
if(y+Tank.HEIGTH>tc.w1.getY()&&y<tc.w1.getY()+tc.w1.getHeigth()) x = oldX;
}

else if(this.dir == Direction.U||this.dir==Direction.D){
if(x+Tank.WIDTH>tc.w1.getX()&&x<tc.w1.getX()+tc.w1.getWidth()) y =oldY;
}

else if(this.dir == Direction.LU){
if(y+Tank.HEIGTH>tc.w1.getY()
&&y<tc.w1.getY()+tc.w1.getHeigth()
&&((tc.w1.getY()+tc.w1.getHeigth()-y>tc.w1.getX()+tc.w1.getWidth()-x)||(y+Tank.HEIGTH-tc.w1.getY()>tc.w1.getX()+tc.w1.getWidth()-x))){
x = oldX;
System.out.println("LU-1");
}

else if(x+Tank.WIDTH>tc.w1.getX()
	&&x<tc.w1.getX()+tc.w1.getWidth()
	&&((tc.w1.getX()+tc.w1.getWidth()-x>tc.w1.getY()+tc.w1.getHeigth()-y)||(x+Tank.WIDTH-tc.w1.getX()>tc.w1.getY()+tc.w1.getHeigth()-y))){
y = oldY;
System.out.println("LU-2");
}

}
else if(this.dir == Direction.RU){
if(y+Tank.HEIGTH>tc.w1.getY()
&&y<tc.w1.getY()+tc.w1.getHeigth()
&&tc.w1.getY()+tc.w1.getHeigth()-y>tc.w1.getX()+tc.w1.getWidth()-x) 
x = oldX;
else if(x+Tank.WIDTH>=tc.w1.getX()
	&&x<tc.w1.getX()+tc.w1.getWidth()
	&&tc.w1.getY()+tc.w1.getHeigth()-y<tc.w1.getX()+tc.w1.getWidth()-x) 
y = oldY;
}
else if(this.dir == Direction.RD){
if(y+Tank.HEIGTH>tc.w1.getY()
&&y<tc.w1.getY()+tc.w1.getHeigth()
&&tc.w1.getY()+tc.w1.getHeigth()-y>x+Tank.WIDTH-tc.w1.getX()) 
x = oldX;
else if(x+Tank.WIDTH>tc.w1.getX()
	&&x<tc.w1.getX()+tc.w1.getWidth()
	&&tc.w1.getY()+tc.w1.getHeigth()-y<x+Tank.WIDTH-tc.w1.getX()) 
y = oldY;
}
else if(this.dir == Direction.LD){
if(y+Tank.HEIGTH>tc.w1.getY()
&&y<tc.w1.getY()+tc.w1.getHeigth()
&&tc.w1.getY()+tc.w1.getHeigth()-y>tc.w1.getX()+tc.w1.getWidth()-x)
x = oldX;
else if(x+Tank.WIDTH>tc.w1.getX()
	&&x<tc.w1.getX()+tc.w1.getWidth()
	&&tc.w1.getY()+tc.w1.getHeigth()-y<tc.w1.getX()+tc.w1.getWidth()-x) 
y = oldY;
}

else{
x =oldX;
y =oldY;
}*/
