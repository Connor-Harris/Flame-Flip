import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RenCostPanel extends JPanel {

	private Home home;
	private JTable table;
	private DefaultTableModel model;
	
	//TextFields
	JTextField itemField, sqftField, ppSqftField, priceField;
	private JButton priceToggleBtn;	
	
	private JPanel eastPanel;
	
	//Price Setters
	private boolean sqft = false;
	private JPanel sqftPanel;
	private JPanel ppSqftPanel;
	private JPanel totalPricePanel;
	private JPanel submitPanel;
	
	private JLabel totalCostLbl;
	private JLabel messageLabel;
	private Timer messageTimer;
	private JLabel blank = new JLabel(""); private JLabel blank2 = new JLabel("");
	private JButton addBtn, editBtn, deleteBtn;
	
	public RenCostPanel() {
		
		home = new Home(home.project);
		
		messageTimer = new Timer(2000,new MessageTimerListener());
		
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		double scrollHeight = screenHeight / 1.6; double scrollWidth = screenWidth/3;
		scroll.setPreferredSize(new Dimension((int)scrollWidth, (int)scrollHeight));
		JPanel tablePan = new JPanel(new BorderLayout());
		int ebs = (int) screenHeight/35;
		tablePan.setBorder(new EmptyBorder(ebs,ebs,ebs,ebs));
		tablePan.add(scroll, BorderLayout.CENTER);
		JPanel totalCostPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel costTitle = new JLabel("Renovation Costs: ");
		costTitle.setFont(home.project.titleFont);
		costTitle.setPreferredSize(new Dimension((int)screenWidth/7,40));
		totalCostLbl = new JLabel("");
		totalCostLbl.setFont(home.project.titleFont);
		updateTotalRenCost();
		totalCostPan.add(costTitle); totalCostPan.add(totalCostLbl);	
		tablePan.add(totalCostPan, BorderLayout.SOUTH);
		
		table.getTableHeader().setFont(new Font
						("Monospace",Font.BOLD,(int)screenHeight/50));
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(home.project.textFont);
		table.setRowHeight((int)screenHeight/30);
		table.addMouseListener(new TableListener());
		
		model.addColumn("Item");
		model.addColumn("Price/SqFt");
		model.addColumn("SqFt");
		model.addColumn("Total Price");
		
		Dimension fieldDim = new Dimension((int)screenWidth/5,(int)screenHeight/25);
		
		JPanel itemPan = new JPanel(new GridLayout(2,1));
		JLabel itemTitle = new JLabel("Item");
		itemTitle.setFont(home.project.titleFont); 
		itemField = new JTextField();
		itemField.setPreferredSize(fieldDim);
		itemField.setFont(home.project.lblFont);
		JPanel itemFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		itemFieldPan.add(itemField);
		itemPan.add(itemTitle); itemPan.add(itemFieldPan);
		
		JPanel priceSelectorPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		priceToggleBtn = new JButton("Set Price/Square-Ft");
		priceToggleBtn.setForeground(Color.WHITE);
		priceToggleBtn.setBackground(home.project.LIGHT_BLUE);
		priceToggleBtn.addActionListener(new PriceListener());
		double ptbHeight = screenHeight/25; double ptbWidth = screenWidth/9;
		priceToggleBtn.setPreferredSize(new Dimension((int)ptbWidth, (int)ptbHeight));
		priceToggleBtn.setFont(home.project.lblFont);
		priceSelectorPan.setBorder(new EmptyBorder((int)ptbHeight,0,0,0));
		priceSelectorPan.add(priceToggleBtn);
		
		//Added to eastPanel on price toggle
		ppSqftPanel = new JPanel(new GridLayout(2,1));
		JLabel ppSqftLabel = new JLabel("Price Per Square-Ft");
		ppSqftLabel.setFont(home.project.titleFont);
		ppSqftField = new JTextField();
		ppSqftField.setPreferredSize(fieldDim);
		ppSqftField.setFont(home.project.lblFont);
		JPanel ppSqftFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ppSqftFieldPan.add(ppSqftField);
		ppSqftPanel.add(ppSqftLabel);
		ppSqftPanel.add(ppSqftFieldPan);
		ppSqftPanel.setVisible(false);
		
		//Added to eastPanel on price toggle
		sqftPanel = new JPanel(new GridLayout(2,1));
		JLabel sqftLabel = new JLabel("Square Feet");
		sqftLabel.setFont(home.project.titleFont);
		sqftField = new JTextField();
		sqftField.setPreferredSize(fieldDim);
		sqftField.setFont(home.project.lblFont);
		JPanel sqftFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sqftFieldPan.add(sqftField);
		sqftPanel.add(sqftLabel);
		sqftPanel.add(sqftFieldPan);
		sqftPanel.setVisible(false);
		
		totalPricePanel = new JPanel(new GridLayout(2,1));
		JLabel totalPriceLbl = new JLabel("Total Price");
		totalPriceLbl.setFont(home.project.titleFont);
		priceField = new JTextField();
		priceField.setPreferredSize(fieldDim);
		priceField.setFont(home.project.lblFont);
		JPanel priceFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		priceFieldPan.add(priceField);
		totalPricePanel.add(totalPriceLbl);
		totalPricePanel.add(priceFieldPan);
		
		submitPanel = new JPanel(new GridLayout(2,1));
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		addBtn = new JButton("Add"); 
		editBtn = new JButton("Edit");
		deleteBtn = new JButton("Delete");
		addBtn.setFont(home.project.lblFont); 
		editBtn.setFont(home.project.lblFont);
		deleteBtn.setFont(home.project.lblFont);
		addBtn.setPreferredSize(home.project.btnDim);
		editBtn.setPreferredSize(home.project.btnDim);
		deleteBtn.setPreferredSize(home.project.btnDim);
		addBtn.setForeground(Color.WHITE);
		addBtn.setBackground(home.project.LIGHT_BLUE);
		editBtn.setForeground(Color.WHITE);
		editBtn.setBackground(home.project.LIGHT_BLUE);
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.setBackground(home.project.LIGHT_BLUE);
		TaskListener tl = new TaskListener();
		addBtn.addActionListener(tl);
		editBtn.addActionListener(tl);
		deleteBtn.addActionListener(tl);
		buttonPanel.add(addBtn);buttonPanel.add(editBtn);buttonPanel.add(deleteBtn);
		
		JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		messageLabel = new JLabel("");
		messageLabel.setForeground(Color.RED);
		messageLabel.setFont(home.project.textFont);
		messagePanel.add(messageLabel);
		
		//submitPanel.setBorder(new EmptyBorder(15,0,0,0));
		submitPanel.add(buttonPanel); submitPanel.add(messagePanel);
		
		eastPanel = new JPanel(new GridLayout(6,1));
		eastPanel.setBorder(new EmptyBorder(30,30,30,30));
		eastPanel.add(itemPan);
		eastPanel.add(priceSelectorPan);
		eastPanel.add(totalPricePanel); //Initialized to be total price
		eastPanel.add(blank); eastPanel.add(blank2);
		//eastPanel.add(ppSqftPanel);
		eastPanel.add(submitPanel);
		
		this.add(tablePan);
		this.add(eastPanel);
		this.setLayout(new GridLayout(1,2));
		
	}
	
	private class PriceListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(sqft) {
				sqft = false;
				priceToggleBtn.setText("Set Price/Square-Ft");
				ppSqftPanel.setVisible(false);
				sqftPanel.setVisible(false);
				totalPricePanel.setVisible(true);
				
				eastPanel.remove(ppSqftPanel); eastPanel.remove(sqftPanel);
				eastPanel.remove(submitPanel); 
				eastPanel.remove(blank);
				eastPanel.add(totalPricePanel); 
				eastPanel.add(blank); eastPanel.add(blank2);
				eastPanel.add(submitPanel);
				
			} else {
				sqft = true;
				priceToggleBtn.setText("Set Total Price");
				totalPricePanel.setVisible(false);
				ppSqftPanel.setVisible(true);
				sqftPanel.setVisible(true);
				
				eastPanel.remove(totalPricePanel); eastPanel.remove(submitPanel);
				eastPanel.remove(blank); eastPanel.remove(blank2);
				eastPanel.add(ppSqftPanel); eastPanel.add(sqftPanel);
				eastPanel.add(blank); 
				eastPanel.add(submitPanel);
				
			}
		}
	}

	private class TaskListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addBtn) {
				String inputCheck = checkInput();
				if(!inputCheck.equals("success")) {
					setMessage(inputCheck, true); //String message, boolean error
				} else {
					setMessage("Item Added.", false);//String message, boolean error
					String item = itemField.getText();
					if(sqft) {
						double sqft = Double.parseDouble(sqftField.getText());
						double ppSqft = Double.parseDouble(ppSqftField.getText());
						addRow(item, sqft, ppSqft);
						clearFields();
					} else {
						double totalPrice = Double.parseDouble(priceField.getText());
						addRow(item, totalPrice);
						clearFields();
					}
				}
				
			} else if(e.getSource() == editBtn) {
				int row = table.getSelectedRow();
				if(row == -1)
					setMessage("Select a row to edit.", true);//String message/boolean error
				else {
					String inputCheck = checkInput();
					if(!inputCheck.equals("success")) {
						setMessage(inputCheck, true);
					} else {
						setMessage("Item edited.", false);
						String newItemName = itemField.getText();
						if(sqft) {
							double ppSqft = Double.parseDouble(ppSqftField.getText());
							double totalSqft = Double.parseDouble(sqftField.getText());
							editRow(row, newItemName, totalSqft, ppSqft);
							clearFields();
						} else {
							double totalPrice = Double.parseDouble(priceField.getText());
							editRow(row, newItemName, totalPrice);
							clearFields();
						}										
					}
				}
			} else if(e.getSource() == deleteBtn) {
				int row = table.getSelectedRow();
				if(row == -1) {
					setMessage("Select a row to delete.", true);
				} else {
					deleteRow(row);
					setMessage("Item Deleted.", false);
					clearFields();
				}
			}
		}
	}
	
	private void addRow(String itemName, double sqft, double ppSqft) {
		double totalPrice = sqft * ppSqft;
		model.addRow(new Object[] {itemName, ppSqft, sqft, totalPrice} );
		RenItem renItem = new RenItem(itemName, ppSqft, sqft, totalPrice);
		home.project.renovationItems.add(renItem);
		home.project.updateApplication();
	}
	private void addRow(String item, double totalPrice) {
		model.addRow(new Object[] {item, " - ", " - ", totalPrice} );
		RenItem renItem = new RenItem(item, -1, -1, totalPrice);
		home.project.renovationItems.add(renItem);
		home.project.updateApplication();
	}
	private void editRow(int row, String item, double sqft, double ppSqft) {
		model.removeRow(row);
		double totalPrice = sqft * ppSqft;
		model.addRow(new Object[] {item, ppSqft,sqft, totalPrice} );
		home.project.renovationItems.remove(row);
		home.project.renovationItems.add(new RenItem(item, ppSqft, sqft, totalPrice));
		home.project.updateApplication();
	}
	private void editRow(int row, String item, double totalPrice) {
		model.removeRow(row);
		model.addRow(new Object[] {item, " - ", " - ", totalPrice});
		home.project.renovationItems.remove(row);
		home.project.renovationItems.add(new RenItem(item, -1,-1, totalPrice));
		home.project.updateApplication();
	}
	private void deleteRow(int row) {
		model.removeRow(row);
		home.project.renovationItems.remove(row);
		home.project.updateApplication();
	}
	private void openAddRow(String item, double sqft, double ppSqft, double totalCost) {
		String sqftString, ppSqftString;
		if(sqft == -1) {
			ppSqftString = " - "; 
			sqftString = " - ";
		} else {
			ppSqftString = Double.toString(ppSqft);
			sqftString = Double.toString(sqft);
		}
		model.addRow(new Object[] {item, ppSqftString, sqftString, totalCost});
	}
	
	private String checkInput() {
		String itemString = itemField.getText();
		if(itemString.equals(""))
			return "What should we call the Item?";
		
		if(sqft) {
			String sqftString = sqftField.getText();
			String ppSqftString = ppSqftField.getText();
			if(!isNumeric(sqftString)) {
				return "How many square feet?";
			} else if(!isNumeric(ppSqftString)) {
				return "How much per square-foot?";
			} else {
				return "success";
			}
		} else {
			String totalPriceString = priceField.getText();
			if(isNumeric(totalPriceString)) {
				return "success";
			} else {
				return "Enter a Price.";
			}
		}
	}
	
	private boolean isNumeric(String s) {
		try {
			double n = Double.parseDouble(s);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private class MessageTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			messageTimer.stop();
			messageLabel.setText("");
		}
	}
	private void setMessage(String message, boolean error) {
		if(error)
			messageLabel.setForeground(Color.RED);
		else
			messageLabel.setForeground(new Color(0,128,0));
		messageLabel.setText(message);
		messageTimer.start();
		
	}
	
	private void clearFields() {
		itemField.setText("");
		priceField.setText("");
		ppSqftField.setText("");
		sqftField.setText("");
	}
	
	private class TableListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			if(row < 0)
				throw new IllegalArgumentException("ROW INDEX NOT POSSIBLE...");
			String item = table.getValueAt(row, 0).toString();
			String ppSqft_String = table.getValueAt(row, 1).toString();
			String sqft_String = table.getValueAt(row, 2).toString();
			String totalPrice_String = table.getValueAt(row, 3).toString();
			
			itemField.setText(item);
			priceField.setText(totalPrice_String);
			if(ppSqft_String.equals(" - ")) {
				ppSqftField.setText("");
				sqftField.setText("");
			} else {
				ppSqftField.setText(ppSqft_String);
				sqftField.setText(sqft_String);
			}
			
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	}
	
	public void updateTotalRenCost() {
		double totalCost = home.project.totalRenCost;
		totalCostLbl.setText(home.project.money.format(totalCost));
	}
	
	public void updateTableOnOpen() {
		
		while(table.getRowCount() != 0)
			model.removeRow(0);
		
		for(int i = 0; i < home.project.renovationItems.size(); i++) {
			String item = home.project.renovationItems.get(i).name;
			double totalPrice = home.project.renovationItems.get(i).totalPrice;
			double sqft = home.project.renovationItems.get(i).sqft;
			double ppSqft = home.project.renovationItems.get(i).ppSqft;
			openAddRow(item, sqft, ppSqft, totalPrice);
		}
	}
}
