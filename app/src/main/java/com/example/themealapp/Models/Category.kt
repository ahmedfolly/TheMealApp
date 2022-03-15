package com.example.themealapp.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
):Parcelable