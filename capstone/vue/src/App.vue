<template>
  <div id="app">
    <div id="nav-two" v-if="this.currentRouteName == 'login' || this.currentRouteName == 'register' || this.currentRouteName == 'postlogin'" :class="{change_color: scrollPosition > 50}">
     
      <router-link style="text-decoration: none" v-bind:to="{ name: 'home' }"><span id="tenmo-image" v-on:click="payClicked()" style="vertical-align:middle; color: rgba(0,140,255); font-size: 48px" >tenmo</span></router-link>
      <div class="dropdown">
        <div class="within-dropdown">About <span class="v-thing" style="vertical-align: baseline">⌄</span>
        <div class="dropdown-content">
        <div id="first-item-container"><span id="first-item">Why?</span></div>
        <div id="second-item-container"><span id="second-item">Why not?</span></div></div></div></div>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'logout' }"  v-if="$store.state.token != ''"><span class="navitem">Log out</span></router-link>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"><span class="navitem" >Log in</span></router-link>
      
      <router-link style="text-decoration: none" v-bind:to="{ name: 'register'}">
      <b-button pill id="get-tenmo" v-on:click="payClicked()" title="Get TEnmo"><span id="get-tenmo-inner"><span class="logo-t">t</span>&emsp;<span class="get-tenmo-text">Get Tenmo</span></span></b-button></router-link>
    </div>
    <div id="nav" v-else-if="this.currentRouteName != 'mainpage'" :class="{change_color: scrollPosition > 50}">
     
      <router-link style="text-decoration: none" v-bind:to="{ name: 'home' }"><span id="tenmo-image" v-on:click="payClicked()" style="vertical-align:middle; color: rgba(0,140,255); font-size: 48px" >tenmo</span></router-link>
      <div class="dropdown">
        <div class="within-dropdown">About <span class="v-thing" style="vertical-align: baseline">⌄</span>
        <div class="dropdown-content">
        <div id="first-item-container"><span id="first-item">Why?</span></div>
        <div id="second-item-container"><span id="second-item">Why not?</span></div></div></div></div>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'logout' }"  v-if="$store.state.token != ''"><span class="navitem">Log out</span></router-link>
      <router-link style="text-decoration: none" v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"><span class="navitem" >Log in</span></router-link>
      
      <router-link style="text-decoration: none" v-bind:to="{ name: 'register'}">
      <b-button pill id="get-tenmo" v-on:click="payClicked()" title="Get TEnmo"><span id="get-tenmo-inner"><span class="logo-t">t</span>&emsp;<span class="get-tenmo-text">Get Tenmo</span></span></b-button></router-link>
    </div>
    
    
    <router-view />
  </div>
</template>
<script>
export default {
  data() {
    return {
    scrollPosition: null,
  }
  },
  
  computed: {
    currentRouteName() {
      return this.$route.name
    }
  },
  mounted() {
     window.addEventListener('scroll', this.updateScroll);
  },
  methods: {
    payClicked() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("SHOW_USER_SEARCH", false);
    },
    updateScroll() {
      this.scrollPosition = window.scrollY
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
  font-family: Helvetica, sans-serif;
  color: rgb(85,88,94);
  
}



#app {

  background-color: rgb(242,249,255);
 
}
#nav {
  position: sticky;
  justify-content: space-around;
  margin-left: 10%;
 
  margin-right: 20%;
  display: flex;
  padding-top: 2%;
  align-items: center;
  
   
  z-index: 5;
  
  
}

#nav-two {
  
  justify-content: space-around;
  
  background-color: white;
  
  display: flex;
  padding-top: 2%;
  align-items: center;
  
   
 
  
  
}






.navitem {
  color: black;
  font-family: "Athletics";
  font-weight: 500;
}

a:hover {
  text-decoration: none;
}


#get-tenmo {
  background-color: white;
  color: black;
  width: 150px!important;
  max-height: 45px;
 
  border: 1.5px solid black!important;
 
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
  font-size: 28px;
  font-weight: 800;
  font-style: italic;
  position: relative;
  top: 2px;
 
}

#get-tenmo-inner {
  
 
  font-weight: 500;
  font-size: 14px;
  position: relative;
  top: -10px;
  left: -4px;
  
}

.dropdown:hover .dropdown-content {
  display: block;
  background-color: white;
  font-family: "Athletics"}





#first-item {
   margin-left: 25%;
}

#first-item:hover {
  background-color: paleturquoise;
 
 
}

#second-item {
  margin-left: 25%;
}

#second-item:hover {
  background-color: peachpuff;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  border-radius: 10%;
  
  

  
}

.dropdown {
  font-weight: 500;
}

.within-dropdown {
}

.v-thing {
  color: rgb(0,140,255);
  font-weight: bold;
}

.change_color {
  background-color: white;
}

#get-tenmo-text {
  position: relative;
  top: 100!;
}
</style>
