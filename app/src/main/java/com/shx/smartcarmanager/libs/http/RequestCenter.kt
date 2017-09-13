package com.shx.smartcarmanager.libs.http

/**
 * Created by 邵鸿轩 on 2017/9/11.
 */
object RequestCenter {
    val login_url: String = "/login/valid"
    val communityList_url:String="/area/community/list"
    val enclosure_url:String="/area/community/enclosure/get"
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
     * 获取围栏信息
     */
    fun getEnclosure(communityId:String,callBack: HttpCallBack){
        var params: HashMap<String, String> = HashMap()
        params["cid"]=communityId
        HttpManager.getInstance().doGet(enclosure_url, params, callBack)
    }
}