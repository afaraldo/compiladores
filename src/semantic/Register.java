package semantic;

import java.util.ArrayList;

import com.sun.tools.javac.util.List;

public class Register {
	public String type;
	public String returnedType;
	public ArrayList<String> methods;
	public ArrayList<String> paramsType;
	public Boolean init;
	
	public Register(String type, String returnedType, Boolean init){
		this.type = type;
		this.returnedType = returnedType;
		this.methods = new ArrayList<String>();
		this.paramsType = new ArrayList<String>();
		this.init = init;
	}
	
	public String toString(){
		return	"[type: " + type + 
				", returnedType: " + returnedType +
				", methods: " + methods +
				", paramsType: " + paramsType +
				", init: "  + init + "]";
	}
}