package redittai.com.shawarma.is.life;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by USER on 13/02/2017.
 */

public class adapter2 extends ArrayAdapter<shawarma> {
    public adapter2(Context context, List<shawarma> resource) {
        super(context,0, resource);

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder= null;
        View row = convertView;

        if(row==null) {

            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) shawarmaList.con.getSystemService(shawarmaList.con.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.shawarmaddapter, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);

        }

        // well set up the ViewHolder

        else{
            holder = (ViewHolder)row.getTag();
        }

        final shawarma sh1 = getItem(position);
        holder.locName=holder.name.getText().toString();


        holder.kosher.setText(R.string.kosher);
        holder. park.setText(R.string.parking);
        holder. pita.setText("פיתה "+sh1.getPita());
        holder. lafa.setText("לאפה:  "+sh1.getLaffa());
        holder.nname = sh1.getName();
        holder. name.setText(sh1.getName());
        float distance1 =Float.parseFloat(new DecimalFormat("###.#").format(sh1.getDistance()));
        holder.distance.setText("מרחק: "+distance1+ "ק''מ");
        if (sh1.getKosher()==1){

            holder.  kosher.setChecked(true);
        }
        else{
            holder. kosher.setChecked(false);
        }

        if (sh1.getParking()==1)
            holder.  park.setChecked(true);
        else
            holder. park.setChecked(false);

        holder. rate.setRating((float) sh1.getRank());
        holder.  rate.setIsIndicator(true);
        // shawarmaddapter.listener lis = new shawarmaddapter.listener(sh1);
        //  holder.  waze.setOnClickListener(lis);
        //  holder.  whats.setOnClickListener(lis);
        holder.   lat=   sh1.getLat();
        holder.   lon= sh1.getLon();
        holder. waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String url = "waze://?ll="+sh1.getLat()+","+sh1.getLon()+"&navigate=yes";
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                    shawarmaList.con. startActivity( intent );
                }
                catch ( ActivityNotFoundException ex  )
                {
                    Intent intent =
                            new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                    shawarmaList.con.startActivity(intent);
                }
                catch (Exception ex)
                {
                    Log.i("a7",ex.toString());
                }
            }
        });
        holder.whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "do you want to Join me to "+sh1.getName()+"?");
                sendIntent.setType("text/plain");

                try {
                    Toast.makeText(shawarmaList.con.getApplicationContext(),sh1.getName(),Toast.LENGTH_SHORT).show();
                    shawarmaList.con.startActivity(sendIntent);
                } catch (Exception e) {
                    Log.i("a7",e.getMessage());
                }
            }
        });

        return row;

    }

    private class ViewHolder {
        TextView pita,lafa,name,distance;
        String locName;
        RatingBar rate;
        CheckBox park,kosher;
        ImageButton waze,whats;
        String nname;
        double lat,lon;
        ViewHolder (View v){
            pita= (TextView)v.findViewById(R.id.adapterEDPita);
            lafa= (TextView)v.findViewById(R.id.adapterEDLaffa);
            name= (TextView)v.findViewById(R.id.adapterEDName);
            rate= (RatingBar)v.findViewById(R.id.adapterRating);
            park= (CheckBox)v.findViewById(R.id.adapterChekParking);
            kosher= (CheckBox)v.findViewById(R.id.adapterChekKosher);
            distance = (TextView)v.findViewById(R.id.textViewdistance);
            waze= (ImageButton)v.findViewById(R.id.adapterWazeBTN);
            whats = (ImageButton)v.findViewById(R.id.whats);
        }

    }
}
