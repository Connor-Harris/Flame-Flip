import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.lang.Object.*;

import javax.swing.*;

public class SaveAs {
	
	private Home home;
	private JFileChooser fileChooser;
	
	public SaveAs() {
		
		home = new Home(home.project);
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:/"));
		int retrieval = fileChooser.showSaveDialog(null);
		if(retrieval == JFileChooser.APPROVE_OPTION) {
			
			try {
				
				File file = fileChooser.getSelectedFile();
				
				String filePath = file.getAbsolutePath();
				if(!filePath.endsWith(".dat")) {
					file = new File(filePath + ".dat");
				}
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				
				int days = home.project.days; 
				String projectName = home.project.projectName;
				double arv = home.project.arv;
				double profitGoal = home.project.profitGoal;
				double principal = home.project.principal;
				double interestRate = home.project.interestRate;
				ArrayList<RenItem> renovationItems = home.project.renovationItems;
				LocalDate loanOutDate = home.project.loanOutDate;
				double percentClosing = home.project.percentClosing;
				double percentBuying = home.project.percentBuying;
				double holdingPerMonth = home.project.holdingPerMonth;				
				SavePackage savePackage = new SavePackage
						(days, projectName, arv, profitGoal, principal, renovationItems, 
						loanOutDate, percentClosing, percentBuying, holdingPerMonth, 
						interestRate);
				
				objectOut.writeObject(savePackage);
				objectOut.close();
				
				home.project.saved = true;
				
			} catch(IOException e) {
				System.out.println(e.getMessage());
				//ALERT USER THAT AN ERROR HAS OCCURED
			}
			
		}
		
	}
}
