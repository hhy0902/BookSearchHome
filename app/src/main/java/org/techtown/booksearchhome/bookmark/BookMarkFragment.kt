package org.techtown.booksearchhome.bookmark

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.techtown.booksearchhome.R
import org.techtown.booksearchhome.bookmark.model.BookMarkItem
import org.techtown.booksearchhome.databinding.FragmentBookmarkBinding

class BookMarkFragment : Fragment(R.layout.fragment_bookmark) {

    lateinit var bookMarkAdaptet : BookMarkAdapter
    private val db = Firebase.firestore
    private val auth : FirebaseAuth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentBookMarkBinding = FragmentBookmarkBinding.bind(view)


        bookMarkAdaptet = BookMarkAdapter(bookMarkButtonClick = { item ->
            db.collection("${auth.currentUser?.uid}bookMark").document("${item.author}${item.title}")
                .delete()
                .addOnSuccessListener {
                    Log.d("delete", "DocumentSnapshot successfully deleted!")
                }
            getBookMarkDb()

        })
        fragmentBookMarkBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragmentBookMarkBinding.recyclerView.adapter = bookMarkAdaptet

        getBookMarkDb()

    }

    private fun getBookMarkDb() {
        val bookList = mutableListOf<BookMarkItem>()
        db.collection("${auth.currentUser?.uid}bookMark").get().addOnSuccessListener {
            for(document in it) {
                bookList.add(BookMarkItem(
                    "${document.data.getValue("title")}",
                    "${document.data.getValue("author")}",
                    "${document.data.getValue("date")}",
                    "${document.data.getValue("cover")}",
                    "${document.data.getValue("publisher")}",
                    document.data.getValue("customerReviewRank").toString().toInt(),
                    document.data.getValue("priceStandard").toString().toInt(),
                    document.data.getValue("priceSales").toString().toInt(),
                    "${document.data.getValue("categoryName")}"))

                Log.d("bookMark", "${document.data.getValue("title")}")
                Log.d("bookMark", "${document.data.getValue("author")}")
                Log.d("bookMark", "${document.data.getValue("date")}")

            }
            bookMarkAdaptet.submitList(bookList)
        }
    }
}









































