package mmcom.ui.dranil.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import mmcom.ui.dranil.models.Course

/**
 * Created by Qasim Ahmed on 28/12/2018.
 */
@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: Course)

    @Update
    fun updateCourse(course: Course)

    @Delete
    fun deleteCouse(course: Course)

    @Query("SELECT * FROM course_table")
    fun getAllCourses(): LiveData<List<Course>>

}