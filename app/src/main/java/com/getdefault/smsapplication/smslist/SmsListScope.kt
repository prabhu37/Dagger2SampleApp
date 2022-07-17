package com.getdefault.smsapplication.smslist

import javax.inject.Scope

//Dagger 2 provides @Scope as a mechanism to handle scoping.
// Scoping allows you to “preserve” the object instance and
// provide it as a “local singleton” for the duration of the scoped component
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class SmsListScope
