package mmcom.ui.dranil.webservice


import mmcom.ui.dranil.model.CourseModel
import mmcom.ui.dranil.model.CourseResponse
import mmcom.ui.dranil.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {


    @FormUrlEncoded
    @POST("user.php")
    fun doLogin(@Field("email") email: String, @Field("password") password: String, @Field("action") action: String): Call<LoginResponse>


    @FormUrlEncoded
    @POST("user.php/getCourceList")
    fun getCourses(@Field("userId") userId: String, @Field("action") action: String): Call<CourseResponse>

    @FormUrlEncoded
    @POST("lecture.php")
    fun getQuestions(@Field("action") action:String, @Field("userId") userId:String , @Field("lectureId") lectureId:String )



    @FormUrlEncoded
    @POST("lecture.php")
    fun submitLectureQuestion(@Field("action") action:String, @Field("userId") userId:String , @Field("lectureId") lectureId:String, @Field("questionId") questionId:String,@Field("answer") answer:String)

}
