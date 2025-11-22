import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from './components/pages/Home'
import Auth from './components/pages/Auth'
import Register from './components/pages/Register'
import Login from './components/pages/Login'
import Logout from './components/profile/Logout'
import Movies from './components/pages/Movies'
import Profile from './components/pages/Profile'

function App() {

  return ( 
    <div className="app">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/movieauth" element={<Auth to="/movies" />} />
          <Route path="/profileauth" element={<Auth to="/profile" />} />
          <Route path="/movies" element={<Movies />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
