package com.example.caneat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterReligion extends RecyclerView.Adapter<AdapterReligion.AHolder> {

    final ArrayList<religion_info>arrayList;
    final Context context;

    public AdapterReligion(ArrayList<religion_info>arrayList,Context context){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    public AHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_allergic,parent,false);
        AHolder holder=new AHolder(view);
        return holder;
    }
   @Override
   public void onBindViewHolder(@NonNull AHolder holder,int position){
        final religion_info Religion_info=arrayList.get(position);


        holder.ingredient_title.setText(arrayList.get(position).getReligion_name());
        holder.ingredient_content.setText(arrayList.get(position).getReligion_ingredient());
   }




    public int getItemCount(){
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }


    public class AHolder extends RecyclerView.ViewHolder{
        TextView ingredient_title,ingredient_content;
        Button check_allergic;

        public  AHolder(@NonNull View itemView){
            super(itemView);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference maindb= database.getReference("user");

            this.check_allergic=itemView.findViewById(R.id.check_allergic);
            this.ingredient_title=itemView.findViewById(R.id.ingredient_title);
            this.ingredient_content=itemView.findViewById(R.id.ingredient_content);

            check_allergic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid=user.getUid();
                    int pos= getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        religion_info Religion_info= arrayList.get(pos);
                        maindb.child(uid).child("myreligion_info").child(Religion_info.getReligion_name()).setValue(Religion_info.getReligion_ingredient());
                    }
                }
            });
        }
    }
    public ArrayList<religion_info>getArrayList(){
        return arrayList;
    }
}
