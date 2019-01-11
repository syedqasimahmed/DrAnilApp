package mmcom.ui.dranil.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_course_detail.*
import mmcom.ui.dranil.R
import mmcom.ui.dranil.backgroundtask.DownloadPDFTask
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class CourseDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }

    var downloadURL: String? = null
    var downloadFileName: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_back.setOnClickListener {
            replaceFragment(ViewAllCoursesFragment())
        }

        showPDF()
        tv_take_exam.setOnClickListener {
            // todo Open Take Exam Fragment here
        }


    }

    fun showPDF() {
        if (arguments != null) {
            downloadURL = arguments?.getString("url")
            downloadFileName = arguments?.getString("filename")
        }

        var pdfTask = DownloadPDFTask(activity, downloadURL, downloadFileName)
        pdfTask.setOnUpdateListner(object : DownloadPDFTask.OnUpdateListner {
            override fun onFileAlreadyExists(file: File?) {
                donut_progress.visibility = View.GONE
                pdfView.visibility = View.VISIBLE
                pdfView.fromFile(file)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(null)
                        .enableAntialiasing(true)
                        .spacing(0)
                        .load();
            }

            override fun onError(filename: String?) {
                donut_progress.visibility = View.GONE

            }

            override fun onUpdate(progress: Int) {
                donut_progress.setDonut_progress(progress.toString())
            }

            override fun onDownloadSuccsesFull(file: File?) {
                donut_progress.visibility = View.GONE
                pdfView.visibility = View.VISIBLE
                pdfView.fromFile(file)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(null)
                        .enableAntialiasing(true)
                        .spacing(0)
                        .load();

            }

        })
        pdfTask.execute()
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

}// Required empty public constructor
