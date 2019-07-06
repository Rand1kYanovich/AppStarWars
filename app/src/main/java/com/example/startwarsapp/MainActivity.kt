package com.example.startwarsapp



import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.startwarsapp.util.FragmentUtil


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val allCardsFragment = AllCardsFragment()
        FragmentUtil.replace(supportFragmentManager,R.id.container,allCardsFragment)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }







}
