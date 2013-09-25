import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.sensor.ChannelInfo;
import javax.microedition.sensor.Data;
import javax.microedition.sensor.DataListener;
import javax.microedition.sensor.SensorConnection;
import javax.microedition.sensor.SensorInfo;
import javax.microedition.sensor.SensorManager;

import com.nokia.mid.ui.DeviceControl;


public class FuelGameCanvas extends GameCanvas implements Runnable,CommandListener, DataListener {

	Graphics g;
	private SensorConnection sensor;
	private static final int BUFFER_SIZE = 1;
	private int direction[]={1,2,3,4};
	private int change=0;
	private int steps = 0;
	private float distance = 0;
	private SaveFuel saveFuel;
	private float petrolSaved = 0;
	private float dieselSaved = 0;
	private float caloriesBurnt = 0;
	private int cPetrol = 2707;
	private int cDiesel = 2681;
	private float moneyPetrolSaved = 0;
	private float moneyDieselSaved = 0;
	private float aCPetrol=0;
	private float aCDiesel=0;
	private int pageStatus= 1;
	private float carbonEmP=0;
	private float carbonEmD=0;
	private float nitrogenEmP=0;
	private float nitrogenEmD=0;
	private float monoEmP=0;
	private float monoEmD=0;
	private float stepCarbonP=0;
	private float stepMononP=0;
	private float stepCarbonD=0;
	private float stepMononD=0;
	
	private static int getActionKey(double axis_x, double axis_y){
		// axis_x: LEFT or RIGHT
		if (Math.abs(axis_x)>Math.abs(axis_y)){
			return axis_x<0?Canvas.RIGHT:Canvas.LEFT;
		}
		// axis_y: UP or DOWN
		return axis_y<0?Canvas.UP:Canvas.DOWN;
	}
	
	protected FuelGameCanvas(SaveFuel saveFuel) {
		super(true);
		super.setFullScreenMode(true);
		this.saveFuel = saveFuel;
		DeviceControl.setLights(0, 100);
		Thread runner = new Thread(this);
		runner.start();
		g = this.getGraphics();
	}

