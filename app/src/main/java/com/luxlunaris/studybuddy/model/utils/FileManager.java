package com.luxlunaris.studybuddy.model.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public static String getRootDirPath(){
        return Environment.getExternalStorageDirectory()+"/StudyBuddy";
    }

    public static void createRootDir(){
        File dir = new File(getRootDirPath());
        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    public static void writeTextFileToRootDir( String title, String body) throws IOException {

        File f = new File(getRootDirPath()+"/"+title+".txt");

        if(f.exists()){
            return;
        }

        f.createNewFile();
        BufferedWriter br = new BufferedWriter(new FileWriter(f));
        br.write(body);

    }

    public static String readTextFileFromRootDir(){
        return "";
    }





}
