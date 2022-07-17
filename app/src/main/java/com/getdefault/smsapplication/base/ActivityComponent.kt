package com.getdefault.smsapplication.base

import com.getdefault.smsapplication.application.ApplicationComponent
import dagger.Component


@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

}