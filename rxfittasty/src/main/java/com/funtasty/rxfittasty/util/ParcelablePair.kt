package com.funtasty.rxfittasty.util

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.fitness.data.DataType

data class ParcelablePair(val dataType: DataType, val fitnessOptionsAccess: Int) : Parcelable {
	constructor(source: Parcel) : this(
			DataType.CREATOR.createFromParcel(source),
			source.readInt()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		dataType.writeToParcel(dest, flags)
		writeInt(fitnessOptionsAccess)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<ParcelablePair> = object : Parcelable.Creator<ParcelablePair> {
			override fun createFromParcel(source: Parcel): ParcelablePair = ParcelablePair(source)
			override fun newArray(size: Int): Array<ParcelablePair?> = arrayOfNulls(size)
		}
	}
}
