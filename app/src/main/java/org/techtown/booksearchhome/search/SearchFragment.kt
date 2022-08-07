package org.techtown.booksearchhome.search

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
import org.techtown.booksearchhome.databinding.FragmentSearchBinding
import org.techtown.booksearchhome.search.model.SearchBookItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var searchBookAdaptet : SearchBookAdapter

    var start = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fragmentSearchBinding = FragmentSearchBinding.bind(view)

        searchBookAdaptet = SearchBookAdapter(searchBookClick = { item ->
            Toast.makeText(activity, "${item.title}", Toast.LENGTH_SHORT).show()
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
        fragmentSearchBinding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)
        fragmentSearchBinding.searchRecyclerView.adapter = searchBookAdaptet

        fragmentSearchBinding.searchButton.setOnClickListener {
            val searchText = fragmentSearchBinding.searchEditText.text.toString()
            searchBook(searchText)
            fragmentSearchBinding.rightButton.visibility = View.VISIBLE
            fragmentSearchBinding.leftButton.visibility = View.VISIBLE
        }

        fragmentSearchBinding.leftButton.setOnClickListener {
            val searchText = fragmentSearchBinding.searchEditText.text.toString()
            if (start > 1) {
                start = start - 1
                searchBook(searchText)
            } else {
                start == 1
                Toast.makeText(activity, "첫 페이지 입니다. ", Toast.LENGTH_SHORT).show()
            }
        }
        fragmentSearchBinding.rightButton.setOnClickListener {
            val searchText = fragmentSearchBinding.searchEditText.text.toString()
            if (start < 4) {
                start = start + 1
                searchBook(searchText)
            } else {
                start == 4
                Toast.makeText(activity, "마지막 페이지 입니다. ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun searchBook(searchText : String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
        val retrofitServices = retrofit.create(RetrofitServices::class.java)
        retrofitServices.getSearchBookItem(searchText, start.toString()).enqueue(object :
            Callback<SearchBookItem> {
            override fun onResponse(call: Call<SearchBookItem>, response: Response<SearchBookItem>) {
                val item = response.body()
                val itemList = item?.item

                searchBookAdaptet.submitList(itemList)

            }

            override fun onFailure(call: Call<SearchBookItem>, t: Throwable) {
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








































