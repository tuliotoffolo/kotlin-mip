package mip.examples;

import mip.*;

import java.util.*;

public class SimpleTest {

    public static void main(String[] args) {
        Model model = new Model("Teste", MIP.MAX, MIP.GUROBI);

        List<Var> vars = new ArrayList<>();
        vars.add(model.addIntVar("x1", 1.0));
        vars.add(model.addIntVar("x2", 2.0));

        model.addLeq(vars, 2);

        model.optimize();
        model.write("x.lp");

        for (Var v : vars) {
            System.out.println(v.getName() + " : " + v.getX());
        }
    }
}
