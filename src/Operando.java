
/** Los operandos peuden ser EsValor o EsSaltoEtiqueta, y tienen
 * un boolean asignado para saber de que tipo son. (Podría
 * tener solo 1, que sería mas seguro, y modificar losg etters y loss etters jugando con el not.
 * pero soy gilipollas.
 * 
 * Si es un EsValor, guardará un valor int y una constante que indicará su direccionameinto
 *  O->(=)Const; 1->( )Dir Directo; 2->(*)Dir Indirecto.
 * En otro caso guardarña un String que contendrá el nombre del salto de etiqueta.
 * @author Yahlunna
 */
public class Operando {

	public int direccionamiento;						
	public int valor;
	
	public String saltoEtiqueta;
	
	public boolean esValor;
	public boolean esSaltoEtiqueta;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operando() {
		setDireccionamiento(-1);
		setValor(-1);
		setSaltoEtiqueta(null);
		setEsValor(false);
		setEsSaltoEtiqueta(false);
	}
	public Operando(int direccionamiento, int valor) {	//Constructor para un valor
		setDireccionamiento(direccionamiento);
		setValor(valor);
		setSaltoEtiqueta(null);
		setEsValor(true);
		setEsSaltoEtiqueta(false);
	}
	public Operando(String saltoEtiqueta) {				//Constructor para un saltoetiqueta
		setDireccionamiento(-1);
		setValor(-1);
		setSaltoEtiqueta(saltoEtiqueta);
		setEsValor(false);
		setEsSaltoEtiqueta(true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getDireccionamiento() {
		return direccionamiento;
	}
	public void setDireccionamiento(int direccionamiento) {
		this.direccionamiento = direccionamiento;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}	
	public String getSaltoEtiqueta() {
		return saltoEtiqueta;
	}
	public void setSaltoEtiqueta(String saltoEtiqueta) {
		this.saltoEtiqueta = saltoEtiqueta;
	}
	public boolean isEsValor() {
		return esValor;
	}
	public void setEsValor(boolean esValor) {
		this.esValor = esValor;
		this.esSaltoEtiqueta = !esValor;
	}
	public boolean isEsSaltoEtiqueta() {
		return esSaltoEtiqueta;
	}
	public void setEsSaltoEtiqueta(boolean esSaltoEtiqueta) {
		this.esSaltoEtiqueta = esSaltoEtiqueta;
		this.esValor = !esSaltoEtiqueta;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
	    if(isEsValor()) {
		String direccionamientoTxt;
			switch(getDireccionamiento()){
			case 0:
				direccionamientoTxt= "=";
				break;
			case 1:
				direccionamientoTxt= "";
				break;
			case 2:
				direccionamientoTxt= "*";
				break;
			default:
				direccionamientoTxt= "<?>";
			}
			return direccionamientoTxt + String.valueOf(getValor());
	    }
	    else {
	    	return saltoEtiqueta;
	    }
	}
}
