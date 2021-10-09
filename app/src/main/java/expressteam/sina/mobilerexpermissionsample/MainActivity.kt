package expressteam.sina.mobilerexpermissionsample



import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import expressteam.sina.rexperm.RequestManager
import expressteam.sina.rexperm.SimplePermissionCallback

class MainActivity : AppCompatActivity() {
    val manager = RequestManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager.inLoop = true
        manager.addCallBack(object :SimplePermissionCallback{
            override fun OnSucess(granted: List<String>) {

            }

            override fun OnFaild(denied: List<String>) {

            }

        })

        findViewById<Button>(R.id.btn_request).setOnClickListener {

        manager.request(this)

        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        manager.registerCallback(requestCode, permissions, grantResults)
    }
}