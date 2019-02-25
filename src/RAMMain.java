
/**
 * 
 * @author Yahlunna
 *	Main para ejecutar el programa, debe recibir como parametros
 *  1) Instrucciones del programa
 *  2) Cinta de entrada
 *  3) CInta de salida
 *  4) Si se desea activar el Debug Mode.
 *  
 */
public class RAMMain {

	public static void main(String[] args) {
		
		try {
			boolean debugMode= Boolean.parseBoolean(args[3]);				//Guarda el modo debug
			Alu alu= new Alu(args[0], args[1], args[2], debugMode);			//Crea la ALY y guarda el programa en la memoria del programa
			alu.checkeoInstruciones();										//Comptueba que las instrucciones estén bien formateadas
			alu.runProgram();												//Ejecuta el programa
		}
		catch (Exception e){
			System.out.println("Excepción: " + e.getMessage());
		}
	}

}


//LEGADO:
// TODO Auto-generated method stub
//Instruccion instrucciontest= new Instruccion("Read =8 *3");
//try {
//ProgramMemory programMemory= new ProgramMemory("test6.ram");
//ListaInstrucciones listaInstruciones= new ListaInstrucciones();
//DataMemory dataMemory= new DataMemory();
//InputUnit inputUnit= new InputUnit("entrada.txt");
//OutputUnit outputUnit= new OutputUnit("salida.txt");
//
//for(Instruccion instruccion : programMemory.getInstrucciones()) {
//listaInstruciones.tieneFormatoValido(instruccion.getInstruccion(), instruccion.getOperandos());
//}
//
//programMemory.showProgram();
//
//}
//catch (Exception e){
//System.out.println("Excepción: " + e.getMessage());
//}