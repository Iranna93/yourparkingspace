# YourParkingSpace Android Tech Test

## Scenario
You are asked to build a very simple app, which allows the user to view submissions to the r/Android subreddit. 

## What we require
You will build an app that works across a range of device sizes.

The app will load the current hot submissions from the Android subreddit. You can use [https://www.reddit.com/r/Android/hot.json](https://www.reddit.com/r/Android/hot.json) to get posts.

The posts will be displayed in a list view. 

If the user scrolls to the bottom of the list, the app should load the next page of results.

If the user taps on a post, they should be taken to that post on reddit.

Each post should give attribution to the author

## Expectations
* Well structured, reusable code that is maintainable.
* Spend no more than 5 hours completing this task.
* Regular commits to Git with meaningful messages.
* We expect you to use the latest release version of Android Studio.

## What to submit
* A bundled / archived repository showing your commit history, for example:

```git bundle create <yourname>.bundle --all --branches```

* A covering note that explains the technology choices you have made

## Bonus Points

* While you won't be assessed on your design choices, Its always good to have a nice looking app.
* Unit Tests
* Offline support / caching
