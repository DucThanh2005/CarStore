<template>
  <div class="container py-5">
    <h2 class="text-center mb-4">📋 Lịch sử yêu cầu</h2>

    <div class="card shadow border-0">
      <div class="card-body">
        <table class="table table-bordered align-middle">
          <thead class="table-dark">
            <tr>
              <th>Tên</th>
              <th>SĐT</th>
              <th>Dịch vụ</th>
              <th>Nội dung</th>
              <th>Trạng thái</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="item in histories" :key="item.id">
              <td>{{ item.name }}</td>
              <td>{{ item.phone }}</td>
              <td>
                <span class="badge bg-info">{{ item.type }}</span>
              </td>
              <td>{{ item.content }}</td>
              <td>
                <span class="badge bg-warning text-dark">
                  {{ item.status }}
                </span>
              </td>
            </tr>

            <tr v-if="histories.length === 0">
              <td colspan="5" class="text-center text-muted">
                Chưa có yêu cầu nào
              </td>
            </tr>
          </tbody>
        </table>

        <div class="text-center mt-4">
          <router-link to="/car/services" class="btn btn-secondary">
            ← Quay lại
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const histories = ref([])

const loadHistory = async () => {
  try {
    const res = await axios.get('/api/support')
    if (res.data.success) {
      histories.value = res.data.data
    }
  } catch (err) {
    console.error('Lỗi tải lịch sử yêu cầu:', err)
  }
}

onMounted(() => {
  loadHistory()
})
</script>