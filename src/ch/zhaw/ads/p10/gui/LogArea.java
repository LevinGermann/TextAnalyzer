package ch.zhaw.ads.p10.gui;

import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.springframework.stereotype.Component;

@Component
public class LogArea extends JTextArea {
	
	public LogArea() {
		super(2, 10);
	}
	
	public void addLog(String logMsg) {
		String currentLog = getText();
		currentLog += "\n" + logMsg;
		setText(currentLog);
		Document doc = getDocument();
		setCaretPosition(doc.getLength());
	}
}
