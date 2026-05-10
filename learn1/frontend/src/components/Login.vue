<template>
  <div class="login-container">
    <div class="login-card">
      <h2>{{ isRegister ? '注册' : '登录' }}</h2>
      <div class="form-group">
        <label>用户名</label>
        <input v-model="username" placeholder="请输入用户名" @keyup.enter="submit" />
      </div>
      <div class="form-group">
        <label>密码</label>
        <input v-model="password" type="password" placeholder="请输入密码" @keyup.enter="submit" />
      </div>
      <div class="form-group" v-if="isRegister">
        <label>姓名</label>
        <input v-model="name" placeholder="请输入姓名" />
      </div>
      <p class="error" v-if="error">{{ error }}</p>
      <button class="btn btn-primary btn-full" @click="submit">{{ isRegister ? '注册' : '登录' }}</button>
      <p class="switch">
        <a href="#" @click.prevent="isRegister = !isRegister; error = ''">
          {{ isRegister ? '已有账号？去登录' : '没有账号？去注册' }}
        </a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login, register } from '../api/auth.js'

const router = useRouter()
const isRegister = ref(false)
const username = ref('')
const password = ref('')
const name = ref('')
const error = ref('')

function submit() {
  error.value = ''
  if (!username.value || !password.value) {
    error.value = '用户名和密码不能为空'
    return
  }
  if (isRegister.value) {
    register({ username: username.value, password: password.value, name: name.value })
      .then(res => {
        if (res.data.code === 200) {
          isRegister.value = false
          error.value = ''
        } else {
          error.value = res.data.message
        }
      })
  } else {
    login(username.value, password.value)
      .then(res => {
        if (res.data.code === 200) {
          localStorage.setItem('token', res.data.token)
          localStorage.setItem('user', JSON.stringify(res.data.user))
          router.push('/')
        } else {
          error.value = res.data.message
        }
      })
  }
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: #f0f2f5; }
.login-card { background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.1); width: 400px; }
.login-card h2 { text-align: center; margin-bottom: 24px; color: #333; }
.form-group { margin-bottom: 16px; }
.form-group label { display: block; margin-bottom: 6px; font-size: 14px; color: #666; }
.form-group input { width: 100%; padding: 10px 12px; border: 1px solid #d9d9d9; border-radius: 4px; font-size: 14px; box-sizing: border-box; }
.error { color: #ff4d4f; font-size: 13px; margin-bottom: 12px; }
.btn-full { width: 100%; padding: 12px; font-size: 16px; }
.switch { text-align: center; margin-top: 16px; }
.switch a { color: #1890ff; text-decoration: none; font-size: 14px; }
</style>
