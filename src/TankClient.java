import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30);
		g.setColor(c);
		
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
		
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();

	}

}
