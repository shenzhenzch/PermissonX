package com.dingtuanyun.mylibrary

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment(){
//    private var callback : ((Boolean,List<String>) -> Unit)? = null
private var callback : PermissionCallback? = null

//    fun requestNow(cb: (Boolean,List<String>) -> Unit, vararg permissions: String) {
fun requestNow(cb: PermissionCallback, vararg permissions: String) {

    callback = cb
        @Suppress("DEPRECATION")
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            val deniedList = ArrayList<String>()
            for ((index,result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }

}