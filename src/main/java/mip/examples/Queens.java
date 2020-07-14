package mip.examples;

import mip.*;

public class Queens {

    public static void main(String[] args) {
        int n = 40;

        Model queens = new Model("Queens");

        Var[][] x = new Var[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                x[i][j] = queens.addBinVar("x(" + i + "," + j + ")");

        // one queen per row
        for (int i = 0; i < n; i++) {
            LinExpr lhs = new LinExpr();
            for (int j = 0; j < n; j++)
                lhs.add(x[i][j]);
            queens.addEq(lhs, 1, "row_" + i);
        }

        // one queen per column
        for (int j = 0; j < n; j++) {
            LinExpr lhs = new LinExpr();
            for (int i = 0; i < n; i++)
                lhs.add(x[i][j]);
            queens.addEq(lhs, 1, "col_" + j);
        }

        // diagonal \
        for (int k = 2 - n; k <= n - 2; k++) {
            LinExpr lhs = new LinExpr();
            for (int i = 0; i < n; i++)
                if (i - k >= 0 && i - k < n)
                    lhs.add(x[i][i - k]);
            queens.addLeq(lhs, 1, "diag1_" + String.valueOf(k).replace('-', 'm'));
        }

        // diagonal /
        for (int k = 3; k <= n + n - 1; k++) {
            LinExpr lhs = new LinExpr();
            for (int i = 0; i < n; i++)
                if (k - i >= 0 && k - i < n)
                    lhs.add(x[i][k - i]);
            queens.addLeq(lhs, 1, "diag2_" + k);
        }

        queens.optimize();
        queens.write("model.lp");

        if (queens.hasSolution()) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    System.out.print(x[i][j].getX() >= MIP.EPS ? "O " : ". ");
                System.out.println();
            }
        }
    }
}