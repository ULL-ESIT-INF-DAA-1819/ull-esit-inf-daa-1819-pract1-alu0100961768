import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**La clase output sobreescribe el fichero de salida cada
 * vez que sea necesario a ficherodesalida + nuevoWrite.
 * @author Yahlunna
 */
public class OutputUnit {

	File outputFile;
	
	public OutputUnit(String fileTxt) {
		setOutputFile(new File(fileTxt));
	}
	public File getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	public void writeNextOutputValue(int value) throws Exception {
		FileWriter fileWritter = new FileWriter(getOutputFile(),true);        
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write(String.valueOf(value));
        bufferWritter.newLine();
        bufferWritter.close();
        fileWritter.close();
	}
	
	
}
