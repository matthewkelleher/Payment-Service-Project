<template>
  
  <div>
   
    <b-button pill id="pay-or-request" v-if="this.$store.state.payClicked == false" v-on:click="payClicked()">Pay or Request</b-button>  
    <p>${{user.balance}} in TEnmo</p>
    <p>Incomplete</p>
    <p>Statements</p>
    <p>Search</p>
    <p>Logout</p>
  
  
  
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
  })
},
methods: {
  payClicked() {
    this.$store.commit("PAY_CLICKED", true);

  }
},
}
</script>

<style>
#pay-or-request {
  background-color: rgba(0,140,255);
}
</style>