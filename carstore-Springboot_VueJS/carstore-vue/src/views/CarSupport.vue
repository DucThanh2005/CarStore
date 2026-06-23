<template>
  <div class="container cs-container py-4">
    <div class="card p-4 border rounded-4 bg-light shadow-sm text-center mb-4">
      <h1 class="fw-bold text-dark mb-2">Hỗ Trợ Khách Hàng</h1>
      <p class="text-secondary">
        Mọi câu hỏi của bạn đều được ban điều phối tư vấn viên phản hồi trong vòng 15 phút.
      </p>
    </div>

    <div class="row g-4">
      <div class="col-12 col-md-7">
        <div class="card p-4 border rounded-4 bg-white shadow-sm h-100">
          <h4 class="fw-bold mb-3 border-bottom pb-2">Câu hỏi thường gặp (FAQ)</h4>

          <div class="accordion accordion-flush" id="faqAccordion">
            <div class="accordion-item" v-for="(f, i) in faqs" :key="i">
              <h2 class="accordion-header" :id="'heading' + i">
                <button
                  class="accordion-button collapsed fw-semibold text-dark"
                  type="button"
                  data-bs-toggle="collapse"
                  :data-bs-target="'#collapse' + i"
                >
                  {{ f.q }}
                </button>
              </h2>

              <div
                :id="'collapse' + i"
                class="accordion-collapse collapse"
                data-bs-parent="#faqAccordion"
              >
                <div class="accordion-body text-secondary small">
                  {{ f.a }}
                </div>
              </div>
            </div>
          </div>

          <router-link to="/car/history" class="btn btn-outline-dark w-100 mt-4 rounded-pill">
            Xem lịch sử yêu cầu
          </router-link>
        </div>
      </div>

      <div class="col-12 col-md-5">
        <div class="card p-4 border rounded-4 bg-white shadow-sm h-100">
          <h4 class="fw-bold mb-3 border-bottom pb-2">Gửi phản hồi trực tiếp</h4>

          <div class="mb-3">
            <label class="form-label small text-muted">Họ và tên</label>
            <input
              v-model="form.name"
              type="text"
              class="form-control rounded-pill"
              placeholder="Họ tên của bạn"
            />
          </div>

          <div class="mb-3">
            <label class="form-label small text-muted">Số điện thoại</label>
            <input
              v-model="form.phone"
              type="text"
              class="form-control rounded-pill"
              placeholder="Số điện thoại của bạn"
            />
          </div>

          <div class="mb-3">
            <label class="form-label small text-muted">Nội dung yêu cầu cần hỗ trợ</label>
            <textarea
              v-model="form.content"
              class="form-control"
              rows="4"
              placeholder="Viết thắc mắc về giá xe, thủ tục trả góp tại đây..."
            ></textarea>
          </div>

          <button class="btn btn-dark w-100 py-2 rounded-pill fw-bold" @click="submitSupport">
            Gửi câu hỏi
          </button>

          <div class="mt-4 p-3 bg-light rounded-3 text-center border">
            <div class="small text-muted fw-semibold">Hotline hỗ trợ khẩn cấp</div>
            <a href="tel:18001052" class="h4 text-danger fw-extrabold text-decoration-none">
              1800 1052 (Miễn phí)
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import axios from 'axios'

const form = reactive({
  name: '',
  phone: '',
  content: ''
})

const submitSupport = async () => {
  if (!form.name || !form.phone || !form.content) {
    alert('Vui lòng nhập đầy đủ họ tên, số điện thoại và nội dung!')
    return
  }

  try {
    const res = await axios.post('/api/support', {
      name: form.name,
      phone: form.phone,
      type: 'chat',
      content: form.content
    })

    if (res.data.success) {
      alert('Gửi phản hồi thành công!')
      form.name = ''
      form.phone = ''
      form.content = ''
    } else {
      alert(res.data.message || 'Gửi phản hồi thất bại!')
    }
  } catch (err) {
    console.error(err)
    alert('Lỗi kết nối API hỗ trợ!')
  }
}

const faqs = [
  {
    q: 'Thủ tục mua xe trả góp cần chuẩn bị giấy tờ gì?',
    a: 'Bạn chỉ cần chuẩn bị Căn cước công dân gắn chip, Giấy chứng nhận độc thân hoặc Đăng ký kết hôn. Showroom sẽ hỗ trợ gói vay tối đa 80% giá trị xe.'
  },
  {
    q: 'Thời gian nhận xe sau khi hoàn tất đặt cọc trực tuyến là bao lâu?',
    a: 'Đối với xe có sẵn trong kho hàng, bạn sẽ nhận xe tận nhà sau từ 3 - 5 ngày làm việc.'
  },
  {
    q: 'Tôi có thể đổi lịch hoặc hủy lịch hẹn bảo dưỡng đã đặt trước không?',
    a: 'Hoàn toàn được. Bạn chỉ cần truy cập mục Dịch vụ hoặc gọi tổng đài trước giờ hẹn ít nhất 1 tiếng.'
  }
]
</script>