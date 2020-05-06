package com.example.kouluprojekti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsPage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.settings_page);
        configureReadyButton();

    }

    public void onRadioButtonClicked(View v) {

        boolean checked = ((RadioButton) v).isChecked();


        switch(v.getId()) {
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

    private void configureReadyButton() {
        Button ReadyButton = findViewById(R.id.button);
        ReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsPage.this, MainActivity.class));
            }
        });
    }

}
