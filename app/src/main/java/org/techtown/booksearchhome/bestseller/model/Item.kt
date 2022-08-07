package org.techtown.booksearchhome.bestseller.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("bestDuration")
    val bestDuration: String?,
    @SerializedName("bestRank")
    val bestRank: Int?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("customerReviewRank")
    val customerReviewRank: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("fixedPrice")
    val fixedPrice: Boolean?,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("isbn13")
    val isbn13: String?,
    @SerializedName("itemId")
    val itemId: Int?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("mallType")
    val mallType: String?,
    @SerializedName("mileage")
    val mileage: Int?,
    @SerializedName("priceSales")
    val priceSales: Int?,
    @SerializedName("priceStandard")
    val priceStandard: Int?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("salesPoint")
    val salesPoint: Int?,
    @SerializedName("seriesInfo")
    val seriesInfo: SeriesInfo?,
    @SerializedName("stockStatus")
    val stockStatus: String?,
    @SerializedName("subInfo")
    val subInfo: SubInfo?,
    @SerializedName("title")
    val title: String?
)