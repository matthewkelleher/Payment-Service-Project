<template>
  <div class="home">
    <div class="left-panel">
    
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
},

}
</script>

<style>
.home {
  
  display: grid;
  grid-template-columns: 1fr 3fr;
  grid-template-areas:
  "left-panel main-body";
  background-color: white;
  
}
.left-panel {
  
  
  margin-left: 10%;
  
  
}

.main-body {
 border-left: 1px solid lightgrey;
 height: 100vh;
 
  
}
</style>