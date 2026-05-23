import { createRouter, createWebHistory } from 'vue-router'
import UserManagement from '@/components/UserManagement.vue'
import BilibiliRanking from '@/components/BilibiliRanking.vue'
import UpRanking from '@/components/UpRanking.vue'
import OpenApiBrowse from '@/components/OpenApiBrowse.vue'
import Login from '@/components/Login.vue'

const routes = [
  { path: '/login', name: 'Login', component: Login },
  { path: '/', name: 'UserManagement', component: UserManagement, meta: { requiresAuth: true } },
  { path: '/bilibili', name: 'BilibiliRanking', component: BilibiliRanking, meta: { requiresAuth: true } },
  { path: '/up-ranking', name: 'UpRanking', component: UpRanking, meta: { requiresAuth: true } },
  { path: '/open-api', name: 'OpenApiBrowse', component: OpenApiBrowse, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/')
  } else {
    next()
  }
})

export default router
