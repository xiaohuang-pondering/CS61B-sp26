import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF connectStatus;
    boolean[][] isOpen;
    int openSitesCount;
    final int SIZE;
    final int TOP;
    final int BOTTOM;

    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("Size of grid must be larger than 0.");
        }

        TOP = N * N;
        BOTTOM = N * N + 1;
        SIZE = N;
        openSitesCount = 0;
        isOpen = new boolean[N][N];
        connectStatus = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) {
            connectStatus.union(i, TOP);
            connectStatus.union(N * (N-1) + i, BOTTOM);
        }
    }

    public void open(int row, int col) {
        validateIndicesAndThrow(row, col);

        if (isOpen[row][col]) {
            return;
        }
        isOpen[row][col] = true;
        openSitesCount += 1;

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
        return isOpen(row, col) && connectStatus.connected(TOP, xyTo2D(row, col));
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        return connectStatus.connected(TOP, BOTTOM);
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
            connectStatus.union(curIdx, xyTo2D(row, col));
        }
    }
}
