import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.awt.*;

public class FixedCosts extends JPanel {

	private Home home; 
	
	/***COLORS***/
	final Color HOLDING_COLOR =	new Color(230,230,230);//(194,215,230);
	final Color CLOSING_COLOR = new Color(240,240,240);//(179,205,224);
	final Color BUYING_COLOR = new Color(240,240,240);//(179,205,224);
	final Color SUCCESS_GREEN = new Color(0,150,0);
	
	/***Holding Costs***/
	private JPanel currentIntPan;
	private JLabel projectedIntLbl;
	private JLabel currentIntLbl;
	private JLabel holdingPerMonthLbl;
	private JTextField holdingPerMonthField;
	private JButton holdingSubmitBtn;
	private JLabel holdingSubmitMessage;
	/***Buying Costs***/
	private JTextField percentBuyingField;
	private JButton buySubmitBtn;
	private JLabel buySubmitMessage;
	/***Closing Costs***/
	private JTextField percentClosingField;
	private JButton closeSubmitBtn;
	private JLabel closeSubmitMessage;
	
	private Timer messageTimer;
	
	public FixedCosts() {
	
		home = new Home(home.project);
		DecimalFormat money = home.project.money;
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		Dimension fieldDim = new Dimension((int)width/6,(int)height/24);		
		SubmitListener submitListener = new SubmitListener(); //submit ActionListener's
		messageTimer = new Timer(2000, new TimerListener());
		
		this.setLayout(new GridLayout(1,3));
		
		JPanel buyingPanel = new JPanel(new BorderLayout());
		JPanel holdingPanel = new JPanel(new BorderLayout());
		JPanel sellingPanel = new JPanel(new BorderLayout());
		holdingPanel.setBackground(HOLDING_COLOR);
		sellingPanel.setBackground(CLOSING_COLOR);
		buyingPanel.setBackground(BUYING_COLOR);
		
		JLabel buyTitle = new JLabel("Buying Costs");
		JLabel holdTitle = new JLabel("Holding Costs");
		JLabel sellTitle = new JLabel("Selling Costs");
		buyingPanel.add(buyTitle, BorderLayout.NORTH);
		holdingPanel.add(holdTitle, BorderLayout.NORTH);
		sellingPanel.add(sellTitle, BorderLayout.NORTH);
		buyTitle.setFont(home.project.titleFont);
		holdTitle.setFont(home.project.titleFont);
		sellTitle.setFont(home.project.titleFont);
		buyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		holdTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sellTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		/******HOLDING COSTS******/
		
		JPanel holdingCenterPan = new JPanel(new GridLayout(4,1));
		holdingPanel.add(holdingCenterPan, BorderLayout.CENTER);
		holdingCenterPan.setBackground(HOLDING_COLOR);
		
		JLabel projectedIntTitle = new JLabel("Projected Interest");
		projectedIntTitle.setFont(home.project.innerTitle);
		JPanel projectedIntPan = new JPanel(new GridLayout(2,1));
		projectedIntPan.setBackground(HOLDING_COLOR);
		projectedIntLbl = new JLabel(money.format(0));
		projectedIntLbl.setFont(home.project.textFont);
		projectedIntPan.add(projectedIntTitle);
		projectedIntPan.add(projectedIntLbl);
		
		JLabel currentIntTitle = new JLabel("Current Interest");
		currentIntTitle.setFont(home.project.innerTitle);
		currentIntPan = new JPanel(new GridLayout(2,1));
		currentIntPan.setBackground(HOLDING_COLOR);
		currentIntLbl = new JLabel(money.format(0));
		currentIntLbl.setFont(home.project.textFont);
		currentIntPan.add(currentIntTitle);
		currentIntPan.add(currentIntLbl);
		currentIntPan.setVisible(false); //Initialize to be in analyze mode...
		
		JPanel holdingPerMonthPan = new JPanel(new GridLayout(3,1));
		holdingPerMonthPan.setBackground(HOLDING_COLOR);
		JLabel holdingPerMonthTitle = new JLabel("Non-Interest Costs Per Month");
		holdingPerMonthTitle.setFont(home.project.innerTitle);
		holdingPerMonthLbl = new JLabel(money.format(0));
		holdingPerMonthLbl.setFont(home.project.textFont);
		holdingPerMonthField = new JTextField();
		holdingPerMonthField.setFont(home.project.lblFont);
		holdingPerMonthField.setPreferredSize(fieldDim);
		JPanel holdingFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		holdingFieldPan.setBackground(HOLDING_COLOR);
		holdingFieldPan.add(holdingPerMonthField);
		holdingPerMonthPan.add(holdingPerMonthTitle);
		holdingPerMonthPan.add(holdingFieldPan);
		holdingPerMonthPan.add(holdingPerMonthLbl);
		
		JPanel holdingSubmitPan = new JPanel(new GridLayout(3,1));
		holdingSubmitPan.setBackground(HOLDING_COLOR);
		holdingSubmitBtn = new JButton("Submit Holding Costs");
		holdingSubmitBtn.setBackground(home.project.LIGHT_BLUE);
		holdingSubmitBtn.setForeground(Color.WHITE);
		holdingSubmitBtn.addActionListener(submitListener);
		holdingSubmitBtn.setFont(home.project.lblFont);
		JPanel holdSubmitBtnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		holdSubmitBtnPan.add(holdingSubmitBtn);
		holdSubmitBtnPan.setBackground(HOLDING_COLOR);
		holdingSubmitMessage = new JLabel("");
		holdingSubmitMessage.setForeground(Color.RED);
		holdingSubmitMessage.setHorizontalAlignment(SwingConstants.CENTER);
		holdingSubmitMessage.setFont(home.project.textFont);
		holdingSubmitPan.add(holdSubmitBtnPan);
		holdingSubmitPan.add(holdingSubmitMessage);
		
		holdingCenterPan.add(holdingPerMonthPan);
		holdingCenterPan.add(projectedIntPan);
		holdingCenterPan.add(currentIntPan);
		holdingCenterPan.add(holdingSubmitPan);
		
		/***BUYING COSTS***/
		JPanel buyingCenterPan = new JPanel(new GridLayout(4,1));
		buyingCenterPan.setBackground(BUYING_COLOR);
		
		JPanel percentBuyPan = new JPanel(new GridLayout(3,1));
		percentBuyPan.setBackground(BUYING_COLOR);
		JLabel percentBuyingTitle = new JLabel("Percent of Purchase Price");
		JLabel decimalDisclaimer = new JLabel("(Decimal)");
		percentBuyingTitle.setFont(home.project.innerTitle);
		decimalDisclaimer.setFont(new Font("Monospace",Font.ITALIC, (int)height/50));
		percentBuyingField = new JTextField();
		JPanel percentBuyFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		percentBuyFieldPan.setBackground(BUYING_COLOR);
		percentBuyingField.setFont(home.project.lblFont);
		percentBuyingField.setPreferredSize(fieldDim);
		percentBuyFieldPan.add(percentBuyingField);
		percentBuyPan.add(percentBuyingTitle);
		percentBuyPan.add(decimalDisclaimer);
		percentBuyPan.add(percentBuyFieldPan);

		
		JPanel buySubmitPan = new JPanel(new GridLayout(2,1));
		buySubmitPan.setBackground(BUYING_COLOR);
		buySubmitBtn = new JButton("Submit Buying Costs");
		buySubmitBtn.setFont(home.project.lblFont);
		buySubmitBtn.setForeground(Color.WHITE);
		buySubmitBtn.setBackground(home.project.LIGHT_BLUE);
		buySubmitBtn.addActionListener(submitListener);
		JPanel buySubmitBtnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buySubmitBtnPan.setBackground(BUYING_COLOR);
		buySubmitBtnPan.add(buySubmitBtn);
		buySubmitMessage = new JLabel("");
		buySubmitMessage.setFont(home.project.textFont);
		buySubmitMessage.setForeground(Color.RED);
		buySubmitMessage.setHorizontalAlignment(SwingConstants.CENTER);
		buySubmitPan.add(buySubmitBtnPan);
		buySubmitPan.add(buySubmitMessage);
		
		buyingCenterPan.add(new JLabel());
		buyingCenterPan.add(percentBuyPan);
		buyingCenterPan.add(new JLabel());
		buyingCenterPan.add(buySubmitPan);
		
		/***CLOSING COSTS***/
		
		JPanel closingCenterPan = new JPanel(new GridLayout(4,1));
		closingCenterPan.setBackground(CLOSING_COLOR);
		
		JPanel percentClosingPan = new JPanel(new GridLayout(3,1));
		percentClosingPan.setBackground(CLOSING_COLOR);
		percentClosingField = new JTextField();
		percentClosingField.setFont(home.project.lblFont);
		percentClosingField.setPreferredSize(fieldDim);
		JPanel percentCloseFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		percentCloseFieldPan.setBackground(CLOSING_COLOR);
		percentCloseFieldPan.add(percentClosingField);
		JLabel percentCloseTitle = new JLabel("Percent of Sell Price");
		percentCloseTitle.setFont(home.project.innerTitle);
		JLabel decimalDisclaimer2 = new JLabel("(Decimal)");
		decimalDisclaimer2.setFont(new Font("Monospace",Font.ITALIC, (int)height/50));
		percentClosingPan.add(percentCloseTitle);
		percentClosingPan.add(decimalDisclaimer2);
		percentClosingPan.add(percentCloseFieldPan);
		
		JPanel closingSubmitPan = new JPanel(new GridLayout(2,1));
		closingSubmitPan.setBackground(CLOSING_COLOR);
		JPanel closingSubmitBtnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		closingSubmitBtnPan.setBackground(CLOSING_COLOR);
		closeSubmitBtn = new JButton("Submit Closing Costs");
		closeSubmitBtn.setFont(home.project.lblFont);
		closeSubmitBtn.setForeground(Color.WHITE);
		closeSubmitBtn.setBackground(home.project.LIGHT_BLUE);
		closeSubmitBtn.addActionListener(submitListener);
		closingSubmitBtnPan.add(closeSubmitBtn);
		closeSubmitMessage = new JLabel("");
		closeSubmitMessage.setForeground(Color.RED);
		closeSubmitMessage.setFont(home.project.textFont);
		closeSubmitMessage.setHorizontalAlignment(SwingConstants.CENTER);
		closingSubmitPan.add(closingSubmitBtnPan);
		closingSubmitPan.add(closeSubmitMessage);
		
		closingCenterPan.add(new JLabel());
		closingCenterPan.add(percentClosingPan);
		closingCenterPan.add(new JLabel());
		closingCenterPan.add(closingSubmitPan);
		
		sellingPanel.add(closingCenterPan, BorderLayout.CENTER);
		
		buyingPanel.add(buyingCenterPan, BorderLayout.CENTER);
		
		this.add(buyingPanel);
		this.add(holdingPanel);
		this.add(sellingPanel);
		
	}
	
