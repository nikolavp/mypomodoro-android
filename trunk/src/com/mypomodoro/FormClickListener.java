package com.mypomodoro;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

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
		try {
			estimatedPomodoros = Integer.parseInt(form.estimatedPomodorosField
					.getText().toString());
		} catch (NumberFormatException ex) {
			Toast.makeText(form,
					form.getString(R.string.invalid_pomodoros_number), 1000)
					.show();
			return;
		}
		name = form.nameField.getText().toString();
		if (name.length() == 0) {
			Toast.makeText(form, form.getString(R.string.invalid_form_input),
					1000).show();
			return;
		}
		deadline = form.deadlineField.getText().toString();
		String spinnerValue = (String) form.typeSpinner.getSelectedItem();
		type = TaskType.valueOf(spinnerValue.toUpperCase());
	}
}
