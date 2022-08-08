package com.example.ontheway;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {
    ArrayList<model> datalist;
    public myadapter(ArrayList<model> datalist) {
        this.datalist = datalist;
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        final model temp = datalist.get(position);

        holder.name.setText(datalist.get(position).getName());
        holder.contact.setText(datalist.get(position).getContact());
        holder.email.setText(datalist.get(position).getEmail());
        holder.district.setText(datalist.get(position).getDistrict());
        holder.city.setText(datalist.get(position).getCity());
        holder.skill.setText(datalist.get(position).getSkill());
        holder.latitude.setText(datalist.get(position).getLatitudeTextView());
        holder.latitude.setText(datalist.get(position).getLongitTextView());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context nm =holder.contact.getContext();
                String ab =datalist.get(position).getContact();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ab));

                callIntent.setFlags(callIntent.FLAG_ACTIVITY_NEW_TASK);
                holder.contact.getContext().startActivity(callIntent);
            }
        });
      holder.maps.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Context la =holder.latitude.getContext();
//              Context lo=holder.longit.getContext();
//              String str =latitudeTextView.getText().toString();
//              String str1 =longitTextView.getText().toString();
              Uri gmmIntentUri = Uri.parse("geo:"+la);
              Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
              mapIntent.setPackage("com.google.android.apps.maps");
              holder.latitude.getContext().startActivity(mapIntent);
          }
      });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView name, contact,city,  district,  skill, email,call,maps,latitude,longit;





        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nm);
            contact=itemView.findViewById(R.id.ct);
            city=itemView.findViewById(R.id.cy);
            district=itemView.findViewById(R.id.as);
            email=itemView.findViewById(R.id.el);
            skill=itemView.findViewById(R.id.kl);
            call=itemView.findViewById(R.id.call);
            maps=itemView.findViewById(R.id.maps);
            latitude=itemView.findViewById(R.id.latitude);
            longit=itemView.findViewById(R.id.longit);

        }
    }

}


