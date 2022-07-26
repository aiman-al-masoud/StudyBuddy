package com.luxlunaris.studybuddy.view.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.view.help.HelpActivity;
import com.luxlunaris.studybuddy.view.intro.IntroActivity;
import com.luxlunaris.studybuddy.view.license.LicenseActivity;

public class AboutActivity extends AppCompatActivity {

    Button seeIntro;
    Button license;
    Button commandsHelp;
    Button aboutCorpora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        seeIntro = findViewById(R.id.seeIntroButton);
        license = findViewById(R.id.licenseButton);
        commandsHelp  = findViewById(R.id.commandsHelpButton);
        aboutCorpora = findViewById(R.id.aboutCorporaButton);

        aboutCorpora.setVisibility(View.GONE);

        license.setOnClickListener(e->{
            startActivity(new Intent(this, LicenseActivity.class));
        });

        seeIntro.setOnClickListener(e->{
            startActivity(new Intent(this, IntroActivity.class));
        });

        commandsHelp.setOnClickListener(e->{
            startActivity(new Intent(this, HelpActivity.class));
        });

        aboutCorpora.setOnClickListener(e->{

        });


    }
}