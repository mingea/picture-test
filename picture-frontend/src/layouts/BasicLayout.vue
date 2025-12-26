<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout>
        <GlobalSider class="sider" />
        <a-layout-content class="content">
          <router-view />
        </a-layout-content>
      </a-layout>
      <a-layout-footer class="footer">
        <a href="https://mingea.github.io/" target="_blank"> 程序员：明</a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalSider from "@/components/GlobalSider.vue";
</script>

<style scoped>
#basicLayout .header {
  padding-inline: 20px;
  background: var(--tech-bg-header, #0d0d1a);
  border-bottom: 1px solid var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
  margin-bottom: 1px;
}

#basicLayout .sider {
  background: var(--tech-bg-sider, #12121f);
  border-right: 1px solid var(--tech-border, #2d2d44);
  padding-top: 20px;
}

#basicLayout :deep(.ant-menu-root) {
  background: transparent;
  border-bottom: none !important;
  border-inline-end: none !important;
}

#basicLayout .content {
  padding: 28px;
  background: var(--tech-bg-layout, #0f0f1a);
  margin-bottom: 28px;
  min-height: calc(100vh - 64px - 53px - 28px);
}

#basicLayout .footer {
  background: var(--tech-bg-header, #0d0d1a);
  border-top: 1px solid var(--tech-border, #2d2d44);
  padding: 16px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
  color: var(--tech-text-secondary, #a1a1aa);
}

#basicLayout .footer a {
  color: var(--tech-primary-hover, #818cf8);
  transition: color 0.3s ease;
}

#basicLayout .footer a:hover {
  color: var(--tech-primary, #6366f1);
}
</style>
