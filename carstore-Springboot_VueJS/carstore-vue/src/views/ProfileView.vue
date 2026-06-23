<template>
  <div class="container cs-container py-5">
    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <div class="card p-4 border rounded-4 bg-white shadow-sm">
          <div class="text-center mb-4">
            <div
              class="d-inline-flex align-items-center justify-content-center bg-primary text-white rounded-circle mb-3"
              style="width: 72px; height: 72px;">
              <svg viewBox="0 0 24 24" width="36" height="36" fill="none" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
            </div>
            <h2 class="fw-bold h4 m-0">Thông tin tài khoản cá nhân</h2>
            <p class="text-muted small mt-1">Quản lý thông tin và quyền hạn thành viên CarStore</p>
          </div>

          <div v-if="account" class="vstack gap-3 border-top pt-3">
            <div class="d-flex justify-content-between border-bottom pb-2">
              <span class="text-muted small fw-semibold">Tên tài khoản (Username):</span>
              <span class="fw-bold text-dark">{{ account.username }}</span>
            </div>
            <div class="d-flex justify-content-between border-bottom pb-2">
              <span class="text-muted small fw-semibold">Họ và tên khách hàng:</span>
              <span class="fw-bold text-primary">{{ account.fullname }}</span>
            </div>
            <div class="d-flex justify-content-between border-bottom pb-2">
              <span class="text-muted small fw-semibold">Địa chỉ thư điện tử:</span>
              <span class="fw-bold text-dark">{{ account.email }}</span>
            </div>
            <div class="d-flex justify-content-between border-bottom pb-2">
              <span class="text-muted small fw-semibold">Cấp bậc hệ thống (Role):</span>
              <span class="badge" :class="account.role === 'ROLE_ADMIN' ? 'bg-danger' : 'bg-success'">
                {{ account.role === 'ROLE_ADMIN' ? 'Quản trị viên' : 'Khách hàng liên kết' }}
              </span>
            </div>

            <div class="d-flex gap-2 mt-3">
              <router-link to="/car/list" class="btn btn-outline-secondary w-100 rounded-pill py-2 fw-semibold small">
                Quay lại trang chủ
              </router-link>
              <button @click="handleLogout" class="btn btn-danger w-100 rounded-pill py-2 fw-semibold small">
                Đăng xuất tài khoản
              </button>
            </div>
          </div>

          <div v-else class="text-center py-4 text-danger fw-bold">
            Vui lòng đăng nhập để xem thông tin hồ sơ!
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const account = ref(null);
const router = useRouter();

onMounted(() => {
  const savedUser = localStorage.getItem('user');
  if (savedUser) {
    account.value = JSON.parse(savedUser);
  } else {
    router.push('/login');
  }
});

const handleLogout = () => {
  axios.post('/logout')
    .finally(() => {
      localStorage.removeItem('user');
      window.dispatchEvent(new Event('user-updated'));
      alert('Đã đăng xuất tài khoản thành công!');
      router.push('/car/list');
    });
};
</script>