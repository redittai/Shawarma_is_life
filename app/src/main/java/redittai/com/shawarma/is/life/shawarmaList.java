package redittai.com.shawarma.is.life;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shawarmaList extends AppCompatActivity {

    ListView lis;
    public static  Context con;
    // List<shawarma>res = new ArrayList<shawarma>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = this;
        setContentView(R.layout.activity_shawarma_list);
        lis = (ListView) findViewById(R.id.SHOWlistView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("name").child("store detailes");

        adapter2 shad = new adapter2(getApplicationContext(),MainActivity.res);
        lis.setAdapter(shad);
    }

}

