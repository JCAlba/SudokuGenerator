import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Integer.valueOf;
import static java.lang.Thread.sleep;

public class SudokuGame {

    public static class PlaySudoku {
        private SudokuGenerator sudokuCreator;
        private int[][] sudoku;
        private boolean solved;


        PlaySudoku() {
            sudokuCreator = new SudokuGenerator();
            sudoku = sudokuCreator.getSudoku();
            solved = false;
        }

        public void startGame() {
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.println("Let's play Sudoku! Please state Sudoku level (1-3)");

            // Read complexity
            while (true) {
                String comp = null;
                try {
                    comp = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Integer complexity = valueOf(comp.toString());

                if (complexity > 0 && complexity < 4) {
                    System.out.println("\nHere is your Sudoku!\n");
                    sudokuCreator.reGenerate(complexity);
                    sudokuCreator.printSudoku();
                    break;
                } else {
                    System.out.println("\nPlease, provide a valid level between 1-3");
                }
            }


            while (!solved) {
                System.out.println("\nPlease, solve by providing <i j number>");

                Integer x;
                Integer y;
                Integer entry;

                while (true) {
                    String solution = null;
                    try {
                        solution = reader.readLine().replaceAll("\\s+","");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    x = Character.getNumericValue(valueOf(solution.charAt(0)));
                    y = Character.getNumericValue(valueOf(solution.charAt(1)));
                    entry = Character.getNumericValue(valueOf(solution.charAt(2)));

                    if (x < 0 || x > 8 || y < 0 || y > 8 || entry < 1 || entry > 9) {
                        System.out.println("Please provide valid input" + x.toString() + y.toString() + entry.toString());
                    } else {
                        break;
                    }
                }

                if (sudoku[x][y] == entry) {
                    sudokuCreator.addEntry(x, y);
                    if (sudokuCreator.isSolved()) {
                        System.out.println("\nCongratulations! You solved the Sudoku!");
                        sudokuCreator.printSudoku();
                        try {
                            sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        solved = true;
                    } else {
                        System.out.println("\nCorrect! Keep going.");
                        sudokuCreator.printSudoku();
                    }
                } else {
                    System.out.println("\nSorry, that number was wrong. Try again.");
                    sudokuCreator.printSudoku();
                }
            }
        }
    }

    public static void main(String[] args) {
        PlaySudoku sudokuGame = new PlaySudoku();
        sudokuGame.startGame();
    }
}
