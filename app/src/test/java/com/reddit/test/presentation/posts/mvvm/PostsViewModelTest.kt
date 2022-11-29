@file:OptIn(ExperimentalCoroutinesApi::class)

package com.reddit.test.presentation.posts.mvvm

import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.domain.sealed.DomainErrorResponse
import com.reddit.test.domain.sealed.DomainSealedResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostsViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun prepareMockData(): List<DomainPostModel> {
        return listOf(
            DomainPostModel(
                title = "Honor 80 Pro and Honor 80 debut with 160MP main cams, 66W charging",
                ups = 4,
                url = "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg",
                thumbnail = "https://b.thumbs.redditmedia.com/nBuyBdICaCRalbLonEZW8B7oX1Fm248sV3apdLKEdBw.jpg",
                thumbnailHeight = 60,
                thumbnailWidth = 130,
                saved = false,
                numComments = 20,
                isVideo = false,
                downs = 30,
                author = "t2_zt423",
                subreddit = "Android",
                selftextHtml = "Note 1. Check [MoronicMondayAndroid](https://old.reddit.com/r/Android/search?q=MoronicMondayAndroid&amp;restrict_sr=on&amp;sort=new&amp;t=all), which serves as a repository for our retired weekly threads. Just pick any thread and Ctrl-F your way to wisdom! \\n\\nNote 2. Join our IRC and Telegram chat-rooms! [Please see our wiki for instructions.](https://www.reddit.com/r/Android/wiki/index#wiki_.2Fr.2Fandroid_chat_rooms)\\n\\nPlease post your questions here. Feel free to use this thread for general questions/discussion as well.\\n\\nThe /r/Android wiki now has a list of recommended phones and covers most areas, the links have been added below. Any suggestions or changes are welcome. Please [contact us](https://www.reddit.com/message/compose/?to=/r/Android) if you would like to help maintain this section. \\n\\n[Entry level (most affordable devices costing under \$250 (US) / \$325 (Canada) / €200 (Europe) / ₹12,500 (India)](https://www.reddit.com/r/Android/wiki/affordable)\\n\\n[Midrange section, covering the \$250-500 (US) / \$300-700 (Canada) / €200-500 (Europe)  / ₹12,500-30,000 segment](https://",
                selftext = "",
                created = 19872372
            ),
            DomainPostModel(
                title = "Honor 80 Pro and Honor 80 160MP main cams, 66W charging",
                ups = 4,
                url = "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg",
                thumbnail = "https://b.thumbs.redditmedia.com/nBuyBdICaCRalbLonEZW8B7oX1Fm248sV3apdLKEdBw.jpg",
                thumbnailHeight = 60,
                thumbnailWidth = 130,
                saved = false,
                numComments = 20,
                isVideo = false,
                downs = 30,
                author = "t2_zt423",
                subreddit = "Android",
                selftextHtml = "Note 1. Check [MoronicMondayAndroid](https://old.reddit.com/r/Android/search?q=MoronicMondayAndroid&amp;restrict_sr=on&amp;sort=new&amp;t=all), which serves as a repository for our retired weekly threads. Just pick any thread and Ctrl-F your way to wisdom! \\n\\nNote 2. Join our IRC and Telegram chat-rooms! [Please see our wiki for instructions.](https://www.reddit.com/r/Android/wiki/index#wiki_.2Fr.2Fandroid_chat_rooms)\\n\\nPlease post your questions here. Feel free to use this thread for general questions/discussion as well.\\n\\nThe /r/Android wiki now has a list of recommended phones and covers most areas, the links have been added below. Any suggestions or changes are welcome. Please [contact us](https://www.reddit.com/message/compose/?to=/r/Android) if you would like to help maintain this section. \\n\\n[Entry level (most affordable devices costing under \$250 (US) / \$325 (Canada) / €200 (Europe) / ₹12,500 (India)](https://www.reddit.com/r/Android/wiki/affordable)\\n\\n[Midrange section, covering the \$250-500 (US) / \$300-700 (Canada) / €200-500 (Europe)  / ₹12,500-30,000 segment](https://",
                selftext = "",
                created = 19872372
            ),
            DomainPostModel(
                title = "Honor 80 Pro and Honor 80 debut with 160MP",
                ups = 4,
                url = "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg",
                thumbnail = "https://b.thumbs.redditmedia.com/nBuyBdICaCRalbLonEZW8B7oX1Fm248sV3apdLKEdBw.jpg",
                thumbnailHeight = 60,
                thumbnailWidth = 130,
                saved = false,
                numComments = 20,
                isVideo = false,
                downs = 30,
                author = "t2_zt423",
                subreddit = "Android",
                selftextHtml = "Note 1. Check [MoronicMondayAndroid](https://old.reddit.com/r/Android/search?q=MoronicMondayAndroid&amp;restrict_sr=on&amp;sort=new&amp;t=all), which serves as a repository for our retired weekly threads. Just pick any thread and Ctrl-F your way to wisdom! \\n\\nNote 2. Join our IRC and Telegram chat-rooms! [Please see our wiki for instructions.](https://www.reddit.com/r/Android/wiki/index#wiki_.2Fr.2Fandroid_chat_rooms)\\n\\nPlease post your questions here. Feel free to use this thread for general questions/discussion as well.\\n\\nThe /r/Android wiki now has a list of recommended phones and covers most areas, the links have been added below. Any suggestions or changes are welcome. Please [contact us](https://www.reddit.com/message/compose/?to=/r/Android) if you would like to help maintain this section. \\n\\n[Entry level (most affordable devices costing under \$250 (US) / \$325 (Canada) / €200 (Europe) / ₹12,500 (India)](https://www.reddit.com/r/Android/wiki/affordable)\\n\\n[Midrange section, covering the \$250-500 (US) / \$300-700 (Canada) / €200-500 (Europe)  / ₹12,500-30,000 segment](https://",
                selftext = "",
                created = 19872372
            ),

            )
    }

    private fun verifyErrorCakesData(
        mockCakeData: List<DomainPostModel>,
        resultData: List<DomainPostModel>,
    ) {
        assertEquals(3, resultData.size)
        assertEquals(mockCakeData[0].title, resultData[0].title)
        assertEquals(mockCakeData[0].numComments, resultData[0].numComments)
        assertEquals(mockCakeData[0].created, resultData[0].created)
    }

    @Test
    fun cakeViewModel_RequestCakeList_Success() = runTest {
        val modelPostsMock = mockk<PostsModel>()
        val mockData = prepareMockData()
        val testScope = CoroutineScope(dispatcher)

        coEvery {
            modelPostsMock.getPosts()
        } returns DomainSealedResponse.Success(data = mockData)

        val viewModel = PostsViewModel(
            postsModel = modelPostsMock
        )

        var initialResultList: List<PostsViewModel.CurrentUIState>? = null

        testScope.launch {
            viewModel.acceptNewIntention(
                intention = PostsViewModel.PostsIntention.RequestPostsList
            )
            initialResultList = viewModel.postStateFlow.take(2).toList()
        }.join()
        assertEquals(true, initialResultList?.isNotEmpty())

        initialResultList?.let { resultsList ->
            assertEquals(
                true,
                resultsList[0] is PostsViewModel.CurrentUIState.NotLoaded
            )
            assertEquals(true, resultsList[1] is PostsViewModel.CurrentUIState.ShowPostsList)

            val resultData =
                resultsList[1] as PostsViewModel.CurrentUIState.ShowPostsList

            verifyErrorCakesData(
                mockCakeData = mockData,
                resultData = resultData.data,
            )
        }
    }

    @Test
    fun cakeViewModel_RequestCakeList_NotLoaded() = runTest {
        val modelPostsMock = mockk<PostsModel>()
        val testScope = CoroutineScope(dispatcher)

        coEvery {
            modelPostsMock.getPosts()
        } returns DomainSealedResponse.Success(data = emptyList())

        val viewModel = PostsViewModel(
            postsModel = modelPostsMock
        )

        var initialResultList: PostsViewModel.CurrentUIState? = null

        testScope.launch {
            viewModel.acceptNewIntention(
                intention = PostsViewModel.PostsIntention.RequestPostsList
            )
            initialResultList = viewModel.postStateFlow.value
        }.join()

        assertEquals(true, initialResultList is PostsViewModel.CurrentUIState.NotLoaded)
    }

    @Test
    fun cakeViewModel_RequestCakeList_Error() = runTest {
        val modelPostsMock = mockk<PostsModel>()
        val testScope = CoroutineScope(dispatcher)

        coEvery {
            modelPostsMock.getPosts()
        } returns DomainSealedResponse.Error(
            data = emptyList(),
            error = DomainErrorResponse(errorMessage = "Testing", errorCode = 11)
        )

        val viewModel = PostsViewModel(
            postsModel = modelPostsMock
        )

        var initialResultList: List<PostsViewModel.CurrentUIState>? = null

        testScope.launch {
            viewModel.acceptNewIntention(
                intention = PostsViewModel.PostsIntention.RequestPostsList
            )
            initialResultList = viewModel.postStateFlow.take(2).toList()
        }.join()
        assertEquals(true, initialResultList?.isNotEmpty())

        initialResultList?.let { resultsList ->
            assertEquals(
                true,
                resultsList[0] is PostsViewModel.CurrentUIState.NotLoaded
            )
            assertEquals(
                true,
                resultsList[1] is PostsViewModel.CurrentUIState.Error
            )
        }
    }
}