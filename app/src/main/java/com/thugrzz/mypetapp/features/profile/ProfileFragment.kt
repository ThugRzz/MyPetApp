package com.thugrzz.mypetapp.features.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.CropAvatar
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.data.model.local.Sex
import com.thugrzz.mypetapp.data.model.local.Source
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.data.model.remote.PetType
import com.thugrzz.mypetapp.databinding.FmtProfileBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.features.dialog.AlertDialogFragment
import com.thugrzz.mypetapp.features.dialog.FileSourceDialogFragment
import com.thugrzz.mypetapp.features.main.MainFragment
import com.thugrzz.mypetapp.features.profile.dialog.ProfileMenuDialog
import com.thugrzz.mypetapp.features.profile.profile_edit.ProfileEditFragment
import com.yalantis.ucrop.UCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class ProfileFragment : Fragment(R.layout.fmt_profile),
    FileSourceDialogFragment.Callback,
    AlertDialogFragment.Callback {

    private val binding by viewBinding(FmtProfileBinding::bind)

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()

        menuIconView.setOnClickListener {
            ProfileMenuDialog.show(parentFragmentManager)
        }
        editFabView.setOnClickListener {
            (requireParentFragment() as MainFragment)
                .pushFragment(ProfileEditFragment.newInstance())
        }

        editAvatarView.setOnClickListener {
            Log.e("awdawd","wadwa")
            startFileChooser()
        }
        avatarContainer.setOnClickListener { startFileChooser() }

        collect(viewModel.petNameFlow, collapsingToolbarLayout::setTitle)
        collect(viewModel.petTypeFlow, ::bindType)
        collect(viewModel.petBreedFlow, ::bindBreed)
        collect(viewModel.petSexFlow, ::bindSex)
        collect(viewModel.petStatusFlow, ::bindStatus)
        collect(viewModel.petHeightFlow, ::bindHeight)
        collect(viewModel.startCameraActionsFlow, ::startCameraActivity)
        collect(viewModel.cropImageActionFlow, ::navigateToCropImage)
        collect(viewModel.avatarUrlFlow, ::bindAvatar)
        collect(viewModel.petWeightFlow, ::bindWeight)
        collect(viewModel.isLoadingFlow, progressView::isVisible::set)
        collect(viewModel.errorActionFlow) {
            Toast.makeText(requireContext(), R.string.error_data_failed, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAvatar()
    }

    override fun onFileSourceChosen(source: Source) {
        when (source) {
            Source.CAMERA -> checkCameraPermission()
            Source.STORAGE -> startStorageActivity()
        }
    }

    override fun onAlertDialogAccepted(requestCode: Int) {
        when (requestCode) {
            REQUEST_DIALOG_CAMERA_PERMISSION -> {
                requestCameraPermissions()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCameraPermissionGranted()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    onCameraSuccess()
                }
            }
            REQUEST_PICK_FILE -> {
                data?.data?.let { uri ->
                    onFilePicked(uri)
                }
            }
            UCrop.REQUEST_CROP -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleUCropResult(data)
                } else {
                    setResultCancelled()
                }
            }
            UCrop.RESULT_ERROR -> {
                setResultCancelled()
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
                setResultCancelled()
            }
        }
    }

    private fun bindAvatar(url: String) {
        val placeHolder = ContextCompat.getDrawable(requireContext(), R.drawable.ic_happy)
        Glide.with(requireContext())
            .load(url)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(binding.profileIconView)

        Glide.with(requireContext())
            .load(url)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(binding.expandedImageView)
    }

    private fun handleUCropResult(data: Intent?) {
        if (data == null) {
            setResultCancelled()
            return
        }
        viewModel.changeAvatar()
    }

    private fun setResultCancelled() {
    }

    private fun startFileChooser() = FileSourceDialogFragment.show(this)

    private fun checkCameraPermission() {
        val permission = Manifest.permission.CAMERA
        when {
            ContextCompat.checkSelfPermission(requireContext(), permission) ==
                    PackageManager.PERMISSION_GRANTED -> {
                onCameraPermissionGranted()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                AlertDialogFragment.show(
                    requestCode = REQUEST_DIALOG_CAMERA_PERMISSION,
                    target = this,
                    messageId = R.string.permission_rationale_camera,
                )
            }
            else -> requestCameraPermissions()
        }
    }

    private fun requestCameraPermissions() =
        requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)

    private fun startStorageActivity() {
        try {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = MIME_IMAGES
            }
            startActivityForResult(intent, REQUEST_PICK_FILE)
        } catch (e: Throwable) {
            Toast.makeText(requireContext(), R.string.error_file_pick, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCameraActivity(uri: Uri) {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, REQUEST_CAMERA)
        } catch (e: Throwable) {
            Toast.makeText(requireContext(), R.string.error_file_camera, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCameraPermissionGranted() {
        viewModel.onCameraSourceChosen()
    }

    private fun onCameraSuccess() {
        viewModel.onCameraSuccess()
    }

    private fun onFilePicked(uri: Uri) {
        viewModel.onFilePicked(uri)
    }

    private fun navigateToCropImage(cropAvatar: CropAvatar) {
        val options = UCrop.Options()
        options.setCompressionQuality(IMAGE_COMPRESSION)
        options.setStatusBarColor(Color.TRANSPARENT)
        options.setToolbarTitle("")
        options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y)
        options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight)
        UCrop.of(cropAvatar.sourceImageUri, cropAvatar.croppedImageUri)
            .withOptions(options)
            .start(requireContext(), this)
    }

    private fun bindType(petType: PetType) = with(binding.profileContentView) {
        topParamsView.leftValue = petType.type
    }

    private fun bindBreed(breed: PetBreed) = with(binding.profileContentView) {
        topParamsView.rightValue = breed.name
    }

    private fun bindSex(sex: Sex) = with(binding.profileContentView) {
        middleParamsView.leftValue = getString(sex.titleId)
    }

    private fun bindStatus(status: PetStatus) = with(binding.profileContentView) {
        middleParamsView.rightValue = getString(status.titleId)
    }

    private fun bindHeight(height: Double) = with(binding.profileContentView) {
        val stringHeight = getString(R.string.pet_height_param, height)
        bottomParamsView.leftValue = stringHeight
    }

    private fun bindWeight(weight: Double) = with(binding.profileContentView) {
        val stringWeight = getString(R.string.pet_weight_param, weight)
        bottomParamsView.rightValue = stringWeight
    }

    private fun setupAppBar() = with(binding) {
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val currentRatio = calculateOffsetRatio(verticalOffset, totalScrollRange)
            profileIconView.alpha = currentRatio
            avatarContainer.alpha = currentRatio
            editAvatarView.alpha = currentRatio
            val mainContentPadding = resources.getDimensionPixelSize(R.dimen.padding_32)
            val currentPadding = calculateCollapsedPadding(mainContentPadding, currentRatio)
            profileContentView.root.setPadding(0, currentPadding, 0, 0)

            setupToolbarIcons(currentRatio)
        })
    }

    private fun setupToolbarIcons(ratio: Float) {
        with(binding) {
            val isRatioZero = ratio == 0f
            menuIconView.visibility = if (isRatioZero) View.VISIBLE else View.INVISIBLE
            toolbarView.navigationIcon?.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    if (isRatioZero) R.color.colorAccent else R.color.white
                )
            )
        }
    }

    private fun calculateOffsetRatio(offset: Int, totalScrollRange: Int) =
        MAX_RATIO + (offset.toFloat() / totalScrollRange)

    private fun calculateCollapsedPadding(padding: Int, ratio: Float) =
        (padding * ratio).roundToInt()

    companion object {

        private const val ASPECT_RATIO_X = 1f
        private const val ASPECT_RATIO_Y = 1f
        private const val bitmapMaxWidth = 300
        private const val bitmapMaxHeight = 300
        private const val IMAGE_COMPRESSION = 95
        private const val REQUEST_PERMISSION_CAMERA = 1
        private const val REQUEST_PICK_FILE = 1
        private const val REQUEST_CAMERA = 2
        private const val REQUEST_DIALOG_CAMERA_PERMISSION = 3
        private const val MIME_IMAGES = "image/*"
        private const val MAX_RATIO = 1

        val TAG = ProfileFragment::class.simpleName!!

        fun newInstance() = ProfileFragment()
    }
}