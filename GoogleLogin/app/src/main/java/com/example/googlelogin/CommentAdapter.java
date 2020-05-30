package com.example.googlelogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommentItem> arrayList = new ArrayList<CommentItem>();

    public CommentAdapter(Context context, ArrayList<CommentItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //뷰홀더 최초로 만들어내는 역할
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //각 아이템에 대한 매칭
        Glide.with(holder.itemView) //프로필 이미지를 url로 받아오기 용이하도록 글라이드 이용
                .load(arrayList.get(position).getIcon())
                .into(holder.CommentIcon);
        holder.CommentNum.setText(arrayList.get(position).getNum());
        holder.CommentName.setText(arrayList.get(position).getName());
        holder.Comment.setText(arrayList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0); //arrayList의 아이템 개수만큼 반환
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CommentNum;
        TextView CommentName;
        TextView Comment;
        ImageView CommentIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.CommentNum = itemView.findViewById(R.id.CommentNum);
            this.CommentName = itemView.findViewById(R.id.CommentName);
            this.Comment = itemView.findViewById(R.id.Comment);
            this.CommentIcon = itemView.findViewById(R.id.CommentIcon);
        }
    }
}

//import android.content.Context;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import org.w3c.dom.Comment;
//
//import java.util.ArrayList;
//
//
//public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
//    private Context context;
//    private ArrayList<CommentItem> items = new ArrayList<CommentItem>();
//
//    //클릭이벤트처리 관련 사용자 정의
////    OnItemClickListener listener; //참고로 OnItemClickListener는 기존에 있는것과 동일한 이름인데 그냥 같은 이름으로 내가 정의를 했다. (리스트뷰에서는 이게 자동구현되있어서 OnItemClickListener를 구현안하고 호출해서 클릭시 이벤트를 처리할 수 있음)
////    public  static interface  OnItemClickListener{
////        public void onItemClick(ViewHolder holder, View view, int position);
////    }
//
//
////    public CommentAdapter(Context context, ArrayList<CommentItem> list) {
////        this.context = context;
////        this.items = list;
////    }
//
//    public CommentAdapter(Context context) {
//        this.context = context;
//    }
//
//    //리사이클러뷰의 아이템 수를 세어줌
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    @NonNull
//    @Override
//    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = inflater.inflate(R.layout.comment_item_view,  parent, false); //viewGroup은 각각의 아이템을 위해서 정의한 xml레이아웃의 최상위 레이아웃이다.
//        return new ViewHolder(itemView); //각각의 아이템을 위한 뷰를 담고있는 뷰홀더객체를 반환 (각 아이템을 위한 XML 레이아웃을 이용해 뷰 객체를 만든 후 뷰홀더에 담아 반환)
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int position) {
//        CommentItem item = items.get(position); //리사이클러뷰에서 몇번째 아이템이 지금 보여야되는시점이다 알려주기위해
//        viewHolder.setItem(item); //그거를 홀더에넣어서 뷰홀더가 데이터를 알 수 있게되서 뷰홀더에 들어가있는 뷰에다가 데이터 설정할 수 있음
//        //클릭리스너
////        viewHolder.setOnItemClickListener(listener);
//    }
//
//    //아이템을 한개 추가해주고싶을때
//    public  void addItem(CommentItem item){
//        items.add(item);
//    }
//
//    //한꺼번에 추가해주고싶을때
//    public void addItems(ArrayList<CommentItem> items){
//        this.items = items;
//    }
//
//    //인덱스 값을 주면 아이템을 리턴
//    public CommentItem getItem(int position){
//        return items.get(position);
//    }
//
//
//    //클릭리스너관련
////    public void setOnItemClickListener(OnItemClickListener listener){
////        this.listener = listener;
////    }
////        OnItemClickListener listener; //클릭이벤트처리관련 변수
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView CommentNum;
//        private TextView CommentName;
//        private TextView Comment;
//        private ImageView CommentIcon;
//
//
//        public ViewHolder(@NonNull final View itemView) { //각각의 아이템을 위한 뷰를 담고있음
//            super(itemView);
//
//            CommentNum = itemView.findViewById(R.id.CommentNum);
//            CommentName = itemView.findViewById(R.id.CommentName);
//            Comment = itemView.findViewById(R.id.Comment);
//            CommentIcon= itemView.findViewById(R.id.CommentIcon);
//
//            //아이템 클릭이벤트처리
////            itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    int position = getAdapterPosition();
////                    if (listener != null) {
////                        listener.onItemClick(ViewHolder.this, itemView, position);
////                    }
////                }
////            });
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        public void setItem(CommentItem item) {
//            CommentName.setText(item.getName());
//            Comment.setText(item.getComment());
//            CommentIcon.setImageDrawable(item.getIcon());
//        }
//    }
//
//    //클릭이벤트처리
////    public void setOnItemClickListener(OnItemClickListener listener){
////        this.listener = listener;
////    }
//}

