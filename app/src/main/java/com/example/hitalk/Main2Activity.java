package com.example.hitalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hitalk.fragment.PeopleFragment;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getFragmentManager().beginTransaction().replace(R.id.mainActivity2_framelayout, new PeopleFragment()).commit();

    }
}
