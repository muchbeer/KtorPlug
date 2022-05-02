package com.muchbeer.ktorplug.data.db

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.muchbeer.ktorplug.Converters
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "AgrievEntity")
data class AgrievanceEntity(
    @PrimaryKey
    val primary_key : String,
    val user_name: String,
    @TypeConverters(Converters::class)
    val papdetails: List<BpapDetailEntity>
) : Parcelable

@Parcelize
@Entity(tableName = "BpapEntity")
data class BpapDetailEntity(
    @PrimaryKey
    val b_pap_id: String,
    val a_username: String,
    @TypeConverters(Converters::class)
    val grievance: List<CgrievTotalEntity>
) : Parcelable

@Entity(tableName = "CgrievEntity")
data class CgrievanceEntity(
    @PrimaryKey
    val id : Int,
    val body : String,
    val title : String
)

@Parcelize
@Entity(tableName = "CgrievTotalEntity")
data class CgrievTotalEntity(
    val agreetosign: String,
    @PrimaryKey(autoGenerate = false)
    val full_name : String,
    val a_username : String,
    @TypeConverters(Converters::class)
    val attachments: List<DpapAttachEntity> = listOf()
) : Parcelable

@Parcelize
@Entity(tableName = "DattachEntity")
data class DpapAttachEntity(
    @PrimaryKey(autoGenerate = true)
    val d_pap_key : Int = 0,
    val file_name : String,
    val c_fullname: String,
    val url_name : String = "",
    @TypeConverters(Converters::class)
    val image_status : IMAGESTATUS = IMAGESTATUS.NOT_AVAILABLE
) : Parcelable

enum class IMAGESTATUS {
    SUCCESSFUL,
    AVAILABLE,
    NOT_AVAILABLE
}

class Mapper  {
  private  fun PostResponseDto.toCgrievEntity(): CgrievanceEntity {
        return CgrievanceEntity(
            id =id,
            title = title,
            body = body,
        )
    }

   fun toCgrievEntityList(initial: List<PostResponseDto>): List<CgrievanceEntity>{
        return initial.map {
            it.toCgrievEntity()  }
    }
}

