package com.varunkumar123.jobseeker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.varunkumar123.jobseeker.R;
import com.varunkumar123.jobseeker.model.SearchByTech;

import java.util.List;

public class SearchTechAdapter extends RecyclerView.Adapter<SearchTechAdapter.MyView>  {
    List<SearchByTech> list;
    public SearchTechAdapter(List<SearchByTech> list){
        this.list =list;
    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_by_tech_item,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
       SearchByTech m= list.get(position);
       holder.tFullName.setText(m.getName());
       holder.tEmail.setText(m.getEmail());
       holder.tJobTitle.setText(m.getJobTitle());
       holder.tJobRole.setText(m.getJobRole());
       holder.tSkills.setText(m.getSkills());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyView extends RecyclerView.ViewHolder{
        TextView tFullName,tEmail,tJobTitle,tJobRole,tSkills;
        public MyView(@NonNull View itemView){
            super(itemView);
            tFullName=itemView.findViewById(R.id.tech_Full_Name);
            tEmail=itemView.findViewById(R.id.tech_Email);
            tJobTitle=itemView.findViewById(R.id.tech_jobTitle);
            tJobRole=itemView.findViewById(R.id.tech_jobRole);
            tSkills=itemView.findViewById(R.id.tech_skills);
        }
    }
}
