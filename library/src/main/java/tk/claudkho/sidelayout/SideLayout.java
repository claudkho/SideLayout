package tk.claudkho.sidelayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SideLayout extends LinearLayout implements Animator.AnimatorListener {
    private ObjectAnimator showAnimator, hideAnimator;
    private boolean isShowing;

    public SideLayout(Context context) {
        super(context);
    }

    public SideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(int width) {
        int childCount = getChildCount();
        if (childCount > 1) {
            View lastView = getChildAt(getChildCount() - 1);
            float sideTransX = -(width - lastView.getMeasuredWidth());
            setTranslationX(sideTransX);
            showAnimator = ObjectAnimator.ofFloat(this, "translationX", sideTransX, 0);
            showAnimator.addListener(this);
            hideAnimator = ObjectAnimator.ofFloat(this, "translationX", 0, sideTransX);
            showAnimator.addListener(this);
        } else {
            throw new RuntimeException("SideLayout must have 2 or more child views");
        }
    }

    public void toggle() {
        if (!isShowing)
            show();
        else {
            hide();
        }
    }

    public void show() {
        isShowing = true;
        showAnimator.start();
    }

    public void hide() {
        isShowing = false;
        hideAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init(w);
    }

    @Override
    public void onAnimationStart(Animator animator) {
        setClickable(false);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        setClickable(true);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
