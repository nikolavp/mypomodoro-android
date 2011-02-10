package com.mypomodoro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

import com.mypomodoro.data.Task;
import com.mypomodoro.data.TaskType;
import com.mypomodoro.db.PomodoroDatabaseHelper;

public class SheetsActivity extends PomodoroActivity implements
		TabContentFactory {

	private static final String TODO = "todo";
	private static final String UNPLANNED = "unplanned";
	private static final String URGENT = "urgent";

	private static final int ITEM_SELECTED_DIALOG = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todosheet);

		TabHost host = (TabHost) findViewById(R.id.tabhost);
		host.setup();
		TabSpec spec = host.newTabSpec(TODO);
		spec.setIndicator(getString(R.string.todo));
		spec.setContent(this);
		host.addTab(spec);

		spec = host.newTabSpec(URGENT);
		spec.setIndicator(getString(R.string.urgent));
		spec.setContent(this);
		host.addTab(spec);

		spec = host.newTabSpec(UNPLANNED);
		spec.setIndicator(getString(R.string.unplanned));
		spec.setContent(this);
		host.addTab(spec);
	}

	private void initSheet(ListView list, TaskType type) {
		PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		try {
			Cursor cursor = db.query(PomodoroDatabaseHelper.TABLE_NAME,
					new String[] { Task._ID, Task.NAME }, "type = "
							+ type.ordinal(), null, null, null, null);

			String[] from = new String[] { "name" };
			int[] to = new int[] { R.id.task_item_name };
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
					R.layout.list_item, cursor, from, to);
			list.setAdapter(adapter);
		} finally {
			db.close();
		}
	}

	@Override
	public View createTabContent(String tag) {
		ListView list = new ListView(this);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				showDialog(ITEM_SELECTED_DIALOG);
			}
		});
		if (TODO.equals(tag)) {
			initSheet(list, TaskType.NORMAL);
		} else if (URGENT.equals(tag)) {
			initSheet(list, TaskType.URGENT);
		} else if (UNPLANNED.equals(tag)) {
			initSheet(list, TaskType.UNPLANNED);
		} else {
			throw new IllegalArgumentException("Unsupported tag name");
		}
		return list;
	}

	private final OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == 0) {
				// TODO: Start edit activity
			} else if (which == 1) {
				// TODO: Start the pomodoro activity
			}
		}
	};

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		if (id == ITEM_SELECTED_DIALOG) {
			String[] items = getResources().getStringArray(
					R.array.item_selected_action);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
			.setTitle(getString(R.string.item_clicked_action_title))
			.setItems(items, onClickListener)
			.setNegativeButton(getString(R.string.cancel), new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}
		return null;
	}
}
