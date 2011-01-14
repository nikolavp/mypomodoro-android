package com.mypomodoro;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CreateButtonClickListener implements OnClickListener {
	private final CreateForm form;

	public CreateButtonClickListener(CreateForm form) {
		this.form = form;
	}

	@Override
	public void onClick(View arg0) {
		Integer estimatedPomodoros = null;
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
		String place = form.placeField.getText().toString();
		if (name.length() == 0 || place.length() == 0) {
			Toast.makeText(form, form.getString(R.string.invalid_form_input),
					1000).show();
			return;
		}
		Toast.makeText(form, "Activity saved", 1000).show();
		form.clear();

		PomodoroDatabaseHelper dbHelper = new PomodoroDatabaseHelper(
				form);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		SQLiteStatement statement = db.compileStatement(INSERT);
		statement.bindString(1, "sadsadas");
		statement.bindString(2, "sadasd");
		statement.executeInsert();
		System.out.println("Saved");
		System.out.println(INSERT);
		db.close();
		// TODO: Activity saving is not implemented yet.
		// Activity activity = new Activity(place, null, name, null, null,
		// estimatedPomodoros);
		// ActivitiesDAO.getInstance().insert(activity);
	}
	
	private static final String INSERT = "INSERT INTO " + PomodoroDatabaseHelper.TABLE_NAME
 + " ("
			+ PomodoroDatabaseHelper.NAME + ", "
			+ PomodoroDatabaseHelper.ESTIMATED + ") values (?, ?)";
}
