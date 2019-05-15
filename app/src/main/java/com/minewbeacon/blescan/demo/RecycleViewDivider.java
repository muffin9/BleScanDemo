package com.minewbeacon.blescan.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 2;//스플릿 라인 높이, 기본값은 2px
    private int mOrientation;//방향을 나열하십시오 : LinearLayoutManager.VERTICAL 또는 LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 기본 분할 선 : 높이 2 픽셀, 색상은 회색
     *
     * @param context
     * @param orientation 목록 방향
     */
    public RecycleViewDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager
                .HORIZONTAL) {
            throw new IllegalArgumentException("올바른 매개 변수를 입력하십시오!");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 구분선 맞춤 설정
     *
     * @param context
     * @param orientation 목록 방향
     * @param drawableId  선 그림 분할, 높이 기본값은 이미지의 원래 높이입니다.
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 구분선 맞춤 설정
     *
     * @param context
     * @param orientation 목록 방향
     * @param drawable    선 그림 분할, 높이 기본값은 이미지의 원래 높이입니다.
     */
    public RecycleViewDivider(Context context, int orientation, Drawable drawable) {
        this(context, orientation);
        mDivider = drawable;
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 구분선 맞춤 설정
     *
     * @param context
     * @param orientation 목록 방향
     * @param drawable    선 그림 분할
     * @param drawableHeight    분할 선 그림 높이
     */
    public RecycleViewDivider(Context context, int orientation, Drawable drawable, int
            drawableHeight) {
        this(context, orientation);
        mDivider = drawable;
        mDividerHeight = drawableHeight;
    }

    /**
     * 구분선 맞춤 설정
     *
     * @param context
     * @param orientation   목록 방향
     * @param dividerHeight 라인 높이 분할
     * @param dividerColor  선 색상 분할
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int
            dividerColor) {
        this(context, orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    //분할 선 크기 얻기
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //분할 선 그리기
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //수평 item 분할선 그리기
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //수직 item 분할선 그리기
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
