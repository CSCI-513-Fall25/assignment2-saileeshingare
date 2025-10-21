package com.columbus;

import java.util.Random;

public class OceanMap {

    private final int dimension = 10;
    private int[][] grid;

    public OceanMap() {
        grid = new int[dimension][dimension];
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                grid[x][y] = 0;
            }
        }
        placeIslands(10);
    }

    private void placeIslands(int numIslands) {
        Random rand = new Random();
        int placed = 0;
        while (placed < numIslands) {
            int x = rand.nextInt(dimension);
            int y = rand.nextInt(dimension);
            if (grid[x][y] == 0) {
                grid[x][y] = 1;
                placed++;
            }
        }
    }

    public int[][] getMap() {
        return grid;
    }

    public int getDimension() {
        return dimension;
    }

    public boolean isOcean(int x, int y) {
        if (x < 0 || x >= dimension || y < 0 || y >= dimension) return false;
        return grid[x][y] == 0;
    }
}
