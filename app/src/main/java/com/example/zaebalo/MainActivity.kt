package com.example.zaebalo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFood: Button = findViewById(R.id.btn_food)
        btnFood.setOnClickListener {
            // Переход к FoodActivity и передача recipe_id
            val intent = Intent(this, FoodActivity::class.java)
            intent.putExtra("recipe_id", 583) // Пример recipe_id. Можете изменить на другое значение
            startActivity(intent)
        }

        val btnSport: Button = findViewById(R.id.btn_sport)
        btnSport.setOnClickListener {
            val intent = Intent(this, SportActivity::class.java)
            startActivity(intent)
        }
        val btnBot:Button= findViewById(R.id.btn_bot)
        btnBot.setOnClickListener {
            val intent = Intent(this,botActivity::class.java)
            startActivity(intent)
        }
    }
}
