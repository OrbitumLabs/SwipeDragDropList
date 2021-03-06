package com.stasoption.swipedragdroplist.swiper;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.stasoption.swipedragdroplist.adapters.SwipeDragDropAdapter;
import com.stasoption.swipedragdroplist.intefaces.SwipeDragDropListener;
import com.stasoption.swipedragdroplist.intefaces.SwipeItemMangerInterface;


public class SwipeItemManager implements SwipeItemMangerInterface {

    private SwipeDragDropAdapter.Mode mode = SwipeDragDropAdapter.Mode.SINGLE;
    private final int INVALID_POSITION = -1;

    private int mOpenPosition = INVALID_POSITION;

    private Set<Integer> mOpenPositions = new HashSet<>();
    private Set<SwipeLayout> mShownLayouts = new HashSet<>();

    @Nullable
    private SwipeDragDropListener mSwipeDragDropListener;

    public SwipeItemManager() {

    }

    public SwipeDragDropAdapter.Mode getMode() {
        return mode;
    }


    public void setMode(SwipeDragDropAdapter.Mode mode) {
        this.mode = mode;
        mOpenPositions.clear();
        mShownLayouts.clear();
        mOpenPosition = INVALID_POSITION;
    }

    public void setSwipeDragDropListener(@Nullable SwipeDragDropListener swipeDragDropListener){
        this.mSwipeDragDropListener = swipeDragDropListener;
    }

    public void bind(SwipeLayout swipeLayout, int position) throws Exception{
        int resId = swipeLayout.getId();
        if (swipeLayout.getTag(resId) == null) {
            OnLayoutListener onLayoutListener = new OnLayoutListener(position);
            SwipeMemory swipeMemory = new SwipeMemory(position);
            swipeLayout.addSwipeListener(swipeMemory);
            swipeLayout.addOnLayoutListener(onLayoutListener);
            swipeLayout.setTag(resId, new ValueBox(position, swipeMemory, onLayoutListener));
            mShownLayouts.add(swipeLayout);
        } else {
            ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
            valueBox.swipeMemory.setPosition(position);
            valueBox.onLayoutListener.setPosition(position);
            valueBox.position = position;
        }
    }

    @Override
    public void openItem(int position) {
        if (mode == SwipeDragDropAdapter.Mode.MULTIPLE) {
            if (!mOpenPositions.contains(position))
                mOpenPositions.add(position);
        } else {
            mOpenPosition = position;
        }

        if(mSwipeDragDropListener != null){
            mSwipeDragDropListener.onItemOpened(position);
        }
    }

    @Override
    public void closeItem(int position) {
        if (mode == SwipeDragDropAdapter.Mode.MULTIPLE) {
            mOpenPositions.remove(position);
        } else {
            if (mOpenPosition == position)
                mOpenPosition = INVALID_POSITION;
        }

        if(mSwipeDragDropListener != null){
            mSwipeDragDropListener.onItemClosed(position);
        }
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        for (SwipeLayout s : mShownLayouts) {
            if (s != layout)
                s.close();
        }
    }

    @Override
    public void closeAllItems() {
        if (mode == SwipeDragDropAdapter.Mode.MULTIPLE) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        for (SwipeLayout s : mShownLayouts) {
            s.close();
        }
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mShownLayouts.remove(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        if (mode == SwipeDragDropAdapter.Mode.MULTIPLE) {
            return new ArrayList<Integer>(mOpenPositions);
        } else {
            return Collections.singletonList(mOpenPosition);
        }
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList<SwipeLayout>(mShownLayouts);
    }

    @Override
    public boolean isOpen(int position) {
        if (mode == SwipeDragDropAdapter.Mode.MULTIPLE) {
            return mOpenPositions.contains(position);
        } else {
            return mOpenPosition == position;
        }
    }

    private class ValueBox {
        OnLayoutListener onLayoutListener;
        SwipeMemory swipeMemory;
        int position;

        ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    private class OnLayoutListener implements SwipeLayout.OnLayout {

        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onLayout(SwipeLayout v) {
            if (isOpen(position)) {
                v.open(false, false);
            } else {
                v.close(false, false);
            }
        }

    }

    private class SwipeMemory extends SimpleSwipeListener {

        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        @Override
        public void onClose(SwipeLayout layout) {
            if (mode == SwipeDragDropAdapter.Mode.MULTIPLE) {
                mOpenPositions.remove(position);
            } else {
                mOpenPosition = INVALID_POSITION;
            }

            if(mSwipeDragDropListener != null){
                mSwipeDragDropListener.onItemClosed(position);
            }
        }

        @Override
        public void onStartOpen(SwipeLayout layout) {
            if (mode == SwipeDragDropAdapter.Mode.SINGLE) {
                closeAllExcept(layout);
            }
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            if (mode == SwipeDragDropAdapter.Mode.MULTIPLE)
                mOpenPositions.add(position);
            else {
                closeAllExcept(layout);
                mOpenPosition = position;
            }

            if(mSwipeDragDropListener != null){
                mSwipeDragDropListener.onItemOpened(position);
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
