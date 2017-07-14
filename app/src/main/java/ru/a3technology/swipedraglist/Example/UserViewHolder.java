package ru.a3technology.swipedraglist.Example;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.a3technology.swipedragdroplist.swiper.SwipeLayout;
import ru.a3technology.swipedraglist.R;

/**
 * Created by Stas on 21.04.2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder{

    public CardView cardView;
    public CardView cvButton_1, cvButton_2, cvButton_3;
    public SwipeLayout swipeLayout;
    public LinearLayout bottom_wrapper;

    public TextView tvCounter, tvTitle, tvTitleDescription, tvSubTitleDescription, tvStatus;

    UserViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView)itemView.findViewById(R.id.cardView);
        cvButton_1 = (CardView) itemView.findViewById(R.id.cvButton_1);
        cvButton_2 = (CardView) itemView.findViewById(R.id.cvButton_2);
        cvButton_3 = (CardView) itemView.findViewById(R.id.cvButton_3);

        swipeLayout = (SwipeLayout)itemView.findViewById(R.id.mSwipeLayout);
        bottom_wrapper = (LinearLayout)itemView.findViewById(R.id.bottom_wrapper);

        tvCounter = (TextView) itemView.findViewById(R.id.tvCounter);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvTitleDescription = (TextView) itemView.findViewById(R.id.tvTitleDescription);
        tvSubTitleDescription = (TextView) itemView.findViewById(R.id.tvSubTitleDescription);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);


    }
}
