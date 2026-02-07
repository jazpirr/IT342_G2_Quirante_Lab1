import React, { useState } from 'react';
import Login from './components/Login';
import Register from './components/Register';

function App() {
  const [showLogin, setShowLogin] = useState(true);

  return (
    <div>
      {showLogin ? (
        <Login onSwitchToRegister={() => setShowLogin(false)} />
      ) : (
        <Register onSwitchToLogin={() => setShowLogin(true)} />
      )}
    </div>
  );
}

export default App;