package hu.relek.solve2048.players;

import java.util.List;

import hu.relek.solve2048.controller.Player;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.Game2048Table;
import hu.relek.solve2048.logic.PlayerInterface;
import hu.relek.solve2048.stategraph.StateGraphNode;
import hu.relek.solve2048.stategraph.StateGraphNodeImpl;

public class GreedyPlayer implements Player {

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
		
		Direction selectedMove = validMoves.get(0);
		int maxScore = 0;
		for (Direction dir : validMoves) {
			StateGraphNode nextNode = node.move(dir);
			Game2048Table nextState = nextNode.getContents();
			
			System.out.println("Move " + dir + " is worth " + nextState.getScore());
			
			if (nextState.getScore() > maxScore) {
				selectedMove = dir;
				maxScore = nextState.getScore();
			}
			
		}
		
		System.out.println("Selected move: " + selectedMove);
		iface.slide(selectedMove);
	}

}
