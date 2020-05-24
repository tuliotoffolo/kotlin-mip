//import mip.*;
//
//import java.util.*;
//
//public class TestJava {
//    public static void main(String[] args) {
//        Model model = new Model();
//        Var v1 = model.addVar("v1");
//        Var v2 = model.addVar("v2");
//
//        ArrayList<Object> vars = new ArrayList<Object>();
//        vars.add(v1);
//        vars.add(v2);
//        vars.add(-30);
//
//        model.addConstr(Constr.eq(new LinExpr(vars), 10));
//
//        model.optimize();
//        model.write("x.lp");
//    }
//}
