package ch.zhaw.ads.p10.gui;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
import javax.swing.JTable;
import javax.swing.JScrollPane;

@Configuration
@ComponentScan({ "ch.zhaw.ads.p10" })
@Component
public class MainPanel extends JDialog {
	static MainPanel theMainPanel;
	private TextAnalyzer textAnalyzer;

	private JPanel pnPanel0;
	private JButton btLoadDataButton;
	private JProgressBar pbProgressBar;
	private JTabbedPane tbpTabbedPane0;

	private JPanel logPanel;
	private LogArea logArea;
	private JScrollPane scpLog;

	private JPanel pnPanel2;
	private JTextField searchTextField;
	private JButton btSearch;
	private WordListTable wordListTable;
	private ButtonClickedListener buttonListener;

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		ApplicationContext context = new AnnotationConfigApplicationContext(MainPanel.class);
		MainPanel demo = (MainPanel) context.getBean("mainPanel");
		demo.start();
	}

	public void start() {
		drawComponents();
		registerComponents();
		enableDisableComponentStates(false);
		btLoadDataButton.setEnabled(true);
	}

	public void updateFolderSelection(File folder) {
		btLoadDataButton.setEnabled(false);
		pbProgressBar.setMaximum(100);
		pbProgressBar.setValue(0);
		textAnalyzer.beginCache(folder);
	}

	public void enableDisableComponentStates(boolean isEnabled) {
		tbpTabbedPane0.setEnabled(isEnabled);
		logArea.setEditable(isEnabled);
		btLoadDataButton.setEnabled(isEnabled);
	}

	private void registerComponents() {
		btLoadDataButton.addActionListener(buttonListener);
		btSearch.addActionListener(buttonListener);
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

		scpLog = new JScrollPane(logArea);
		gbcPanel1.gridx = 0;
		gbcPanel1.gridy = 0;
		gbcPanel1.gridwidth = 18;
		gbcPanel1.gridheight = 12;
		gbcPanel1.fill = GridBagConstraints.BOTH;
		gbcPanel1.weightx = 1;
		gbcPanel1.weighty = 1;
		gbcPanel1.anchor = GridBagConstraints.NORTH;
		gbPanel1.setConstraints(scpLog, gbcPanel1);
		logPanel.add(scpLog);
		tbpTabbedPane0.addTab("Log", logPanel);

		pnPanel2 = new JPanel();
		GridBagLayout gbPanel2 = new GridBagLayout();
		GridBagConstraints gbcPanel2 = new GridBagConstraints();
		pnPanel2.setLayout(gbPanel2);

		searchTextField = new JTextField();
		gbcPanel2.gridx = 0;
		gbcPanel2.gridy = 0;
		gbcPanel2.gridwidth = 12;
		gbcPanel2.gridheight = 1;
		gbcPanel2.fill = GridBagConstraints.BOTH;
		gbcPanel2.weightx = 1;
		gbcPanel2.weighty = 0;
		gbcPanel2.anchor = GridBagConstraints.NORTH;
		gbPanel2.setConstraints(searchTextField, gbcPanel2);
		pnPanel2.add(searchTextField);

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
		
		JScrollPane scpFrequency = new JScrollPane(wordListTable);
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
	
	public String getSearchExpression() {
		return searchTextField.getText();
	}

	public JProgressBar getPbProgressBar() {
		return pbProgressBar;
	}

	public void loadWords() {
		logArea.addLog("Analysing data...");
		textAnalyzer.startAnalysis();
	}

	@Autowired
	public final void setLogArea(LogArea logArea) {
		this.logArea = logArea;
	}

	@Autowired
	public final void setTextAnalyzer(TextAnalyzer textAnalyzer) {
		this.textAnalyzer = textAnalyzer;
	}
	
	@Autowired
	public final void setWordListTable(WordListTable wordListTable) {
		this.wordListTable = wordListTable;
	}
	
	@Autowired
	public void setButtonListener(ButtonClickedListener buttonListener) {
		this.buttonListener = buttonListener;
	}
}