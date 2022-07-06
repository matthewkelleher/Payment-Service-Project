<template>
  <div class="home">
    <div class="home-left">
      <div id="first-para-container">
      <p id="first-para">Fast, safe, social payments</p>
      </div>
      <div id="second-para-container">
      <p id="second-para">Pay. Get paid. Shop or share—if those features are implemented. Join more than several fictional people who use the Tenmo app.</p>
<router-link v-bind:to="{ name: 'mainpage' }"><b-button v-on:click="payClicked()" id="get-tenmo-button" pill variant="primary">Get Tenmo</b-button></router-link>
</div>
    </div>
    <div class="home-right">
      <img id="first-image" src="alt-phone-image.webp"/>
      <img id="third-image" src="alt-phone-image-backdrop.webp"/>
      <img id="second-image" src="other-image.webp"/>
    </div>

    
    
  </div>
</template>

<script>
export default {
  name: "home",
  methods: {
      payClicked() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("SHOW_USER_SEARCH", false);
    }
  }
};
</script>

<style scoped>

@keyframes onLoadTranslateRight {
  0% {
    transform: translateX(-5%) translateY(5%);
  }
  100% {
    transform: translateX(0) translateY(0);
  }
}

@keyframes onLoadTranslateLeft {
  0% {
    transform: translateX(5%) translateY(5%);
  }

  100% {
    transform: translateX(0) translateY(0);
   
  }
}

.home {
  background-color: rgba(242,249,255);  
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "home-left home-right";
  margin-left: 15%;
  margin-right: 5%;
  margin-top: 15%;
  

}

.home-left {
  margin-right: 15%;
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

#first-para-container {

}

#first-para {
  font-size: 72px;
  font-weight: 500;
  font-family: "Athletics";
  
}

#second-para {
  font-size: 18px;
  font-family: Helvetica;
}

img {
  max-width: 100%;
}

#first-image {
 position: relative;
 top: -200px;
 z-index: 4;
 animation: 1s ease-out 0s 1 onLoadTranslateRight;
}

#second-image {
  position: relative;
  top: -1525px;
  z-index: 0;
  animation: 1s ease-out 0s 1 onLoadTranslateLeft;
}

#third-image {
  position: relative;
  top: -870px;
  z-index: 1;
  opacity: 0.7;
  animation: 1s ease-out 0s 1 onLoadTranslateRight;
}
</style>
