package com.blackmagic.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.blackmagic.camera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val audioGranted = permissions[Manifest.permission.RECORD_AUDIO] ?: false

        updatePermissionUi(cameraGranted && audioGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGrantPermissions.setOnClickListener {
            requestAppPermissions()
        }

        checkInitialPermissions()
    }

    private fun checkInitialPermissions() {
        val allGranted = requiredPermissions.all { perm ->
            ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
        }
        updatePermissionUi(allGranted)
    }

    private fun requestAppPermissions() {
        permissionLauncher.launch(requiredPermissions)
    }

    private fun updatePermissionUi(isGranted: Boolean) {
        if (isGranted) {
            binding.tvPermissionStatus.text = getString(R.string.permission_granted)
            binding.tvPermissionStatus.setTextColor(ContextCompat.getColor(this, R.color.status_green))
            binding.btnGrantPermissions.visibility = View.GONE
        } else {
            binding.tvPermissionStatus.text = getString(R.string.permission_rationale)
            binding.tvPermissionStatus.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            binding.btnGrantPermissions.visibility = View.VISIBLE
        }
    }
}
