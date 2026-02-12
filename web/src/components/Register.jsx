import React, { useState } from "react";
import { Eye, EyeOff, Mail, Lock, User, UserPlus } from "lucide-react";
import "../css/Auth.css";

const Register = ({ onSwitchToLogin }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate passwords match
    if (formData.password !== formData.confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    const [fName, lName] = formData.name.split(" ");

    try {
      const response = await fetch(
        "http://localhost:8080/api/auth/register",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
          body: JSON.stringify({
            fName: fName || "",
            lName: lName || "",
            email: formData.email,
            password: formData.password,
          }),
        }
      );

      const data = await response.json();

      if (response.ok) {
        alert("Account created successfully!");
        onSwitchToLogin();
      } else {
        alert(data);
      }
    } catch (error) {
      alert("Server error. Is backend running?");
    }
  };

  return (
    <div className="auth-container">
      <div className="background-blob-1"></div>
      <div className="background-blob-2"></div>
      
      <div className="glass-card">
        <div className="auth-header">
          <h1 className="auth-title">Create Account</h1>
          <p className="auth-subtitle">Join us today and get started</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-group">
            <div className="input-wrapper">
              <div className="icon-wrapper">
                <User size={20} />
              </div>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Full Name"
                required
                className="input-field"
              />
            </div>
          </div>

          <div className="form-group">
            <div className="input-wrapper">
              <div className="icon-wrapper">
                <Mail size={20} />
              </div>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Email address"
                required
                className="input-field"
              />
            </div>
          </div>

          <div className="form-group">
            <div className="input-wrapper">
              <div className="icon-wrapper">
                <Lock size={20} />
              </div>
              <input
                type={showPassword ? "text" : "password"}
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Password"
                required
                className="input-field"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="toggle-password-btn"
              >
                {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          <div className="form-group">
            <div className="input-wrapper">
              <div className="icon-wrapper">
                <Lock size={20} />
              </div>
              <input
                type={showConfirmPassword ? "text" : "password"}
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleChange}
                placeholder="Confirm Password"
                required
                className="input-field"
              />
              <button
                type="button"
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                className="toggle-password-btn"
              >
                {showConfirmPassword ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          <button type="submit" className="submit-btn">
            Create Account
            <UserPlus size={18} />
          </button>
        </form>

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