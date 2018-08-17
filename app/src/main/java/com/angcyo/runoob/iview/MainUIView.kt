package com.angcyo.runoob.iview

import com.angcyo.runoob.R
import com.angcyo.runoob.x5.X5WebUIView
import com.angcyo.uiview.base.UISlidingTabView
import com.angcyo.uiview.model.TitleBarItem
import com.angcyo.uiview.model.TitleBarPattern

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/28 11:57
 * 修改人员：Robi
 * 修改时间：2017/06/28 11:57
 * 修改备注：
 * Version: 1.0.0
 */
class MainUIView : UISlidingTabView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().addRightItem(TitleBarItem("Jquery") {
            mParentILayout.startIView(X5WebUIView("http://jquery.cuishifeng.cn/"))
        }.apply { rightMargin = getDimensionPixelOffset(R.dimen.base_xhdpi) })
    }

    override fun getPageTitle(position: Int) =
            when (position) {
                0 -> "首页"
                1 -> "菜鸟笔记"
                2 -> "Android"
                3 -> "互联网"
                4 -> "杂乱无章"
                5 -> "科技资讯"
                6 -> "程序人生"
                7 -> "程序员笑话"
                8 -> "编程技术"
                else -> "$position"
            }


    override fun getPageCount(): Int {
        return 9
    }

    override fun getPageIView(position: Int) =
            when (position) {
                0 -> HomeUIView()
                1 -> BaseListUIView("http://www.runoob.com/w3cnote")
                2 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/android")
                3 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/net-2")
                4 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/disorganized")
                5 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/tech")
                6 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/coderlife")
                7 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/joke")
                8 -> BaseListUIView("http://www.runoob.com/w3cnote_genre/code")
                else -> HomeUIView()
            }
}