
import java.util.*;
import javax.swing.Timer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class DetailPanel extends JPanel {

	private Home home;
	
	public JTextField buyField, nameField, arvField, profitGoalField, 
	daysField, principalField, irField;
	
	//DATE PICKER
	private JFXPanel pickerPanel;
	public DatePicker loanDatePicker;
	//
	
	private JButton submit;
	private JButton clear;
	private JLabel errorLabel;
	private Timer messageTimer;
	
	protected JPanel profitGoalPan; //ANALYZE MODE
	protected JPanel buyPan; //EXECUTE MODE
	protected JPanel loanOutPan; //EXECUTE MODE
	protected JPanel westPanel;
	protected JPanel eastPanel;
	
	public DetailPanel() {
		
		messageTimer = new Timer(2000, new MessageListener());
	
		nameField = new JTextField();
		buyField = new JTextField();
		arvField = new JTextField();
		profitGoalField = new JTextField();
		daysField = new JTextField();
		principalField = new JTextField();
		irField = new JTextField();
		nameField.setFont(home.project.lblFont);
		buyField.setFont(home.project.lblFont);
		arvField.setFont(home.project.lblFont);
		profitGoalField.setFont(home.project.lblFont);
		daysField.setFont(home.project.lblFont);
		principalField.setFont(home.project.lblFont);
		irField.setFont(home.project.lblFont);
		
		/*******DATE PICKER*******/
		pickerPanel = new JFXPanel();
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Scene scene = createPickerScene();
				pickerPanel.setScene(scene);
			}
		});
		
		JPanel datePickerPanel = new JPanel();
		datePickerPanel.add(pickerPanel);
		datePickerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel nameLbl = new JLabel("Project Name");
		JLabel buyLbl = new JLabel("Buy Price");
		JLabel arvLbl = new JLabel("ARV");
		JLabel profitGoalLbl = new JLabel("Profit Goal");
		JLabel daysLbl = new JLabel("Project Length (days)");
		JLabel financeLbl = new JLabel("Hard Money Loan");
		JLabel principalLbl = new JLabel("Principal");
		JLabel irLbl = new JLabel("Interest Rate (decimal)");
		JLabel outLabel = new JLabel("Loan-Out Date");
		nameLbl.setFont(home.project.textFont);
		buyLbl.setFont(home.project.textFont);
		arvLbl.setFont(home.project.textFont);
		profitGoalLbl.setFont(home.project.textFont);
		daysLbl.setFont(home.project.textFont);
		financeLbl.setFont(home.project.titleFont);
		principalLbl.setFont(home.project.textFont);
		irLbl.setFont(home.project.textFont);
		outLabel.setFont(home.project.textFont);
		
		GridLayout formLay = new GridLayout(2,1);
		FlowLayout fieldLay = new FlowLayout(FlowLayout.LEFT);
		Dimension fieldDim = home.project.fieldDim;
		
		JPanel namePan = new JPanel(formLay);
		JPanel nameFieldPan = new JPanel(fieldLay);
		nameFieldPan.add(nameField);
		nameField.setPreferredSize(fieldDim);
		namePan.add(nameLbl);
		namePan.add(nameFieldPan);
		
		//FOR EXECUTE MODE
		buyPan = new JPanel(formLay);
		JPanel buyFieldPan = new JPanel(fieldLay);
		buyFieldPan.add(buyField);
		buyField.setPreferredSize(fieldDim);
		buyPan.add(buyLbl);
		buyPan.add(buyFieldPan);
		buyPan.setVisible(false);
		
		//FOR EXECUTE MODE
		loanOutPan = new JPanel(formLay);
		loanOutPan.add(outLabel);
		loanOutPan.add(datePickerPanel);
		loanOutPan.setVisible(false); //Initialized to be unseen
		
		//FOR ANALYZE MODE
		profitGoalPan = new JPanel(formLay);
		JPanel profitGoalFieldPan = new JPanel(fieldLay);
		profitGoalFieldPan.add(profitGoalField);
		profitGoalField.setPreferredSize(fieldDim);
		profitGoalPan.add(profitGoalLbl);
		profitGoalPan.add(profitGoalFieldPan);
		profitGoalPan.setVisible(true);
		
		JPanel arvPan = new JPanel(formLay);
		JPanel arvFieldPan = new JPanel(fieldLay);
		arvFieldPan.add(arvField);
		arvField.setPreferredSize(fieldDim);
		arvPan.add(arvLbl);
		arvPan.add(arvFieldPan);
		
		JPanel daysPan = new JPanel(formLay);
		JPanel daysFieldPan = new JPanel(fieldLay);
		daysFieldPan.add(daysField);
		daysField.setPreferredSize(fieldDim);
		daysPan.add(daysLbl);
		daysPan.add(daysFieldPan);
		
		JPanel financePan = new JPanel(formLay);
		double financeTopEB = Toolkit.getDefaultToolkit().getScreenSize()
								.getHeight() / 14;
		financePan.setBorder(new EmptyBorder((int)financeTopEB,0,0,0));
		financePan.add(financeLbl);
		
		JPanel principalPan = new JPanel(formLay);
		JPanel principalFieldPan = new JPanel(fieldLay);
		principalFieldPan.add(principalField);
		principalField.setPreferredSize(fieldDim);
		principalPan.add(principalLbl);
		principalPan.add(principalFieldPan);
		
		JPanel irPan = new JPanel(formLay);
		JPanel irFieldPan = new JPanel(fieldLay);
		irFieldPan.add(irField);
		irField.setPreferredSize(fieldDim);
		irPan.add(irLbl);
		irPan.add(irFieldPan);
		
		westPanel = new JPanel(new GridLayout(4,1));
		eastPanel = new JPanel(new GridLayout(4,1));
		
		westPanel.add(namePan);
		westPanel.add(daysPan);
		westPanel.add(arvPan);
		westPanel.add(profitGoalPan); //SWITCH TO BUYPAN FOR EXECUTE
		eastPanel.add(financePan);
		eastPanel.add(principalPan);
		eastPanel.add(irPan);
		eastPanel.add(loanOutPan); //ONLY VISIBLE IN EXECUTE MODE
		
		
		JPanel centerPanel = new JPanel(new GridLayout(1,2));
		centerPanel.add(westPanel);
		centerPanel.add(eastPanel);
		
		JPanel errorPanel = new JPanel();
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setFont(home.project.textFont);
		errorPanel.add(errorLabel);
		double epEB = Toolkit.getDefaultToolkit().getScreenSize()
						.getHeight() / 40;
		errorPanel.setBorder(new EmptyBorder(0,0,(int)epEB,0));
		
		JPanel btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		submit = new JButton("Submit");
		submit.setBackground(home.project.LIGHT_BLUE);
		submit.setForeground(Color.WHITE);
		submit.setPreferredSize(home.project.btnDim);
		btnPan.add(submit);
		submit.setFont(home.project.lblFont);
		submit.addActionListener(new SubmitListener());
		
		clear = new JButton("Clear");
		clear.setBackground(home.project.LIGHT_BLUE);
		clear.setForeground(Color.WHITE);
		clear.setPreferredSize(home.project.btnDim);
		clear.setFont(home.project.lblFont);
		clear.addActionListener(new ClearListener());
		btnPan.add(clear);
		
		
		JPanel errorBtnPan = new JPanel(new GridLayout(2,1));
		errorBtnPan.add(btnPan);
		errorBtnPan.add(errorPanel);
		
		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(errorBtnPan, BorderLayout.SOUTH);
		
	}
	
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(checkInputData()) {
				errorLabel.setText("");
				if(home.project.executeMode) {
					String name = nameField.getText();
					int days = Integer.parseInt(daysField.getText());
					double arv = Double.parseDouble(arvField.getText());
					double buyPrice = Double.parseDouble(buyField.getText());
					double principal = Double.parseDouble(principalField.getText());
					double ir = Double.parseDouble(irField.getText());
					LocalDate loanOutDate = loanDatePicker.getValue();
					
					home.project.arv = arv; home.project.projectName = name;
					home.project.buyPrice = buyPrice; home.project.interestRate = ir;
					home.project.principal = principal; home.project.days = days;
					home.project.loanOutDate = loanOutDate;
					home.project.mainPage.setTitle("Flip Calculator  -  " + name);
					errorLabelTimer("Success");
					home.project.updateApplication();
				} else {
					String name = nameField.getText();
					int days = Integer.parseInt(daysField.getText());
					double arv = Double.parseDouble(arvField.getText());
					double profitGoal = Double.parseDouble(profitGoalField.getText());
					double principal = Double.parseDouble(principalField.getText());
					double ir = Double.parseDouble(irField.getText());
					
					home.project.arv = arv; home.project.projectName = name;
					home.project.profitGoal = profitGoal; home.project.interestRate = ir;
					home.project.principal = principal; home.project.days = days;
					home.project.mainPage.setTitle("Flip Calculator  -  " + name);
					errorLabelTimer("Success");
					home.project.updateApplication();
				}
			} else {
				errorLabelTimer("Input Error");
			}
		}
	}
	private class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			home.project.arv = 0;
			home.project.buyPrice = 0;
			home.project.profitGoal = 0;
			home.project.projectName = "";
			home.project.days = 0;
			home.project.principal = 0;
			home.project.interestRate = 0;
			home.project.mainPage.setTitle("Flip Calculator");
			arvField.setText("");
			buyField.setText("");
			daysField.setText("");
			nameField.setText("");
			profitGoalField.setText("");
			principalField.setText("");
			irField.setText("");
			loanDatePicker.setValue(null);
			home.project.updateApplication();
		}
	}
	
	private boolean checkInputData() {
		if(home.project.executeMode) {
			String lengthString = daysField.getText();
			String arvString = arvField.getText();
			String buyPriceString = buyField.getText();
			String principalString = principalField.getText();
			String irString = irField.getText();
			LocalDate loanDate = loanDatePicker.getValue();
			
			if(isNumeric(lengthString) && isNumeric(arvString) 
					&& isNumeric(buyPriceString) 
					&& isNumeric(principalString)
					&& isNumeric(irString)
					&& loanDate != null) {
				return true;
			}
		} else {
			String lengthString = daysField.getText();
			String arvString = arvField.getText();
			String profitString = profitGoalField.getText();
			String principalString = principalField.getText();
			String irString = irField.getText();
			
			if(isNumeric(lengthString) && isNumeric(arvString) 
					&& isNumeric(profitString) 
					&& isNumeric(principalString)
					&& isNumeric(irString)) {
				return true;
			}
		}
		
		return false;
	}

	private boolean isNumeric(String numString) {
		try {
			double n = Double.parseDouble(numString);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private void errorLabelTimer(String s) {
		if(s.equals("Input Error")) {
			errorLabel.setText(s);
			errorLabel.setForeground(Color.RED);
			messageTimer.start();
		} else if(s.equals("Success")) {
			errorLabel.setText(s);
			errorLabel.setForeground(new Color(0,128,0));
			messageTimer.start();
		}
	}
	private class MessageListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			errorLabel.setText("");
			messageTimer.stop();
		}
	}
	
	private Scene createPickerScene() {
		double width = Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() / 4;
		double height = Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight() / 23;
		
		Group root = new Group();
		Scene scene = new Scene(root);
		loanDatePicker = new DatePicker();
		loanDatePicker.setEditable(false);
		loanDatePicker.setPrefSize(width,height);
		root.getChildren().add(loanDatePicker);
		return scene;
	}
	
}
