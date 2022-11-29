package com.reddit.test.presentation.posts.compose

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.reddit.test.R
import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.presentation.posts.mvvm.PostsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

object PostsScreen {
    @Composable
    fun initialise(
        currentUIStateFlow: StateFlow<PostsViewModel.CurrentUIState>,
        intentionListener: PostActionListener,
        sideEffects: Flow<PostsViewModel.SideEffects>
    ) {
        currentUIStateFlow.collectAsState().value.let { currentUIState ->
            when (currentUIState) {
                is PostsViewModel.CurrentUIState.Loading -> {
                    displayLoadingView()
                }
                is PostsViewModel.CurrentUIState.ShowPostsList -> {
                    showPosts(
                        data = currentUIState.data,
                        intentionListener = intentionListener
                    )
                }
                is PostsViewModel.CurrentUIState.Error -> {
                    displayErrorView(errorMessage = currentUIState.errorMessages)
                }
                is PostsViewModel.CurrentUIState.NotLoaded -> {
                    displayEmptyView()
                }
            }
        }
        val context = LocalContext.current
        LaunchedEffect(key1 = "SideEffects", block = {
            sideEffects.onEach {
                if (it is PostsViewModel.SideEffects.NavigateToPostDetails) {
                    Toast.makeText(context, "Navigating to ${it.url}", Toast.LENGTH_SHORT).show()
                }
            }.collect()
        })
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun showPosts(
        data: List<DomainPostModel>,
        intentionListener: PostActionListener
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(data.size) { item ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                ) {
                    data[item]?.let { post ->
                        Row(
                            verticalAlignment = Alignment.Top,
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                                    contentDescription = "ups"
                                )
                                val count = post.ups + post.downs
                                Text(text = "$count")
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                                    contentDescription = "Downs"
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        intentionListener.acceptNewIntention(
                                            PostsViewModel.PostsIntention.RequestPostDetailsPage(
                                                post.url
                                            )
                                        )
                                    },
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.place_holder_author,
                                        post.author
                                    ),
                                    textAlign = TextAlign.Start,
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .padding(start = 5.dp, bottom = 5.dp)
                                )

                                Text(
                                    text = post.title,
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 5.dp, bottom = 5.dp)
                                )
                                if (post.isVideo) {
                                    //Displaying video view
                                }
                                post.thumbnail?.let {
                                    AsyncImage(
                                        model = Uri.parse(it),
                                        placeholder = painterResource(id = R.drawable.ic_baseline_image_24),
                                        contentDescription = data[item].title,
                                        contentScale = ContentScale.Inside,
                                        alignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxWidth(post.thumbnailWidth.toFloat())
                                            .fillMaxHeight(post.thumbnailWidth.toFloat())
                                    )
                                }
                                Text(
                                    text =
                                    post.selftextHtml?.let {
                                        HtmlCompat.fromHtml(
                                            it,
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    } ?: kotlin.run {
                                        HtmlCompat.fromHtml(
                                            post.selftext,
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    maxLines = 20,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start,
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .padding(start = 5.dp, bottom = 5.dp)
                                )
                                Row {
                                    Row() {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_comment_24),
                                            contentDescription = "",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.size(3.dp))
                                        Text(
                                            text = "${post.numComments} comments",
                                            fontSize = 14.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(2.dp))
                                    Row() {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_ios_share_24),
                                            contentDescription = "",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.size(3.dp))

                                        Text(
                                            text = "share",
                                            fontSize = 14.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(1.5.dp))
                                    Row() {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_save_24),
                                            contentDescription = "",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.size(3.dp))
                                        Text(
                                            text = "Save",
                                            fontSize = 14.sp,
                                            color = Color.Gray,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Divider()
            }
        }

        listState.OnBottomReached {
            // do on load more
            intentionListener.acceptNewIntention(PostsViewModel.PostsIntention.RequestPostsList)
        }
    }

    @Composable
    fun LazyListState.OnBottomReached(
        loadMore: () -> Unit
    ) {
        val shouldLoadMore = remember {
            derivedStateOf {
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                    ?: return@derivedStateOf true

                lastVisibleItem.index == layoutInfo.totalItemsCount - 1
            }
        }

        // Convert the state into a cold flow and collect
        LaunchedEffect(shouldLoadMore) {
            snapshotFlow { shouldLoadMore.value }.collect {
                if (it) loadMore()
            }
        }
    }

    @Composable
    fun displayEmptyView() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_perm_device_information_24),
                contentDescription = stringResource(id = R.string.message_not_loaded),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))

            Text(text = stringResource(id = R.string.message_not_loaded))
        }
    }

    @Composable
    fun displayLoadingView() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.label_loading))
        }
    }

    @Composable
    fun displayErrorView(errorMessage: String?) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_error_24),
                contentDescription = stringResource(id = R.string.message_not_loaded),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))

            errorMessage?.let { Text(text = it) }
        }
    }
}