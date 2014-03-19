package hu.relek.solve2048.machines;

import hu.relek.numbertable.Coordinates;
import hu.relek.solve2048.controller.Machine;
import hu.relek.solve2048.logic.MachineInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ConsoleMachine implements Machine {

	private MachineInterface iface;
	private BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public void setIface(MachineInterface iface) {
		this.iface = iface;
	}

	@Override
	public void turn() {
		while (true) {
			try {
				System.out.println("Enter the machine's move as x y value, then press Enter");
				String input = inReader.readLine();
				StringTokenizer st = new StringTokenizer(input);
	
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int value = Integer.parseInt(st.nextToken());
				
				iface.put(value, new Coordinates(x, y));
				break;
			} catch (Exception e) {
				continue;
			}
		}
	}

}
