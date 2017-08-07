package AST;
import semantic.SemanticError;
import semantic.SymbolTable;
import AST.Visitor.Visitor;

public class Plus extends Exp {
  public Exp e1,e2;
  
  public Plus(Exp ae1, Exp ae2, int ln) { 
    super(ln);
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  
  public Type typeCheck(SymbolTable s) {
	  Type t1 = e1.typeCheck(s),
	  t2 = e2.typeCheck(s);
	  if (t1 instanceof IntegerType && t2 instanceof IntegerType)
		  return new IntegerType(1);
	  else
		  return null;
  }
}
