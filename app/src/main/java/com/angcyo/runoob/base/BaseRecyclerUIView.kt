package com.angcyo.runoob.base

import android.os.Bundle
import com.angcyo.uiview.base.UIRecyclerUIView
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.widget.viewpager.UIViewPager

/**
 * Created by angcyo on 2017-06-24.
 */
abstract class BaseRecyclerUIView<T> : UIRecyclerUIView<String, T, String>() {
    var page = 1

    open fun isLoadInViewPager() = true

    override fun getTitleBar(): TitleBarPattern? = if (isLoadInViewPager()) null else super.getTitleBar()

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        if (isLoadInViewPager()) {

        } else {
            onBaseLoadData()
        }
    }

    override fun onShowInPager(viewPager: UIViewPager?) {
        super.onShowInPager(viewPager)
        if (isLoadInViewPager() && showInPagerCount <= 1) {
            onBaseLoadData()
        }
    }

    override fun onBaseLoadData() {
        super.onBaseLoadData()
        page = 1
        onUILoadData(page)
    }

    override fun onBaseLoadMore() {
        super.onBaseLoadMore()
        page++
        onUILoadData(page)
    }

    /**重载此方法, 加载网络数据*/
    open fun onUILoadData(page: Int) {

    }

    /**数据获取之后调用此方法*/
    open fun onUILoadDataFinish(datas: List<T>) {
        resetLoadUI()
        if (datas.isEmpty()) {
            showEmptyLayout()
        } else {
            showContentLayout()
            if (page <= 1) {
                mExBaseAdapter.resetAllData(datas)
            } else {
                mExBaseAdapter.appendAllData(datas)
            }

            if (haveNextPage(datas, page)) {
                if (!mExBaseAdapter.isEnableLoadMore) {
                    mExBaseAdapter.setLoadMoreEnd()
                }
            } else {
                if (mExBaseAdapter.isEnableLoadMore) {
                    mExBaseAdapter.setNoMore(true)
                }
            }
        }
    }

    /**从写此方法, 判断是否有分页*/
    open fun haveNextPage(datas: List<T>, page: Int): Boolean {
        return page > 1 && datas.isNotEmpty()
    }

    fun resetLoadUI() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshEnd()
        }
        if (mExBaseAdapter != null && mExBaseAdapter.isEnableLoadMore) {
            mExBaseAdapter.setLoadMoreEnd()
        }
    }
}
