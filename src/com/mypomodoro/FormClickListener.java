package com.mypomodoro;

import java.text.ParseException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.data.TaskType;

public class FormClickListener implements OnClickListener {
	final Form form;
	int estimatedPomodoros = -1;
	String name;
	String deadline;
	TaskType type;

	public FormClickListener(Form form) {
		this.form = form;
	}

	@Override
	public void onClick(View v) {

		name = form.nameField.getText().toString();

		deadline = form.deadlineField.getText().toString();
		String spinnerValue = (String) form.typeSpinner.getSelectedItem();
		type = TaskType.valueOf(spinnerValue.toUpperCase());
	}

	public boolean populateTask(Task task) {
		if (name.length() == 0) {
			Toast.makeText(form, form.getString(R.string.invalid_form_input),
					1000).show();
			return false;
		}
		try {
			estimatedPomodoros = Integer.parseInt(form.estimatedPomodorosField
					.getText().toString());
			task.setEstimatedPomodoros(estimatedPomodoros);
		} catch (NumberFormatException ex) {
			Toast.makeText(form,
					form.getString(R.string.invalid_pomodoros_number), 1000)
					.show();
			return false;
		}
		if (deadline.length() > 0) {
			try {
				task.setDeadline(Dates.DATE_FORMATTER.parse(deadline));
			} catch (ParseException e) {
				Toast.makeText(form,
						form.getString(R.string.invalid_deadline_input), 1000)
						.show();
				return false;
			}
		}
		
		task.setType(type);
		task.setName(name);
		return true;
	}
}
