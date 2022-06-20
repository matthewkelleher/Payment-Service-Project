<template>
<div v-if="this.$store.state.payClicked == true">
{{transfer}}
{{this.toUserName}}
  <p>TEnmo | Pay & Request</p>
    <div class="autocomplete">
    <b-form-input v-model="transfer.amount" placeholder="0"></b-form-input>
    <b-form-input placeholder="Username" v-model="toUserName"></b-form-input>
    </div>
    <b-button pill v-on:click="transferMoney()">Pay</b-button> <b-button pill v-on:click="getId()">Request</b-button>


</div>

</template>

<script>

import TransferService from '../services/TransferService'
 export default {
   
data() {
    return {
        amount: null,
        toUserName: "",
        
    transfer: {
    account_from: 0,
    account_to: 0,
    amount: 0,
    
   },
   user: {
    userId: null,
    balance: null
   }
    }
},
created() {
  TransferService.balance().then((response) => {
    this.user.userId = response.data.userId;
    this.transfer.account_from = response.data.userId;
    this.user.balance = response.data.balance;
  })
},
mounted() {
    
},
methods: {
    transferMoney() {
        TransferService.getUserId(this.toUserName).then((response) => {
            this.transfer.account_to = response.data;
        })
        TransferService.send(this.transfer).then((response) => {
            console.log(response.data);
        })
},
    getId() {
        console.log(this.toUserName)
        TransferService.getUserId(this.toUserName).then((response) => {
            console.log(response.data);
            this.toUserName = "";
        })
    }},
}
</script>

<style>

</style>