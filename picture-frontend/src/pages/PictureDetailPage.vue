<template>
  <div id="pictureDetailPage">
    <a-row :gutter="[16, 16]">
      <!-- 图片预览 -->
      <a-col :sm="24" :md="16" :xl="18">
        <a-card title="图片预览">
          <a-image :src="picture.url" style="max-height: 600px; object-fit: contain" />
        </a-card>
      </a-col>
      <!-- 图片信息区域 -->
      <a-col :sm="24" :md="8" :xl="6">
        <a-card title="图片信息">
          <a-descriptions :column="1">
            <a-descriptions-item label="作者">
              <a-space>
                <a-avatar :size="24" :src="picture.user?.userAvatar" />
                <div>{{ picture.user?.userName }}</div>
              </a-space>
            </a-descriptions-item>
            <a-descriptions-item label="名称">
              {{ picture.name ?? '未命名' }}
            </a-descriptions-item>
            <a-descriptions-item label="简介">
              {{ picture.introduction ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="分类">
              {{ picture.category ?? '默认' }}
            </a-descriptions-item>
            <a-descriptions-item label="标签">
              <a-tag v-for="tag in picture.tags" :key="tag">
                {{ tag }}
              </a-tag>
            </a-descriptions-item>
            <a-descriptions-item label="格式">
              {{ picture.picFormat ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="宽度">
              {{ picture.picWidth ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="高度">
              {{ picture.picHeight ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="宽高比">
              {{ picture.picScale ?? '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="大小">
              {{ formatSize(picture.picSize) }}
            </a-descriptions-item>
            <a-descriptions-item label="主色调">
              <a-space>
                {{ picture.picColor ?? '-' }}
                <div
                  v-if="picture.picColor"
                  :style="{
                    width: '16px',
                    height: '16px',
                    backgroundColor: toHexColor(picture.picColor),
                  }"
                />
              </a-space>
            </a-descriptions-item>
          </a-descriptions>
          <!-- 图片操作 -->
          <a-space wrap>
            <a-button type="primary" @click="doDownload">
              免费下载
              <template #icon>
                <DownloadOutlined />
              </template>
            </a-button>
            <a-button :icon="h(ShareAltOutlined)" type="primary" ghost @click="doShare">
              分享
            </a-button>
            <a-button v-if="canEdit" :icon="h(EditOutlined)" type="default" @click="doEdit">
              编辑
            </a-button>
            <a-button v-if="canDelete" :icon="h(DeleteOutlined)" danger @click="doDelete">
              删除
            </a-button>
          </a-space>
        </a-card>
      </a-col>
    </a-row>
    <ShareModal ref="shareModalRef" :link="shareLink" />
  </div>
</template>

<script setup lang="ts">
import { computed, h, onMounted, ref } from 'vue'
import { deletePictureUsingPost, getPictureVoByIdUsingGet } from '@/api/pictureController.ts'
import { message } from 'ant-design-vue'
import {
  DeleteOutlined,
  DownloadOutlined,
  EditOutlined,
  ShareAltOutlined,
} from '@ant-design/icons-vue'
import { useRouter } from 'vue-router'
import { downloadImage, formatSize, toHexColor } from '@/utils'
import ShareModal from '@/components/ShareModal.vue'
import { SPACE_PERMISSION_ENUM } from '@/constants/space.ts'

interface Props {
  id: string | number
}

const props = defineProps<Props>()
const picture = ref<API.PictureVO>({})

// 通用权限检查函数
function createPermissionChecker(permission: string) {
  return computed(() => {
    return (picture.value.permissionList ?? []).includes(permission)
  })
}

// 定义权限检查
const canEdit = createPermissionChecker(SPACE_PERMISSION_ENUM.PICTURE_EDIT)
const canDelete = createPermissionChecker(SPACE_PERMISSION_ENUM.PICTURE_DELETE)

// 获取图片详情
const fetchPictureDetail = async () => {
  try {
    const res = await getPictureVoByIdUsingGet({
      id: props.id,
    })
    if (res.data.code === 0 && res.data.data) {
      picture.value = res.data.data
    } else {
      message.error('获取图片详情失败，' + res.data.message)
    }
  } catch (e: any) {
    message.error('获取图片详情失败：' + e.message)
  }
}

onMounted(() => {
  fetchPictureDetail()
})

const router = useRouter()

// 编辑
const doEdit = () => {
  router.push({
    path: '/add_picture',
    query: {
      id: picture.value.id,
      spaceId: picture.value.spaceId,
    },
  })
}

// 删除数据
const doDelete = async () => {
  const id = picture.value.id
  if (!id) {
    return
  }
  const res = await deletePictureUsingPost({ id })
  if (res.data.code === 0) {
    message.success('删除成功')
  } else {
    message.error('删除失败')
  }
}

// 下载图片
const doDownload = () => {
  downloadImage(picture.value.url)
}

// ----- 分享操作 ----
const shareModalRef = ref()
// 分享链接
const shareLink = ref<string>()
// 分享
const doShare = () => {
  shareLink.value = `${window.location.protocol}//${window.location.host}/picture/${picture.value.id}`
  if (shareModalRef.value) {
    shareModalRef.value.openModal()
  }
}
</script>

<style scoped>
#pictureDetailPage {
  margin-bottom: 16px;
}

#pictureDetailPage :deep(.ant-card) {
  background: var(--tech-bg-container, #1a1a2e);
  border: 1px solid var(--tech-border, #2d2d44);
  border-radius: 12px;
}

#pictureDetailPage :deep(.ant-card-head) {
  border-bottom-color: var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
}

#pictureDetailPage :deep(.ant-card-head-title) {
  color: var(--tech-text, #e4e4e7);
}

#pictureDetailPage :deep(.ant-descriptions-item-label) {
  color: var(--tech-text-secondary, #a1a1aa);
}

#pictureDetailPage :deep(.ant-descriptions-item-content) {
  color: var(--tech-text, #e4e4e7);
}

#pictureDetailPage :deep(.ant-tag) {
  background: rgba(99, 102, 241, 0.1);
  border-color: rgba(99, 102, 241, 0.3);
  color: var(--tech-primary-hover, #818cf8);
}

#pictureDetailPage :deep(.ant-btn-primary) {
  background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%);
  border: none;
  box-shadow: 0 0 15px rgba(99, 102, 241, 0.3);
}

#pictureDetailPage :deep(.ant-btn-primary:hover) {
  box-shadow: 0 0 25px rgba(99, 102, 241, 0.5);
}

#pictureDetailPage :deep(.ant-btn-primary.ant-btn-background-ghost) {
  background: transparent;
  border: 1px solid var(--tech-primary, #6366f1);
  color: var(--tech-primary, #6366f1);
  box-shadow: none;
}

#pictureDetailPage :deep(.ant-btn-primary.ant-btn-background-ghost:hover) {
  border-color: var(--tech-primary-hover, #818cf8);
  color: var(--tech-primary-hover, #818cf8);
  box-shadow: 0 0 15px rgba(99, 102, 241, 0.2);
}

#pictureDetailPage :deep(.ant-btn-default) {
  background: var(--tech-bg-elevated, #16213e);
  border-color: var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
}

#pictureDetailPage :deep(.ant-btn-default:hover) {
  border-color: var(--tech-primary, #6366f1);
  color: var(--tech-primary, #6366f1);
}

#pictureDetailPage :deep(.ant-btn-dangerous) {
  background: rgba(239, 68, 68, 0.1);
  border-color: rgba(239, 68, 68, 0.5);
  color: #ef4444;
}

#pictureDetailPage :deep(.ant-btn-dangerous:hover) {
  background: rgba(239, 68, 68, 0.2);
  border-color: #ef4444;
}

#pictureDetailPage :deep(.ant-image) {
  display: flex;
  justify-content: center;
  background: var(--tech-bg-elevated, #16213e);
  border-radius: 8px;
  padding: 16px;
}

#pictureDetailPage :deep(.ant-avatar) {
  border: 2px solid var(--tech-border, #2d2d44);
}

#pictureDetailPage :deep(.ant-space) {
  margin-top: 16px;
}
</style>
