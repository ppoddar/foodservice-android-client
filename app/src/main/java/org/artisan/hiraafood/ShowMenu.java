package org.artisan.hiraafood;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(this.getClass() + ".onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        TableLayout tableLayout = findViewById(R.id.menu);

        init(tableLayout);
    }

    public  void init(TableLayout tableLayout) {
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);

      for (int i = 1; i < 100; i++) {
            System.out.println("adding row " + i);
            TableRow row = new TableRow(this);
            row.setId(i);
            row.setLayoutParams(rowParams);
            row.setLayoutParams(tableLayout.getLayoutParams());
            TextView text = new TextView(this);
            text.setLayoutParams(rowParams);
            text.setGravity(Gravity.LEFT);
            text.setBackgroundColor(Color.parseColor("#f8f8f8"));
            text.setText("row " + i);
            row.addView(text);
            tableLayout.addView(row, rowParams);
        }
    }

}
