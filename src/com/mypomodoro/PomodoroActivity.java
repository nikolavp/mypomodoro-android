package com.mypomodoro;


import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.mypomodoro.menu.PomodoroMenu;

/**
 * An abstract class to contain the logic for pomodoro menu setup. This menu is
 * the same for every top level activity in the project.
 * 
 * @author nikolavp
 * 
 */
public abstract class PomodoroActivity extends Activity {
	private PomodoroMenu pomodoroMenu;

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
