package com.mypomodoro;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PomodoroTimerActivity extends PomodoroActivity {
	private static final int SECOND = 1000;
	private static final int MINUTES = 60 * SECOND;
	private static final long POMODORO_LENGTH = 25 * MINUTES;
	private TextView text;
	private long time = POMODORO_LENGTH;
	private final Handler handler = new Handler();
	private Button stopButton;
	private Button startButton;

	private void updateTimer() {
		text.setText(format.format(time));
	}
	
	private final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			time -= SECOND;
			updateTimer();
			handler.postDelayed(this, SECOND);
		}

		
	};

	private static final SimpleDateFormat format = new SimpleDateFormat("mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text = (TextView) findViewById(R.id.pomodoro_timer);
		
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
				handler.removeCallbacks(runnable);
				time = POMODORO_LENGTH;
				updateTimer();
				canStart(true);
			}
		});
	}
	
	private void canStart(boolean canStart){
		startButton.setEnabled(canStart);
		stopButton.setEnabled(!canStart);
	}
	
	private void goInPomodoro(){
		handler.post(runnable);
		canStart(false);
	}
}
