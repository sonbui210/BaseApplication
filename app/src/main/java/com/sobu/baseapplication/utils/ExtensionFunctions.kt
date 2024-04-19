package com.sobu.baseapplication.utils

import android.Manifest
import android.app.Activity
import android.app.UiModeManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfRenderer
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.sobu.baseapplication.R
import com.sobu.baseapplication.commons.Constants
import com.sobu.baseapplication.utils.SharePreference.billingRequireKey
import kotlinx.coroutines.*
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.util.*


fun Context.isInternetConnected(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setSafeOnClickListener(interval: Long = 2000, onSafeClick: (View) -> Unit) {
    var lastClickTime: Long = 0

    setOnClickListener { v ->
        val clickTime = SystemClock.elapsedRealtime()

        if (clickTime - lastClickTime < interval) {
            // Người dùng nhấn quá nhanh, tránh double click
            return@setOnClickListener
        }

        // Thực hiện công việc của bạn ở đây
        onSafeClick(v)
        lastClickTime = clickTime
    }
}

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    val snack = Snackbar.make(this, message, duration)
    snack.setTextColor(ResourcesCompat.getColor(context.resources, R.color.white, context.theme))
    snack.show()
}

fun Context.showToast(msg: String) {
    CoroutineScope(Dispatchers.Main).launch {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Context.isPermissionsGranted() = REQUIRED_PERMISSIONS_Gallery.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

val REQUIRED_PERMISSIONS_Gallery = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

fun Context.launchAnotherApplication(packageName: String) {
    val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
    if (launchIntent != null) {
        startActivity(launchIntent) //null pointer check in case package name was not found
    } else {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}

fun Context.isAppInstalled(packageName: String): Boolean {
    val pm = packageManager
    try {
        pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        return true
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    return false
}

fun String.getFileNameExtension(): String {
    return substring(lastIndexOf("."))
}

fun String.changeExtension(newExtension: String): String {
    return if (this.contains(".")) {
        val i = lastIndexOf('.')
        val name = substring(0, i)
        name + newExtension
    } else {
        this + newExtension
    }
}

fun Activity.hideStatusBar() {
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
    window.statusBarColor = Color.TRANSPARENT

//    //icon color -> black
//    val window = this.window
//    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//    window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
//    WindowCompat.getInsetsController(window, window.decorView).apply {
//        isAppearanceLightStatusBars = true
//    }
}

fun Activity.isDarkModeEnabled(context: Context): Boolean {
    val uiModeManager = this.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    return uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
}

fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val win = window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun Context.isAlreadyPurchased(): Boolean {
    val sharedPeref = this.getSharedPreferences(
        Constants.nameDataSaveApp, Context.MODE_PRIVATE
    )
    val isPremium = sharedPeref.getBoolean(billingRequireKey, false)
    SLog.d("Context.isAlreadyPurchased(): $isPremium")
    return isPremium
}

fun Context.setPurchaseStatus(isPremium: Boolean) {
    val sharedPeref = this.getSharedPreferences(
        Constants.nameDataSaveApp, Context.MODE_PRIVATE
    )
    val sharedPerefEditor = sharedPeref.edit()
    sharedPerefEditor.putBoolean(billingRequireKey, isPremium)
    sharedPerefEditor.apply()


}

private val lastToastTimes: MutableMap<String, Long> = HashMap()
fun Context.showToastOnceEvery30Seconds(msg: String) {
    val currentTime = System.currentTimeMillis()

    if (!lastToastTimes.containsKey(msg) || currentTime - lastToastTimes[msg]!! > 30000) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        }

        lastToastTimes[msg] = currentTime
    }
}

fun Context.showToastOnceEvery15Seconds(msg: String) {
    val currentTime = System.currentTimeMillis()

    if (!lastToastTimes.containsKey(msg) || currentTime - lastToastTimes[msg]!! > 15000) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        }

        lastToastTimes[msg] = currentTime
    }
}

