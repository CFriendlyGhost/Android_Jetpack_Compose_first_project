package com.example.appdrawer.settings
import android.os.Parcel
import android.os.Parcelable

data class DataClass(var dataImage:Int, var dataTitle:String, var dataDesc: String, var category: String,
                     var dataDetailImage: Int, var phoneNumber: String, var rating: Float, var color: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dataImage)
        parcel.writeString(dataTitle)
        parcel.writeString(dataDesc)
        parcel.writeString(category)
        parcel.writeInt(dataDetailImage)
        parcel.writeString(phoneNumber)
        parcel.writeFloat(rating)
        parcel.writeInt(color)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<DataClass> {
        override fun createFromParcel(parcel: Parcel): DataClass {
            return DataClass(parcel)
        }
        override fun newArray(size: Int): Array<DataClass?> {
            return arrayOfNulls(size)
        }
    }
}