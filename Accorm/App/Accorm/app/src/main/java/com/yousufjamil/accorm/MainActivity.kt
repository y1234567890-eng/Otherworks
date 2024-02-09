package com.yousufjamil.accorm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.accorm.ui.theme.AccormTheme
import kotlinx.coroutines.launch
import org.json.JSONObject

lateinit var navController: NavHostController
lateinit var poppins: FontFamily
lateinit var lexend: FontFamily
lateinit var subject: String

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        poppins = FontFamily(
            Font(R.font.poppins_thin, FontWeight.Thin),
            Font(R.font.poppins_light, FontWeight.Light),
            Font(R.font.poppins_bold, FontWeight.Bold),
            Font(R.font.poppins_semibold, FontWeight.SemiBold),
            Font(R.font.poppins_regular, FontWeight.Normal)
        )

        lexend = FontFamily(
            Font(R.font.lexend_thin, FontWeight.Thin),
            Font(R.font.lexend_regular, FontWeight.Normal),
            Font(R.font.lexend_bold, FontWeight.Bold)
        )

        setContent {
            AccormTheme {

                navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    NavigationDrawer() { scope.launch { drawerState.close() } }
                                }
                            }
                        }
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Scaffold(
                                modifier = Modifier.padding(0.dp),
                                topBar = {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        TopAppBar(
                                            title = {
                                                Text(
                                                    text = "Accorm",
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.95f)
                                                        .padding(top = 6.dp),
                                                    textAlign = TextAlign.Left,
                                                    fontFamily = poppins,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            },
                                            navigationIcon = {
                                                IconButton(
                                                    onClick = {
                                                        scope.launch { drawerState.open() }
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Menu,
                                                        contentDescription = "Menu Button",
                                                        modifier = Modifier.size(30.dp)
                                                    )
                                                }
                                            },
                                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                                navigationIconContentColor = Color.White,
                                                containerColor = Color(75, 75, 110, 255)
                                            )
                                        )
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(it),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Navigation(this@MainActivity, navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(context: Context, navHostController: NavHostController) {
    NavHost(navHostController, "home") {
        composable("home") {
            HomeScreen(context)
        }
        composable("resources") {
            SubjectsScreen(context)
        }
        composable("notes-resources") {
            NotesResourcesScreen(context)
        }
        composable("videos-resources") {
            VideosResourcesScreen(context)
        }
        composable("about") {
            AboutUsScreen(context)
        }
    }
}

@Composable
fun NavigationDrawer(closeDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(46, 55, 72, 255))
            .padding(horizontal = 20.dp)
            .clickable { closeDrawer() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        @Composable
        fun Divider() {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(33, 41, 56, 255))
                    .height(2.dp)
            )
        }

        @Composable
        fun NavSingleButton(
            onClick: () -> Unit,
            usesImageVector: Boolean,
            imageVector: ImageVector = Icons.Default.Warning,
            painterResource: Int = R.drawable.app_ic,
            contentDescription: String
        ) {
            Button(
                onClick = {
                    closeDrawer()
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(corner = CornerSize(1.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (usesImageVector) {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(painterResource),
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        text = contentDescription,
                        fontFamily = lexend,
                        fontSize = 19.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_ic),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(50.dp)))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Accorm",
                fontFamily = lexend,
                color = Color.White,
                fontSize = 35.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            NavSingleButton(
                onClick = { navController.navigate("resources") },
                usesImageVector = false,
                painterResource = R.drawable.book_open,
                contentDescription = "Subjects"
            )
            NavSingleButton(
                onClick = { },
                usesImageVector = false,
                painterResource = R.drawable.apps,
                contentDescription = "Services"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            NavSingleButton(
                onClick = { },
                usesImageVector = true,
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Contribute"
            )
            NavSingleButton(
                onClick = { navController.navigate("about") },
                usesImageVector = true,
                imageVector = Icons.Default.Info,
                contentDescription = "About"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            NavSingleButton(
                onClick = { },
                usesImageVector = true,
                imageVector = Icons.Default.Person,
                contentDescription = "Profile"
            )
            NavSingleButton(
                onClick = { },
                usesImageVector = false,
                painterResource = R.drawable.baseline_logout_24,
                contentDescription = "Logout"
            )
            NavSingleButton(
                onClick = { },
                usesImageVector = false,
                painterResource = R.drawable.baseline_person_add_alt_1_24,
                contentDescription = "Sign up"
            )
            NavSingleButton(
                onClick = { },
                usesImageVector = false,
                painterResource = R.drawable.baseline_login_24,
                contentDescription = "Login"
            )
        }
    }
}

@Composable
fun HomeScreen(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        Color(92, 62, 123),
                        Color(44, 44, 76)
                    ),
                    radius = 1500f
                )
            )
//            .background(Color(38, 38, 47, 255))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(color = Color.White, fontFamily = poppins, fontSize = 60.sp)
                ) {
                    append("Educate")
                    withStyle(
                        SpanStyle(
                            color = Color(144, 144, 214, 255)
                        )
                    ) {
                        append(".\n\n\n")
                    }
                    append("Empower")
                    withStyle(
                        SpanStyle(
                            color = Color(144, 144, 214, 255)
                        )
                    ) {
                        append(".\n\n\n")
                    }
                    append("Excel")
                    withStyle(
                        SpanStyle(
                            color = Color(144, 144, 214, 255)
                        )
                    ) {
                        append(".\n\n")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Where students and educational \ncontent blend",
            color = Color(144, 144, 214, 255),
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {

            @Composable
            fun HomeIcon(
                onClick: () -> Unit,
                usesImageVector: Boolean,
                imageVector: ImageVector = Icons.Default.Warning,
                painterResource: Int = R.drawable.app_ic,
                contentDescription: String
            ) {
                IconButton(
                    onClick = { onClick() },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(53, 53, 93, 255)
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(100))
                ) {
                    if (usesImageVector) {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(painterResource),
                            contentDescription = contentDescription,
                            tint = Color(171, 171, 248, 255),
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
            HomeIcon(
                onClick = {},
                usesImageVector = false,
                painterResource = R.drawable.book_open,
                contentDescription = "Subjects"
            )
            HomeIcon(
                onClick = {},
                usesImageVector = false,
                painterResource = R.drawable.apps,
                contentDescription = "Features"
            )
            HomeIcon(
                onClick = {},
                usesImageVector = true,
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Contribute"
            )

            HomeIcon(
                onClick = { navController.navigate("about") },
                usesImageVector = true,
                imageVector = Icons.Default.Info,
                contentDescription = "About"
            )
            HomeIcon(
                onClick = {},
                usesImageVector = false,
                painterResource = R.drawable.baseline_shield_24,
                contentDescription = "Privacy & Terms"
            )
        }
    }
}

@Composable
fun SubjectsScreen(context: Context) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(31, 31, 54, 255))
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(Color(116, 132, 195, 255))
                .padding(25.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.book_open),
                contentDescription = "Subjects",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "❝A wide range of in-depth resources for 10 different subjects, covering all the main subjects and sciences, extended course.❞",
            color = Color.White,
            fontFamily = poppins,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "© Accorm 2024 | All Rights Reserved",
            color = Color(172, 172, 249),
            fontFamily = poppins,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "IGCSE",
            color = Color.White,
            fontFamily = poppins,
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        @Composable
        fun SingleColumn(
            title: String,
            content: @Composable () -> Unit
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(38, 38, 66, 255))
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = lexend,
                    fontSize = 30.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(15.dp))
                content()
            }
        }

        SingleColumn(title = "Blogs") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(100, 86, 144, 255))
                    .padding(10.dp)

            ) {
                Text(
                    text = "Educational Blogs",
                    fontFamily = lexend,
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Powered by ")
                        withStyle(
                            SpanStyle(textDecoration = TextDecoration.Underline)
                        ) {
                            append("Ginastic Blogs")
                        }
                    },
                    fontSize = 15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(142, 142, 209, 255)
                    )
                ) {
                    Text(
                        text = "Browse",
                        fontFamily = lexend,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Browse"
                    )
                }
            }
        }

        SingleColumn(title = "Notes, Videos & PPQs") {
            @Composable
            fun SingleSubject(
                code: String,
                title: String
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(100, 86, 144, 255))
                        .padding(10.dp)

                ) {
                    Text(
                        text = code,
                        fontFamily = lexend,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = title,
                        fontFamily = lexend,
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            subject = title
                            navController.navigate("notes-resources")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(142, 142, 209, 255)
                        )
                    ) {
                        Text(
                            text = "Notes",
                            fontFamily = lexend,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Browse"
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(
                        onClick = {
                            subject = title
                            navController.navigate("videos-resources")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(142, 142, 209, 255)
                        )
                    ) {
                        Text(
                            text = "Videos",
                            fontFamily = lexend,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Browse"
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(142, 142, 209, 255)
                        )
                    ) {
                        Text(
                            text = "PPQs",
                            fontFamily = lexend,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Browse"
                        )
                    }
                }
            }
            SingleSubject("0493", "Islamiyat")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0448", "Pakistan Studies, \n \n History")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0448", "Pakistan Studies, \n \n Geography")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0452", "Accounting")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0625", "Physics")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0620", "Chemistry")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0610", "Biology")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0478", "Computer Science")
            Spacer(modifier = Modifier.height(15.dp))
            SingleSubject("0480", "Maths")
        }
    }
}

