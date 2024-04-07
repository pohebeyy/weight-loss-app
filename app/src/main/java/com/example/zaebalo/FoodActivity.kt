package com.example.zaebalo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
    }

    override fun onResume() {
        super.onResume()
        // Получение случайного полезного блюда
        fetchRandomHealthyRecipe()
    }

    private fun fetchRandomHealthyRecipe() {
        val url = URL("https://food2fork.ca/api/recipe/search/?query=healthy")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "Token 9c8b06d329136da358c2d00e76946b0111ce2c48")

        GlobalScope.launch(Dispatchers.IO) {
            val tvDishName:TextView = findViewById(R.id.tvDishName)
            val imageView:ImageView = findViewById(R.id.imageView)
            val tvIngredients:TextView = findViewById(R.id.tvIngredients)
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
                val results = jsonObject.getJSONArray("results")

                // Получение случайного блюда из результатов поиска
                val randomIndex = Random.nextInt(0, results.length())
                val recipeObject = results.getJSONObject(randomIndex)
                val title = recipeObject.getString("title")
                val imageUrl = recipeObject.getString("featured_image")
                val ingredients = recipeObject.getJSONArray("ingredients")

                // Обновление пользовательского интерфейса в основном потоке
                launch(Dispatchers.Main) {
                    tvDishName.text = title
                    // Загрузка изображения с помощью Picasso
                    Picasso.get().load(imageUrl).into(imageView)
                    // Формирование списка ингредиентов
                    val ingredientsList = StringBuilder()
                    for (i in 0 until ingredients.length()) {
                        ingredientsList.append("${i + 1}. ${ingredients.getString(i)}\n")
                    }
                    tvIngredients.text = ingredientsList.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection.disconnect()
            }
        }
    }
}
