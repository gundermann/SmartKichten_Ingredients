package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.List;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.R;

public abstract class AbstractListActivity<T> extends AbstractActivity
		implements OnClickListener {

	private ListView list;
	private ImageButton addIngredientButton;

	protected void initElements() {
		addIngredientButton = (ImageButton) findViewById(R.id.addButton);
		addIngredientButton.setOnClickListener(this);
		list = (ListView) findViewById(R.id.list);
		setupList();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_layout);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	public ListView getList() {
		return list;
	}

	private void setupList() {
		TextView emptyView = new TextView(getApplicationContext());
		emptyView.setText(R.string.emptyList);
		list.setEmptyView(findViewById(android.R.id.empty));
	}

	protected void updateList() {
		list.setAdapter(getAdapter());
	}

	protected abstract List<T> getElements();

	protected abstract ListAdapter getAdapter();

}
