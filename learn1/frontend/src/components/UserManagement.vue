<template>
  <div class="container">
    <h2>用户信息维护</h2>

    <div class="panel">
      <div class="form-row">
        <div class="form-group">
          <label>姓名</label>
          <input v-model="searchName" placeholder="请输入姓名" @keyup.enter="search" />
        </div>
        <div class="form-group">
          <label>邮箱</label>
          <input v-model="searchEmail" placeholder="请输入邮箱" @keyup.enter="search" />
        </div>
        <button class="btn btn-primary" @click="search">查询</button>
        <button class="btn btn-default" @click="resetSearch">重置</button>
        <button class="btn btn-success" @click="openAddDialog">新增用户</button>
        <button class="btn btn-primary" @click="openBatchDialog">批量新增</button>
        <button class="btn btn-default" @click="exportData">导出Excel</button>
        <label class="btn btn-default" style="cursor:pointer;margin:0;">
          导入Excel
          <input type="file" accept=".xlsx,.xls" style="display:none" @change="importData" />
        </label>
      </div>
    </div>

    <div class="panel">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>邮箱</th>
            <th>电话</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="users.length === 0">
            <td colspan="6" class="empty-text">暂无数据</td>
          </tr>
          <tr v-for="u in users" :key="u.id">
            <td>{{ u.id }}</td>
            <td>{{ u.name }}</td>
            <td>{{ u.age }}</td>
            <td>{{ u.email }}</td>
            <td>{{ u.phone }}</td>
            <td>
              <button class="btn btn-primary btn-sm" @click="openEditDialog(u)">编辑</button>
              <button class="btn btn-danger btn-sm" @click="delUser(u.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button class="btn btn-default" :disabled="currentPage <= 1" @click="prevPage">上一页</button>
        <span>第 {{ currentPage }} / {{ totalPages }} 页，共 {{ totalElements }} 条</span>
        <button class="btn btn-default" :disabled="currentPage >= totalPages" @click="nextPage">下一页</button>
      </div>
    </div>

    <div class="dialog-overlay" v-if="dialogVisible" @click="closeDialog"></div>
    <div class="dialog" v-if="dialogVisible">
      <h3>{{ isEdit ? '编辑用户' : '新增用户' }}</h3>
      <div class="form-group">
        <label>姓名</label>
        <input v-model="form.name" placeholder="请输入姓名" />
      </div>
      <div class="form-group">
        <label>年龄</label>
        <input v-model.number="form.age" type="number" placeholder="请输入年龄" />
      </div>
      <div class="form-group">
        <label>邮箱</label>
        <input v-model="form.email" placeholder="请输入邮箱" />
      </div>
      <div class="form-group">
        <label>电话</label>
        <input v-model="form.phone" placeholder="请输入电话" />
      </div>
      <div class="dialog-btns">
        <button class="btn btn-default" @click="closeDialog">取消</button>
        <button class="btn btn-primary" @click="saveUser">保存</button>
      </div>
    </div>

    <div class="dialog-overlay" v-if="batchVisible" @click="closeBatchDialog"></div>
    <div class="dialog batch-dialog" v-if="batchVisible">
      <h3>批量新增用户</h3>
      <div class="batch-toolbar">
        <button class="btn btn-primary btn-sm2" @click="addBatchRow">添加行</button>
        <button class="btn btn-danger btn-sm2" @click="removeBatchRow">删除末行</button>
        <span>快捷添加</span>
        <input type="number" v-model.number="quickCount" min="1" max="50" class="count-input" />
        <span>行</span>
        <button class="btn btn-primary btn-sm2" @click="quickAddRows">确定</button>
      </div>
      <div class="batch-table-wrapper">
        <table class="batch-table">
          <thead>
            <tr>
              <th style="width:40px">#</th>
              <th>姓名</th>
              <th style="width:80px">年龄</th>
              <th>邮箱</th>
              <th>电话</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(r, i) in batchList" :key="i">
              <td>{{ i + 1 }}</td>
              <td><input v-model="r.name" placeholder="姓名" /></td>
              <td><input v-model.number="r.age" type="number" placeholder="年龄" /></td>
              <td><input v-model="r.email" placeholder="邮箱" /></td>
              <td><input v-model="r.phone" placeholder="电话" /></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="dialog-btns">
        <button class="btn btn-default" @click="closeBatchDialog">取消</button>
        <button class="btn btn-success" @click="batchSave">批量保存</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { queryUsers, saveUser as apiSaveUser, deleteUser as apiDeleteUser, batchSaveUsers } from '../api/user.js'

const searchName = ref('')
const searchEmail = ref('')
const users = ref([])
const currentPage = ref(1)
const totalPages = ref(0)
const totalElements = ref(0)

function search(p) {
  if (p !== undefined) currentPage.value = p
  queryUsers({ name: searchName.value, email: searchEmail.value, page: currentPage.value, size: 10 })
    .then(res => {
      users.value = res.data.content || []
      totalPages.value = res.data.totalPages || 0
      totalElements.value = res.data.totalElements || 0
    })
}

function resetSearch() {
  searchName.value = ''
  searchEmail.value = ''
  search(1)
}

function prevPage() {
  if (currentPage.value > 1) search(currentPage.value - 1)
}
function nextPage() {
  if (currentPage.value < totalPages.value) search(currentPage.value + 1)
}

const dialogVisible = ref(false)
const isEdit = ref(false)
const form = reactive({ id: null, name: '', age: null, email: '', phone: '' })

function openAddDialog() {
  isEdit.value = false
  form.id = null; form.name = ''; form.age = null; form.email = ''; form.phone = ''
  dialogVisible.value = true
}

function openEditDialog(u) {
  isEdit.value = true
  form.id = u.id; form.name = u.name; form.age = u.age; form.email = u.email; form.phone = u.phone
  dialogVisible.value = true
}

