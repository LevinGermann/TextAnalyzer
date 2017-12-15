package ch.zhaw.ads.p10.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.ads.p10.gui.MainPanel;
import ch.zhaw.ads.p10.gui.WordListTable;

@Component
public class ButtonClickedListener implements ActionListener {
	private MainPanel mainPanel;
	private WordListTable wordListTable;

	private final String LOAD_DATA = "Load Data";
	private final String SEARCH = "Search";

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case LOAD_DATA:
			chooseFolder();
			break;
		case SEARCH:
			search();
			break;
		default:
			break;
		}
	}

	private void search() {
		String toSearch = mainPanel.getSearchExpression();
		wordListTable.filterWord(toSearch);
	}

	private void chooseFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose the fileset folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = chooser.getSelectedFile();
			mainPanel.updateFolderSelection(selectedFolder);
		} else {
			System.out.println("No Selection ");
		}
	}

	@Autowired
	public final void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	@Autowired
	public void setWordListTable(WordListTable wordListTable) {
		this.wordListTable = wordListTable;
	}
}
