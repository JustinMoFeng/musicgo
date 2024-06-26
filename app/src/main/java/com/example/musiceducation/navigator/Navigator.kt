package com.example.musiceducation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musiceducation.config.RouteConfig
import com.example.musiceducation.ui.composables.authenticate.LoginPage
import com.example.musiceducation.ui.composables.book.BookCatalogPage
import com.example.musiceducation.ui.composables.book.BookPage
import com.example.musiceducation.ui.composables.book.BookReadPage
import com.example.musiceducation.ui.composables.common.WebViewPage
import com.example.musiceducation.ui.composables.forum.ForumPage
import com.example.musiceducation.ui.composables.authenticate.RegisterPage
import com.example.musiceducation.ui.composables.me.MePage
import com.example.musiceducation.ui.viewModels.BookCatalogViewModel
import com.example.musiceducation.ui.viewModels.UserViewModel
import java.net.URLDecoder


@Composable
fun MusicEducationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouteConfig.ROUTE_BOOK
){

    val bookCatalogViewModel = BookCatalogViewModel()
    val userViewModel : UserViewModel = viewModel(factory = UserViewModel.Factory)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable(RouteConfig.ROUTE_BOOK){
            BookPage(navController = navController)
        }

        composable(RouteConfig.ROUTE_ME){
            MePage(navController = navController)
        }

        composable(RouteConfig.ROUTE_FORUM){
            ForumPage(navController = navController)
        }

        composable(RouteConfig.ROUTE_BOOK_READ+"/{bookId}/{pageIndex}"){
            val bookId = it.arguments?.getString("bookId") ?: ""
            val pageIndex = it.arguments?.getString("pageIndex") ?: "0"
            val pageIndexInt = Integer.parseInt(pageIndex)


            BookReadPage(navController = navController, bookId = bookId, pageIndex = pageIndexInt)
        }

        composable(RouteConfig.COMMON_WEBVIEW+"/{url}/{title}"){
            var url = it.arguments?.getString("url") ?: ""
            url = URLDecoder.decode(url, "utf-8")
            val title = it.arguments?.getString("title") ?: ""
            WebViewPage(navController = navController, url = url, title = title)
        }

        composable(RouteConfig.ROUTE_BOOK_CATALOG+"/{bookId}/{currentPage}"){
            val bookId = it.arguments?.getString("bookId") ?: ""
            val currentPage = it.arguments?.getString("currentPage") ?: "0"
            val currentPageInt = Integer.parseInt(currentPage)
            BookCatalogPage(navController = navController, bookName = bookId, currentPage = currentPageInt,bookCatalogViewModel = bookCatalogViewModel)
        }

        composable(RouteConfig.ROUTE_REGISTER){
            RegisterPage(userViewModel = userViewModel, navController = navController)
        }

        composable(RouteConfig.ROUTE_LOGIN){
            LoginPage(userViewModel = userViewModel, navController = navController)
        }
    }

}