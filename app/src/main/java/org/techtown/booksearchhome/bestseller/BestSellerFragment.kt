package org.techtown.booksearchhome.bestseller

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.techtown.booksearchhome.R

class BestSellerFragment : Fragment(R.layout.fragment_bestseller){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbhhy090200921001&Query=히가시노&MaxResults=200&start=1&SearchTarget=Book&output=JS&Version=20131101"

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}



































