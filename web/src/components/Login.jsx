import React, { useState } from "react";
import { Eye, EyeOff, Mail, Lock, LogIn } from "lucide-react";
import "../css/Auth.css";

const Login = ({ onSwitchToRegister, setUser }) => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        "http://localhost:8080/api/auth/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
          body: JSON.stringify(formData),
        }
      );

      const data = await response.json();

      if (response.ok) {
        setUser(data);
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
          <h1 className="auth-title">Welcome Back!</h1>
          <p className="auth-subtitle">Sign in to continue to your account</p>
        </div>

        <form onSubmit={handleSubmit} className="auth-form">
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

          <button type="submit" className="submit-btn">
            Sign In
            <LogIn size={18} />
          </button>
        </form>

        <div className="auth-links">
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