package com.example.googlelogin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuestBookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private Button buttonInsert;
    private EditText writeComment;
    private ArrayList<CommentItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_book);

        recyclerView = (RecyclerView) findViewById(R.id.CommentList);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // 방명록 CommentItem 객체를 담을 arrayList

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("User"); //

        //리사이클러뷰 최근 글이 맨 위로 올라오도록
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);

        buttonInsert = (Button) findViewById(R.id.ButtonInsert);
        writeComment = (EditText) findViewById(R.id.WriteComment);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List 추출
                    CommentItem commentItem = snapshot.getValue(CommentItem.class);
                    arrayList.add(commentItem); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던 중 에러 발생 시
                Log.e("GuestBookActivity", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });
        adapter = new CommentAdapter(this, arrayList);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 추가
    }
}

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class GuestBookActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private Button buttonInsert;
//    private EditText writeComment;
//    private ArrayList<CommentItem> mArrayList;
//    private CommentAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_guest_book);
//
//        recyclerView = (RecyclerView)findViewById(R.id.CommentList);
//        buttonInsert = (Button) findViewById(R.id.ButtonInsert);
//        writeComment = (EditText) findViewById(R.id.WriteComment);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        //리사이클러뷰 최근 글이 맨 위로 올라오도록
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(layoutManager);
//
//        //방명록 글 추가 어댑터
//        final CommentAdapter adapter = new CommentAdapter(getApplicationContext());
////        adapter.addItem(new CommentItem("박서윤","빨래좀 해!", ContextCompat.getDrawable(this,R.drawable.profile1)));
////        adapter.addItem(new CommentItem("박똥개","집이 너무 깨끗해", ContextCompat.getDrawable(this,R.drawable.profile1)));
//        recyclerView.setAdapter(adapter);
//
//        //글 작성 버튼 눌렀을 때
//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String comment = writeComment.getText().toString();
//                adapter.addItem(new CommentItem("test",  comment , ContextCompat.getDrawable(getApplicationContext(), R.drawable.profile1)));
//                adapter.notifyDataSetChanged();
//                Toast.makeText(GuestBookActivity.this, "글 작성 성공", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//}