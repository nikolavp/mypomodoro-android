package com.mypomodoro;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

/**
 * 
 * @author nikolavp
 * @deprecated Just use a simple list of activity names. We are cluttering the
 *             code too much with this stuff
 */
@Deprecated
public class TableViewGenerator {

	boolean firstCellForRow;

	private TableRow row;

	private final Context context;

	private final TableLayout tableLayout;

	public TableViewGenerator(Context context, TableLayout tableLayout) {
		this.context = context;
		this.tableLayout = tableLayout;
		startRow();
	}

	private static final TableRow.LayoutParams CELL_PARAMS = getParamBetweenCells();
	private static final TableRow.LayoutParams ROW_PARAMS = getRowParams();

	private static LayoutParams getParamBetweenCells() {
		LayoutParams layoutParams = new TableRow.LayoutParams();
		layoutParams.setMargins(2, 0, 0, 0);
		return layoutParams;
	}

	private static LayoutParams getRowParams() {
		LayoutParams layoutParams = new TableRow.LayoutParams();
		layoutParams.setMargins(0, 2, 0, 0);
		return layoutParams;
	}

	public void addTextCell(String text) {
		TextView textView = new TextView(context);
		textView.setBackgroundResource(R.drawable.cell_background);
		textView.setTextColor(Color.BLACK);
		textView.setText(text);

		if (firstCellForRow) {
			row.addView(textView);
			firstCellForRow = false;
		} else {
			row.addView(textView, CELL_PARAMS);
		}
	}

	public void startRow() {
		firstCellForRow = true;
		row = new TableRow(context);
		// row.setBackgroundResource(R.color.border);
		tableLayout.addView(row, ROW_PARAMS);
	}

}
