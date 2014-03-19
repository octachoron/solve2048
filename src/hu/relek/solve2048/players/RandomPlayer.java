package hu.relek.solve2048.players;

import java.util.List;

import hu.relek.solve2048.controller.Player;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.PlayerInterface;
import hu.relek.solve2048.stategraph.StateGraphNode;
import hu.relek.solve2048.stategraph.StateGraphNodeImpl;

public class RandomPlayer implements Player {

	private PlayerInterface iface;
	
	@Override
	public void setIface(PlayerInterface iface) {
		this.iface = iface;
		
	}

	@Override
	public void turn() {
		StateGraphNode node = new StateGraphNodeImpl(iface.takeSnapshot());
		
		List<Direction> validMoves = node.getValidMoves();
		System.out.println("Valid moves: " + validMoves);
		int selectedMoveIdx = (int)Math.round(Math.floor(Math.random() * validMoves.size()));
		System.out.println("Doing move: " + validMoves.get(selectedMoveIdx));
		iface.slide(validMoves.get(selectedMoveIdx));
	}

}
