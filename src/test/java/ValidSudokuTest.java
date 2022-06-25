import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidSudokuTest {

    SudokuGenerator sudokuGenerator = new SudokuGenerator();
    boolean unique;
    int [][] sudoku = sudokuGenerator.getSudoku();

    @BeforeEach
    void setUp() {
        unique = true;
    }

    @Test
    void rowElementsAreUnique () {
        sudokuGenerator.printSudoku();
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                for (int k = j + 1; k < sudoku.length; k++) {
                    unique = unique && sudoku[i][j] != sudoku[i][k];
                }
            }
            assertTrue(unique);
        }
    }

    @Test
    void colElementsAreUnique () {
        for (int j = 0; j < sudoku[0].length; j++) {
            for (int i = 0; i < sudoku.length; i++) {
                for (int k = i + 1; k < sudoku.length; k++) {
                    unique = unique && sudoku[i][j] != sudoku[k][j];
                }
            }
            assertTrue(unique);
        }
    }

    @Test
    void blockElementsAreUnique () {
        for (int i = 0; i <= 6; i = i + 3) {
            for (int j = 0; j <= 6; j = j + 3) {
                unique3x3Block(i, j);
            }
        }

        assertTrue(unique);
    }

    void unique3x3Block(int rowInd, int colInd) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int blockJump = 0;
                for (int k = i * 3 + j + 1; k < 9; k++) {
                    if (k % 3 == 0)
                        blockJump++;
                    unique = unique && sudoku[rowInd + i][colInd + j] != sudoku[rowInd + (i % 3) + blockJump][colInd + (k % 3)];
                }
            }
        }
    }
}
