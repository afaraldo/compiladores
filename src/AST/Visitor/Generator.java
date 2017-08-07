package AST.Visitor;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import AST.*;

// Sample print visitor from MiniJava web site with small modifications for UW CSE.
// HP 10/11

public class Generator implements Visitor {
	String jasminCode = "";
	String filename = "a";

  // Display added for toy example language.  Not used in regular MiniJava
  public void visit(Display n) {
    System.out.print("display ");
    n.e.accept(this);
    System.out.print(";");
  }
  
  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n) {
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
        System.out.println();
        n.cl.get(i).accept(this);
    }
    filename = n.m.i1.toString();
    toFile();
    System.out.println(jasminCode);
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(MainClass n) {
	jasminCode += ".class public " + n.i1.toString();
    n.i1.accept(this);
    //jasminCode += "\n\t; default constructor\n";
    jasminCode += "\n\t.super java/lang/Object\n";
    jasminCode += "\t.method public <init>()V\n";
    jasminCode += "\taload_0 ; push this\n";
    jasminCode += "\tinvokespecial java/lang/Object/<init>()V ; call super\n";
    jasminCode += "\treturn\n";
    jasminCode += ".end method\n";
    jasminCode += ".method public static main([Ljava/lang/String;)V\n";
    jasminCode += "\t; allocate stack big enough to hold 2 items\n";
    jasminCode += "\t.limit stack 2\n";
    n.i2.accept(this);
    n.s.accept(this);
    jasminCode += "\treturn\n";
    
    
    jasminCode += ".end method";
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n) {
	jasminCode += "\n.class public " + n.i.toString();
	jasminCode += "\n\t.super java/lang/Object\n";
	jasminCode += "\t.method public <init>()V\n";
	jasminCode += "\taload_0 ; push this\n";
	jasminCode += "\tinvokespecial java/lang/Object/<init>()V ; call super\n";
	jasminCode += "\treturn\n";
	jasminCode += ".end method\n";
    n.i.accept(this);

    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.get(i).accept(this);
    }
    
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.get(i).accept(this);
    }

  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclExtends n) {  
	n.i.accept(this);
    jasminCode += "\n.class public " + n.i.toString();
	jasminCode += "\n\t.super " + n.j.toString() + "\n";
	jasminCode += "\t.method public <init>()V\n";
	jasminCode += "\taload_0 ; push this\n";
	jasminCode += "\tinvokespecial" +  n.j.toString() + "/<init>()V ; call super\n";
	jasminCode += "\treturn\n";
	jasminCode += ".end method\n";
    n.j.accept(this);
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.get(i).accept(this);
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.get(i).accept(this);
    }
  }

  // Type t;
  // Identifier i;
  public void visit(VarDecl n) {
    n.i.accept(this);
    jasminCode += ".field private " + n.i.toString();
    n.t.accept(this);
  }

  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public void visit(MethodDecl n) {
    //System.out.print("  public ");
    n.t.accept(this);
    //System.out.print(" ");
    n.i.accept(this);
    //System.out.print(" (");
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.get(i).accept(this);
    }
    //System.out.println(") { ");
    for ( int i = 0; i < n.vl.size(); i++ ) {
        //System.out.print("    ");
        n.vl.get(i).accept(this);
        //System.out.println("");
    }
    for ( int i = 0; i < n.sl.size(); i++ ) {
        //System.out.print("    ");
        n.sl.get(i).accept(this);
        //if ( i < n.sl.size() ) { System.out.println(""); }
    }
    //System.out.print("    return ");
    n.e.accept(this);
    //System.out.println(";");
    //System.out.print("  }");
  }

  // Type t;
  // Identifier i;
  public void visit(Formal n) {
    n.t.accept(this);
    //System.out.print(" ");
    n.i.accept(this);
  }

  public void visit(IntArrayType n) {
    //System.out.print("int []");
  }

  public void visit(BooleanType n) {
	jasminCode += " B\n";
    //System.out.print("boolean");
  }

  public void visit(IntegerType n) {
	jasminCode += " I\n";
    //System.out.print("int");
  }

  // String s;
  public void visit(IdentifierType n) {
	jasminCode += " " + n.s + "\n";
    //System.out.print(n.s);
  }

  // StatementList sl;
  public void visit(Block n) {
    //System.out.println("{ ");
    for ( int i = 0; i < n.sl.size(); i++ ) {
        //System.out.print("      ");
        n.sl.get(i).accept(this);
        //System.out.println();
    }
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
	jasminCode += "\t; push java.lang.System.out (type PrintStream)\n";
	jasminCode += "\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n";
	jasminCode += "\t; push string to be printed\n";
	n.e.accept(this);
	jasminCode += "\t; invoke println\n";
	jasminCode += "\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n";
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
        //if ( i+1 < n.el.size() ) { System.out.print(", "); }
    }
    //System.out.print(")");
  }

  // int i;
  public void visit(IntegerLiteral n) {
	  jasminCode += "\tldc \"" + n.i + "\"\n";
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
	  //filename =  n.s;
  }
  
  public void toFile(){
	  try{
		  FileWriter fw = new FileWriter(filename.concat(".j"));
		  BufferedWriter bw = new BufferedWriter(fw);
		  bw.write(jasminCode);
		  bw.close();
		} catch (IOException e) {
		   System.out.println(e.getStackTrace());
		}
  }
}
