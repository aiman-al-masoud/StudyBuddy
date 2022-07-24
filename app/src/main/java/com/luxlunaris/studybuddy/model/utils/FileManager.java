package com.luxlunaris.studybuddy.model.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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
        FileWriter fr = new FileWriter(f);
        fr.write(body);
        fr.flush();
        fr.close();

    }


    public static String uriToText(Context context, Uri uri) throws  IOException{

        InputStream is = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int i;
        while ((i = is.read()) != -1 ){
            os.write(i);
        }

        return os.toString();
    }


    public static String readTextFileFromRootDir(String title) throws  IOException{
        String path = getRootDirPath()+"/"+title+".txt";
        File f = new File(path);

        if(f.exists()){
            FileReader fr = new FileReader(f);
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            int i;
            while ((i = fr.read()) != -1 ){
                os.write(i);
            }

            return os.toString();
        }

        throw  new IOException();
    }









}
