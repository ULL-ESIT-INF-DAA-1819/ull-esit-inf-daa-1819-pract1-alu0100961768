//test
/**
 * @author Yahlunna
 * La clase Alu va leyendo las instrucciones de la Memoria del programa, 
 * y ejectuando sus instucciones leyendo o escribiendo en la cinta de 
 * entrada y salida, o modificando los valores de los registros
 *  cuando sea necesario. 
 */
public class Alu{

	public ProgramMemory programMemory;					//Programa cargado en memoria
	public DataMemory dataMemory;						//Memoria (registros)
	public ListaInstrucciones listaInstrucciones;		//Instrucciones que entiende el ALU
	public InputUnit inputUnit;							//CInta de entrada
	public OutputUnit outputUnit;						//Cinta de salida
	public int IDPointer;								//Puntero del alu que indica la siguiente instrucci�n a ejecutar
	public boolean debugMode;							//Si es true, se muestran paso a paso el valor de los registros.
	public int numberOfInstructions;					//Numero de instrucciones ejecutadas hasta el momento
	public boolean stop;								//Cuando sea true, se parar� la ejecuci�n del programa.
	
	/**Constructor de la Unidad Aritmetico Logica
	 * @param programaTxt
	 * @param ficheroEntradaTxt
	 * @param ficheroSalidaTxt
	 * @param esDebugMode
	 * @throws Exception
	 */
	public Alu(String programaTxt, String ficheroEntradaTxt, String ficheroSalidaTxt, boolean esDebugMode) throws Exception{
		setProgramMemory(new ProgramMemory(programaTxt));
		setDataMemory(new DataMemory());
		setListaInstrucciones(new ListaInstrucciones());
		setInputUnit(new InputUnit(ficheroEntradaTxt));
		setOutputUnit(new OutputUnit(ficheroSalidaTxt));
		setIDPointer(0);
		setDebugMode(esDebugMode);
		setStop(false);
	}
	
	// GETTERS Y SETTERS:
	
	public ProgramMemory getProgramMemory() {
		return programMemory;
	}
	public void setProgramMemory(ProgramMemory programMemory) {
		this.programMemory = programMemory;
	}
	public DataMemory getDataMemory() {
		return dataMemory;
	}
	public void setDataMemory(DataMemory dataMemory) {
		this.dataMemory = dataMemory;
	}
	public ListaInstrucciones getListaInstrucciones() {
		return listaInstrucciones;
	}
	public void setListaInstrucciones(ListaInstrucciones listaInstrucciones) {
		this.listaInstrucciones = listaInstrucciones;
	}
	public InputUnit getInputUnit() {
		return inputUnit;
	}
	public void setInputUnit(InputUnit inputUnit) {
		this.inputUnit = inputUnit;
	}
	public OutputUnit getOutputUnit() {
		return outputUnit;
	}
	public void setOutputUnit(OutputUnit outputUnit) {
		this.outputUnit = outputUnit;
	}
	public boolean isDebugMode() {
		return debugMode;
	}
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
	public int getIDPointer() {
		return IDPointer;
	}
	public void setIDPointer(int iDPointer) {
		IDPointer = iDPointer;
	}
	public int getNumberOfInstructions() {
		return numberOfInstructions;
	}
	public void setNumberOfInstructions(int numberOfInstructions) {
		this.numberOfInstructions = numberOfInstructions;
	}
	public boolean isStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	
	/**Ejecuta instruccion por instrucci�n el programa cargado en memoria hasta que se encuentra un halt
	 * o una isntrucci�n nula (que trata como un halt).
	 * @throws Exception
	 */
	public void runProgram() throws Exception{		
		
		if(isDebugMode()) getProgramMemory().showProgram();
		
		while(!isStop()) {
			if(isDebugMode()) System.out.println("Ejecutando la instrucci�n con ID: " + getIDPointer());
			executeInstruction();
			setIDPointer(getIDPointer()+1);
			setNumberOfInstructions(getNumberOfInstructions()+1);
			if(isDebugMode()) System.out.println("Se han ejecutado un total de " + Integer.toString(getNumberOfInstructions()) + " instrucciones.");
			if(isDebugMode()) getDataMemory().showDataMemory();
			if(isDebugMode()) 
			System.in.read();
		}
		System.out.println("[Resultado final]: Se han ejecutado un total de " + Integer.toString(getNumberOfInstructions()) + " instrucciones.");
	}
	
