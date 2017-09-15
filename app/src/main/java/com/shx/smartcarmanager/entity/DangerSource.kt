package com.shx.smartcarmanager.entity

import java.io.Serializable

/**
 * Created by 邵鸿轩 on 2017/9/15.
 */

class DangerSource : Serializable {

    /**
     * id : 6fa3d6b7-5032-4078-8b6a-6800be8995af
     * name : wwww
     * type : null
     * cLatitude : 40.061325
     * cLongtide : 116.309541
     * cAddress : null
     * cPoints : {"lng":116.309541,"lat":40.061325}
     * createTime : 2017-09-13 13:28:41
     * modifyTime : null
     * createUserId : admin
     * modifyUserId : null
     * icon : null
     */

    var id: String? = null
    var name: String? = null
    var type: Any? = null
    var cLatitude: Double ?= null
    var cLongtide: Double ?=null
    var cAddress: Any? = null
    var cPoints: String? = null
    var createTime: String? = null
    var modifyTime: Any? = null
    var createUserId: String? = null
    var modifyUserId: Any? = null
    var icon: Any? = null

}
