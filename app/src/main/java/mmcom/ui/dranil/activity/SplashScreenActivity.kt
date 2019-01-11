package mmcom.ui.dranil.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mmcom.ui.dranil.R
import mmcom.ui.dranil.db.AppDatabase


class SplashScreenActivity : AppCompatActivity() {

    var isResult = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Observable.fromCallable({
            var db = AppDatabase.getAppDatabase(context = this@SplashScreenActivity)
            var userDao = db?.userDao()


            with(userDao) {
                var loggedInUSer = userDao?.getLogInUser("1")
                if (loggedInUSer != null) {
                    if (loggedInUSer.loginState == "1")
                        isResult = true


                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()


        //logic
        object : CountDownTimer(1200, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (isResult) {

                    startActivity(Intent(this@SplashScreenActivity, DrawerActivity::class.java))
                    finish()
                } else {

                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    finish()
                }
            }

        }.start()
    }
}