@Composable
fun NotesResourcesScreen(context: Context) {
    var canDecode by remember {
        mutableStateOf(false)
    }

    var result by remember {
        mutableStateOf("")
    }

    fun retrieveData(subject: String) {
        val bgWorker = BackgroundWorker(context)
        Thread {
            bgWorker.execute("https://accorm.ginastic.co/300/fetch/?access-id=65aea3e3e6184&subject=$subject")
        }.start()

        fun checkStatus() {
            Handler().postDelayed(
                {
                    if (bgWorker.status.toString() == "FINISHED") {
                        canDecode = true
                        result = bgWorker.response
                    } else {
                        checkStatus()
                    }
                }, 3000
            )
        }
        checkStatus()
    }

    var subjectRetrieve by remember {
        mutableStateOf("")
    }
    var subjectCode by remember {
        mutableStateOf("")
    }
    subjectRetrieve = when (subject) {
        "Islamiyat" -> "islamiyat"
        "Pakistan Studies, \n \n History" -> "history"
        "Pakistan Studies, \n \n Geography" -> "geography"
        "Accounting" -> "accounting"
        "Physics" -> "physics"
        "Chemistry" -> "chemistry"
        "Biology" -> "biology"
        "Computer Science" -> "computer_science"
        else -> "maths"
    }
    subjectCode = when (subject) {
        "Islamiyat" -> "0493"
        "Pakistan Studies, \n \n History" -> "0448"
        "Pakistan Studies, \n \n Geography" -> "0448"
        "Accounting" -> "0452"
        "Physics" -> "0625"
        "Chemistry" -> "0620"
        "Biology" -> "0610"
        "Computer Science" -> "0478"
        else -> "0580"
    }
    retrieveData(subjectRetrieve)

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(38, 38, 47, 255))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(115, 114, 164, 255)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color.White,
                                fontFamily = lexend
                            )
                        ) {
                            withStyle(
                                SpanStyle(
                                    fontSize = 18.sp
                                )
                            ) {
                                append("IGCSE \n\n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 48.sp
                                )
                            ) {
                                append("$subject \n\n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 28.sp
                                )
                            ) {
                                append(subjectCode)
                            }
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(66, 66, 66, 255))
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notes",
                fontSize = 22.sp,
                fontFamily = lexend,
                color = Color.White
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(145, 145, 254, 255))
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("videos-resources")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Videos",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(145, 145, 254, 255))
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Blogs",
                        color = Color.White
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(36, 36, 36, 255))
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!canDecode) {
                Text(
                    text = "Loading...",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 28.sp
                )
            }
            @Composable
            fun SingleNotesBox(
                bgRgb: Color,
                logoLetter: String,
                nameDisplay: String,
                textDisplay: String
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(66, 66, 66, 255))
                        .padding(20.dp)
                        .height(150.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .width(50.dp)
                                .height(50.dp)
                                .background(bgRgb)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = logoLetter,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontFamily = poppins
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = nameDisplay,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = lexend
                        )
                    }
                    Text(
                        text = textDisplay,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = lexend
                    )
                }
            }

            if (canDecode) {
                val jsonObject = JSONObject(result)
                val noOfRows = jsonObject.getInt("num-of-rows")
                println("Stuff: $noOfRows")
                for (i in 1..noOfRows) {
                    fun decode(): List<JSONObject> {
                        return try {
                            listOf(jsonObject.getJSONObject("$i"))
                        } catch (_: Exception) {
                            listOf(JSONObject())
                        }
                    }

                    if (decode()[0].has("logo_bg")) {
                        println("${decode()[0]}")
                        val r: Int =
                            decode()[0].getString("logo_bg").substring(1, 3).toInt(16) // 16 for hex

                        val g: Int =
                            decode()[0].getString("logo_bg").substring(3, 5).toInt(16) // 16 for hex

                        val b: Int =
                            decode()[0].getString("logo_bg").substring(5, 7).toInt(16) // 16 for hex

                        SingleNotesBox(
                            bgRgb = Color(r, g, b),
                            logoLetter = decode()[0].getString("logo"),
                            nameDisplay = decode()[0].getString("publisher"),
                            textDisplay = decode()[0].getString("title")
                        )

//                        println("Stuff: $r, $g, $b -- ${decode()[1].getString("logo")} -- ${decode()[1].getString("publisher")} -- ${decode()[1].getString("title")}")
                    }
                }
            }

        }
    }
}

