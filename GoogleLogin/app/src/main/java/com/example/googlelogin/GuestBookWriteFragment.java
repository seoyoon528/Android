package com.example.googlelogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GuestBookWriteFragment extends Fragment {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public CommentAdapter adapter;
    private ArrayList<CommentItem> arrayList;

    private EditText Comment;
    private Button btn_insert;

    // 값 받아오는 변수들
    private int CommentCount;
    private String newCount;
    private SimpleDateFormat dateform;
    private Calendar date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guestbook_write, container, false);

        Comment = view.findViewById(R.id.WriteComment);
        btn_insert = view.findViewById(R.id.btn_insert);

        if(getArguments() != null){
            // 게시글 Count 받아오기
            CommentCount = getArguments().getInt("CommentCount");

        }

        // 글 작성 시 DB에 데이터 추가하기
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (firebaseAuth.getCurrentUser() != null) {

                        // 게시글 작성 시간 받아오기
                        long now = System.currentTimeMillis();
                        dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        date = Calendar.getInstance();

                        // 게시글 Number 증가
                        newCount = String.valueOf(CommentCount + 1);

                        // DB에 데이터 추가
                        Map<String, Object> data = new HashMap<>();
                        data.put(FirebaseID.documentID, firebaseAuth.getCurrentUser().getUid());
                        data.put(FirebaseID.num, newCount);
                        data.put(FirebaseID.name, "name");
                        //TODO 후에 icon부분 사용자가 직접 설정한 아이콘으로 변경
                        data.put(FirebaseID.icon, "https://firebasestorage.googleapis.com/v0/b/malivelogin.appspot.com/o/profile.png?alt=media&token=d2130f80-1023-440f-a9cc-6a183f88a975");
                        data.put(FirebaseID.comment, Comment.getText().toString());
                        data.put(FirebaseID.date, dateform.format(date.getTime()));

                        firestore.collection(FirebaseID.GuestBook).document(newCount).set(data, SetOptions.merge());
                    }
            }
        });
        return view;
    }
}
