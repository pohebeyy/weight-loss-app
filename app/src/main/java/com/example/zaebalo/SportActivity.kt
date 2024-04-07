package com.example.zaebalo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class SportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport)

        // Получение случайного упражнения
        fetchRandomExercise()
    }

    private fun fetchRandomExercise() {
        val url = URL("https://wger.de/api/v2/exercise/")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "Token 57e73c10d783b5f017040179fc8af7be29dfb830")

        GlobalScope.launch(Dispatchers.IO) {
            val tvExerciseName: TextView = findViewById(R.id.tvExerciseName)
            val tvExerciseDescription: TextView = findViewById(R.id.textExerciseDescription)
            try {
                val response = StringBuilder()
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()

                // Обработка ответа JSON
                val jsonObject = JSONObject(response.toString())
                val jsonArray = jsonObject.getJSONArray("results")

                // Получение случайного упражнения из результатов
                val randomIndex = Random.nextInt(0, jsonArray.length())
                val exerciseObj = jsonArray.getJSONObject(randomIndex)
                val exerciseName = exerciseObj.getString("name")
                val exerciseDescription = exerciseObj.getString("description")

                // Обновление пользовательского интерфейса в основном потоке
                launch(Dispatchers.Main) {
                    tvExerciseName.text = exerciseName
                    tvExerciseDescription.text = exerciseDescription
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection.disconnect()
            }
        }
    }
}
