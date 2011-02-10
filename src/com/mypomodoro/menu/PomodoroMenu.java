package com.mypomodoro.menu;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mypomodoro.CreateForm;
import com.mypomodoro.PomodoroTimerActivity;
import com.mypomodoro.R;
import com.mypomodoro.SheetsActivity;

public class PomodoroMenu {
	private final Activity activity;

	public PomodoroMenu(Activity activity, Menu menu) {
		this.activity = activity;
		MenuInflater inflater = new MenuInflater(activity);
		inflater.inflate(R.menu.main_menu, menu);
	}

	private boolean fireActivityClass(Class<?> clazz) {
		Intent intent = new Intent(activity, clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		activity.startActivity(intent);
		return true;
	}

	public boolean selectItem(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create_menu_item:
			return fireActivityClass(CreateForm.class);
		case R.id.todosheet_menu_item:
			return fireActivityClass(SheetsActivity.class);
		case R.id.pomodor_menu_item:
			return fireActivityClass(PomodoroTimerActivity.class);
		default:
			return activity.onOptionsItemSelected(item);
		}

	}
}
