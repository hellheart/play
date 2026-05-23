<template>
  <div class="container">
    <h2>免费OpenAPI查询</h2>

    <div class="panel">
      <div class="form-row">
        <div class="form-group">
          <label>名称</label>
          <input v-model="searchName" placeholder="搜索API" @keyup.enter="fetch" />
        </div>
        <div class="form-group">
          <label>分类</label>
          <select v-model="searchCategory">
            <option value="">全部分类</option>
            <option v-for="c in categories" :key="c" :value="c">{{ c }}</option>
          </select>
        </div>
        <button class="btn btn-primary" @click="loadData">查询</button>
        <button class="btn btn-default" @click="reset">重置</button>
      </div>
    </div>

    <div class="panel" v-if="loading">
      <div class="loading">加载中...</div>
    </div>

    <div class="panel" v-else>
      <div class="api-grid" v-if="list.length > 0">
        <div class="api-card" v-for="item in list" :key="item.id">
          <div class="card-header">
            <span class="tag" :style="methodStyle(item.method)">{{ item.method }}</span>
            <span class="tag tag-cat">{{ item.category }}</span>
            <span class="tag tag-auth" v-if="item.needAuth">需认证</span>
            <span class="tag tag-free" v-else>免费</span>
          </div>
          <h3 class="card-title">{{ item.name }}</h3>
          <p class="card-desc">{{ item.description }}</p>
          <div class="card-url" @click="copyUrl(item.url)" :title="'点击复制: ' + item.url">
            {{ item.url }}
          </div>
        </div>
      </div>
      <div class="no-data" v-else>暂无数据</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchName = ref('')
const searchCategory = ref('')
const list = ref([])
const categories = ref([])
const loading = ref(false)

function loadData() {
  loading.value = true
  const token = localStorage.getItem('token')
  const params = new URLSearchParams({ name: searchName.value, category: searchCategory.value })
  window.fetch('/api/open-api?' + params, {
    headers: token ? { Authorization: 'Bearer ' + token } : {}
  })
    .then(res => {
      if (res.status === 401) { localStorage.clear(); window.location.href = '/login'; return }
      return res.json()
    })
    .then(data => {
      if (!data) return
      loading.value = false
      list.value = data.list || []
      categories.value = data.categories || []
    })
    .catch(() => { loading.value = false })
}

function reset() {
  searchName.value = ''
  searchCategory.value = ''
  loadData()
}

function methodStyle(m) {
  if (m === 'POST') return { background: '#52c41a', color: '#fff' }
  if (m === 'PUT') return { background: '#fa8c16', color: '#fff' }
  if (m === 'DELETE') return { background: '#ff4d4f', color: '#fff' }
  return { background: '#1890ff', color: '#fff' }
}

function copyUrl(url) {
  navigator.clipboard.writeText(url).then(() => alert('已复制URL到剪贴板'))
}

loadData()
</script>

<style scoped>
.container { max-width: 1100px; margin: 0 auto; }
h2 { margin-bottom: 20px; color: #333; }
.panel { background: #fff; padding: 20px; border-radius: 6px; margin-bottom: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
.form-row { display: flex; gap: 12px; align-items: flex-end; flex-wrap: wrap; }
.form-group { display: flex; flex-direction: column; }
.form-group label { font-size: 13px; color: #666; margin-bottom: 4px; }
.form-group input, .form-group select { padding: 8px 10px; border: 1px solid #d9d9d9; border-radius: 4px; width: 200px; font-size: 14px; }
.btn { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; }
.btn-primary { background: #1890ff; color: #fff; }
.btn-primary:hover { background: #40a9ff; }
.btn-default { background: #fff; color: #333; border: 1px solid #d9d9d9; }
.loading, .no-data { text-align: center; padding: 40px; color: #999; }
.api-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 16px; }
.api-card { border: 1px solid #f0f0f0; border-radius: 6px; padding: 16px; transition: box-shadow 0.2s; }
.api-card:hover { box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
.card-header { display: flex; gap: 6px; margin-bottom: 10px; flex-wrap: wrap; }
.tag { padding: 2px 8px; border-radius: 3px; font-size: 12px; }
.tag-cat { background: #f0f0f0; color: #666; }
.tag-auth { background: #fff2e8; color: #fa8c16; }
.tag-free { background: #f6ffed; color: #52c41a; }
.card-title { font-size: 16px; color: #333; margin-bottom: 8px; }
.card-desc { font-size: 13px; color: #999; margin-bottom: 10px; }
.card-url { font-size: 12px; color: #1890ff; word-break: break-all; cursor: pointer; background: #f6f8fa; padding: 6px 8px; border-radius: 3px; }
.card-url:hover { text-decoration: underline; }
</style>
