<template>
  <div id="app">
    <div id="nav" v-if="this.currentRouteName != 'mainpage'">
     
      <router-link style="text-decoration: none" v-bind:to="{ name: 'home' }"><span id="tenmo-image" v-on:click="payClicked()" class="navitem" style="vertical-align:middle; color: rgba(0,140,255)" >tenmo</span></router-link>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'logout' }"><span class="navitem"  v-if="$store.state.token != ''">Log out</span></router-link>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'login' }"><span class="navitem" v-if="$store.state.token == ''">Log in</span></router-link>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'register'}">
      <b-button pill id="get-tenmo" v-on:click="payClicked()" title="Get TEnmo"><span id="get-tenmo-inner"><span class="logo-t">t</span>&emsp;Get Tenmo</span></b-button></router-link>
    </div>
    <router-view />
  </div>
</template>
<script>
export default {
  computed: {
    currentRouteName() {
      return this.$route.name
    }
  },
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

@import url('https://fonts.googleapis.com/css?family=Roboto+Condensed');

html, body {
  font-family: 'Roboto', sans-serif;
  color: rgb(85,88,94);
  
}

#app {

  background-color: rgb(242,249,255);
}
#nav {
  justify-content: space-evenly;
  margin-left: 20%;
  margin-right: 20%;
  display: flex;
  
  align-items: center;
  
 
  
}





.navitem {
  color: black;
}

a:hover {
  text-decoration: none;
}


#get-tenmo {
  background-color: white;
  color: black;
  font-size: 12px;
  display: inline-block;
}

#get-tenmo:hover {
  background-color: rgb(242,249,255);
}

#tenmo-image
{
  color: rgba(0,140,255);
  font-weight: 800;
  font-size: 30px;
  font-style: italic;
  
  
}

#tenmo-image:link {
  text-decoration: none;
  
}

#tenmo-image:hover
{
  text-decoration: none;
  
}

.logo-t {
  color: rgba(0,140,255);
  font-size: 24px;
  font-weight: 800;
  font-style: italic;
}

#get-tenmo-inner {
  text-align: center;
}
</style>
