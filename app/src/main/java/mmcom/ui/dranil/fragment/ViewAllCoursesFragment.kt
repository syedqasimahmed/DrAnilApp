package mmcom.ui.dranil.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mmcom.ui.dranil.Adapter.AdapterCourse
import mmcom.ui.dranil.R
import mmcom.ui.dranil.model.CourseModel
import mmcom.ui.dranil.model.CourseResponse
import mmcom.ui.dranil.webservice.ApiClient
import mmcom.ui.dranil.webservice.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewAllCoursesFragment : Fragment() {


    var adapter: AdapterCourse? = null
    var rv: RecyclerView? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_viewallcourses, container, false)
        rv = rootView.findViewById(R.id.rvcourse);
        get_data();
        return rootView

    }



    override fun onResume() {
        super.onResume()
     }


    fun get_data() {

        val apiService = ApiClient.getClient(ApiClient.BASE_URL)?.create(ApiInterface::class.java)
        val call = apiService?.getCourses("2", "getCourceList")
        call?.enqueue(object : Callback<CourseResponse> {

            override fun onResponse(call: Call<CourseResponse>, response: Response<CourseResponse>) {
                if (response.body() != null) {

                    val coursesResponse = response.body()
                    Log.d("coursesResponse", "courses Response: " + coursesResponse)
                    if (coursesResponse!!.status.equals("success")) {

                        if (coursesResponse.data != null && coursesResponse.data.size > 0) {
                            adapter = AdapterCourse(activity, coursesResponse.data, object : AdapterCourse.MyAdapterListener {
                                override fun onStartLectureListner(v: View?, position: Int) {

                                    val courseDetailFragment = CourseDetailFragment()
                                    var args = Bundle()
                                    args.putString("url",coursesResponse.data[position].lectureFile)
                                    args.putString("filename",coursesResponse.data[position].lectureId)
                                    courseDetailFragment.arguments = args
                                    replaceFragment(courseDetailFragment)
                                }

                                override fun onSwicthClickListner(v: View?, position: Int, isAllowed: Boolean) {

                                    if (isAllowed)
                                        Toast.makeText(activity, "This course is accessible", Toast.LENGTH_LONG).show()
                                    else
                                        Toast.makeText(activity, "This course is inaccessible", Toast.LENGTH_LONG).show()


                                }

                            })

                            rv?.setAdapter(adapter)
                            rv?.setHasFixedSize(true)
                            rv?.setItemViewCacheSize(20)
                            rv?.setDrawingCacheEnabled(true)
                            rv?.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
                            val llm = LinearLayoutManager(activity)
                            rv!!.setLayoutManager(llm)

                        } else {
                            showMessage("courses not available")
                        }

                    } else {
                        showMessage("Server not responding")
                    }
                } else {
                    showMessage("Server not responding")
                }
            }

            override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                showMessage("Remote server not responding")

            }
        })
    }

    fun showMessage(message: String) {
        if (et_email != null)
            Snackbar.make(et_email, message, Snackbar.LENGTH_LONG).show()
    }


    fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name


        val manager = activity?.supportFragmentManager
        var fragmentPopped = false
        try {

            fragmentPopped = manager?.popBackStackImmediate(backStateName, 0)!!

        } catch (ignored: IllegalStateException) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }

        if (!fragmentPopped && manager?.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            val ft = manager?.beginTransaction()
            ft?.replace(R.id.fragment_container, fragment, backStateName)
            ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft?.addToBackStack(backStateName)
            ft?.commitAllowingStateLoss()
        }

    }

}
