package com.example.appdrawer.settings

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
class DataEntity(
    id: Int?,
    dataImage: Int,
    dataTitle: String,
    dataDesc: String,
    category: String,
    dataDetailImage: Int,
    phoneNumber: String,
    rating: Float,
    color: Int
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id = 0
    var dataImage: Int? = dataImage
    var dataTitle: String? = dataTitle
    var dataDesc: String? = dataDesc
    var category: String? = category
    var dataDetailImage: Int? = dataDetailImage
    var phoneNumber: String? = phoneNumber
    var rating: Float? = rating
    var color: Int? = color

    init {
        this.id = id ?: 0
    }

    companion object CREATOR : Parcelable.Creator<DataEntity> {
        override fun createFromParcel(parcel: Parcel): DataEntity {
            return DataEntity(parcel)
        }

        override fun newArray(size: Int): Array<DataEntity?> {
            return arrayOfNulls(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        dataImage = parcel.readInt(),
        dataTitle = parcel.readString() ?: "",
        dataDesc = parcel.readString() ?: "",
        category = parcel.readString() ?: "",
        dataDetailImage = parcel.readInt(),
        phoneNumber = parcel.readString() ?: "",
        rating = parcel.readFloat(),
        color = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(dataImage ?: 0)
        parcel.writeString(dataTitle)
        parcel.writeString(dataDesc)
        parcel.writeString(category)
        parcel.writeInt(dataDetailImage ?: 0)
        parcel.writeString(phoneNumber)
        parcel.writeFloat(rating ?: 0f)
        parcel.writeInt(color ?: 0)
    }

    override fun describeContents(): Int {
        return 0
    }
}


