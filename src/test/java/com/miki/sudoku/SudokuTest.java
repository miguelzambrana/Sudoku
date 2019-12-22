package com.miki.sudoku;

import com.miki.sudoku.business.SudokuTable;
import com.miki.sudoku.pojo.Position;

import java.io.IOException;

public class SudokuTest
{
    public static void main( String[] args ) throws IOException
    {
        long time = System.currentTimeMillis();

        SudokuTable sudokuTable = new SudokuTable( "./boards/sudoku1" );

        System.out.println(sudokuTable);
        System.out.println(" Free Positions: " + sudokuTable.getFreePositions().size() );
        System.out.println(" Free Positions possibilites: ");

        for ( Position freePosition : sudokuTable.getFreePositions() )
        {
            System.out.println("  > " + freePosition.toString() + " :: " + sudokuTable.getPosibleValues(freePosition));
        }

        System.out.println(" Total time: " + ( System.currentTimeMillis() - time ) + " ms" );
    }
}
