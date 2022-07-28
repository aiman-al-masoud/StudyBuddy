package com.luxlunaris.studybuddy.model.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;


import com.luxlunaris.studybuddy.model.challenge.exceptions.NoSuchFileException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileManager {


    public static FileManagerListener listener;

    public static  void setListener(FileManagerListener l){
        listener = l;
    }


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
        overwriteTextFileInRootDir(title, body);

    }


    public static void overwriteTextFileInRootDir(String title, String newBody) throws IOException{

        File f = new File(getRootDirPath()+"/"+title+".txt");
        FileWriter fr = new FileWriter(f);
        fr.write(newBody);
        fr.flush();
        fr.close();


        try{
            listener.onFileChanged(title, newBody);
        }catch (NullPointerException e ){
            e.printStackTrace();
        }

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

    public static void deleteTextFileFromRootDir(String title){
        String path = getRootDirPath()+"/"+title+".txt";
        File f = new File(path);
        f.delete();
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

    /**
     * Lists filenames in the root directory, including their extension.
     * @return
     * @throws IOException
     */
    public static List<String> lsRootDir() throws IOException{

        try {
            return Arrays.stream(new File(getRootDirPath()).listFiles()).map(File::getName).collect(Collectors.toList());
        }catch (NullPointerException e){
            throw new IOException();
        }
    }

    /**
     * Get the closest matching text file name that exists in
     * root dir, if no match is found throw a NoSuchFileException.
     * @param maybeFileName
     * @return
     * @throws NoSuchFileException
     */
    public static String getClosestMatchingFileName(String maybeFileName) throws NoSuchFileException {

        List<String> kws =  Keywords.extractKeywords(maybeFileName);

        try{
            Optional<String> s = lsRootDir().stream().map(n->{
                int matches = kws.stream().mapToInt(k->n.contains(k)?1:0).sum();
                return Arrays.asList(n, matches);
            }).filter(l->{
                return (Integer)l.get(1) >= 1;
            }).sorted((l1, l2)->{
                return (Integer) l1.get(1) - (Integer)l2.get(1);
            }).map(l->(String)l.get(0)).findFirst();


            if(s.isPresent()){
                return s.get().replace(".txt", "");
            }else{
                throw new NoSuchFileException(maybeFileName);
            }

        }catch (IOException e){
            e.printStackTrace();
            throw new NoSuchFileException(maybeFileName);
        }

    }









}
