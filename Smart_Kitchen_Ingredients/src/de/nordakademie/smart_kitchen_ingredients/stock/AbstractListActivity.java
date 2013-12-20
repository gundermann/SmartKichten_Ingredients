package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.List;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.R;

public abstract class AbstractListActivity<T> extends AbstractActivity
		implements OnClickListener, OnItemLongClickListener {

	private ListView list;
	private ImageButton addIngredientButton;

	protected void initElements() {
		addIngredientButton = (ImageButton) findViewById(R.id.addButton);
		addIngredientButton.setOnClickListener(this);
		list = (ListView) findViewById(R.id.list);
		list.setOnItemLongClickListener(this);
		setupList();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view,
			final int position, long arg3) {
		AlertDialog dialog = getDialog(position);
		if (dialog != null) {
			getDialog(position).show();
		}
		updateList();
		return true;
	}

	protected abstract AlertDialog getDialog(int position);

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
