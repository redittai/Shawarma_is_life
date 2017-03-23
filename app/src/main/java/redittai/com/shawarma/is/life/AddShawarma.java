package redittai.com.shawarma.is.life;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShawarma extends AppCompatActivity {
    CheckBox kosher, parking;
    RadioButton egel, ceves, pargit;
    EditText edname, edpita, edlafa;
    TextView tv;
    RatingBar ratingBar;
    Button add;
    LocationManager locationManager;
    double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shawarma);
        tv = (TextView)findViewById(R.id.textView);
        kosher = (CheckBox) findViewById(R.id.ADDchecKosher);
        parking = (CheckBox) findViewById(R.id.ADDchecParking);
        egel = (RadioButton) findViewById(R.id.AddRadioEgel);
        ceves = (RadioButton) findViewById(R.id.AddRadioCeves);
        pargit = (RadioButton) findViewById(R.id.AddRadioPargit);
        ceves.setText(R.string.ceves);
        egel.setText(R.string.egel);
        pargit.setText(R.string.pargit);
        edname = (EditText) findViewById(R.id.AddNameED);
        edpita = (EditText) findViewById(R.id.AddPitaED);
        edlafa = (EditText) findViewById(R.id.AddLafaED);
        edlafa.setHint(R.string.hintLaffa);
        edpita.setHint(R.string.hintPita);
        edname.setHint(R.string.hintName);
        ratingBar = (RatingBar) findViewById(R.id.AddRating);
        add = (Button) findViewById(R.id.AddBTNadd);
        add.setText(R.string.add);
        try {
            tv.setText(R.string.add_notice);
        } catch (Exception e) {
            Log.i("a",e.getMessage());
        }
        kosher.setText(R.string.kosher);
        parking.setText(R.string.parking);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l1 = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        lat=l1.getLatitude();
        lon=l1.getLongitude();

        try {
            add.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name;
                    int pita,lafa, isKosher, isParking, isEgel, isPargit, isCeves;
                    //double rate;

                    if (egel.isChecked())
                        isEgel=1;
                    else
                        isEgel=0;

                    if (kosher.isChecked())
                        isKosher=1;
                    else isKosher=0;

                    if (parking.isChecked())
                        isParking=1;
                    else isParking=0;

                    if (pargit.isChecked())
                        isPargit=1;
                    else isPargit=0;

                    if (ceves.isChecked())
                        isCeves=1;
                    else isCeves=0;

                    float rrate= ratingBar.getRating();
                    double rate = (double)rrate;
                    name= edname.getText().toString();
                    Log.i("a9",name);
                    lafa= Integer.parseInt(edlafa.getText().toString().trim());
                    pita= Integer.parseInt(edpita.getText().toString().trim());

                    shawarma sh1 = null;

                    sh1 = new shawarma(name,pita,lafa,isEgel,isCeves,isPargit,isKosher,  rate,isParking,lat,lon);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("name").child("store detailes");


                    DatabaseReference pushRef = myRef.push();
                    String key = pushRef.getKey();
                    sh1.setFBkey(key);
                    Log.i("a11",key);

                    pushRef.child(name).setValue(sh1);

                    Toast.makeText(getApplicationContext(),name+" added to database"  ,Toast.LENGTH_LONG).show();
                    Intent in1 =  new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(in1);
                }
            });

        } catch (Exception e) {
            Log.i("a2",e.getMessage());
        }

    }
}
