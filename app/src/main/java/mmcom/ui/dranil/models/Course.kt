package mmcom.ui.dranil.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Qasim Ahmed on 09/01/2019.
 */
@Entity(tableName = "course_table")
data class Course(

        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String,

        @ColumnInfo(name = "lectureId")
        var lectureId: String,

        @ColumnInfo(name = "status")
        var status: String,

        @ColumnInfo(name = "completed")
        var completed: String,

        @ColumnInfo(name = "lectureName")
        var lectureName: String,

        @ColumnInfo(name = "lectureDescription")
        var lectureDescription: String,

        @ColumnInfo(name = "lectureFile")
        var lectureFile: String,

        @ColumnInfo(name = "lectureStatus")
        var lectureStatus: String

)
