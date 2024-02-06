package com.page_1.app.admin.Fees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.page_1.app.R;

import java.util.ArrayList;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.Viewholder> {

    private Context context;
    private ArrayList<FeeModel> FeeModelArrayList;

    // Constructor
    public FeeAdapter(Context context, ArrayList<FeeModel> FeeModelArrayList) {
        this.context = context;
        this.FeeModelArrayList = FeeModelArrayList;
    }

    @NonNull
    @Override
    public FeeAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        FeeModel model = FeeModelArrayList.get(position);
        holder.ERP.setText(model.getERP());
        holder.NameFee.setText(model.getName());
        holder.Amount.setText(model.getAmount());
        holder.PayId.setText(model.getPayId());
        holder.Date.setText(model.getDate());
        holder.Time.setText(model.getTime());
        holder.count.setText(model.getCount());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return FeeModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView ERP,NameFee,Amount,PayId,Date,Time,count;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ERP = itemView.findViewById(R.id.ERP);
            NameFee = itemView.findViewById(R.id.NameFee);
            Amount = itemView.findViewById(R.id.Amount);
            PayId = itemView.findViewById(R.id.PAYID);
            Date = itemView.findViewById(R.id.Date);
            Time=itemView.findViewById(R.id.Time);
            count=itemView.findViewById(R.id.count);

        }
    }
}
