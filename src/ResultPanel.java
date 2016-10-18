
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ResultPanel extends JPanel {

	private Home home;
	
	private final Color lightBlue = new Color(173,203,227);
	private final Color darkBlue = Home.project.DARK_BLUE;
	private final Color gray = new Color(240,240,240);
	private final DecimalFormat percentFormat = new DecimalFormat("#0.00%");
	private final DecimalFormat money = home.project.money;
	/***JLABELS***/
	//Cost
	private JLabel renovationCostLbl;
	private JLabel buyingCostLbl;
	private JLabel closingCostLbl;
	private JLabel holdingCostLbl;
	private JLabel interestLbl;
	private JLabel purchaseCostLbl;
	private JLabel totalCostLbl;
	//Profit
	private JLabel arvLbl;
	private JPanel netProfitPan;
	private JLabel netProfitLbl;
	//ROI
	private JLabel roiLbl;
	//ANALYSIS RESULTS
	private JLabel analysisResultLbl;
	private JPanel resultAnalysisPan;
	//EXECUTION RESULTS
	private JLabel executionResultLbl;
	private JPanel resultExecutionPan;
	
	public ResultPanel() {
	
		home = new Home(home.project);
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		
		
		this.setLayout(new GridLayout(2,2));
		
		JPanel costPanel = new JPanel(new GridLayout(8,1));
		costPanel.setBackground(lightBlue);
		JLabel costTitle = new JLabel("Costs");
		costTitle.setFont(new Font("Monospace", Font.BOLD, (int)height/35));
		costTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridLayout rowLay = new GridLayout(1,2);
		
		JPanel renovationCostPan = new JPanel(rowLay);
		renovationCostPan.setBackground(lightBlue);
		JLabel renTitle = new JLabel("Renovation: ");
		renTitle.setHorizontalAlignment(SwingConstants.LEFT);
		renTitle.setFont(home.project.innerTitle);
		renovationCostLbl = new JLabel(money.format(home.project.totalRenCost));
		renovationCostLbl.setFont(home.project.textFont);
		renovationCostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		renovationCostPan.add(renTitle);
		renovationCostPan.add(renovationCostLbl);
		
		JPanel buyingCostPan = new JPanel(rowLay);
		buyingCostPan.setBackground(lightBlue);
		JLabel buyCostTitle = new JLabel("Buying Costs: ");
		buyCostTitle.setFont(home.project.innerTitle);
		buyCostTitle.setHorizontalAlignment(SwingConstants.LEFT);
		buyingCostLbl = new JLabel(money.format(home.project.buyingCosts));
		buyingCostLbl.setFont(home.project.textFont);
		buyingCostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		buyingCostPan.add(buyCostTitle);
		buyingCostPan.add(buyingCostLbl);
		
		JPanel holdingCostPan = new JPanel(rowLay);
		holdingCostPan.setBackground(lightBlue);
		JLabel holdCostTitle = new JLabel("Holding Costs: ");
		holdCostTitle.setFont(home.project.innerTitle);
		holdCostTitle.setHorizontalAlignment(SwingConstants.LEFT);
		holdingCostLbl = new JLabel(money.format(home.project.buyingCosts));
		holdingCostLbl.setFont(home.project.textFont);
		holdingCostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		holdingCostPan.add(holdCostTitle);
		holdingCostPan.add(holdingCostLbl);
		
		JPanel closingCostPan = new JPanel(rowLay);
		closingCostPan.setBackground(lightBlue);
		JLabel closeCostTitle = new JLabel("Closing Costs: ");
		closeCostTitle.setFont(home.project.innerTitle);
		closeCostTitle.setHorizontalAlignment(SwingConstants.LEFT);
		closingCostLbl = new JLabel(money.format(home.project.closingCosts));
		closingCostLbl.setFont(home.project.textFont);
		closingCostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		closingCostPan.add(closeCostTitle);
		closingCostPan.add(closingCostLbl);
		
		JPanel interestCostPan = new JPanel(rowLay);
		interestCostPan.setBackground(lightBlue);
		JLabel interestCostTitle = new JLabel("Total Interest: ");
		interestCostTitle.setFont(home.project.innerTitle);
		interestCostTitle.setHorizontalAlignment(SwingConstants.LEFT);
		interestLbl = new JLabel(money.format(home.project.projectedInterest));
		interestLbl.setFont(home.project.textFont);
		interestLbl.setHorizontalAlignment(SwingConstants.LEFT);
		interestCostPan.add(interestCostTitle);
		interestCostPan.add(interestLbl);
		
		JPanel purchaseCostPan = new JPanel(rowLay);
		purchaseCostPan.setBackground(lightBlue);
		JLabel purchaseCostTitle = new JLabel("Purchase Price: ");
		purchaseCostTitle.setFont(home.project.innerTitle);
		purchaseCostTitle.setHorizontalAlignment(SwingConstants.LEFT);
		purchaseCostLbl = new JLabel(money.format(home.project.projectedInterest));
		purchaseCostLbl.setFont(home.project.textFont);
		purchaseCostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		purchaseCostPan.add(purchaseCostTitle);
		purchaseCostPan.add(purchaseCostLbl);
		
		JPanel totalCostPan = new JPanel(rowLay);
		totalCostPan.setBackground(lightBlue);
		JLabel totalCostTitle = new JLabel("TOTAL COST: ");
		totalCostTitle.setFont(home.project.innerTitle);
		totalCostTitle.setHorizontalAlignment(SwingConstants.LEFT);
		totalCostLbl = new JLabel(money.format(home.project.totalCost));
		totalCostLbl.setFont(new Font("Monospace",Font.BOLD,(int)height/35));
		totalCostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalCostPan.add(totalCostTitle);
		totalCostPan.add(totalCostLbl);
		
		costPanel.add(costTitle);
		costPanel.add(renovationCostPan);
		costPanel.add(buyingCostPan);
		costPanel.add(holdingCostPan);
		costPanel.add(closingCostPan);
		costPanel.add(interestCostPan);
		costPanel.add(purchaseCostPan);
		costPanel.add(totalCostPan);
		
		/***PROFIT PANEL***/
		JPanel profitPanel = new JPanel();
		profitPanel.setLayout(new GridLayout(2,1));
		profitPanel.setBackground(gray);
		
		JLabel arvTitle = new JLabel("After-Repair-Value");
		arvTitle.setFont(new Font("Monospace",Font.BOLD, (int)height/35));
		arvTitle.setHorizontalAlignment(SwingConstants.LEFT);
		arvLbl = new JLabel(money.format(home.project.arv));
		arvLbl.setFont(new Font("Monospace", Font.BOLD, (int)height/20));
		arvLbl.setVerticalAlignment(SwingConstants.CENTER);
		arvLbl.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel arvPan = new JPanel(new GridLayout(2,1));
		arvPan.setBackground(gray);
		arvPan.add(arvTitle);
		arvPan.add(arvLbl);
		
		JLabel profitTitle = new JLabel("Net Profit");
		profitTitle.setFont(new Font("Monospace",Font.BOLD, (int)height/35));
		profitTitle.setHorizontalAlignment(SwingConstants.LEFT);
		netProfitLbl = new JLabel(money.format(0));
		netProfitLbl.setFont(new Font("Monospace", Font.BOLD, (int)height/20));
		netProfitLbl.setVerticalAlignment(SwingConstants.CENTER);
		netProfitLbl.setHorizontalAlignment(SwingConstants.CENTER);
		netProfitPan = new JPanel(new GridLayout(2,1));
		netProfitPan.setBackground(gray);
		netProfitPan.add(profitTitle);
		netProfitPan.add(netProfitLbl);
		
		profitPanel.add(arvPan);
		profitPanel.add(netProfitPan);
		
		/***ROI PANEL***/
		JPanel roiPanel = new JPanel(new BorderLayout());
		roiPanel.setBackground(darkBlue);
		
		JPanel roiTitlePan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		roiTitlePan.setBackground(darkBlue);
		roiTitlePan.setBorder(new EmptyBorder(10,10,10,10));
		JLabel roiTitle = new JLabel("Return-On-Investment");
		roiTitlePan.add(roiTitle);
		roiTitle.setFont(new Font("Monospace",Font.BOLD, (int)height/35));
		roiTitle.setForeground(Color.WHITE);
		roiTitle.setHorizontalAlignment(SwingConstants.LEFT);
		roiLbl = new JLabel(percentFormat.format(0));
		roiLbl.setFont(new Font("Monospace",Font.BOLD, (int)height/7));
		roiLbl.setHorizontalAlignment(SwingConstants.CENTER);
		roiLbl.setVerticalAlignment(SwingConstants.CENTER);
		roiLbl.setForeground(Color.WHITE);
		
		roiPanel.add(roiTitlePan, BorderLayout.NORTH);
		roiPanel.add(roiLbl, BorderLayout.CENTER);
		
								/*******RESULTS*******/
		
		/**ANALYSIS MODE RESULTS**/
		resultAnalysisPan = new JPanel(new BorderLayout());
		resultAnalysisPan.setBackground(Color.WHITE);
		JPanel analysisTitlePan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		analysisTitlePan.setBackground(Color.WHITE);
		JLabel analysisTitle = new JLabel("Maximum Purchase Price");
		analysisTitle.setFont(new Font("Monospace",Font.BOLD, (int)height/30));
		analysisTitlePan.add(analysisTitle);
		analysisTitlePan.setBorder(new EmptyBorder(10,10,10,10));
		analysisResultLbl = new JLabel(money.format(0));
		analysisResultLbl.setVerticalAlignment(SwingConstants.CENTER);
		analysisResultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		analysisResultLbl.setFont(new Font("Monospace",Font.BOLD,(int)height/12));
		
		resultAnalysisPan.add(analysisTitlePan, BorderLayout.NORTH);
		resultAnalysisPan.add(analysisResultLbl, BorderLayout.CENTER);
		
		/**EXECUTION MODE RESULTS**/
		resultExecutionPan = new JPanel(new BorderLayout());
		resultExecutionPan.setBackground(Color.WHITE);
		JLabel resultExecutionTitle = new JLabel("Net Profit");
		resultExecutionTitle.setFont(new Font("Monospace",Font.BOLD, (int)height/30));
		JPanel resultExecutionTitlePan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		resultExecutionTitlePan.add(resultExecutionTitle);
		resultExecutionTitlePan.setBorder(new EmptyBorder(10,10,10,10));
		resultExecutionTitlePan.setBackground(Color.WHITE);
		executionResultLbl = new JLabel(money.format(0));
		executionResultLbl.setFont(new Font("Monospace",Font.BOLD, (int)height/12));
		executionResultLbl.setVerticalAlignment(SwingConstants.CENTER);
		executionResultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		resultExecutionPan.add(resultExecutionTitlePan, BorderLayout.NORTH);
		resultExecutionPan.add(executionResultLbl, BorderLayout.CENTER);
		
		this.add(costPanel);
		this.add(roiPanel);
		this.add(profitPanel);
		this.add(resultAnalysisPan); //App starts in ANALYSIS mode
		
	}
	
	public void setMode(boolean execution) {
		if(execution) {
			resultExecutionPan.setVisible(true);
			resultAnalysisPan.setVisible(false);
			netProfitPan.setVisible(false);
			this.remove(resultAnalysisPan);
			this.add(resultExecutionPan);
			
		} else {
			resultExecutionPan.setVisible(false);
			resultAnalysisPan.setVisible(true);
			netProfitPan.setVisible(true);
			this.remove(resultExecutionPan);
			this.add(resultAnalysisPan);
		}
	}
	
	public void setPanel() {
		double interest = home.project.projectedInterest;
		double buyingCosts = home.project.buyingCosts;
		double holdingCosts = home.project.holdingCosts;
		double sellingCosts = home.project.closingCosts;
		double totalRen = home.project.totalRenCost;
		double purchasePrice = home.project.buyPrice;
		double totalCosts = home.project.totalCost;
		double arv = home.project.arv;
		double netProfit = home.project.netProfit;
		double roi = home.project.roi;
		
		renovationCostLbl.setText(money.format(totalRen));
		buyingCostLbl.setText(money.format(buyingCosts));
		holdingCostLbl.setText(money.format(holdingCosts));
		closingCostLbl.setText(money.format(sellingCosts));
		interestLbl.setText(money.format(interest));
		purchaseCostLbl.setText(money.format(purchasePrice));
		totalCostLbl.setText(money.format(totalCosts));
		arvLbl.setText(money.format(arv));
		netProfitLbl.setText(money.format(netProfit));
		roiLbl.setText(percentFormat.format(roi));
		
		analysisResultLbl.setText(money.format(purchasePrice));
		executionResultLbl.setText(money.format(netProfit));
		
	}
	
}
