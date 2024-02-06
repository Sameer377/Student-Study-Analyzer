package com.page_1.app.DisplayFiles;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.page_1.app.R;

//import static com.page_1.app.DisplayFiles.DisplayFiles.progress;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, int position, @NonNull final model model) {

        holder.header.setText(model.getFilename());

        holder.textviewbook.setText(String.valueOf(model.getNov()));
        holder.textlike.setText(String.valueOf(model.getNol()));
        holder.textdislike.setText(String.valueOf(model.getNod()));
//        progress.dismiss();
                holder.cardfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(holder.img1.getContext(),viewpdf.class);
                        intent.putExtra("filename",model.getFilename());
                        intent.putExtra("fileurl",model.getFileurl());
                        Uri url=Uri.parse(model.getFileurl());
//                        startActivity(new Intent(Intent.ACTION_VIEW,url));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.img1.getContext().startActivity(new Intent(Intent.ACTION_VIEW,url));
                    }
                });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singelrowdesign,parent,false);
        return  new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
     {
         ImageView img1;
         CardView cardfile;
         TextView header;
         ImageView readbook,likebook,dislikebook;
         TextView textviewbook,textlike,textdislike;

         public myviewholder(@NonNull View itemView)
         {
             super(itemView);

             img1=itemView.findViewById(R.id.img1);
             header=itemView.findViewById(R.id.header);
             cardfile=itemView.findViewById(R.id.cardFile);
             readbook=itemView.findViewById(R.id.readbook);
             likebook=itemView.findViewById(R.id.likebook);
             dislikebook=itemView.findViewById(R.id.dislikebook);

             textviewbook=itemView.findViewById(R.id.textviewbook);
             textlike=itemView.findViewById(R.id.textlike);
             textdislike=itemView.findViewById(R.id.textdislike);
      }
     }
}
