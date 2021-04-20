package com.thugrzz.mypetapp.ext

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.thugrzz.mypetapp.features.main.MainFragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Fragment.getColorCompat(color: Int) = context?.getColorCompat(color)

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun Fragment.getMainFragment() = requireParentFragment() as MainFragment

fun DialogFragment.showIfNotShowing(fragmentManager: FragmentManager, tag: String) {
    if (fragmentManager.findFragmentByTag(tag) == null) {
        show(fragmentManager, tag)
    }
}

fun FragmentManager.clearBackStack() {
    if (backStackEntryCount > 0) {
        val fragment = getBackStackEntryAt(0)
        popBackStack(fragment.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

fun Fragment.ensureArguments(): Bundle {
    var arguments = this.arguments
    if (arguments == null) {
        arguments = Bundle()
        this.arguments = arguments
    }
    return arguments
}

fun intArgument() = object : ReadWriteProperty<Fragment, Int> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Int =
        thisRef.requireArguments().getInt(property.name)

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Int) =
        thisRef.ensureArguments().putInt(property.name, value)
}

fun floatArgument() = object : ReadWriteProperty<Fragment, Float> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Float =
        thisRef.requireArguments().getFloat(property.name)

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Float) =
        thisRef.ensureArguments().putFloat(property.name, value)
}

fun longArgument() = object : ReadWriteProperty<Fragment, Long> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Long =
        thisRef.requireArguments().getLong(property.name)

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Long) =
        thisRef.ensureArguments().putLong(property.name, value)
}

fun stringArgument() = object : ReadWriteProperty<Fragment, String> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): String =
        thisRef.requireArguments().getString(property.name, null)!!

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: String) =
        thisRef.ensureArguments().putString(property.name, value)
}

fun nullableStringArgument() = object : ReadWriteProperty<Fragment, String?> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): String? =
        thisRef.requireArguments().getString(property.name, null)

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: String?) =
        thisRef.ensureArguments().putString(property.name, value)
}

fun boolArgument() = object : ReadWriteProperty<Fragment, Boolean> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Boolean =
        thisRef.requireArguments().getBoolean(property.name)

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Boolean) =
        thisRef.ensureArguments().putBoolean(property.name, value)
}