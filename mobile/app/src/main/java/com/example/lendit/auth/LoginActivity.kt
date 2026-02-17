package com.example.lendit.auth

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.lendit.R
import com.example.lendit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupValidation()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        // Play animations every time the activity becomes visible
        setupAnimations()
    }

    private fun setupAnimations() {
        // Animate card entrance with bounce
        binding.loginCard.apply {
            alpha = 0f
            translationY = 120f
            scaleX = 0.9f
            scaleY = 0.9f
            animate()
                .alpha(1f)
                .translationY(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }

        // Animate logo with rotation and scale
        binding.logoIcon.apply {
            scaleX = 0f
            scaleY = 0f
            rotation = -180f
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .rotation(0f)
                .setDuration(600)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setStartDelay(250)
                .start()
        }

        // Pulse logo glow
        animateLogoGlow()

        // Slide in accent bar
        binding.accentBar.apply {
            alpha = 0f
            scaleX = 0f
            animate()
                .alpha(1f)
                .scaleX(1f)
                .setDuration(500)
                .setStartDelay(400)
                .start()
        }

        // Rotate background blobs continuously
        animateBlob(binding.blobTop, 22000L)
        animateBlob(binding.blobBottom, 28000L, reverse = true)
        animateBlob(binding.blobMiddle, 18000L)

        // Float particles
        animateParticle(binding.particle1, 4000L, 30f)
        animateParticle(binding.particle2, 5000L, 40f)
        animateParticle(binding.particle3, 3500L, 35f)
    }

    private fun animateLogoGlow() {
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.3f, 1f)
        scaleAnimator.duration = 2000
        scaleAnimator.repeatCount = ValueAnimator.INFINITE
        scaleAnimator.interpolator = AccelerateDecelerateInterpolator()
        scaleAnimator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            binding.logoGlow.scaleX = scale
            binding.logoGlow.scaleY = scale
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

    private fun animateParticle(view: View, duration: Long, distance: Float) {
        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, -distance, 0f)
        animator.duration = duration
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    private fun setupValidation() {
        // Email validation
        binding.emailInput.addTextChangedListener {
            binding.emailLayout.error = null
        }

        // Password validation
        binding.passwordInput.addTextChangedListener {
            binding.passwordLayout.error = null
        }
    }

    private fun setupClickListeners() {
        // Login button
        binding.loginButton.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        // Forgot password
        binding.forgotPassword.setOnClickListener {
            // Navigate to forgot password screen
            Toast.makeText(this, "Forgot password feature coming soon", Toast.LENGTH_SHORT).show()
        }

        // Register link
        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()

        var isValid = true

        // Validate email
        if (email.isEmpty()) {
            binding.emailLayout.error = "Email is required"
            shakeView(binding.emailLayout)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = "Please enter a valid email"
            shakeView(binding.emailLayout)
            isValid = false
        }

        // Validate password
        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password is required"
            shakeView(binding.passwordLayout)
            isValid = false
        } else if (password.length < 6) {
            binding.passwordLayout.error = "Password must be at least 6 characters"
            shakeView(binding.passwordLayout)
            isValid = false
        }

        return isValid
    }

    private fun shakeView(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
        animator.duration = 500
        animator.start()
    }

    private fun performLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()

        // Show loading
        showLoading(true)

        // Simulate API call
        binding.root.postDelayed({
            // TODO: Implement actual authentication
            showLoading(false)

            // Example success
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

            // Navigate to dashboard
            val intent = Intent(this, com.example.lendit.DashboardActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }

    private fun showLoading(show: Boolean) {
        binding.loadingOverlay.visibility = if (show) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !show
    }
}