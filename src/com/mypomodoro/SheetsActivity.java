package com.mypomodoro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

import com.mypomodoro.data.Task;
import com.mypomodoro.data.TaskType;
import com.mypomodoro.db.PomodoroDatabaseHelper;

public class SheetsActivity extends PomodoroActivity implements
		TabContentFactory {

	private static final int ITEM_SELECTED_DIALOG = 0;
	public static final String TASK_ID = "TASK_ID";

	/**
	 * From fields for every task item in the database
	 */
	private static final String[] from = new String[] { Task.NAME };
	/**
	 * Map the from fields to this array of view ids.
	 */
	private static final int[] to = new int[] { R.id.task_item_name };

	private TabHost host;

	private ListView todoList;

	private ListView urgetList;

	private ListView unplannedList;
	private SQLiteDatabase db;
	private PomodoroDatabaseHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sheet);

		helper = new PomodoroDatabaseHelper(this);
		db = helper.getReadableDatabase();

		host = (TabHost) findViewById(R.id.tabhost);
		host.setup();
		TabSpec spec = host.newTabSpec(TaskType.NORMAL.toString());
		spec.setIndicator(getString(R.string.todo));
		spec.setContent(this);
		host.addTab(spec);

		spec = host.newTabSpec(TaskType.URGENT.toString());
		spec.setIndicator(getString(R.string.urgent));
		spec.setContent(this);
		host.addTab(spec);

		spec = host.newTabSpec(TaskType.UNPLANNED.toString());
		spec.setIndicator(getString(R.string.unplanned));
		spec.setContent(this);
		host.addTab(spec);

		host.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				refreshTab(tabId);
			}
		});
	}

	/**
	 * This will reinstatiate the cursor for this tabTag and refresh the whole
	 * content in it.
	 * 
	 * @param tabTag
	 */
	private void refreshTab(String tabTag) {
		TaskType sheetType = TaskType.valueOf(tabTag);
		if (sheetType == TaskType.NORMAL) {
			todoList.setAdapter(getSheetAdapter(sheetType));
		} else if (sheetType == TaskType.UNPLANNED) {
			unplannedList.setAdapter(getSheetAdapter(sheetType));
		} else if (sheetType == TaskType.URGENT) {
			urgetList.setAdapter(getSheetAdapter(sheetType));
		}
		Log.i(SheetsActivity.class.getName(), "Refreshing tab " + tabTag);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshTab(host.getCurrentTabTag());
	}

	/**
	 * Gets the sheet cursor adapter for this type of tasks.
	 * 
	 * @param type
	 * @return
	 */
	private SimpleCursorAdapter getSheetAdapter(TaskType type) {
		Cursor cursor = db.query(PomodoroDatabaseHelper.TABLE_NAME,
				new String[] { Task._ID, Task.NAME }, "type = '"
						+ type.toString() + "'", null, null, null, null);
		Log.d("database", cursor.getCount() + " records loaded from database.");
		startManagingCursor(cursor);

		return new SimpleCursorAdapter(this, R.layout.list_item, cursor, from,
				to);

	}

	@Override
	public View createTabContent(String tag) {

		TaskType sheetType = TaskType.valueOf(tag);
		SimpleCursorAdapter adapter = getSheetAdapter(sheetType);
		ListView list;
		if (sheetType == TaskType.NORMAL) {
			list = todoList = new ListView(this);
			todoList.setAdapter(adapter);
		} else if (sheetType == TaskType.URGENT) {
			list = urgetList = new ListView(this);
			urgetList.setAdapter(adapter);
		} else if (sheetType == TaskType.UNPLANNED) {
			list = unplannedList = new ListView(this);
			unplannedList.setAdapter(adapter);
		} else {
			throw new IllegalArgumentException("Unsupported tag name");
		}

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putInt(TASK_ID, (int) id);
				showDialog(ITEM_SELECTED_DIALOG, bundle);
			}
		});

		return list;
	}

	@Override
	protected Dialog onCreateDialog(int id, final Bundle args) {
		if (id == ITEM_SELECTED_DIALOG) {
			String[] items = getResources().getStringArray(
					R.array.item_selected_action);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
					.setTitle(getString(R.string.item_clicked_action_title))
					.setItems(items, new ItemAction(this, args.getInt(TASK_ID)))
					.setNegativeButton(getString(R.string.cancel),
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			builder.show();
		}
		return null;
	}

	/**
	 * Action class that will fire the class that was selected with the dialog
	 * on every item from the list.
	 * 
	 * @author nikolavp
	 * 
	 */
	private static class ItemAction implements OnClickListener {
		private final int id;
		private final Context context;

		public ItemAction(Context context, int id) {
			this.id = id;
			this.context = context;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = null;
			if (which == 0) {
				intent = new Intent(context, EditForm.class);
			} else if (which == 1) {
				intent = Activities.getIntentForClass(context, PomodoroTimerActivity.class);
			}
			intent.putExtra(TASK_ID, id);
			context.startActivity(intent);
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
		db.close();
	}
}
