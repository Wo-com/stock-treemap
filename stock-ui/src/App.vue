<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const currentPath = ref('')
const route = useRoute()

onMounted(() => {
  currentPath.value = route.path
})
</script>

<template>
  <div class="app-container">
    <nav class="navbar">
      <div class="nav-brand">股票数据中心</div>
    </nav>
    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<style scoped>
.app-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  height: 50px;
  background-color: #383b44;
  color: white;
}

.nav-brand {
  font-size: 16px;
  font-weight: bold;
}

.nav-links {
  display: flex;
  gap: 20px;
}

.nav-links a {
  color: #bbb;
  text-decoration: none;
  padding: 8px 16px;
  border-radius: 4px;
  transition: all 0.3s;
}

.nav-links a:hover {
  background-color: #585a64;
  color: white;
}

.nav-links a.active {
  background-color: #2949be;
  color: white;
}

.content {
  flex: 1;
  overflow: hidden;
}
</style>