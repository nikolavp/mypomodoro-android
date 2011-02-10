package com.mypomodoro;

import android.content.Context;
import android.content.Intent;

public class Activities {
	public static boolean fireActivityClass(Context context, Class<?> clazz) {
		Intent intent = getIntentForClass(context, clazz);
		context.startActivity(intent);
		return true;
	}

	public static Intent getIntentForClass(Context context, Class<?> clazz) {
		Intent intent = new Intent(context, clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		return intent;
	}
}
