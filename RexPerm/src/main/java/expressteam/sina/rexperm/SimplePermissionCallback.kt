package expressteam.sina.rexperm

interface SimplePermissionCallback {

    fun OnSucess(granted: List<String>)

    fun OnFaild(denied: List<String>)

}