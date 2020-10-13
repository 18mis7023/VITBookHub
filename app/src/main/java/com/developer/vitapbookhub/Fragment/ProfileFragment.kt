package com.developer.vitapbookhub.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.developer.vitapbookhub.R
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
lateinit var txtemailprofile:TextView
    lateinit var txtnameprofile:TextView
    lateinit var imgprofile:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        txtemailprofile=view.findViewById(R.id.txtemailprofile)
        txtnameprofile=view.findViewById(R.id.txtnameprofile)
        imgprofile=view.findViewById(R.id.imgprofile)
        var signinAccount= GoogleSignIn.getLastSignedInAccount(activity as Context)
        if(signinAccount!=null)
        {
            txtnameprofile.text=signinAccount.displayName
            txtemailprofile.text=signinAccount.email
            Picasso.get().load(signinAccount.photoUrl).into(imgprofile);

            //imgProfile.setImageResource(signinAccount.photoUrl)

        }

        return view

    }

}