	public void run() {
		
		sensor = openSensor();
		if (sensor==null) return;
		sensor.setDataListener(this, BUFFER_SIZE);
		
		while(true){
			
			if (pageStatus==1)
			{
				g = this.getGraphics();
				this.flushGraphics();
				g.setColor(0x000000);
				g.fillRect(0, 0, getWidth(), getHeight());
				try {
					g.drawImage(Image.createImage("/background.png"), 0, 0, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				g.setColor(0x000000);
				g.drawString("No of steps taken: ",5,25,0);
				g.drawString(Integer.toString(steps),180,25,0);
				g.drawString("Distance Travelled[m]: ",5,55,0);
				g.drawString(Float.toString(distance),180,55,0);
				g.drawString("Petrol saved[mL]: " ,5,85,0);
				g.drawString(Float.toString(petrolSaved),180,85,0);
				g.drawString("Diesel saved[mL]: ",5,115,0);
				g.drawString(Float.toString(dieselSaved),180,115,0);
				g.drawString("Calories burned: ",5,145,0);
				g.drawString(Float.toString(caloriesBurnt),180,145,0);
				g.drawString("Money saved: ",5,175,0);
				g.drawString(Float.toString(moneyPetrolSaved),180,175,0);
				g.drawString("(P)",160,175,0);
				g.drawString(Float.toString(moneyDieselSaved),180,190,0);
				g.drawString("(D)",160,190,0);
				/*g.drawString("**Co2 emission saved **",5,180,0);
				g.drawString("Petrol[g]: " + Float.toString(aCPetrol),80,210,0);
				g.drawString("Diesel[g]: " + Float.toString(aCDiesel),80,240,0);*/
				
					
					
			}
			if (pageStatus==2){
				g = this.getGraphics();
				this.flushGraphics();
				g.setColor(0x000000);
				g.fillRect(0, 0, getWidth(), getHeight());
				try {
					g.drawImage(Image.createImage("/background2.png"), 0, 0, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				carbonEmP = 2707*petrolSaved;
				carbonEmD = 2681*dieselSaved;
				nitrogenEmP = (float) (132728.357*petrolSaved);
				nitrogenEmD = (float) (13596.5*dieselSaved);
				monoEmP = (float) (386.71*petrolSaved);
				monoEmD = (float) (383*dieselSaved);
				g.drawString("27 ",60,60,0);
				g.drawString("- ",60,115,0);
				g.drawString("0.1 ",60,170,0);
				stepCarbonP = 27 * petrolSaved*1000;
				stepMononP = (float) (0.1 * petrolSaved*1000);
				stepCarbonD = 27 * dieselSaved*1000;
				stepMononD = (float) (0.1 * dieselSaved*1000);
				g.drawString(Float.toString(stepCarbonP),120,60,0);
				g.drawString(Float.toString(stepCarbonD),180,60,0);
				g.drawString(" - ",120,120,0);
				g.drawString(" - ",180,120,0);
				g.drawString(Float.toString(stepMononP),120,180,0);
				g.drawString(Float.toString(stepMononD),180,180,0);
				
				
			}
			if (pageStatus==3){
				g = this.getGraphics();
				this.flushGraphics();
				g.setColor(0x0000FF);
				g.fillRect(0, 0, getWidth(), getHeight());
				try {
					g.drawImage(Image.createImage("/background3.png"), 0, 0, 0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				g.drawString("CO2 is about 55% of  ",5,10,0);
				g.drawString("and N2 about 5% of  ",5,30,0);
				g.drawString("GreenHouse Gases. You reduced",5,50,0);
				g.drawString("7% CO2 and 3.3% of N2  ",5,70,0);
				g.drawString("by not using your vehicle today. ",5,90,0);
				//g.drawString("2. N2 is about 5%of GreenHouse Gases. ",5,80,0);
				//g.drawString("You reduced 3.3% by not using your vehicle today.  ",5,100,0);
				//g.drawString("3.",5,130,0);
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		/*sensor.removeDataListener();
		try{
			sensor.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}*/
		
		
	}
	
	private SensorConnection openSensor(){
		SensorInfo infos[] = SensorManager.findSensors("acceleration", null);
		if (infos.length == 0) return null;
		try{
			return (SensorConnection)Connector.open(infos[0].getUrl());
		}catch(SecurityException se){
			se.printStackTrace();
			return null;
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.out.println("Couldn't open sensor : "
					+ infos[0].getUrl()+"!");
			return null;
		}
        catch(IllegalArgumentException iae) {
			iae.printStackTrace();
			return null;

        }
	}
	
	private static int[] data2actionEvents(Data[] data){

		ChannelInfo cInfo = data[0].getChannelInfo();
		boolean isInts = cInfo.getDataType() == ChannelInfo.TYPE_INT? true: false;
		int[] events = new int[BUFFER_SIZE];

		if (isInts){
			int[][] ints = new int[2][BUFFER_SIZE];
			for (int i=0; i<2; i++){
				ints[i] = data[i].getIntValues();
			}
			for (int i=0; i<BUFFER_SIZE; i++){
				events[i] = getActionKey(ints[0][i], ints[1][i]);
			}
			return events;
		}
		double[][] doubles = new double[2][BUFFER_SIZE];
		for (int i=0; i<2; i++){
			doubles[i] = data[i].getDoubleValues();
		}
		for (int i=0; i<BUFFER_SIZE; i++){
			events[i] = getActionKey(doubles[0][i], doubles[1][i]);
		}
		return events;
	}

	public void dataReceived(SensorConnection sensor, Data[] d,	boolean isDataLost) {
		int[] events = data2actionEvents(d);
		
		
		for (int i=0; i<BUFFER_SIZE; i++){
			switch(events[i]){
			case Canvas.UP:
				//TODO:put action
				//temp = "UP";
				if (direction[0]!=change)
				{
					steps = steps + 1;
				}
				change = 1;
				break;
			case Canvas.DOWN:
				//temp = "DOWN";
				if (direction[1]!=change)
				{
					steps = steps + 1;
				}
				change = 2;
				break;
			case Canvas.LEFT:
				//temp = "LEFT";
				if (direction[2]!=change)
				{
					steps = steps + 1;
				}
				change = 3;
				
				break;
			case Canvas.RIGHT:
				//temp = "RIGHT";
				if (direction[3]!=change)
				{
					steps = steps + 1;
				}
				change = 4;
				break;
		
			}
			distance = (float) (steps * 0.3);
			petrolSaved =  ((int)(distance*100/15))/100;
			dieselSaved = ((int)(distance*100/20))/100;
			caloriesBurnt = (int)((18*distance)/100);
			moneyPetrolSaved = (float) ((petrolSaved*69.2)/1000);
			moneyDieselSaved = (float) ((dieselSaved*50.42)/1000);
			
		}
	}
	
	protected void pointerPressed(int x, int y) {
		
		if(pageStatus==3&&x>10&&x<130&&y<310&&y>260){
			this.saveFuel.notifyDestroyed();
		}
		if(pageStatus==2&&x>20&&x<220&&y<310&&y>220){
			pageStatus=3;
		}
		if(pageStatus==1&&x>20&&x<220&&y<300&&y>220){
			pageStatus=2;
		}		
		
	}
	
	public void hideNotify() {
	}

	public void showNotify() {
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		
	}

}
