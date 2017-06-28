package com.angcyo.runoob.iview

import android.os.Bundle
import com.angcyo.runoob.base.BaseRecyclerUIView
import com.angcyo.runoob.bean.HomeBean
import com.angcyo.runoob.bean.HomeSubBean
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RFunc
import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RGroupAdapter
import com.angcyo.uiview.recycler.adapter.RGroupData
import org.jsoup.Jsoup

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
class HomeUIView : BaseRecyclerUIView<HomeGroup>() {

    override fun getTitleBar(): TitleBarPattern? {
        return null
    }

    override fun createAdapter(): RExBaseAdapter<String, HomeGroup, String>? {
        return object : RGroupAdapter<String, HomeGroup, String>(mActivity) {

        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        Rx.base(object : RFunc<List<HomeBean>>() {
            override fun call(t: String?): List<HomeBean> {
                val document = Jsoup.connect("http://www.runoob.com/").get()
                val elements = document.select("div.codelist-desktop")
                val beans = arrayListOf<HomeBean>()

                elements.map {
                    val bean = HomeBean()
                    bean.category = it.select("h2")[0].text()
                    bean.subBean = arrayListOf<HomeSubBean>()

                    it.select("a").map {
                        val subBean = HomeSubBean()
                        subBean.title = it.select("h4")[0].text()
                        subBean.des = it.select("strong")[0].text()
                        subBean.url = "http:${it.attr("href")}"
                        subBean.ico = "http:${it.select("img")[0].attr("src")}"

                        bean.subBean!!.add(subBean)
                    }
                    beans.add(bean)
                }
                return beans
            }
        }, object : RSubscriber<List<HomeBean>>() {
            override fun onSucceed(bean: List<HomeBean>) {
                super.onSucceed(bean)
                if (bean.isEmpty()) {
                    showEmptyLayout()
                } else {
                    showContentLayout()

                    val groups = arrayListOf<HomeGroup>()
                    bean.map {
                        val group = HomeGroup()
                        group.category = it.category
                        group.resetDatas(it.subBean)
                        groups.add(group)
                    }

                    mExBaseAdapter.resetAllData(groups)
                }
            }
        })
    }
}

class HomeGroup : RGroupData<HomeSubBean>() {
    var category: String? = null
}