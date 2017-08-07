package semantic;
import java.util.HashMap;
import java.util.Stack;

import AST.IntegerType;
import AST.Type;

public class SymbolTable {
	
	private Stack<HashMap<String, Register>> stack;
	
	public SymbolTable(){
		stack = new Stack<HashMap<String, Register>>();
		stack.push(new HashMap<String, Register>());
	}
	
	public Register lookup(String id) {
		return stack.peek().get(id);
	}
	
	public void add(String id, Register binding){
		stack.peek().put(id, binding);
	}

	// Push scope: Enter a new scope.
	public void push_scope(){
		
		stack.push((HashMap<String, Register>) stack.peek().clone());
	}
	
	//Pop scope: Leave a scope, discarding all declarations in it.
	public void pop_scope(){
		stack.pop();
	}
	
	//Insert symbol: Add a new entry to the current scope.
	public void insert_symbol(String identifier, Object obj){
		//stack.peek().put(identifier, obj);
	}
	
	//Lookup symbol: Find what a name corresponds to.
	public Type lookup_symbol(String identifier){
		stack.peek().get(identifier);
		return new IntegerType(1);
	}
	
	/** Gets the string representation of the symbol table.  
    *
    * @return the string rep
    * */
	public String toString() {
		String res = "";
		// I break the abstraction here a bit by knowing that stack is 
		// really a vector...
		System.out.println("{");
		while (!stack.isEmpty()){
			HashMap<String,Register> pila = stack.pop();
			System.out.println("{");
			for(String currentKey : pila.keySet()){
				System.out.println(currentKey + " : " + pila.get(currentKey));
			}
			System.out.println("}");
		}
		System.out.println("}");
		return res;
	}
}