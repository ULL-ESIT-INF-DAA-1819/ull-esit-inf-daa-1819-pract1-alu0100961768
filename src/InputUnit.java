import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** Cinta de entrada. Lee el fichero asignado como cinta de entrada linea a linea buscando ints.
 *  Guarda estos valores en un vector.
 *  Cada vez que un  valor es solicitado, se le va dando el siguiente valror del vector.
 * @author Yahlunna
 *
 */
public class InputUnit {

	public ArrayList<Integer> inputData = new ArrayList<Integer>();
	public int inputPointer;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public InputUnit(String fileTxt) throws Exception {
		setInputPointer(0);
		File inputFile= new File(fileTxt); 
		//BufferedReader bufferedReader= new BufferedReader(new FileReader(inputFile));
		Scanner scan= new Scanner(inputFile);
		while(scan.hasNext()) {
			getInputData().add(scan.nextInt());
		}
		scan.close();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<Integer> getInputData() {
		return inputData;
	}

	public void setInputData(ArrayList<Integer> inputData) {
		this.inputData = inputData;
	}

	public int getInputPointer() {
		return inputPointer;
	}

	public void setInputPointer(int inputPointer) {
		this.inputPointer = inputPointer;
	}
	
	/**
	 * @return Devolverá el siguiente valor del vector hasta que no quede ninguno. Si se han leido todos los valores de la cinta de entrada, se devolverá el último e imprimirá un warning por pantalla.
	 * @throws Exception
	 */
	public int getNextInputValue() throws Exception {
		if(getInputPointer()>= getInputData().size()) {
			System.out.println("[ADVERTENCIA]: Se está accediendo a un valor de la cinta de entrada no especificado. Se usará el último valor de la cinta de entrada");
			setInputPointer(getInputData().size() -1);
		}
		int result=getInputData().get(getInputPointer()).intValue();
		setInputPointer(getInputPointer()+1);
		return result;
	}
	
	
	
}
