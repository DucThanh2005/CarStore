<template>
  <div>
    <div class="ford-search-row" :class="{ 'is-open': searchOpen }" :aria-hidden="!searchOpen ? 'true' : 'false'">
      <div class="ford-search-form">
        <input type="search" v-model="searchQuery" @keyup.enter="handleSearch" placeholder="Tìm kiếm theo tên xe..."
          autocomplete="off">
        <button type="button" @click="handleSearch">Tìm</button>
      </div>
    </div>

    <section class="ford-intro">
      <div class="ford-intro-grid">
        <div class="ford-intro-text text-center text-lg-start">
          <small class="ford-badge text-uppercase fw-bold">Trải nghiệm đỉnh cao</small>
          <h1 class="display-4 fw-bold">Khai Phá Mọi Hành Trình</h1>
          <p class="lead">Khám phá các dòng xe Ford mới nhất. Đặt cọc online, nhận xe tận nhà cùng nhiều ưu đãi hấp dẫn.
          </p>
          <div class="ford-intro-actions">
            <router-link class="ford-btn-primary" to="/car/inventory">Xem tất cả xe</router-link>
            <router-link class="ford-btn-outline" to="/cart/view">Giỏ hàng của bạn</router-link>
          </div>
        </div>
        <div class="ford-intro-image">
          <video autoplay muted loop playsinline class="ford-video-hero">
            <source src="http://localhost:8082/videos/ford-intro.mp4" type="video/mp4">
            Trình duyệt của bạn không hỗ trợ video.
          </video>
        </div>
      </div>
    </section>

    <div class="ford-section container">
      <div class="ford-section-head d-flex justify-content-between align-items-end mb-4">
        <div>
          <h2 class="fw-bold">Sản phẩm nổi bật</h2>
          <div class="ford-section-meta" v-if="searchQuery">
            Tìm kiếm: “<span class="text-primary fw-bold">{{ searchQuery }}</span>” ·
            <a href="#" @click.prevent="clearSearch" class="text-decoration-none">Xóa bộ lọc</a>
          </div>
        </div>
        <div class="d-flex gap-2" v-if="user && user.role === 'ROLE_ADMIN'">
          <router-link class="ford-btn-primary-sm" to="/admin/dashboard">Thêm xe mới</router-link>
          <router-link class="ford-btn-outline" to="/admin/dashboard">Báo cáo</router-link>
        </div>
      </div>

      <div class="row g-4">
        <div class="col-12 col-md-6 col-lg-4" v-for="car in cars" :key="car.id">
          <article class="ford-car-card h-100 shadow-sm">
            <div class="ford-car-img">
              <img
                :src="car.image ? 'http://localhost:8082/images/' + car.image : 'http://localhost:8082/images/camry.jpg'"
                :alt="car.name">
            </div>
            <div class="ford-car-body p-4">
              <small class="text-muted text-uppercase">Mã hãng: {{ car.brandId }}</small>
              <h3 class="h5 fw-bold my-2">{{ car.name }}</h3>

              <div class="ford-price-tag mb-3 fs-5 text-danger fw-bold">
                <span>{{ formatMoney(car.price) }}</span> <small>VNĐ</small>
              </div>

              <div class="d-grid gap-2 d-md-flex">
                <button class="ford-btn-primary w-100" @click="goToDetail(car.id)">Chi tiết</button>
                <button class="ford-btn-outline w-100" @click="addToCart(car.id)">Thêm giỏ hàng</button>
              </div>
              <div class="mt-2 text-center" v-if="user && user.role === 'ROLE_ADMIN'">
                <router-link to="/admin/dashboard" class="btn btn-sm btn-link text-secondary">Chỉnh sửa xe</router-link>
              </div>
            </div>
          </article>
        </div>
      </div>
      <div v-if="cars.length === 0" class="text-center text-muted py-5">Chưa có dữ liệu xe phù hợp trong hệ thống.</div>
    </div>

    <section class="ford-showcase container mt-5">
      <div class="ford-section-head mb-4">
        <div>
          <h2 class="fw-bold">Khám phá bộ sưu tập</h2>
          <p class="ford-section-subtitle">Những mẫu xe Ford dành cho mọi hành trình: phong cách, kinh tế, và công nghệ.
          </p>
        </div>
      </div>

      <div class="row g-4 align-items-stretch">
        <div class="col-12 col-lg-7">
          <article class="ford-feature-card">
            <div class="ford-feature-card-img">
              <img src="http://localhost:8082/images/hero-banner1.png" alt="Bộ sưu tập Ford">
            </div>
            <div class="ford-feature-card-body">
              <span class="ford-badge text-uppercase fw-bold">Nổi bật</span>
              <h3>Xe SUV & Sedan cho hành trình hiện đại</h3>
              <p>Trải nghiệm CarStore với các mẫu xe đa dạng từ SUV cá tính đến sedan sang trọng, phù hợp cho gia đình
                và doanh nghiệp.</p>
              <div class="d-flex flex-wrap gap-2">
                <router-link class="ford-btn-primary" to="/car/inventory">Xem xe SUV</router-link>
                <router-link class="ford-btn-outline" to="/car/inventory">Xem sedan</router-link>
              </div>
            </div>
          </article>
        </div>
        <div class="col-12 col-lg-5">
          <div class="ford-grid-small">
            <article class="ford-small-card bg-sky p-4 rounded-4 border mb-3">
              <div>
                <h4 class="fw-bold">Xe gia đình</h4>
                <p class="small text-muted">Tiện nghi tối ưu, không gian rộng, phù hợp cho mọi chuyến đi dài ngày.</p>
              </div>
              <router-link to="/car/inventory" class="ford-link-muted text-decoration-none fw-bold">Xem chi tiết
                →</router-link>
            </article>
            <article class="ford-small-card bg-rose p-4 rounded-4 border">
              <div>
                <h4 class="fw-bold">Xe thương mại</h4>
                <p class="small text-muted">Độ bền cao, vận hành ổn định cho doanh nghiệp và dịch vụ vận tải.</p>
              </div>
              <router-link to="/car/inventory" class="ford-link-muted text-decoration-none fw-bold">Khám phá ngay
                →</router-link>
            </article>
          </div>
        </div>
      </div>

      <div class="row g-4 mt-3">
        <div class="col-12 col-md-6 col-xl-3" v-for="item in showcaseProducts" :key="item.name">
          <article class="ford-product-card border rounded-4 p-3 bg-white shadow-sm h-100">
            <img :src="'http://localhost:8082/images/' + item.img" class="img-fluid rounded mb-2 w-100 object-fit-cover"
              style="height: 140px;" :alt="item.name">
            <div>
              <span class="ford-badge text-uppercase mb-1 d-inline-block">{{ item.tag }}</span>
              <h5 class="fw-bold m-0">{{ item.name }}</h5>
              <p class="small text-muted mt-1 mb-0">{{ item.desc }}</p>
            </div>
          </article>
        </div>
      </div>

      <div class="row g-4 mt-4">
        <div class="col-12">
          <div
            class="ford-showcase-banner bg-white border rounded-4 p-4 d-flex flex-column flex-md-row justify-content-between align-items-center gap-4">
            <div class="ford-showcase-banner-body">
              <span class="ford-badge text-uppercase fw-bold">Phong cách</span>
              <h3 class="fw-bold my-2">Không gian trải nghiệm dành cho mọi khách hàng</h3>
              <p class="text-muted">Chọn xe theo phong cách sống: sang trọng, thể thao, địa hình hay đa dụng — tất cả
                đều có tại CarStore.</p>
              <div class="d-flex flex-wrap gap-2 mt-3">
                <router-link class="ford-btn-primary" to="/car/inventory">Xem ngay</router-link>
                <router-link class="ford-btn-outline" to="/car/inventory">Khám phá ưu đãi</router-link>
              </div>
            </div>
            <div class="ford-showcase-banner-media" style="max-width: 380px;">
              <img src="http://localhost:8082/images/hero-banner.png" class="img-fluid rounded-3" alt="Ford Experience">
            </div>
          </div>
        </div>
      </div>

      <div class="row g-4 mt-4 mb-5">
        <div class="col-12 col-md-4">
          <article class="ford-info-card p-4 border rounded-4 bg-white h-100 shadow-sm">
            <h4 class="fw-bold text-dark">Tiết kiệm nhiên liệu</h4>
            <p class="small text-muted m-0">Đa dạng lựa chọn xe hybrid và máy xăng tiết kiệm, phù hợp cho chu trình di
              chuyển hàng ngày và gia đình.</p>
          </article>
        </div>
        <div class="col-12 col-md-4">
          <article class="ford-info-card ford-info-card-pale p-4 border rounded-4 h-100 shadow-sm"
            style="background-color: #f0fdf4;">
            <h4 class="fw-bold text-success">Công nghệ an toàn</h4>
            <p class="small text-muted m-0">Hệ thống hỗ trợ lái, cảnh báo va chạm và phanh tự động giúp hành trình yên
              tâm hơn mỗi ngày.</p>
          </article>
        </div>
        <div class="col-12 col-md-4">
          <article class="ford-info-card p-4 border rounded-4 bg-white h-100 shadow-sm">
            <h4 class="fw-bold text-dark">Đặt xe nhanh</h4>
            <p class="small text-muted m-0">Đặt cọc online, nhận xe nhanh chóng tại chi nhánh gần nhất hoặc giao tận nơi
              theo yêu cầu.</p>
          </article>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      cars: [],
      searchQuery: '',
      searchOpen: false, // Trạng thái đóng/mở thanh tìm kiếm trượt
      user: null,
      showcaseProducts: [
        { name: 'Ford Camry', tag: 'Sedan', img: 'camry.jpg', desc: 'Phong cách lịch lãm, công nghệ an toàn cao cấp.' },
        { name: 'Ford C300', tag: 'Hạng sang', img: 'c300.jpg', desc: 'Thiết kế tràn viền, vận hành êm ái.' },
        { name: 'Ford Civic', tag: 'Thành thị', img: 'civic.jpg', desc: 'Tiết kiệm nhiên liệu, cá tính trẻ trung.' },
        { name: 'Ford X5', tag: 'Đa dụng', img: 'x5.jpg', desc: 'Không gian linh hoạt, phù hợp mọi địa hình.' }
      ]
    };
  },
  watch: {
    '$route.query.q': {
      immediate: true,
      handler(newQ) {
        this.searchQuery = newQ || '';
        this.loadCars();
      }
    }
  },

  mounted() {
    this.checkUserSession();
    window.addEventListener('toggle-vue-search', this.toggleSearch);
  },

  beforeUnmount() {
    window.removeEventListener('toggle-vue-search', this.toggleSearch);
  },
  methods: {

    toggleSearch() {
      this.searchOpen = !this.searchOpen;
    },

    loadCars() {
      const url = this.searchQuery
        ? `/api/cars/search?keyword=${encodeURIComponent(this.searchQuery)}`
        : '/api/cars';
      axios.get(url).then(res => {
        if (this.searchQuery) {
          this.cars = res.data.success ? res.data.data : [];
        } else {
          this.cars = res.data;
        }
      }).catch(err => console.error("Lỗi API kết nối kho xe:", err));
    },
    checkUserSession() {
      const savedUser = localStorage.getItem('user');
      if (savedUser) this.user = JSON.parse(savedUser);
    },
    handleSearch() {
      if (!this.searchQuery.trim()) {
        this.clearSearch();
      } else {
        this.$router.push({ path: '/car/list', query: { q: this.searchQuery } });
      }
    },
    clearSearch() {
      this.searchQuery = '';
      this.$router.push('/car/list');
    },
    addToCart(id) {

      console.log("ADD CART:", id);

      axios.post(`/api/cart/add/${id}`)
        .then(res => {

          console.log("RESPONSE:", res.data);

          if (res.data.success) {
            alert("Đã thêm xe vào đơn đặt cọc thành công!");
            window.dispatchEvent(new Event('cart-updated'));
          } else {
            alert(res.data.message);
          }
        })
        .catch(err => {

          console.error("ADD CART ERROR:", err);

          if (err.response) {
            console.log(err.response.data);
          }

          alert("Không thể thêm vào giỏ hàng!");
        });
    },

    goToDetail(id) {
      this.$router.push(`/car/detail/${id}`);
    },
    formatMoney(val) {
      return val ? Number(val).toLocaleString('vi-VN') : "0";
    }
  }
};
</script>