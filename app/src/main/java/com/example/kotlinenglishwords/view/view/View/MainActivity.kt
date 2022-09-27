package com.example.kotlinenglishwords.view.view.View


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlinenglishwords.R
import com.example.kotlinenglishwords.databinding.ActivityMainBinding
import com.example.kotlinenglishwords.view.view.Adapter.WordsAdapter
import com.example.kotlinenglishwords.view.view.Model.Words
import com.example.kotlinenglishwords.view.view.Room.WordDao
import com.example.kotlinenglishwords.view.view.Room.WordDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var compositeDisposable = CompositeDisposable()
    private lateinit var wordDao : WordDao
    private lateinit var db : WordDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding
        setContentView(view.root)
        title = "The words"
        db = Room.databaseBuilder(applicationContext,WordDatabase::class.java,"Words").build()
        wordDao = db.WordDao()

        compositeDisposable.addAll(
            wordDao.allget()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRespose)
        )



    }
    fun handleRespose(words: List<Words>){
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter =  WordsAdapter(words)
        binding.recyclerview.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Add_word){
            val intent = Intent(this,AddWordActivity::class.java)
            intent.putExtra("info", "new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}