package com.mypomodoro.db;

import java.io.Closeable;
import java.io.IOException;
import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mypomodoro.data.Task;
import com.mypomodoro.data.TaskType;

/**
 * A dao that will provide an abstractions for common operations with the
 * database and task objects.
 * 
 * @author nikolavp
 * 
 */
public class TaskDao implements Closeable {
	private final SQLiteDatabase db;
	private static final String INSERT_TASK_SQL = "INSERT INTO tasks(name, type, "
			+ "deadline, date_created, estimated) values (?, ?, ?, ?, ?)";
	private final SQLiteStatement insertStatement;

	/**
	 * Default constructor.
	 * 
	 * @param context
	 *            the context that wants to create this dao object.
	 */
	public TaskDao(PomodoroDatabaseHelper helper) {
		db = helper.getWritableDatabase();
		insertStatement = db.compileStatement(INSERT_TASK_SQL);

	}

	/**
	 * Save a task to the database.
	 * 
	 * @param task
	 *            the task to be saved.
	 */
	public void save(Task task) {
		insertStatement.bindString(1, task.getName());
		insertStatement.bindString(2, task.getType().toString());
		if (task.getDeadline() == null) {
			insertStatement.bindNull(3);
		} else {
			insertStatement.bindLong(3, task.getDeadline().getTime());
		}
		insertStatement.bindLong(4, task.getDateCreated().getTime());
		insertStatement.bindLong(5, task.getEstimatedPomodoros());
		insertStatement.execute();
	}

	/**
	 * Load a task from the database.
	 * 
	 * @param taskID
	 *            name of the task to be loaded.
	 */
	public Task load(int taskID) {
		Cursor cursor = null;
		try {
			cursor = db.query(PomodoroDatabaseHelper.TABLE_NAME, new String[] {
					Task.NAME, Task.DEADLINE, Task.TYPE,
					Task.ESTIMATED_POMODOROS }, Task._ID + " = " + taskID,
					null, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
			Task task = new Task();
			int estimated = cursor.getInt(cursor
					.getColumnIndex(Task.ESTIMATED_POMODOROS));
			String name = cursor.getString(cursor.getColumnIndex(Task.NAME));
			String type = cursor.getString(cursor.getColumnIndex(Task.TYPE));
			long deadline = cursor
					.getLong(cursor.getColumnIndex(Task.DEADLINE));

			task.setName(name);
			task.setType(TaskType.valueOf(type.toUpperCase()));
			task.setEstimatedPomodoros(estimated);
			Calendar deadlineCalendar = Calendar.getInstance();
			deadlineCalendar.setTimeInMillis(deadline);
			task.setDeadline(deadlineCalendar.getTime());
			return task;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * Close the dao without throwing exceptions to the caller.
	 */
	public void closeQuietly() {
		try {
			close();
		} catch (IOException e) {
		}
	}

	@Override
	public void close() throws IOException {
		insertStatement.close();
		db.close();
	}
}
