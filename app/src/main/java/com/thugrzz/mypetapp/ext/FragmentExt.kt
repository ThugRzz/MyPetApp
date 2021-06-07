package com.thugrzz.mypetapp.ext

import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.thugrzz.mypetapp.features.main.MainFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Fragment.pixelsOf(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Fragment.intPixelsOf(dp: Float) =
    pixelsOf(dp).roundToInt()

inline fun <T> Fragment.collect(
    source: Flow<T>,
    crossinline consumer: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        source.collect {
            consumer(it)
        }
    }
}

inline fun <T> Fragment.collectNotNull(
    source: Flow<T?>,
    crossinline consumer: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        source.mapNotNull { it }
            .collect {
                consumer(it)
            }
    }
}

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

fun <T : Parcelable> parcelableArgument() = object : ReadWriteProperty<Fragment, T> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        thisRef.requireArguments().getParcelable(property.name)!!

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) =
        thisRef.ensureArguments().putParcelable(property.name, value)
}

fun <T : Parcelable> nullableParcelableArgument() = object : ReadWriteProperty<Fragment, T?> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? =
        thisRef.arguments?.getParcelable(property.name) as T?

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) =
        thisRef.ensureArguments().putParcelable(property.name, value)
}

inline fun <reified T : Enum<*>> enumArgument() = object : ReadWriteProperty<Fragment, T> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val name = thisRef.requireArguments().getString(property.name)!!
        return enumValueOf(name)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) =
        thisRef.ensureArguments().putString(property.name, value.name)
}

fun <T : Parcelable> parcelableListArgument() = object : ReadWriteProperty<Fragment, List<T>> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): List<T> =
        thisRef.requireArguments().getParcelableArrayList(property.name)!!

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<T>) =
        thisRef.ensureArguments().putParcelableArrayList(property.name, ArrayList(value))
}
