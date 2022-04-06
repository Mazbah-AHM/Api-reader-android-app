package my.edu.utem.ftmk.labtest2_b031820478;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView uniListView;
    ArrayList<University> universities = new ArrayList<>();
    ArrayList<String> university_names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uniListView = findViewById(R.id.uni_list);


        new Task(MainActivity.this).loadInBackground();
    }

    class Task extends AsyncTaskLoader<String> {

        public Task(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public String loadInBackground() {
            Ion.with(getApplicationContext()).load("http://universities.hipolabs.com/search?country=Malaysia").asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {

                    Log.d("data", result);
                    try {
                        JSONArray data = new JSONArray(result);
                        for(int i=0; i<data.length();i++){
                            University university = new University();
                            JSONObject obj = data.getJSONObject(i);
                            university.name = obj.getString("name");
                            JSONArray websites = obj.getJSONArray("web_pages");
                            university.websites = new String[websites.length()];
                            for(int j=0;j<websites.length();j++){
                                university.websites[j] = (String) websites.get(j);
                            }
                            university_names.add(university.name);
                            universities.add(university);
                        }
                        ListAdapter adapter = new ListAdapter(MainActivity.this, universities);
                        uniListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        uniListView.setHasFixedSize(true);
                        uniListView.setAdapter(adapter);



                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                }
            });

            return null;
        }
    }
}