package com.funtasty.fittester.rxFitTasty.util

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.fitness.data.DataType

data class ParcalablePair(val first: DataType, val second: Int) : Parcelable {
	constructor(source: Parcel) : this(
			DataType.CREATOR.createFromParcel(source),
			source.readInt()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		first.writeToParcel(dest, flags)
		writeInt(second)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<ParcalablePair> = object : Parcelable.Creator<ParcalablePair> {
			override fun createFromParcel(source: Parcel): ParcalablePair = ParcalablePair(source)
			override fun newArray(size: Int): Array<ParcalablePair?> = arrayOfNulls(size)
		}
	}
}
