package br.ufop.jmip;

import br.ufop.jmip.entities.*;

public class TestJava {
    public static void main(String args[]) {
        Model model = new Model();
        Var v1 = model.addVar();
        LinExpr expr = new LinExpr(v1, Constants.INF);
        Column col = Column.EMPTY;
        System.out.println(Double.POSITIVE_INFINITY);
    }
}
