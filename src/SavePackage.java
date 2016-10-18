import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class SavePackage implements Serializable {

	private Home home;
	
	public int days;
	public String projectName;
	public double arv;
	public double profitGoal;
	public double principal;
	public double interestRate;
	public ArrayList<RenItem> renovationItems;
	public LocalDate loanOutDate;
	public double percentClosing;
	public double percentBuying;
	public double holdingPerMonth;
	
	public SavePackage
		(int days, String projectName, double arv, double profitGoal, double principal,
				ArrayList<RenItem> renovationItems, LocalDate loanOutDate, 
				double percentClosing, double percentBuying, double holdingPerMonth, 
				double interestRate) {
		
		this.days = days; 
		this.projectName = projectName;
		this.arv = arv;
		this.profitGoal = profitGoal;
		this.interestRate = interestRate;
		this.principal = principal;
		this.renovationItems = renovationItems;
		this.loanOutDate = loanOutDate;
		this.percentClosing = percentClosing;
		this.percentBuying = percentBuying;
		this.holdingPerMonth = holdingPerMonth;
		
	}
	
	public void updateProject(String filePath) {
		
		home.project.days = this.days;
		home.project.projectName = this.projectName;
		home.project.arv = this.arv;
		home.project.profitGoal = this.profitGoal;
		home.project.interestRate = this.interestRate;
		home.project.principal = this.principal;
		home.project.renovationItems = this.renovationItems;
		home.project.loanOutDate = this.loanOutDate;
		home.project.percentClosing = this.percentClosing;
		home.project.percentBuying = this.percentBuying;
		home.project.holdingPerMonth = this.holdingPerMonth;
		
		home.project.filePath = filePath;
		home.project.saved = true;
		home.project.setFields();
		home.project.mainPage.setTitle("Flip Calculator  -  " + this.projectName);
		home.project.updateApplication();
		
	}
}
/*
public int days = 0;
public String projectName = "";
public double arv = 0;
public double profitGoal = 0; public double buyPrice = 0;
public double principal = 0; public double interestRate = 0;
public ArrayList<RenItem> renovationItems;
public LocalDate loanOutDate = null;
public double percentClosing = 0;
public double percentBuying = 0;
public double holdingPerMonth = 0;
//CALCULATED DATA
public double totalCost = 0;
public double totalRenCost = 0;
public double projectedInterest = 0;
public double currentInterest = 0;
public double closingCosts = 0;
public double buyingCosts = 0;
public double holdingCosts = 0;
public double netProfit = 0;
public double roi = 0;

*/