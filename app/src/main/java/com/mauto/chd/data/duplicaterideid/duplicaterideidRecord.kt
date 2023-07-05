package com.mauto.chd.data.duplicaterideid

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "duplicaterideidreqused")
@Parcelize()
data class duplicaterideidRecord(@PrimaryKey(autoGenerate = true) val id: Long?,
                                 @ColumnInfo(name = "rideid") val rideid: String,
                                 @ColumnInfo(name = "timestamp") val timestamp: String) : Parcelable