package com.miki.sudoku.business;

import com.miki.sudoku.pojo.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SudokuTable
{
    int[][]        sudoku;
    List<Position> freePositions;

    public SudokuTable ( String file ) throws IOException
    {
        freePositions   = new ArrayList<>();

        try ( BufferedReader bf = new BufferedReader(new FileReader(file)) )
        {
            sudoku = new int[9][9];

            String line;
            int position = 0;

            while((line=bf.readLine())!=null)
            {
                String[] values = line.split(",");
                int valuePos = 0;

                for ( String value : values )
                {
                    int currentValue = getCurrentValue(value);
                    sudoku[position][valuePos] = currentValue;

                    if ( currentValue == 0 )
                    {
                        freePositions.add(new Position(position, valuePos));
                    }

                    valuePos++;
                }

                position++;
            }
        }
    }

    public List<Position> getFreePositions()
    {
        return freePositions;
    }

    private int getCurrentValue ( String value )
    {
        try {
            return Integer.valueOf(value);
        }
        catch ( Exception e ) {
            return 0;
        }
    }

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
