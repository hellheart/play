import axios from 'axios'

const api = axios.create({ baseURL: '/user' })

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

export function queryUsers(params) {
  return api.get('/api', { params })
}

export function saveUser(data) {
  return api.post('/api', data)
}

export function deleteUser(id) {
  return api.delete('/api/' + id)
}

export function batchSaveUsers(users) {
  return api.post('/api/batch', users)
}
