import java.util.ArrayList;

/**El único objetivo de esta clase es comprobar la correcta sintaxis de las instrucciones.
 * @author Yahlunna
 *
 */
public class ListaInstrucciones {

	// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
	public ListaInstrucciones() {}
	
	public void getListaInstrucciones() {
		System.out.println("Carga:");
		System.out.println("LOAD op - Carga op en R0ACC.");														//Inmediato, directo, indirecto
		System.out.println("STORE op - Carga el contenido de R0ACC en Rop.");									//Directo, Indirecto
		System.out.println();
		
		System.out.println("Operaciones:");
		System.out.println("ADD op[] - Suma op[] a R0.");														//(puede ser vector) Inmediato, directo, indirecto
		System.out.println("SUB op[] - Resta op[] a R0.");														//(puede ser vector) Inmediato, directo, indirecto
		System.out.println("MUL op[] - Multiplica op[] a R0.");													//(puede ser vector) Inmediato, directo, indirecto
		System.out.println("DIV op[] - Divide op[] a R0.");														//(puede ser vector) Inmediato, directo, indirecto
		System.out.println();
		
		System.out.println("Lectura y escritura:");
		System.out.println("READ op - Lee el contenido de la cinta de entrada y lo carga en Rop.");				//Directo, indirecto
		System.out.println("WRITE op - Escribe op en la cinta de salida.");										//Inmediato, directo, indirecto
		System.out.println();
		
		System.out.println("Saltos:");	
		System.out.println("JUMP etiq - Cambia el valor del registro ID al que indique la etiqueta.");			//Etiqueta
		System.out.println("JZERO etiq - Si R0=0, cambia el valor del registro ID al que indique la etiqueta.");//Etiqueta
		System.out.println("JGTZ etiq - Si R0>0, cambia el valor del registro ID al que indique la etiqueta.");	//Etiqueta
		System.out.println();
		
		System.out.println("HALT - Finaliza el programa.");
		System.out.println();
	}
	
	// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
	
	public boolean tieneFormatoValido(String instruccion, ArrayList<Operando> operandos) throws Exception{

		instruccion= instruccion.toUpperCase();
		
		switch(instruccion){
		case "LOAD":
		case "WRITE":
			if(operandos.size()!= 1) {
				throw new Exception("¡" + instruccion + " no puede tener más de 1 operando!");
			}
			else if(operandos.get(0).isEsSaltoEtiqueta() || !operandos.get(0).isEsValor()) {
				throw new Exception("¡" + instruccion + " no soporta un salto de etiqueta! Su op debe ser un entero (int)");
			}
			break;
		case "READ":
		case "STORE":
			if(operandos.size()!= 1) {
				throw new Exception("¡" + instruccion + " no puede tener más de 1 operando!");
			}
			else if(operandos.get(0).isEsSaltoEtiqueta() || !operandos.get(0).isEsValor()) {
				throw new Exception("¡" + instruccion + " no soporta un salto de etiqueta! Su op debe ser un entero (int)");
			}
			else if(operandos.get(0).getDireccionamiento()==0) {
				throw new Exception("¡" + instruccion + " no puede tener una constante como operando! Debe ser DD(' ') o DI('*').");
			}
			break;
		case "ADD":
		case "SUB":
		case "MUL":
		case "MULT":
		case "DIV":
			for(Operando operando : operandos) {
				if(operando.esSaltoEtiqueta || !operando.isEsValor()) {
					throw new Exception("¡" + instruccion + " no puede ser un salto de etiqueta! (" + operando.toString() + ").Todos sus operandos deben ser (int).");
				}	
			}
			break;
		case "JUMP":
		case "JZERO":
		case "JGTZ":
			if(operandos.size()!= 1) {
				throw new Exception("¡" + instruccion + " no puede tener más de 1 operando!");
			}
			else if(!operandos.get(0).isEsSaltoEtiqueta() || operandos.get(0).isEsValor()) {
				throw new Exception("¡" + instruccion + " solo puede tener como parámetro un salto de etiqueta!");
			}
			break;
		case "HALT":
			if(operandos.size()!= 0) {
				System.out.println("[ADVERTENCIA]: La instrucción HALT no soporta operandos. Se ignorarán estos operandos a la hora de procesar la instrucción.");
			}
			break;
		default:
			throw new Exception("¡" + instruccion + " no es una instrucción válida!");
		}
		
//		switch(instruccion) {
//		case "READ":
//		case "WRITE":
//			if(operandos.get(0).getValor()==0) {
//				throw new Exception("¡" + instruccion + " no puede usar el acumulador (ACC-R0)!");
//			}
//		}
		return true;
	}
	
	
	
	
	
}
