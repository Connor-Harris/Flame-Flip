import java.util.*;
import java.io.*;
import java.io.*;
import javax.swing.*;

public class Open {

	private JFileChooser fileChooser;
	
	public Open() {
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(":C/"));
		int retrieval = fileChooser.showOpenDialog(null);
		
		if(retrieval == JFileChooser.APPROVE_OPTION) {
			
			try {
				
				File inputFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
				FileInputStream fileIn = new FileInputStream(inputFile);
				ObjectInputStream objectInput = new ObjectInputStream(fileIn);
				
				SavePackage savePackage = (SavePackage) objectInput.readObject();
				savePackage.updateProject(inputFile.getAbsolutePath());
				
			} catch(IOException e) {
				System.out.println("IOException in Open.java");
				System.out.println(e.getMessage());
				//ALERT USER THAT AN ERROR HAS OCCURED
			} catch(ClassNotFoundException noClass) {
				System.out.println("Class Not Found Exception:\n" + noClass.getMessage());
				//ALERT USER THAT AN ERROR HAS OCCURED
			}
			
		} 		
		
	}
	
}
