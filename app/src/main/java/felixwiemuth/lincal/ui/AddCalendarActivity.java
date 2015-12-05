/*
 * Copyright (C) 2015 Felix Wiemuth
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package felixwiemuth.lincal.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import felixwiemuth.lincal.R;
import felixwiemuth.lincal.data.LinCal;
import felixwiemuth.lincal.data.Main;
import felixwiemuth.lincal.parser.LinCalParser;
import java.io.File;
import java.io.IOException;
import linearfileparser.ParseException;

public class AddCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        Button chooseFileButton = (Button) findViewById(R.id.cb_file);
        Button addButton = (Button) findViewById(R.id.cb_add);
        final EditText file = (EditText) findViewById(R.id.ce_file);

        // Set file if activity was opened by file
        Intent fileIntent = getIntent();
        Uri uri = fileIntent.getData();
        if (uri != null) {
            file.setText(uri.toString());
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinCalParser parser = new LinCalParser(getApplicationContext());
                try {
                    LinCal calendar = parser.parse(new File(file.getText().toString()));
                    Main.get().addCalendar(calendar);
                    AddCalendarActivity.this.finish();
                } catch (IOException | ParseException ex) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCalendarActivity.this);
                    builder.setTitle("Error").setMessage(ex.getMessage());
                    AlertDialog dialog = builder.show();
                    Snackbar.make(getCurrentFocus(), ex.getMessage(), Snackbar.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
