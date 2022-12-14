package org.techtown.booksearchhome.newbook

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
import org.techtown.booksearchhome.databinding.FragmentNewbookBinding
import org.techtown.booksearchhome.newbook.model.NewBookItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NewBookFragment : Fragment(R.layout.fragment_newbook) {

    lateinit var newBookAdaptet : NewBookAdapter
    var start = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fragmentNewBookBinding = FragmentNewbookBinding.bind(view)

        newBookAdaptet = NewBookAdapter(newBookItemClick = { item ->
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
        fragmentNewBookBinding.newBookRecyclerView.adapter = newBookAdaptet
        fragmentNewBookBinding.newBookRecyclerView.layoutManager = LinearLayoutManager(activity)

        searchBook()

        fragmentNewBookBinding.leftButton.visibility = View.VISIBLE
        fragmentNewBookBinding.rightButton.visibility = View.VISIBLE

        fragmentNewBookBinding.leftButton.setOnClickListener {
            if (start > 1) {
                start = start - 1
                searchBook()
            } else {
                start == 1
                Toast.makeText(activity, "??? ????????? ?????????. ", Toast.LENGTH_SHORT).show()
            }
        }

        fragmentNewBookBinding.rightButton.setOnClickListener {
            if (start < 20) {
                start = start + 1
                searchBook()
            } else {
                start == 20
                Toast.makeText(activity, "????????? ????????? ?????????. ", Toast.LENGTH_SHORT).show()
            }


        }

    }


    private fun searchBook() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
        val retrofitServices = retrofit.create(RetrofitServices::class.java)

        retrofitServices.getNewBookItem(start.toString()).enqueue(object : Callback<NewBookItem> {
            override fun onResponse(call: Call<NewBookItem>, response: Response<NewBookItem>) {
                val item = response.body()
                val itemList = item?.item
                newBookAdaptet.submitList(itemList)
                itemList?.forEach {
                    Log.d("itemList new","$it")
                }
            }
            override fun onFailure(call: Call<NewBookItem>, t: Throwable) {
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







































