package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    private lateinit var ivAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvFollower: TextView
    private lateinit var tvFollowing: TextView
    private lateinit var tvRepository: TextView
    private lateinit var tvCompany: TextView
    private lateinit var tvLocation: TextView
    private lateinit var btnShare: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivAvatar = findViewById(R.id.iv_avatar)
        tvName = findViewById(R.id.tv_name)
        tvUsername = findViewById(R.id.tv_username)
        tvFollower = findViewById(R.id.tv_follower)
        tvFollowing = findViewById(R.id.tv_following)
        tvRepository = findViewById(R.id.tv_repository)
        tvCompany = findViewById(R.id.tv_company)
        tvLocation = findViewById(R.id.tv_location)
        btnShare = findViewById(R.id.btn_share)

        val data = intent.getParcelableExtra<User>("DATA")

        data?.avatar?.let { ivAvatar.setImageResource(it.toInt()) }
        tvName.text = data?.name.toString()
        tvUsername.text = "@" + data?.username.toString()
        tvFollower.text = "Follower : " + data?.follower.toString()
        tvFollowing.text = "Following : " + data?.following.toString()
        tvRepository.text = "Repository : " + data?.repository.toString()
        tvCompany.text = "Company \n" + data?.company.toString()
        tvLocation.text = "Location \n" + data?.location.toString()

        btnShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, data?.name.toString() + " - @" + data?.username.toString())
            intent.type =  "text/plain"
            startActivity(Intent.createChooser(intent, "Share the user"))
        }
    }
}