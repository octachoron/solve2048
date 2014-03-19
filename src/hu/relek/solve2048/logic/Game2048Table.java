package hu.relek.solve2048.logic;

import hu.relek.numbertable.ArrayNumberTable;
import hu.relek.numbertable.Coordinates;
import hu.relek.numbertable.NumberTable;

import java.util.ArrayList;
import java.util.List;

public class Game2048Table implements PlayerInterface, MachineInterface {
	
	private NumberTable table = new ArrayNumberTable(4, 4);
	private int score = 0;

	public Game2048Table() {
		//empty
	}
	
	public Game2048Table(Game2048Table other) {
		table = new ArrayNumberTable(other.table);
		score = other.score;
	}
	
	@Override
	public void put(int value, Coordinates cell) {
		table.put(value, cell);
	}

	@Override
	public void slide(Direction dir) {
		//Check for adjacent equal values from the given direction, disregarding empty cells.
		//If found, multiply the former by 2, and set the latter to null.
		for (int i = 0; i < 4; i++) {
			next:
			for (int j = 0; j < 4; j++) {
				
				Coordinates prevCell = null, curCell = null;
				
				switch (dir) {
				case UP:
					prevCell = table.getFirstFilledInCol(i, j);
					if (prevCell == null) {
						break next;
					}
					curCell = table.getFirstFilledInCol(i, prevCell.getY() + 1);
					if (curCell == null) {
						break next;
					}
					j = curCell.getY() - 1;
					break;
				case LEFT:
					prevCell = table.getFirstFilledInRow(i, j);
					if (prevCell == null) {
						break next;
					}
					curCell = table.getFirstFilledInRow(i, prevCell.getX() + 1);
					if (curCell == null) {
						break next;
					}
					j = curCell.getX() - 1;
					break;
				case DOWN:
					prevCell = table.getLastFilledInCol(i, 3 - j);
					if (prevCell == null) {
						break next;
					}
					curCell = table.getLastFilledInCol(i, prevCell.getY() - 1);
					if (curCell == null) {
						break next;
					}
					j = 3 - curCell.getY() - 1;
					break;
				case RIGHT:
					prevCell = table.getLastFilledInRow(i, 3 - j);
					if (prevCell == null) {
						break next;
					}
					curCell = table.getLastFilledInRow(i, prevCell.getX() - 1);
					if (curCell == null) {
						break next;
					}
					j = 3 - curCell.getX() - 1;
					break;
				default:
					throw new IllegalArgumentException();
				}
				
				Integer prev = table.get(prevCell);
				Integer cur = table.get(curCell);
				
				if (prev.equals(cur)) {
					table.put(prev * 2, prevCell);
					table.put(null, curCell);
					score += prev * 2;
				}
			}
		}
		
		//Slide all current numbers to the given direction
		compact(dir);
	}
	
	private void compact(Direction dir) {
		for (int i = 0; i < 4; i++) {
			List<Integer> filledOnly = new ArrayList<Integer>();

			for (int j = 0; j < 4; j++) {
				Coordinates cell;
				
				switch (dir) {
				case LEFT:
				case RIGHT:
					cell = new Coordinates(j, i);
					break;
				
				case UP:
				case DOWN:
					cell = new Coordinates(i, j);
					break;
					
				default:
					throw new IllegalArgumentException();
				}
				
				Integer value = table.get(cell);
				if (value != null) {
					filledOnly.add(value);
					table.put(null, cell);
				}
			}
			
			for (int j = 0; j < filledOnly.size(); j++) {
				switch (dir) {
				case LEFT:
					table.put(filledOnly.get(j), new Coordinates(j, i));
					break;
					
				case RIGHT:
					table.put(filledOnly.get(j), new Coordinates(3 - filledOnly.size() + 1 + j, i));
					break;
					
				case UP:
					table.put(filledOnly.get(j), new Coordinates(i, j));
					break;
					
				case DOWN:
					table.put(filledOnly.get(j), new Coordinates(i, 3 - filledOnly.size() + 1 + j));
					break;
					
				default:
					throw new IllegalArgumentException();
				}
			}
		}
	}
	
	public boolean canSlide(Direction dir) {
		Game2048Table copy = new Game2048Table(this);
		copy.slide(dir);
		return !this.equals(copy);
	}
	
	public boolean isGameOver() {
		for (Direction dir : Direction.values()) {
			if (canSlide(dir)) {
				return false;
			}
		}
		return true;
	}
	

	@Override
	public List<Coordinates> getFreeCells() {
		return table.getFreeCells();
	}

	@Override
	public String toString() {
		return table.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Game2048Table)) {
			return false;
		}
		Game2048Table gameTable = (Game2048Table) obj;
		return table.equals(gameTable.table);
	}
	
	@Override
	public int hashCode() {
		return table.hashCode();
	}

	@Override
	public Game2048Table takeSnapshot() {
		return new Game2048Table(this);
	}
	
	public int getScore() {
		return score;
	}
}
