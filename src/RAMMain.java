
public class RAMMain {

	public static void main(String[] args) {
		
		try {
			boolean debugMode= Boolean.parseBoolean(args[3]);
			Alu alu= new Alu(args[0], args[1], args[2], debugMode);
			alu.checkeoInstruciones();
			alu.runProgram();
		}
		catch (Exception e){
		System.out.println("Excepción: " + e.getMessage());
		}
		
		
		// TODO Auto-generated method stub
			//Instruccion instrucciontest= new Instruccion("Read =8 *3");
//		try {
//		ProgramMemory programMemory= new ProgramMemory("test6.ram");
//		ListaInstrucciones listaInstruciones= new ListaInstrucciones();
//		DataMemory dataMemory= new DataMemory();
//		InputUnit inputUnit= new InputUnit("entrada.txt");
//		OutputUnit outputUnit= new OutputUnit("salida.txt");
//		
//		for(Instruccion instruccion : programMemory.getInstrucciones()) {
//			listaInstruciones.tieneFormatoValido(instruccion.getInstruccion(), instruccion.getOperandos());
//		}
//		
//		programMemory.showProgram();
//		
//		}
//		catch (Exception e){
//			System.out.println("Excepción: " + e.getMessage());
//		}
	}

}