@Composable
fun VideosResourcesScreen(context: Context) {
    var canDecode by remember {
        mutableStateOf(false)
    }

    var result by remember {
        mutableStateOf("")
    }

    fun retrieveData(subject: String) {
        val bgWorker = BackgroundWorker(context)
        Thread {
            bgWorker.execute("https://accorm.ginastic.co/300/vids/?access-id=5ece4797eaf5e&subject=$subject")
        }.start()

        fun checkStatus() {
            Handler().postDelayed(
                {
                    if (bgWorker.status.toString() == "FINISHED") {
                        canDecode = true
                        result = bgWorker.response
                    } else {
                        checkStatus()
                    }
                }, 3000
            )
        }
        checkStatus()
    }

    var subjectRetrieve by remember {
        mutableStateOf("")
    }
    var subjectCode by remember {
        mutableStateOf("")
    }
    subjectRetrieve = when (subject) {
        "Islamiyat" -> "islamiyat"
        "Pakistan Studies, \n \n History" -> "history"
        "Pakistan Studies, \n \n Geography" -> "geography"
        "Accounting" -> "accounting"
        "Physics" -> "physics"
        "Chemistry" -> "chemistry"
        "Biology" -> "biology"
        "Computer Science" -> "computer%20science"
        else -> "maths"
    }
    subjectCode = when (subject) {
        "Islamiyat" -> "0493"
        "Pakistan Studies, \n \n History" -> "0448"
        "Pakistan Studies, \n \n Geography" -> "0448"
        "Accounting" -> "0452"
        "Physics" -> "0625"
        "Chemistry" -> "0620"
        "Biology" -> "0610"
        "Computer Science" -> "0478"
        else -> "0580"
    }
    retrieveData(subjectRetrieve)

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(38, 38, 47, 255))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(115, 114, 164, 255)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color.White,
                                fontFamily = lexend
                            )
                        ) {
                            withStyle(
                                SpanStyle(
                                    fontSize = 18.sp
                                )
                            ) {
                                append("IGCSE \n\n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 48.sp
                                )
                            ) {
                                append("$subject \n\n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 28.sp
                                )
                            ) {
                                append(subjectCode)
                            }
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(66, 66, 66, 255))
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Videos",
                fontSize = 22.sp,
                fontFamily = lexend,
                color = Color.White
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(145, 145, 254, 255))
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("notes-resources")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Notes",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(145, 145, 254, 255))
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Blogs",
                        color = Color.White
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(36, 36, 36, 255))
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!canDecode) {
                Text(
                    text = "Loading...",
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 28.sp
                )
            }
            @Composable
            fun SingleVideoBox(
                bgRgb: Color,
                logoLetter: String,
                nameDisplay: String,
                textDisplay: String,
                link: String
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(66, 66, 66, 255))
                        .padding(20.dp)
                        .height(150.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .width(50.dp)
                                .height(50.dp)
                                .background(bgRgb)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = logoLetter,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontFamily = poppins
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = nameDisplay,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = lexend
                        )
                    }
                    Column {
                        Text(
                            text = textDisplay,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontFamily = lexend
                        )
                        Button(
                            onClick = {
                                val openVideoIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(link)
                                )
                                context.startActivity(openVideoIntent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(142, 142, 209, 25)
                            )
                        ) {
                            Text(
                                text = "Open link",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "Open link"
                            )
                        }
                    }
                }
            }

            if (canDecode) {
                if (result != "no data available.") {
                    val jsonObject = JSONObject(result)
                    val noOfRows = jsonObject.getInt("num-of-rows")
                    println("Stuff: $noOfRows")
                    for (i in 1..noOfRows) {
                        fun decode(): List<JSONObject> {
                            return try {
                                listOf(jsonObject.getJSONObject("$i"))
                            } catch (_: Exception) {
                                listOf(JSONObject())
                            }
                        }

                        if (decode()[0].has("logo_bg")) {
                            println("${decode()[0]}")
                            val r: Int =
                                decode()[0].getString("logo_bg").substring(1, 3)
                                    .toInt(16) // 16 for hex

                            val g: Int =
                                decode()[0].getString("logo_bg").substring(3, 5)
                                    .toInt(16) // 16 for hex

                            val b: Int =
                                decode()[0].getString("logo_bg").substring(5, 7)
                                    .toInt(16) // 16 for hex

                            SingleVideoBox(
                                bgRgb = Color(r, g, b),
                                logoLetter = decode()[0].getString("logo"),
                                nameDisplay = decode()[0].getString("publisher"),
                                textDisplay = decode()[0].getString("title"),
                                link = decode()[0].getString("link")
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Unknown Error",
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 28.sp
                    )
                }
            }

        }
    }
}

