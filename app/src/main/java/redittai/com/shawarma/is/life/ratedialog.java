package redittai.com.shawarma.is.life;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 14/02/2017.
 */

public class ratedialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    RatingBar ratingBar;
    public String st;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("name").child("store detailes");
    Map<String, Object> taskMap = new HashMap<String, Object>();

    public ratedialog(Activity a, String st) {
        super(a);
        this.st = st;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ratedialog);
        yes = (Button) findViewById(R.id.bYes);
        no = (Button) findViewById(R.id.bNO);
        ratingBar = (RatingBar) findViewById(R.id.raterating);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        shawarma sh = new shawarma();
        switch (v.getId()) {
            case R.id.bYes:
                final float rate = ratingBar.getRating();
                Log.i("a7", rate + "");

                final String name = st;
                for (int i = 0; i < MainActivity.res.size(); i++) {
                    if (name.equals(MainActivity.res.get(i).getName())) {
                        MainActivity.res.get(i).rateStore(rate);
                        sh = MainActivity.res.get(i);
                        Log.i("a11", "rank: " + sh.getRank() + " total rate: " + sh.getTotal_rate() + " counter: " + sh.getCounter());
                        double totRate, Rankk;
                        final Map<String, Object> taskMap = new HashMap<String, Object>();
                        taskMap.put("name", sh.getName());
                        Rankk = sh.getRank();
                        taskMap.put("rank", Rankk);
                        MainActivity.Rank = Rankk;
                        totRate = sh.getTotal_rate();
                        taskMap.put("total_rate", totRate);
                        taskMap.put("counter", sh.getCounter());
                        try {
                            DatabaseReference nameRef, storeRef, keyRef, updateRef;

                            keyRef = myRef.child(sh.getFBkey());
                            updateRef = keyRef.child(name);
                            updateRef.updateChildren(taskMap);
                            Toast.makeText(getContext(), "rate updated ", Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            Log.i("a12", e.getMessage());
                        }


                    }
                    dismiss();
                }
                break;

            case R.id.bNO:

                dismiss();
                break;

        }

    }
}
