<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }"><span v-on:click="payClicked()">Home</span></router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'register'}">
      <b-button pill variant="outline-primary" v-on:click="payClicked()">Get TEnmo</b-button></router-link>
    </div>
    <router-view />
  </div>
</template>
<script>
export default {
  methods: {
    payClicked() {
      this.$store.commit("PAY_CLICKED", false);
    }
  }
}
</script>
<style>
@font-face {
  font-family: "Athletics";
  src: local("Athletics"),
  url(/fonts/Athletics/Athletics-Regular.otf) format("otf");
}
#nav {
  justify-content: space-evenly;
}
</style>
