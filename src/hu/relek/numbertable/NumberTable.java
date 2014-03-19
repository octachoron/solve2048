package hu.relek.numbertable;

import java.util.List;

public interface NumberTable {
	void put(Integer value, Coordinates cell);
	Integer get(Coordinates cell);
	List<Coordinates> getFreeCells();
	
	int getRowCnt();
	int getColCnt();
	
	Coordinates getFirstFilledInRow(int row, int from);
	Coordinates getLastFilledInRow(int row, int until);
	Coordinates getFirstFilledInCol(int col, int from);
	Coordinates getLastFilledInCol(int col, int until);
	
}
