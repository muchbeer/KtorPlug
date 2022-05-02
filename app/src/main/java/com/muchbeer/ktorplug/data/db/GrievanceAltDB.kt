package com.muchbeer.ktorplug.data.db

import android.os.Parcelable
import androidx.room.*
import com.muchbeer.ktorplug.Converters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "AgrievEntityalt")
data class AgrievanceAltEntity(
    @PrimaryKey
    val primary_key : String,
    val user_name: String,
    val valuation_number: String
) : Parcelable

@Parcelize
@Entity(tableName = "CgrievTotalEntityalt")
data class CgrievTotalAltEntity(
    val agreetosign: String,
    @PrimaryKey(autoGenerate = false)
    val full_name : String,
    val a_username : String,
    val a_primary_key : String

) : Parcelable

@Parcelize
@Entity(tableName = "DattachEntityalt")
data class DpapAttachAltEntity(
    @PrimaryKey(autoGenerate = true)
    val d_pap_key : Int = 0,
    val file_name : String,
    val c_fullname: String,
    val url_name : String = "",
    @TypeConverters(Converters::class)
    val image_status : IMAGESTATUS = IMAGESTATUS.NOT_AVAILABLE
) : Parcelable

data class CgrievWithDattachmnt(
    @Embedded val cgrievance: CgrievTotalAltEntity,
    @Relation(
        parentColumn = "full_name",
        entityColumn = "c_fullname",
    )
    val dpap_attach: List<DpapAttachAltEntity>
)

data class AgrievWithCgrieAndDattachList(
    @Embedded val agrievance: AgrievanceAltEntity,
    @Relation(
                entity = CgrievTotalAltEntity::class,
                parentColumn = "primary_key",
                entityColumn = "a_primary_key"
)
val cgriev_general: List<CgrievWithDattachmnt>
)
object MapperAlt  {
}