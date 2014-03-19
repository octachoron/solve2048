package hu.relek.solve2048.logic;

import hu.relek.numbertable.Coordinates;
import hu.relek.solve2048.controller.Machine;

import java.util.List;

/**
 * When given turn, chooses a free cell on the table at random, and randomly puts a 2 or a 4 there.
 * @author relek
 *
 */
public class Normal2048Machine implements Machine {

	private MachineInterface iface;
	
	@Override
	public void setIface(MachineInterface iface) {
		this.iface = iface;
	}

	@Override
	public void turn() {
		List<Coordinates> freeCells = iface.getFreeCells();
		Coordinates chosenCell = freeCells.get((int)Math.round(Math.floor(Math.random() * freeCells.size())));
		iface.put(Math.random() < 0.9 ? 2 : 4, chosenCell);
	}

}