function closeDialog() {
  dialogVisible.value = false
}

function saveUser() {
  apiSaveUser({ ...form }).then(() => { closeDialog(); search() })
}

function delUser(id) {
  if (!confirm('确定删除吗？')) return
  apiDeleteUser(id).then(() => search())
}

const batchVisible = ref(false)
const quickCount = ref(5)
const batchList = ref([])

function openBatchDialog() {
  batchVisible.value = true
  quickAddRows()
}

function closeBatchDialog() {
  batchVisible.value = false
}

function addBatchRow() {
  batchList.value.push({ name: '', age: null, email: '', phone: '' })
}

function removeBatchRow() {
  if (batchList.value.length > 1) batchList.value.pop()
}

function quickAddRows() {
  const count = quickCount.value || 5
  batchList.value = Array.from({ length: count }, () => ({ name: '', age: null, email: '', phone: '' }))
}

function batchSave() {
  const list = batchList.value.filter(r => r.name || r.email || r.phone)
  if (list.length === 0) { alert('请至少填写一行数据'); return }
  batchSaveUsers(list).then(() => { closeBatchDialog(); search(1) })
}

function exportData() {
  const token = localStorage.getItem('token')
  fetch('/user/api/export', { headers: token ? { Authorization: 'Bearer ' + token } : {} })
    .then(res => {
      if (res.status === 401) { redirectLogin(); return }
      updateToken(res)
      return res.blob()
    })
    .then(blob => {
      if (!blob) return
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = '用户数据.xlsx'
      a.click()
      URL.revokeObjectURL(url)
    })
}

function importData(e) {
  const file = e.target.files[0]
  if (!file) return
  const token = localStorage.getItem('token')
  const form = new FormData()
  form.append('file', file)
  fetch('/user/api/import', {
    method: 'POST',
    headers: token ? { Authorization: 'Bearer ' + token } : {},
    body: form
  })
    .then(res => {
      if (res.status === 401) { redirectLogin(); return }
      updateToken(res)
      return res.json()
    })
    .then(data => {
      if (!data) return
      alert(data.message)
      search(1)
    })
  e.target.value = ''
}

function updateToken(res) {
  const newToken = res.headers.get('X-Refresh-Token')
  if (newToken) {
    localStorage.setItem('token', newToken)
  }
}

function redirectLogin() {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  window.location.href = '/login'
}

search(1)
</script>

<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body { font-family: 'Microsoft YaHei', Arial, sans-serif; padding: 20px; background: #f0f2f5; }
.container { max-width: 1000px; margin: 0 auto; }
h2 { margin-bottom: 20px; color: #333; }
.panel { background: #fff; padding: 20px; border-radius: 6px; margin-bottom: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
.form-row { display: flex; gap: 12px; align-items: flex-end; flex-wrap: wrap; }
.form-group { display: flex; flex-direction: column; }
.form-group label { font-size: 13px; color: #666; margin-bottom: 4px; }
.form-group input { padding: 8px 10px; border: 1px solid #d9d9d9; border-radius: 4px; width: 200px; font-size: 14px; }
.btn { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; }
.btn:disabled { opacity: 0.5; cursor: not-allowed; }
.btn-primary { background: #1890ff; color: #fff; }
.btn-primary:hover:not(:disabled) { background: #40a9ff; }
.btn-danger { background: #ff4d4f; color: #fff; }
.btn-danger:hover:not(:disabled) { background: #ff7875; }
.btn-success { background: #52c41a; color: #fff; }
.btn-success:hover:not(:disabled) { background: #73d13d; }
.btn-default { background: #fff; color: #333; border: 1px solid #d9d9d9; }
.btn-default:hover:not(:disabled) { background: #f5f5f5; }
.btn-sm { padding: 2px 10px; margin-right: 4px; font-size: 13px; }
.btn-sm2 { padding: 4px 12px; font-size: 13px; }
table { width: 100%; border-collapse: collapse; }
th, td { padding: 10px 12px; text-align: left; border-bottom: 1px solid #f0f0f0; }
th { background: #fafafa; color: #666; font-weight: 600; font-size: 13px; }
td { font-size: 14px; color: #333; }
tr:hover td { background: #e6f7ff; }
.empty-text { text-align: center; color: #999; }
.pagination { margin-top: 16px; display: flex; align-items: center; gap: 8px; }
.pagination span { font-size: 13px; color: #666; }
.pagination button { padding: 4px 12px; }
.dialog-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.4); z-index: 999; }
.dialog { position: fixed; top: 50%; left: 50%; transform: translate(-50%,-50%); background: #fff; padding: 24px; border-radius: 6px; z-index: 1000; min-width: 400px; }
.dialog h3 { margin-bottom: 16px; }
.dialog .form-group { margin-bottom: 12px; }
.dialog .form-group input { width: 100%; }
.dialog-btns { margin-top: 16px; display: flex; gap: 8px; justify-content: flex-end; }
.batch-dialog { min-width: 700px; }
.batch-toolbar { display: flex; gap: 8px; margin-bottom: 12px; align-items: center; }
.batch-toolbar span { font-size: 13px; color: #666; }
.count-input { width: 60px; padding: 4px 6px; border: 1px solid #d9d9d9; border-radius: 3px; font-size: 13px; }
.batch-table-wrapper { max-height: 400px; overflow: auto; }
.batch-table { width: 100%; border-collapse: collapse; margin-bottom: 12px; }
.batch-table th, .batch-table td { padding: 6px 8px; border-bottom: 1px solid #f0f0f0; }
.batch-table input { width: 100%; padding: 6px 8px; border: 1px solid #d9d9d9; border-radius: 3px; font-size: 13px; box-sizing: border-box; }
</style>
