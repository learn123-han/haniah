package com.example.specialminds

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class JSONHelper(private val context: Context) {

    fun loadJSONFromAsset(): String? {
        return try {
            val inputStream = context.assets.open("questions.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun parseJSON(jsonString: String): List<QuizModel> {
        val quizModelList = mutableListOf<QuizModel>()
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val quizObject = jsonArray.getJSONObject(i)
            val id = quizObject.getString("id")
            val title = quizObject.getString("title")
            val subtitle = quizObject.getString("subtitle")
            val time = quizObject.getString("time")

            val questionArray = quizObject.getJSONArray("questionList")
            val questionList = mutableListOf<QuestionModel>()

            for (j in 0 until questionArray.length()) {
                val questionObject = questionArray.getJSONObject(j)
                val question = questionObject.getString("question")
                val optionsArray = questionObject.getJSONArray("options")
                val options = mutableListOf<String>()
                for (k in 0 until optionsArray.length()) {
                    options.add(optionsArray.getString(k))
                }
                val correct = questionObject.getString("correct")
                questionList.add(QuestionModel(question, options, correct))
            }
            quizModelList.add(QuizModel(id, title, subtitle, time, questionList))
        }

        return quizModelList
    }
}
