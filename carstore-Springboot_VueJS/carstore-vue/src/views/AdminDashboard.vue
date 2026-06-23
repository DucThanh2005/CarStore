<template>
  <div class="container py-4" v-if="stats">
    <div class="cs-hero bg-dark text-white p-4 rounded-4 mb-4 border">
      <span class="badge bg-danger mb-2">🧰 Control Panel</span>
      <h1 class="display-6 fw-bold m-0">Hệ Thống Quản lý Kinh Doanh</h1>
    </div>
    <div class="row g-3 mb-4">
      <div class="col-6 col-md-3" v-for="(val, key) in {'Dòng xe': stats.totalCars, 'Khách hàng': stats.totalUsers, 'Đơn đặt': stats.totalOrders, 'Hãng xe': stats.totalBrands}" :key="key">
        <div class="card p-3 shadow-sm border rounded-3 bg-white text-center">
          <div class="text-muted small mb-1">{{ key }}</div>
          <div class="h3 fw-bold text-primary m-0">{{ val }}</div>
        </div>
      </div>
    </div>
    <div class="card p-4 border rounded-4 bg-white shadow-sm">
      <h4 class="fw-bold mb-3">Danh Mục Quản Lý Xe Kho</h4>
      <div class="vstack gap-2">
        <div class="p-3 border rounded-3 bg-light d-flex justify-content-between align-items-center" v-for="c in cars" :key="c.id">
          <div>
            <span class="fw-bold text-uppercase">{{ c.name }}</span>
            <div class="small text-muted">Giá bán: {{ Number(c.price).toLocaleString() }} VNĐ | Màu: {{ c.color }}</div>
          </div>
          <button class="btn btn-sm btn-outline-danger rounded-pill px-3" @click="deleteCar(c.id)">Xóa bỏ</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

axios.defaults.withCredentials = true;

export default {
  data() {
    return {
      stats: null,
      cars: []
    };
  },
  mounted() {
    const localUser = JSON.parse(localStorage.getItem('user'));

    if (!localUser || localUser.role !== 'ROLE_ADMIN') {
      alert("Quyền hạn của bạn bị từ chối truy cập!");
      this.$router.push('/login');
      return;
    }

    this.loadData();
  },
  methods: {
    loadData() {
      axios.get('/api/admin/stats')
        .then(res => {
          if (res.data.success) {
            this.stats = res.data;
          }
        })
        .catch(() => {
          alert('Không tải được dữ liệu thống kê!');
        });

      axios.get('/api/admin/cars')
        .then(res => {
          this.cars = res.data;
        })
        .catch(() => {
          this.cars = [];
        });
    },

    deleteCar(id) {
      if (confirm("Bạn có chắc chắn muốn xóa mẫu xe này?")) {
        axios.delete(`/api/admin/cars/${id}`)
          .then(res => {
            if (res.data.success) {
              alert('Xóa xe thành công!');
              this.loadData();
            } else {
              alert(res.data.message || 'Không thể xóa xe!');
            }
          })
          .catch(() => {
            alert('Lỗi khi xóa xe!');
          });
      }
    }
  }
}
</script>