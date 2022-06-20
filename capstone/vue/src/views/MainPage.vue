<template>
  <div class="home">
    <div class="left-panel">
      {{this.$store.state.payClicked}}
    <MenuPanel/>
    </div>
    <div class="main-body">
    <MainBody/>
    </div>
  </div>
</template>

<script>
import MenuPanel from '../components/MenuPanel.vue'
import MainBody from '../components/MainBody.vue'
export default {
name: 'mainpage',
components: {
  MenuPanel,
  MainBody
},
data() {
    return {

    }
}
}
</script>

<style>
.home {
  height: 100%;
  display: grid;
  grid-template-columns: 20% 80%;
  grid-template-areas:
  "left-panel main-body"
}
.left-panel {
  border-right: 1px solid grey;
  height: 100%;
}

.main-body {
  margin-left: auto;
  margin-right: auto;
}
</style>