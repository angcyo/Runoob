package com.angcyo.runoob.bean

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/28 16:29
 * 修改人员：Robi
 * 修改时间：2017/06/28 16:29
 * 修改备注：
 * Version: 1.0.0
 */
data class HomeBean(
        var category: String? = null,
        var subBean: ArrayList<HomeSubBean>? = null
) {
    override fun toString(): String {
        return "category:$category ${subBean.toString()}"
    }
}

data class HomeSubBean(var title: String? = null,
                       var url: String? = null,
                       var ico: String? = null,
                       var des: String? = null)