package com.developer.vitapbookhub.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.developer.vitapbookhub.Activity.LoginActivity
import com.developer.vitapbookhub.R
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass.
 */
class LogoutFragment : Fragment() {

    private lateinit var googleSignInClient:GoogleSignInClient
    lateinit var txtlogoutemail: TextView
    lateinit var txtlogoutname: TextView
    lateinit var imgProfile:ImageView
    lateinit var btnlogout:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_logout, container, false)
        val testName= arguments?.getString("email")
       // System.out.println(testName)
        txtlogoutname=view.findViewById(R.id.txtlogoutname)
        txtlogoutemail=view.findViewById(R.id.txtlogoutemail)
        btnlogout=view.findViewById(R.id.btnlogout)
        imgProfile=view.findViewById(R.id.imgprofile)
        txtlogoutemail.text=testName
        var signinAccount=GoogleSignIn.getLastSignedInAccount(activity as Context)
        if(signinAccount!=null)
        {
            txtlogoutname.text=signinAccount.displayName
            txtlogoutemail.text=signinAccount.email
            Picasso.get().load(signinAccount.photoUrl).into(imgProfile);
            //imgProfile.setImageResource(signinAccount.photoUrl)

        }
        btnlogout.setOnClickListener {
            val gso =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

            val googleSignInClient = GoogleSignIn.getClient(context!!, gso)
            googleSignInClient.signOut()
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(activity as Context,LoginActivity::class.java)
            /*intent.putExtra("logout",false)
            intent.putExtra("idLogged","0")*/
            startActivity(intent)

        }
        return view
    }
   /* fun onClick(v: View) {
        when (v.id) {
            R.id.btnlogout -> signOut()
        }
    }
    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this, object : OnCompleteListener<Void?> {
                fun onComplete(task: Task<Void?>) {
                    // ...
                }
            })
    }*/

}
