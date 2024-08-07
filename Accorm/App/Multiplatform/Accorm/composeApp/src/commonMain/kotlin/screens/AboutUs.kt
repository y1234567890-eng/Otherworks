package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Bars
import compose.icons.fontawesomeicons.solid.BezierCurve
import compose.icons.fontawesomeicons.solid.File
import compose.icons.fontawesomeicons.solid.Info
import compose.icons.fontawesomeicons.solid.User
import compose.icons.fontawesomeicons.solid.Video
import screens.assets.CopyrightMessage
import screens.assets.Person
import screens.assets.Role
import screens.assets.Service

object AboutUs : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Info"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.Info)
            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(31, 31, 54))
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White, fontSize = 24.sp)) {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("Accorm ")
                        }
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append("Team")
                        }
                    }
                }
            )
            Spacer(Modifier.height(10.dp))
            Person(
                name = "M. Musab Khan",
                icon = "https://accorm.ginastic.co/pfp/musab1.jpg",
                email = "contact@ginastic.co",
                roles = listOf(
                    Role(
                        role = "CEO",
                        majorRole = true,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Founder",
                        majorRole = true,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Hoster",
                        majorRole = true,
                        clickPopup = "Accorm is hosted on Ginastic (ginastic.co). Musab Khan is the owner of the premium domain ginastic.co."
                    ),
                    Role(
                        role = "Frontend Dev.",
                        majorRole = false,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Backend Dev.",
                        majorRole = false,
                        clickPopup = ""
                    )
                )
            )
            Person(
                name = "M. Abdullah Umair",
                icon = "https://accorm.ginastic.co/pfp/abd.jpg",
                email = "solution.i67@outlook.com",
                roles = listOf(
                    Role(
                        role = "Premier Advisor",
                        majorRole = true,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Web Layout Designer",
                        majorRole = false,
                        clickPopup = ""
                    )
                )
            )
            Person(
                name = "M. Yousuf Jamil",
                icon = "https://accorm.ginastic.co/pfp/yousuf.png",
                email = "muhammadyousufjamil@gmail.com",
                roles = listOf(
                    Role(
                        role = "App Developer",
                        majorRole = true,
                        clickPopup = "Accorm App for Android and Desktop is developed by Yousuf Jamil. Accorm is available for download on Play Store via his account."
                    )
                )
            )
            Person(
                name = "M. Faizan Ali",
                icon = "https://accorm.ginastic.co/pfp/faizan.png",
                email = "adamcroft715@gmail.com",
                roles = listOf(
                    Role(
                        role = "Premier Content Provider",
                        majorRole = true,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Advisor",
                        majorRole = false,
                        clickPopup = ""
                    )
                )
            )
            Person(
                name = "Majid M.",
                icon = "https://accorm.ginastic.co/pfp/majid3.png",
                email = "mylifechoice96@gmail.com",
                roles = listOf(
                    Role(
                        role = "Premier Content Provider",
                        majorRole = true,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Advisor",
                        majorRole = false,
                        clickPopup = ""
                    )
                )
            )
            Person(
                name = "Abdullah Kamil",
                icon = "https://accorm.ginastic.co/pfp/abdkamil.png",
                email = "abdullahkamil1107@gmail.com",
                roles = listOf(
                    Role(
                        role = "Content Provider",
                        majorRole = false,
                        clickPopup = ""
                    ),
                    Role(
                        role = "Advisor",
                        majorRole = false,
                        clickPopup = ""
                    )
                )
            )
            Person(
                name = "Taqi Ahmed",
                icon = "https://accorm.ginastic.co/pfp/taqi.jpg",
                email = "",
                roles = listOf(
                    Role(
                        role = "Content Writer",
                        majorRole = false,
                        clickPopup = ""
                    )
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}