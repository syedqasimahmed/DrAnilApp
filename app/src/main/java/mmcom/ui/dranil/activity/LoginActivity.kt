package mmcom.ui.dranil.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import mmcom.ui.dranil.R
import mmcom.ui.dranil.R.id.*
import mmcom.ui.dranil.dao.UserDao
import mmcom.ui.dranil.db.AppDatabase
import mmcom.ui.dranil.model.LoginResponse
import mmcom.ui.dranil.utils.DelayedProgressDialog
import mmcom.ui.dranil.webservice.ApiClient
import mmcom.ui.dranil.webservice.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

  private var db: AppDatabase? = null
  private var userDao: UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        bt_signin.setOnClickListener({
            var emailString = et_email.text.toString()
            var passwordString = et_password.text.toString()
            doLogin(emailString, passwordString)
        })

    }

    fun doLogin(email: String, password: String) {
        var progressBar:DelayedProgressDialog = DelayedProgressDialog()
        progressBar.show(supportFragmentManager,"tag")
        val apiService = ApiClient.getClient(ApiClient.BASE_URL)?.create(ApiInterface::class.java)
        val call = apiService?.doLogin(email, password, "login")
        call?.enqueue(object : Callback<LoginResponse> {

            // test@gmail.com
            // 123456
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressBar.cancel()
                if (response.body() != null) {

                    val loginResponse = response.body()
                    Log.d("LoginActivity", "Login Response: "+loginResponse)
                    if(loginResponse!!.status.equals("success")) {
                        showMessage("Login Successfully")

                         Observable.fromCallable({
                            db = AppDatabase.getAppDatabase(context = this@LoginActivity)
                            userDao = db?.userDao()
                            loginResponse.data.loginState = "1"
                             loginResponse.data.password = password
                            with(userDao){
                                this?.insertUser(loginResponse.data)
                            }
                        }) .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()

                        startActivity(Intent(this@LoginActivity, DrawerActivity::class.java))
                        finish()

                    }else{
                        showMessage("Login Failed")
                    }
                }else {
                    showMessage("Login Failed")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progressBar.cancel()
                Toast.makeText(this@LoginActivity, "Server not responding..", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun showMessage(message:String)
    {
        if(et_email !=null)
        Snackbar.make(et_email , message,Snackbar.LENGTH_LONG).show()
    }
}
