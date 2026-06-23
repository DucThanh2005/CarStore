<template>
  <div class="container cs-container py-4">
    <div class="cs-hero mb-4">
      <div class="cs-badge mb-3">🚗 Inventory</div>
      <h1 class="cs-page-title mb-0">Hệ thống kho xe toàn quốc</h1>
      <div class="cs-muted">Lọc và tìm kiếm các dòng xe Ford có sẵn, hỗ trợ giao xe tận showroom gần nhất.</div>
    </div>

    <nav class="ford-nav-left">
      <router-link to="/car/inventory">Sản phẩm</router-link>
      <router-link to="/cart/view">Mua xe</router-link>
      <router-link to="/car/services">Dịch vụ</router-link>
      <router-link to="/car/support">Hỗ trợ</router-link>
    </nav>

    <router-link class="ford-logo" to="/car/list">CarStore</router-link>

    <div class="ford-inventory-grid">

      <aside class="ford-filter-panel">
        <div class="card p-3 border rounded-4 bg-white shadow-sm mb-3">
          <h5 class="fw-bold mb-3 text-dark d-flex align-items-center gap-2">
            Bộ lọc kho xe
          </h5>
          <hr class="my-2">

          <div class="mb-3">
            <label class="form-label small fw-bold text-muted text-uppercase mb-1">Mức giá tối đa</label>
            <input type="range" class="form-range" min="500000000" max="5000000000" step="50000000"
              v-model="filter.maxPrice" @input="applyFilter">
            <div class="text-end fw-bold text-danger small mt-1">
              {{ Number(filter.maxPrice).toLocaleString('vi-VN') }} VNĐ
            </div>
          </div>

          <div class="mb-2">
            <label class="form-label small fw-bold text-muted text-uppercase mb-1">Màu ngoại thất</label>
            <select class="form-select form-select-sm rounded-pill" v-model="filter.color" @change="applyFilter">
              <option value="">Tất cả các màu</option>
              <option value="Đen">Đen</option>
              <option value="Trắng">Trắng</option>
              <option value="Đỏ">Đỏ</option>
              <option value="Xanh">Xanh</option>
              <option value="Xanh đen">Xanh đen</option>
            </select>
          </div>

          <button class="btn btn-light btn-sm border w-100 rounded-pill mt-3 small" @click="resetFilter">
            Xóa bộ lọc
          </button>
        </div>

        <div class="ford-model-nav card p-2 border rounded-4 bg-white shadow-sm d-none d-md-flex">
          <a href="#xe-suv" class="active">Xe SUV</a>
          <a href="#xe-thuong-mai">Xe Thương Mại</a>
          <a href="#xe-dien">Xe Điện</a>
        </div>
      </aside>

      <main>
        <div class="ford-inventory-hero mb-4">
          <div>
            <h2>Khám phá ngay các mẫu xe sẵn có</h2>
          </div>
          <div class="ford-hero-actions">
            <a href="#" @click.prevent>Báo giá</a>
            <a href="#" @click.prevent>Chọn màu xe</a>
            <a href="#" @click.prevent>Lái thử</a>
          </div>
        </div>

        <section class="ford-category-section" id="xe-suv">
          <div class="d-flex flex-column flex-md-row justify-content-between align-items-start gap-3 mb-3">
            <div>
              <h3>Xe SUV</h3>
              <p class="ford-category-subtitle">Những mẫu SUV ấn tượng, phong cách và mạnh mẽ cho mọi hành trình.</p>
            </div>
            <a href="#xe-thuong-mai" class="ford-link-muted">Xem thêm xe thương mại</a>
          </div>

          <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col" v-for="car in suvCars" :key="car.id">
              <article class="ford-vehicle-card">
                <div class="ford-vehicle-card-media">
                  <img :src="getCarImage(car.image)" :alt="car.name" />
                </div>
                <div class="ford-vehicle-card-body">
                  <h3>{{ car.name }}</h3>
                  <p>Giá từ: {{ Number(car.price).toLocaleString('vi-VN') }} VNĐ</p>
                  <div class="small text-muted mb-3">
                    Màu: <strong>{{ car.color }}</strong> | Đời: <strong>{{ car.year }}</strong>
                  </div>

                  <router-link class="ford-link-action mb-2" :to="'/car/detail/' + car.id">Xem chi tiết</router-link>

                  <div class="d-flex gap-2 mt-2">
                    <button type="button" @click="addToCart(car.id)"
                      class="btn btn-light btn-sm border flex-grow-1 rounded-pill fw-bold small">
                      🛒 Thêm giỏ
                    </button>
                    <button type="button" @click="depositCar(car.id)"
                      class="btn btn-primary btn-sm flex-grow-1 rounded-pill fw-bold small">
                      💳 Đặt cọc
                    </button>
                  </div>
                </div>
              </article>
            </div>
          </div>

          <div v-if="suvCars.length === 0" class="text-muted small py-3 ps-2">
            Không tìm thấy xe SUV nào phù hợp bộ lọc.
          </div>
        </section>

        <section class="ford-category-section mt-5" id="xe-thuong-mai">
          <div class="d-flex flex-column flex-md-row justify-content-between align-items-start gap-3 mb-3">
            <div>
              <h3>Xe Thương Mại</h3>
              <p class="ford-category-subtitle">
                Dòng xe thương mại bền bỉ, tải lớn và phù hợp cho doanh nghiệp vận tải.
              </p>
            </div>
            <a href="#xe-dien" class="ford-link-muted">Xem thêm xe điện</a>
          </div>

          <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col" v-for="car in commercialCars" :key="car.id">
              <article class="ford-vehicle-card">
                <div class="ford-vehicle-card-media">
                  <img :src="getCarImage(car.image)" :alt="car.name" />
                </div>
                <div class="ford-vehicle-card-body">
                  <h3>{{ car.name }}</h3>
                  <p>Giá từ: {{ Number(car.price).toLocaleString('vi-VN') }} VNĐ</p>
                  <div class="small text-muted mb-3">
                    Màu: <strong>{{ car.color }}</strong> | Đời: <strong>{{ car.year }}</strong>
                  </div>

                  <router-link class="ford-link-action mb-2" :to="'/car/detail/' + car.id">Xem chi tiết</router-link>

                  <div class="d-flex gap-2 mt-2">
                    <button type="button" @click="addToCart(car.id)"
                      class="btn btn-light btn-sm border flex-grow-1 rounded-pill fw-bold small">
                      🛒 Thêm giỏ
                    </button>
                    <button type="button" @click="depositCar(car.id)"
                      class="btn btn-primary btn-sm flex-grow-1 rounded-pill fw-bold small">
                      💳 Đặt cọc
                    </button>
                  </div>
                </div>
              </article>
            </div>
          </div>

          <div v-if="commercialCars.length === 0" class="text-muted small py-3 ps-2">
            Không tìm thấy xe thương mại nào phù hợp bộ lọc.
          </div>
        </section>

        <section class="ford-category-section mt-5" id="xe-dien">
          <div class="d-flex flex-column flex-md-row justify-content-between align-items-start gap-3 mb-3">
            <div>
              <h3>Xe Điện</h3>
              <p class="ford-category-subtitle">
                Tương lai xanh với những mẫu xe điện hiện đại và thân thiện môi trường.
              </p>
            </div>
            <a href="#xe-suv" class="ford-link-muted">Trở về SUV</a>
          </div>

          <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col" v-for="car in electricCars" :key="car.id">
              <article class="ford-vehicle-card">
                <div class="ford-vehicle-card-media">
                  <img :src="getCarImage(car.image)" :alt="car.name" />
                </div>
                <div class="ford-vehicle-card-body">
                  <h3>{{ car.name }}</h3>
                  <p>Giá từ: {{ Number(car.price).toLocaleString('vi-VN') }} VNĐ</p>
                  <div class="small text-muted mb-3">
                    Màu: <strong>{{ car.color }}</strong> | Đời: <strong>{{ car.year }}</strong>
                  </div>

                  <router-link class="ford-link-action mb-2" :to="'/car/detail/' + car.id">Xem chi tiết</router-link>

                  <div class="d-flex gap-2 mt-2">
                    <button type="button" @click="addToCart(car.id)"
                      class="btn btn-light btn-sm border flex-grow-1 rounded-pill fw-bold small">
                      🛒 Thêm giỏ
                    </button>
                    <button type="button" @click="depositCar(car.id)"
                      class="btn btn-primary btn-sm flex-grow-1 rounded-pill fw-bold small">
                      💳 Đặt cọc
                    </button>
                  </div>
                </div>
              </article>
            </div>
          </div>

          <div v-if="electricCars.length === 0" class="text-muted small py-3 ps-2">
            Không tìm thấy xe điện nào phù hợp bộ lọc.
          </div>
        </section>
      </main>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const cars = ref([]);
