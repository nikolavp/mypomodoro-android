package com.mypomodoro;

import java.util.Date;

import android.os.Bundle;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.mypomodoro.data.Task;

/**
 * An activity that should fire up when the user selects a name of a task so he
 * can edit it's info.
 * 
 * @author nikolavp
 * 
 */
public class EditForm extends Form {
	private static String spentPreffix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		spentPreffix = getString(R.string.pomodoros_spent);
		Bundle intentExtras = getIntent().getExtras();
		int taskId = intentExtras.getInt(SheetsActivity.TASK_ID);

		((ViewStub) findViewById(R.id.edit_button_stub)).inflate();

		Button editButton = (Button) findViewById(R.id.edit_button);

		TextView pomodorosSpentField = (TextView) findViewById(R.id.actual_pomodoros_edit_view);
		Task task = taskDao.load(taskId);

		editButton.setOnClickListener(new EditButtonClickListener(task, this));

		Spinner typeSpinner = (Spinner) findViewById(R.id.task_type_spinner);
		
		nameField.setText(task.getName());
		Date deadline = task.getDeadline();
		if (deadline != null) {
			deadlineField.setText(Dates.DATE_FORMATTER.format(deadline));
		}
		pomodorosSpentField.setText(spentPreffix + task.getActualPomodoros());
		estimatedPomodorosField.setText("" + task.getEstimatedPomodoros());
		typeSpinner.setSelection(task.getType().ordinal());
	}



}
