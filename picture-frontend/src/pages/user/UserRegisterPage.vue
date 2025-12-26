<template>
  <div id="userRegisterPage" class="tech-particles-bg">
    <div class="register-container">
      <div class="register-card">
        <!-- Logo 区域 -->
        <div class="logo-area">
          <div class="logo-icon">
            <img src="@/assets/logo.png" alt="logo" class="logo-img" />
          </div>
          <h1 class="title tech-gradient-text">图库云</h1>
          <p class="subtitle">企业级智能协同云图库</p>
        </div>

        <!-- 注册表单 -->
        <a-form
          :model="formState"
          name="basic"
          autocomplete="off"
          @finish="handleSubmit"
          class="register-form"
        >
          <a-form-item name="userAccount" :rules="[{ required: true, message: '请输入账号' }]">
            <a-input
              v-model:value="formState.userAccount"
              placeholder="请输入账号"
              size="large"
              class="tech-input"
            >
              <template #prefix>
                <UserOutlined class="input-icon" />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item
            name="userPassword"
            :rules="[
              { required: true, message: '请输入密码' },
              { min: 8, message: '密码长度不能小于 8 位' },
            ]"
          >
            <a-input-password
              v-model:value="formState.userPassword"
              placeholder="请输入密码"
              size="large"
              class="tech-input"
            >
              <template #prefix>
                <LockOutlined class="input-icon" />
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item
            name="checkPassword"
            :rules="[
              { required: true, message: '请输入确认密码' },
              { min: 8, message: '确认密码长度不能小于 8 位' },
            ]"
          >
            <a-input-password
              v-model:value="formState.checkPassword"
              placeholder="请输入确认密码"
              size="large"
              class="tech-input"
            >
              <template #prefix>
                <SafetyOutlined class="input-icon" />
              </template>
            </a-input-password>
          </a-form-item>

          <div class="form-footer">
            <span class="tips">
              已有账号？
              <RouterLink to="/user/login" class="link">去登录</RouterLink>
            </span>
          </div>

          <a-form-item>
            <a-button type="primary" html-type="submit" size="large" class="register-button">
              注 册
            </a-button>
          </a-form-item>
        </a-form>

        <!-- 装饰线条 -->
        <div class="decoration-line"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { UserOutlined, LockOutlined, SafetyOutlined } from '@ant-design/icons-vue'
import { userRegisterUsingPost } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/useLoginUserStore.ts'
import { message } from 'ant-design-vue'
import router from '@/router'

const formState = reactive<API.UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

const loginUserStore = useLoginUserStore()

const handleSubmit = async (values: any) => {
  if (values.userPassword !== values.checkPassword) {
    message.error('两次输入的密码不一致')
    return
  }
  const res = await userRegisterUsingPost(values)
  if (res.data.code === 0 && res.data.data) {
    message.success('注册成功')
    router.push({
      path: '/user/login',
      replace: true,
    })
  } else {
    message.error('注册失败，' + res.data.message)
  }
}
</script>

<style scoped>
#userRegisterPage {
  min-height: calc(100vh - 64px - 53px);
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--tech-bg-layout, #0f0f1a);
  position: relative;
  margin: -28px;
  padding: 28px;
}

.register-container {
  position: relative;
  z-index: 1;
}

.register-card {
  width: 420px;
  padding: 48px 40px;
  background: var(--tech-bg-container, #1a1a2e);
  border-radius: 16px;
  border: 1px solid var(--tech-border, #2d2d44);
  position: relative;
  transition: all 0.3s ease;
}

.register-card:hover {
  border-color: rgba(99, 102, 241, 0.3);
  box-shadow: 0 0 40px rgba(99, 102, 241, 0.1);
}

.logo-area {
  text-align: center;
  margin-bottom: 40px;
}

.logo-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  border-radius: 20px;
  background: linear-gradient(135deg, rgba(99, 102, 241, 0.2), rgba(168, 85, 247, 0.2));
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid rgba(99, 102, 241, 0.3);
}

.logo-img {
  width: 50px;
  height: 50px;
}

.title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
}

.subtitle {
  color: var(--tech-text-secondary, #a1a1aa);
  font-size: 14px;
  margin: 0;
}

.register-form {
  margin-top: 24px;
}

.tech-input {
  background: var(--tech-bg-elevated, #16213e) !important;
  border-color: var(--tech-border, #2d2d44) !important;
  border-radius: 8px !important;
}

.tech-input:hover,
.tech-input:focus {
  border-color: var(--tech-primary, #6366f1) !important;
}

.input-icon {
  color: var(--tech-primary, #6366f1);
}

.form-footer {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 24px;
}

.tips {
  color: var(--tech-text-tertiary, #71717a);
  font-size: 13px;
}

.link {
  color: var(--tech-primary-hover, #818cf8);
  margin-left: 4px;
  transition: color 0.3s ease;
}

.link:hover {
  color: var(--tech-primary, #6366f1);
}

.register-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%) !important;
  border: none !important;
  box-shadow: 0 0 20px rgba(99, 102, 241, 0.3);
  transition: all 0.3s ease;
}

.register-button:hover {
  box-shadow: 0 0 30px rgba(99, 102, 241, 0.5);
  transform: translateY(-2px);
}

.decoration-line {
  position: absolute;
  bottom: -20px;
  left: 50%;
  transform: translateX(-50%);
  width: 60%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #6366f1, #a855f7, transparent);
  border-radius: 2px;
}

/* 响应式 */
@media (max-width: 480px) {
  .register-card {
    width: 100%;
    padding: 32px 24px;
    margin: 0 16px;
  }
}
</style>
