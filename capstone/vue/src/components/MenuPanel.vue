<template>
  
  <div id="menubar">
    <router-link style="text-decoration: none" v-bind:to="{ name: 'home' }"><p id="tenmo-image">tenmo</p></router-link>
    <MenuPanelBlurb/>
    <b-button pill size="lg" id="pay-or-request" v-if="this.$store.state.payClicked == false" v-on:click="payClicked()">Pay or Request</b-button>  
    
    <br>
    <br>
    <p>${{user.balance}} in TEnmo</p>
    <br>
    <p class="menuitem">Search</p>
    <p class="menuitem" v-on:click="showPending()">Incomplete</p>
    <p class="menuitem" v-on:click="showStatements()">Statements</p>
    
    <p class="menuitem">Log out</p>
  
  
  
  </div>
</template>

<script>
import TransferService from '../services/TransferService'
import MenuPanelBlurb from '../components/MenuPanelBlurb'

export default {
name: "menu-panel",
components: {
  MenuPanelBlurb
},
props: {

},
data() {
  return {
   user: {
    userName: null,
    userId: null,
    accountId: null,
    balance: null,   
   },
   
  }
},
created() {
  TransferService.balance().then((response) => {
    this.user.userId = response.data.userId;
    this.user.accountId = response.data.accountId;
    this.user.balance = response.data.balance;
    this.user.userName = response.data.username;
  })
 
},
mounted() {
 
},
methods: {
  payClicked() {
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("PAY_CLICKED", true);

  },
  showStatements() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("SHOW_STATEMENTS", true);
  },
  showPending() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", true);
  }
},
}
</script>

<style>
#pay-or-request {
  background-color: rgba(0,116,222);
  font-weight: bold;
  
  
}

#pay-or-request.hover {

}

.menuitem:hover {
  cursor: pointer; 
  color: rgba(0,140,255)
}

#tenmo-image
{
  font-weight: 800;
  font-size: 30px;
  font-style: italic;
  color: rgba(0,140,255);
}

#menubar {
  background-color: white;
}
</style>