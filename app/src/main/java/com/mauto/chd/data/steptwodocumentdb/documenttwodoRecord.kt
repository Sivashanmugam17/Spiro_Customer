package com.mauto.chd.data.steptwodocumentdb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "steptwodocument")
@Parcelize()
data class documenttwodoRecord(@PrimaryKey(autoGenerate = true) val id: Long?,
                               @ColumnInfo(name = "coloumnid") val coloumnid: String,
                               @ColumnInfo(name = "coloumnname") val coloumnname: String,
                               @ColumnInfo(name = "category") val category: String,
                               @ColumnInfo(name = "is_req") val is_req: String,
                               @ColumnInfo(name = "is_exp") val is_exp: String,
                               @ColumnInfo(name = "has_input") val has_input: String,
                               @ColumnInfo(name = "notes") val notes: String,
                               @ColumnInfo(name = "expirydate") val expirydate: String,
                               @ColumnInfo(name = "account_number") val account_number: String,
                               @ColumnInfo(name = "imagestore") val imagestore: String,
                               @ColumnInfo(name = "imagestore1") val imagestore1: String,
                               @ColumnInfo(name = "uploadstatus") val uploadstatus: String,
                               @ColumnInfo(name = "frontandback") val frontandback: String,
                               @ColumnInfo(name = "submitedfully") val submitedfully: String,
                               @ColumnInfo(name = "document_name") val document_name: String,
                               @ColumnInfo(name = "uuid") val uuid: String) : Parcelable