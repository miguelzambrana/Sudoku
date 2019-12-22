package com.miki.sudoku.utils;

import com.miki.sudoku.business.SudokuBoard;
import com.miki.sudoku.pojo.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuReader
{
    /**
     * Get Board from file
     * @param sudokuBoard
     * @param filename
     * @return
     * @throws IOException
     */
    public static void readBoardFromFile ( SudokuBoard sudokuBoard, String filename ) throws IOException
    {
        sudokuBoard.sudoku = new int[9][9];

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
                    sudokuBoard.sudoku[position][valuePos] = currentValue;

                    if ( currentValue == 0 )
                    {
                        sudokuBoard.freePositions.add(new Position(position, valuePos));
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
