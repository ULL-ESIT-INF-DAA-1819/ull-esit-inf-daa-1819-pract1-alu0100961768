
public class Registro {

	public int valor;
	public int rID;
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public int getrID() {
		return rID;
	}
	public void setrID(int rID) {
		this.rID = rID;
	}
	
	public Registro() {
		setValor(-1);
		setrID(-1);
	}
	
	public Registro(int valor, int rID) {
		setValor(valor);
		setrID(rID);
	}
	
	
}
