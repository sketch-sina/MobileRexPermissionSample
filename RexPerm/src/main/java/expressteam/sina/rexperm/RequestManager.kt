package expressteam.sina.rexperm

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.jaredrummler.apkparser.ApkParser
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class RequestManager {

    private lateinit var context: Activity
    private lateinit var callback: SimplePermissionCallback
    private lateinit var jsonArray: JSONArray
    private val tag = "REQUEST_MANAGER"
    var inLoop = false

    fun request(context: Activity) {
        this.context = context

        val apkPath = context.packageCodePath
        val parser = ApkParser.create(apkPath)
        val json = XmlToJson.Builder(parser.manifestXml).build()
        // Log.e(tag,json.toJson().toString())

        val list = json.toJson()!!.getJSONObject("manifest").getJSONArray("uses-permission")
        jsonArray = list
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(getDenieds(list).toTypedArray(), 810)
        }


    }



    fun registerCallback(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 810) {

            if (this::callback.isInitialized) {

                if (getDenieds(jsonArray).isEmpty()) {
                    callback.OnSucess(permissions.toList())
                } else {

                    if (inLoop){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            context.requestPermissions(getDenieds(jsonArray).toTypedArray(), 810)
                        }
                    }else{
                        callback.OnFaild(permissions.toList())
                    }

                }


            }


        }
    }



  private  fun getDenieds(list: JSONArray): List<String> {


        val array = mutableListOf<String>()

        for (i in 0 until list.length()) {
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    context.checkSelfPermission(
                            list.getJSONObject(i).getString("android:name")
                        ) != PackageManager.PERMISSION_GRANTED
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            ) {

                array.add(list.getJSONObject(i).getString("android:name"))
            }
        }


        return array
    }

    fun addCallBack(callback: SimplePermissionCallback) {
        this.callback = callback


    }


}

