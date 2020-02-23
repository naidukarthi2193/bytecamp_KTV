package com.venkat.ktv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRequestAdapter extends FirestoreRecyclerAdapter<AdminRequestModel, AdminRequestAdapter.AdminRequestViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private OnItemClickListener listener;

    public AdminRequestAdapter(@NonNull FirestoreRecyclerOptions<AdminRequestModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdminRequestViewHolder holder, int i, @NonNull AdminRequestModel model) {
        holder.uidn.setText(model.getUid());
        holder.adm_status.setText(model.getStatus());
    }

    @NonNull
    @Override
    public AdminRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adminrequest,parent,false);
        return new AdminRequestAdapter.AdminRequestViewHolder(v);
    }

    public class AdminRequestViewHolder extends RecyclerView.ViewHolder {
        TextView uidn,adm_status;
        public AdminRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            uidn = itemView.findViewById(R.id.adm_request);
            adm_status = itemView.findViewById(R.id.adm_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(AdminRequestAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
