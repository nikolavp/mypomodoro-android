package com.mypomodoro;

import android.os.Bundle;
import android.view.ViewStub;
import android.widget.Button;

public class CreateForm extends Form {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		((ViewStub) findViewById(R.id.create_button_stub)).inflate();
		
		Button createButton = (Button) findViewById(R.id.create_button);

		createButton.setOnClickListener(new CreateButtonClickListener(this));
	}
}