fun Activity.sendEmail() {
    val addresses = arrayOf("sobubu.care@gmail.com")
    val subject = "Feed back " + applicationContext.getString(R.string.app_name) + ""
    val body =
        "Tell us which issues you are facing using " + applicationContext.getString(R.string.app_name) + " App?"
    try {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        emailIntent.type = "plain/text"
        emailIntent.setClassName(
            "com.google.android.gm",
            "com.google.android.gm.ComposeActivityGmail"
        )
//        emailIntent.setPackage("com.google.android.gm");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(emailIntent)
    } catch (e: Exception) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:" + addresses[0])
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        try {
            startActivity(
                Intent.createChooser(
                    emailIntent,
                    "getString(R.string.mes_send_email_using)"
                )
            )
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "getString(R.string.mes_no_email_clients_installed)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

fun Context.rateUs() {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}


fun addDelay(time: Long, onComplete: (() -> Unit)) {
    Handler(Looper.getMainLooper()).postDelayed({
        onComplete.invoke()
    }, time)
}

fun Activity.addButtonDelay(value: Long) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )

    val buttonTimer = Timer()
    buttonTimer.schedule(object : TimerTask() {
        override fun run() {
            runOnUiThread {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }, value)
}


fun Context.sizeFormatter(size: Long) =
    android.text.format.Formatter.formatShortFileSize(this, size)

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun <T> Context.openActivityWithClearTask(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

fun <T> Context.openActivityAndClearApp(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun getMonthInEng(month: Int): String {
    return when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> "Jan"
    }
}


fun Context.getDrawableResource(restId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, restId, theme)
}

fun Context.getColorResource(restId: Int): Int {
    return ResourcesCompat.getColor(resources, restId, theme)
}

fun startScopeFunction(onStart: (() -> Unit), onComplete: (() -> Unit)) {
    GlobalScope.launch(Dispatchers.Main) {
        onStart.invoke()
    }.invokeOnCompletion {
        GlobalScope.launch(Dispatchers.Main) {
            onComplete.invoke()
        }
    }
}

fun startMainScopeFunction(onStart: (() -> Unit), onComplete: (() -> Unit)? = null) {
    GlobalScope.launch(Dispatchers.Main) {
        onStart.invoke()
    }.invokeOnCompletion {
        onComplete?.invoke()
    }
}

fun Bitmap.resizeImage(): Bitmap {

    val width = width
    val height = height

    val scaleWidth = width / 10
    val scaleHeight = height / 10

    if (byteCount <= 100000000)
        return this

    return Bitmap.createScaledBitmap(this, scaleWidth, scaleHeight, false)
}

fun Activity.showKeyboardOnView(view: View) {
    view.post {
        view.requestFocus()
        val imgr = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Context.getImageUri(inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path: String = MediaStore.Images.Media.insertImage(contentResolver, inImage, "Title", null)
    return Uri.parse(path)
}

fun Context.openUrl(url: String) {
    try {
        val uri = Uri.parse(url) // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG
        ).show();
        e.printStackTrace();
    }
}

var ROTATION_FROM_ANGEL = 0f
var ROTATION_TO_ANGEL = 0f

fun View.rotateRight(): Float {
    var rotate: RotateAnimation? = null
    if (ROTATION_FROM_ANGEL == 0f && ROTATION_TO_ANGEL == 90f) {
        rotate = RotateAnimation(
            90f,
            180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 90f
        ROTATION_TO_ANGEL = 180f
    } else if (ROTATION_FROM_ANGEL == 90f && ROTATION_TO_ANGEL == 180f) {
        rotate = RotateAnimation(
            180f,
            270f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 180f
        ROTATION_TO_ANGEL = 270f
    } else if (ROTATION_FROM_ANGEL == 180f && ROTATION_TO_ANGEL == 270f) {
        rotate = RotateAnimation(
            270f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 270f
        ROTATION_TO_ANGEL = 360f
    } else if (ROTATION_FROM_ANGEL == 270f && ROTATION_TO_ANGEL == 360f) {
        rotate = RotateAnimation(
            0f,
            90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 0f
        ROTATION_TO_ANGEL = 90f
    } else if (ROTATION_FROM_ANGEL == 0f && ROTATION_TO_ANGEL == -90f) {
        rotate = RotateAnimation(
            -90f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -90f
        ROTATION_TO_ANGEL = 0f
    } else if (ROTATION_FROM_ANGEL == -90f && ROTATION_TO_ANGEL == -180f) {
        rotate = RotateAnimation(
            -180f,
            -90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -180f
        ROTATION_TO_ANGEL = -90f
    } else if (ROTATION_FROM_ANGEL == -180f && ROTATION_TO_ANGEL == -90f) {
        rotate = RotateAnimation(
            -90f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -90f
        ROTATION_TO_ANGEL = 0f
    } else if (ROTATION_FROM_ANGEL == -180f && ROTATION_TO_ANGEL == -270f) {
        rotate = RotateAnimation(
            -270f,
            -180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -270f
        ROTATION_TO_ANGEL = -180f
    } else if (ROTATION_FROM_ANGEL == 180f && ROTATION_TO_ANGEL == 90f) {
        rotate = RotateAnimation(
            90f,
            180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 90f
        ROTATION_TO_ANGEL = 180f
    } else if (ROTATION_FROM_ANGEL == -270f && ROTATION_TO_ANGEL == -180f) {
        rotate = RotateAnimation(
            -180f,
            -90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -180f
        ROTATION_TO_ANGEL = -90f
    } else if (ROTATION_FROM_ANGEL == -270f && ROTATION_TO_ANGEL == -360f) {
        rotate = RotateAnimation(
            0f,
            90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 0f
        ROTATION_TO_ANGEL = 90f
    } else {
        rotate = RotateAnimation(
            0f,
            90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 0f
        ROTATION_TO_ANGEL = 90f
    }

    rotate.apply {
        fillAfter = true
        repeatCount = 0
        duration = 500
        interpolator = LinearInterpolator()
    }
    startAnimation(rotate)

    return ROTATION_TO_ANGEL
}

fun View.rotateLeft(): Float {
    var rotate: RotateAnimation? = null
    if (ROTATION_FROM_ANGEL == 0f && ROTATION_TO_ANGEL == 90f) {
        rotate = RotateAnimation(
            90f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 90f
        ROTATION_TO_ANGEL = 0f
    } else if (ROTATION_FROM_ANGEL == 90f && ROTATION_TO_ANGEL == 180f) {
        rotate = RotateAnimation(
            180f,
            90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 180f
        ROTATION_TO_ANGEL = 90f
    } else if (ROTATION_FROM_ANGEL == 180f && ROTATION_TO_ANGEL == 270f) {
        rotate = RotateAnimation(
            270f,
            180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 270f
        ROTATION_TO_ANGEL = 180f
    } else if (ROTATION_FROM_ANGEL == 270f && ROTATION_TO_ANGEL == 180f) {
        rotate = RotateAnimation(
            180f,
            90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 180f
        ROTATION_TO_ANGEL = 90f
    } else if (ROTATION_FROM_ANGEL == 180f && ROTATION_TO_ANGEL == 90f) {
        rotate = RotateAnimation(
            90f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 90f
        ROTATION_TO_ANGEL = 0f
    } else if (ROTATION_FROM_ANGEL == 270f && ROTATION_TO_ANGEL == 360f) {
        rotate = RotateAnimation(
            360f,
            270f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 360f
        ROTATION_TO_ANGEL = 270f
    } else if (ROTATION_FROM_ANGEL == 360f && ROTATION_TO_ANGEL == 270f) {
        rotate = RotateAnimation(
            270f,
            180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 270f
        ROTATION_TO_ANGEL = 180f
    } else if (ROTATION_FROM_ANGEL == 0f && ROTATION_TO_ANGEL == -90f) {
        rotate = RotateAnimation(
            -90f,
            -180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -90f
        ROTATION_TO_ANGEL = -180f
    } else if (ROTATION_FROM_ANGEL == -90f && ROTATION_TO_ANGEL == -180f) {
        rotate = RotateAnimation(
            -180f,
            -270f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -180f
        ROTATION_TO_ANGEL = -270f
    } else if (ROTATION_FROM_ANGEL == -180f && ROTATION_TO_ANGEL == -270f) {
        rotate = RotateAnimation(
            -270f,
            -360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -270f
        ROTATION_TO_ANGEL = -360f
    } else if (ROTATION_FROM_ANGEL == -270f && ROTATION_TO_ANGEL == -360f) {
        rotate = RotateAnimation(
            0f,
            -90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 0f
        ROTATION_TO_ANGEL = -90f
    } else if (ROTATION_FROM_ANGEL == -270f && ROTATION_TO_ANGEL == -180f) {
        rotate = RotateAnimation(
            -180f,
            -90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -180f
        ROTATION_TO_ANGEL = -90f
    } else if (ROTATION_FROM_ANGEL == -180f && ROTATION_TO_ANGEL == -90f) {
        rotate = RotateAnimation(
            -90f,
            -180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = -90f
        ROTATION_TO_ANGEL = -180f
    } else {
        rotate = RotateAnimation(
            0f,
            -90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        ROTATION_FROM_ANGEL = 0f
        ROTATION_TO_ANGEL = -90f
    }

    rotate.apply {
        fillAfter = true
        repeatCount = 0
        duration = 500
        interpolator = LinearInterpolator()
    }
    startAnimation(rotate)

    return ROTATION_TO_ANGEL
}

private const val TAG = "ExtensionFunctions"

fun Context.pdfToBitmap(pdfFile: File): ArrayList<Bitmap> {
    val bitmaps: ArrayList<Bitmap> = ArrayList()
    try {
        val renderer = PdfRenderer(
            ParcelFileDescriptor.open(
                pdfFile,
                ParcelFileDescriptor.MODE_READ_ONLY
            )
        )
        var bitmap: Bitmap
        val pageCount = renderer.pageCount
        for (i in 0 until pageCount) {
            val page = renderer.openPage(i)
            val width: Int = resources.displayMetrics.densityDpi / 72 * page.width
            val height: Int = resources.displayMetrics.densityDpi / 72 * page.height
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmaps.add(bitmap)
            page.close()
        }
        renderer.close()
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    return bitmaps
}

private fun openFile(file: File?): ParcelFileDescriptor? {
    return try {
        ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }
}

fun ImageView.getBitmapFromImageView(): Bitmap {
    invalidate()
    val drawable: BitmapDrawable = drawable as BitmapDrawable
    return drawable.bitmap
}

fun loadBitmap(url: Uri?): Bitmap? {
    var bm: Bitmap? = null
    var `is`: InputStream? = null
    var bis: BufferedInputStream? = null
    try {
        val conn: URLConnection = URL(url.toString()).openConnection()
        conn.connect()
        `is` = conn.getInputStream()
        bis = BufferedInputStream(`is`, 8192)
        bm = BitmapFactory.decodeStream(bis)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (bis != null) {
            try {
                bis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (`is` != null) {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return bm
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Activity.copyToClipboard(text: String) {
    val clipboardManager =
        getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    clipboardManager.text = text
    Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
}

fun Context.share(text: String, subject: String = ""): Boolean {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}

fun Activity.changeStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.changeStatusBarWindow(color: Int) {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    window.statusBarColor = ContextCompat.getColor(this, color)
}