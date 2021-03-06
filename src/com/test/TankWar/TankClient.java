package com.test.TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public class TankClient extends Frame {
	
	public static final int GAME_WIDTH = 800 ;
	public static final int GAME_HEIGTH = 600 ;

	Tank myTank = new Tank (50,50,true,this,Direction.STOP);
	Wall w1 = new Wall(200,300,100,200);
	//Tank enemyTank = new Tank (80,80,false,this);
	Explode e = new Explode(200,200,this);
	
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	int x =50 ,y = 50;
	
	Image offScreenImage = null;
	
	Blood b = new Blood();
	
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString("missiles count:"+missiles.size(), 10, 50);
		g.drawString("explodes count:"+explodes.size(), 10, 70);
		g.drawString("tanks count:"+tanks.size(), 10, 90);
		g.drawString("tank localtion:"+"("+myTank.getX()+" , "+myTank.getY()+")" ,10, 110);
		g.drawString("myTank HP : "+myTank.getLife(), 10, 130);
		g.setColor(c);
		
		myTank.collidesWithWall(w1);
		myTank.draw(g);	
		w1.draw(g);
		b.draw(g);
		//enemyTank.draw(g);
		//e.draw(g);
		myTank.eatBlood(b);
		
		for(int i=0; i<missiles.size();i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.collidesWithWall(w1);
			//m.hitTank(enemyTank);
			m.draw(g);
			//if(!m.isLive())missiles.remove(m);
			//else m.draw(g);
		}
		
		for(int i=0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			e.draw(g);
			}

		for(int i = 0; i<tanks.size();i++){
			Tank t = tanks.get(i);
			t.collidesWithWall(this.w1);
			t.collidesWithTank(tanks);
			t.draw(g);
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGTH);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
		gOffScreen.setColor(c);
		paint (gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame(){
		int initTankCount =PropertyMgr.getPropertyValue("initTankCount");
		
		for(int i = 0;i<initTankCount;i++){
			tanks.add(new Tank(50+40*(i+1),50,false,this,Direction.D));
		}
		
		this.setLocation(400,300);
		this.setSize(GAME_WIDTH,GAME_HEIGTH);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {				
				System.exit(0);
			}
			
		});
		this.setBackground(Color.DARK_GRAY);
		this.setVisible(true);
		this.setResizable(false);
		
		this.addKeyListener(new KeyMonitor());
		
		new Thread(new PaintThread()).start();
		
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();

	}
	
	private class PaintThread implements Runnable{
		
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyChar();
			switch(key){
			case KeyEvent.VK_1 :
				addMytank();
				break;
			case KeyEvent.VK_2 :
				addEnemytank();
				break;
			}
			
			myTank.keyPressed(e);
			}
			
		}

	private void addMytank(){
		if(myTank == null ||!this.myTank.isLive()) {
			
			myTank = new Tank (50,50,true,this,Direction.STOP);
		}
		else System.out.println("you still alive!!!!!!!");
			
	}
	
	private void addEnemytank(){
		if(tanks.size()<20){
			
			for(int i = 0;i<PropertyMgr.getPropertyValue("addTanksPerTime");i++){
				tanks.add(new Tank(50+40*(i+1),50,false,this,Direction.D));
			}
		}
		else System.out.println("too many tanks!!!!!");
	}
		
}
