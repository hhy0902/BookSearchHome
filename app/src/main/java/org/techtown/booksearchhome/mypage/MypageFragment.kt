package org.techtown.booksearchhome.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.techtown.booksearchhome.LoginActivity
import org.techtown.booksearchhome.R
import org.techtown.booksearchhome.databinding.FragmentMypageBinding

class MypageFragment : Fragment(R.layout.fragment_mypage) {

    private val auth : FirebaseAuth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMypageBinding = FragmentMypageBinding.bind(view)

        if (auth.currentUser != null) {
            fragmentMypageBinding.emailValue.text = auth.currentUser!!.email
            fragmentMypageBinding.uidValue.text = auth.currentUser!!.uid
        }

        fragmentMypageBinding.logoutButton.setOnClickListener {
            Log.d("logoutButton", "logoutButton")
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            // activity에서 finish 하는거 랑 동일
            activity?.finish()
        }

    }


}

































