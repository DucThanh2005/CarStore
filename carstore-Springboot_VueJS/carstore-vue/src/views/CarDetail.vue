<template>
  <div class="container cs-container py-4" v-if="car">
    <div class="cs-hero mb-4 bg-dark text-white p-4 rounded-4">
      <div class="cs-badge mb-2 bg-secondary d-inline-block p-1 rounded small">🚗 Chi tiết dòng xe</div>
      <h1 class="cs-page-title mb-0">{{ car.name }}</h1>
    </div>
    <div class="row g-4">
      <div class="col-12 col-md-6">
        <div class="cs-card p-3 border rounded-4 bg-white text-center shadow-sm">
          <img
            :src="car.image ? 'http://localhost:8082/images/' + car.image : 'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=500'"
            class="img-fluid rounded-3" :alt="car.name">
        </div>
      </div>
      <div class="col-12 col-md-6">
        <div class="cs-card p-4 border rounded-4 bg-white h-100 shadow-sm">
          <h3 class="h4 mb-3 fw-bold">{{ car.name }}</h3>
          <div class="mb-3">
            <span class="text-muted">Giá niêm yết: </span>
            <span class="h5 fw-bold text-primary">{{ formatMoney(car.price) }}</span>
          </div>
          <div class="mb-3">
            <span class="text-muted">Mô tả sản phẩm:</span>
            <p class="mt-1 text-secondary">{{ car.description }}</p>
          </div>
          <div class="row g-2 mb-4">
            <div class="col-6">Năm sản xuất: <strong>{{ car.year }}</strong></div>
            <div class="col-6">Màu ngoại thất: <strong>{{ car.color }}</strong></div>
          </div>
          <div class="d-flex gap-2">
            <button class="btn btn-primary px-4 rounded-pill" @click="addToCart(car.id)">Thêm vào giỏ</button>
            <button class="btn btn-outline-secondary px-4 rounded-pill" @click="$router.push('/car/list')">Quay
              lại</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() { return { car: null }; },
  mounted() {
    axios.get(`/api/cars/${this.$route.params.id}`).then(res => {
      if (res.data.success) this.car = res.data.data;
    });
  },
  methods: {
    addToCart(id) {

      console.log("ADD CART:", id);

      axios.post(`/api/cart/add/${id}`)
        .then(res => {

          console.log("RESPONSE:", res.data);

          if (res.data.success) {
            alert("Thêm giỏ hàng thành công");
            window.dispatchEvent(new Event('cart-updated'));
          }
        })
        .catch(err => {
          console.error("ADD CART ERROR", err);
        });
    },

    formatMoney(val) {
      return Number(val).toLocaleString('vi-VN') + " VNĐ";
    }
  }
}
</script>
