package com.luxlunaris.studybuddy.model.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;

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

        builder.setTitle("Unsupported Language");


        builder.setPositiveButton("Change Language", (d, w)->{
            goToLanguageSettings(context);
        });

        builder.setNegativeButton("Exit App", (d, w)->{
            d.cancel();
            System.exit(0);
        });

        builder.show();
    }

    public static void checkLanguage(Context context){

        if(!getAvailableLanguages().contains(getLanguage())){
            unsupportedLanguageDialog(context);
        }

    }


}
