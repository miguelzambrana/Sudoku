package com.miki.sudoku;

import com.miki.sudoku.business.SudokuBoard;
import com.miki.sudoku.scheduler.SolveSudoku;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class SudokuTest
{
    @Test
    public void testSudokuBoard1() throws IOException, InterruptedException
    {
        SudokuBoard sudokuBoard = new SudokuBoard( "./boards/sudoku1" );
        SudokuBoard solvedBoard = SolveSudoku.solveSudoku(sudokuBoard);
        checkSudokuBoard(solvedBoard);
    }

    @Test
    public void testSudokuBoard2() throws IOException, InterruptedException
    {
        SudokuBoard sudokuBoard = new SudokuBoard( "./boards/sudoku2" );
        SudokuBoard solvedBoard = SolveSudoku.solveSudoku(sudokuBoard);
        checkSudokuBoard(solvedBoard);
    }

    /**
     * Check Sudoku Board
     * @param finishedBoard
     */
    private void checkSudokuBoard (SudokuBoard finishedBoard)
    {
        assertEquals(0, finishedBoard.freePositions.size());

        int[][] board = finishedBoard.getSudoku();

        for ( int x = 0 ; x < 9 ; x++ )
        {
            for ( int y = 0 ; y < 9 ; y++ )
            {
                checkSudokuPosition ( x , y , board );
            }
        }
    }

    private void checkSudokuPosition ( int x , int y , int[][] board )
    {
        int currentValue = board[x][y];

        // Check ROW
        checkSudokuRow    ( x , board , currentValue );

        // Check COLUMN
        checkSudokuColumn ( y , board , currentValue );

        // Check SQUARE
        checkSudokuSquare ( x , y , board , currentValue );


    }

    /**
     * Check Sudoku Row
     * @param x
     * @param board
     * @param currentValue
     */
    private void checkSudokuRow ( int x , int[][] board , int currentValue )
    {
        int matches = 0;

        for ( int i = 0 ; i < 9 ; i++ )
        {
            int newValue = board[x][i];

            assertNotEquals(0, newValue);

            if ( newValue == currentValue )
            {
                matches++;
            }
        }

        assertEquals(1, matches);
    }

    /**
     * Check Sudoku Column
     * @param y
     * @param board
     * @param currentValue
     */
    private void checkSudokuColumn ( int y , int[][] board , int currentValue )
    {
        int matches = 0;

        for ( int i = 0 ; i < 9 ; i++ )
        {
            int newValue = board[i][y];

            assertNotEquals(0, newValue);

            if ( newValue == currentValue )
            {
                matches++;
            }
        }

        assertEquals(1, matches);
    }

    /**
     * Check sudoku square
     * @param x
     * @param y
     * @param board
     * @param currentValue
     */
    private void checkSudokuSquare ( int x , int y , int[][] board , int currentValue )
    {
        int matches = 0;
        int xInitial, xFinish, yInitial, yFinish;

        if ( x < 3 )
        {
            xInitial    = 0;
            xFinish     = 3;
        }
        else if ( x < 6 )
        {
            xInitial    = 3;
            xFinish     = 6;
        }
        else
        {
            xInitial    = 6;
            xFinish     = 9;
        }

        if ( y < 3 )
        {
            yInitial    = 0;
            yFinish     = 3;
        }
        else if ( y < 6 )
        {
            yInitial    = 3;
            yFinish     = 6;
        }
        else
        {
            yInitial    = 6;
            yFinish     = 9;
        }

        // Remove from square delimited
        for ( int i = xInitial ; i < xFinish ; i++ )
        {
            for ( int j = yInitial ; j < yFinish ; j++)
            {
                int newValue = board[i][j];

                assertNotEquals(0, newValue);

                if ( newValue == currentValue )
                {
                    matches++;
                }
            }
        }

        assertEquals(1, matches);
    }
}
