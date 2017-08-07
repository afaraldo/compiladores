package AST;
import semantic.SymbolTable;
import AST.Visitor.Visitor;

public abstract class Exp extends ASTNode {
    public Exp(int ln) {
        super(ln);
    }
    public abstract void accept(Visitor v);
	public Type typeCheck(SymbolTable s) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
