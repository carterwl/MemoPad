package edu.jsu.mcis.cs408.memopad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private final MainActivity activity;
    private final List<Memo> memoList;

    public MemoAdapter(MainActivity activity, List<Memo> memoList) {
        super();
        this.activity = activity;
        this.memoList = memoList;
    }

    public Memo getItem(int position) {
        return memoList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_item, parent, false);

        view.setOnClickListener(activity.getItemClick());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.memoLabel.setText(memoList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView memoLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memoLabel = itemView.findViewById(R.id.memoLabel);
        }
    }
}