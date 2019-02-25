import java.util.ArrayList;

/**
 * @author Yahlunna
 * Registros de nuestra RAM
 * @acumulador es R0
 * El resto de registros se guardan en @registros, un array list de @Registro
 * Al principio no hay registros en el array list, cuando se van tratando de acceder se guardan en el ArrayList
 * Nunca ordeno el arraylist, mitad por pereza, mitad porque no merece la pena más aya de mostrarlo bonito por pantalla.
 */
public class DataMemory {

	public static Registro acumulador;						// = new Registro(0, 0);
	public static ArrayList<Registro> registros;			// = new ArrayList<Registro>();
	
	/**
	 * Constructor por defecto. R= hay registros y no se han accedido a más registros
	 */
	public DataMemory() {
		setAcumulador(new Registro(0, 0));
		setRegistros(new ArrayList<Registro>());
	}
	
	// GETTERS Y SETTERS:
	
	public static Registro getAcumulador() {
		return acumulador;
	}
	public static void setAcumulador(Registro acumulador) {
		DataMemory.acumulador = acumulador;
	}
	public static ArrayList<Registro> getRegistros() {
		return registros;
	}
	public static void setRegistros(ArrayList<Registro> registros) {
		DataMemory.registros = registros;
	}
	
	public int getValorAcumulador() {
		return acumulador.getValor();
	}
	public void setValorAcumulador(int valor) {
		acumulador.setValor(valor);
	}
	
	/**
	 * Devuelve el valor guardado en "R(X)"
	 * @param rID
	 * @return
	 */
	public int getValorOfR(int rID) {	
		if(existeR(rID)) {
			if(rID==0) {
				return getAcumulador().getValor();
			}
			else {
				for(Registro registro : getRegistros()) {
					if(registro.getrID()==rID) {
						return registro.getValor();
					}
				}
			}
		}
		else {
			getRegistros().add(new Registro(rID, 0));
		}
		return 0;
	}	
	/**Reescribe el valor de "R(X," al parametro asignado.
	 * @param rID
	 * @param value
	 */
	public void setValorOfR(int rID, int value) {
		if(existeR(rID)) {
			if(rID==0) {
				getAcumulador().setValor(value);
			}
			else {
				for(Registro registro : getRegistros()) {
					if(registro.getrID()==rID) {
						registro.setValor(value);
					}
				}
			}
		}
		else {
			getRegistros().add(new Registro(value, rID));
		}
	}
	
	/** Comprueba si existe el registro "R(X)"
	 * @param rID
	 * @return
	 */
	public boolean existeR(int rID) {
		if(rID==0) {
			return true;
		}
		for(Registro registro : getRegistros()) {
			if(registro.getrID()==rID) {
				return true;
			}
		}
		return false;
	}

	/**Accede a "R(operando)", guarda su valor y con el accede a "R(valor)".
	 * Devuelve "R(valor)"
	 * @param rID
	 * @return
	 */
	public int getValorOfRConDireccionamientoIndirecto(int rID) {
		int realRID= getValorOfR(rID);
		return getValorOfR(realRID);
	}
	/**Accede a "R(operando,", guarda su valor y con el accede a "R(valor)".
	 * Reescribe R(valor) por el value especificado. 
	 * @param rID
	 * @param value
	 */
	public void setValorOfRConDireccionamientoIndirecto(int rID, int value) {
		int realRID= getValorOfR(rID);
		setValorOfR(realRID, value);
	}
	
	/**
	 * Muestra por pantalla el estado acutal de los registros (Me daba mucha pereza hacer un toString());
	 */
	public void showDataMemory(){
		
		System.out.println("Registros:");
		System.out.println("---------------------------------------------------------------");
		System.out.println("R0ACC= " + String.valueOf(getValorAcumulador()));
		for(Registro registro : getRegistros()) {
			System.out.println("R" + String.valueOf(registro.getrID()) + "= " + registro.getValor());
		}
		System.out.println("---------------------------------------------------------------");
	}
}
