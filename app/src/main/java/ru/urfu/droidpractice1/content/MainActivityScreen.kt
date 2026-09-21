@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import ru.urfu.droidpractice1.SecondActivity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.clickable


@Composable //задаем интерфейс
fun MainActivityScreen() { //внутри ыункции описываем весь внеш вид
    //счетчики лайков и дизлайков
    //mutableIntStateOf - создаёт числовое состояние со стартовым значением 0, когд анажимаем класс/диз знач увелич или убавл
    //rememberSaveable - сохраняет значения лайков/дизов при повороте экрана
    var likesCount by rememberSaveable { mutableIntStateOf(0) }
    var dislikesCount by rememberSaveable { mutableIntStateOf(0) }
    DroidPractice1Theme { //обертка для интерфейса
        val context = LocalContext.current
        // Сохраняем состояние "Прочитано" при повороте экрана
        //isSecondArticleRead - для хранения статуса 2 экрана, т.е. прочитан или нет
        //mutableStateOf(false) - следит за переменной, если состояние изменяется, то меняется цвет карточки со статьей на 1 экране
        //rememberSaveable - сохраняет значение "прочитано" при повороте экрана
        var isSecondArticleRead by rememberSaveable { mutableStateOf(false) }

        // Лончер для запуска SecondActivity и ожидания ответа
        val launcher = rememberLauncherForActivityResult(
            //запускаем другую activity и ожидаем от неё результат обратно
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result -> //срабатывает в момент, когда второй экран закрывается
            if (result.resultCode == Activity.RESULT_OK) { //проверяеv, успешно ли закрылся второй экран
                val isRead = result.data?.getBooleanExtra("EXTRA_IS_READ", false) ?: false //вытаскивает из "письма" (Intent), пришедшего со второго экрана, значение галочки по ключу "EXTRA_IS_READ"
                isSecondArticleRead = isRead //обновляет переменную состояния, как только она обновляется, карточка становится бледной/яркой
            }
        }
        Scaffold( //каркас экрана
            modifier = Modifier.fillMaxSize(), //растянуть на весь экран тф
            topBar = { //шапка приложения
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    },
                    actions = {
                        Button( //кнопка пделиться
                            onClick = { //если нажали
                                shareArticle( //вызов функции поделиться
                                    context = context,
                                    title = "Ученые в Южной Америке открыли новый вид диких кошек - тилькайо",
                                    text = "Кот Тигрино — пока единственный известный ученым представитель вида"
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share, //стандартная иконка поделиться
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )
            }) { innerPadding ->
            // Column расставляет все элементы сверху вниз
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) //отступ под шапку
                        .verticalScroll(rememberScrollState()) //включить прокрутку
                        .padding(16.dp)
            )
                {
                    Text (
                        //Заголовок статьи
                        text = "Ученые в Южной Америке открыли новый вид диких кошек - тилькайо",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF283593)
                    )

                    // Подзаголовок
                    Text(
                        text = "Кот Тигрино — пока единственный известный ученым представитель вида",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    // Бразмещение кнопок лайка и дизлайка
                    Row( //выстраиваем в ряд
                        modifier = Modifier
                            .fillMaxWidth() //растяжение на всю ширину
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Start //выравнивает содержимое строки по левому краю
                    ) {
                        // Кнопка Лайк
                        Button(
                            onClick = { likesCount++ }, //увелич на 1 лайк
                            modifier = Modifier.padding(end = 12.dp), //отступ српава от кнопки 12dp
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF283593), // Синий цвет кнопки
                                contentColor = Color.White           // Цвет текста внутри
                            )
                        ) {
                            Text(text = "👍 $likesCount")
                        }

                        // Кнопка Дизлайк
                        Button(
                            onClick = { dislikesCount++ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF283593), // цвет кнопки
                                contentColor = Color.White           // Цвет текста/эмодзи внутри
                            )
                        ) {
                            Text(text = "👎 $dislikesCount")
                        }
                    }

                    // Картинка из интернета библиотека Coil
                    AsyncImage(
                        model = "https://ichef.bbci.co.uk/ace/ws/640/cpsprodpb/43f8/live/f74a8020-b326-11f1-bc1f-3f186ca4140c.jpg.webp",
                        contentDescription = "Обложка статьи",
                        modifier = Modifier
                            .fillMaxWidth() // Растягиваем по ширине
                            .height(300.dp) // Фиксированная высота
                            .padding(bottom = 24.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop, // Обрезаем картинку по размеру рамки
                        placeholder = painterResource(id = R.drawable.ic_launcher_background), //чтобы было видно на превью, что что-то загружается
                        error = rememberVectorPainter(image = Icons.Default.Warning) //вывод ошибки, была проблема с отсутствием интернета на эмуляторе, переключила на тф
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text (
                        text = "Отдел новостей",
                        fontSize = 17.sp,
                        lineHeight = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEFB8C8)
                    )

                    Text (
                        text = "Русская служба Би-би-си",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
                        color = Color(0xFFD0BCFF)
                    )

                    Text (
                        text = "19 сентября 2026",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = Color(0xFFCCC2DC)
                    )

                    Text (
                        text = "Время чтения: 5 минут",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(bottom = 24.dp),
                        color = Color(0xFF676767)
                    )

                    // Основной текст статьи
                    Text(
                        text = "Пятнистая кошка из лесов Боливии официально признана новым видом — Leopardus tilcayo. " +
                                "Это первое открытие нового вида диких кошек за более чем 100 лет. " +
                                "Пока ученым известен только один представитель этого вида, но они уверены, что его сородичи обитают в лесах Южной Америки. " +
                                "Об этом говорится в исследовании, опубликованном в журнале Current Biology.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold, //жирный текст
                        modifier = Modifier.padding(bottom = 24.dp),
                        color = Color(0xFFEFB8C8)
                    )

                    Text(
                        text = "Этот небольшой хищник, размером примерно с обычную домашнюю кошку, " +
                                "долгое время оставался незамеченным, так как его, судя по всему, " +
                                "путали с другими дикими кошками, имеющими схожий пятнистый или полосатый окрас.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "Однако анализ ДНК показал, что эта кошка представляет собой самостоятельный вид." +
                                " Это открытие подчеркивает, как много еще предстоит узнать об этих одних из самых" +
                                " неуловимых млекопитающих на планете, говорят авторы исследования.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "По словам открывательницы вида Паолы Ногалес-Аскаррунц, " +
                                "было принято решение дать виду имя «тилькайо» (Leopardus tilcayo," +
                                " тилькайская тигровая кошка) — так местные жители называют эту кошку." +
                                " Этим мы отдаем дань уважения знаниям местных жителей и их связи с этим животным," +
                                " отметила исследовательница.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    // Картинка из интернета с помощью библиотеки Coil
                    AsyncImage(
                        model = "https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/b5fe/live/7a5d0910-b327-11f1-a430-4d16ee157c41.jpg.webp",
                        contentDescription = "Обложка статьи",
                        modifier = Modifier
                            .fillMaxWidth() // Растягиваем по ширине
                            .height(300.dp) // Фиксированная высота
                            .padding( bottom = 4.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop, // Обрезаем картинку по размеру рамки
                        placeholder = painterResource(id = R.drawable.ic_launcher_background), //чтобы было видно на превью, что что-то загружается
                        error = rememberVectorPainter(image = Icons.Default.Warning) //вывод ошибки, была проблема с отсутствием интернета на эмуляторе, переключила на тф
                    )
                    Text (
                        text = "Местный житель нашел котенка в лесу и дал ему кличку Тигрино." +
                                " Он принял его за детеныша домашней кошки",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(bottom = 24.dp) // Отступ снизу на 24 dp
                    )

                    Text(
                        text = "Пока известна лишь одна особь этого вида — кот," +
                                " живущий в приюте в Боливии, однако ученые полагают," +
                                " что есть и другие представители этого вида.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "Именно этот десятилетний кот по кличке Тигрино" +
                                " навел ученых на след и привел к открытию.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "Боливийский ученый доктор Ногалес-Аскаррунс" +
                                " работала волонтером в приюте, когда обратила" +
                                " внимание на необычный внешний вид животного," +
                                " появившегося у них в приюте.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "Тигрино в приют принес местный житель, который нашел животное" +
                                " в лесу и поначалу принял его за домашнего котенка. " +
                                "Однако по мере взросления котенка стало ясно, что ведет" +
                                " он себя совсем не так, как домашняя кошка.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // КНОПКА ПЕРЕХОДА НА ВТОРУЮ СТАТЬЮ
                    // Если статья прочитана — меняем прозрачность (alpha = 0.4f), делая её тусклой
                    Card( //контейнер с закругленными углами для анонса статьи
                        modifier = Modifier
                            .fillMaxWidth()
                            //alpha() изменяет прозрачность всего элемента (от 0.0f — полностью невидимый, до 1.0f — полностью видимый)
                            .alpha(if (isSecondArticleRead) 0.4f else 1.0f) //если статья прочитана то карточка становится прозрачнее
                            .clickable { //вся область карточки нажимаема
                                val intent = Intent(context, SecondActivity::class.java).apply { //создаёт намерение открыть экран второй статьи
                                    //внутри Intent лежит состояние (прочитана статья или нет) под ключом "EXTRA_IS_READ", чтобы SecondActivity знала, в каком положении оставить галочку при открытии
                                    putExtra("EXTRA_IS_READ", isSecondArticleRead) //
                                }
                                launcher.launch(intent) //запускает SecondActivity с режимом ожидания результата от неё
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8EAF6)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Зачем ежу GPS-трекер? Спасти популяцию британских ежей помогут спутники и ИИ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSecondArticleRead) Color.Gray else Color.Black
                            )

                            if (isSecondArticleRead) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "✓ Прочитано",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainActivityScreen()
}
// Функция-помощник для вызова системного диалога "Поделиться"
fun shareArticle(context: Context, title: String, text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$title\n\n$text")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Поделиться статьей")
    context.startActivity(shareIntent)//лоло
}