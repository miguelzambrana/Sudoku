package com.miki.sudoku.utils;

import com.miki.sudoku.business.SudokuTable;
import com.miki.sudoku.pojo.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuReader
{
    /**
     * Get Board from file
     * @param sudokuTable
     * @param filename
     * @return
     * @throws IOException
     */
    public static void readBoardFromFile ( SudokuTable sudokuTable , String filename ) throws IOException
    {
        sudokuTable.sudoku = new int[9][9];

        try ( BufferedReader bf = new BufferedReader(new FileReader(filename)) )
        {
            String line;
            int position = 0;

            while((line=bf.readLine())!=null)
            {
                String[] values = line.split(",");
                int valuePos = 0;

                for ( String value : values )
                {
                    int currentValue = getCurrentValue(value);
                    sudokuTable.sudoku[position][valuePos] = currentValue;

                    if ( currentValue == 0 )
                    {
                        sudokuTable.freePositions.add(new Position(position, valuePos));
                    }

                    valuePos++;
                }

                position++;
            }
        }
    }

    /**
     * Get Current board value
     * @param value
     * @return
     */
    private static int getCurrentValue ( String value )
    {
        try {
            return Integer.valueOf(value);
        }
        catch ( Exception e ) {
            return 0;
        }
    }

}
