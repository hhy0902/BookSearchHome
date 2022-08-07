package org.techtown.booksearchhome.newbook.model

import com.google.gson.annotations.SerializedName

data class NewBookItem(
    @SerializedName("item")
    val item: List<Item>?,
    @SerializedName("itemsPerPage")
    val itemsPerPage: Int?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("query")
    val query: String?,
    @SerializedName("searchCategoryId")
    val searchCategoryId: Int?,
    @SerializedName("searchCategoryName")
    val searchCategoryName: String?,
    @SerializedName("startIndex")
    val startIndex: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("version")
    val version: String?
)