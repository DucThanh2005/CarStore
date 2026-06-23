<template>
  <div class="container cs-container py-4">
    <div class="bg-dark text-white p-5 rounded-4 mb-4 text-center shadow-sm">
      <span class="badge bg-primary text-uppercase px-3 py-2 rounded-pill mb-2">
        Ford Service
      </span>
      <h1 class="display-5 fw-bold">Trung Tâm Dịch Vụ Ủy Quyền</h1>
      <p class="lead mb-0">Bảo dưỡng định kỳ, sửa chữa chuyên nghiệp với linh kiện chuẩn toàn cầu.</p>
    </div>

    <div class="row g-4 mb-5">
      <div class="col-12 col-md-4" v-for="s in services" :key="s.title">
        <div class="card p-4 border rounded-4 h-100 shadow-sm text-center bg-white">
          <div class="fs-1 text-primary mb-3">
            <i :class="s.icon"></i>
          </div>
          <h4 class="fw-bold">{{ s.title }}</h4>
          <p class="text-secondary small">{{ s.desc }}</p>
        </div>
      </div>
    </div>

    <div class="card p-4 border rounded-4 bg-white shadow-sm max-width-md mx-auto" style="max-width: 600px;">
      <h3 class="fw-bold mb-3 text-center text-primary">Đặt lịch hẹn dịch vụ Online</h3>

      <div class="mb-3">
        <label class="form-label small text-muted">Họ và tên</label>
        <input v-model="form.name" type="text" class="form-control rounded-pill" placeholder="Tên của bạn" />
      </div>

      <div class="mb-3">
        <label class="form-label small text-muted">Số điện thoại</label>
        <input v-model="form.phone" type="text" class="form-control rounded-pill" placeholder="Số điện thoại" />
      </div>

      <div class="mb-3">
        <label class="form-label small text-muted">Biển số xe / Dòng xe của bạn</label>
        <input
          v-model="form.carInfo"
          type="text"
          class="form-control rounded-pill"
          placeholder="Ví dụ: 51G-123.45 hoặc Ford Ranger"
        />
      </div>

      <div class="mb-3">
        <label class="form-label small text-muted">Chọn loại dịch vụ cần thực hiện</label>
        <select v-model="form.type" class="form-select rounded-pill">
          <option value="Bảo dưỡng định kỳ">Bảo dưỡng định kỳ</option>
          <option value="Kiểm tra phanh lốp">Kiểm tra phanh, lốp xe</option>
          <option value="Sửa chữa máy gầm">Sửa chữa máy gầm, điều hòa</option>
          <option value="Đồng sơn">Đồng sơn, phục hồi thân vỏ xe</option>
        </select>
      </div>

      <div class="row g-2 mb-4">
        <div class="col-6">
          <label class="form-label small text-muted">Ngày hẹn</label>
          <input v-model="form.date" type="date" class="form-control rounded-pill" />
        </div>

        <div class="col-6">
          <label class="form-label small text-muted">Giờ hẹn</label>
          <input v-model="form.time" type="time" class="form-control rounded-pill" />
        </div>
      </div>

      <button class="btn btn-primary w-100 py-2 rounded-pill fw-bold" @click="submitService">
        Xác nhận đặt lịch
      </button>

      <router-link to="/car/history" class="btn btn-outline-dark w-100 mt-3 rounded-pill">
        Xem lịch sử yêu cầu
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import axios from 'axios'

const form = reactive({
  name: '',
  phone: '',
  carInfo: '',
  type: 'Bảo dưỡng định kỳ',
  date: '',
  time: ''
})

const submitService = async () => {
  if (!form.name || !form.phone || !form.carInfo || !form.date || !form.time) {
    alert('Vui lòng nhập đầy đủ thông tin đặt lịch!')
    return
  }

  const content = `Xe: ${form.carInfo} - Ngày hẹn: ${form.date} - Giờ hẹn: ${form.time}`

  try {
    const res = await axios.post('/api/support', {
      name: form.name,
      phone: form.phone,
      type: form.type,
      content: content
    })

    if (res.data.success) {
      alert('Đã gửi yêu cầu dịch vụ thành công!')
      form.name = ''
      form.phone = ''
      form.carInfo = ''
      form.type = 'Bảo dưỡng định kỳ'
      form.date = ''
      form.time = ''
    } else {
      alert(res.data.message || 'Gửi thất bại!')
    }
  } catch (err) {
    console.error(err)
    alert('Lỗi kết nối API dịch vụ!')
  }
}

const services = [
  {
    icon: 'bi bi-tools',
    title: 'Bảo Dưỡng Nhanh',
    desc: 'Chỉ mất 60 phút hoàn thành toàn bộ quy trình kiểm tra xe nghiêm ngặt.'
  },
  {
    icon: 'bi bi-shield-check',
    title: 'Bảo Hành Chính Hãng',
    desc: 'An tâm tuyệt đối với gói bảo hành mở rộng lên tới 5 năm hoặc 150.000km.'
  },
  {
    icon: 'bi bi-telephone-inbound',
    title: 'Cứu Hộ 24/7',
    desc: 'Đội ngũ kỹ thuật hỗ trợ tận nơi bất cứ lúc nào khi gặp sự cố trên mọi nẻo đường.'
  }
]
</script>