package com.example.startwarsapp



import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item!!.itemId) {
        R.id.action_favorite-> {
            Log.e("Hi","Girls")
            FragmentUtil.replaceWithBackStack(supportFragmentManager,R.id.container,FavoriteCardsFragment.newInstance())
            true
        }

        else->{
            super.onOptionsItemSelected(item)
        }



    }







}
