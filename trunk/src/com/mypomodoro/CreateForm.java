package com.mypomodoro;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class CreateForm extends Activity {
	EditText nameField;
	EditText placeField;
	EditText estimatedPomodorosField;
	private PomodoroMenu pomodoroMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create);

		Button createButton = (Button) findViewById(R.id.createButton);
		nameField = (EditText) findViewById(R.id.activityNameTextField);
		placeField = (EditText) findViewById(R.id.placeTextField);
		estimatedPomodorosField = (EditText) findViewById(R.id.activityEstimatedPomodoros);
		createButton.setOnClickListener(new CreateButtonClickListener(this));
	}


	void clear() {
		nameField.setText("");
		placeField.setText("");
		estimatedPomodorosField.setText("");
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
