<template>
  <div id="homePage">
    <!-- 搜索框 -->
    <div class="search-bar">
      <a-input-search
        v-model:value="searchParams.searchText"
        placeholder="从海量图片中搜索"
        enter-button="搜索"
        size="large"
        @search="doSearch"
      />
    </div>
    <!-- 分类和标签筛选 -->
    <a-tabs v-model:active-key="selectedCategory" @change="doSearch">
      <a-tab-pane key="all" tab="全部" />
      <a-tab-pane v-for="category in categoryList" :tab="category" :key="category" />
    </a-tabs>
    <div class="tag-bar">
      <span style="margin-right: 8px">标签：</span>
      <a-space :size="[0, 8]" wrap>
        <a-checkable-tag
          v-for="(tag, index) in tagList"
          :key="tag"
          v-model:checked="selectedTagList[index]"
          @change="doSearch"
        >
          {{ tag }}
        </a-checkable-tag>
      </a-space>
    </div>
    <!-- 图片列表 -->
    <PictureList :dataList="dataList" :loading="loading" />
    <!-- 分页 -->
    <a-pagination
      style="text-align: right"
      v-model:current="searchParams.current"
      v-model:pageSize="searchParams.pageSize"
      :total="total"
      @change="onPageChange"
    />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import {
  listPictureTagCategoryUsingGet,
  listPictureVoByPageUsingPost,
} from '@/api/pictureController.ts'
import { message } from 'ant-design-vue'
import PictureList from '@/components/PictureList.vue' // 定义数据

// 定义数据
const dataList = ref<API.PictureVO[]>([])
const total = ref(0)
const loading = ref(true)

// 搜索条件
const searchParams = reactive<API.PictureQueryRequest>({
  current: 1,
  pageSize: 12,
  sortField: 'createTime',
  sortOrder: 'descend',
})

// 获取数据
const fetchData = async () => {
  loading.value = true
  // 转换搜索参数
  const params = {
    ...searchParams,
    tags: [] as string[],
  }
  if (selectedCategory.value !== 'all') {
    params.category = selectedCategory.value
  }
  // [true, false, false] => ['java']
  selectedTagList.value.forEach((useTag, index) => {
    if (useTag) {
      params.tags.push(tagList.value[index])
    }
  })
  const res = await listPictureVoByPageUsingPost(params)
  if (res.data.code === 0 && res.data.data) {
    dataList.value = res.data.data.records ?? []
    total.value = res.data.data.total ?? 0
  } else {
    message.error('获取数据失败，' + res.data.message)
  }
  loading.value = false
}

// 页面加载时获取数据，请求一次
onMounted(() => {
  fetchData()
})

// 分页参数
const onPageChange = (page: number, pageSize: number) => {
  searchParams.current = page
  searchParams.pageSize = pageSize
  fetchData()
}

// 搜索
const doSearch = () => {
  // 重置搜索条件
  searchParams.current = 1
  fetchData()
}

// 标签和分类列表
const categoryList = ref<string[]>([])
const selectedCategory = ref<string>('all')
const tagList = ref<string[]>([])
const selectedTagList = ref<boolean[]>([])

/**
 * 获取标签和分类选项
 * @param values
 */
const getTagCategoryOptions = async () => {
  const res = await listPictureTagCategoryUsingGet()
  if (res.data.code === 0 && res.data.data) {
    tagList.value = res.data.data.tagList ?? []
    categoryList.value = res.data.data.categoryList ?? []
  } else {
    message.error('获取标签分类列表失败，' + res.data.message)
  }
}

onMounted(() => {
  getTagCategoryOptions()
})
</script>

<style scoped>
#homePage {
  margin-bottom: 16px;
}

#homePage .search-bar {
  max-width: 480px;
  margin: 0 auto 24px;
}

