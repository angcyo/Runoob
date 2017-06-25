package com.angcyo.runoob

import android.content.Context
import android.support.v4.content.ContextCompat
import com.angcyo.uiview.skin.BaseSkin

/**
 * Created by angcyo on 2017-06-25.
 */
class MainSkin(var context: Context) : BaseSkin(context) {
    override fun getThemeColor(): Int {
        return ContextCompat.getColor(context, R.color.colorPrimary)
    }

    override fun getThemeSubColor(): Int {
        return themeColor
    }

    override fun getThemeDarkColor(): Int {
        return ContextCompat.getColor(context, R.color.colorPrimaryDark)
    }
}
