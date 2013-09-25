import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;


public class Splash extends GameCanvas implements Runnable{

	Graphics g;
	private SaveFuel saveFuel;
	private boolean shift=true;
	
	protected Splash(SaveFuel saveFuel) {
		super(true);
		super.setFullScreenMode(true);
		this.saveFuel=saveFuel;
		Thread tempo=new Thread(this);
		tempo.start();
		// TODO Auto-generated constructor stub
	}

	public void run() {
		while(this.shift){
			g = this.getGraphics();
			this.flushGraphics();
			g.setColor(0x00FF00);
			g.fillRect(0, 0, getWidth(), getHeight());
			try {
				g.drawImage(Image.createImage("/splash.png"), 0, 0, 0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.currentThread().sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		saveFuel.fuelgamecanvas=new FuelGameCanvas(saveFuel);
		saveFuel.d.setCurrent(this.saveFuel.fuelgamecanvas);
	}
	
	public void pointerPressed(int x, int y){
		this.shift=false;
	}

}
