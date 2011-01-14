package com.mypomodoro;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class ActivitiesPanel extends ListActivity {
	private PomodoroMenu pomodoroMenu;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> strings = new ArrayList<String>();
		strings.add("sdasd");
		strings.add("sadasdas");
		ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.caption,
				strings);
		setListAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return pomodoroMenu.selectItem(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		pomodoroMenu = new PomodoroMenu(this, menu);
		return super.onCreateOptionsMenu(menu);
	}
}