package com.example.googlelogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GuestBookActivity extends AppCompatActivity implements OnItemClick {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;
    public CommentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CommentItem> arrayList;

    private EditText Comment;

    private TextView CommentNum;
    private TextView CommentName;
    private TextView CommentDate;
    private ImageView CommentIcon;

    private Button btn_write;
    private Button btn_insert;
    private Button btn_delete;
    private Button btn_like;

    boolean likeState = false;
    boolean unlikeState = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_book);

        CommentNum = findViewById(R.id.CommentNum);
        CommentName = findViewById(R.id.CommentName);
        CommentDate = findViewById(R.id.CommentDate);
        Comment = findViewById(R.id.WriteComment);
        CommentIcon = findViewById(R.id.CommentIcon);

        btn_write = findViewById(R.id.btn_write);
        btn_insert = findViewById(R.id.btn_insert);
        btn_delete = findViewById(R.id.btn_delete);


        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestBookActivity.this, GuestBookActivity.class);
                startActivity(intent);
                Toast.makeText(GuestBookActivity.this, "방명록으로 이동!", Toast.LENGTH_SHORT).show();
            }
        });


        // DB의 데이터 불러와 어레이리스트에 넣기
        arrayList = new ArrayList<>();
        firestore.collection(FirebaseID.GuestBook)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                arrayList.clear();

                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    Map<String, Object> shot = snapshot.getData();
                                    String CommentNum = String.valueOf(shot.get(FirebaseID.num));
                                    String CommentName = String.valueOf(shot.get(FirebaseID.name));
                                    String Comment = String.valueOf(shot.get(FirebaseID.comment));
                                    String CommentIcon = String.valueOf(shot.get(FirebaseID.icon));
                                    String CommentDate = String.valueOf(shot.get(FirebaseID.date));

                                    CommentItem data = new CommentItem(CommentNum, CommentName, Comment, CommentIcon, CommentDate);
                                    arrayList.add(data);
                                }
                                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                            }
                        }
                    }
                });

        //어댑터와 리사이클러뷰 연결해서 화면에 띄우기
        recyclerView = (RecyclerView) findViewById(R.id.CommentList);
        recyclerView.setHasFixedSize(true);
        adapter = new CommentAdapter(arrayList, this);
        layoutManager = new LinearLayoutManager(this);

        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);

        recyclerView.scrollToPosition(0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // 글 작성 시 DB에 데이터 추가하기
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null) {

                    // 게시글 작성 시간 받아오기
                    long now = System.currentTimeMillis();
                    SimpleDateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Calendar date = Calendar.getInstance();

                    // 게시글 Number 증가
                    String newCount = String.valueOf(adapter.getItemCount() + 1);

                    Map<String, Object> data = new HashMap<>();
                    data.put(FirebaseID.documentID, firebaseAuth.getCurrentUser().getUid());
                    data.put(FirebaseID.num, newCount);
                    data.put(FirebaseID.name, "name");
                    //TODO 후에 icon부분 사용자가 직접 설정한 아이콘으로 변경
                    data.put(FirebaseID.icon, "https://firebasestorage.googleapis.com/v0/b/malivelogin.appspot.com/o/profile.png?alt=media&token=d2130f80-1023-440f-a9cc-6a183f88a975");
                    data.put(FirebaseID.comment, Comment.getText().toString());
                    data.put(FirebaseID.date, dateform.format(date.getTime()));

                    firestore.collection(FirebaseID.GuestBook).document(newCount).set(data, SetOptions.merge());
//                    firestore.collection(FirebaseID.GuestBook).document(Comment.getText().toString()).set(data, SetOptions.merge());
                    Toast.makeText(GuestBookActivity.this, "글쓰기 성공", Toast.LENGTH_SHORT).show();
                }

                // 새로 고침
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }


    // 방명록 글 삭제 메소드
    @Override
    public void deleteItem(final int position) {

        //삭제 여부를 묻는 알림창 띄우기
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제");
        builder.setMessage("삭제하시겠습니까?");
        builder.setCancelable(true);

        //"삭제" 버튼 클릭 시
        builder.setPositiveButton(
                "삭제",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                                // DB 내부의 데이터 삭제
//                                firestore.collection(FirebaseID.GuestBook).document(arrayList.get(position).getComment())
                                firestore.collection(FirebaseID.GuestBook).document(arrayList.get(position).getNum())
                                .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(GuestBookActivity.this, "삭제 완료!", Toast.LENGTH_LONG).show();
                                                Log.d("GuestBookActivity", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(GuestBookActivity.this, "삭제 실패!", Toast.LENGTH_LONG).show();
                                                Log.w("GuestBookActivity", "Error deleting document", e);
                                            }
                                        });
                        // 새로 고침
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

        //"취소" 버튼 클릭 시
        builder.setNegativeButton(
                "취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // 방명록 글 신고 메소드
    public void reportItem(final int position) {

        //신고 여부를 묻는 알림창 띄우기
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("신고");
        builder.setMessage("이 글을 신고하시겠습니까?");
        builder.setCancelable(true);

        //"신고" 버튼 클릭 시
        builder.setPositiveButton(
                "신고",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //신고할 데이터 "GuestBook" 컬렉션에서 받아오기
                        firestore.collection(FirebaseID.GuestBook).document(arrayList.get(position).getNum())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {

                                            //컬렉션 내의 document에 접근
                                            DocumentSnapshot document = task.getResult();

                                            if (document.exists()) {
                                                Map<String, Object> shot = document.getData();
                                                String CommentNum = String.valueOf(shot.get(FirebaseID.num));
                                                String CommentName = String.valueOf(shot.get(FirebaseID.name));
                                                String Comment = String.valueOf(shot.get(FirebaseID.comment));
                                                Log.d("GuestBookActivity", "신고할 데이터 Get 완료");

                                                //신고할 데이터 "ReportComment" 컬렉션에 추가
                                                Map<String, Object> data = new HashMap<>();
                                                data.put(FirebaseID.documentID, firebaseAuth.getCurrentUser().getUid());
                                                data.put(FirebaseID.name, "name");
                                                data.put(FirebaseID.comment, Comment);

                                                firestore.collection(FirebaseID.ReportComment).document(CommentNum).set(data, SetOptions.merge());
                                                Log.d("GuestBookActivity", "신고할 데이터 put 완료");

                                            } else {
                                                Log.d("GuestBookActivity", "No such document");
                                            }
                                        } else {
                                            Log.d("GuestBookActivity", "get failed with ", task.getException());
                                        }
                                    }
                                });

                        // 새로 고침
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

        //"취소" 버튼 클릭 시
        builder.setNegativeButton(
                "취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
