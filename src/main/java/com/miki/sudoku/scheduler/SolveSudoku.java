package com.miki.sudoku.scheduler;

import com.miki.sudoku.business.SudokuBoard;
import com.miki.sudoku.pojo.Position;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SolveSudoku
{
    // Create Executor service with the double of processor cores available...
    private static ExecutorService executorService;

    // Final solution
    public static SudokuBoard sudokeSolved;

    /**
     * Solve Sudoku problem
     * @param initialBoard
     * @return
     */
    public static SudokuBoard solveSudoku ( SudokuBoard initialBoard ) throws InterruptedException
    {
        final SudokuBoard currentBoard = initialBoard;

        executorService = Executors.newFixedThreadPool ( Runtime.getRuntime().availableProcessors() * 4 );

        executorService.submit(() -> {

            try
            {
                Position currentPosition        = currentBoard.freePositions.get(0);
                List<Integer> possibleValues    = currentBoard.getPossibleValues(currentPosition);

                for ( int currentValue : possibleValues )
                {
                    // Clone board
                    SudokuBoard newBoard = new SudokuBoard(currentBoard);

                    // Fill new value
                    newBoard.fillPosition(0, currentPosition, currentValue);

                    // And send solution
                    sendSolution(newBoard);
                }
            }
            catch ( Exception e ) { e.printStackTrace(); }

        });

        // Wait to executor finished
        executorService.awaitTermination (1, TimeUnit.MINUTES );

        return sudokeSolved;
    }

    /**
     * Send solution to executor
     * @param board
     */
    public static void sendSolution ( SudokuBoard board )
    {
        if ( board.freePositions.size() > 0 )
        {
            final SudokuBoard currentBoard = board;

            executorService.submit(() -> {

                try
                {
                    Position currentPosition        = currentBoard.freePositions.get(0);
                    List<Integer> possibleValues    = currentBoard.getPossibleValues(currentPosition);

                    if ( possibleValues.size() > 0 )
                    {
                        for ( int currentValue : possibleValues )
                        {
                            // Clone board
                            SudokuBoard newBoard = new SudokuBoard(currentBoard);

                            // Fill new value
                            newBoard.fillPosition(0, currentPosition, currentValue);

                            // And send solution
                            sendSolution(newBoard);
                        }
                    }
                    // else Board without solutions!!!
                }
                catch ( Exception e ) {}

            });
        }
        else
        {
            // And shutdown executor!!
            executorService.shutdown();

            // Solve Board
            SolveSudoku.sudokeSolved = board;
        }
    }
}
