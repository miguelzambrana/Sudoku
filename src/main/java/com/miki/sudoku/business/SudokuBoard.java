package com.miki.sudoku.business;

import com.miki.sudoku.pojo.Position;
import com.miki.sudoku.utils.SudokuReader;

import java.io.IOException;
import java.util.*;

public class SudokuBoard
{
    public int[][]        sudoku;
    public List<Position> freePositions;

    /**
     * Construct board by filename
     * @param filename
     * @throws IOException
     */
    public SudokuBoard( String filename ) throws IOException
    {
        freePositions   = new ArrayList<>();

        // Read sudoku from file
        SudokuReader.readBoardFromFile(this, filename);
    }

    /**
     * Construct board by other board (clone)
     * @param sudokuBoard
     */
    public SudokuBoard ( SudokuBoard sudokuBoard )
    {
        this.sudoku         = cloneBoard(sudokuBoard.sudoku);
        this.freePositions  = new ArrayList(sudokuBoard.freePositions);
    }

    /**
     * Fill position
     * @param indexPosition
     * @param position
     * @param fillValue
     */
    public void fillPosition ( int indexPosition , Position position , int fillValue )
    {
        this.sudoku[position.getX()][position.getY()] = fillValue;
        this.freePositions.remove(indexPosition);
    }

    public int[][] getSudoku()
    {
        return sudoku;
    }

    public List<Position> getFreePositions()
    {
        return freePositions;
    }

    /**
     * Get possibilities from position
     * @param freePosition
     * @return
     */
    public List<Integer> getPossibleValues ( Position freePosition )
    {
        // Create possibilities
        Set<Integer> possibilities = new HashSet<>();

        // Add all possible values
        possibilities.addAll(new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9)));

        int x = freePosition.getX();
        int y = freePosition.getY();

        // Check first row
        for ( int i = 0 ; i < 9 ; i++ )
        {
            possibilities.remove(sudoku[x][i]);
        }

        // And column
        for ( int i = 0 ; i < 9 ; i++ )
        {
            possibilities.remove(sudoku[i][y]);
        }

        // And remove possibilities from square
        removePossibilitiesFromSquare ( x , y , possibilities );

        return new ArrayList<>(possibilities);
    }

    /**
     *
     * @param x
     * @param y
     * @param possibilities
     */
    private void removePossibilitiesFromSquare ( int x , int y , Set<Integer> possibilities )
    {
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
                possibilities.remove(sudoku[i][j]);
            }
        }

    }

    @Override
    public String toString()
    {
        return toStringSudoku(sudoku);
    }

    /**
     * To String Sudoku board
     * @param sudoku
     * @return
     */
    private String toStringSudoku ( int[][] sudoku )
    {
        StringBuffer stringBuffer = new StringBuffer();

        for ( int i = 0 ; i < sudoku.length ; i++ )
        {
            for ( int j = 0 ; j < sudoku[i].length ; j++ )
            {
                if ( j > 0 ) {
                    stringBuffer.append(",");
                } else {
                    stringBuffer.append(" ");
                }

                stringBuffer.append(sudoku[i][j]);
            }

            stringBuffer.append("\n");
        }

        return stringBuffer.toString();
    }

    /**
     * Array clone
     * @param board
     * @return
     */
    public static int[][] cloneBoard(int[][] board)
    {
        int[][] newBoard = new int[board.length][];

        for (int i = 0; i < board.length; i++)
        {
            newBoard[i] = board[i].clone();
        }

        return newBoard;
    }
}
