package com.mypomodoro;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

public class EditButtonClickListener implements OnClickListener {
	private final EditForm form;
	private final Task task;

	public EditButtonClickListener(Task task, EditForm form) {
		this.form = form;
		this.task = task;
	}

	@Override
	public void onClick(View v) {
		int estimatedPomodoros = -1;
		try {
			estimatedPomodoros = Integer.parseInt(form.estimatedPomodorosField
					.getText().toString());
		} catch (NumberFormatException e) {
			Toast.makeText(form,
					form.getString(R.string.invalid_pomodoros_number), 1000)
					.show();
			return;
		}
		String name = form.nameField.getText().toString();
		if (name.length() == 0) {
			Toast.makeText(form, form.getString(R.string.invalid_form_input),
					1000).show();
			return;
		}

		task.setEstimatedPomodoros(estimatedPomodoros);
		task.setName(name);

		PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(form);
		TaskDao taskDao = new TaskDao(helper);
		try {
			taskDao.update(task);
			Toast.makeText(form, form.getString(R.string.activity_edited), 1000)
					.show();
		} finally {
			helper.close();
			taskDao.closeQuietly();
		}
	}
}
