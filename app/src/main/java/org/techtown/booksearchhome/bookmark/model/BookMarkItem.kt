package org.techtown.booksearchhome.bookmark.model

data class BookMarkItem(
    val title : String,
    val author : String,
    val date : String,
    val cover : String,
    val publisher : String,
    val customerReviewRank : Int,
    val priceStandard : Int,
    val priceSales : Int,
    val categoryName : String
)
