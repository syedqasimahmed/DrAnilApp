package mmcom.ui.dranil.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;

import mmcom.ui.dranil.R;
import mmcom.ui.dranil.model.CourseModel;

/**
 * Created by Qasim Ahmed on 06/01/2019.
 */

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.MyViewHolder> {

private ArrayList<CourseModel> arrayList ;
        Context acontext;
public MyAdapterListener onClickListener;

    public void setOnClickListener(MyAdapterListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface MyAdapterListener {
    void onSwicthClickListner(View v, int position,Boolean isAllowed);
    void onStartLectureListner(View v , int position);
}

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tv_name, tv_StartLecture, tv_phoneno;
    public ImageView iveditcompany,ivdeletecompany;
    ImageView ivcompany ;
    CardView cardView;
    LinearLayout layout_allowed;
    LinearLayout layout_not_allowed;


    public MyViewHolder(View v){
        super(v);


        cardView = (CardView) v.findViewById(R.id.cardview);
        tv_name= (TextView) v.findViewById(R.id.coursename);
        tv_StartLecture = (TextView) v.findViewById(R.id.startlecture);
        layout_allowed = v.findViewById(R.id.layout_allowed);
        layout_not_allowed = v.findViewById(R.id.layout_not_allowed);
        itemView.setOnClickListener(this); // bind the listener
        tv_StartLecture.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
     final CourseModel c = arrayList.get(getAdapterPosition());
        switch (v.getId()){
            case R.id.startlecture:
               // open Lecture Screen
                if (onClickListener != null) onClickListener.onStartLectureListner(v, getAdapterPosition());
                break;



        }




    }
}


    public AdapterCourse(Context context, ArrayList<CourseModel> arrayList , MyAdapterListener listener) {
        this.arrayList = arrayList;
        acontext = context;
        onClickListener = listener;

    }
    @Override
    public AdapterCourse.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcourselist, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){

        CourseModel current = arrayList.get(position);
        Log.d("Data"," name : "+ current.getLectureName());
        holder.tv_name.setText(current.getLectureName());
        if(current.getStatus() != null && current.getStatus().equals("allowed"))
        {
            holder.layout_allowed.setVisibility(View.VISIBLE);
            holder.layout_not_allowed.setVisibility(View.GONE);

        }else{
            holder.layout_not_allowed.setVisibility(View.VISIBLE);
            holder.layout_allowed.setVisibility(View.GONE);

        }
        holder.layout_not_allowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                onClickListener.onSwicthClickListner(holder.cardView, position,false);
            }
        });
        holder.layout_allowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                onClickListener.onSwicthClickListner(holder.cardView, position,true);
            }
        });




    }

    @Override
    public int getItemCount() { return arrayList.size(); }

}
