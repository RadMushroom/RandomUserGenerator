package example.com.randomusergenerator.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import example.com.randomusergenerator.R
import example.com.randomusergenerator.domain.RandomUser
import kotlinx.android.synthetic.main.activity_user_details.*
import java.text.SimpleDateFormat
import java.util.*

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        val bundle = intent.getBundleExtra("bundle")
        val receivedUser = bundle.getParcelable<RandomUser>("user")
        Glide.with(this)
            .load(receivedUser.image)
            .apply(RequestOptions.circleCropTransform())
            .into(userImage)
        userNameText.text = "${receivedUser.firstName} ${receivedUser.secondName}"
        genderAndAgeText.text = "${receivedUser.gender}, ${receivedUser.age} years old"
        phoneText.text = receivedUser.phone
        phoneText.setOnClickListener {

            if (isPermitted(Manifest.permission.CALL_PHONE)) {
                startCall()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_PERMISSION_REQUEST_CODE
                )
            }
        }
        emailText.text = receivedUser.email
        locationText.text = "${receivedUser.city}, ${receivedUser.country}"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US)
        val output = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(receivedUser.dateOfBirthday)
        val formattedDate = output.format(date)
        dobText.text = formattedDate.toString()
    }

    private fun startCall() {
        val uri = "tel:${phoneText.text.trim()}"
        val intent = Intent(Intent.ACTION_CALL).setData(Uri.parse(uri))
        intent.resolveActivity(packageManager)?.let {
            startActivity(intent)
        } ?: run {
            Toast.makeText(this, R.string.no_phone_app, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (isPermitted(Manifest.permission.CALL_PHONE)) {
                startCall()
            } else {
                Toast.makeText(this, R.string.call_permission_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
