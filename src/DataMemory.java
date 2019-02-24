import java.util.ArrayList;

public class DataMemory {

	public static Registro acumulador;						// = new Registro(0, 0);
	public static ArrayList<Registro> registros;			// = new ArrayList<Registro>();
	
	public DataMemory() {
		setAcumulador(new Registro(0, 0));
		setRegistros(new ArrayList<Registro>());
	}
	
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

	public int getValorOfRConDireccionamientoIndirecto(int rID) {
		int realRID= getValorOfR(rID);
		return getValorOfR(realRID);
	}
	public void setValorOfRConDireccionamientoIndirecto(int rID, int value) {
		int realRID= getValorOfR(rID);
		setValorOfR(realRID, value);
	}
	
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
