package com.mypomodoro;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class CreateForm extends PomodoroActivity {
	EditText nameField;
	EditText estimatedPomodorosField;
	EditText deadlineField;

	private static int DATE_PICKER_DIALOG = 0;

	private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, monthOfYear, dayOfMonth);
			updateDisplay(calendar);
		}

	};

	private void updateDisplay(Calendar calendar) {
		deadlineField.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(calendar.get(Calendar.DAY_OF_MONTH)).append("-")
				.append(calendar.get(Calendar.MONTH) + 1).append("-").append(
						calendar.get(Calendar.YEAR)).append(""));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create);

		Button createButton = (Button) findViewById(R.id.create_button);
		nameField = (EditText) findViewById(R.id.task_name);
		deadlineField = (EditText) findViewById(R.id.task_deadline_date);
		deadlineField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_DIALOG);
			}
		});
		estimatedPomodorosField = (EditText) findViewById(R.id.task_estimated_pomodoros);
		createButton.setOnClickListener(new CreateButtonClickListener(this));
	}

	void clear() {
		nameField.setText("");
		deadlineField.setText("");
		estimatedPomodorosField.setText("");
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar calendar = Calendar.getInstance();
		if (id == DATE_PICKER_DIALOG) {
			return new DatePickerDialog(this, dateSetListener, calendar
					.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
					.get(Calendar.DAY_OF_MONTH));
		}
		return null;
	}
}
