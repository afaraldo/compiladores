package AST.Visitor;

import com.sun.tools.javac.util.List;

import AST.*;
import semantic.Register;
import semantic.SymbolTable;

// Sample print visitor from MiniJava web site with small modifications for UW CSE.
// HP 10/11

public class InterfazVisitor implements Visitor{
	
	private SymbolTable symtab;
	private String var;
	private Boolean result;
	
	public InterfazVisitor(){
		symtab = new SymbolTable();
		result = true;
	}

	  // Display added for toy example language.  Not used in regular MiniJava
	  public void visit(Display n) {
	    ////System.out.print("display ");
	    n.e.accept(this);
	    ////System.out.print("");   
	  }
  
  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n) {
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
        n.cl.get(i).accept(this);
    }
    System.out.println(symtab.toString());
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(MainClass n) {
    ////System.out.print("class ");
    n.i1.accept(this);
    Register r1 = new Register("Class", null, false);
	symtab.add(n.i1.s, r1);
    ////System.out.println(" {");
    ////System.out.print("  public static void main (String [] ");
    n.i2.accept(this);
    Register r2 = new Register("StringArrayType", null, true);
	symtab.add(n.i2.s, r2);
    ////System.out.println(") {");
    ////System.out.print("    ");
    n.s.accept(this);
    ////System.out.println("  }");
    ////System.out.println("}");
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n) {
    ////System.out.print("class ");
    n.i.accept(this);
    Register r = new Register("Class", null, false);
	symtab.add(n.i.s, r);
    ////System.out.println(" { ");
    for ( int i = 0; i < n.vl.size(); i++ ) {
        ////System.out.print("  ");
        n.vl.get(i).accept(this);
        
        if ( i+1 < n.vl.size() ) {
        	//System.out.println(); 
        }
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        ////System.out.println();
        n.ml.get(i).accept(this);
    	
    	String method = n.ml.get(i).i.s + "(";
    	for (int j = 0; j < n.ml.get(i).fl.size(); j++){
    		n.ml.get(i).fl.get(j).t.accept(this);
    		method += this.var + " ";
    	}
    	method += ")";
    	symtab.lookup(n.i.s).methods.add( method );
    }
    ////System.out.println();
    ////System.out.println("}");
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclExtends n) {
    ////System.out.print("class ");
    n.i.accept(this);
    ////System.out.println(" extends ");
    n.j.accept(this);
    //System.out.println(" { ");
    for ( int i = 0; i < n.vl.size(); i++ ) {
        ////System.out.print("  ");
        n.vl.get(i).accept(this);
        if ( i+1 < n.vl.size() ) { 
        	//System.out.println();
        }
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        ////System.out.println();
        n.ml.get(i).accept(this);
    }
    ////System.out.println();
    ////System.out.println("}");
  }

  // Type t;
  // Identifier i;
  public void visit(VarDecl n) {
	  n.i.accept(this);
	  n.t.accept(this);
	  Register r = new Register(this.var, null, false);
	  symtab.add(n.i.s, r);
  }

  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public void visit(MethodDecl n) {
    //System.out.print("  public ");
	Register r3 = new Register("Method", null, false);
  	symtab.add(n.i.s, r3);
  	
    n.t.accept(this);
    symtab.lookup(n.i.s).returnedType = this.var;
    //System.out.print(" ");
    n.i.accept(this);
    //System.out.print(" (");
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.get(i).accept(this);
        if (i+1 < n.fl.size()) {
        	////System.out.print(", ");
    	}
    }
    //System.out.println(") { ");
    for ( int i = 0; i < n.vl.size(); i++ ) {
        ////System.out.print("    ");
        n.vl.get(i).accept(this);
        //System.out.println("");
    }
    for ( int i = 0; i < n.sl.size(); i++ ) {
        ////System.out.print("    ");
        n.sl.get(i).accept(this);
        if ( i < n.sl.size() ) {
        	//System.out.println("");
        }
    }
    ////System.out.print("    return ");
    n.e.accept(this);
    //System.out.println(";");
    ////System.out.print("  }");
  }

  // Type t;
  // Identifier i;
  public void visit(Formal n) {
    n.t.accept(this);
    Register r = new Register(this.var, null, true);
    //System.out.print(" ");
    n.i.accept(this);
   
	symtab.add(n.i.s, r);
  }

  public void visit(IntArrayType n) {
	  var = "IntArrayType";
    //System.out.print("int []");
  }

  public void visit(BooleanType n) {
	  var = "Boolean";
    //System.out.print("boolean");
  }

  public void visit(IntegerType n) {
	  var = "Integer";
    //System.out.print("int");
  }

  // String s;
  public void visit(IdentifierType n) {
    //System.out.print(n.s);
  }

  // StatementList sl;
  public void visit(Block n) {
    //System.out.println("{ ");
	symtab.push_scope();
	
    for ( int i = 0; i < n.sl.size(); i++ ) {
        //System.out.print("      ");
        n.sl.get(i).accept(this);
        //System.out.println();
    }
    symtab.pop_scope();
    //System.out.print("    } ");
  }

  // Exp e;
  // Statement s1,s2;
  public void visit(If n) {
    //System.out.print("if (");
    n.e.accept(this);
    //System.out.println(") ");
    //System.out.print("    ");
    n.s1.accept(this);
    //System.out.println();
    //System.out.print("    else ");
    n.s2.accept(this);
  }

  // Exp e;
  // Statement s;
  public void visit(While n) {
    //System.out.print("while (");
    n.e.accept(this);
    //System.out.print(") ");
    n.s.accept(this);
  }

  // Exp e;
  public void visit(Print n) {
    //System.out.print("//System.out.println(");
    n.e.accept(this);
    //System.out.print(");");
  }
  
  // Identifier i;
  // Exp e;
  public void visit(Assign n) {
    n.i.accept(this);
    //System.out.print(" = ");
    n.e.accept(this);
    //System.out.print(";");
  }

  // Identifier i;
  // Exp e1,e2;
  public void visit(ArrayAssign n) {
    n.i.accept(this);
    //System.out.print("[");
    n.e1.accept(this);
    //System.out.print("] = ");
    n.e2.accept(this);
    //System.out.print(";");
  }

  // Exp e1,e2;
  public void visit(And n) {
    //System.out.print("(");
    n.e1.accept(this);
    //System.out.print(" && ");
    n.e2.accept(this);
    //System.out.print(")");
  }

  // Exp e1,e2;
  public void visit(LessThan n) {
    //System.out.print("(");
    n.e1.accept(this);
    //System.out.print(" < ");
    n.e2.accept(this);
    //System.out.print(")");
  }

  // Exp e1,e2;
  public void visit(Plus n) {
    //System.out.print("(");
    n.e1.accept(this);
    //System.out.print(" + ");
    n.e2.accept(this);
    //System.out.print(")");
  }

  // Exp e1,e2;
  public void visit(Minus n) {
    //System.out.print("(");
    n.e1.accept(this);
    //System.out.print(" - ");
    n.e2.accept(this);
    //System.out.print(")");
  }

  // Exp e1,e2;
  public void visit(Times n) {
    //System.out.print("(");
    n.e1.accept(this);
    //System.out.print(" * ");
    n.e2.accept(this);
    //System.out.print(")");
  }

  // Exp e1,e2;
  public void visit(ArrayLookup n) {
    n.e1.accept(this);
    //System.out.print("[");
    n.e2.accept(this);
    //System.out.print("]");
  }

  // Exp e;
  public void visit(ArrayLength n) {
    n.e.accept(this);
    //System.out.print(".length");
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  public void visit(Call n) {
    n.e.accept(this);
    //System.out.print(".");
    n.i.accept(this);
    //System.out.print("(");
    for ( int i = 0; i < n.el.size(); i++ ) {
        n.el.get(i).accept(this);
        if ( i+1 < n.el.size() ) { 
        	//System.out.print(", ");
        }
    }
    //System.out.print(")");
  }

  // int i;
  public void visit(IntegerLiteral n) {
    //System.out.print(n.i);
  }

  public void visit(True n) {
    //System.out.print("true");
  }

  public void visit(False n) {
    //System.out.print("false");
  }

  // String s;
  public void visit(IdentifierExp n) {
    //System.out.print(n.s);
  }

  public void visit(This n) {
    //System.out.print("this");
  }

  // Exp e;
  public void visit(NewArray n) {
    //System.out.print("new int [");
    n.e.accept(this);
    //System.out.print("]");
  }

  // Identifier i;
  public void visit(NewObject n) {
    //System.out.print("new ");
    //System.out.print(n.i.s);
    //System.out.print("()");
  }

  // Exp e;
  public void visit(Not n) {
    //System.out.print("!");
    n.e.accept(this);
  }

  // String s;
  public void visit(Identifier n) {
	  var = n.s;
    //System.out.print(n.s);
  }
}
