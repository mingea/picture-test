<template>
  <div class="picture-list">
    <!-- 图片列表 -->
    <a-list
      :grid="{ gutter: 16, xs: 1, sm: 2, md: 3, lg: 4, xl: 5, xxl: 6 }"
      :data-source="dataList"
      :loading="loading"
    >
      <template #renderItem="{ item: picture }">
        <a-list-item style="padding: 0">
          <!-- 单张图片 -->
          <a-card hoverable @click="doClickPicture(picture)">
            <template #cover>
              <img
                :alt="picture.name"
                :src="picture.thumbnailUrl ?? picture.url"
                style="height: 180px; object-fit: cover"
              />
            </template>
            <a-card-meta :title="picture.name">
              <template #description>
                <a-flex>
                  <a-tag color="green">
                    {{ picture.category ?? '默认' }}
                  </a-tag>
                  <a-tag v-for="tag in picture.tags" :key="tag">
                    {{ tag }}
                  </a-tag>
                </a-flex>
              </template>
            </a-card-meta>
            <template v-if="showOp" #actions>
              <ShareAltOutlined @click="(e) => doShare(picture, e)" />
              <SearchOutlined @click="(e) => doSearch(picture, e)" />
              <EditOutlined v-if="canEdit" @click="(e) => doEdit(picture, e)" />
              <DeleteOutlined v-if="canDelete" @click="(e) => doDelete(picture, e)" />
            </template>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
    <ShareModal ref="shareModalRef" :link="shareLink" />
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import {
  DeleteOutlined,
  EditOutlined,
  SearchOutlined,
  ShareAltOutlined,
} from '@ant-design/icons-vue'
import { deletePictureUsingPost } from '@/api/pictureController.ts'
import { message } from 'ant-design-vue'
import ShareModal from '@/components/ShareModal.vue'
import { ref } from 'vue'

interface Props {
  dataList?: API.PictureVO[]
  loading?: boolean
  showOp?: boolean
  canEdit?: boolean
  canDelete?: boolean
  onReload?: () => void
}

const props = withDefaults(defineProps<Props>(), {
  dataList: () => [],
  loading: false,
  showOp: false,
  canEdit: false,
  canDelete: false,
})

const router = useRouter()
// 跳转至图片详情页
const doClickPicture = (picture: API.PictureVO) => {
  router.push({
    path: `/picture/${picture.id}`,
  })
}

// 搜索
const doSearch = (picture, e) => {
  // 阻止冒泡
  e.stopPropagation()
  // 打开新的页面
  window.open(`/search_picture?pictureId=${picture.id}`)
}

// 编辑
const doEdit = (picture, e) => {
  // 阻止冒泡
  e.stopPropagation()
  // 跳转时一定要携带 spaceId
  router.push({
    path: '/add_picture',
    query: {
      id: picture.id,
      spaceId: picture.spaceId,
    },
  })
}

// 删除数据
const doDelete = async (picture, e) => {
  // 阻止冒泡
  e.stopPropagation()
  const id = picture.id
  if (!id) {
    return
  }
  const res = await deletePictureUsingPost({ id })
  if (res.data.code === 0) {
    message.success('删除成功')
    props.onReload?.()
  } else {
    message.error('删除失败')
  }
}

// ----- 分享操作 ----
const shareModalRef = ref()
// 分享链接
const shareLink = ref<string>()
// 分享
const doShare = (picture, e) => {
  // 阻止冒泡
  e.stopPropagation()
  shareLink.value = `${window.location.protocol}//${window.location.host}/picture/${picture.id}`
  if (shareModalRef.value) {
    shareModalRef.value.openModal()
  }
}
</script>

<style scoped>
.picture-list :deep(.ant-card) {
  background: var(--tech-bg-container, #1a1a2e);
  border: 1px solid var(--tech-border, #2d2d44);
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.picture-list :deep(.ant-card:hover) {
  border-color: rgba(99, 102, 241, 0.5);
  box-shadow:
    0 0 25px rgba(99, 102, 241, 0.15),
    0 0 50px rgba(168, 85, 247, 0.1);
  transform: translateY(-4px);
}

.picture-list :deep(.ant-card-cover) {
  overflow: hidden;
}

.picture-list :deep(.ant-card-cover img) {
  transition: transform 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.picture-list :deep(.ant-card:hover .ant-card-cover img) {
  transform: scale(1.08);
}

.picture-list :deep(.ant-card-body) {
  padding: 12px 16px;
}

.picture-list :deep(.ant-card-meta-title) {
  color: var(--tech-text, #e4e4e7);
  font-size: 14px;
}

.picture-list :deep(.ant-card-meta-description) {
  margin-top: 8px;
}

.picture-list :deep(.ant-tag) {
  background: rgba(99, 102, 241, 0.1);
  border-color: rgba(99, 102, 241, 0.3);
  color: var(--tech-primary-hover, #818cf8);
  border-radius: 4px;
  font-size: 12px;
}

.picture-list :deep(.ant-tag-green) {
  background: rgba(34, 197, 94, 0.1);
  border-color: rgba(34, 197, 94, 0.3);
  color: #22c55e;
}

.picture-list :deep(.ant-card-actions) {
  background: var(--tech-bg-elevated, #16213e);
  border-top: 1px solid var(--tech-border, #2d2d44);
}

.picture-list :deep(.ant-card-actions > li) {
  color: var(--tech-text-secondary, #a1a1aa);
  transition: color 0.3s ease;
}

.picture-list :deep(.ant-card-actions > li:hover) {
  color: var(--tech-primary, #6366f1);
}

.picture-list :deep(.ant-card-actions > li:not(:last-child)) {
  border-right-color: var(--tech-border, #2d2d44);
}

.picture-list :deep(.ant-list-item) {
  padding: 0 0 16px 0 !important;
}

.picture-list :deep(.ant-empty-description) {
  color: var(--tech-text-tertiary, #71717a);
}
</style>
