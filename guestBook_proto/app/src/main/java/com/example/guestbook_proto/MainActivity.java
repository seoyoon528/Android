package com.example.guestbook_proto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.guestbook_proto.R.drawable.profile1;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button buttonInsert;
    ArrayList<CommentItem> mArrayList;
    EditText writeComment;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.CommentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //리사이클러뷰 최근 글이 맨 위로 올라오도록
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

//        mArrayList = new ArrayList<>();
//        adapter = new CommentAdapter(mArrayList);

        final CommentAdapter adapter = new CommentAdapter(getApplicationContext());

//        adapter.addItem(new CommentItem("박서윤","빨래좀 해!", ContextCompat.getDrawable(this,R.drawable.profile)));
//        adapter.addItem(new CommentItem("박똥개","집이 너무 깨끗해", ContextCompat.getDrawable(this,R.drawable.profile3)));

        recyclerView.setAdapter(adapter);

        buttonInsert = (Button) findViewById(R.id.ButtonInsert);
        writeComment = (EditText) findViewById(R.id.WriteComment);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = writeComment.getText().toString();
//                CommentItem data = new CommentItem("test", comment, ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile1));
//                mArrayList.add(data);

                adapter.addItem(new CommentItem("test",  comment , ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile1)));
//                adapter.notifyItemChanged(0);
                adapter.notifyDataSetChanged();
                
            }
        });
    }

}
