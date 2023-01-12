package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplyRecyclerAdapter extends RecyclerView.Adapter<ReplyRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ReplyRecyclerAdapter";
    Context context;
    ArrayList<Reply> replyList;//DB

    public ReplyRecyclerAdapter(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }//DB

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_replying_details,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;

    }

    /**
     * fill the text and image of each view
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.rowCountTextView.setText(String.valueOf(position));
        holder.time.setText(replyList.get(position).getReplyTime().toString().replace('T', ' '));
        holder.content.setText(replyList.get(position).getReplyContent());
        holder.replyID.setText(Integer.toString(replyList.get(position).getReplyID()));
        holder.author.setText(replyList.get(position).getReplyAuthor().getFirstName()+" "+replyList.get(position).getReplyAuthor().getLastName());
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }


    /**
     * connect the text and image of each view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView content,author,time,replyID;
        //CardView postWrapper, postDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            content=itemView.findViewById(R.id.reply_description);
            author=itemView.findViewById(R.id.reply_name);
            time=itemView.findViewById(R.id.reply_time);
            //postWrapper=itemView.findViewById(R.id.post_wrapper);
            //postDetail=itemView.findViewById(R.id.post_wrapper);
            replyID =itemView.findViewById(R.id.reply_id);

            //rowCountTextView=itemView.findViewById(R.id.rowCountTextView);

            //itemView.setOnClickListener(this);
            //content.setOnClickListener(this);
            //author.setOnClickListener(this);
            //time.setOnClickListener(this);
            //postDetail.setOnClickListener(this);
            //postWrapper.setOnClickListener(this);
            //replyID.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //note search recycler adapter is not a context
            //Intent intent = PostPageActivity.makeIntent(context, Integer.parseInt(postID.getText().toString()));
            //context.startActivity(intent);
        }
    }
}
