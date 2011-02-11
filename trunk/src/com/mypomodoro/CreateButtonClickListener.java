package com.mypomodoro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.data.TaskType;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

public class CreateButtonClickListener implements OnClickListener {
	private final CreateForm form;

	public CreateButtonClickListener(CreateForm form) {
		this.form = form;
	}

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd-MM-yyyy");

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
		String deadline = form.deadlineField.getText().toString();
		

		Task task = new Task();
		task.setType(TaskType.valueOf(form.type.toUpperCase()));
		task.setDateCreated(new Date());


		if (deadline.length() != 0) {
			try {
				task.setDeadline(dateFormatter.parse(deadline));
			} catch (ParseException e) {
				Toast.makeText(form,
						form.getString(R.string.invalid_deadline_input), 1000)
						.show();
				return;
			}
		}
		task.setEstimatedPomodoros(estimatedPomodoros);
		task.setName(name);

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
