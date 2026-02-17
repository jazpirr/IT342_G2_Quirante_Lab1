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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.lendit.R
import com.example.lendit.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        // Animate card entrance
        binding.registerCard.apply {
            alpha = 0f
            translationY = 120f
            scaleX = 0.95f
            scaleY = 0.95f
            animate()
                .alpha(1f)
                .translationY(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }

        // Animate logo with bounce
        binding.logoIcon.apply {
            scaleX = 0f
            scaleY = 0f
            rotation = 180f
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

        // Rotate background blobs
        animateBlob(binding.blobTopRight, 24000L)
        animateBlob(binding.blobBottomLeft, 30000L, reverse = true)
        animateBlob(binding.blobMiddleRight, 20000L)

        // Float particles
        animateParticle(binding.particle1, 4500L, 35f)
        animateParticle(binding.particle2, 3800L, 30f)
    }

    private fun animateLogoGlow() {
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.25f, 1f)
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
        // Real-time validation
        binding.emailInput.addTextChangedListener {
            binding.emailLayout.error = null
        }

        binding.passwordInput.addTextChangedListener {
            binding.passwordLayout.error = null
            updatePasswordStrength(it.toString())
        }

        binding.confirmPasswordInput.addTextChangedListener {
            binding.confirmPasswordLayout.error = null
        }

        binding.nameInput.addTextChangedListener {
            binding.nameLayout.error = null
        }
    }

    private fun updatePasswordStrength(password: String) {
        // Visual feedback for password strength
        val strength = calculatePasswordStrength(password)
        // You can update a progress bar or text indicator here
    }

    private fun calculatePasswordStrength(password: String): Int {
        var strength = 0
        if (password.length >= 8) strength++
        if (password.any { it.isUpperCase() }) strength++
        if (password.any { it.isLowerCase() }) strength++
        if (password.any { it.isDigit() }) strength++
        if (password.any { !it.isLetterOrDigit() }) strength++
        return strength
    }

    private fun setupClickListeners() {
        // Register button
        binding.registerButton.setOnClickListener {
            if (validateAllInputs()) {
                performRegistration()
            }
        }

        // Terms and conditions
        binding.termsText.setOnClickListener {
            showTermsDialog()
        }

        // Login link
        binding.loginLink.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun validateAllInputs(): Boolean {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val phone = binding.phoneInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()
        val termsAccepted = binding.termsCheckbox.isChecked

        var isValid = true

        // Validate name
        if (name.isEmpty()) {
            binding.nameLayout.error = "Full name is required"
            shakeView(binding.nameLayout)
            isValid = false
        } else if (name.length < 3) {
            binding.nameLayout.error = "Name must be at least 3 characters"
            shakeView(binding.nameLayout)
            isValid = false
        }

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

        // Validate phone (optional but if provided, must be valid)
        if (phone.isNotEmpty() && !Patterns.PHONE.matcher(phone).matches()) {
            binding.phoneLayout.error = "Please enter a valid phone number"
            shakeView(binding.phoneLayout)
            isValid = false
        }

        // Validate password
        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password is required"
            shakeView(binding.passwordLayout)
            isValid = false
        } else if (password.length < 8) {
            binding.passwordLayout.error = "Password must be at least 8 characters"
            shakeView(binding.passwordLayout)
            isValid = false
        } else if (!isPasswordStrong(password)) {
            binding.passwordLayout.error = "Password must contain letters and numbers"
            shakeView(binding.passwordLayout)
            isValid = false
        }

        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordLayout.error = "Please confirm your password"
            shakeView(binding.confirmPasswordLayout)
            isValid = false
        } else if (password != confirmPassword) {
            binding.confirmPasswordLayout.error = "Passwords do not match"
            shakeView(binding.confirmPasswordLayout)
            isValid = false
        }

        // Validate terms acceptance
        if (!termsAccepted) {
            Toast.makeText(this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
            shakeView(binding.termsCheckbox)
            isValid = false
        }

        return isValid
    }

    private fun isPasswordStrong(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return hasLetter && hasDigit
    }

    private fun shakeView(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
        animator.duration = 500
        animator.start()
    }

    private fun performRegistration() {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val phone = binding.phoneInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()

        // Show loading
        showLoading(true)

        // Simulate API call
        binding.root.postDelayed({
            // TODO: Implement actual registration API call
            showLoading(false)

            // Show success message
            MaterialAlertDialogBuilder(this)
                .setTitle("🎉 Registration Successful!")
                .setMessage("Your account has been created successfully! Welcome to LendIT.")
                .setPositiveButton("Continue") { dialog, _ ->
                    dialog.dismiss()
                    // Navigate to login or main activity
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
                .setCancelable(false)
                .show()

        }, 2000)
    }

    private fun showTermsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Terms and Conditions")
            .setMessage("Welcome to LendIT!\n\n" +
                    "By using our services, you agree to:\n\n" +
                    "1. Provide accurate information\n" +
                    "2. Respect other users\n" +
                    "3. Comply with all applicable laws\n" +
                    "4. Protect your account credentials\n\n" +
                    "Please read our full Terms and Privacy Policy on our website.")
            .setPositiveButton("I Understand") { dialog, _ ->
                dialog.dismiss()
                binding.termsCheckbox.isChecked = true
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showLoading(show: Boolean) {
        binding.loadingOverlay.visibility = if (show) View.VISIBLE else View.GONE
        binding.registerButton.isEnabled = !show
    }
}