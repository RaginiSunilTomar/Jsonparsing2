package com.gauri.jsonparsing2;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;


    public CarsAdapter(Context context, ArrayList<Post> posts) {//to fetch the data by using constructer
        this.posts=posts;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        // it will manipulate and inflate the layout which is generall the separat lyaout for it to
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.userid.setText(posts.get(i).getUserId().toString());
        viewHolder.title.setText(posts.get(i).getTitle());
        viewHolder.completed.setText(posts.get(i).getCompleted().toString());


        //Picasso.get().load(carsModels.get(i).getImage()).fit().centerCrop().into(viewHolder.car_image);
    }





    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userid,title,completed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userid=itemView.findViewById(R.id.tv);
            title= itemView.findViewById(R.id.tv2);
            completed=itemView.findViewById(R.id.tv3);
        }
    }


    //handeling click for recycler view

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }


    //creating inner class for handeling clicks in recycler view


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }





}
