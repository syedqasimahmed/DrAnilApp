package mmcom.ui.dranil.application

import android.app.Application
import android.util.Log
import mmcom.ui.dranil.utils.TypefaceUtil

/**
 * Created by Qasim Ahmed on 25/12/2018.
 */

class AppControler : Application() {

    override fun onCreate() {
        super.onCreate()
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/arial_bold.ttf")
        Log.d("AppController","In app controller onCreate()")
    }
}
