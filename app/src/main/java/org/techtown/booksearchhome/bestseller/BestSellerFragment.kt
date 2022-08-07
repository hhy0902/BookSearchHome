package org.techtown.booksearchhome.bestseller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.techtown.booksearchhome.BookDetailActivity
import org.techtown.booksearchhome.BuildConfig
import org.techtown.booksearchhome.R
import org.techtown.booksearchhome.RetrofitServices
import org.techtown.booksearchhome.bestseller.model.BestSellerItem
import org.techtown.booksearchhome.databinding.FragmentBestsellerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BestSellerFragment : Fragment(R.layout.fragment_bestseller){

    lateinit var bestSellerAdaptet : BestSellerAdapter
    var start = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentBestSellerBinding = FragmentBestsellerBinding.bind(view)

        val address = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbhhy090200921001&QueryType=Bestseller&MaxResults=200&start=1&SearchTarget=Book&output=JS&Version=20131101"

        Log.d("BestSellerFragment", "BestSellerFragment")

        bestSellerAdaptet = BestSellerAdapter(bookItemClick = { item ->
            val intent = Intent(activity, BookDetailActivity::class.java)
            intent.putExtra("title",item.title)
            intent.putExtra("imageCover", item.cover)
            intent.putExtra("author",item.author)
            intent.putExtra("date",item.pubDate)
            intent.putExtra("isbn13",item.isbn13)
            intent.putExtra("publisher", item.publisher)
            intent.putExtra("customerReviewRank", item.customerReviewRank)
            intent.putExtra("priceStandard", item.priceStandard)
            intent.putExtra("priceSales", item.priceSales)
            intent.putExtra("categoryName", item.categoryName)
            intent.putExtra("description", item.description)
            intent.putExtra("link", item.link)
            startActivity(intent)
        })

        fragmentBestSellerBinding.bestSellerRecyclerView.adapter = bestSellerAdaptet
        fragmentBestSellerBinding.bestSellerRecyclerView.layoutManager = LinearLayoutManager(activity)

        searchBook()

        fragmentBestSellerBinding.leftButton.visibility = View.VISIBLE
        fragmentBestSellerBinding.rightButton.visibility = View.VISIBLE

        fragmentBestSellerBinding.leftButton.setOnClickListener {
            if (start > 1) {
                start = start - 1
                searchBook()
            } else {
                start == 1
                Toast.makeText(activity, "첫 페이지 입니다. ", Toast.LENGTH_SHORT).show()
            }
        }
        fragmentBestSellerBinding.rightButton.setOnClickListener {
            if (start < 20) {
                start = start + 1
                searchBook()
            } else {
                start == 20
                Toast.makeText(activity, "마지막 페이지 입니다. ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchBook() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
        val retrofitService = retrofit.create(RetrofitServices::class.java)
        retrofitService.getBestSellerItem(start.toString()).enqueue(object :
            Callback<BestSellerItem> {
            override fun onResponse(call: Call<BestSellerItem>, response: Response<BestSellerItem>) {
                if (response.isSuccessful) {
                    val item = response.body()
                    val itemList = item?.item
                    bestSellerAdaptet.submitList(itemList)
                    itemList?.forEach {
                        Log.d("itemList best","$it")
                    }
                }
            }

            override fun onFailure(call: Call<BestSellerItem>, t: Throwable) {
                Log.d("onFailure error", "${t.message}")
            }
        })
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }
}



































