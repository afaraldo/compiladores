package AST;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import semantic.SymbolTable;
import semantic.UndefinedIdentifier;
import AST.Visitor.Visitor;

public class Identifier extends ASTNode {
  public String s;

  public Identifier(String as, int ln) { 
    super(ln);
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public String toString(){
    return s;
  }
}
