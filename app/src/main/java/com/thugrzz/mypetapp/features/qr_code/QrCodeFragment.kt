package com.thugrzz.mypetapp.features.qr_code

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtQrCodeBinding
import com.thugrzz.mypetapp.ext.collectNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class QrCodeFragment : Fragment(R.layout.fmt_qr_code) {

    private val binding by viewBinding(FmtQrCodeBinding::bind)
    private val viewModel by viewModel<QrCodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val writer = QRCodeWriter()

        val bitMatrix = writer.encode("ya.ru\nhello", BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        generateButton.setOnClickListener {
            viewModel.onGenerateClick()
        }

        collectNotNull(viewModel.qrCodeFlow, ::bindQrCode)
    }

    private fun bindQrCode(bitmap: Bitmap) {
        binding.qrCodeView.setImageBitmap(bitmap)
    }

    companion object {

        val TAG = QrCodeFragment::class.simpleName!!

        fun newInstance() = QrCodeFragment()
    }
}