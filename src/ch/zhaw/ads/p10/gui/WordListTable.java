package ch.zhaw.ads.p10.gui;

import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.ads.p10.TextAnalyzer;
import ch.zhaw.ads.p10.gui.comparators.FrequencyComparator;
import ch.zhaw.ads.p10.gui.comparators.WordComparator;

@Component
public class WordListTable extends JTable {
	private TextAnalyzer textAnalyzer;

	public WordListTable() {
		super(new DefaultTableModel(new String[][] {}, new String[] { "Word", "Frequency" }));
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
		setRowSorter(sorter);
		sorter.setModel(getModel());

		sorter.setComparator(0, new WordComparator());
		sorter.setComparator(1, new FrequencyComparator());
	}

	public void addWord(String word, int frequency) {
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.addRow(new Object[] { word, frequency });
	}

	public void filterWord(String searchExpression) {
		DefaultTableModel model = (DefaultTableModel) getModel();
		Map<String, Integer> filtered = textAnalyzer.filterWords(searchExpression);
		emptyResults();
		filtered.forEach((word, frequency) -> {
			model.addRow(new Object[] { word, frequency });
		});
	}

	private void emptyResults() {
		DefaultTableModel model = (DefaultTableModel) getModel();
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
	}

	@Autowired
	public final void setTextAnalyzer(TextAnalyzer textAnalyzer) {
		this.textAnalyzer = textAnalyzer;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
