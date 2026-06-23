<template>
  <div class="container cs-container py-4">
    <div class="cs-hero mb-4 bg-light p-4 rounded-4 border">
      <h1 class="cs-page-title m-0">🛒 Giỏ hàng đặt cọc của bạn</h1>
    </div>
    <div class="row g-4">
      <div class="col-12 col-lg-8">
        <div class="cs-card p-4 bg-white border rounded-4 shadow-sm">
          <div class="table-responsive">
            <table class="table align-middle">
              <thead>
                <tr>
                  <th>Mẫu xe</th>
                  <th class="text-end">Đơn giá</th>
                  <th class="text-center">Số lượng</th>
                  <th class="text-end">Tổng cộng</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in items" :key="item.id">
                  <td class="fw-bold text-uppercase">{{ item.name }}</td>
                  <td class="text-end">{{ formatMoney(item.price) }}</td>
                  <td class="text-center">
                    <button class="btn btn-sm btn-light border py-0 px-2"
                      @click="changeQty(item.id, 'decrement')">-</button>
                    <span class="mx-2 fw-bold">{{ item.quantity }}</span>
                    <button class="btn btn-sm btn-light border py-0 px-2"
                      @click="changeQty(item.id, 'increment')">+</button>
                  </td>
                  <td class="text-end text-primary fw-bold">{{ formatMoney(item.price * item.quantity) }}</td>
                  <td class="text-end">
                    <button class="btn btn-danger btn-sm rounded-pill" @click="removeItem(item.id)">Xóa</button>
                  </td>
                </tr>
                <tr v-if="items.length === 0">
                  <td colspan="5" class="text-center text-muted py-4">Giỏ hàng trống rỗng!</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="col-12 col-lg-4" v-if="items.length > 0">
        <div class="cs-card p-4 bg-white border rounded-4 shadow-sm">
          <h4 class="fw-bold border-bottom pb-2">Thông tin nhận xe</h4>
          <div class="mb-3">
            <label class="form-label small text-muted">Địa chỉ thường trú (đăng ký biển số)</label>
            <textarea class="form-control" v-model="address" placeholder="Nhập địa chỉ chính xác..."
              rows="3"></textarea>
          </div>
          <hr>
          <div class="d-flex justify-content-between align-items-center mb-3">
            <span>Tổng thanh toán:</span>
            <span class="h4 text-danger fw-bold m-0">{{ formatMoney(total) }}</span>
          </div>
          <button class="btn btn-success w-100 py-2 rounded-pill fw-bold" @click="handleCheckout">Gửi yêu cầu đặt xe
            ngay</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      items: [],
      total: 0,
      address: ''
    };
  },
  mounted() {
    this.loadCart();
  },
  methods: {
    // Gọi API lấy dữ liệu giỏ hàng chuẩn Map JSON từ Spring Boot
    loadCart() {
      axios.get('/api/cart')
        .then(res => {
          if (res.data.success) {
            this.items = [...(res.data.items || [])];
            this.total = res.data.total || 0;
          }
        })
        .catch(err => {
          console.error("Không lấy được dữ liệu giỏ hàng:", err);
        });
    },

    // Xử lý tăng giảm số lượng chuẩn xác theo API mới không bị lệch giá thành phần
    changeQty(id, type) {
      const endpoint =
        type === 'increment'
          ? `/api/cart/increment/${id}`
          : `/api/cart/decrement/${id}`;

      axios.post(endpoint)
        .then(res => {
          if (res.data.success) {
            this.loadCart();
          } else {
            alert(res.data.message || 'Không cập nhật được số lượng!');
          }
        })
        .catch(err => {
          console.error('API ERROR:', err);
          alert('Lỗi cập nhật số lượng!');
        });
    },

    // Gọi API xóa sản phẩm cho VueJS
    removeItem(id) {
      axios.delete(`/api/cart/remove/${id}`)
        .then(res => {
          if (res.data.success) {
            this.loadCart();
          } else {
            alert(res.data.message || 'Không xóa được sản phẩm!');
          }
        })
        .catch(err => {
          console.error(err);
          alert('Lỗi khi xóa sản phẩm!');
        });
    },

    handleCheckout() {

      if (!this.address.trim()) {
        return alert("Vui lòng nhập địa chỉ nhận xe!");
      }

      axios.post('/api/orders/checkout', {
        address: this.address
      })
        .then(res => {

          if (res.data.success) {

            alert("Đặt xe thành công! Điều phối viên Showroom sẽ liên hệ ngay.");

            this.items = [];
            this.total = 0;
            window.dispatchEvent(new Event('cart-updated'));
            
            this.$router.push('/car/list');
          } else {
            alert(res.data.message);
          }

        })
        .catch(err => {
          console.error(err);
          alert("Có lỗi khi đặt hàng!");
        });
    },

    formatMoney(val) {
      return Number(val).toLocaleString('vi-VN') + " VNĐ";
    }
  }
}
</script>