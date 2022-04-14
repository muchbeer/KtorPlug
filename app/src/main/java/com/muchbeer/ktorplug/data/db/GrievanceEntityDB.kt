package com.muchbeer.ktorplug.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.serialization.SerialName

@Entity(tableName = "AgrievEntity")
data class AgrievanceEntity(
    @PrimaryKey
    val id : String
)

@Entity(tableName = "BpapEntity")
data class BpapEntity(
    @PrimaryKey
    val id : String
)

@Entity(tableName = "CgrievEntity")
data class CgrievanceEntity(
    @PrimaryKey
    val id : Int,
    val body : String,
    val title : String
) {

}

@Entity(tableName = "DattachEntity")
data class DattachEntity(
    @PrimaryKey
    val id : String
)

class Mapper () {
    fun PostResponseDto.toCgrievEntity(): CgrievanceEntity {
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

