package ch.zhaw.ads.p10.gui;

import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.springframework.stereotype.Component;

@Component
public class LogArea extends JTextArea {
	private JTextArea taLog;
	
	public LogArea() {
		taLog = new JTextArea(2, 10);
	}
	
	public void addLog(String logMsg) {
		String currentLog = taLog.getText();
		currentLog += "\n" + logMsg;
		taLog.setText(currentLog);
		Document doc = taLog.getDocument();
		taLog.setCaretPosition(doc.getLength());
	}
	
	public JTextArea getLogTextArea(){
		return taLog;
	}
}
