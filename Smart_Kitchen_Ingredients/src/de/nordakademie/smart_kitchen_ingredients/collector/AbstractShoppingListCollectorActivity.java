package de.nordakademie.smart_kitchen_ingredients.collector;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.AbstractCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.R;

public abstract class AbstractShoppingListCollectorActivity<T> extends
		AbstractCollectorActivity<T> implements OnClickListener {

	private Button changeCollectorButton;

	@Override
	protected void initElements() {
		super.initElements();
		changeCollectorButton = (Button) findViewById(R.id.changeCollectorButton);
		changeCollectorButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		startNextActivity();
	}

	protected abstract void startNextActivity();

}
