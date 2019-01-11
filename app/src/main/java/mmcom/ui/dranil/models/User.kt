package mmcom.ui.dranil.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Qasim Ahmed on 28/12/2018.
 */
@Entity(tableName = "user_table")
data class User(

        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String,

        @ColumnInfo(name = "email")
        var email: String,

        @ColumnInfo(name = "password")
        var password: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "access_token")
        var accessToken: String,

        @ColumnInfo(name = "loginState")
        var loginState: String
)
