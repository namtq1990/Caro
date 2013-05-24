package com.example.caro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, Board.class));
        
        /*/
        Button b = new Button(this);
        b.setText("Start");
        this.setContentView(b);
        b.setOnClickListener(this);
        /*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onClick(View v)
    {
    	startActivity(new Intent(this, Board.class));
    }
}
