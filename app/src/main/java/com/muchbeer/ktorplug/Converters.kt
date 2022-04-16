package com.muchbeer.ktorplug

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muchbeer.ktorplug.data.db.BpapDetailEntity
import com.muchbeer.ktorplug.data.db.CgrievTotalEntity
import com.muchbeer.ktorplug.data.db.DpapAttachEntity
import com.muchbeer.ktorplug.data.db.IMAGESTATUS
import java.io.ByteArrayOutputStream
import java.io.File

class Converters {
    private inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(
        json,
        object : TypeToken<T>() {}.type
    )

    // ***********BGrievanceModel*****************

    @TypeConverter
    fun papDetailModelToJson(listOfPapIdentity: List<BpapDetailEntity>): String {
        val gson = Gson()

        if (listOfPapIdentity.isNullOrEmpty()) return ""

        return gson.toJson(listOfPapIdentity)
    }

    @TypeConverter
    fun papDetailModelToPapModel(jsonPapDetail: String): List<BpapDetailEntity> {
        if (jsonPapDetail.isEmpty()) return emptyList()

        return Gson().fromJson(jsonPapDetail)
    }

    // ***********CGrievanceModel*****************
    @TypeConverter
    fun papGrievanceDataToJson(listOfGrievance: List<CgrievTotalEntity>): String {
        val gson = Gson()

        if (listOfGrievance.isNullOrEmpty()) return ""

        return gson.toJson(listOfGrievance)
    }

    @TypeConverter
    fun papGrievanceDataToGModel(jsonGrievanceDetail: String): List<CgrievTotalEntity> {

        if (jsonGrievanceDetail.isEmpty()) return emptyList()

        return Gson().fromJson(jsonGrievanceDetail)
    }

    // ***********DAttachement*****************
    @TypeConverter
    fun papDAttachmentToJson(listOfAttachment: List<DpapAttachEntity>): String {
        val gson = Gson()

        if (listOfAttachment.isNullOrEmpty()) return ""

        return gson.toJson(listOfAttachment)
    }

    @TypeConverter
    fun papDAttachmentToGModel(jsonAttachment: String): List<DpapAttachEntity> {
        if (jsonAttachment.isEmpty()) return emptyList()

        return Gson().fromJson(jsonAttachment)
    }

    @TypeConverter
    fun fromBitmap(bitmap : Bitmap) : ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray : ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }


    @TypeConverter
    fun fromImageStatus(imageStatus: IMAGESTATUS): String {
        return imageStatus.name
    }

    @TypeConverter
    fun toImageStatus(imageString: String): IMAGESTATUS {
        return IMAGESTATUS.valueOf(imageString)
    }
}