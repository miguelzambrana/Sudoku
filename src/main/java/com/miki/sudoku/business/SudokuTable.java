package com.miki.sudoku.business;

import com.miki.sudoku.pojo.Position;
import com.miki.sudoku.utils.SudokuReader;

import java.io.IOException;
import java.util.*;

public class SudokuTable
{
    public int[][]        sudoku;
    public List<Position> freePositions;

    public SudokuTable ( String filename ) throws IOException
    {
        freePositions   = new ArrayList<>();

        // Read sudoku from file
        SudokuReader.readBoardFromFile(this, filename);
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
    public List<Integer> getPosibleValues ( Position freePosition )
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

        return new ArrayList<>(possibilities);
    }

    private String toStringSudoku ( int[][] sudoku )
    {
        StringBuffer stringBuffer = new StringBuffer();

        for ( int i = 0 ; i < sudoku.length ; i++ )
        {
            stringBuffer.append("\n");

            for ( int j = 0 ; j < sudoku[i].length ; j++ )
            {
                if ( j > 0 ) {
                    stringBuffer.append(",");
                }

                stringBuffer.append(sudoku[i][j]);
            }
        }

        return stringBuffer.toString();
    }

    @Override
    public String toString()
    {
        return "SudokuTable{" +
                "sudoku: " + toStringSudoku(sudoku) + "\n}";
    }
}
