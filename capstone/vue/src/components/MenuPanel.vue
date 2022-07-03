<template>
  
  <div id="menubar" :key="checkIt">
    
    <router-link @click="allFalse()" style="text-decoration: none" v-bind:to="{ name: 'home' }"><p id="tenmo-logo">tenmo</p></router-link>
    <MenuPanelBlurb/>
    <b-button pill size="lg" id="pay-or-request" v-if="this.$store.state.payClicked == false" v-on:click="payClicked()">
    <span id="pay-request">t</span>&ensp;<span>Pay or Request</span></b-button>  
    
    <br>
    <br>
    <p>{{formatMoney(this.user.balance)}} in Tenmo</p>
    <br>
    <p class="menuitem" v-on:click="showUserSearch()">Search</p>
    <p class="menuitem" v-on:click="showPending()">Incomplete</p>
    <p class="menuitem" v-on:click="showStatements()">Statements</p>
    
    <router-link v-bind:to="{ name: 'logout' }" style="text-decoration: none"><p class="menuitem">Log out</p></router-link>
  
  
  
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
  checkIt: 0
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
  this.$store.commit("SET_ACTIVE_USER", this.user);
},
methods: {
  payClicked() {
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("PAY_CLICKED", true);
    this.$store.commit("SHOW_USER_SEARCH", false);

  },
  showStatements() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("SHOW_STATEMENTS", true);
    this.$store.commit("SHOW_USER_SEARCH", false);
  },
  showPending() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", true);
    this.$store.commit("SHOW_USER_SEARCH", false);
  },
  showUserSearch() {
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("SHOW_USER_SEARCH", true);
  },
  allFalse() {
    
    this.$store.commit("PAY_CLICKED", false);
    this.$store.commit("SHOW_STATEMENTS", false);
    this.$store.commit("SHOW_PENDING", false);
    this.$store.commit("SHOW_USER_SEARCH", false);
  },

  formatMoney(number) {
  return number.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
}
}
 
}
</script>

<style>
#pay-or-request {
  background-color: rgba(0,116,222);
  font-weight: bold;
  
  
}

#pay-or-request.hover {

}

.menuitem {
  color: black;
}

.menuitem:hover {
  cursor: pointer; 
  color: rgba(0,140,255)
}

#tenmo-logo
{
  font-weight: 800;
  font-size: 40px;
  font-style: italic;
  
}

#menubar {
  background-color: white;
}

#pay-or-request {
  width: 90%;
  display: flex;
  max-height: 60px !important;
  align-items: center;
  justify-content: center;
  
  
}

#pay-request
{
  font-weight: 800;
  font-size: 30px;
  font-style: italic;
  color: white !important;
}
</style>