package com.example.kouluprojekti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


/**
 * SettingsPage creates a new page where the user can change the colour theme of the application
 */
public class SettingsPage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.settings_page);
        configureReadyButton();

    }

    /**
     * onRadioButtonClicked() checks which radio button has been pressed and calls changeToTheme() to change the theme to the picked one
     * @param v The view where the radio buttons are located
     */
    public void onRadioButtonClicked(View v) {

        boolean checked = ((RadioButton) v).isChecked();


        switch (v.getId()) {
            case R.id.radioPurple:
                if (checked)
                    Utils.changeToTheme(this, Utils.THEME_PURPLE);
                break;
            case R.id.radioBlue:
                if (checked)
                    Utils.changeToTheme(this, Utils.THEME_BLUE);
                break;
            case R.id.radioRed:
                if (checked)
                    Utils.changeToTheme(this, Utils.THEME_RED);
                break;
            case R.id.radioYellow:
                if (checked)
                    Utils.changeToTheme(this, Utils.THEME_YELLOW);
                break;
        }
    }

    /**
     * configureReadyButton() makes the ReadyButton clickable to take the user back to the MainActivity
     */
    private void configureReadyButton() {
        Button ReadyButton = findViewById(R.id.button);
        ReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = getSharedPreferences("theme", Activity.MODE_PRIVATE).edit();
                edit.putInt("startThemeKeys", Utils.getThemee());
                edit.commit();

                startActivity(new Intent(SettingsPage.this, MainActivity.class));
            }
        });
    }
}
