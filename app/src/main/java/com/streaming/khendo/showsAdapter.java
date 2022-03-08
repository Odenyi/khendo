package com.streaming.khendo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khendo.R;

import java.util.ArrayList;

public class showsAdapter extends RecyclerView.Adapter<showsAdapter.MyViewHolder> {

        ArrayList<ShowsModel> mList;
        Context context;




public showsAdapter(Context context, ArrayList<ShowsModel> mList) {

        this.mList = mList;
        this.context = context;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule, parent, false);

        return new MyViewHolder(v);

        }

    @Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       ShowsModel model = (ShowsModel) mList.get(position);
        holder.show.setText(model.getShow());
        holder.about.setText(model.getAbout());
        holder.presenters.setText(model.getPresenters());
        holder.start.setText(model.getTimestart());
        holder.stop.setText(model.getTimestop());
        holder.day.setText(model.getDay());
    }


        @Override
        public int getItemCount () {
            return mList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView  show,about, presenters,start, stop, day;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                show = itemView.findViewById(R.id.showTv1);
                about = itemView.findViewById(R.id.abouttv);
                presenters = itemView.findViewById(R.id.presentersTv1);
                start = itemView.findViewById(R.id.fromtv1);
                stop = itemView.findViewById(R.id.totv1);
                day = itemView.findViewById(R.id.daytv1);
            }
        }
    }

