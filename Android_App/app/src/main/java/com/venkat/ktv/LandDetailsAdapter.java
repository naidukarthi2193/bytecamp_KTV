package com.venkat.ktv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LandDetailsAdapter extends FirestoreRecyclerAdapter<LandDetailsModel, LandDetailsAdapter.LandDetailsViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    //public DetailsAdapterListener onClickListener;
    private OnItemClickListener listener;
    public LandDetailsAdapter(@NonNull FirestoreRecyclerOptions<LandDetailsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LandDetailsViewHolder holder, int i, @NonNull LandDetailsModel model) {

        holder.name.setText(model.getName());
        holder.phno.setText(model.getPhnum());
        holder.district.setText(model.getDistrict());
        holder.taluka.setText(model.getTaluka());
        holder.village.setText(model.getVillage());
        holder.div.setText(model.getDivision());
        holder.status.setText(model.getStatus());
        if(model.getStatus().equals("Verified")){
            holder.applygeo.setVisibility(View.GONE);
            holder.viewLand.setVisibility(View.VISIBLE);
            holder.viewappl.setVisibility(View.GONE);
        }
        else if(model.getStatus().equals("Applied")){
            holder.applygeo.setVisibility(View.GONE);
            holder.viewLand.setVisibility(View.GONE);
            holder.viewappl.setVisibility(View.VISIBLE);
        }
        else{
            holder.applygeo.setVisibility(View.VISIBLE);
            holder.viewLand.setVisibility(View.GONE);
            holder.viewappl.setVisibility(View.GONE);
        }

    }

    @NonNull
    @Override
    public LandDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_farmer712_details,parent,false);
        return new LandDetailsAdapter.LandDetailsViewHolder(v);
    }

    public class LandDetailsViewHolder extends RecyclerView.ViewHolder{

        TextView name,phno,district,taluka,village,div,surveyno,status,farmer;
        Button applygeo,viewLand,viewappl;
        public LandDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTV);
            phno = itemView.findViewById(R.id.phnoTV);
            district = itemView.findViewById(R.id.distTV);
            taluka = itemView.findViewById(R.id.talukaTV);
            village = itemView.findViewById(R.id.villageTV);
            div = itemView.findViewById(R.id.divTV);
            //surveyno = itemView.findViewById(R.id.surveyTV);
            status = itemView.findViewById(R.id.statusTV);
            applygeo = itemView.findViewById(R.id.apply_geofenceBtn);
            viewLand = itemView.findViewById(R.id.viewLand);
            viewappl = itemView.findViewById(R.id.viewAppl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

//            applygeo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickListener.classOnClick(v, getAdapterPosition());
//                }
//            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

//    public interface DetailsAdapterListener {
//
//        void classOnClick(View v, int position);
//
//    }
}
