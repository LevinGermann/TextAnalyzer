package ch.zhaw.ads.p10.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import ch.zhaw.ads.p10.gui.MainPanel;

public class ButtonClickedListener implements ActionListener {
	private final MainPanel main;

	private final String LOAD_DATA = "Load Data";

	public ButtonClickedListener(MainPanel main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case LOAD_DATA:
			chooseFolder();
			break;
		default:
			break;
		}
	}

	private void chooseFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose the fileset folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = chooser.getSelectedFile();
			main.updateFolderSelection(selectedFolder);
		} else {
			System.out.println("No Selection ");
		}
	}

}
