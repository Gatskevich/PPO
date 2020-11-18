package com.example.mykurs;

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var wordButton: Button? = null
    var imageButton: Button? = null
    var soloButton: Button? = null
    var scoreText: TextView? = null
    var help: SQLiteManagerMine? = null
    var score = 0
    var imageIndex = 0
    var wordIndex = 0
    var soloIndex = 0
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        help = SQLiteManagerMine(this, null)
        wordButton = findViewById(R.id.wordButton) as Button?
        wordButton!!.setOnClickListener { view ->
            var i = Intent(view.context, PlayActivity::class.java)
            i = pushItems(i, score, wordIndex, imageIndex, soloIndex)
            startActivity(i)
        }
        imageButton = findViewById(R.id.imageButton) as Button?
        imageButton!!.setOnClickListener { view ->
            var i = Intent(view.context, ImageActivity::class.java)
            i = pushItems(i, score, wordIndex, imageIndex, soloIndex)
            startActivity(i)
        }
        soloButton = findViewById(R.id.soloButton) as Button?
        soloButton!!.setOnClickListener { view ->
            var i = Intent(view.context, SoloActivity::class.java)
            i = pushItems(i, score, wordIndex, imageIndex, soloIndex)
            startActivity(i)
        }
        scoreText = findViewById(R.id.scoreText) as TextView?
    }

    fun notAvailable(v: View?) {
        Toast.makeText(getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
    }

     @SuppressLint("SetTextI18n")
     override fun onResume() {
        super.onResume()
        if (getIntent().getExtras() != null) {
            loadItems()
        }
        scoreText!!.text = "Score: " + String.format("%02d", score)
    }

    protected fun pushItems(i: Intent, score: Int, wordIndex: Int, imageIndex: Int, soloIndex: Int): Intent {
        i.putExtra("score", score)
        i.putExtra("wordIndex", wordIndex)
        i.putExtra("imageIndex", imageIndex)
        i.putExtra("soloIndex", soloIndex)
        return i
    }
    protected fun loadItems() {
        score = getIntent().getExtras()!!.getInt("score")
        wordIndex = getIntent().getExtras()!!.getInt("wordIndex")
        imageIndex = getIntent().getExtras()!!.getInt("imageIndex")
        soloIndex = getIntent().getExtras()!!.getInt("soloIndex")
    }
}