import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/**
 * 1631. Path With Minimum Effort
 * https://leetcode.com/problems/path-with-minimum-effort/
 */
public class PathWithMinimumEffort {


    // **** global variable(s) ****
    static boolean vis[][]      = null;
    static boolean arrived      = false;
    static int effort           = Integer.MIN_VALUE;
    static final int[][] dirs   = { {0,1}, {1,0}, {0,-1}, {-1,0} };

    // **** global variables to generate gate ****
    static int left     = 0;
    static int right    = 1000000;


    /**
     * Search for minimum effort.
     * Recursive procedure.
     * Execution time:  O(rows * cols)
     */
    static int dfs(int[][] heights, int row, int col, int gate) {

        // **** get the number of rows and columns ****
        int rows = heights.length;
        int cols = heights[0].length;

        // **** base case (lower-right cell) ****
        if (row == rows - 1 && col == cols - 1) {
            arrived = true;
            return heights[row][col];
        }

        // **** flag that this cell as been visited ****
        vis[row][col] = true;

        // **** recurse in all possible directions O(rows * cols)****
        for (int i = 0; i < dirs.length; i++) {

            // **** next cell ****
            int r = row + dirs[i][0];
            int c = col + dirs[i][1];

            // **** ****
            if (r >= 0 && r < rows && c >= 0 && c < cols && 
                !vis[r][c] && 
                Math.abs(heights[row][col] - heights[r][c]) <= gate) {

                // **** recursive call ****
                int ph = dfs(heights, r, c, gate);

                // **** current effort between cells ****
                int ce = Math.abs(heights[row][col] - ph);

                // **** update effort ****
                effort = Math.max(effort, ce);
            }
        }

        // **** return height of current cell ****
        return heights[row][col];
    }


    /**
     * Generate the next value of a binary search.
     * Set gate == -1 to initialize.
     */
    static int genGate(int gate) {

        // **** initialization ****
        if (gate == -1) {
            left    = 0;
            right   = 1000000;
            return right / 2;
        }

        // **** done ****
        if (left > right) {
            return gate;
        }

        // **** compute mid value ****
        int mid = left + (right - left) / 2;

        // **** go left ****
        if (arrived) {
            right = mid - 1;
        }

        // **** go right ****
        else {
            left = mid + 1;
        }

        // **** update gate ****
        gate = left + (right - left) / 2;

        // **** return gate ****
        return gate;
    }


    /**
     * Entry point for recursive procedure.
     *
     * Runtime: 124 ms, faster than 33.18% of Java online submissions.
     * Memory Usage: 40.2 MB, less than 5.71% of Java online submissions.
     */
    static int minimumEffortPath(int[][] heights) {

        // **** get number of rows and columns ****
        int rows = heights.length;
        int cols = heights[0].length;

        // **** sanity checks ****
        if (rows <= 1 && cols <= 1)
            return 0;


        // **** loop incrementing the gate ****
        // for (int gate = 0; gate < 1000000; gate++) {

        //     // **** loop pass initialization ****
        //     arrived = false;
        //     effort  = Integer.MIN_VALUE;
        //     vis     = new boolean[rows][cols];

        //     // **** start recursion  ****
        //     dfs(heights, 0, 0, gate);

        //     // **** if reached bottom-right cell; we are done ****
        //     if (arrived)
        //         break;
        // }


        // **** initial gate ****
        int gate = genGate(-1);

        // **** ****
        boolean done = false;
        do {

            // **** loop pass initialization ****
            arrived = false;
            effort  = Integer.MIN_VALUE;
            vis     = new boolean[rows][cols];

            // **** save for comparison ****
            int prevGate = gate;

            // **** start recursion  ****
            dfs(heights, 0, 0, gate);

            // **** generate new gate ****
            gate = genGate(gate);

            // **** matching values ****
            if (gate == prevGate)
                done = true;
        } while (!done);

        // **** return effort ****
        return effort;
    }
    

    /**
     * Test scaffolding.
     */
    public static void main(String[] args) {

        // **** initialize random number generator ****
        Random rand = new Random();

        // **** target gate ****
        int targetGate  = rand.nextInt(1000000) + 1;

        // **** display target gate value ****
        System.out.println("main <<< targetGate: " + targetGate);

        // **** loop until target gate is found ****
        boolean done    = false;
        int gate        = genGate(-1);
        do {

            // **** save for comparison ****
            int prevGate = gate;

            // **** update arrived as needed ****
            arrived = (gate >= targetGate) ? true : false;

            // **** new gate ****
            gate = genGate(gate);

            // **** matching values ****
            if (gate == prevGate)
                done = true;
        } while (!done);

        // **** display gate ****
        System.out.println("main <<<       gate: " + gate);

        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read number of rows and columns ****
        String[] rcStr = sc.nextLine().trim().split(",");

        // **** extract number of rows and columns ****
        int rows = Integer.parseInt(rcStr[0]);
        int cols = Integer.parseInt(rcStr[1]);

        // **** declare the array ****
        int[][] heights = new int[rows][cols];

        // **** loop populating the heights array ****
        for (int r = 0; r < rows; r++) {

            // **** ****
            String[] colsStr = sc.nextLine().trim().split(",");

            // **** ****
            for (int c = 0; c < cols; c++) {
                heights[r][c] = Integer.parseInt(colsStr[c]);
            }
        }

        // ???? ????
        System.out.println("main <<< heights:");
        for (int[] rr : heights) {
            System.out.println(Arrays.toString(rr));
        }

        // **** close scanner ****
        sc.close();

        // **** find and display minimum effort for path ****
        System.out.println("main <<< output: " + minimumEffortPath(heights));
    }
}