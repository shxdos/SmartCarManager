package com.shx.smartcarmanager.libs.http

/**
 * Created by 邵鸿轩 on 2017/9/11.
 */
object RequestCenter {
    val login_url = "/login/valid"
    val communityList_url="/area/community/list"
    val enclosure_url="/area/community/enclosure/get"
    val dangersource_url="/area/app/dangersource/list"
    val saveDangerSource_url="/area/dangersource/save"
    val dangersourcetype_url="/basic/danger/type/list"
    /**
     * 登录
     */
    fun doLogin(userName: String, password: String, callBack: HttpCallBack) {
        var params: HashMap<String, String>
        params = HashMap()
        params["username"] = userName
        params["password"] = password
        HttpManager.getInstance().doGet(login_url, params, callBack)
    }

    /**
     * 获取社区列表
     */
    fun getCommunityList(region:String,regionCode:String,name:String,page:Int,rows:Int,callBack: HttpCallBack){
        var params: HashMap<String, String> = HashMap()
        params["region"]=region
        params["regionCode"]=regionCode
        params["name"]=name
        params["page"]=page.toString()
        params["rows"]=rows.toString()
        HttpManager.getInstance().doGet(communityList_url, params, callBack)

    }

    /**
     * 获取危险点
     */
    fun getDangersourceList(communityId: String,page:Int,rows:Int,callBack: HttpCallBack){
        var params: HashMap<String, String> = HashMap()
        params["id"]=communityId
        params["page"]=page.toString()
        params["rows"]=rows.toString()
        HttpManager.getInstance().doGet(dangersource_url, params, callBack)

    }
    /**
     * 新增危险点
     *        map["name"]=
    map["communityId"]=
    map["dangerSourceType"]=
    map["icon"]=
     */
    fun SaveDangersource(cLatitude:String,cLongtide:String,cPoints:String,name:String,communityId:String,dangerSourceType:String,icon:String,callBack: HttpCallBack){
        var params: HashMap<String, String> = HashMap()
        params["cLatitude"]=cLatitude
        params["cLongtide"]=cLongtide
        params["cPoints"]=cPoints
        params["name"]=name
        params["communityId"]=communityId
        params["dangerSourceType"]=dangerSourceType
        params["icon"]=icon
        HttpManager.getInstance().doGet(saveDangerSource_url, params, callBack)

    }
    /**
     * 获取围栏信息
     */
    fun getEnclosure(communityId:String,callBack: HttpCallBack){
        var params: HashMap<String, String> = HashMap()
        params["cid"]=communityId
        HttpManager.getInstance().doGet(enclosure_url, params, callBack)
    }
    /**
     * 获取社区列表
     */
    fun getDangersourceType(page:Int,rows:Int,callBack: HttpCallBack){
        var params: HashMap<String, String> = HashMap()
        params["page"]=page.toString()
        params["rows"]=rows.toString()
        HttpManager.getInstance().doGet(dangersourcetype_url, params, callBack)

    }

}