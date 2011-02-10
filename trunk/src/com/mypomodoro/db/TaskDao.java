package com.mypomodoro.db;

import java.io.Closeable;
import java.io.IOException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mypomodoro.data.Task;

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
		insertStatement.bindLong(2, task.getType().ordinal());
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
		db.close();
	}
}
