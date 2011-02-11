package com.mypomodoro;

import java.text.ParseException;
import java.util.Date;

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
		if (deadline.length() > 0) {
			try {
				Date deadlineDate = Dates.DATE_FORMATTER.parse(deadline);
				task.setDeadline(deadlineDate);
			} catch (ParseException e) {
				Toast.makeText(form, R.string.invalid_deadline_input, 1000)
						.show();
				return;
			}
		}
		task.setEstimatedPomodoros(estimatedPomodoros);
		task.setName(name);
		task.setType(type);
		
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
