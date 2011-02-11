package com.mypomodoro;

import android.content.Context;
import android.content.Intent;

public class Activities {
	/**
	 * Fires this activity based on {@link Activities#getIntentForClass(Context, Class)}
	 * @param context the context that want to fire this activity.
	 * @param clazz the class for the activity that is needs to be started.
	 * @return true if the activity was started and false otherwise.
	 */
	public static boolean fireActivityClass(Context context, Class<?> clazz) {
		Intent intent = getIntentForClass(context, clazz);
		context.startActivity(intent);
		return true;
	}
	/**
	 * Get single top intent for a specific class
	 * @param context the context which wanted to create the intent.
	 * @param clazz the class that is the target of the intent.
	 * @return the Intent object for this class.
	 */
	public static Intent getIntentForClass(Context context, Class<?> clazz) {
		Intent intent = new Intent(context, clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		return intent;
	}
}
