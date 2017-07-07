package com.angcyo.runoob.iview

import com.angcyo.runoob.R
import com.angcyo.runoob.base.BaseRecyclerUIView
import com.angcyo.runoob.bean.HomeSubBean
import com.angcyo.runoob.x5.X5WebUIView
import com.angcyo.uiview.net.RException
import com.angcyo.uiview.net.RFunc
import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.net.Rx
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import org.jsoup.Jsoup

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/07/07 14:29
 * 修改人员：Robi
 * 修改时间：2017/07/07 14:29
 * 修改备注：
 * Version: 1.0.0
 */
open class BaseListUIView(var baseUrl: String) : BaseRecyclerUIView<HomeSubBean>() {

    override fun createAdapter(): RExBaseAdapter<String, HomeSubBean, String> = object : RExBaseAdapter<String, HomeSubBean, String>(mActivity) {
        override fun getItemLayoutId(viewType: Int) = R.layout.item_note_layout

        override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: HomeSubBean?) {
            super.onBindDataView(holder, posInData, dataBean)
            holder.tv(R.id.text_view).text = dataBean?.title
            holder.tv(R.id.des_view).text = dataBean?.des

            with(holder.glideImgV(R.id.image_view)) {
                checkGif = true
                url = dataBean?.ico
            }

            holder.itemView.setOnClickListener {
                mParentILayout.startIView(X5WebUIView(dataBean?.url))
            }
        }
    }

    override fun haveNextPage(datas: List<HomeSubBean>, page: Int) = true

    override fun onUILoadData(page: Int) {
        super.onUILoadData(page)
        add(Rx.base(object : RFunc<List<HomeSubBean>>() {
            override fun onFuncCall(): List<HomeSubBean> {
                val document = Jsoup.connect("$baseUrl/page/$page").get()
                val elements = document.select("div.archive-list")
                val beans = arrayListOf<HomeSubBean>()

                elements[0].select("div.archive-list-item").map {
                    val bean = HomeSubBean()
                    bean.title = it.select("h2").select("a").text()
                    bean.des = it.select("p").text()
                    bean.url = it.select("a")[0].attr("href")
                    bean.ico = "http:${it.select("a")[0].select("img").attr("src")}"

                    beans.add(bean)

                    //L.e("call: onFuncCall -> $bean")
                }
                return beans
            }
        }, object : RSubscriber<List<HomeSubBean>>() {
            override fun onSucceed(bean: List<HomeSubBean>) {
                super.onSucceed(bean)
                onUILoadDataFinish(bean)
            }

            override fun onEnd(isError: Boolean, isNoNetwork: Boolean, e: RException?) {
                super.onEnd(isError, isNoNetwork, e)
                if (isError) {
                    showNonetLayout { onBaseLoadData() }
                }
            }
        }))
    }

}