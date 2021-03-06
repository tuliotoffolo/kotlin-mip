package mip.examples;

import mip.*;

import java.util.*;

public class SimpleTest {

    public static void main(String[] args) {
        Model model = new Model("Teste", MIP.MAX, MIP.CBC);

        List<Var> vars = new ArrayList<>();
        vars.add(model.addNumVar("x1", 1.0));
        vars.add(model.addNumVar("x2", 2.0));

        LinExpr expr = new LinExpr(vars.get(0));
        expr.add(vars.get(1));
        model.addConstr(Constr.leq(expr, 2.5));

        model.setThreads(1);
        model.optimize();
        model.write("x.lp");

        for (Var v : vars) {
            System.out.println(v.getName() + " : " + v.getX());
        }
    }
}
