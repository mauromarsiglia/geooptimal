package com.test.geo.optimal;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.test.geo.optimal.library.Muestra;
import com.test.geo.optimal.persistence.SQLiteManager;
import com.test.geo.optimal.threads.OnClicTableRow;

public class Results extends Activity {

	private static final String TAG = "RESULTS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		TableLayout table = (TableLayout) findViewById(R.id.table);
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		List<Muestra> muestras = SQLiteManager.getInstance().getAll(this);
		Log.i("CANTIDAD", muestras.size() + "");
		for (final Muestra muestra : muestras) {

			TableRow newRow = new TableRow(this);

			newRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new OnClicTableRow(v).start();
					cambiarActivity(muestra);
				}
			});
			TextView day1High = new TextView(this);
			day1High.setText(muestra.getLatitude());
			day1High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day1High);

			TextView day2High = new TextView(this);
			day2High.setText(muestra.getLonguitude() + "");
			day2High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day2High);

			TextView day3High = new TextView(this);
			day3High.setText(muestra.getCalificacion() + "");
			day3High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day3High);

			TextView day4High = new TextView(this);
			day4High.setText(muestra.getPrecision() + "");
			day4High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day4High);

			TextView day5High = new TextView(this);
			day5High.setText(muestra.getNumeroSatelites() + "");
			day5High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day5High);

			TextView day6High = new TextView(this);
			day6High.setText(muestra.getProviderName());
			day6High.setGravity(Gravity.CENTER_HORIZONTAL);

			newRow.addView(day6High);

			table.addView(newRow);
		}

	}

	private void cambiarActivity(Muestra muestra) {
		Log.i(TAG, muestra.getDescripcion());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

}
