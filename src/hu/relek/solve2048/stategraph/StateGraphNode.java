package hu.relek.solve2048.stategraph;

import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.Game2048Table;

import java.util.List;

public interface StateGraphNode {
	
	Game2048Table getContents();
	List<Direction> getValidMoves();
	StateGraphNode move(Direction dir);
	
	List<MachineMove> getValidMachineMoves();
	StateGraphNode machineMove(MachineMove move);

}
