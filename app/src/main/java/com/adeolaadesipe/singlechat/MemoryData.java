package com.adeolaadesipe.singlechat;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryData {
    public static void saveData(String data, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("userdata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveName(String data, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("userName.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveLastMessage(String data, String chatID, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(chatID+".txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    // getting the data from the file where it is stored in memory

    public static String getData(Context context){
        String data = "";
        try {
            FileInputStream inputStream = context.openFileInput("userdata.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            data = streamReader.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    public static String getName(Context context){
        String data = "";
        try {
            FileInputStream inputStream = context.openFileInput("userName.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            data = streamReader.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    public static String getLastMessage(Context context, String chatID){
        String data = "0";
        try {
            FileInputStream inputStream = context.openFileInput(chatID+".txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            data = streamReader.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
