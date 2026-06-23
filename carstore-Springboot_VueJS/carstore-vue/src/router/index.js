import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import CarList from '../views/CarList.vue'
import CarDetail from '../views/CarDetail.vue'
import CartView from '../views/CartView.vue'
import LoginView from '../views/LoginView.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import CarServices from '../views/CarServices.vue'
import CarSupport from '../views/CarSupport.vue'
import ProfileView from '../views/ProfileView.vue'
import ServiceHistory from '../views/ServiceHistory.vue'

const routes = [
  { path: '/', redirect: '/car/list' },

  // Home hiện tại của bạn là trang danh sách xe chính
  { path: '/car/list', component: Home },

  // Inventory hiện tại của bạn là CarList.vue
  { path: '/car/inventory', component: CarList },

  { path: '/car/detail/:id', component: CarDetail },
  { path: '/cart/view', component: CartView },
  { path: '/login', component: LoginView },
  { path: '/profile', component: ProfileView },
  { path: '/admin/dashboard', component: AdminDashboard },

  { path: '/car/services', component: CarServices },
  { path: '/car/support', component: CarSupport },
  {
    path: '/car/history',
    name: 'ServiceHistory',
    component: ServiceHistory
  },

  { path: '/:pathMatch(.*)*', redirect: '/car/list' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router