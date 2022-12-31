package com.gulehri.idownloader

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.gulehri.idownloader.config.MyApp
import com.gulehri.idownloader.config.Utils
import com.gulehri.idownloader.config.Utils.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var button: AppCompatButton
    private lateinit var field: AppCompatEditText
    private var url: String = ""
    private var output: MutableLiveData<String> = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        button = findViewById(R.id.btn)
        field = findViewById(R.id.edField)

        askPerm()

        button.setOnClickListener {
            checkPerms()
            this.hideKeyboard()
            button.isEnabled = false
        }

        output.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                button.isEnabled = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        button.isEnabled = true

    }

    private fun getInstaPost(url: String) {
        output.postValue("Processing Data")

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val post = MyApp.instagramApi.getInstaPost(url.trim())
                if (post.isSuccessful && post.body() != null) {
                    val media = post.body()!!.media
                    startDownload(media)
                } else output.postValue("Download Failed")

            }
        }catch (e:Exception){output.postValue("Something went wrong")}

    }



    private fun getFacebookPost(url: String) {
        output.postValue("Processing Data")
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val post = MyApp.facebookApi.getFacebookPost(url.trim())
                if (post.isSuccessful && post.body() != null) {

                    if (post.body()?.links?.highQuality != null) {
                        startDownload(post.body()!!.links.highQuality)
                    } else output.postValue("Failed to download")
                } else output.postValue("Something went wrong")
            }
        }catch (e:Exception){output.postValue("Something went wrong")}

    }

    private fun checkPerms() {

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            url = field.text.toString()
            if (url.isNotEmpty()) {
                when {
                    url.contains("Instagram", true) -> getInstaPost(url)
                    url.contains("facebook", true) || url.contains("fb", true) -> getFacebookPost(
                        url)
                }

            } else output.postValue("Enter Url")
        }else askPerm()
    }

    private fun startDownload(
        url: String,
        title: String = Random.nextLong(99999999L).toString(),
    ) {

        output.postValue("Download Start")

        val fileName = "$title.mp4"
        val request = DownloadManager.Request(Uri.parse(url.trim()))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }


    private fun askPerm() = ActivityCompat.requestPermissions(this,
        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
        34)

}