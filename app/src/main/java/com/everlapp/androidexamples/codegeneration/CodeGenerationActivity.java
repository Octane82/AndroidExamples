package com.everlapp.androidexamples.codegeneration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.everlapp.androidexamples.R;
import com.everlapp.bindview_api.BindView;

/**
 * https://medium.com/@robhor/annotation-processing-for-android-b7eda1a41051
 */
public class CodeGenerationActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView titleView;

    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.about_button)
    Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generation);

        ViewBinder.bind(this);

        titleView.setText("(Octane) Annotation Processing Example");
        textView.setText("(Octane) TextView bound using generated binder class");

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                //startActivity(intent);
            }
        });
    }
}

