
public class Etiqueta {
	
	public String nombre;
	public int PunteroID;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Etiqueta() {
		setNombre(" ");
		setPunteroID(0);
	}
	public Etiqueta(String nombre, int punteroID) {
		setNombre(nombre);
		setPunteroID(punteroID);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPunteroID() {
		return PunteroID;
	}
	public void setPunteroID(int punteroID) {
		PunteroID = punteroID;
	}
	public String toString() {
		return getNombre() + " ID: " + String.valueOf(getPunteroID());
	}
}
