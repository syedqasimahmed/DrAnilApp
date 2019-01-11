package mmcom.ui.dranil.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import mmcom.ui.dranil.models.User

/**
 * Created by Qasim Ahmed on 28/12/2018.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user:User)

    @Update
    fun updateUser(user:User)

    @Delete
    fun deleteUser(user:User)



    @Query("SELECT * FROM user_table WHERE  loginState LIKE :loginState  LIMIT 1 ")
    fun  getLogInUser(  loginState:String):User

}