#homePage .search-bar :deep(.ant-input) {
  background: var(--tech-bg-container, #1a1a2e);
  border-color: var(--tech-border, #2d2d44);
  color: var(--tech-text, #e4e4e7);
  border-radius: 8px 0 0 8px;
}

#homePage .search-bar :deep(.ant-input::placeholder) {
  color: var(--tech-text-tertiary, #71717a);
}

#homePage .search-bar :deep(.ant-input:hover),
#homePage .search-bar :deep(.ant-input:focus) {
  border-color: var(--tech-primary, #6366f1);
  box-shadow: 0 0 0 2px rgba(99, 102, 241, 0.1);
}

#homePage .search-bar :deep(.ant-btn-primary) {
  background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%);
  border: none;
  border-radius: 0 8px 8px 0;
  box-shadow: 0 0 15px rgba(99, 102, 241, 0.3);
}

#homePage .search-bar :deep(.ant-btn-primary:hover) {
  box-shadow: 0 0 25px rgba(99, 102, 241, 0.5);
}

#homePage .tag-bar {
  margin-bottom: 16px;
  color: var(--tech-text-secondary, #a1a1aa);
}

#homePage :deep(.ant-tabs-nav::before) {
  border-bottom-color: var(--tech-border, #2d2d44);
}

#homePage :deep(.ant-tabs-tab) {
  color: var(--tech-text-secondary, #a1a1aa);
  transition: color 0.3s ease;
}

#homePage :deep(.ant-tabs-tab:hover) {
  color: var(--tech-primary-hover, #818cf8);
}

#homePage :deep(.ant-tabs-tab-active .ant-tabs-tab-btn) {
  color: var(--tech-primary, #6366f1) !important;
}

#homePage :deep(.ant-tabs-ink-bar) {
  background: linear-gradient(90deg, #6366f1, #a855f7);
}

#homePage :deep(.ant-tag-checkable) {
  background: transparent;
  border: 1px solid var(--tech-border, #2d2d44);
  color: var(--tech-text-secondary, #a1a1aa);
  transition: all 0.3s ease;
}

#homePage :deep(.ant-tag-checkable:hover) {
  color: var(--tech-primary, #6366f1);
  border-color: var(--tech-primary, #6366f1);
}

#homePage :deep(.ant-tag-checkable-checked) {
  background: rgba(99, 102, 241, 0.15);
  border-color: var(--tech-primary, #6366f1);
  color: var(--tech-primary-hover, #818cf8);
}

#homePage :deep(.ant-pagination) {
  margin-top: 24px;
}

#homePage :deep(.ant-pagination-item) {
  background: var(--tech-bg-container, #1a1a2e);
  border-color: var(--tech-border, #2d2d44);
  border-radius: 6px;
}

#homePage :deep(.ant-pagination-item a) {
  color: var(--tech-text-secondary, #a1a1aa);
}

#homePage :deep(.ant-pagination-item:hover) {
  border-color: var(--tech-primary, #6366f1);
}

#homePage :deep(.ant-pagination-item:hover a) {
  color: var(--tech-primary, #6366f1);
}

#homePage :deep(.ant-pagination-item-active) {
  background: rgba(99, 102, 241, 0.15);
  border-color: var(--tech-primary, #6366f1);
}

#homePage :deep(.ant-pagination-item-active a) {
  color: var(--tech-primary, #6366f1);
}

#homePage :deep(.ant-pagination-prev .ant-pagination-item-link),
#homePage :deep(.ant-pagination-next .ant-pagination-item-link) {
  background: var(--tech-bg-container, #1a1a2e);
  border-color: var(--tech-border, #2d2d44);
  color: var(--tech-text-secondary, #a1a1aa);
  border-radius: 6px;
}

#homePage :deep(.ant-pagination-prev:hover .ant-pagination-item-link),
#homePage :deep(.ant-pagination-next:hover .ant-pagination-item-link) {
  border-color: var(--tech-primary, #6366f1);
  color: var(--tech-primary, #6366f1);
}
</style>