const suvCars = ref([]);
const commercialCars = ref([]);
const electricCars = ref([]);

const filter = ref({
  maxPrice: 5000000000,
  color: ''
});

const getCarImage = (image) => {
  if (!image) {
    return 'http://localhost:8082/images/camry.jpg';
  }

  if (image.startsWith('http://') || image.startsWith('https://')) {
    return image;
  }

  if (image.startsWith('/images/')) {
    return 'http://localhost:8082' + image;
  }

  if (image.startsWith('images/')) {
    return 'http://localhost:8082/' + image;
  }

  return 'http://localhost:8082/images/' + image;
};

const loadData = () => {
  axios.get('/api/cars')
    .then(res => {
      cars.value = Array.isArray(res.data) ? res.data : [];
      applyFilter();
    })
    .catch(err => {
      console.error("Lỗi đồng bộ dữ liệu API kho xe:", err);
      cars.value = [];
      applyFilter();
    });
};

const addToCart = (id) => {
  axios.post(`/api/cart/add/${id}`)
    .then(res => {
      if (res.data.success) {
        alert("🎉 Đã thêm xe vào giỏ hàng thành công!");
        window.dispatchEvent(new Event('cart-updated'));
      } else {
        alert(res.data.message || "Không thể thêm vào giỏ hàng!");
      }
    })
    .catch(err => {
      console.error("ADD CART ERROR:", err);
      alert("❌ Không thể thêm vào giỏ hàng!");
    });
};

