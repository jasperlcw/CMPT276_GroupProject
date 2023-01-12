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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private static final String TAG = "SearchRecyclerAdapter";
    Context context;
    ArrayList<Post> postList;//DB

    public SearchRecyclerAdapter(ArrayList<Post> postList) {
        this.postList = postList;
    }//DB

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_post_details,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;


    }

    /**
     * fill the text and image of each view
     */
    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {
        //holder.rowCountTextView.setText(String.valueOf(position));
        holder.title.setText(postList.get(position).getPostTitle());
        holder.time.setText(postList.get(position).getPostTime().toString().replace('T', ' '));
        holder.content.setText(postList.get(position).getPostContent());
        holder.postID.setText(Integer.toString(postList.get(position).getPostID()));
        Glide.with(context).load(postList.get(position).getPictureLink()).into(holder.imageView);
        holder.author.setText(postList.get(position).getPostAuthor().getFirstName());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }// depend on search result


    /**
     * connect the text and image of each view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView title,content,author,time,postID;
        CardView postWrapper, postDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.post_detail_image);
            title=itemView.findViewById(R.id.post_detail_title);
            content=itemView.findViewById(R.id.post_detail_description);
            author=itemView.findViewById(R.id.post_detail_name);
            time=itemView.findViewById(R.id.post_detail_time);
            postWrapper=itemView.findViewById(R.id.post_wrapper);
            postDetail=itemView.findViewById(R.id.post_wrapper);
            postID =itemView.findViewById(R.id.post_detail_post_id);

            //rowCountTextView=itemView.findViewById(R.id.rowCountTextView);

            //itemView.setOnClickListener(this);
            imageView.setOnClickListener(this);
            title.setOnClickListener(this);
            content.setOnClickListener(this);
            author.setOnClickListener(this);
            time.setOnClickListener(this);
            postDetail.setOnClickListener(this);
            postWrapper.setOnClickListener(this);
            postID.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //note search recycler adapter is not a context
            Intent intent = PostPageActivity.makeIntent(context, Integer.parseInt(postID.getText().toString()));
            context.startActivity(intent);
        }
    }
}
