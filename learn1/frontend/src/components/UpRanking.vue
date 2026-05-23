<template>
  <div class="container">
    <h2>B站UP主热门排行</h2>

    <div class="panel">
      <div class="form-row">
        <button class="btn btn-primary" @click="fetchRanking">刷新排行</button>
        <span class="refresh-time" v-if="lastUpdated">最后更新：{{ lastUpdated }}</span>
      </div>
    </div>

    <div class="panel" v-if="loading">
      <div class="loading">加载中...</div>
    </div>

    <div class="panel" v-else-if="error">
      <div class="error">加载失败：{{ error }}</div>
    </div>

    <div class="panel" v-else-if="list.length > 0">
      <table class="rank-table">
        <thead>
          <tr>
            <th>排名</th>
            <th>头像</th>
            <th>UP主</th>
            <th>热门作品</th>
            <th>播放量</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in list" :key="item.id || index">
            <td class="rank">{{ index + 1 }}</td>
            <td><img :src="item.face" class="avatar" /></td>
            <td class="uname">{{ item.name }}</td>
            <td class="title" :title="item.title">{{ item.title }}</td>
            <td class="stats">{{ item.view }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="panel" v-else>
      <div class="no-data">暂无排行数据</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const loading = ref(false)
const error = ref('')
const list = ref([])
const lastUpdated = ref('')

function fetchRanking() {
  loading.value = true
  error.value = ''
  const token = localStorage.getItem('token')
  fetch('/api/bilibili/up-ranking', {
    headers: token ? { Authorization: 'Bearer ' + token } : {}
  })
    .then(res => {
      if (res.status === 401) {
        localStorage.removeItem('token'); localStorage.removeItem('user')
        window.location.href = '/login'; return
      }
      const newToken = res.headers.get('X-Refresh-Token')
      if (newToken) localStorage.setItem('token', newToken)
      return res.json()
    })
    .then(data => {
      if (!data) return
      loading.value = false
      const rawList = (data.data && data.data.list) || []
      const map = new Map()
      rawList.forEach(item => {
        const owner = item.owner || {}
        const key = owner.mid
        if (!map.has(key)) {
          map.set(key, {
            id: key,
            name: owner.name || '--',
            face: owner.face || '',
            title: item.title || '',
            view: formatNum((item.stat && item.stat.view) || 0)
          })
        }
      })
      list.value = [...map.values()]
      lastUpdated.value = new Date().toLocaleTimeString()
    })
    .catch(err => { loading.value = false; error.value = err.message })
}

function formatNum(n) {
  if (n >= 10000) return (n / 10000).toFixed(1) + '万'
  return n.toLocaleString()
}

fetchRanking()
</script>

<style scoped>
.container { max-width: 900px; margin: 0 auto; }
h2 { margin-bottom: 20px; color: #333; }
.panel { background: #fff; padding: 20px; border-radius: 6px; margin-bottom: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
.form-row { display: flex; gap: 12px; align-items: center; }
.btn { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; }
.btn-primary { background: #1890ff; color: #fff; }
.btn-primary:hover { background: #40a9ff; }
.refresh-time { font-size: 13px; color: #666; margin-left: 12px; }
.loading, .error, .no-data { text-align: center; padding: 40px; color: #999; }
.error { color: #ff4d4f; }
.rank-table { width: 100%; border-collapse: collapse; }
.rank-table th, .rank-table td { padding: 10px 12px; border-bottom: 1px solid #f0f0f0; }
.rank-table th { background: #fafafa; font-size: 13px; color: #666; }
.rank { text-align: center; font-weight: bold; font-size: 16px; color: #1890ff; }
.avatar { width: 36px; height: 36px; border-radius: 50%; }
.uname { font-size: 14px; color: #333; }
.title { max-width: 300px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; font-size: 13px; color: #666; }
.stats { text-align: right; font-family: monospace; font-size: 13px; color: #666; }
</style>
