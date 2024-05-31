package com.syedcodes.githubreposearch.presentation.repo_info

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.syedcodes.githubreposearch.R
import com.syedcodes.githubreposearch.domain.model.Contributor
import com.syedcodes.githubreposearch.domain.model.RepoListing
@Composable
fun RepoInfoScreen(
    repo: RepoListing,
    modifier: Modifier = Modifier,
    viewModel: RepoInfoViewModel = hiltViewModel(),
    onProjectLinkClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFAFAFA)) // Light background color
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(if (repo.ownerImageLink.isNullOrEmpty()) "-" else repo.ownerImageLink)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp)) // Rounded square image
                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = repo.githubRepoName,
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF333333)), // Darker text color
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Owner: ${repo.repoOwnerName}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF666666)) // Gray text color
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (repo.description.isNullOrEmpty()) "Description not available" else repo.description,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF444444)), // Medium dark text color
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (!repo.projectUrl.isNullOrEmpty())
                        onProjectLinkClick(repo.projectUrl)
                    else {
                        Toast.makeText(context, "Project link not available", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "View Project", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (!repo.contributorsURL.isNullOrEmpty())
                        viewModel.onEvent(RepoInfoEvent.LoadContributors(repo.contributorsURL))
                    else {
                        Toast.makeText(context, "No Contributors", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "View Contributors", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.state.contributors.size) { index ->
                    ContributorItem(viewModel.state.contributors[index])
                }
            }
        }
    }
}

@Composable
fun ContributorItem(contributor: Contributor) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .shadow(1.dp, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(contributor.avatar_url)
                .crossfade(true)
                .placeholder(R.drawable.ic_launcher_foreground)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = contributor.login,
                style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF333333)),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Contributions: ${contributor.contributions}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666))
            )
        }
    }
}

@Preview
@Composable
private fun RepoInfoScreenPreview() {
    RepoInfoScreen(
        repo = RepoListing(
            id = null,
            githubRepoName = "Carl French",
            repoOwnerName = "Bobby Summers",
            projectUrl = null,
            ownerImageLink = null,
            description = null,
            contributorsURL = null
        )
    ) {

    }
}