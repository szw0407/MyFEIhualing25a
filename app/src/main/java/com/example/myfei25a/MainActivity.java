package com.example.myfei25a;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick_go(View view) {
        EditText key = findViewById(R.id.id_key);
        ListView res = findViewById(R.id.id_res);
        InputStream input = getResources().openRawResource((R.raw.shi300));
        try {
            int k = 0;
            String temp = "";
            List<String> resultList = new ArrayList<>();
            Scanner scan = new Scanner(input);
            int max_lines = 65536;
            for(int i=1;i<27;i++){
                scan.nextLine();
            }
            while (scan.hasNext()) {
            String str = scan.nextLine();
            if (str.isEmpty()) {
                continue;
            }
            if (str.contains("：")) {
                // title of poem
                temp = str;
                continue;
            }
            if (str.contains(key.getText().toString())) {
                // line of poem valid
                k++;
                String highlightedStr = str.replace(key.getText().toString(), "<b>" + key.getText().toString() + "</b>");
                resultList.add(k + "." + highlightedStr + "——" + temp);
                max_lines--;
                if (max_lines <= 0) break;
            }
            }
            Toast.makeText(this, "一共从《唐诗三百首》中找出" + k + "个诗句", Toast.LENGTH_LONG).show();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, resultList) {
                @NonNull
                @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setText(Html.fromHtml(getItem(position)));
                    return view;
                }
            };
            res.setAdapter(adapter);
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}