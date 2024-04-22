package com.chanyoung.jack.application

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LinkInGroupProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SearchInGroupProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SearchProvider