	/** Detecta la siguiente instrucci�n a ejecutar y llama a la funci�n necesaria
	 *  para ejecutarla.
	 * @throws Exception
	 */
	public void executeInstruction() throws Exception{
		
		Instruccion instruccionActual= getProgramMemory().getInstruccion(getIDPointer());
		if(isDebugMode()) System.out.println("Ejecutando una instrucci�n " + instruccionActual);
		
		switch(instruccionActual.getInstruccion().toUpperCase()) {
		case "LOAD":
			load(instruccionActual);
			break;
		case "STORE":
			store(instruccionActual);
			break;
		case "ADD":
			add(instruccionActual);
			break;
		case "SUB":
			sub(instruccionActual);
			break;
		case "MUL":
		case "MULT":
			mul(instruccionActual);
			break;
		case "DIV":
			div(instruccionActual);
			break;
		case "READ":
			read(instruccionActual);
			break;
		case "WRITE":
			write(instruccionActual);
			break;
		case "JUMP":
			jump(instruccionActual);
			break;
		case "JZERO":
			jzero(instruccionActual);
			break;
		case "JGTZ":
			jgtz(instruccionActual);
			break;
		case "HALT":
			halt();
			break;
		default:
			System.out.println("[ERROR INESPERADO]: No existe la orden " + instruccionActual.getInstruccion() + " con ID:"+ getIDPointer() +"). Se forzar� la detencion del programa usando HALT...");
			halt();
			break;
		}
	}
	
	/** Carga el operando que se le pasa por par�metro en el acumulador
	 * @param Direccionamiento= O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
	 */
	public void load(Instruccion instruccion) throws Exception {
		switch(instruccion.getOperandos().get(0).getDireccionamiento()) {
		case 0:
			getDataMemory().setValorAcumulador(instruccion.getOperandos().get(0).getValor());
			break;
		case 1:
			getDataMemory().setValorAcumulador(getDataMemory().getValorOfR(instruccion.getOperandos().get(0).getValor()));
			break;
		case 2:
			getDataMemory().setValorAcumulador(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor()));
			break;
		default:
			throw new Exception("[ERROR INESPERADO]: no existe valor para la instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
		}
	}
	
