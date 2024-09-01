package com.example.specialminds

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.specialminds.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var quizModelList: MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromLocalJSON()
    }

    private fun setupRecyclerView() {
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromLocalJSON() {
        binding.progressBar.visibility = View.VISIBLE

        val jsonHelper = JSONHelper(this)
        val jsonString = jsonHelper.loadJSONFromAsset()

        jsonString?.let {
            quizModelList.clear()
            quizModelList.addAll(jsonHelper.parseJSON(it))
            setupRecyclerView()
        }

        binding.progressBar.visibility = View.GONE
    }
}

