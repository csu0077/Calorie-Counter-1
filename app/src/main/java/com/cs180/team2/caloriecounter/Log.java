package com.cs180.team2.caloriecounter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import static com.cs180.team2.caloriecounter.LoginActivity.username;

public class Log extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        File dir = Environment.getDataDirectory();
        if(!dir.exists()) {
            dir.mkdirs();
        }

        Calendar c = Calendar.getInstance();
        String Filename = Integer.toString(c.get(Calendar.MONTH)) + "." + Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "." + Integer.toString(c.get(Calendar.YEAR));
        Filename = Filename + "_" + username + ".txt";
        File file = new File(dir, Filename);
        if(!file.getParentFile().exists()) {
            if(file.getParentFile().mkdirs()) {
                System.out.println(file.getParentFile() + " created!");
            }

            try {
                System.out.println(file.getCanonicalPath() + " about to be created!");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }


        ArrayList<FoodEntry> breakfast = new ArrayList<FoodEntry>();
        ArrayList<FoodEntry> lunch = new ArrayList<FoodEntry>();
        ArrayList<FoodEntry> dinner = new ArrayList<FoodEntry>();
        ArrayList<FoodEntry> snacks = new ArrayList<FoodEntry>();
        TextView log = (TextView) findViewById(R.id.log);

        if(username.isEmpty()) {
            log.setVisibility(View.INVISIBLE);
        } else {
            if (file.exists()) {
                try {
                    InputStream in = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    FoodEntry tempFood = new FoodEntry("", Long.valueOf(0), "", "", "");

                    String line = reader.readLine();
                    while(line != null) {
                        tempFood.Name = line;
                        tempFood.Calories = Long.valueOf(reader.readLine());
                        tempFood.Description = reader.readLine();
                        tempFood.Tag = reader.readLine();
                        tempFood.User = reader.readLine();
                        tempFood.Tag = reader.readLine();
                        reader.readLine();
                        line = reader.readLine();
                        if(tempFood.Tag == "Breakfast") {
                            breakfast.add(tempFood);
                        }
                        else if(tempFood.Tag == "Lunch") {
                            lunch.add(tempFood);
                        }
                        else if(tempFood.Tag == "Dinner") {
                            dinner.add(tempFood);
                        }
                        else if(tempFood.Tag == "Snacks") {
                            snacks.add(tempFood);
                        }
                    }
                    log.append("Breakfast\n\n");
                    for(FoodEntry food:breakfast) {
                        log.append("Name: " + food.Name + "\n");
                        log.append("Calories: " + food.Calories.toString() + "\n");
                        log.append("Description: " + food.Description + "\n\n");
                    }

                    log.append("Lunch\n\n");
                    for(FoodEntry food:lunch) {
                        log.append("Name: " + food.Name + "\n");
                        log.append("Calories: " + food.Calories.toString() + "\n");
                        log.append("Description: " + food.Description + "\n\n");
                    }

                    log.append("Dinner\n\n");
                    for(FoodEntry food:dinner) {
                        log.append("Name: " + food.Name + "\n");
                        log.append("Calories: " + food.Calories.toString() + "\n");
                        log.append("Description: " + food.Description + "\n\n");
                    }

                    log.append("Snacks\n\n");
                    for(FoodEntry food:snacks) {
                        log.append("Name: " + food.Name + "\n");
                        log.append("Calories: " + food.Calories.toString() + "\n");
                        log.append("Description: " + food.Description + "\n\n");
                    }

                } catch (FileNotFoundException e) {
                    Context context = getApplicationContext();
                    CharSequence text = "Log file not found!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration); //Chanho: Toast is a popup notification that disappears automatically after a period of time
                    toast.show();
                } catch (IOException e) {
                    Context context = getApplicationContext();
                    CharSequence text = "Log file not found!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration); //Chanho: Toast is a popup notification that disappears automatically after a period of time
                    toast.show();
                }
            }
        }
    }
}