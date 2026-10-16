import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF percolateUF;
    private WeightedQuickUnionUF fullUF;
    private boolean[][] isOpen;
    private int numberOfOpenSites;
    private final int SIZE;
    private final int TOP;
    private final int BOTTOM;

    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("Size of grid must be larger than 0.");
        }

        TOP = N * N;
        BOTTOM = N * N + 1;
        SIZE = N;
        numberOfOpenSites = 0;
        isOpen = new boolean[N][N];
        percolateUF = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) {
            percolateUF.union(i, TOP);
            percolateUF.union(N * (N-1) + i, BOTTOM);
        }
        fullUF = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            fullUF.union(i, TOP);
        }
    }

    public void open(int row, int col) {
        validateIndicesAndThrow(row, col);

        if (isOpen[row][col]) {
            return;
        }
        isOpen[row][col] = true;
        numberOfOpenSites += 1;

        int curIdx = xyTo2D(row, col);
        connectToNeighbor(curIdx, row, col - 1);
        connectToNeighbor(curIdx, row, col + 1);
        connectToNeighbor(curIdx, row - 1, col);
        connectToNeighbor(curIdx, row + 1, col);
    }

    public boolean isOpen(int row, int col) {
        validateIndicesAndThrow(row, col);
        return isOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && fullUF.connected(TOP, xyTo2D(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        if (SIZE == 1) {
            return isOpen(0, 0);
        }
        return percolateUF.connected(TOP, BOTTOM);
    }

    private int xyTo2D(int row, int col) {
        return SIZE * row + col;
    }

    private boolean validateIndices(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private void validateIndicesAndThrow(int row, int col) {
        if (!validateIndices(row, col)) {
            throw new IndexOutOfBoundsException("Row and col must between 0 and N-1.");
        }
    }

    private void connectToNeighbor(int curIdx, int row, int col) {
        if (validateIndices(row, col) && isOpen[row][col]) {
            percolateUF.union(curIdx, xyTo2D(row, col));
            fullUF.union(curIdx, xyTo2D(row, col));
        }
    }
}
