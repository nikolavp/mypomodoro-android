package com.mypomodoro;

import java.text.ParseException;
import java.util.Date;

import android.view.View;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

public class CreateButtonClickListener extends FormClickListener {
	/**
	 * Ctor
	 * 
	 * @param form
	 *            the form that will be used to create that new activity.
	 */
	public CreateButtonClickListener(CreateForm form) {
		super(form);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		Task task = new Task();
		task.setType(type);
		task.setDateCreated(new Date());

		if (deadline.length() != 0) {
			try {
				task.setDeadline(Dates.DATE_FORMATTER.parse(deadline));
			} catch (ParseException e) {
				Toast.makeText(form,
						form.getString(R.string.invalid_deadline_input), 1000)
						.show();
				return;
			}
		}
		task.setEstimatedPomodoros(estimatedPomodoros);
		task.setName(name);
		task.setType(type);

		saveTask(task);

	}

	private void saveTask(Task task) {
		PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(form);
		TaskDao taskDao = new TaskDao(helper);
		try {
			taskDao.save(task);
			Toast.makeText(form, form.getString(R.string.activity_saved), 1000)
					.show();
			form.clear();
		} finally {
			helper.close();
			taskDao.closeQuietly();
		}
	}
}
