package com.dzakyadlh.wikirecipe.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class SavedRecipe(
    @field:PrimaryKey(autoGenerate = false)
    var id: String,

    var name: String,
    var description: String,
    var photoUrl: String,
) : Parcelable