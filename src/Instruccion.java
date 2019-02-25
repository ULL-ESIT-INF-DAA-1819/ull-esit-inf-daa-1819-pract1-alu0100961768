
import java.util.*;


/** La clase instruccion guarda el nombre de la instruccion y todos sus parámetros.
 * @author Yahlunna
 *
 */
public class Instruccion {

	public String instruccion;
	public ArrayList<Operando> operandos = new ArrayList<Operando>();
	
	public Instruccion() throws Exception {
		throw new Exception("¡No puede existir una instrucción vacía!");
	}
	
	public Instruccion(String instruccionTxt) throws Exception{
		
		String[] instruccionTxtSeparado= instruccionTxt.split(" ");	
		
		setInstruccion(instruccionTxtSeparado[0]);
		for (int i = 1; i < instruccionTxtSeparado.length; i++) {
			setOperandos(instruccionTxtSeparado[i]);
		}
	}
	
	// GETTERS AND SETTERS: ///////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getInstruccion() {
		return instruccion;
	}

	public void setInstruccion(String instruccion) throws Exception{
		this.instruccion = instruccion;
	}

	public ArrayList<Operando> getOperandos() {
		return operandos;
	}

	public void setOperandos(ArrayList<Operando> operandos) {				//No es muy útil, pero lo intenta y estoy muy orgulloso de él por ello.
		this.operandos = operandos;
	}
	
	public void setOperandos(String operandoTxt) {
		
		if(operandoTxt.length()==0) {
			return;
		}
		else if((Character.isDigit(operandoTxt.charAt(0))) || operandoTxt.charAt(0)=='=' || operandoTxt.charAt(0)=='*') {
			int direccionamiento=1;
			int valor=0;						// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
		
			if(operandoTxt.charAt(0)=='=') {
				direccionamiento=0;
				operandoTxt= operandoTxt.replace("=", "");
			}
			else if(operandoTxt.charAt(0)=='*') {
				direccionamiento=2;
				operandoTxt= operandoTxt.replace("*", "");
			}
		
			valor= Integer.parseInt(operandoTxt);								//TODO: (Opcional): hacer un try catch para que devuelva un error personalizado en caso de no poder transformar el valor en int.
		
			getOperandos().add(new Operando(direccionamiento, valor));
		}
		else {
			getOperandos().add(new Operando(operandoTxt));
		}
	}
	
	// FUNCIONES: /////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		String instruccionTxt= getInstruccion() + " ";
		for(Operando operando : getOperandos()) {
			instruccionTxt= instruccionTxt + " " + operando.toString() + " ";
		}
		return instruccionTxt;
	}
	
	
	
	
	
	
}
