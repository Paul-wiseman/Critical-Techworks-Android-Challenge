package com.example.criticaltechworkschallenge.data.database

import androidx.room.TypeConverter
import com.example.criticaltechworkschallenge.entity.Source
import com.google.gson.Gson

class NewsDatabaseTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun sourceFromString(value: String?): Source? {
        return gson.fromJson(value, Source::class.java)
    }

    @TypeConverter
    fun sourceToString(source: Source?): String? {
        return gson.toJson(source)
    }
}