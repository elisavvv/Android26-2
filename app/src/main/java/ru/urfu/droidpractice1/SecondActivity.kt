package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding
import android.util.Log
import android.content.pm.ActivityInfo

class SecondActivity : AppCompatActivity() { //класс для кэкранов в Android, разметка которых делается через XML Views
    //ActivitySecondBinding — это автосгенерированный класс на основе файла activity_second.xml.
    // Он превращает все элементы XML, у которых есть android:id  в готовые переменные
    //создаем переменную binding здесь, но инициализируем позже
    //Если объявить переменную внутри onCreate, она будет существовать только пока выполняется onCreate
    // Когда пользователь нажмет кнопку или галочку и вызовется другой метод, код «не увидит» эту переменную
    private companion object {
        private const val TAG = "Lifecycle_SecondActivity"
    }

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Фиксируем портретный режим
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Отключаем учет датчиков поворота для данного окна
        window.attributes = window.attributes.apply {
            screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        Log.d(TAG, "onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater) //превращает XML-файл в реальные объекты в памяти
        setContentView(binding.root) //передаёт готовое дерево отображения в окно экрана, чтобы пользователь увидел интерфейс

        // Кнопка "Назад"
        //binding.toolbar — обращение к компоненту <Toolbar> из activity_second.xml
        //setNavigationOnClickListener { ... } — при нажатии на стрелочку «Назад»
        //saveResultAndFinish() — при клике сохранится статус «Прочитано» и закроется текущий экран
        binding.toolbar.setNavigationOnClickListener {
            saveResultAndFinish()
        }

        // Загрузка картинки
        //load("https://...") — скачивает изображение из интернета, кеширует его и вставляет в ivHedgehogImage
        binding.ivHedgehogImage.load("https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/4993/live/c53b1340-52d9-11f1-a650-a94a8fc7de38.jpg.webp") {
            error(android.R.drawable.stat_notify_error) //системная иконка ошибки если картинка не загрузится
        }

        // Состояние "Прочитано"
        //intent — свойство Activity, который прислала MainActivity при запуске этого экрана
        //getBooleanExtra("EXTRA_IS_READ", false) — достаёт из этого конверта значение Boolean по ключу "EXTRA_IS_READ"
        val isReadInitial = intent.getBooleanExtra("EXTRA_IS_READ", false)
        binding.cbIsRead.isChecked = isReadInitial //устанавливает галочку на чекбоксе (cbIsRead) в то положение (отмечено / не отмечено), которое передал первый экран
        //setOnCheckedChangeListener — событие, которое срабатывает каждый раз, когда пользователь меняет состояние прочитано
        //{ _, isChecked -> ... }
        //isChecked —  состояние чекбокса (true, если галочка поставлена, false, если снята)
        binding.cbIsRead.setOnCheckedChangeListener { _, isChecked ->
            setReadResult(isChecked)  //вызывает метод, который подготавливает итоговый Intent с новым статусом для отправки обратно в MainActivity
        }
    }

    //запаковывает состояние «Прочитано» в ответный  для системы Android
    private fun setReadResult(isRead: Boolean) {
        val resultIntent = Intent().apply { //Intent() — создаётся пустой объект Intent
            putExtra("EXTRA_IS_READ", isRead) //внутрь Intent кладутся: флаг isRead (true или false) под ключом "EXTRA_IS_READ"
        }
        setResult(RESULT_OK, resultIntent) //RESULT_OK — константа, подтверждение, что операция прошла успешно
        //resultIntent — с обновленными данными
    }
    //если нажали назад
    private fun saveResultAndFinish() {
        setReadResult(binding.cbIsRead.isChecked) //передача текущего состояния "прочитано" в метод setReadResult
        finish() //закрытие SecondActivity
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}