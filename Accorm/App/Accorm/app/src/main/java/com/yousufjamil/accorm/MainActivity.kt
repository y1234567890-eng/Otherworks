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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
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

    @Preview(showSystemUi = true)
    @Composable
    fun Previewer() {
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

        subject = "Islamiyat"

        Text(text = "Previewer")
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
        composable("ppqs") {
            PPQsScreen(context)
        }
        composable("blogs") {
            BlogsResourcesScreen(context)
        }
        composable("contribute") {
            ContributeScreen(context)
        }
        composable("about") {
            AboutUsScreen(context)
        }
        composable("pptc") {
            PPTC(context)
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("home")
                }
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
                    onClick = { navController.navigate("blogs") },
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
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("blogs")
                        },
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
                textDisplay: String,
                fileID: String
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(49, 49, 49, 255))
                        .padding(20.dp)
                        .height(180.dp),
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
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                val openNotesIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://drive.google.com/file/d/$fileID/view")
                                )
                                context.startActivity(openNotesIntent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(105, 105, 151, 255)
                            )
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "View Notes",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Play Video"
                                )
                            }
                        }
                    }
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
                            textDisplay = decode()[0].getString("title"),
                            fileID = decode()[0].getString("fileID"),
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
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("blogs")
                        },
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
                        .background(Color(49, 49, 49, 255))
                        .padding(20.dp)
                        .height(180.dp),
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
                        Spacer(modifier = Modifier.height(10.dp))
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
                                containerColor = Color(105, 105, 151, 255)
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Watch Video",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Watch Video"
                                )
                            }
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
fun BlogsResourcesScreen(context: Context) {
    var canDecode by remember {
        mutableStateOf(false)
    }

    var result by remember {
        mutableStateOf("")
    }

    fun retrieveData() {
        val bgWorker = BackgroundWorker(context)
        Thread {
            bgWorker.execute("https://accorm.ginastic.co/300/blogs/?access-id=65c3ad4b976e3")
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

    retrieveData()

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
                                    fontSize = 22.sp
                                )
                            ) {
                                append("Educational \n\n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 48.sp
                                )
                            ) {
                                append("Blogs \n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp
                                )
                            ) {
                                append("by Accorm")
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
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Find helpful tips, experiences, and much more.",
                fontSize = 22.sp,
                fontFamily = lexend,
                color = Color.White,
                textAlign = TextAlign.Center
            )
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
            fun SingleBlogBox(
                nameDisplay: String,
                textDisplay: String,
                linkId: String,
                date: String
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(49, 49, 49, 255))
                        .padding(20.dp)
                        .height(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = textDisplay,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = lexend
                    )
                    Column {
                        Text(
                            text = "By $nameDisplay",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = lexend
                        )
                        Text(
                            text = "Published on ${date.subSequence(0, 8)}",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = lexend
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                val openVideoIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(linkId)
                                )
                                context.startActivity(openVideoIntent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(105, 105, 151, 255)
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Open Blog",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Open Blog"
                                )
                            }
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

                        if (decode()[0].has("title")) {
                            println("${decode()[0]}")

                            SingleBlogBox(
                                nameDisplay = decode()[0].getString("publisher"),
                                textDisplay = decode()[0].getString("title"),
                                linkId = "https://blogs.ginastic.co/blog/?id=${
                                    decode()[0].getString(
                                        "unique_id"
                                    )
                                }",
                                date = decode()[0].getString("date")
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
fun ContributeScreen(context: Context) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(31, 31, 54, 255))
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
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
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Contribute",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "❝Capable of helping? Go ahead! Contribute and appear here in the Contributors list, so that the public knows who is one of their major helper.❞",
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
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Color(38, 38, 66, 255))
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(120, 105, 218, 255))
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Appear on Top Contributors List",
                    fontFamily = lexend,
                    fontSize = 30.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "See the requirements from dashboard",
                    fontFamily = lexend,
                    fontSize = 22.sp,
                    color = Color.White
                )
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(255,255,255,255)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Contribute",
                            color = Color(120,105,218,255),
                            fontSize = 20.sp
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Contribute",
                            tint = Color(120,105,218,255)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Contributors",
                fontFamily = lexend,
                fontSize = 30.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "No Contributors found",
                fontFamily = lexend,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
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
        email: String,
        stdClass: String
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color(58, 58, 84, 255))
                .fillMaxWidth(0.8f)
                .height(180.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .width(60.dp)
                            .height(60.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = name,
                            color = Color(227, 227, 228, 255),
                            fontFamily = poppins,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.height(30.dp)
                        )
                        Text(
                            text = "From $stdClass",
                            color = Color(128, 128, 128, 255),
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = role,
                    color = Color(172, 172, 249, 255),
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .background(Color(114, 114, 253, 255))
                        .clickable {
                            val url = "mailto:$email"
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            context.startActivity(intent)
                        }
                        .fillMaxWidth()
                        .height(40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(255, 255, 255, 255),
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Contact",
                        color = Color(255, 255, 255, 255),
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(31, 31, 54, 255))
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
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
                painter = painterResource(id = R.drawable.baseline_people_24),
                contentDescription = "Subjects",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) {
                    append("For Students")
                }
                withStyle(SpanStyle(color = Color(172, 172, 249, 255))) {
                    append(". \n")
                }
                withStyle(SpanStyle(color = Color.White)) {
                    append("By Students")
                }
                withStyle(SpanStyle(color = Color(172, 172, 249, 255))) {
                    append(".")
                }
            },
            fontFamily = poppins,
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )
        Text(
            text = "© Accorm 2024 | All Rights Reserved",
            color = Color(172, 172, 249),
            fontFamily = poppins,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        DeveloperBox(
            icon = R.drawable.musab,
            name = "Musab Khan",
            role = "Manager, Owner, Founder, Full-Stack Developer",
            email = "contact@ginastic.co",
            stdClass = "Y10-B4"
        )
        DeveloperBox(
            icon = R.drawable.abd,
            name = "Abdullah Umair",
            role = "Front-end Developer, Advisor \n",
            email = "solution.i67@outlook.com",
            stdClass = "Y10-B4"
        )
        DeveloperBox(
            icon = R.drawable.yousuf,
            name = "M.Yousuf Jamil",
            role = "App Developer \n",
            email = "contact.us.myj@gmail.com",
            stdClass = "Y10-B1"
        )
        DeveloperBox(
            icon = R.drawable.majid,
            name = "Majid",
            role = "Content Provider \n",
            email = "mylifechoice96@gmail.com",
            stdClass = "Y10-B4"
        )
        DeveloperBox(
            icon = R.drawable.faizan,
            name = "Faizan Ali",
            role = "Content Provider \n",
            email = "adamcroft715@gmail.com",
            stdClass = "Y10-B4"
        )
        DeveloperBox(
            icon = R.drawable.taqi,
            name = "Taqi Ahmed",
            role = "Content Provider \n",
            email = "",
            stdClass = "Y10-B4"
        )
        DeveloperBox(
            icon = R.drawable.abdkamil,
            name = "Abdullah Kamil",
            role = "Content Provider, Content Writer, Advisor",
            email = "abdullahkamil1107@gmail.com",
            stdClass = "Y10-B5"
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun PPTC(context: Context) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(31, 31, 54, 255))
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Privacy & \n\n Terms",
            color = Color.White,
            fontSize = 58.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Last Updated: 15/2/2024",
            color = Color(255,255,255, 191),
            fontSize = 20.sp,
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Introduction",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Accorm gathers & displays top study resources for specific subjects. We consider it our duty to provide everyone with a central hub for learning. We also make it our job to credit the providers for their hard work, but this does not at all mean that we intend to promote them in any way. By using Accorm and the provided services, you agree to abide to our policies.",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Rights & Responsibilities",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "By using Accorm's services, users agree NOT to:",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = poppins
        )
        val agreeList = mutableListOf(
            "spam/scam or submit abusive, unethical, or illegal content",
            "submit any malicious or virus-containing files or links",
            "to credit the author of resources appropriately",
            "to not copy, share, resell or re-use any content or code of the website thereof."
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            agreeList.forEach() {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 16.dp, end = 8.dp)
                            .size(8.dp)
                            .background(Color.White, shape = CircleShape),
                    )

                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = poppins
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Data Collection & Use",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "We do not intend to harm your data in anyways, or use it inappropriately or illegally. However, we collect data for tracking web traffic, managing your account, and managing website content. We use your email to control your account and provide you with your data. We display your name, and profile picture, however, we do not make public your email address.",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Content Credits Policy",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "A major policy to keep in mind is to give credits to the rightful author of the resource properly. However, there are some websites that we do not accept any content from (such as SaveMyExams), due to certain reasons such as dis-allowance for re-using their content elsewhere. In severe cases(such as informal, illegal, unethical, or abusive content submissions), we may delete your content or take any other severe actions.",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Changes & Updates",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Being a user of this website, you agree thereof to comply with any changes to the policies. We will update the policies based on new scenarios observed and basing on any new feature developed on the website. Therefore, we do not intend to keep changing the policy on set intervals, but rather on situations.",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Contact Info",
            color = Color.White,
            fontSize = 28.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "If you face any difficulty, problem, or have any concern, please do not hesitate to contact us at our email.",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = poppins
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                val emailIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("mailto:contact&ginastic.co")
                )
                context.startActivity(emailIntent)
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(105, 105, 151, 255)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Email",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Email"
                )
            }
        }
    }
}














