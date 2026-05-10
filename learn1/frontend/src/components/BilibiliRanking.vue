<template>
  <div class="container">
    <h2>B站视频排行榜</h2>

    <div class="panel">
      <div class="form-row">
        <button class="btn btn-primary" @click="fetchRanking">刷新排行榜</button>
        <span class="refresh-time" v-if="lastUpdated">最后更新：{{ lastUpdated }}</span>
      </div>
    </div>

    <div class="panel" v-if="loading">
      <div class="loading">加载中...</div>
    </div>

    <div class="panel" v-else-if="error">
      <div class="error">加载失败：{{ error }}</div>
    </div>

    <div class="panel" v-else-if="rankingData && rankingData.data && rankingData.data.list && rankingData.data.list.length > 0">
      <table class="ranking-table">
        <thead>
          <tr>
            <th>排名</th>
            <th>标题</th>
            <th>UP主</th>
            <th>播放数</th>
            <th>点赞数</th>
            <th>硬币数</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in rankingData.data.list" :key="index">
            <td>{{ index + 1 }}</td>
            <td class="title">{{ item.title }}</td>
            <td class="author">{{ item.owner.name }}</td>
            <td class="stats">{{ item.stat.view }}</td>
            <td class="stats">{{ item.stat.like }}</td>
            <td class="stats">{{ item.stat.coin }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="panel" v-else>
      <div class="no-data">暂无排行榜数据</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const loading = ref(false)
const error = ref('')
const rankingData = ref(null)
const lastUpdated = ref('')

function fetchRanking() {
  loading.value = true
  error.value = ''
  const token = localStorage.getItem('token')
  fetch('/api/bilibili/ranking', {
    headers: token ? { Authorization: 'Bearer ' + token } : {}
  })
    .then(response => {
      if (response.status === 401) {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        window.location.href = '/login'
        return
      }
      const newToken = response.headers.get('X-Refresh-Token')
      if (newToken) {
        localStorage.setItem('token', newToken)
      }
      return response.json()
    })
    .then(data => {
      if (!data) return
      loading.value = false
      rankingData.value = data
      lastUpdated.value = new Date().toLocaleTimeString()
    })
    .catch(err => {
      loading.value = false
      error.value = err.message
    })
}

fetchRanking()
</script>

<style scoped>
.container { max-width: 1000px; margin: 0 auto; }
h2 { margin-bottom: 20px; color: #333; }
.panel { background: #fff; padding: 20px; border-radius: 6px; margin-bottom: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
.form-row { display: flex; gap: 12px; align-items: center; flex-wrap: wrap; }
.btn { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; }
.btn-primary { background: #1890ff; color: #fff; }
.btn-primary:hover { background: #40a9ff; }
.refresh-time { font-size: 13px; color: #666; margin-left: 12px; }
.loading, .error { text-align: center; padding: 20px; color: #666; }
.error { color: #ff4d4f; }
.no-data { text-align: center; color: #999; padding: 40px; }
.ranking-table { width: 100%; border-collapse: collapse; margin-top: 12px; }
.ranking-table th, .ranking-table td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #f0f0f0; }
.ranking-table th { background: #fafafa; color: #666; font-weight: 600; font-size: 13px; }
.ranking-table td { font-size: 14px; color: #333; }
.ranking-table tr:hover td { background: #e6f7ff; }
.title { max-width: 300px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.author { max-width: 120px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.stats { text-align: right; font-family: 'Courier New', monospace; }
</style>
