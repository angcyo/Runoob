package com.angcyo.runoob

import com.angcyo.uiview.RApplication
import com.angcyo.uiview.Root
import com.angcyo.uiview.skin.SkinHelper

/**
 * Created by angcyo on 2017-06-24.
 */
class RunoobApp : RApplication() {
    override fun onInit() {
        super.onInit()
        Root.APP_FOLDER = "Runoob"

        SkinHelper.init(MainSkin(this))
    }
}
