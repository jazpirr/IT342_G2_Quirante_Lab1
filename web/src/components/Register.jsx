import React, { useState } from 'react';
import { Eye, EyeOff, Mail, Lock, User, UserPlus } from 'lucide-react';
import '../css/Auth.css';

const Register = ({ onSwitchToLogin }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: ''
  });
  const [particles, setParticles] = useState([]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    // Create celebration particles
    const newParticles = Array.from({ length: 30 }, (_, i) => ({
      id: Date.now() + i,
      x: Math.random() * 100,
      y: Math.random() * 100,
      delay: Math.random() * 0.5
    }));
    setParticles(newParticles);
    
    // Simulate success
    setTimeout(() => {
      alert('🎊 Account created successfully!');
      setFormData({ name: '', email: '', password: '' });
      setParticles([]);
    }, 1500);
  };

  return (
    <div className="auth-container">
      {/* Animated background blobs */}
      <div className="background-blob-1" />
      <div className="background-blob-2" />

      {/* Success particles */}
      {particles.map(particle => (
        <div
          key={particle.id}
          className="particle"
          style={{
            left: `${particle.x}%`,
            top: `${particle.y}%`,
            animationDelay: `${particle.delay}s`,
          }}
        />
      ))}

      {/* Main Card */}
      <div className="glass-card">
        {/* Header */}
        <div className="auth-header">
          <h1 className="auth-title">Join Us!</h1>
          <p className="auth-subtitle">Create your account to get started</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="auth-form">
          {/* Name Field */}
          <div className="form-group">
            <div className="input-wrapper">
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Full Name"
                required
                className="input-field"
              />
              <div className="icon-wrapper">
                <User size={20} />
              </div>
            </div>
          </div>

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
            Create Account
            <UserPlus size={20} />
          </button>
        </form>

        {/* Links */}
        <div className="auth-links">
          <div className="toggle-mode">
            Already have an account?
            <span className="link-text" onClick={onSwitchToLogin}>
              Sign In
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;