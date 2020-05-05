package com.example.kouluprojekti;

import android.view.View;
import android.widget.RadioButton;

public class SettingsPage {

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioPurple:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radioBlue:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radioRed:
                if (checked)
                    //
                    break;
            case R.id.radioYellow:
                if (checked)
                    //
                    break;
        }
    }

}
