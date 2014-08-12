import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {
	
	int x =50 ,y = 50;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		
		y+=50;
	}

	public void lauchFrame(){
		this.setLocation(400,300);
		this.setSize(800,600);
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
