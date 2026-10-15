import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static class Site {
        boolean isOpen;
        boolean isFull;
        boolean isConnectingBottom;
    }

    Site[] sites;
    WeightedQuickUnionUF connectStatus;
    int openSitesCount;
    int size;
    boolean isPercolate;

    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("Size of grid must be larger than 0.");
        }
        sites = new Site[N * N];
        for (int i = 0; i < N * N; i++) {
            sites[i] = new Site();
        }
        connectStatus = new WeightedQuickUnionUF(N * N);
        openSitesCount = 0;
        size = N;
    }

    public void open(int row, int col) {
        validateIndicesAndThrow(row, col);
        int curIdx = calcIndex(row, col);
        if (sites[curIdx].isOpen) {
            return;
        }

        sites[curIdx].isOpen = true;
        if (row == 0) {
            sites[curIdx].isFull = true;
        }
        if (row == size - 1) {
            sites[curIdx].isConnectingBottom = true;
        }
        openSitesCount += 1;

        connectToNeighbor(curIdx, row, col - 1);
        connectToNeighbor(curIdx, row, col + 1);
        connectToNeighbor(curIdx, row - 1, col);
        connectToNeighbor(curIdx, row + 1, col);

        if (isFull(row, col) && isConnectingBottom(row, col)) {
            isPercolate = true;
        }
    }

    public boolean isOpen(int row, int col) {
        validateIndicesAndThrow(row, col);
        return sites[calcIndex(row, col)].isOpen;
    }

    public boolean isFull(int row, int col) {
        validateIndicesAndThrow(row, col);
        return sites[connectStatus.find(calcIndex(row, col))].isFull;
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        return isPercolate;
    }

    // Return the index where a site is stored in openStatus and connectStatus.
    private int calcIndex(int row, int col) {
        return size * col + row;
    }

    private boolean validateIndices(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private void validateIndicesAndThrow(int row, int col) {
        if (!validateIndices(row, col)) {
            throw new IndexOutOfBoundsException("Row and col must between 0 and N-1.");
        }
    }

    // Connect an open node to one of its four neighbors, maintaining their isFull and isConnectingBottom status.
    private void connectToNeighbor(int curIdx, int row, int col) {
        if (validateIndices(row, col) && sites[calcIndex(row, col)].isOpen) {
            int oldRootIdx = connectStatus.find(curIdx);
            int neighborIdx = calcIndex(row, col);
            int neighborRootIdx = connectStatus.find(neighborIdx);

            connectStatus.union(oldRootIdx, neighborRootIdx);
            int newRootIdx = connectStatus.find(curIdx);
            sites[newRootIdx].isFull = sites[oldRootIdx].isFull || sites[neighborRootIdx].isFull;
            sites[newRootIdx].isConnectingBottom =
                    sites[oldRootIdx].isConnectingBottom || sites[neighborRootIdx].isConnectingBottom;
        }
    }

    private boolean isConnectingBottom(int row, int col) {
        return sites[connectStatus.find(calcIndex(row, col))].isConnectingBottom;
    }
}
