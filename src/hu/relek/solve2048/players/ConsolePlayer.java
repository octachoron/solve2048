package hu.relek.solve2048.players;

import hu.relek.solve2048.controller.Player;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.PlayerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * When given turn, asks on the console what to do, then performs that operation.
 * This class makes no decisions. Its primary use is manual playing, but it may
 * be possible to talk to the UI from a script.
 * 
 * @author relek
 * 
 */
public class ConsolePlayer implements Player {

	private PlayerInterface iface;
	private BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public void setIface(PlayerInterface iface) {
		this.iface = iface;
	}

	@Override
	public void turn() {
		System.out.println("Use WASD, then Enter to do your move");
		try {
			choice:
			while (true) {
				String input = inReader.readLine();
				if (input.isEmpty()) {
					continue;
				}
				switch (input.charAt(0)) {
				case 'w':
				case 'W':
					System.out.println("You move up");
					iface.slide(Direction.UP);
					break choice;
				
				case 'a':
				case 'A':
					System.out.println("You move left");
					iface.slide(Direction.LEFT);
					break choice;
				
				case 's':
				case 'S':
					System.out.println("You move down");
					iface.slide(Direction.DOWN);
					break choice;
				
				case 'd':
				case 'D':
					System.out.println("You move right");
					iface.slide(Direction.RIGHT);
					break choice;
				
				default:
					System.out.println("Invalid choice, try again. Valid choices are: " +
							"w or W for up, a or A for left, s or S for down, and d or D for right");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
