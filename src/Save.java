import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Save {

	private Home home;
	
	public Save() {
		home = new Home(home.project);
		if(!home.project.saved) {
			SaveAs saveAs = new SaveAs();
		} else {
			
			try {

				File file = new File(home.project.filePath);
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
				SavePackage savePackage = new SavePackage(
							days, projectName, arv, profitGoal, 
							principal, renovationItems, loanOutDate, 
							percentClosing, percentBuying, holdingPerMonth, interestRate
				);
				
				objectOut.writeObject(savePackage);
				
				objectOut.close();
				
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
			
			
		}
	}
}
