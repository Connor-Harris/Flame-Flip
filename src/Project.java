import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.swing.JTextField;

public class Project {

	//Save & Open
	public boolean saved = false;
	public String filePath = "";
	
	//MainFrame
	MainPage mainPage;
	
	//Fonts
	Font tabFont;
	Font textFont;
	Font lblFont;
	Font titleFont;
	Font innerTitle;
	DecimalFormat money = new DecimalFormat("$##,##0.00");
	//Dimensions
	Dimension fieldDim;
	Dimension btnDim;
	//Colors
	final Color DARK_BLUE = new Color(28,76,122);
	final Color LIGHT_BLUE = new Color(90,145,165);//(100,151,177);
	//PANELS
	public DetailPanel detailPanel;
	public RenCostPanel renCostPanel;
	public FixedCosts fixedCosts;
	public ResultPanel resultPanel;
	//CURRENT MODE
	boolean executeMode = false;
	
	//DATA
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
	
	public Project() {
		
		
		double width = Toolkit.getDefaultToolkit().getScreenSize()
						.getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize()
						.getHeight();
		
		tabFont = new Font("Monospace", Font.BOLD, (int)height/45);
		titleFont = new Font("Monospace", Font.BOLD, (int)height/40);
		textFont = new Font("Monospace", Font.PLAIN, (int)height/45);
		lblFont = new Font("Monospace", Font.PLAIN, (int)height/55);
		fieldDim = new Dimension((int)width/4, (int)height/22);
		btnDim = new Dimension((int)width/10, (int)height/24);
		innerTitle = new Font("Monospace", Font.BOLD, (int)height/45);
		
		totalRenCost = 0;
		renovationItems = new ArrayList<RenItem>();
	}
	
	public void changeMode(boolean execute) {
		
		//DETAILS PANEL		
		if(execute == true) {
			executeMode = true;
			//Details Panel
			detailPanel.buyPan.setVisible(true);
			detailPanel.profitGoalPan.setVisible(false);
			detailPanel.westPanel.remove(detailPanel.profitGoalPan);
			detailPanel.westPanel.add(detailPanel.buyPan);
			detailPanel.loanOutPan.setVisible(true);
			buyPrice = buyPriceHandleModeSwitch();
			//Cost Panel
			fixedCosts.switchMode(executeMode);
			//Results Panel
			resultPanel.setMode(true);
		} else {
			executeMode = false;
			//Details Panel
			detailPanel.buyPan.setVisible(false);
			detailPanel.profitGoalPan.setVisible(true);
			detailPanel.westPanel.remove(detailPanel.buyPan);
			detailPanel.westPanel.add(detailPanel.profitGoalPan);
			detailPanel.loanOutPan.setVisible(false);
			//Cost Panel
			fixedCosts.switchMode(executeMode);
			//Result Panel
			resultPanel.setMode(false);
			
		}
		
		updateApplication();
	}
	
	private double buyPriceHandleModeSwitch() {
		double price;
		try {
			price = Double.parseDouble(detailPanel.buyField.getText());
		} catch(NumberFormatException e) {
			price = buyPrice;
		}
		return price;
	}
	
	/*************UPDATE APPLICATION****************/
	public void updateApplication() {
		//setFixedCosts();
		setTotalRen();
		setInterest();
		setFixedCosts();
		setTotalCost();
		calculateResult();
		setFixedCosts();
		setROI();
		fixedCosts.updateFixed();
		resultPanel.setPanel();
		
	}
	/*********\\\\\UPDATE APPLICATION\\\\\*********/
	
	private void setTotalRen() {
		double total = 0;
		for(int i = 0; i < renovationItems.size(); i++) {
			total += renovationItems.get(i).totalPrice;
		}
		totalRenCost = total;
		renCostPanel.updateTotalRenCost();
	}
	
	public void setFixedCosts() {
		
		double close = percentClosing * arv;
		if(close >= 0)
			this.closingCosts = close;
		if(arv == 0)
			this.closingCosts = 0;
		
		double daysDouble = (double) days;
		
		double plusDays = daysDouble % 30;
		double months = (daysDouble - plusDays) / 30;
		double holding = months * this.holdingPerMonth;
		double extraPercent = 100 * plusDays / 30;
		holding = holding + (extraPercent * this.holdingPerMonth / 100);
		if(holding >= 0) {
			this.holdingCosts = holding;
		}
		double buy = percentBuying * buyPrice;
		if(buy >= 0)
			this.buyingCosts = buy;
		if(buyPrice == 0)
			this.buyingCosts = 0;
	}
	
	public void setInterest() {
		double totalInterest = principal * interestRate * days/365;
		this.projectedInterest = totalInterest;
		if(executeMode) {
			if(loanOutDate != null) {
				int currentDays = dayDifference(this.loanOutDate, LocalDate.now());
				if(currentDays > 0) {
					this.currentInterest = principal * interestRate * currentDays/365;
				} else
					this.currentInterest = 0;
			}
		}
	}
	
	public int dayDifference(LocalDate d1, LocalDate d2) {
		long daysBetween = ChronoUnit.DAYS.between(d1,d2);
		return (int) daysBetween;
	}
	
	public void setTotalCost() {
		totalCost = 0;
		totalCost += totalRenCost + holdingCosts + closingCosts + 
				buyingCosts + buyPrice + projectedInterest;
	}
	
	public void calculateResult() {
		if(!executeMode) { //[Analysis] (find buy price)
		
			buyPrice = (arv - profitGoal - totalRenCost - 
					holdingCosts - closingCosts - projectedInterest) 
					/ (1 + percentBuying);
			if(buyPrice < 0)
				buyPrice = 0;
			if(arv == 0)
				buyPrice = 0;
			buyingCosts = percentBuying * buyPrice;
			if(buyingCosts < 0)
				buyingCosts = 0;
		}
		
		setTotalCost();
		
		//For execute mode AND analysis mode
		netProfit = arv - totalCost;
		
	}
	
	public void setROI() {
		
		roi = netProfit / totalCost;
		if(Double.isNaN(roi))
			roi = 0;
		if(arv == 0)
			roi = 0;
	}
	
	public void setFields() {
		//Details Page
		detailPanel.buyField.setText(Double.toString(buyPrice));
		detailPanel.nameField.setText(projectName);
		detailPanel.profitGoalField.setText(Double.toString(profitGoal));
		detailPanel.daysField.setText(Integer.toString(days));
		detailPanel.arvField.setText(Double.toString(arv));
		detailPanel.principalField.setText(Double.toString(principal));
		detailPanel.irField.setText(Double.toString(interestRate));
		detailPanel.loanDatePicker.setValue(loanOutDate);
		//Renovation Costs
		renCostPanel.updateTableOnOpen();
		//Fixed Costs
		fixedCosts.updateFieldsOnOpen();
	}
	
	/*private JTextField nameField, arvField, profitGoalField, 
						daysField, principalField, irField;
	public JTextField buyField;*/
	
}
