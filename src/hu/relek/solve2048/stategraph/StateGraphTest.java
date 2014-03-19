package hu.relek.solve2048.stategraph;

import hu.relek.solve2048.controller.Machine;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.Game2048Table;
import hu.relek.solve2048.logic.MachineInterface;
import hu.relek.solve2048.logic.Normal2048Machine;

import java.util.List;

public class StateGraphTest {
	
	public static void main(String[] args) {
		Machine machine = new Normal2048Machine();
		Game2048Table table = new Game2048Table();
		machine.setIface((MachineInterface) table);
		machine.turn();
		machine.turn();
		
		//Initial table done, create the graph view, and fool around with it.
		//Fool around means choosing a random valid move until we reach a game over state.
		StateGraphNode node = new StateGraphNodeImpl(table);
		int movesMade = 0;
		
		while (!node.getContents().isGameOver()) {
			System.out.println("Current state:");
			System.out.println(node);
			List<Direction> validMoves = node.getValidMoves();
			System.out.println("Valid moves: " + validMoves);
			int selectedMoveIdx = (int)Math.round(Math.floor(Math.random() * validMoves.size()));
			System.out.println("Doing move: " + validMoves.get(selectedMoveIdx));
			node = node.move(validMoves.get(selectedMoveIdx));
			movesMade++;
			machine.setIface((MachineInterface) node.getContents());
			machine.turn();
		}
		System.out.println("Game over after " + movesMade + " moves. Final state:");
		System.out.println(node.getContents());
	}
}
