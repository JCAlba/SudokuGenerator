import java.util.*;

public class SudokuGenerator {
    private int[][] m_sudoku = new int[9][9];
    private Integer m_complexity = 2; // 1 = Easy, 2 = Medium, 3 = Hard.
    protected List<IntPair> m_emptySquares = new ArrayList<IntPair>();

    class IntPair {
        final Integer x;
        final Integer y;
        IntPair(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        boolean equals(IntPair p) {
            if (this.x == p.x && this.y == p.y) {
                return true;
            }
            return false;
        }


    }

    SudokuGenerator() {
        Integer increment = 0;
        for (int i = 1; i < m_sudoku.length + 1; i++) {
            for (int j = 1; j < m_sudoku[0].length + 1; j++) {
                m_sudoku[i - 1][j - 1] = j + increment > 9 ? j + increment - 9: j + increment;
                if (j == m_sudoku[0].length) {
                    increment = (increment + 3) % 9;
                }
            }
            if (i % 3 == 0) {
                increment = increment + 1;
            }
        }

        for (int j = 1; j < 100; j++) {
            shuffleNumbers();
        }

        randomEmptySquares();
    }

    public void reGenerate(Integer complexity) {
        m_complexity = complexity;

        for (int j = 1; j < 100; j++) {
            shuffleNumbers();
        }
        m_emptySquares.clear();
        randomEmptySquares();
    }

    private Integer complexityToElements (Integer com) {
        switch (com) {
            case 1:
                return 40;
            case 2:
                return 50;
            case 3:
                return 60;
            default:
                System.out.println("Complexity must be 1-3");
                return 0;
        }
    }

    private void randomEmptySquares() {
        int elems = complexityToElements(m_complexity);
        for (int i = 0; i < elems; i++) {
            Random rand = new Random();
            int ranNum1 = rand.nextInt(9);
            int ranNum2 = rand.nextInt(9);

            IntPair pair = new IntPair(ranNum1, ranNum2);
            if (!isSquareEmpty(pair.x, pair.y)) {
                m_emptySquares.add(pair);
            } else {
                i--;
            }
        }
    }

    void shuffleNumbers() {
        for (int i = 0; i < 9; i++) {
            Random rand = new Random();
            int ranNum = rand.nextInt(8);
            swapNumbers(i + 1, ranNum + 1);
        }
    }

    private void swapNumbers(int n1, int n2) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (m_sudoku[x][y] == n1) {
                    m_sudoku[x][y] = n2;
                } else if (m_sudoku[x][y] == n2) {
                    m_sudoku[x][y] = n1;
                }
            }
        }
    }

    private boolean isSquareEmpty(Integer x, Integer y) {
        for (IntPair pair: m_emptySquares) {
            if ((!pair.x.equals(null) && !pair.y.equals(null)) && pair.x == x && pair.y == y)
                    return true;
        }
        return false;
    }

    String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    public void printSudoku() {
        String sudokuFormat = new String();
        for (int i = 0; i < m_sudoku.length; i++) {
            if (i % 3 == 0) {
                if (i != 0) {
                    sudokuFormat = removeLastChar(sudokuFormat) + "\n";
                }
                sudokuFormat = sudokuFormat + "-------------------------------------\n| ";
            } else {
                sudokuFormat = sudokuFormat + "\n| ";
            }
            for (int j = 0; j < m_sudoku[0].length; j++) {
                if (isSquareEmpty(i, j)) {
                    sudokuFormat = sudokuFormat + "  | ";
                } else {
                    sudokuFormat = sudokuFormat + Integer.toString(m_sudoku[i][j]) + " | ";
                }
            }
        }
        sudokuFormat = removeLastChar(sudokuFormat) + "\n-------------------------------------";
        System.out.println(sudokuFormat);
    }

    public void addEntry(Integer x, Integer y) {
        IntPair ip = new IntPair(x, y);
        for (int i = 0; i < m_emptySquares.size(); i++) {
            if (ip.equals(m_emptySquares.get(i))) {
                m_emptySquares.remove(i);
                break;
            }
        }
    }

    public boolean isSolved() {
        return m_emptySquares.size() == 0;
    }
    public int[][] getSudoku() {
        return m_sudoku;
    }
}
