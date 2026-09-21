package ru.urfu.droidpractice1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.droidpractice1.content.MainActivityScreen
import android.content.pm.ActivityInfo

class MainActivity : ComponentActivity() {
    private companion object {
        private const val TAG = "Lifecycle_MainActivity" //нужна для того, чтобы в окне Logcat можно было отфильтровать сообщения и видеть только логи от этого экрана
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Фиксируем портретный режим
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Отключаем учет датчиков поворота для данного окна
        window.attributes = window.attributes.apply {
            screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        //Log.d означает Debug Log (отладочный лог)
        //Функция отправляет в системный журнал Android строчку: метку TAG ("Lifecycle_MainActivity") и текст сообщения ("onCreate")
        Log.d(TAG, "onCreate")

        // Запускаем Jetpack Compose экран из файла MainActivityScreen.kt
        setContent {
            MainActivityScreen()
        }
    }
    //  Методы жизненного цикла для отслеживания в Logcat
    //Экран становится видим пользователю на дисплее (но пока ещё не готов к активному взаимодействию)
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }
    //Экран полностью вышел на передний план и готов принимать нажатия пользователя
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
    //Экран теряет фокус ( когда нажимаем на карточку второй статьи и поверх первой начинает открываться SecondActivity)
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }
    //MainActivity полностью скрылась с экрана (открылся другой экран во весь дисплей или приложение свернули)
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }
    //Экран полностью уничтожается и выгружается из памяти ( при перевороте устройства или явном закрытии приложения)
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
    //Пользователь возвращается на MainActivity после того, как она была остановлена (закрыв SecondActivity или развернув свернутое приложение)
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}