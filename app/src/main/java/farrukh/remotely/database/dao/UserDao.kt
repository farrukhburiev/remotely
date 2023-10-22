package farrukh.remotely.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import farrukh.remotely.database.entity.UserData
@Dao

interface UserDao {
    @Query("select * from userdata")
    fun getUser():List<UserData>

    @Insert
    fun addUser(userData: UserData)
}