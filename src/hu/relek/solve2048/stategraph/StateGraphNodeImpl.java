package hu.relek.solve2048.stategraph;

import hu.relek.numbertable.Coordinates;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.Game2048Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrap a state with an instance of this class to get a computed view of the state graph
 * starting at the given state.
 * 
 * @author relek
 *
 */
public class StateGraphNodeImpl implements StateGraphNode {

	private Game2048Table contents;
	
	public StateGraphNodeImpl(Game2048Table state) {
		this.contents = state;
	}
	
	@Override
	public Game2048Table getContents() {
		return contents;
	}

	@Override
	public List<Direction> getValidMoves() {
		List<Direction> ret = new ArrayList<Direction>();
		
		for (Direction dir : Direction.values()) {
			if (contents.canSlide(dir)) {
				ret.add(dir);
			}
		}
		
		return ret;
	}

	@Override
	public StateGraphNode move(Direction dir) {
		Game2048Table newState = new Game2048Table(contents);
		newState.slide(dir);
		return new StateGraphNodeImpl(newState);
	}
	
	@Override
	public String toString() {
		return contents == null ? "(null state)" : contents.toString();
	}

	@Override
	public List<MachineMove> getValidMachineMoves() {
		List<Coordinates> freeCells = contents.getFreeCells();
		List<MachineMove> ret = new ArrayList<MachineMove>();
		
		for (Coordinates cell : freeCells) {
			ret.add(new MachineMove(cell, 2));
			ret.add(new MachineMove(cell, 4));
		}
		
		return ret;
	}

	@Override
	public StateGraphNode machineMove(MachineMove move) {
		Game2048Table newState = new Game2048Table(contents);
		newState.put(move.getB(), move.getA());
		return new StateGraphNodeImpl(newState);
	}

}
