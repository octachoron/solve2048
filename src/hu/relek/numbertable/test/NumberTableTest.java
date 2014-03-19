package hu.relek.numbertable.test;

import hu.relek.numbertable.ArrayNumberTable;
import hu.relek.numbertable.Coordinates;
import hu.relek.numbertable.NumberTable;

import java.util.List;

public class NumberTableTest {
	
	public static void main(String[] args) {
		NumberTable testTable = new ArrayNumberTable(4, 4);
		
		testTable.put(100, new Coordinates(0, 0));
		testTable.put(121, new Coordinates(2, 1));
		testTable.put(999, new Coordinates(2, 3));
		
		System.out.println(testTable);
		System.out.println("Value at (2,1): " + testTable.get(new Coordinates(2, 1)));
		
		List<Coordinates> freeCells = testTable.getFreeCells();
		System.out.println("Free cells: ");
		for (Coordinates cell : freeCells) {
			System.out.println(cell);
		}
		
		System.out.println("First filled cell in row 1: " + testTable.getFirstFilledInRow(1, 0));
		System.out.println("First filled cell in col 2: " + testTable.getFirstFilledInCol(2, 0)); //should be the same
		System.out.println("Last filled cell in row 1: " + testTable.getLastFilledInRow(1, 3));
		System.out.println("Last filled cell in col 2: " + testTable.getLastFilledInCol(2, 3));
		
		System.out.println("First filled cell in row 0: " + testTable.getFirstFilledInRow(0, 1)); //should be null
		
	}
}
