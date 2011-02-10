package com.mypomodoro;

import java.util.Date;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

public class CreateButtonClickListener implements OnClickListener {
	private final CreateForm form;

	public CreateButtonClickListener(CreateForm form) {
		this.form = form;
	}

	@Override
	public void onClick(View arg0) {
		int estimatedPomodoros = -1;
		try {
			estimatedPomodoros = Integer.parseInt(form.estimatedPomodorosField
					.getText().toString());
		} catch (NumberFormatException ex) {
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

		Task task = new Task();
		task.setDateCreated(new Date());
		task.setEstimatedPomodoros(estimatedPomodoros);
		task.setName(name);

		PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(form);
		TaskDao taskDao = new TaskDao(helper);
		try {
			taskDao.save(task);
			Toast.makeText(form, "Activity saved", 1000).show();
			form.clear();
		} finally {
			taskDao.closeQuietly();
		}

	}
}
