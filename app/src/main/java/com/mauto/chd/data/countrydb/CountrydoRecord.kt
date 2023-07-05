package com.mauto.chd.data.countrydb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "countryflag")
@Parcelize()
data class CountrydoRecord(@PrimaryKey(autoGenerate = true) val id: Long?,
                           @ColumnInfo(name = "code") val title: String,
                           @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val imagestore: ByteArray,
                           @ColumnInfo(name = "countryname") val content: String) : Parcelable