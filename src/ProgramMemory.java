
import java.io.*;
import java.util.*;

public class ProgramMemory {
	
	public static ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
	public static ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ProgramMemory(String rutaFicheroPrograma) throws Exception{
		
		int punteroID=0;
		FileReader FicheroPrograma= new FileReader(rutaFicheroPrograma);
		BufferedReader bufferedReader= new BufferedReader(FicheroPrograma);
		String instruccion;
		
		while((instruccion= bufferedReader.readLine())!= null) {
			instruccion= instruccion.trim();						      //Elimina los espacios/tabulados por delante y detrás de la línea.
			if(!instruccion.equals("") && instruccion.charAt(0)!='#') {	  //Si no es un comentario o una línea en blanco
				if(contieneEtiquetas(instruccion)) {
					String etiquetaEInstruccion[]= instruccion.split(":");
					if(etiquetaEInstruccion.length > 2) {
						bufferedReader.close();
						throw new Exception("¡No puede existir más de una etiqueta por instrucción!");
					}
					//for (int i = 0; i < etiquetasEInstruccion.length -1; i++) { //LEGADO: Para más de una etiqueta por instrucción.
					//	guardarEtiqueta(etiquetaEInstruccion[0], punteroID);
					//}
					guardarEtiqueta(etiquetaEInstruccion[0], punteroID);
					instruccion= etiquetaEInstruccion[1];
				}	
				guardarInstruccion(instruccion);
				punteroID++;
			}
			
		}
		
		bufferedReader.close();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Etiqueta> getEtiquetas() {
		return etiquetas;
	}
	public static void setEtiquetas(ArrayList<Etiqueta> etiquetas) {
		ProgramMemory.etiquetas = etiquetas;
	}
	public ArrayList<Instruccion> getInstrucciones() {
		return instrucciones;
	}
	public static void setInstrucciones(ArrayList<Instruccion> instrucciones) {
		ProgramMemory.instrucciones = instrucciones;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean contieneEtiquetas(String instruccion) {
		if(instruccion.indexOf(':')>=0) {
			return true;
		}
		else {
			return false;
		}	
	}
	public void guardarEtiqueta(String etiqueta, int punteroID) {
		etiqueta= etiqueta.trim();
		//TODO: Comprobar que la etiqueta no exista ya anteriormente. Si no, lanzar un error.
		getEtiquetas().add(new Etiqueta(etiqueta, punteroID));
	}
	public void guardarInstruccion(String instruccion) throws Exception {
		instruccion= instruccion.trim();
		getInstrucciones().add(new Instruccion(instruccion));
	}
	public void showProgram() {
		System.out.println("---------------------------------------------------------------");
		System.out.println("Programa:");
		int idCounter= 0;
		for(Instruccion instruccion : getInstrucciones()) {
			System.out.println(String.valueOf(idCounter) + "- " + instruccion.toString());
			idCounter++;
		}
		System.out.println("---------------------------------------------------------------");
		System.out.println("Etiquetas:");
		for(Etiqueta etiqueta : getEtiquetas()) {
			System.out.println(etiqueta.toString());
		}
		System.out.println("---------------------------------------------------------------");
	}
	public Instruccion getInstruccion(int punteroID) throws Exception{
		if(punteroID > instrucciones.size()) {
			return new Instruccion("HALT");
		}
		return instrucciones.get(punteroID);
	}
	public int direccionEtiqueta(String saltoEtiqueta) throws Exception{
		for(Etiqueta etiqueta : getEtiquetas()) {
			if(Objects.equals(etiqueta.getNombre(), saltoEtiqueta)) {
				return etiqueta.getPunteroID()-1;
			}
		}
		throw new Exception("No existe la etiqueta " + saltoEtiqueta);
	}

}
