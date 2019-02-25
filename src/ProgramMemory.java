
import java.io.*;
import java.util.*;

/**Guarada una lsita con las instrucciones del programa y las etiquetas del programa
 * @author Yahlunna
 *
 */
public class ProgramMemory {
	
	public static ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
	public static ArrayList<Instruccion> instrucciones = new ArrayList<Instruccion>();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**Constructor
	 * @param rutaFicheroPrograma
	 * Lee del fichero de entrada linea por linea,
	 * detecta las instrucciones
	 * detecta si hay etiquetas en una linea
	 * guarda ambas en diferentes arraylists
	 * 
	 * No comprueba que los parámetros sean correctos.
	 */
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
	
	/**
	 * Si la linea contiene una etiqueta (caracter :) devuelve verdadero.
	 * @return
	 */
	public boolean contieneEtiquetas(String instruccion) {
		if(instruccion.indexOf(':')>=0) {
			return true;
		}
		else {
			return false;
		}	
	}
	/**Guarda en el vector de etiquetas una nueva @Etiqueta con el valor ID que tiene asignado
	 */
	public void guardarEtiqueta(String etiqueta, int punteroID) {
		etiqueta= etiqueta.trim();
		//TODO: Comprobar que la etiqueta no exista ya anteriormente. Si no, lanzar un error.
		getEtiquetas().add(new Etiqueta(etiqueta, punteroID));
	}
	/**
	 * Guarda una nueva @Instruccion en el vector de instrucciones.
	 * El constructor de @Instruccion se encarga de separar los operandos de la instruccion.
	 */
	public void guardarInstruccion(String instruccion) throws Exception {
		instruccion= instruccion.trim();
		getInstrucciones().add(new Instruccion(instruccion));
	}
	/**
	 * Muestra el programa por pantalla. Estaba de flojeara y no me paetecía ahcer el toString();
	 */
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
	/**Busca la instruccion numero @punteroID.
	 * Si no existe, devuelve HALT. 
	 * 
	 * @param punteroID
	 * @throws Exception
	 */
	public Instruccion getInstruccion(int punteroID) throws Exception{
		if(punteroID > instrucciones.size()) {
			return new Instruccion("HALT");
		}
		return instrucciones.get(punteroID);
	}
	/**
	 * Devuelve el valor al que apunta la etiqueta @saltoEtiqueta .
	 */
	public int direccionEtiqueta(String saltoEtiqueta) throws Exception{
		for(Etiqueta etiqueta : getEtiquetas()) {
			if(Objects.equals(etiqueta.getNombre(), saltoEtiqueta)) {
				return etiqueta.getPunteroID()-1;
			}
		}
		throw new Exception("No existe la etiqueta " + saltoEtiqueta);
	}

}
