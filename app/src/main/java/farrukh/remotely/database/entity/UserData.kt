package farrukh.remotely.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class UserData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var id_user:Int ? = null,
    var name:String? = null,
    var password:String? = null

)