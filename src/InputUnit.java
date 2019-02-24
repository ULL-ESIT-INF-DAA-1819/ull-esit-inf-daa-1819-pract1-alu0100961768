import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InputUnit {

	public ArrayList<Integer> inputData = new ArrayList<Integer>();
	public int inputPointer;
	
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
