<template>
  
  <div>
   
    <b-button pill id="pay-or-request" v-if="this.$store.state.payClicked == false" v-on:click="payClicked()">Pay or Request</b-button>  
    <p>${{user.balance}} in TEnmo</p>
    <p class="menuitem" v-on:click="showPending()">Incomplete</p>
    <p class="menuitem" v-on:click="showStatements()">Statements</p>
    <p class="menuitem">Search</p>
    <p class="menuitem">Logout</p>
  
  
  
  </div>
</template>

<script>
import TransferService from '../services/TransferService'

export default {
name: "menu-panel",
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
  background-color: rgba(0,140,255);
}

.menuitem {
  font-weight: bold;
  
}
.menuitem:hover {
  cursor: pointer; 
  color: rgba(0,140,255)
}
</style>