package com.thugrzz.mypetapp.features.qr_code

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.thugrzz.mypetapp.arch.BaseViewModel
import com.thugrzz.mypetapp.data.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class QrCodeViewModel(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    private val innerUserIdFlow = MutableStateFlow<Long?>(null)

    val qrCodeFlow = innerUserIdFlow.mapNotNull(::generateQrCode)

    fun onGenerateClick() {
        viewModelScope.launch {
            innerUserIdFlow.emit(preferencesRepository.getUserId())
        }
    }

    private fun generateQrCode(userId: Long?): Bitmap? {
        if (userId != null) {
            val writer = QRCodeWriter()

            val bitMatrix = writer.encode(
                "https://mypetappdiploma.herokuapp.com/${userId}",
                BarcodeFormat.QR_CODE,
                512,
                512
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bmp
        } else return null
    }
}