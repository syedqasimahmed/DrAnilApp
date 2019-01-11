package mmcom.ui.dranil.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import mmcom.ui.dranil.dao.UserDao
import mmcom.ui.dranil.models.User


/**
 * Created by Qasim Ahmed on 28/12/2018.
 */

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                   INSTANCE = Room.databaseBuilder(context.applicationContext,
                             AppDatabase::class.java, "myaDB")
                           .allowMainThreadQueries()
                           .build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}
