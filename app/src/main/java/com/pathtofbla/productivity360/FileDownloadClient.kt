package com.pathtofbla.productivity360

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

public interface FileDownloadClient {
    @GET("/feeds/calendars/user_Py2297IbInuASlj5idpgDugS7oyJneAOvC22l1Kz.ics")
    fun downloadFile(): Call<ResponseBody>
}