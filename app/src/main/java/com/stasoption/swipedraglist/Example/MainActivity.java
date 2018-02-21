package com.stasoption.swipedraglist.Example;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import com.stasoption.swipedragdroplist.adapters.SwipeDragDropAdapter;
import com.stasoption.swipedragdroplist.intefaces.SwipeDragDropListener;
import com.stasoption.swipedraglist.R;
import com.stasoption.swipedraglist.Model.User;


public class MainActivity extends AppCompatActivity implements SwipeDragDropListener<User> {

    private SwipeDragDropAdapter<User> mUserAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);

        ArrayList<User> testUserArrayList = new ArrayList<>();
        testUserArrayList.add(new User("Stas Averin", "averin.developer@gmail.com", "31 years", true));
        testUserArrayList.add(new User("Steve Rogers", "cwilliams@gmail.com", "29 years", false));
        testUserArrayList.add(new User("Peter Parker", "pete@gmail.com", "21 years", false));
        testUserArrayList.add(new User("Natasha Romanoff",  "pwong@gmail.com", "28 years", true));
        testUserArrayList.add(new User("Tony Stark", "bmartinez@gmail.com", "45 years", false));
        testUserArrayList.add(new User("Bruce Banner", "ralph_washington@gmail.com", "41 years", true));


        mUserAdapter = new SwipeDragDropAdapter<User>(testUserArrayList) {

            @NonNull
            @Override
            public Context setContext() {
                return MainActivity.this;
            }

            @Override
            public int setSurfaceView() {
                return R.layout.item_surface_view;
            }

            @Override
            public int setBottomView() {
                return R.layout.item_bottom_view;
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(View view) {
                return new UserViewHolder(view);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder h, User user, int position) {
                UserViewHolder holder = (UserViewHolder) h;
                try {
                    holder.tvNumber.setText(String.valueOf(position + 1));
                    holder.tvName.setText(user.getName());
                    holder.tvAge.setText(user.getAge());
                    holder.tvEmail.setText(user.getMail());
                    holder.tvStatus.setTextColor(user.getStatus() ? Color.BLUE :Color.RED);
                    holder.tvStatus.setText(user.getStatus() ? R.string.text_online : R.string.text_offline);

                    holder.mSurface.setOnClickListener(v -> {
                        closeAllItems();
                        onItemClicked(user, position);
                    });
                    holder.mBottomBtn_1.setOnClickListener(v -> {
                        closeAllItems();
                        Log.d("Button 1 clicked", position + ". " + user.getName());
                    });
                    holder.mBottomBtn_2.setOnClickListener(v -> {
                        closeAllItems();
                        Log.d("Button 2 clicked", position + ". " + user.getName());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onExceptionReceived(Exception e) {
                e.printStackTrace();
            }
        };

        mUserAdapter.setMode(SwipeDragDropAdapter.Mode.MULTIPLE);
        mUserAdapter.bindToRecyclerView(mRecyclerView);
        mUserAdapter.setSwipeDragDropListener(this);
    }

    @Override
    public void onItemClicked(@Nullable User val, int position) {
        Log.d("onItemClicked", position + ". " + val.getName());
    }

    @Override
    public void onItemOpened(int position) {
        Log.d("onItemOpened", position + ". " + mUserAdapter.getItem(position).getName());
    }

    @Override
    public void onItemClosed(int position) {
        Log.d("onItemClosed", position + ". " + mUserAdapter.getItem(position).getName());
    }

    @Override
    public void onItemDragged(int from, int to) {
        Log.d("onItemDragged", from + ". " + to);
    }
}



