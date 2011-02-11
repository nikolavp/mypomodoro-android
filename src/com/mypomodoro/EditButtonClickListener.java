package com.mypomodoro;

import android.view.View;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

public class EditButtonClickListener extends FormClickListener {

	private final Task task;

	public EditButtonClickListener(Task task, Form form) {
		super(form);
		this.task = task;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (!populateTask(task)) {
			return;
		}
		PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(form);
		TaskDao taskDao = new TaskDao(helper);
		try {
			taskDao.update(task);
			Toast
					.makeText(form, form.getString(R.string.activity_edited),
							1000).show();
		} finally {
			helper.close();
			taskDao.closeQuietly();
		}
		Activities.fireActivityClass(form, SheetsActivity.class);
	}
}
