package com.luxlunaris.studybuddy.model.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Language {


    public static List<String> getAvailableLanguages(){
        return Arrays.asList("en");
    }


    public static String getLanguage(){
        return Locale.getDefault().getLanguage();
    }

    public static void goToLanguageSettings(Context context){
        Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        context.startActivity(i);
    }

    private static void unsupportedLanguageDialog(Context context){

        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setText("Sorry, your language is not currently supported :(\nSupported languages include: "+getAvailableLanguages().toString().replaceAll("\\[|\\]", ""));

        builder.setTitle("Unsupported Language");
        builder.setView(textView);

        builder.setPositiveButton("Change Language", (d, w)->{
            goToLanguageSettings(context);
        });

        builder.setNegativeButton("Exit App", (d, w)->{
            d.cancel();
            System.exit(0);
        });

        builder.setOnDismissListener(e->{
            System.exit(0);
        });

        builder.show();
    }

    public static boolean checkLanguage(Context context){

        boolean langOk = getAvailableLanguages().contains(getLanguage());

        if(!langOk){
            unsupportedLanguageDialog(context);
        }

        return langOk;
    }


}
