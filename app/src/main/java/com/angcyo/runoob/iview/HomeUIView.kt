package com.angcyo.runoob.iview

import com.angcyo.runoob.base.BaseRecyclerUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/28 12:00
 * 修改人员：Robi
 * 修改时间：2017/06/28 12:00
 * 修改备注：
 * Version: 1.0.0
 */
class HomeUIView : BaseRecyclerUIView<String>() {

    override fun getTitleBar(): TitleBarPattern? {
        return null
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String>? {
        return null
    }
}