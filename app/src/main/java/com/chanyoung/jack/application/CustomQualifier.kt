package com.chanyoung.jack.application

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LinkInGroupProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SearchProvider

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class GroupId
