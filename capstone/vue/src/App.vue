<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'register'}">
      <b-button pill variant="outline-primary">Get TEnmo</b-button></router-link>
    </div>
    <router-view />
  </div>
</template>
<script>

</script>
<style scoped>
#nav {
  justify-content: space-evenly;
}
</style>
