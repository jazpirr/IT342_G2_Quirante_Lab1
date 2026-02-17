package com.example.lendit

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lendit.auth.LoginActivity
import com.example.lendit.databinding.ActivityProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        loadUserData()
    }

    override fun onResume() {
        super.onResume()
        setupAnimations()
    }

    private fun setupAnimations() {
        // Pulse avatar glow
        animateAvatarGlow()

        // Rotate background blobs
        animateBlob(binding.blobTop, 22000L)
        animateBlob(binding.blobBottom, 28000L, reverse = true)
    }

    private fun animateAvatarGlow() {
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.2f, 1f)
        scaleAnimator.duration = 2500
        scaleAnimator.repeatCount = ValueAnimator.INFINITE
        scaleAnimator.interpolator = AccelerateDecelerateInterpolator()
        scaleAnimator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            binding.avatarGlow.scaleX = scale
            binding.avatarGlow.scaleY = scale
        }
        scaleAnimator.start()
    }

    private fun animateBlob(view: View, duration: Long, reverse: Boolean = false) {
        val rotation = if (reverse) -360f else 360f
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, rotation)
        animator.duration = duration
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    private fun setupClickListeners() {
        // Back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Edit Profile
        binding.editProfileButton.setOnClickListener {
            Toast.makeText(this, "Edit Profile feature coming soon", Toast.LENGTH_SHORT).show()
        }

        // Change Password
        binding.changePasswordButton.setOnClickListener {
            Toast.makeText(this, "Change Password feature coming soon", Toast.LENGTH_SHORT).show()
        }

        // Notifications
        binding.notificationsButton.setOnClickListener {
            Toast.makeText(this, "Notifications feature coming soon", Toast.LENGTH_SHORT).show()
        }

        // Logout button
        binding.logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { dialog, _ ->
                dialog.dismiss()
                performLogout()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun performLogout() {
        // TODO: Clear user session, preferences, tokens, etc.
        // For example:
        // SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        // prefs.edit().clear().apply()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate to Login screen and clear back stack
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun loadUserData() {
        // TODO: Load actual user data from your backend/database
        // For now, using placeholder data
        binding.userNameText.text = "John Doe"
        binding.userEmailText.text = "john.doe@email.com"
    }
}