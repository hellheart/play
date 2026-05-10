import axios from 'axios'
import qs from 'qs'

const api = axios.create({ baseURL: '/auth' })

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = 'Bearer ' + token
  }
  return config
})

api.interceptors.response.use(
  res => {
    const newToken = res.headers['x-refresh-token']
    if (newToken) {
      localStorage.setItem('token', newToken)
    }
    return res
  },
  err => {
    if (err.response && err.response.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(err)
  }
)

export function login(username, password) {
  return api.post('/login', qs.stringify({ username, password }))
}

export function register(data) {
  return api.post('/register', qs.stringify(data))
}

export function isLoggedIn() {
  return !!localStorage.getItem('token')
}

export function logout() {
  const token = localStorage.getItem('token')
  const headers = token ? { Authorization: 'Bearer ' + token } : {}
  api.post('/logout', null, { headers }).finally(() => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  })
}
