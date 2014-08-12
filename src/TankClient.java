import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {
	
	public static final int GAME_WIGTH = 800 ;
	public static final int GAME_HIGTH = 600 ;			

	int x =50 ,y = 50;
	
	Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		
		y+=5;
		if(y>600){
			y=50;
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage = this.createImage(GAME_WIGTH, GAME_HIGTH);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.DARK_GRAY);
		gOffScreen.fillRect(0, 0, GAME_WIGTH, GAME_HIGTH);
		gOffScreen.setColor(c);
		paint (gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame(){
		this.setLocation(400,300);
		this.setSize(GAME_WIGTH,GAME_HIGTH);
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

}
