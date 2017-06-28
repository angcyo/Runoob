package com.angcyo.runoob.iview

import com.angcyo.uiview.base.UISlidingTabView
import com.angcyo.uiview.view.IView

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
    override fun getPageTitle(position: Int): String {
        return "标题$position"
    }

    override fun getPageCount(): Int {
        return 3
    }

    override fun getPageIView(position: Int): IView {
        return HomeUIView()
    }
}