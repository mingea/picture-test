<template>
  <div id="app">
    <a-config-provider :locale="zhCN" :theme="darkThemeConfig">
      <BasicLayout />
    </a-config-provider>
  </div>
</template>

<script setup lang="ts">
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'
import { theme } from 'ant-design-vue'
import BasicLayout from '@/layouts/BasicLayout.vue'

dayjs.locale('zh-cn')

// 暗黑科技主题配置
const darkThemeConfig = {
  algorithm: theme.darkAlgorithm,
  token: {
    // 主色调 - 科技蓝紫
    colorPrimary: '#6366f1',
    // 背景色
    colorBgContainer: '#1a1a2e',
    colorBgElevated: '#16213e',
    colorBgLayout: '#0f0f1a',
    // 边框色
    colorBorder: '#2d2d44',
    colorBorderSecondary: '#252540',
    // 文本色
    colorText: '#e4e4e7',
    colorTextSecondary: '#a1a1aa',
    colorTextTertiary: '#71717a',
    // 功能色
    colorSuccess: '#22c55e',
    colorWarning: '#f59e0b',
    colorError: '#ef4444',
    colorInfo: '#3b82f6',
    // 链接色
    colorLink: '#818cf8',
    colorLinkHover: '#a5b4fc',
    // 圆角
    borderRadius: 8,
  },
  components: {
    Card: {
      colorBgContainer: '#1a1a2e',
      boxShadowTertiary: '0 0 20px rgba(99, 102, 241, 0.1)',
    },
    Menu: {
      colorItemBg: 'transparent',
      colorItemBgSelected: 'rgba(99, 102, 241, 0.15)',
      colorItemTextSelected: '#818cf8',
      colorItemBgHover: 'rgba(99, 102, 241, 0.1)',
    },
    Button: {
      primaryShadow: '0 0 15px rgba(99, 102, 241, 0.4)',
    },
    Input: {
      colorBgContainer: '#16213e',
      colorBorder: '#2d2d44',
      activeBorderColor: '#6366f1',
      hoverBorderColor: '#6366f1',
    },
    Select: {
      colorBgContainer: '#16213e',
      colorBorder: '#2d2d44',
      optionSelectedBg: 'rgba(99, 102, 241, 0.15)',
    },
    Table: {
      colorBgContainer: '#1a1a2e',
      headerBg: '#16213e',
      rowHoverBg: 'rgba(99, 102, 241, 0.08)',
    },
    Modal: {
      contentBg: '#1a1a2e',
      headerBg: '#16213e',
    },
    Tabs: {
      inkBarColor: '#6366f1',
      itemSelectedColor: '#6366f1',
      itemHoverColor: '#818cf8',
    },
    Pagination: {
      colorBgContainer: '#1a1a2e',
      colorBorder: '#2d2d44',
    },
  },
}
</script>

<style>
#app {
  min-height: 100vh;
  background: #0f0f1a;
}
</style>
