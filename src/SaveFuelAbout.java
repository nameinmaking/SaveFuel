import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;


public class SaveFuelAbout extends Form implements ItemCommandListener {

	private SaveFuel saveFuel;
	private StringItem btnStart;
	private Command cmdStart;
	private StringItem btnExit;
	private Command cmdExit;
	private StringItem instructions;
	
	public SaveFuelAbout(SaveFuel timer) {
		super("About");
		this.saveFuel=saveFuel;
		
		btnStart = new StringItem(null, "Main Menu", Item.BUTTON);
		cmdStart = new Command("Main Menu", Command.OK, 1);
		btnStart.setDefaultCommand(cmdStart);
		btnStart.setItemCommandListener(this);
		append(btnStart);
		
		instructions = new StringItem("\t\t\tAbout","Application Name:\n" +
				"\tSaveFuel\n" +
				"Version:\n" +
				"\t1.0\n" +
				"Developed By:\n" +
				"\tVipul Sharma\n" +
				"Email:\n" +
				"\tvipulsharma018@gmail.com",Item.PLAIN);
		append(instructions);		
		
		
		btnExit = new StringItem(null, "Exit", Item.BUTTON);
		cmdExit = new Command("EXIT", Command.EXIT, 1);
		btnExit.setDefaultCommand(cmdExit);
		btnExit.setItemCommandListener(this);
		append(btnExit);
	}

	public void commandAction(Command arg0, Item arg1) {
		if (arg0.getLabel() == "Main Menu"){
			saveFuel.showstart();
		}
		if (arg0.getLabel() == "EXIT"){
			saveFuel.notifyDestroyed();
		}
	}

}