@Composable
fun PPQsScreen(context: Context) {
    var canDecode by remember {
        mutableStateOf(false)
    }

    var result1 by remember {
        mutableStateOf("")
    }
    var result2 by remember {
        mutableStateOf("")
    }
    var result3 by remember {
        mutableStateOf("")
    }
    var result4 by remember {
        mutableStateOf("")
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
    fun retrieveData(resultNo: Int) {
        val bgWorker = BackgroundWorker(context)
        val fileName = when (resultNo) {
            1 -> "qp_mj.json"
            2 -> "qp_on.json"
            3 -> "ms_mj.json"
            else -> "ms_on.json"
        }
        Thread {
            bgWorker.execute("https://accorm.ginastic.co/200/$subjectCode/$fileName")
        }.start()

        fun checkStatus() {
            Handler().postDelayed(
                {
                    if (bgWorker.status.toString() == "FINISHED") {
                        when (resultNo) {
                            1 -> result1 = bgWorker.response
                            2 -> result2 = bgWorker.response
                            3 -> result3 = bgWorker.response
                            else -> {
                                result4 = bgWorker.response
                                canDecode = true
                            }
                        }
                    } else {
                        checkStatus()
                    }
                }, 3000
            )
        }
        checkStatus()
    }

    for (i in 1..4) {
        retrieveData(i)
    }

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
                text = "PPQs",
                fontSize = 22.sp,
                fontFamily = lexend,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(36, 36, 36, 255))
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!canDecode) {
//                Text(
//                    text = "Loading...",
//                    color = Color.White,
//                    fontFamily = poppins,
//                    fontSize = 28.sp
//                )
            }
            @Composable
            fun SinglePPQBox(
                paper: String,
                fileIDQp: String,
                fileIDMs: String
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(49, 49, 49, 255))
                        .padding(20.dp)
                        .height(190.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "$subjectCode $subject - Paper $paper",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontFamily = lexend
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                val openPaperIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(fileIDQp)
                                )
                                context.startActivity(openPaperIntent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(105, 105, 151, 255)
                            )
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Question Paper",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Question Paper"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                val openPaperIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(fileIDMs)
                                )
                                context.startActivity(openPaperIntent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(105, 105, 151, 255)
                            )
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Marking Scheme",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Marking Scheme"
                                )
                            }
                        }
                    }
                }
            }

            SinglePPQBox(paper = "12", fileIDQp = "sfdasfds", fileIDMs = "saffs")
            SinglePPQBox(paper = "12", fileIDQp = "sfdasfds", fileIDMs = "saffs")
            SinglePPQBox(paper = "12", fileIDQp = "sfdasfds", fileIDMs = "saffs")
            SinglePPQBox(paper = "12", fileIDQp = "sfdasfds", fileIDMs = "saffs")
            SinglePPQBox(paper = "12", fileIDQp = "sfdasfds", fileIDMs = "saffs")

            if (canDecode) {
                val jsonObject1 = JSONObject(result1)
                val jsonObject2 = JSONObject(result2)
                val jsonObject3 = JSONObject(result3)
                val jsonObject4 = JSONObject(result4)

//                val noOfRows = jsonObject1.getInt("num-of-rows")
//                println("Stuff: $noOfRows")
//                for (i in 1..noOfRows) {
//                    fun decode(): List<JSONObject> {
//                        return try {
//                            listOf(jsonObject1.getJSONObject("$i"))
//                        } catch (_: Exception) {
//                            listOf(JSONObject())
//                        }
//                    }
//
//                    if (decode()[0].has("logo_bg")) {
//                        println("${decode()[0]}")
//
//                        SinglePPQBox(paper = paper, fileIDQp = qpLink, fileIDMs = msLink)
//
////                        println("Stuff: $r, $g, $b -- ${decode()[1].getString("logo")} -- ${decode()[1].getString("publisher")} -- ${decode()[1].getString("title")}")
//                    }
//                }
            }

        }
    }
}