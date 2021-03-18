package com.rado.friends.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "friends")
@Parcelize
data class Friend(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) : Parcelable {


}