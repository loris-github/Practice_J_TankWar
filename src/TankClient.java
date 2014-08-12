import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {

	public static final int GAME_WIDTH = 800 ;
	public static final int GAME_HEIGTH = 600 ;
	
	Tank myTank = new Tank (50,50,this);
	Missile m = null;
	
	int x =50 ,y = 50;
	
	Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		myTank.draw(g);	
		if(this.m!=null){
			this.m.draw(g);
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGTH);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.DARK_GRAY);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
		gOffScreen.setColor(c);
		paint (gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame(){
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
			myTank.keyPressed(e);
			}
			
		}
		
}
