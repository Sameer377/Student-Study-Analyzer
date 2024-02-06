package com.page_1.app.DisplayFiles.Links;


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.page_1.app.R;
        import com.page_1.app.admin.Fees.FeeModel;

        import java.util.ArrayList;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.Viewholder> {

    private Context context;
    private ArrayList<LinksModel> LinksModelArrayList;

    // Constructor
    public LinksAdapter(Context context, ArrayList<LinksModel> LinksModelArrayList) {
        this.context = context;
        this.LinksModelArrayList = LinksModelArrayList;
    }

    @NonNull
    @Override
    public LinksAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.links_card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinksAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        LinksModel model = LinksModelArrayList.get(position);
        holder.urlName.setText(model.getUrlName());
        holder.url.setText(model.getUrl());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return LinksModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView urlName,url;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            urlName = itemView.findViewById(R.id.UrlName);
            url = itemView.findViewById(R.id.URL);


        }
    }
}
