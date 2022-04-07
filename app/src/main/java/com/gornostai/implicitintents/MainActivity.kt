package com.gornostai.implicitintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.gornostai.implicitintents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val uri = intent.data
        if (uri != null){
            val uriStr = "URI: ${uri.toString()}"
            binding.textUriMessage.text = uriStr
        }

        binding.openLocationButton.setOnClickListener { openLocation() }

        binding.openWebsiteButton.setOnClickListener { openWebsite() }

        binding.shareTextButton.setOnClickListener { shareText() }

    }

    fun openLocation(){
        val loc = binding.locationEdittext.text.toString()
        val addressUri = Uri.parse("geo:0,0?q=$loc")
        val intent = Intent(Intent.ACTION_VIEW,addressUri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    fun openWebsite(){
        val url = binding.websiteEdittext.text.toString()
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW,webpage)
        if (intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        } else {
            Toast.makeText(this,"Can't handle this!",Toast.LENGTH_SHORT).show()
        }
    }

    fun shareText(){
        val txt = binding.shareEdittext.text.toString()
        ShareCompat.IntentBuilder
            .from(this)
            .setType("text/plain")
            .setChooserTitle(R.string.share_text_with)
            .setText(txt)
            .startChooser();
    }
}