package com.developer.vitapbookhub.Activity.Books

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.vitapbookhub.R
import kotlinx.android.synthetic.main.activity_download_view.*

class DownloadViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_view)
             var url="http://facultymember.iaukhsh.ac.ir/images/Uploaded_files/[Strang_G.]_Linear_algebra_and_its_applications(4)[5881001].PDF"
            PDFView.fromUri(Uri.parse(url))
                .load()
           /* webview.webViewClient=MyWEbViewClient()
            webview.loadUrl(url)*/
    }
    /*class MyWEbViewClient:WebViewClient(){
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }
    }*/
}
