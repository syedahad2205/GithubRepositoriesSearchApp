package com.syedcodes.githubreposearch.ui.theme.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.syedcodes.githubreposearch.domain.model.RepoListing
import com.syedcodes.githubreposearch.presentation.repo_info.RepoInfoScreen
import com.syedcodes.githubreposearch.presentation.repo_info.WebViewScreen
import com.syedcodes.githubreposearch.presentation.repo_listings.RepoListingScreen

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            RepoListingScreen(
                onRepoClick = {
                    Log.d("***", "onRepoClick: $it")
                    navController.navigate(
                        "${Routes.REPO_INFO}?" +
                                "id=${it.id}&" +
                                "githubRepoName=${it.githubRepoName}&" +
                                "repoOwnerName=${it.repoOwnerName}&" +
                                "projectUrl=${it.projectUrl ?: ""}&" +
                                "ownerImageLink=${it.ownerImageLink ?: ""}&" +
                                "description=${it.description ?: ""}&" +
                                "contributorsURL=${it.contributorsURL ?: ""}"
                    )
                }
            )
        }
        composable(
            route = "${Routes.REPO_INFO}?" +
                    "id={id}&" +
                    "githubRepoName={githubRepoName}&" +
                    "repoOwnerName={repoOwnerName}&" +
                    "projectUrl={projectUrl}&" +
                    "ownerImageLink={ownerImageLink}&" +
                    "description={description}&" +
                    "contributorsURL={contributorsURL}",
            arguments = listOf(
                navArgument("id") {type = NavType.IntType},
                navArgument("githubRepoName") {type = NavType.StringType},
                navArgument("repoOwnerName") {type = NavType.StringType},
                navArgument("projectUrl") {type = NavType.StringType},
                navArgument("ownerImageLink") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("contributorsURL") {type = NavType.StringType}
            )
        ) {
            val repo = RepoListing(
                id = it.arguments?.getInt("id") ?: 0,
                githubRepoName = it.arguments?.getString("githubRepoName") ?: "",
                repoOwnerName = it.arguments?.getString("repoOwnerName") ?: "",
                projectUrl = it.arguments?.getString("projectUrl") ?: "",
                ownerImageLink = it.arguments?.getString("ownerImageLink") ?: "",
                description = it.arguments?.getString("description") ?: "",
                contributorsURL = it.arguments?.getString("contributorsURL") ?: ""
            )
            RepoInfoScreen(
                repo = repo,
                onProjectLinkClick = {link ->
                    navController.navigate("${Routes.WEB_VIEW}?url=$link")
                }
            )
        }

        composable(
            route = "${Routes.WEB_VIEW}?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url)
        }
    }
}