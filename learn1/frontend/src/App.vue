<template>
  <div>
    <nav class="navbar" v-if="loggedIn">
      <div class="nav-left">
        <h1>学习项目</h1>
      </div>
      <div class="nav-right">
        <router-link to="/" class="nav-link">用户管理</router-link>
        <router-link to="/bilibili" class="nav-link">B站视频</router-link>
        <router-link to="/up-ranking" class="nav-link">UP主排行</router-link>
        <router-link to="/open-api" class="nav-link">OpenAPI</router-link>
        <span class="nav-user">{{ userInfo ? userInfo.username : '' }}</span>
        <a href="#" class="nav-link" @click.prevent="handleLogout">退出</a>
      </div>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { logout } from './api/auth.js'

const router = useRouter()
const route = useRoute()
const loggedIn = ref(!!localStorage.getItem('token'))
const userInfo = ref(JSON.parse(localStorage.getItem('user') || 'null'))

watch(() => route.path, () => {
  loggedIn.value = !!localStorage.getItem('token')
  userInfo.value = JSON.parse(localStorage.getItem('user') || 'null')
})

function handleLogout() {
  logout()
  loggedIn.value = false
  userInfo.value = null
  router.push('/login')
}
</script>

<style scoped>
.navbar { display: flex; justify-content: space-between; align-items: center; padding: 0 24px; height: 56px; background: #001529; }
.nav-left h1 { margin: 0; font-size: 18px; color: #fff; }
.nav-right { display: flex; gap: 4px; align-items: center; }
.nav-link { color: rgba(255,255,255,0.65); text-decoration: none; padding: 0 16px; font-size: 14px; line-height: 56px; display: inline-block; }
.nav-link:hover, .nav-link.router-link-active { color: #fff; background: #1890ff; }
.nav-user { color: rgba(255,255,255,0.45); font-size: 13px; margin-right: 8px; }
</style>
