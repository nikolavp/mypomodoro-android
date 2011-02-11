package com.mypomodoro;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.mypomodoro.data.Task;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

/**
 * An activity that should fire up when the user selects a name of a task so he
 * can edit it's info.
 * 
 * @author nikolavp
 * 
 */
public class EditForm extends PomodoroActivity {
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

		Bundle intentExtras = getIntent().getExtras();
		setContentView(R.layout.form);
		((ViewStub) findViewById(R.id.edit_button_stub)).inflate();

		Button createButton = (Button) findViewById(R.id.edit_button);
		nameField = (EditText) findViewById(R.id.task_name);
		deadlineField = (EditText) findViewById(R.id.task_deadline_date);
		deadlineField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_DIALOG);
			}
		});
		Spinner typeSpinner = (Spinner) findViewById(R.id.task_type_spinner);
		estimatedPomodorosField = (EditText) findViewById(R.id.task_estimated_pomodoros);
		createButton.setOnClickListener(new EditButtonClickListener(this));

		PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(this);
		
		TaskDao taskDao = new TaskDao(helper);
		Task task = taskDao.load(intentExtras.getInt(SheetsActivity.TASK_ID));

		nameField.setText(task.getName());
		Date deadline = task.getDeadline();
		if (deadline != null) {
			deadlineField.setText(Dates.DATE_FORMATTER.format(deadline));
		}
		estimatedPomodorosField.setText(""+task.getEstimatedPomodoros());
		typeSpinner.setSelection(task.getType().ordinal());
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
