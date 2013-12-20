package de.nordakademie.smart_kitchen_ingredients.shopping;

import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import de.nordakademie.smart_kitchen_ingredients.stock.AbstractListActivity;

/**
 * 
 * @author niels gundermann
 * 
 * @param <T>
 */
public abstract class AbstractDeletableListActivity<T> extends
		AbstractListActivity<T> implements OnItemLongClickListener {

	protected void initElements() {
		super.initElements();
		getList().setOnItemLongClickListener(this);

	}

	protected abstract AlertDialog getDialog(int position);

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

}
