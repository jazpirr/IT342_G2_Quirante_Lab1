import React, { useState } from 'react';
import { Eye, EyeOff, Mail, Lock, LogIn } from 'lucide-react';
import '../css/Auth.css';

const Login = ({ onSwitchToRegister }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('🎉 Login successful!');
    setFormData({ email: '', password: '' });
  };

  return (
    <div className="auth-container">
      {/* Animated background blobs */}
      <div className="background-blob-1" />
      <div className="background-blob-2" />

      {/* Main Card */}
      <div className="glass-card">
        {/* Header */}
        <div className="auth-header">
          <h1 className="auth-title">Welcome Back!</h1>
          <p className="auth-subtitle">Sign in to continue your journey</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="auth-form">
          {/* Email Field */}
          <div className="form-group">
            <div className="input-wrapper">
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Email Address"
                required
                className="input-field"
              />
              <div className="icon-wrapper">
                <Mail size={20} />
              </div>
            </div>
          </div>

          {/* Password Field */}
          <div className="form-group">
            <div className="input-wrapper">
              <input
                type={showPassword ? 'text' : 'password'}
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Password"
                required
                className="input-field"
              />
              <div className="icon-wrapper">
                <Lock size={20} />
              </div>
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="toggle-password-btn"
              >
                {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          {/* Submit Button */}
          <button type="submit" className="submit-btn">
            Sign In
            <LogIn size={20} />
          </button>
        </form>

        {/* Links */}
        <div className="auth-links">
          <div className="forgot-password">
            <span
              className="link-text"
              onClick={() => alert('Password reset link sent! 📧')}
            >
              Forgot password?
            </span>
          </div>

          <div className="toggle-mode">
            Don't have an account?
            <span className="link-text" onClick={onSwitchToRegister}>
              Sign Up
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;