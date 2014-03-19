package hu.relek.solve2048.controller;

import hu.relek.solve2048.logic.Game2048Table;
import hu.relek.solve2048.logic.MachineInterface;
import hu.relek.solve2048.logic.PlayerInterface;

public class GameOf2048 {
	
	private Machine machine;
	private Player player;
	
	public GameOf2048(Machine machine, Player player) {
		this.machine = machine;
		this.player = player;
	}
	
	public void start() {
		Game2048Table table = new Game2048Table();
		machine.setIface((MachineInterface) table);
		player.setIface((PlayerInterface) table);
		
		//Machine puts 2 random blocks on the table by default
		machine.turn();
		machine.turn();
		System.out.println(table);
		
		int turn = 0;
		//Main game loop
		while (!table.isGameOver()) {
			System.out.println("Turn " + (turn++) + " starts, current score is " + table.getScore());
			Game2048Table snapshot = new Game2048Table(table);
			do {
				player.turn();
			} while (table.equals(snapshot));
			machine.turn();
			System.out.println(table);
		}
		
		System.out.println("No more moves. Completed turns: " + turn + ". Final score: " + table.getScore() + ".");
		System.out.println("Final state:");
		System.out.println(table);
	}
}
