<template>
  <div class="home">
   
    

    <router-link v-bind:to="{ name: 'mainpage' }"><b-button v-on:click="payClicked()" id="get-tenmo-button" pill variant="primary">Get Tenmo</b-button></router-link>
    
  </div>
</template>

<script>
export default {
  name: "home",
  methods: {
      payClicked() {
      this.$store.commit("PAY_CLICKED", false);
    }
  }
};
</script>

<style scoped>

.home {
  background-color: rgba(242,249,255)
}
#get-tenmo-button {
  width: 140px;
  height: 45px;
  /* font-family: 'Roboto', sans-serif; */
  /* font-size: 11px; */
  /* text-transform: uppercase; */
  /* letter-spacing: 2.5px; */
  font-weight: bold;
  color: white;
  background-color: rgba(0,140,255);
  border: none;
  border-radius: 45px;
  box-shadow: 7px 9px  rgba(72,225,178);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  text-align: center;
  
  display: inline-block;
  
}

#get-tenmo-button:hover {

 
  box-shadow: 0px 0px 0px rgba(0,140,225);

  transform: translateY(7px) translateX(9px);
}
</style>
