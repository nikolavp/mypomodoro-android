package com.mypomodoro;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

/**
 * Abstract class that represents a pomodoro item form.
 * 
 * @author nikolavp
 * 
 */
public class Form extends PomodoroActivity {
	EditText nameField;
	EditText estimatedPomodorosField;
	EditText deadlineField;

	PomodoroDatabaseHelper helper;
	TaskDao taskDao;

	static final int DATE_PICKER_DIALOG = 0;

	private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, monthOfYear, dayOfMonth);
			updateDisplay(calendar);
		}
	};
	Spinner typeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);

		helper = new PomodoroDatabaseHelper(this);
		taskDao = new TaskDao(helper);

		nameField = (EditText) findViewById(R.id.task_name);
		deadlineField = (EditText) findViewById(R.id.task_deadline_date);
		deadlineField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_DIALOG);
			}
		});

		typeSpinner = (Spinner) findViewById(R.id.task_type_spinner);
		estimatedPomodorosField = (EditText) findViewById(R.id.task_estimated_pomodoros);

	};

	/**
	 * Update the deadline field.
	 * 
	 * @param calendar
	 *            a calendar from which it will extract the info.
	 */
	private void updateDisplay(Calendar calendar) {
		deadlineField.setText(Dates.DATE_FORMATTER.format(calendar.getTime()));
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
		taskDao.closeQuietly();
	}

	/**
	 * Clear the fields of the form.
	 */
	void clear() {
		nameField.setText("");
		deadlineField.setText("");
		estimatedPomodorosField.setText("");
	}
}