@Composable
fun AboutUsScreen(context: Context) {
    val scrollState = rememberScrollState()

    @Composable
    fun DeveloperBox(
        icon: Int,
        name: String,
        role: String,
        email: String
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color(53, 53, 62, 255))
                .fillMaxWidth(0.8f)
                .height(280.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.9f)
                        .height(140.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = name,
                    color = Color(227, 227, 228, 255),
                    fontFamily = poppins,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = role,
                    color = Color(160, 160, 231, 255),
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .background(Color(75, 75, 110, 255))
                        .clickable {
                            val url = "mailto:$email"
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            context.startActivity(intent)
                        }
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(255, 255, 255, 255),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(38, 38, 47, 255))
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        DeveloperBox(
            icon = R.drawable.musab,
            name = "Musab Khan",
            role = "Project Manager",
            email = "contact@ginastic.co"
        )
        DeveloperBox(
            icon = R.drawable.abd,
            name = "Abdullah Umair",
            role = "Designer",
            email = "solution.i67@outlook.com"
        )
        DeveloperBox(
            icon = R.drawable.yousuf,
            name = "M.Yousuf Jamil",
            role = "Project Manager",
            email = "contact.us.myj@gmail.com"
        )
        DeveloperBox(
            icon = R.drawable.majid,
            name = "Majid Muhammad",
            role = "Content Provider",
            email = "mylifechoice96@gmail.com"
        )
        DeveloperBox(
            icon = R.drawable.faizan,
            name = "Faizan",
            role = "Content Provider",
            email = "adamcroft715@gmail.com"
        )
        DeveloperBox(
            icon = R.drawable.basim,
            name = "Basim Kashif",
            role = "Contributor",
            email = "bkashamsi2008@gmail.com"
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}
