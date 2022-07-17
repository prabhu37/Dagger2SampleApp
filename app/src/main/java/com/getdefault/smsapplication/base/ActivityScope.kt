package com.getdefault.smsapplication.base

import javax.inject.Scope


//@Scope as a mechanism to handle scoping.
// Scoping allows you to “preserve” the object instance and
// provide it as a “local singleton” for the duration of the scoped component.
// It provides your dependencies as a singleton as long as you are using the same component

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope