package com.rado.friends.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "friends")
@Parcelize
data class Friend(
    val name: String,
//    val address: String,
//    val phone: String,
//    val email: String,
//    val birthday: String,
//    val website: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) : Parcelable {


}