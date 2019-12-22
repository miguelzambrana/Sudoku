package com.miki.sudoku;

import com.miki.sudoku.business.SudokuBoard;
import com.miki.sudoku.scheduler.SolveSudoku;

import java.io.IOException;

public class SudokuService
{
    public static void main( String[] args )
    {
        long initialTime = System.currentTimeMillis();

        try
        {
            if ( args.length > 0 )
            {
                String filename = args[0];

                SudokuBoard sudokuBoard = new SudokuBoard( filename );

                SudokuBoard solution    = SolveSudoku.solveSudoku(sudokuBoard);
                long finishedTime       = System.currentTimeMillis();

                System.out.println("Show Sudoku solved:");
                System.out.println("-------------------");
                System.out.println("\n" + solution.toString());
                System.out.println("\nResolution time: " + ( finishedTime - initialTime ) + " ms");
            }
            else
            {
                System.out.println(" > Mandatory to send, like argument, soduku filename...");
            }
        }
        catch ( InterruptedException e )
        {
            System.out.println(" > Problems during sudoku solution");
        }
        catch ( IOException ioexception )
        {
            System.out.println(" > Problem opening sudoku file!!");
        }
    }
}
