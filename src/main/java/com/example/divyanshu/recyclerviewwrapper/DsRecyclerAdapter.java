package com.example.divyanshu.recyclerviewwrapper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DsRecyclerAdapter<T> extends RecyclerView.Adapter<DsRecyclerAdapter.DsViewHolder> {

    ArrayList<T> list;
    int layout;
    ArrayList<DsView> views;
    Class c;
    listItemClickLister itemClickLister;


    public DsRecyclerAdapter(ArrayList<T> list,int layout,Class c,listItemClickLister itemClickLister){
        this.list=list;
        this.layout=layout;
        this.c=c;
        this.itemClickLister=itemClickLister;
        views=new ArrayList<>();
    }

    public void addView(String type,int view,String fieldName){
        views.add(new DsView(type,view,fieldName));
    }

    @NonNull
    @Override
    public DsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new DsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DsViewHolder holder, int position) {

        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class DsViewHolder<T> extends RecyclerView.ViewHolder {

        ArrayList<View> allViews;
        public DsViewHolder(View itemView) {
            super(itemView);

            allViews=new ArrayList<>();
            for(DsView v:views){

                int id=v.getId();

                if(v.getView().equals("TextView")){
                    TextView view=(TextView)itemView.findViewById(id);
                     allViews.add(view);

                }
                else if(v.getView().equals("ImageView")){
                    ImageView view=(ImageView) itemView.findViewById(id);
                    allViews.add(view);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickLister.itemClicked(list.get(getAdapterPosition()));
                }
            });

        }

        public void bind(T item){

            for(int i=0;i<allViews.size();i++) {

                View v = allViews.get(i);
                Class aClass = c;
                Method method = null;

                try {
                    if (v instanceof TextView) {


                        method = aClass.getDeclaredMethod("get" + views.get(i).getFieldName().replace(views.get(i).getFieldName().charAt(0) + "", (views.get(i).getFieldName().charAt(0) + "").toUpperCase()));

                        Object value = method.invoke(item);


                        ((TextView) v).setText(value.toString());

                    }

                else if (v instanceof ImageView) {

                    method = aClass.getDeclaredMethod("get" + views.get(i).getFieldName().replace(views.get(i).getFieldName().charAt(0) + "", (views.get(i).getFieldName().charAt(0) + "").toUpperCase()));

                    Object value = method.invoke(item);


                    ((ImageView) v).setImageResource(Integer.parseInt(value.toString()));

                }
            }
                catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  interface listItemClickLister<T>{
        void itemClicked(T t);
    }
}
