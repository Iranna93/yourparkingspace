package com.reddit.test.presentation.posts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.reddit.test.R
import com.reddit.test.presentation.posts.compose.PostsScreen
import com.reddit.test.presentation.posts.mvvm.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RedditPostsActivity : AppCompatActivity() {

    private val postsViewModel: PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PostsScreen.initialise(
                    currentUIStateFlow = postsViewModel.postStateFlow,
                    intentionListener = postsViewModel,
                    sideEffects = postsViewModel.sideEffects
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        postsViewModel.acceptNewIntention(PostsViewModel.PostsIntention.RequestPostsList)
    }
}