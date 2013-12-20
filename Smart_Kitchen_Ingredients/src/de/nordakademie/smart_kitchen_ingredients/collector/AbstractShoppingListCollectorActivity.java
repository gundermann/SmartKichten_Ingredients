package de.nordakademie.smart_kitchen_ingredients.collector;

/**
 * @author Frederic Oppermann
 */
import android.view.View;
import android.view.View.OnClickListener;
import de.nordakademie.smart_kitchen_ingredients.R;

public abstract class AbstractShoppingListCollectorActivity<T> extends
		AbstractCollectorActivity<T> implements OnClickListener {

	private View changeCollectorButton;

	@Override
	protected void initElements() {
		super.initElements();
		changeCollectorButton = findViewById(R.id.changeCollectorButton);
		changeCollectorButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switchCollectorActivity();
	}

	protected abstract void switchCollectorActivity();

}
