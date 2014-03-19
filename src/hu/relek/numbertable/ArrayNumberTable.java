package hu.relek.numbertable;

import java.util.ArrayList;
import java.util.List;

public class ArrayNumberTable implements NumberTable {

	private Integer[][] table;
	
	public ArrayNumberTable(int rows, int cols) {
		table = new Integer[rows][cols];
	}
	
	public ArrayNumberTable(NumberTable other) {
		table = new Integer[other.getRowCnt()][other.getColCnt()];
		for (int row = 0; row < table.length; row++) {
			for (int col = 0; col < table[0].length; col++) {
				table[row][col] = other.get(new Coordinates(col, row));
			}
		}
	}
	
	@Override
	public void put(Integer value, Coordinates cell) {
		table[cell.getY()][cell.getX()] = value;

	}

	@Override
	public Integer get(Coordinates cell) {
		int row = cell.getY();
		int col = cell.getX();
		
		return table[row][col];
	}

	@Override
	public List<Coordinates> getFreeCells() {
		List<Coordinates> ret = new ArrayList<Coordinates>();
		
		for (int row = 0; row < table.length; row++) {
			for (int col = 0; col < table[0].length; col++) {
				if (table[row][col] == null) {
					ret.add(new Coordinates(col, row));
				}
			}
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int row = 0; row < table.length; row++) {
			sb.append('|');
			for (int col = 0; col < table[0].length; col++) {
				Integer value = table[row][col];
				if (value != null) {
					sb.append(value);
				}
				sb.append("\t|");
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}

	@Override
	public Coordinates getFirstFilledInRow(int row, int from) {
		for (int i = from; i < table[0].length; i++) {
			if (table[row][i] != null) {
				return new Coordinates(i, row);
			}
		}
		return null;
	}

	@Override
	public Coordinates getLastFilledInRow(int row, int until) {
		for (int i = until; i >= 0; i--) {
			if (table[row][i] != null) {
				return new Coordinates(i, row);
			}
		}
		return null;
	}

	@Override
	public Coordinates getFirstFilledInCol(int col, int from) {
		for (int i = from; i < table.length; i++) {
			if (table[i][col] != null) {
				return new Coordinates(col, i);
			}
		}
		return null;
	}

	@Override
	public Coordinates getLastFilledInCol(int col, int until) {
		for (int i = until; i >= 0; i--) {
			if (table[i][col] != null) {
				return new Coordinates(col, i);
			}
		}
		return null;
	}

	@Override
	public int getRowCnt() {
		return table.length;
	}

	@Override
	public int getColCnt() {
		return table[0].length;
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof ArrayNumberTable)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		
		ArrayNumberTable other = (ArrayNumberTable)obj;
		if( (getRowCnt() != other.getRowCnt()) || (getColCnt() != other.getColCnt()) ) {
			return false;
		}
		
		for (int row = 0; row < getRowCnt(); row++) {
			for (int col = 0; col < getColCnt(); col++) {
				if (table[row][col] != other.table[row][col]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return table.hashCode();
	}
}
