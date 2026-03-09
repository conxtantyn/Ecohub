package com.ecohub.ui.design

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

const val DEFAULT_NAVIGATION_DURATION = 250

@Composable
fun <S> DesignNavigation(
    targetState: S,
    transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform = {
        designDefaultNavigationEntry() togetherWith designDefaultNavigationExit()
    },
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    label: String = "DesignNavigation",
    contentKey: (targetState: S) -> Any? = { it },
    content: @Composable() AnimatedContentScope.(targetState: S) -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = transitionSpec,
        contentAlignment = contentAlignment,
        label = label,
        contentKey = contentKey,
        content = content
    )
}

private fun defaultNavigationTransition(): ContentTransform {
    return fadeIn(tween(220)) togetherWith
            fadeOut(tween(180))
}

private fun designDefaultNavigationEntry(
    duration: Int = 250
): EnterTransition =
    EnterTransition.None + fadeIn(animationSpec = tween(duration))

private fun designDefaultNavigationExit(
    duration: Int = 250
): ExitTransition =
    ExitTransition.None + fadeOut(animationSpec = tween(duration))

@Composable
fun <S : Screen?> DesignScreenNavigation(
    targetState: S,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    label: String = "DesignNavigation",
    contentKey: (targetState: S) -> Any? = { it },
    content: @Composable() AnimatedContentScope.(targetState: S) -> Unit,
) {
    DesignNavigation(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            val targetIndex = navigator.items.indexOf(targetState as? Screen)
                .takeIf { it >= 0 } ?: return@DesignNavigation defaultNavigationTransition()
            val initialIndex = navigator.items.indexOf(initialState as? Screen)
                .takeIf { it >= 0 } ?: Int.MAX_VALUE
            val isForward = targetIndex > initialIndex
            if (isForward) {
                forwardEntry() togetherWith backwardExit()
            } else {
                backwardEntry() togetherWith forwardExit()
            }.using(SizeTransform(clip = false))
        },
        contentAlignment = contentAlignment,
        label = label,
        contentKey = contentKey,
        content = content
    )
}

private fun forwardEntry() =
    slideInHorizontally(
        initialOffsetX = { (it * 0.38f).toInt() },
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, easing = FastOutSlowInEasing)
    ) + fadeIn(
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, delayMillis = 60)
    ) + scaleIn(
        initialScale = 0.95f,
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, easing = FastOutSlowInEasing)
    )

private fun backwardEntry() =
    slideInHorizontally(
        initialOffsetX = { -(it * 0.38f).toInt() },
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, easing = FastOutSlowInEasing)
    ) + fadeIn(
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, delayMillis = 60)
    ) + scaleIn(
        initialScale = 0.95f,
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, easing = FastOutSlowInEasing)
    )

private fun forwardExit() =
    slideOutHorizontally(
        targetOffsetX = { (it * 0.22f).toInt() },
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, easing = FastOutSlowInEasing)
    ) + fadeOut(
        animationSpec = tween((DEFAULT_NAVIGATION_DURATION * 0.85f).toInt())
    ) + scaleOut(
        targetScale = 0.97f,
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION)
    )

private fun backwardExit() =
    slideOutHorizontally(
        targetOffsetX = { -(it * 0.22f).toInt() },
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION, easing = FastOutSlowInEasing)
    ) + fadeOut(
        animationSpec = tween((DEFAULT_NAVIGATION_DURATION * 0.85f).toInt())
    ) + scaleOut(
        targetScale = 0.97f,
        animationSpec = tween(DEFAULT_NAVIGATION_DURATION)
    )
