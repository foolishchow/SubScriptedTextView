package me.foolishchow.subscripted;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.foolishchow.android.subscripted.SubScriptedTextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubScriptedTextView viewById = findViewById(R.id.text);
        viewById.setBottomSubScript("bottom --- ");
    }
}