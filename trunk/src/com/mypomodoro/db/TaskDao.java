package com.mypomodoro.db;

import java.io.Closeable;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
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
	 * Save a task object to the database.
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
	 *            _id of the task to be loaded.
	 * 
	 * @see Task#_ID
	 */
	public Task load(int taskID) {
		Cursor cursor = null;
		try {
			cursor = db.query(PomodoroDatabaseHelper.TABLE_NAME, new String[] {
					Task._ID, Task.NAME, Task.DEADLINE, Task.TYPE,
					Task.ESTIMATED_POMODOROS, Task.ACTUAL_POMODOROS }, Task._ID
					+ " = " + taskID, null, null, null, null);
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
			int id = cursor.getInt(cursor.getColumnIndex(Task._ID));
			int actualPomodoros = cursor.getInt(cursor
					.getColumnIndex(Task.ACTUAL_POMODOROS));

			task.setId(id);
			task.setName(name);
			task.setType(TaskType.valueOf(type.toUpperCase()));
			task.setEstimatedPomodoros(estimated);
			task.setActualPomodoros(actualPomodoros);
			// SQLite can't give us NULL here so it will rollback to 0
			if (deadline != 0) {
				Calendar deadlineCalendar = Calendar.getInstance();
				deadlineCalendar.setTimeInMillis(deadline);
				task.setDeadline(deadlineCalendar.getTime());
			}
			return task;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * Updates this task in the database. This method should be called with a
	 * task object that has the same id as one in the database.
	 * 
	 * Use this method after you get an existing object from he database and you
	 * need to update it.
	 * 
	 * @param task
	 *            the task object that represents the update task object from
	 *            the database.
	 * @return tre on success and false otherwise
	 */
	public boolean update(Task task) {
		ContentValues values = new ContentValues();
		values.put(Task.ESTIMATED_POMODOROS, task.getEstimatedPomodoros());
		values.put(Task.ACTUAL_POMODOROS, task.getActualPomodoros());
		values.put(Task.NAME, task.getName());
		values.put(Task.TYPE, task.getType().toString());
		Date deadline = task.getDeadline();
		if (deadline != null) {
			values.put(Task.DEADLINE, deadline.getTime());
		}
		return updateTask(values, task.getId());
	}

	private boolean updateTask(ContentValues values, int taskId) {
		db.update(PomodoroDatabaseHelper.TABLE_NAME, values, Task._ID + " = "
				+ taskId, null);
		return true;
	}

	/**
	 * Increment the number of pomodoros associated with this task.
	 * 
	 * @param taskId
	 *            the taskId of the task we want to update.
	 * @return true on success and false otherwise.
	 */
	public boolean incrementPomodoros(int taskId) {
		Task task = load(taskId);
		ContentValues values = new ContentValues();
		values.put(Task.ACTUAL_POMODOROS, task.getActualPomodoros() + 1);
		return updateTask(values, taskId);
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
