package com.example.guestbook_proto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Comment;

import java.util.ArrayList;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;

    ArrayList<CommentItem> items = new ArrayList<CommentItem>();

    //클릭이벤트처리 관련 사용자 정의
//    OnItemClickListener listener; //참고로 OnItemClickListener는 기존에 있는것과 동일한 이름인데 그냥 같은 이름으로 내가 정의를 했다. (리스트뷰에서는 이게 자동구현되있어서 OnItemClickListener를 구현안하고 호출해서 클릭시 이벤트를 처리할 수 있음)
//    public  static interface  OnItemClickListener{
//        public void onItemClick(ViewHolder holder, View view, int position);
//    }

    public CommentAdapter(Context context) {
        this.context = context;
    }

    //테스트용 Adapter
//    public CommentAdapter(ArrayList<CommentItem> list) {
//        this.items = list;
//    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.comment_item_view,  parent, false); //viewGroup은 각각의 아이템을 위해서 정의한 xml레이아웃의 최상위 레이아웃이다.

        return new ViewHolder(itemView); //각각의 아이템을 위한 뷰를 담고있는 뷰홀더객체를 반환한다.(각 아이템을 위한 XML 레이아웃을 이용해 뷰 객체를 만든 후 뷰홀더에 담아 반환 null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int position) {
        CommentItem item = items.get(position); //리사이클러뷰에서 몇번쨰게 지금 보여야되는시점이다 알려주기위해
        viewHolder.setItem(item); //그거를 홀더에넣어서 뷰홀더가 데이터를 알 수 있게되서 뷰홀더에 들어가있는 뷰에다가 데이터 설정할 수 있음
        //클릭리스너
//        viewHolder.setOnItemClickListener(listener);
    }

    //아이템을 한개 추가해주고싶을때
    public  void addItem(CommentItem item){
        items.add(item);
    }

    //한꺼번에 추가해주고싶을때
    public void addItems(ArrayList<CommentItem> items){
        this.items = items;
    }

    //인덱스 값을 주면 아이템을 리턴
    public CommentItem getItem(int position){
        return items.get(position);
    }


    //클릭리스너관련
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener = listener;
//    }

//        OnItemClickListener listener; //클릭이벤트처리관련 변수

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CommentNum;
        TextView CommentID;
        TextView Comment;
        ImageView imageView;


        public ViewHolder(@NonNull final View itemView) { //뷰홀더는 각각의 아이템을 위한 뷰를 담고있다.
            super(itemView);

            CommentNum = itemView.findViewById(R.id.CommentNum);
            CommentID = itemView.findViewById(R.id.CommentID);
            Comment = itemView.findViewById(R.id.Comment);
            imageView = itemView.findViewById(R.id.imageView);

            //아이템 클릭이벤트처리
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null) {
//                        listener.onItemClick(ViewHolder.this, itemView, position);
//                    }
//                }
//            });
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setItem(CommentItem item) {
            CommentID.setText(item.getName());
            Comment.setText(item.getComment());
            imageView.setImageDrawable(item.getIcon());
        }

    }

    //클릭이벤트처리
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener = listener;
//    }

}
