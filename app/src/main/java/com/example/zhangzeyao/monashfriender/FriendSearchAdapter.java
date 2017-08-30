package com.example.zhangzeyao.monashfriender;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Button;

import com.example.zhangzeyao.monashfriender.models.Student;

import java.util.ArrayList;

/**
 * Created by zhangzeyao on 4/5/17.
 */

public class FriendSearchAdapter extends BaseAdapter {

    private Context m_cCurrentContext;

    private ArrayList<Student> m_FriendList;

    public FriendSearchAdapter(Context m_cCurrentContext, ArrayList<Student> m_FriendList) {
        this.m_cCurrentContext = m_cCurrentContext;
        this.m_FriendList = m_FriendList;
    }

    @Override
    public int getCount() {
        return m_FriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return m_FriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) m_cCurrentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_student_item, null);
        }

        // Set the textview and button connected to the ones in xml file
        TextView friendnameView = (TextView)convertView.findViewById(R.id.friendNameText);
        TextView favMovieView = (TextView)convertView.findViewById(R.id.favMovieText);
        TextView favSportView = (TextView)convertView.findViewById(R.id.favSportText);
        TextView favUnitView = (TextView)convertView.findViewById(R.id.favUnitText);
        TextView currJobView = (TextView)convertView.findViewById(R.id.currJobText);
        final Button viewButton = (Button)convertView.findViewById(R.id.viewButton);


        friendnameView.setText(m_FriendList.get(position).getFirstname() + " "+ m_FriendList.get(position).getSurname());
        favMovieView.setText("Favorite Movie: "+m_FriendList.get(position).getFavmovie());
        favSportView.setText("Favorite Sport: "+m_FriendList.get(position).getFavsport());
        favUnitView.setText("Favorite Unit: "+m_FriendList.get(position).getFavunit());
        currJobView.setText("Current Job"+m_FriendList.get(position).getCurrjob());
        viewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(m_cCurrentContext,ViewStudentActivity.class);
                newIntent.putExtra("Student",m_FriendList.get(position));
                m_cCurrentContext.startActivity(newIntent);
            }
        });



        return convertView;
    }
}
