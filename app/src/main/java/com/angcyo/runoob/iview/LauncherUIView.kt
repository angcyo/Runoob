package com.angcyo.runoob.iview

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import com.angcyo.runoob.R
import com.angcyo.runoob.base.BaseContentUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.resources.AnimUtil
import com.angcyo.uiview.resources.ResUtil
import com.angcyo.uiview.skin.SkinHelper
import com.angcyo.uiview.utils.UI

/**
 * Created by angcyo on 2017-06-24.
 */
class LauncherUIView : BaseContentUIView() {
    companion object {
        const val ANIM_TIME = 300L
    }

    override fun getTitleBar(): TitleBarPattern? {
        return null
    }

    override fun inflateContentLayout(baseContentLayout: RelativeLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_launcher_layout)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val go: View = v(R.id.go)
        UI.setBackgroundDrawable(go, ResUtil.selector(ResUtil.createDrawable(Color.WHITE, Color.TRANSPARENT, (2 * density()).toInt(), 3 * density()),
                ResUtil.createDrawable(Color.WHITE, SkinHelper.getTranColor(Color.RED, 0x40), (2 * density()).toInt(), 3 * density())))

        click(R.id.go) {
            replaceIView(MainUIView().setEnableClipMode(ClipMode.CLIP_START))
        }
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        AnimUtil.startArgb(mRootView, Color.WHITE, SkinHelper.getSkin().themeColor, 10 * ANIM_TIME)
        topAnim(v(R.id.t1))
        leftAnim(v(R.id.l1), 1)
        rightAnim(v(R.id.r1), 2)
        leftAnim(v(R.id.l2), 3)
        rightAnim(v(R.id.r2), 4)
        leftAnim(v(R.id.l3), 5)
        rightAnim(v(R.id.r3), 6)
        leftAnim(v(R.id.l4), 7)
        rightAnim(v(R.id.r4), 8)

        postDelayed({ bottomAnim(v(R.id.go)) }, 10 * ANIM_TIME)
    }

    private fun leftAnim(view: View, index: Int) {
        x(view, index, (-view.measuredWidth).toFloat())
    }

    private fun rightAnim(view: View, index: Int) {
        x(view, index, (view.measuredWidth).toFloat())
    }

    private fun x(view: View, index: Int, startX: Float) {
        ViewCompat.setTranslationX(view, startX)
        view.visibility = View.VISIBLE
        view.animate()
                .setDuration(ANIM_TIME)
                .translationX(0f)
                .setInterpolator(DecelerateInterpolator())
                .setStartDelay(index * ANIM_TIME)
                .start()
    }

    private fun topAnim(view: View) {
        y(view, (-view.measuredHeight).toFloat())
    }

    private fun bottomAnim(view: View) {
        y(view, (view.measuredHeight).toFloat())
    }

    private fun y(view: View, startY: Float) {
        ViewCompat.setTranslationY(view, startY)
        view.visibility = View.VISIBLE
        view.animate()
                .setDuration(ANIM_TIME)
                .translationY(0f)
                .setInterpolator(DecelerateInterpolator())
                .start()
    }
}
