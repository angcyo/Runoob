package com.angcyo.runoob.iview

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.RelativeLayout
import com.angcyo.library.utils.L
import com.angcyo.runoob.R
import com.angcyo.runoob.base.BaseRecyclerUIView
import com.angcyo.runoob.bean.HomeBean
import com.angcyo.runoob.bean.HomeSubBean
import com.angcyo.runoob.x5.X5WebUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RFunc
import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RGroupAdapter
import com.angcyo.uiview.recycler.adapter.RGroupData
import com.angcyo.uiview.rsen.RefreshLayout
import com.angcyo.uiview.skin.SkinHelper
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
class HomeUIView : BaseRecyclerUIView<HomeUIView.HomeGroup>() {

    override fun getTitleBar(): TitleBarPattern? {
        return null
    }

    override fun createAdapter(): RExBaseAdapter<String, HomeGroup, String>? {
        return object : RGroupAdapter<String, HomeGroup, String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                return when (viewType) {
                    RGroupAdapter.TYPE_GROUP_HEAD -> R.layout.item_single_text

                    else -> R.layout.item_category_layout
                }
            }
        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
    }

    override fun initRefreshLayout(refreshLayout: RefreshLayout?, baseContentLayout: RelativeLayout?) {
        super.initRefreshLayout(refreshLayout, baseContentLayout)
        refreshLayout?.setNotifyListener(false)
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: RelativeLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.layoutManager = object : GridLayoutManager(mActivity, 2) {

        }.apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val adapter = mExBaseAdapter as RGroupAdapter
                    if (adapter.isInGroup(position)) {
                        return 2
                    }
                    return 1
                }

            }
        }
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

                        L.e("call: onSucceed -> $it \n\n")
                    }

                    mExBaseAdapter.resetAllData(groups)
                }
            }
        })
    }

    inner class HomeGroup : RGroupData<HomeSubBean>() {
        var category: String? = null

        override fun onBindGroupView(holder: RBaseViewHolder, position: Int, indexInGroup: Int) {
            super.onBindGroupView(holder, position, indexInGroup)
            holder.tv(R.id.text_view).text = category
        }

        override fun onBindDataView(holder: RBaseViewHolder, position: Int, indexInData: Int, bean: HomeSubBean?) {
            super.onBindDataView(holder, position, indexInData, bean)
            holder.tv(R.id.text_view).text = bean?.title
            holder.tv(R.id.text_view).setTextColor(SkinHelper.getSkin().themeSubColor)
            holder.tv(R.id.des_view).text = bean?.des

            with(holder.glideImgV(R.id.image_view)) {
                url = bean?.ico
            }

            holder.itemView.setOnClickListener {
                mParentILayout.startIView(X5WebUIView(bean?.url))
            }
        }
    }
}