	/** Guarda en R(operando) el valor del acumulador.
	 * @param instruccion
	 */
	public void store(Instruccion instruccion) throws Exception{
		int acumuladorAuxiliar= getDataMemory().getValorAcumulador();
		
		switch(instruccion.getOperandos().get(0).getDireccionamiento()) {
		case 1:
			getDataMemory().setValorOfR(instruccion.getOperandos().get(0).getValor(), acumuladorAuxiliar);
			//if(isDebugMode()) System.out.println("R" + instruccion.getOperandos().get(0).getValor() + " ahora vale " + getDataMemory().getValorOfR(instruccion.getOperandos().get(0).getValor()));
			break;
		case 2:
			getDataMemory().setValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor(), acumuladorAuxiliar);
			//if(isDebugMode()) System.out.println("R" + getDataMemory().getValorOfR(instruccion.getOperandos().get(0).getValor()) + " ahora vale " + getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor()));
			break;
		default:
			throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
		}
	}
	
	/** Suma todos sus operandos al acumulador.
	 * @param instruccion
	 */
	public void add(Instruccion instruccion) throws Exception{
		int acumuladorAuxiliar= getDataMemory().getValorAcumulador();
		for(Operando operando : instruccion.getOperandos()) {
			switch(operando.getDireccionamiento()) {
			case 0:
				acumuladorAuxiliar+= operando.getValor();
				break;
			case 1:
				acumuladorAuxiliar+= getDataMemory().getValorOfR(operando.getValor());
				break;
			case 2:
				acumuladorAuxiliar+= getDataMemory().getValorOfRConDireccionamientoIndirecto(operando.getValor());
				break;
			default:
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		//if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	/** Resta todos sus operandos al acumulador.
	 * @param instruccion
	 */
	public void sub(Instruccion instruccion) throws Exception{
		int acumuladorAuxiliar= getDataMemory().getValorAcumulador();
		for(Operando operando : instruccion.getOperandos()) {
			switch(operando.getDireccionamiento()) {
			case 0:
				acumuladorAuxiliar-= operando.getValor();
				break;
			case 1:
				acumuladorAuxiliar-= getDataMemory().getValorOfR(operando.getValor());
				break;
			case 2:
				acumuladorAuxiliar-= getDataMemory().getValorOfRConDireccionamientoIndirecto(operando.getValor());
				break;
			default:
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		//if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	/** Multiplica todos sus operandos al acumulador.
	 * @param instruccion
	 */
	public void mul(Instruccion instruccion) throws Exception{
		int acumuladorAuxiliar= getDataMemory().getValorAcumulador();
		for(Operando operando : instruccion.getOperandos()) {
			switch(operando.getDireccionamiento()) {
			case 0:
				acumuladorAuxiliar*= operando.getValor();
				break;
			case 1:
				acumuladorAuxiliar*= getDataMemory().getValorOfR(operando.getValor());
				break;
			case 2:
				acumuladorAuxiliar*= getDataMemory().getValorOfRConDireccionamientoIndirecto(operando.getValor());
				break;
			default:
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	/** Divide todos sus operandos al acumulador.
	 * @param instruccion
	 */
	public void div(Instruccion instruccion) throws Exception{
		int acumuladorAuxiliar= getDataMemory().getValorAcumulador();
		for(Operando operando : instruccion.getOperandos()) {
			switch(operando.getDireccionamiento()) {
			case 0:
				acumuladorAuxiliar/= operando.getValor();
				break;
			case 1:
				acumuladorAuxiliar/= getDataMemory().getValorOfR(operando.getValor());
				break;
			case 2:
				acumuladorAuxiliar/= getDataMemory().getValorOfRConDireccionamientoIndirecto(operando.getValor());
				break;
			default:
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		//if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	
	/** Guarda en R(operando) el proximo valor en la cinta de entrada
	 * @param instruccion
	 */
	public void read(Instruccion instruccion) throws Exception {
		int valorDeEntrada= getInputUnit().getNextInputValue();
		if(isDebugMode()) System.out.println("El proximo input de entrada vale: " + valorDeEntrada);
		switch(instruccion.getOperandos().get(0).getDireccionamiento()) {			// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
		case 1:
			if(instruccion.getOperandos().get(0).getValor()==0) {
				throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", est� tratando de acceder al acumulador!");
			}
			else {
				getDataMemory().setValorOfR(instruccion.getOperandos().get(0).getValor(), valorDeEntrada);
			}
			break;
		case 2:
			if(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor())==0) {
				throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", est� tratando de acceder al acumulador!");
			}
			else {
				getDataMemory().setValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor(), valorDeEntrada);
			}
			break;
		default:
			throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", tiene un formato de operando o direccionamiento no v�lido!");
		}
	}
	
	/** Escribe en la cinta de salida el valor del operando. No puede acceder al acumulador.
	 * @param instruccion
	 */
	public void write(Instruccion instruccion) throws Exception {
		switch(instruccion.getOperandos().get(0).getDireccionamiento()) {			// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
		case 0:
			//if(instruccion.getOperandos().get(0).getValor()==0) {	//Esto esta mal y no hace falta, y de hecho puede dar problemas usando write =0. Arreglar si lo considero necesario (solo tengo que borrarlo).
			//	throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", est� tratando de acceder al acumulador!");
			//}
			//else {
				getOutputUnit().writeNextOutputValue(instruccion.getOperandos().get(0).getValor());
			//}
		case 1:
			if(instruccion.getOperandos().get(0).getValor()==0) {
				throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", est� tratando de acceder al acumulador!");
			}
			else {
				getOutputUnit().writeNextOutputValue(getDataMemory().getValorOfR(instruccion.getOperandos().get(0).getValor()));
			}
			break;
		case 2:
			if(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor())==0) {
				throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", est� tratando de acceder al acumulador!");
			}
			else {
				getOutputUnit().writeNextOutputValue(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor()));
			}
			break;
		default:
			throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", tiene un formato de operando o direccionamiento no v�lido!");
		}
	}
	
	/**Edita el valor de ID seg�n el valor id que tenga registrada la etiqueta que se le pasa por par�metro.
	 * @param instruccion
	 */
	public void jump(Instruccion instruccion) throws Exception {
		String etiqueta= instruccion.getOperandos().get(0).getSaltoEtiqueta();
		setIDPointer(getProgramMemory().direccionEtiqueta(etiqueta)); 
		if(getIDPointer()==-2) {
			throw new Exception("[ERROR INESPERADO]: �La instrucci�n "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", no puede encontrar una etiqueta '" + etiqueta +"' a la que saltar!");
		}
		//if(isDebugMode()) System.out.println("El IDPointer ahora apunta a ID: " + getIDPointer());
	}
	
	/**Llama a @jump si el acumulador vale 0
	 * @param instruccion
	 */
	public void jzero(Instruccion instruccion) throws Exception {
		if(getDataMemory().getValorAcumulador()==0) {
			jump(instruccion);
		}
	}
	/**Llama a @jump si el acumulador es mayor que 0
	 * @param instruccion
	 */
	public void jgtz(Instruccion instruccion) throws Exception {
		if(getDataMemory().getValorAcumulador()>0) {
			jump(instruccion);
		}
	}
	
	/**
	 * Finaliza el programa.
	 */
	public void halt() {
		setStop(true);
	}

	/**Comprueba que las instrucciones en la memoria del programa tengan un formato adecuado.
	 * @throws Exception
	 */
	public void checkeoInstruciones() throws Exception{
		for(Instruccion instruccion : getProgramMemory().getInstrucciones()) {
			if(!getListaInstrucciones().tieneFormatoValido(instruccion.getInstruccion(), instruccion.getOperandos())) {
				System.out.print("Este mensaje es de prueba y nunca deber�a verse. Si lo lees por terminal, es que la cosa est� jodida");
			}		
		}		
	}

}
