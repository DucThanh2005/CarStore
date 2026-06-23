<template>
  <div>
    <header class="ford-header">
      <div class="ford-header-inner">

        <nav class="ford-nav-left">
          <router-link to="/car/inventory">Sản phẩm</router-link>
          <router-link to="/cart/view">
            Mua xe
            <span v-if="cartCount > 0" class="badge bg-danger ms-1">
              {{ cartCount }}
            </span>
          </router-link>
          <router-link to="/car/services">Dịch vụ</router-link>
          <router-link to="/car/support">Hỗ trợ</router-link>
        </nav>

        <router-link class="ford-logo" to="/car/list">CarStore</router-link>

        <div class="ford-nav-right">

          <router-link to="/car/inventory" class="ford-icon-btn" title="Đại lý / Danh sách" aria-label="Đại lý">
            <svg viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z" />
              <circle cx="12" cy="10" r="3" />
            </svg>
          </router-link>

          <router-link v-if="!user" to="/login" class="ford-icon-btn" title="Tài khoản" aria-label="Đăng nhập">
            <svg viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
              <circle cx="12" cy="7" r="4" />
            </svg>
          </router-link>

          <template v-else>
            <router-link to="/profile" class="ford-icon-btn" title="Tài khoản" aria-label="Tài khoản">
              <svg viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
            </router-link>

            <router-link v-if="user.role === 'ROLE_ADMIN'" to="/admin/dashboard"
              class="btn btn-sm btn-danger rounded-pill px-3 me-1"
              style="font-size: 0.75rem; font-weight: 700; line-height: 1.5;">
              Admin
            </router-link>

            <a href="#" @click.prevent="logout" class="ford-icon-btn" title="Đăng xuất" aria-label="Đăng xuất">
              <svg viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                <polyline points="16 17 21 12 16 7" />
                <line x1="21" y1="12" x2="9" y2="12" />
              </svg>
            </a>
          </template>

          <button type="button" class="ford-icon-btn" id="fordSearchToggle" title="Tìm kiếm" aria-label="Tìm kiếm"
            @click="toggleSearchRow">
            <svg viewBox="0 0 24 24">
              <circle cx="11" cy="11" r="8" />
              <line x1="21" y1="21" x2="16.65" y2="16.65" />
            </svg>
          </button>

        </div>
      </div>
    </header>

    <router-view></router-view>

    <footer class="ford-footer mt-5">
      <div class="ford-footer-inner container">
        <div class="ford-footer-grid">
          <div class="ford-footer-col">
            <h3>Sản phẩm</h3>
            <router-link to="/car/inventory">Xe SUV</router-link>
            <router-link to="/car/inventory">Xe Thương Mại</router-link>
          </div>

          <div class="ford-footer-col">
            <h3>Mua Xe</h3>
            <router-link to="/cart/view">Quản lý đặt cọc</router-link>
            <router-link to="/car/inventory">Kiểm tra xe có sẵn</router-link>
            <router-link to="/car/inventory">Lựa chọn mẫu xe và báo giá</router-link>
            <router-link to="/car/inventory">Bảng giá</router-link>
          </div>

          <div class="ford-footer-col">
            <h3>Dịch Vụ</h3>
            <router-link to="/car/services">Đặt lịch dịch vụ</router-link>
            <router-link to="/car/services">Báo giá dịch vụ</router-link>
            <router-link to="/car/services">Lịch bảo dưỡng định kỳ</router-link>
            <router-link to="/car/support">Nhận & giao xe tận nơi</router-link>
          </div>

          <div class="ford-footer-col">
            <h3>FORD VIỆT NAM</h3>
            <router-link to="/car/support">Lịch sử công ty</router-link>
            <router-link to="/car/support">Tin tức & Sự kiện</router-link>
            <router-link to="/car/support">Hỗ trợ cộng đồng</router-link>
          </div>
        </div>

        <div class="ford-footer-bottom">
          <p class="mb-0">Theo dõi Ford</p>
          <div class="ford-footer-socials">
            <a href="#" aria-label="Facebook">F</a>
            <a href="#" aria-label="YouTube">Y</a>
            <a href="#" aria-label="Instagram">I</a>
            <a href="#" aria-label="Zalo">Z</a>
            <a href="#" aria-label="TikTok">T</a>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import './assets/app-oem.css';

axios.defaults.withCredentials = true;

const user = ref(null);
const cartCount = ref(0);
const router = useRouter();

const checkSession = () => {
  const savedUser = localStorage.getItem('user');
  if (savedUser) {
    user.value = JSON.parse(savedUser);
  } else {
    user.value = null;
  }
};

const updateCartCount = () => {
  axios.get('/api/cart')
    .then(res => {
      if (res.data.success) {
        cartCount.value = res.data.items
          ? res.data.items.length
          : (res.data.count || 0);
      }
    })
    .catch(() => {
      cartCount.value = 0;
    });
};

const toggleSearchRow = () => {
  window.dispatchEvent(new Event('toggle-vue-search'));
};

const logout = () => {
  axios.post('/logout')
    .finally(() => {
      localStorage.removeItem('user');
      user.value = null;
      cartCount.value = 0;
      alert('Đã đăng xuất khỏi hệ thống!');
      router.push('/car/list');
    });
};

onMounted(() => {
  checkSession();
  updateCartCount();

  window.addEventListener('cart-updated', updateCartCount);
  window.addEventListener('user-updated', checkSession);
});

onBeforeUnmount(() => {
  window.removeEventListener('cart-updated', updateCartCount);
  window.removeEventListener('user-updated', checkSession);
});

defineExpose({
  checkSession,
  updateCartCount
});
</script>