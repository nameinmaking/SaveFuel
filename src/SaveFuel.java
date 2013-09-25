import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class SaveFuel extends MIDlet {

	public Display d;
	public FuelGameCanvas fuelgamecanvas;
	public Splash splash;
	protected Display display;
	
	
	public SaveFuel() {
		d=Display.getDisplay(this);
		this.splash = new Splash(this);
		//fuelgamecanvas=new FuelGameCanvas(this);
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}
	
	synchronized public void showstart(){
		display.setCurrent(splash);
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		d.setCurrent(splash);

	}

}
