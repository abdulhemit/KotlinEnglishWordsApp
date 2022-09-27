package com.example.kotlinenglishwords.view.view.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinenglishwords.R
import com.example.kotlinenglishwords.databinding.ActivityAddWordBinding
import com.example.kotlinenglishwords.view.view.Model.Words
import com.example.kotlinenglishwords.view.view.Room.WordDao
import com.example.kotlinenglishwords.view.view.Room.WordDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWordBinding
    val compositeDisposable = CompositeDisposable()
    private lateinit var db : WordDatabase
    private lateinit var wordDao : WordDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        val view = binding
        setContentView(view.root)
        title = "Add word"



        db = Room.databaseBuilder(applicationContext,WordDatabase::class.java,"Words").build()
        wordDao = db.WordDao()

        binding.btnSave.setOnClickListener {
            save()
        }
        binding.btnDelete.setOnClickListener {
            delete()
        }
        val intent = intent
        val info = intent.getStringExtra("info")
        if (info == "new"){
            binding.btnSave.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.GONE
        }else{
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnSave.visibility = View.GONE
            val wordsFromMainActivity = intent.getSerializableExtra("selectWord") as? Words
            wordsFromMainActivity.let {
                binding.editTextEn.setText(it?.En_word)
                binding.EditTextTr.setText(it?.Tr_word)
                binding.EditTextSentence.setText(it?.En_Sentence)
            }

        }


    }
    private fun save(){
        when{
            TextUtils.isEmpty(binding.editTextEn.text.toString()) -> {
                Toast.makeText(this,"Please write English Word", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.EditTextTr.text.toString()) -> {
                Toast.makeText(this,"Please write Turkish Word", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.EditTextSentence.text.toString()) -> {
                Toast.makeText(this,"Please write your a Sentence", Toast.LENGTH_LONG).show()
            }
            else->{
                val words = Words(binding.editTextEn.text.toString(),binding.EditTextTr.text.toString(),binding.EditTextSentence.text.toString())
                wordDao.insert(words)
                compositeDisposable.addAll(
                    wordDao.insert(words)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleRespose)
                )

            }
        }
    }
    private fun delete(){
        val wordsFromMainActivity = intent.getSerializableExtra("selectWord") as? Words
        wordsFromMainActivity?.let{
            wordDao.delete(it)
           compositeDisposable.addAll(
               wordDao.delete(it)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(this::handleRespose)
           )
        }
    }

    fun handleRespose(){
        val intent = Intent(this@AddWordActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}