const depositCar = (id) => {
  axios.post(`/api/cart/add/${id}`)
    .then(res => {
      if (res.data.success) {
        window.dispatchEvent(new Event('cart-updated'));
        router.push('/cart/view');
      } else {
        alert(res.data.message || "Không thể đặt cọc xe!");
      }
    })
    .catch(err => {
      console.error("DEPOSIT CART ERROR:", err);
      alert("❌ Không thể đặt cọc xe!");
    });
};

const applyFilter = () => {
  const result = cars.value.filter(car => {
    const matchPrice = Number(car.price) <= Number(filter.value.maxPrice);
    const matchColor = filter.value.color ? car.color === filter.value.color : true;
    return matchPrice && matchColor;
  });

  suvCars.value = result.filter(c =>
    c.brandId === 1 ||
    c.name.toLowerCase().includes('everest') ||
    c.name.toLowerCase().includes('explorer') ||
    c.name.toLowerCase().includes('raptor') ||
    c.name.toLowerCase().includes('x5')
  );

  commercialCars.value = result.filter(c =>
    c.brandId === 2 ||
    c.name.toLowerCase().includes('transit') ||
    c.name.toLowerCase().includes('ranger') ||
    c.name.toLowerCase().includes('civic') ||
    c.name.toLowerCase().includes('hero-banner1')
  );

  electricCars.value = result.filter(c =>
    c.brandId === 3 ||
    c.name.toLowerCase().includes('mach-e') ||
    c.name.toLowerCase().includes('electric') ||
    c.name.toLowerCase().includes('camry') ||
    c.name.toLowerCase().includes('c300') ||
    c.name.toLowerCase().includes('hero-banner')
  );
};

const resetFilter = () => {
  filter.value = {
    maxPrice: 5000000000,
    color: ''
  };
  applyFilter();
};

onMounted(() => {
  loadData();
});
</script>