//test
public class Alu{

	public ProgramMemory programMemory;
	public DataMemory dataMemory;
	public ListaInstrucciones listaInstrucciones;
	public InputUnit inputUnit;
	public OutputUnit outputUnit;
	public int IDPointer;
	public boolean debugMode;
	public int numberOfInstructions;
	public boolean stop;
	
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
	
	public void runProgram() throws Exception{		
		
		if(isDebugMode()) getProgramMemory().showProgram();
		
		while(!isStop()) {
			if(isDebugMode()) System.out.println("Ejecutando la instrucción con ID: " + getIDPointer());
			executeInstruction();
			setIDPointer(getIDPointer()+1);
			setNumberOfInstructions(getNumberOfInstructions()+1);
			if(isDebugMode()) System.out.println("Se han ejecutado un total de " + Integer.toString(getNumberOfInstructions()) + " instrucciones.");
			if(isDebugMode()) getDataMemory().showDataMemory();
		}
		System.out.println("[Resultado final]: Se han ejecutado un total de " + Integer.toString(getNumberOfInstructions()) + " instrucciones.");
	}
	
	public void executeInstruction() throws Exception{
		
		Instruccion instruccionActual= getProgramMemory().getInstruccion(getIDPointer());
		if(isDebugMode()) System.out.println("Ejecutando una instrucción " + instruccionActual);
		
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
			System.out.println("[ERROR INESPERADO]: No existe la orden " + instruccionActual.getInstruccion() + " con ID:"+ getIDPointer() +"). Se forzará la detencion del programa usando HALT...");
			halt();
			break;
		}
	}
	
	// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
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
			throw new Exception("[ERROR INESPERADO]: no existe valor para la instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
		}
	}
	
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
			throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
		}
	}
	
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
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		//if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	
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
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		//if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	
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
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	
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
				throw new Exception("[ERROR INESPERADO]: Operando no valido para la instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", o su direccionamiento no es correcto");
			}
		}
		getDataMemory().setValorAcumulador(acumuladorAuxiliar);
		//if(isDebugMode()) System.out.println("R0ACC ahora vale " + getDataMemory().getValorAcumulador());
	}
	
	public void read(Instruccion instruccion) throws Exception {
		int valorDeEntrada= getInputUnit().getNextInputValue();
		if(isDebugMode()) System.out.println("El proximo input de entrada vale: " + valorDeEntrada);
		switch(instruccion.getOperandos().get(0).getDireccionamiento()) {			// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
		case 1:
			if(instruccion.getOperandos().get(0).getValor()==0) {
				throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", está tratando de acceder al acumulador!");
			}
			else {
				getDataMemory().setValorOfR(instruccion.getOperandos().get(0).getValor(), valorDeEntrada);
			}
			break;
		case 2:
			if(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor())==0) {
				throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", está tratando de acceder al acumulador!");
			}
			else {
				getDataMemory().setValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor(), valorDeEntrada);
			}
			break;
		default:
			throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", tiene un formato de operando o direccionamiento no válido!");
		}
	}
	
	public void write(Instruccion instruccion) throws Exception {
		switch(instruccion.getOperandos().get(0).getDireccionamiento()) {			// O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
		case 0:
			if(instruccion.getOperandos().get(0).getValor()==0) {
				throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", está tratando de acceder al acumulador!");
			}
			else {
				getOutputUnit().writeNextOutputValue(instruccion.getOperandos().get(0).getValor());
			}
		case 1:
			if(instruccion.getOperandos().get(0).getValor()==0) {
				throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", está tratando de acceder al acumulador!");
			}
			else {
				getOutputUnit().writeNextOutputValue(getDataMemory().getValorOfR(instruccion.getOperandos().get(0).getValor()));
			}
			break;
		case 2:
			if(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor())==0) {
				throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", está tratando de acceder al acumulador!");
			}
			else {
				getOutputUnit().writeNextOutputValue(getDataMemory().getValorOfRConDireccionamientoIndirecto(instruccion.getOperandos().get(0).getValor()));
			}
			break;
		default:
			throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", tiene un formato de operando o direccionamiento no válido!");
		}
	}
	
	public void jump(Instruccion instruccion) throws Exception {
		String etiqueta= instruccion.getOperandos().get(0).getSaltoEtiqueta();
		setIDPointer(getProgramMemory().direccionEtiqueta(etiqueta)); 
		if(getIDPointer()==-2) {
			throw new Exception("[ERROR INESPERADO]: ¡La instrucción "  + instruccion.getInstruccion() + " con ID:" + getIDPointer()+ ", no puede encontrar una etiqueta '" + etiqueta +"' a la que saltar!");
		}
		//if(isDebugMode()) System.out.println("El IDPointer ahora apunta a ID: " + getIDPointer());
	}
	
	public void jzero(Instruccion instruccion) throws Exception {
		if(getDataMemory().getValorAcumulador()==0) {
			jump(instruccion);
		}
	}
	
	public void jgtz(Instruccion instruccion) throws Exception {
		if(getDataMemory().getValorAcumulador()>0) {
			jump(instruccion);
		}
	}
	
	public void halt() {
		setStop(true);
	}

	public void checkeoInstruciones() throws Exception{
		for(Instruccion instruccion : getProgramMemory().getInstrucciones()) {
			if(!getListaInstrucciones().tieneFormatoValido(instruccion.getInstruccion(), instruccion.getOperandos())) {
				System.out.print("Este mensaje es de prueba y nunca debería verse. Si lo lees por terminal, es que la cosa está jodida");
			}		
		}		
	}

}
