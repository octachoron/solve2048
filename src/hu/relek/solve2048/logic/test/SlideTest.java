package hu.relek.solve2048.logic.test;

import hu.relek.numbertable.Coordinates;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.Game2048Table;

public class SlideTest {
	
	public static void main(String[] args) {
		Game2048Table table = new Game2048Table();
		
		table.put(2, new Coordinates(0, 0));
		table.put(64, new Coordinates(2, 1));
		table.put(32, new Coordinates(2, 3));
		table.put(32, new Coordinates(0, 3));
		table.put(64, new Coordinates(1, 1));
		table.put(2, new Coordinates(0, 1));
		table.put(4, new Coordinates(3, 1));
		table.put(4, new Coordinates(3, 3));
		table.put(4, new Coordinates(3, 0));
		table.put(4, new Coordinates(3, 2));
		
		System.out.println("Table before sliding: ");
		System.out.println(table);
		
		System.out.println("Table after sliding: ");
		table.slide(Direction.LEFT);
		System.out.println(table);
	}

}
