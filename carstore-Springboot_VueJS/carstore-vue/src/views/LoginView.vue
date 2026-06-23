<template>
  <div class="container py-5 d-flex justify-content-center">
    <div class="card p-4 shadow-sm border rounded-4 bg-white" style="max-width: 420px; width: 100%;">

      <div class="d-flex justify-content-center gap-3 mb-4 border-bottom pb-2">
        <button class="btn border-0 fw-bold fs-5 pb-2"
          :class="isLoginTab ? 'text-primary border-bottom border-primary border-3' : 'text-muted'"
          @click="isLoginTab = true">Đăng nhập</button>
        <button class="btn border-0 fw-bold fs-5 pb-2"
          :class="!isLoginTab ? 'text-primary border-bottom border-primary border-3' : 'text-muted'"
          @click="isLoginTab = false">Đăng ký</button>
      </div>

      <div v-if="isLoginTab">
        <div class="form-group mb-3">
          <label class="form-label text-muted small">Tên đăng nhập</label>
          <input type="text" class="form-control rounded-pill" v-model="loginData.username" placeholder="Username">
        </div>
        <div class="form-group mb-3">
          <label class="form-label text-muted small">Mật khẩu</label>
          <input type="password" class="form-control rounded-pill" v-model="loginData.password" placeholder="••••••••"
            @keyup.enter="handleLogin">
        </div>
        <button class="btn btn-primary w-100 py-2 rounded-pill fw-bold mt-2" @click="handleLogin">Đăng nhập hệ
          thống</button>
      </div>

      <div v-else>
        <div class="form-group mb-2">
          <label class="form-label text-muted small">Tên đăng nhập *</label>
          <input type="text" class="form-control rounded-pill" v-model="signupData.username"
            placeholder="Nhập username mong muốn">
        </div>
        <div class="form-group mb-2">
          <label class="form-label text-muted small">Họ và tên</label>
          <input type="text" class="form-control rounded-pill" v-model="signupData.fullname"
            placeholder="Nhập họ và tên của bạn">
        </div>
        <div class="form-group mb-2">
          <label class="form-label text-muted small">Địa chỉ Email *</label>
          <input type="email" class="form-control rounded-pill" v-model="signupData.email"
            placeholder="example@domain.com">
        </div>
        <div class="form-group mb-3">
          <label class="form-label text-muted small">Mật khẩu *</label>
          <input type="password" class="form-control rounded-pill" v-model="signupData.password"
            placeholder="Tối thiểu 6 ký tự">
        </div>
        <button class="btn btn-success w-100 py-2 rounded-pill fw-bold" @click="handleSignup">Tạo tài khoản mới</button>
      </div>

      <div class="text-center border-top pt-3 mt-3">
        <p class="small text-muted mb-2">Hoặc liên kết nhanh qua</p>
        <a class="btn btn-outline-danger w-100 rounded-pill btn-sm" href="/oauth2/authorization/google">
          <i class="bi bi-google"></i> Đăng nhập bằng Google
        </a>
      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      isLoginTab: true,
      loginData: { username: '', password: '' },
      signupData: { username: '', fullname: '', email: '', password: '' }
    };
  },
  methods: {
    async handleLogin() {
      if (!this.loginData.username || !this.loginData.password) {
        return alert("Vui lòng nhập đầy đủ thông tin!");
      }

      try {
        const loginRes = await axios.post('/api/auth/login', {
          username: this.loginData.username,
          password: this.loginData.password
        }, {
          withCredentials: true
        });

        if (!loginRes.data.success) {
          alert(loginRes.data.message || 'Sai tài khoản hoặc mật khẩu!');
          return;
        }

        const profile = await axios.get("/api/profile", {
          withCredentials: true
        });

        if (profile.data.success) {
          localStorage.setItem("user", JSON.stringify(profile.data));

          alert("Đăng nhập thành công!");

          window.dispatchEvent(new Event('user-updated'));

          this.$router.push(
            profile.data.role === "ROLE_ADMIN"
              ? "/admin/dashboard"
              : "/car/list"
          );
        } else {
          alert(profile.data.message || "Không lấy được thông tin tài khoản!");
        }
      } catch (error) {
        alert("Tài khoản hoặc mật khẩu không chính xác!");
      }
    },
    handleSignup() {
      if (!this.signupData.username || !this.signupData.email || !this.signupData.password) {
        return alert("Vui lòng điền đầy đủ các trường thông tin bắt buộc (*)");
      }

      axios.post('/api/auth/signup', this.signupData).then(res => {
        if (res.data.success) {
          alert('Đăng ký tài khoản thành công! Hãy tiến hành đăng nhập.');
          this.loginData.username = this.signupData.username;
          this.signupData = { username: '', fullname: '', email: '', password: '' }; // Reset form đăng ký
          this.isLoginTab = true; // Chuyển về tab Đăng nhập
        } else {
          alert(res.data.message);
        }
      }).catch(err => alert('Lỗi hệ thống khi đăng ký: ' + err));
    }
  }
}
</script>