	private class SubmitListener implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == holdingSubmitBtn) {
				String check = checkInput("HOLDING");
				if(!check.equals("SUCCESS")) {
					setMessage("HOLDING", check, true); //COST, message, error bool
				} else {
					setMessage("HOLDING", "Success.", false);
					double holdingPerMonth = 
							Double.parseDouble(holdingPerMonthField.getText());
					home.project.holdingPerMonth = holdingPerMonth;
					home.project.updateApplication();
				}
			} else if(e.getSource() == closeSubmitBtn) {
				String check = checkInput("CLOSING");
				if(!check.equals("SUCCESS")) {
					setMessage("CLOSING",check, true);
				} else {
					setMessage("CLOSING", "Success.", false);
					double percentClosing = 
							Double.parseDouble(percentClosingField.getText());
					home.project.percentClosing = percentClosing;
					home.project.updateApplication();
				}
				
			} else if(e.getSource() == buySubmitBtn) {
				String check = checkInput("BUYING");
				if(!check.equals("SUCCESS")) {
					setMessage("BUYING", check, true);
				} else {
					setMessage("BUYING", "Success.", false);
					double percentBuying = 
							Double.parseDouble(percentBuyingField.getText());
					home.project.percentBuying = percentBuying;
					home.project.updateApplication();
				}
			}
		}
	}
	
	public void switchMode(boolean execution) {
		if(execution) {
			currentIntPan.setVisible(true);
		} else {
			currentIntPan.setVisible(false);
		}
	}
	
	private String checkInput(String cost) {
		if(cost.equals("HOLDING")) {
			String holdingCosts = holdingPerMonthField.getText();
			if(!isNumeric(holdingCosts)) {
				return "Holding costs must be numeric.";
			} else {
				return "SUCCESS";
			}			
		} else if(cost.equals("CLOSING")) {
			String closingPercent = percentClosingField.getText();
			if(!isNumeric(closingPercent)) {
				return "Percent must be numeric.";
			} else {
				return "SUCCESS";
			}
		} else if(cost.equals("BUYING")) {
			String buyingPercent = percentBuyingField.getText();
			if(!isNumeric(buyingPercent)) {
				return "Percent must be numeric.";
			} else {
				return "SUCCESS";
			}
		} else {
			return "Error: Not 'BUYING', 'CLOSING', or 'HOLDING'."; //For compiler...
		}
	}
	private boolean isNumeric(String s) {
		try {
			double num = Double.parseDouble(s);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private void setMessage(String cost, String message, boolean error) {
		if(cost.equals("HOLDING")) {
			if(error) {
				holdingSubmitMessage.setText(message);
				holdingSubmitMessage.setForeground(Color.RED);
				messageTimer.stop();
				messageTimer.start();
			} else {
				holdingSubmitMessage.setForeground(SUCCESS_GREEN);
				holdingSubmitMessage.setText(message);
				messageTimer.stop();
				messageTimer.start();
			}
		} else if(cost.equals("BUYING")) {
			if(error) {
				buySubmitMessage.setText(message);
				buySubmitMessage.setForeground(Color.RED);
				messageTimer.stop();
				messageTimer.start();
			} else {
				buySubmitMessage.setText(message);
				buySubmitMessage.setForeground(SUCCESS_GREEN);
				messageTimer.stop();
				messageTimer.start();
			}
		} else { //CLOSING
			if(error) {
				closeSubmitMessage.setText(message);
				closeSubmitMessage.setForeground(Color.RED);
				messageTimer.stop();
				messageTimer.start();
			} else {
				closeSubmitMessage.setText(message);
				closeSubmitMessage.setForeground(SUCCESS_GREEN);
				messageTimer.stop();
				messageTimer.start();
			}
		}
	}
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			messageTimer.stop();
			closeSubmitMessage.setText("");
			holdingSubmitMessage.setText("");
			buySubmitMessage.setText("");
		}
	}
	
	public void updateFixed() {
		DecimalFormat money = home.project.money;
		this.holdingPerMonthLbl.setText(money.format(home.project.holdingCosts));
		//Interest:
		this.projectedIntLbl.setText(money.format(home.project.projectedInterest));
		if(home.project.executeMode)
			this.currentIntLbl.setText(money.format(home.project.currentInterest));
	}
	
	public void updateFieldsOnOpen() {
		holdingPerMonthField.setText(Double.toString(home.project.holdingPerMonth));
		percentBuyingField.setText(Double.toString(home.project.percentBuying));
		percentClosingField.setText(Double.toString(home.project.percentClosing));
	}
	
}
