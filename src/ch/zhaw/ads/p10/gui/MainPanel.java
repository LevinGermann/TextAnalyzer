package ch.zhaw.ads.p10.gui;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import ch.zhaw.ads.p10.TextAnalyzer;
import ch.zhaw.ads.p10.gui.listeners.ButtonClickedListener;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class MainPanel extends JDialog {
	static MainPanel theMainPanel;
	private TextAnalyzer textAnalyzer;

	JPanel pnPanel0;
	JButton btLoadDataButton;
	JProgressBar pbProgressBar;
	JTabbedPane tbpTabbedPane0;

	JPanel logPanel;
	JTextArea taLog;
	JScrollPane scpLog;

	JPanel pnPanel2;
	JTextField tfText0;
	JButton btSearch;
	JTable tbFrequency;

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		theMainPanel = new MainPanel();
		
	}

	public MainPanel() {
		textAnalyzer = new TextAnalyzer();
		drawComponents();
		registerComponents();
		enableDisableComponentStates(false);
		btLoadDataButton.setEnabled(true);
	}
	
	public void updateFolderSelection(File folder) {
		try {
			btLoadDataButton.setEnabled(false);
			pbProgressBar.setMaximum(100);
			pbProgressBar.setValue(0);
			textAnalyzer.beginCache(folder, this);		
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void enableDisableComponentStates(boolean isEnabled) {
		tbpTabbedPane0.setEnabled(isEnabled);
		taLog.setEditable(isEnabled);
		btLoadDataButton.setEnabled(isEnabled);
	}

	private void registerComponents() {
		btLoadDataButton.addActionListener(new ButtonClickedListener(this));
	}

	private void drawComponents() {
		pnPanel0 = new JPanel();
		pnPanel0.setBorder(BorderFactory.createTitledBorder("ADS Praktikum 10"));
		GridBagLayout gbPanel0 = new GridBagLayout();
		GridBagConstraints gbcPanel0 = new GridBagConstraints();
		pnPanel0.setLayout(gbPanel0);

		btLoadDataButton = new JButton("Load Data");
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints(btLoadDataButton, gbcPanel0);
		pnPanel0.add(btLoadDataButton);

		pbProgressBar = new JProgressBar();
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 1;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints(pbProgressBar, gbcPanel0);
		pnPanel0.add(pbProgressBar);

		tbpTabbedPane0 = new JTabbedPane();

		logPanel = new JPanel();
		GridBagLayout gbPanel1 = new GridBagLayout();
		GridBagConstraints gbcPanel1 = new GridBagConstraints();
		logPanel.setLayout(gbPanel1);

		taLog = new JTextArea(2, 10);
		scpLog = new JScrollPane( taLog );
		gbcPanel1.gridx = 0;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 18;
		gbcPanel1.gridheight = 12;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 1;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints( scpLog, gbcPanel1 );
		//gbPanel1.setConstraints(taLog, gbcPanel1);
		logPanel.add( scpLog );
		tbpTabbedPane0.addTab("Log", logPanel);

		pnPanel2 = new JPanel();
		GridBagLayout gbPanel2 = new GridBagLayout();
		GridBagConstraints gbcPanel2 = new GridBagConstraints();
		pnPanel2.setLayout(gbPanel2);

		tfText0 = new JTextField();
		gbcPanel2.gridx = 0;
		gbcPanel2.gridy = 0;
		gbcPanel2.gridwidth = 12;
		gbcPanel2.gridheight = 1;
		gbcPanel2.fill = GridBagConstraints.BOTH;
		gbcPanel2.weightx = 1;
		gbcPanel2.weighty = 0;
		gbcPanel2.anchor = GridBagConstraints.NORTH;
		gbPanel2.setConstraints(tfText0, gbcPanel2);
		pnPanel2.add(tfText0);

		btSearch = new JButton("Search");
		gbcPanel2.gridx = 12;
		gbcPanel2.gridy = 0;
		gbcPanel2.gridwidth = 6;
		gbcPanel2.gridheight = 1;
		gbcPanel2.fill = GridBagConstraints.HORIZONTAL;
		gbcPanel2.weightx = 0;
		gbcPanel2.weighty = 0;
		gbcPanel2.anchor = GridBagConstraints.EAST;
		gbcPanel2.insets = new Insets(0, 6, 0, 0);
		gbPanel2.setConstraints(btSearch, gbcPanel2);
		pnPanel2.add(btSearch);

		String[][] dataFrequency = new String[][] { new String[] { "11", "21" }, new String[] { "12", "22" },
				new String[] { "13", "23" } };
		String[] colsFrequency = new String[] { "", "" };
		tbFrequency = new JTable(dataFrequency, colsFrequency);
		JScrollPane scpFrequency = new JScrollPane(tbFrequency);
		gbcPanel2.gridx = 0;
		gbcPanel2.gridy = 1;
		gbcPanel2.gridwidth = 18;
		gbcPanel2.gridheight = 11;
		gbcPanel2.fill = GridBagConstraints.BOTH;
		gbcPanel2.weightx = 1;
		gbcPanel2.weighty = 1;
		gbcPanel2.anchor = GridBagConstraints.NORTH;
		gbPanel2.setConstraints(scpFrequency, gbcPanel2);
		pnPanel2.add(scpFrequency);
		tbpTabbedPane0.addTab("Search", pnPanel2);
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints(tbpTabbedPane0, gbcPanel0);
		pnPanel0.add(tbpTabbedPane0);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setContentPane(pnPanel0);
		pack();
		setVisible(true);
	}

	public JProgressBar getPbProgressBar() {
		return pbProgressBar;
	}
	
	public void addLog(String logMsg) {
		String currentLog = taLog.getText();
		currentLog += "\n" + logMsg;
		taLog.setText(currentLog);
		Document doc = taLog.getDocument();
		taLog.setCaretPosition(doc.getLength());
	}

	public void loadWords() {
		addLog("Analysing data...");
		textAnalyzer.startAnalysis();
		
	}
}