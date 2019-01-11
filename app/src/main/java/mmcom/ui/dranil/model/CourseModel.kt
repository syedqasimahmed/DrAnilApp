package mmcom.ui.dranil.model

/**
 * Created by Qasim Ahmed on 06/01/2019.
 */
data class CourseModel
(var id:String ,
 var lectureId:String ,
 var status:String ,
 var completed:String ,
 var lectureName:String ,
 var lectureDescription:String ,
 var lectureFile:String ,
 var lectureStatus:String)

data class CourseResponse(
        var data:ArrayList<CourseModel>,
        var status: String ,
        var message:String
)