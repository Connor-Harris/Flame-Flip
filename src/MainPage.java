import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainPage extends JFrame {

	private Home home;
	private JButton executeBtn, analyzeBtn;
	
	private JMenuItem saveAsItem;
	private JMenuItem saveItem;
	private JMenuItem openItem;
	//JMenuBar Mode
	private JRadioButtonMenuItem executeItem;
	private JRadioButtonMenuItem analysisItem;
	
	public static void main(String[] args) {
		
		MainPage mainPage = new MainPage();
		mainPage.setVisible(true);
		
	}
	
	public MainPage() {
		
		double screenWidth = Toolkit.getDefaultToolkit()
							.getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit()
								.getScreenSize().getHeight();
		
		/***PROJECT STARTING POINT***/
		Project project = new Project();
		home = new Home(project);
		
		home.project.mainPage = this;
		
		this.setBounds(100,50,1400,950);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("Flip Calculator");
		JTabbedPane pane = new JTabbedPane();
		pane.setFont(home.project.tabFont);
		
		pane.addTab("Details", detailPanel());
		pane.addTab("Costs", costPanel());
		pane.addTab("Results", resultPanel());
		
		JLabel modeTitle = new JLabel("Current Mode: ");
			modeTitle.setPreferredSize(new Dimension(200,40));
			modeTitle.setFont(home.project.titleFont);
			modeTitle.setForeground(Color.WHITE);
		
		executeBtn = new JButton("Execute");
		analyzeBtn = new JButton("Analyze");
			executeBtn.setForeground(Color.WHITE);
			executeBtn.setBackground(home.project.LIGHT_BLUE);
			analyzeBtn.setForeground(Color.WHITE);
			analyzeBtn.setBackground(home.project.LIGHT_BLUE);
			executeBtn.setFont(home.project.tabFont);
			analyzeBtn.setFont(home.project.tabFont);
			ModeListener ml = new ModeListener();
			analyzeBtn.addActionListener(ml);
			executeBtn.addActionListener(ml);
			Dimension btnDim = new Dimension
					((int) screenWidth/6, (int) screenHeight/20);
			executeBtn.setPreferredSize(btnDim);
			analyzeBtn.setPreferredSize(btnDim);
			
		JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		modePanel.setBackground(home.project.DARK_BLUE);
		modePanel.add(modeTitle); 
		modePanel.add(analyzeBtn); modePanel.add(executeBtn);
		modePanel.setPreferredSize(new Dimension
				((int)screenWidth, (int)screenHeight/10));
		modePanel.setBorder(new EmptyBorder((int)screenHeight/50, 0,0,0));
		
		//Beginning in analyze mode
		executeBtn.setVisible(false);
		
		/***MENU BAR***/
		
		Font menuFont = new Font("Monospace",Font.BOLD, (int)screenHeight/50);
		
		SaveListener saveListener = new SaveListener();
		OpenListener openListener = new OpenListener();
		
		Dimension menuItemDim = new Dimension((int)screenWidth/10, (int)screenHeight/25);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(home.project.DARK_BLUE);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setForeground(Color.WHITE);
		fileMenu.setHorizontalAlignment(SwingConstants.CENTER);
		fileMenu.setPreferredSize(new Dimension((int)screenWidth/20,(int)screenHeight/25));
		fileMenu.setFont(menuFont);
		saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(saveListener);
		saveAsItem.setFont(menuFont);
		saveAsItem.setPreferredSize(menuItemDim);
		saveItem = new JMenuItem("Save");
		saveItem.setFont(menuFont);
		saveItem.addActionListener(saveListener);
		saveItem.setPreferredSize(menuItemDim);
		saveItem.setAccelerator(KeyStroke.getKeyStroke
				('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openItem = new JMenuItem("Open");
		openItem.setFont(menuFont);
		openItem.addActionListener(openListener);
		openItem.setPreferredSize(menuItemDim);
		openItem.setAccelerator(KeyStroke.getKeyStroke
				('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileMenu.add(saveAsItem);
		fileMenu.add(saveItem);
		fileMenu.add(openItem);
		
		JMenu modeMenu = new JMenu("Mode");
		modeMenu.setForeground(Color.WHITE);
		modeMenu.setHorizontalAlignment(SwingConstants.CENTER);
		modeMenu.setPreferredSize(new Dimension((int)screenWidth/20,(int)screenHeight/25));
		modeMenu.setFont(menuFont);
		executeItem = new JRadioButtonMenuItem("Execute");
		executeItem.addActionListener(ml);
		executeItem.setFont(menuFont);
		executeItem.setPreferredSize(menuItemDim);
		executeItem.setAccelerator(KeyStroke.getKeyStroke
						('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		analysisItem = new JRadioButtonMenuItem("Analyze");
		analysisItem.setSelected(true);
		analysisItem.addActionListener(ml);
		analysisItem.setFont(menuFont);
		analysisItem.setPreferredSize(menuItemDim);
		analysisItem.setAccelerator(KeyStroke.getKeyStroke
				('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		modeMenu.add(analysisItem);
		modeMenu.add(executeItem);
		
		menuBar.add(fileMenu);
		menuBar.add(modeMenu);
		
		
		menuBar.setPreferredSize(new Dimension((int)screenWidth, (int)screenHeight/25));
		
		this.setLayout(new BorderLayout());
		this.add(menuBar, BorderLayout.NORTH);
		this.add(pane, BorderLayout.CENTER);
		this.add(modePanel, BorderLayout.SOUTH);
	}
	
	private JPanel detailPanel() {
		DetailPanel dp = new DetailPanel();
		home.project.detailPanel = dp;
		return dp;
	}
	private JPanel costPanel() {
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1.4;
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.45;
		JPanel costPanel = new JPanel();
		JTabbedPane costPane = new JTabbedPane();
		costPane.setFont(home.project.textFont);
		costPane.setPreferredSize(new Dimension((int)width, (int)height));
		costPanel.setPreferredSize(new Dimension((int)width, (int)height));
		RenCostPanel rcp = new RenCostPanel();
		FixedCosts fc = new FixedCosts();
		costPane.addTab("Renovation Costs", rcp);
		costPane.addTab("Fixed Costs", fc);
		costPanel.add(costPane);
		home.project.renCostPanel = rcp;
		home.project.fixedCosts = fc;
		return costPanel;
	}
	private JPanel resultPanel() {
		ResultPanel rp = new ResultPanel();
		home.project.resultPanel = rp;
		return rp;
	}
	
	private class ModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == executeBtn || e.getSource() == analysisItem) {
				executeItem.setSelected(false);
				analysisItem.setSelected(true);
				
				analyzeBtn.setVisible(true);
				executeBtn.setVisible(false);
				home.project.changeMode(false); //changes mode to analyze
			} else if(e.getSource() == analyzeBtn || e.getSource() == executeItem) {
				executeItem.setSelected(true);
				analysisItem.setSelected(false);
				
				analyzeBtn.setVisible(false);
				executeBtn.setVisible(true);
				home.project.changeMode(true); //changes mode to execute
			}
		}
	}
	
	private class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == saveItem) {
				Save save = new Save();
			} else if(e.getSource() == saveAsItem) {
				SaveAs saveAs = new SaveAs();
			}
		}
	}
	private class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == openItem) {
				Open open = new Open();
			}
		}
	}
	
}
