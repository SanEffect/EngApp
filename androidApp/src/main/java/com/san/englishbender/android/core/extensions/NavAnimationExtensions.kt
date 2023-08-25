package com.san.englishbender.android.core.extensions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

typealias enterTransitionScope = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)

typealias exitTransitionScope = (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)


fun enterTransitionRight(durationMillis: Int = 350): enterTransitionScope = {
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(durationMillis)
    )
}

fun enterTransitionLeft(durationMillis: Int = 350): enterTransitionScope = {
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(durationMillis)
    )
}

fun exitTransitionRight(durationMillis: Int = 350): exitTransitionScope = {
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(durationMillis)
    )
}

fun exitTransitionLeft(durationMillis: Int = 350): exitTransitionScope = {
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(durationMillis)
    )
}
