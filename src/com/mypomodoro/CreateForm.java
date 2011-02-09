package com.mypomodoro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateForm extends PomodoroActivity {
	EditText nameField;
	EditText placeField;
	EditText estimatedPomodorosField;

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

}
