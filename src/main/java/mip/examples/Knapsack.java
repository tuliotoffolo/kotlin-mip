package mip.examples;

import mip.*;

import java.util.*;

public class Knapsack {

    public static void main(String[] args) {
        int[] p = { 10, 13, 18, 31, 7, 15 };
        int[] w = { 11, 15, 20, 35, 10, 33 };
        int c = 47;
        int n = w.length;

        // creating model
        Model m = new Model("Knapsack");

        // creating vars and setting objective function
        List<Var> x = new ArrayList<>();
        for (int i = 0; i < n; i++)
            x.add(m.addBinVar("item_" + i));

        // creating and setting objective function
        LinExpr obj = new LinExpr();
        for (int i = 0; i < n; i++)
            obj.add(x.get(i), p[i]);
        m.setObjective(MIP.maximize(obj));

        // adding constraint
        LinExpr expr = new LinExpr();
        for (int i = 0; i < n; i++)
            expr.add(x.get(i), w[i]);
        m.addLeq(expr, c);

        // solving model
        m.optimize();

        // printing result
        System.out.print("Selected items: { ");
        for (int i = 0; i < n; i++)
            if (x.get(i).getX() >= 0.99)
                System.out.print(i + " ");
        System.out.println("}");
    }
}