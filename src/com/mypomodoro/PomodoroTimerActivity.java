package com.mypomodoro;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mypomodoro.data.Task;
import com.mypomodoro.db.PomodoroDatabaseHelper;
import com.mypomodoro.db.TaskDao;

public class PomodoroTimerActivity extends PomodoroActivity {
	private static final int SECOND = 1000;
	private static final int MINUTES = 60 * SECOND;
	private static final long POMODORO_LENGTH = 25 * MINUTES;
	private static final long POMODORO_SHORT_BREAK_LENGTH = 5 * MINUTES;
	private static final long POMODORO_LONG_BREAK_LENGTH = POMODORO_SHORT_BREAK_LENGTH * 5;

	private TextView taskName;
	private TextView timerText;
	private long time = POMODORO_LENGTH;
	private final Handler handler = new Handler();
	private Button stopButton;
	private Button startButton;

	private boolean inpomodoro;
	private int currentTaskId;

	public boolean isInPomodoro() {
		return inpomodoro;
	}

	private void updateTimer() {
		timerText.setText(format.format(time));
	}

	private final Runnable runnable = new Runnable() {
		int i = 0;

		@Override
		public void run() {
			if (time >= 1) {
				time -= SECOND;
				updateTimer();
				handler.postDelayed(this, SECOND);
			} else {
				//TODO: Change the Task actual pomodoro numbers
				if (isInPomodoro()) {
					if (i > 3) {
						goInLongBreak();
						i = 0;
					} else {
						i++;
						goInShortBreak();
					}
					inpomodoro = false;
				} else {
					goInPomodoro();
				}
			}
		}

		private void goInShortBreak() {
			time = POMODORO_SHORT_BREAK_LENGTH;
		}

		private void goInLongBreak() {
			time = POMODORO_LONG_BREAK_LENGTH;
		}

	};

	private static final SimpleDateFormat format = new SimpleDateFormat("mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		timerText = (TextView) findViewById(R.id.pomodoro_timer);
		taskName = (TextView) findViewById(R.id.pomodoro_task_name);
		startButton = (Button) findViewById(R.id.start);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goInPomodoro();
			}
		});
		stopButton = (Button) findViewById(R.id.stop);
		stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				voidPomodoro();
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Bundle extras = getIntent().getExtras();
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		if (extras != null) {
			int otherTaskIdId = extras.getInt(SheetsActivity.TASK_ID);
			if(currentTaskId != otherTaskIdId && isInPomodoro()){
				Toast.makeText(this, getString(R.string.double_tasks_error), 3000).show();
				return;
			}else{
				currentTaskId = otherTaskIdId;
			}
		} else {
			currentTaskId = preferences.getInt(SheetsActivity.TASK_ID, -1);
		}
		if (currentTaskId != -1) {
			PomodoroDatabaseHelper helper = new PomodoroDatabaseHelper(this);
			TaskDao taskDao = new TaskDao(helper);
			try {
				Task task = taskDao.load(currentTaskId);
				taskName.setText(task.getName());
			} finally {
				helper.close();
				taskDao.closeQuietly();
			}
		}
	}

	private void canStart(boolean canStart) {
		startButton.setEnabled(canStart);
		stopButton.setEnabled(!canStart);
	}
	
	private void voidPomodoro(){
		handler.removeCallbacks(runnable);
		time = POMODORO_LENGTH;
		updateTimer();
		canStart(true);
		inpomodoro = false;
	}

	private void goInPomodoro() {
		time = POMODORO_LENGTH;
		inpomodoro = true;
		handler.post(runnable);
		canStart(false);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Editor edit = getPreferences(MODE_PRIVATE).edit();
		edit.putInt(SheetsActivity.TASK_ID, currentTaskId);
		edit.commit();
	}
}
