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
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
@Composable
fun MainActivityScreen() {
    DroidPractice1Theme {
        val context = LocalContext.current
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                shareArticle(
                                    context = context,
                                    title = "Ученые в Южной Америке открыли новый вид диких кошек - тилькайо",
                                    text = "Кот Тигрино — пока единственный известный ученым представитель вида"
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
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
                    // Пустой отступ в 8 пикселей
                    //Spacer(modifier = Modifier.height(8.dp))
                    // Подзаголовок / Дата (Мелкий и серый — другой стиль)
                    Text(
                        text = "Кот Тигрино — пока единственный известный ученым представитель вида",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    // Картинка из интернета с помощью библиотеки Coil
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
                        fontWeight = FontWeight.Bold
                    )

                    Text (
                        text = "Русская служба Би-би-си",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp) // <-- Отступ снизу на 24 dp
                    )

                    Text (
                        text = "19 сентября 2026",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(bottom = 4.dp) // <-- Отступ снизу на 24 dp
                    )

                    Text (
                        text = "Время чтения: 5 минут",
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(bottom = 24.dp) // Отступ снизу на 24 dp
                    )

                    // 4. Основной текст статьи (Обычный размер)
                    Text(
                        text = "Пятнистая кошка из лесов Боливии официально признана новым видом — Leopardus tilcayo. " +
                                "Это первое открытие нового вида диких кошек за более чем 100 лет. " +
                                "Пока ученым известен только один представитель этого вида, но они уверены, что его сородичи обитают в лесах Южной Америки. " +
                                "Об этом говорится в исследовании, опубликованном в журнале Current Biology.",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
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
    context.startActivity(shareIntent)
}