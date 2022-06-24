<template>
<div>
<div v-if="this.$store.state.payClicked == true">
{{transfer}}
{{this.user}}
{{this.toUserName}}
  <p>TEnmo | Pay & Request</p>
    <div class="autocomplete">
    <b-form-input v-model="transfer.amount" placeholder="0"></b-form-input>
    <b-form-input placeholder="Username" v-model="toUserName"></b-form-input>
    </div>
    <b-button pill v-on:click="transferMoney()">Pay</b-button> <b-button pill v-on:click="requestMoney()">Request</b-button>


</div>
  <div class="statements" v-if="this.$store.state.showStatements == true">
  
  <table id="statementTable">
    <thead>
    <tr>
      <th>To</th>
      <th>From</th>
      <th>Type</th>      
    </tr>
    </thead>
    <tbody>
      <tr v-for="pastTransfer in listOfTransfers"
  v-bind:key="pastTransfer.id">
    <td>{{pastTransfer.usernameTo}}</td>
    <td>{{pastTransfer.usernameFrom}}</td>
    <td>{{pastTransfer.transfer_type_desc}}</td>
    <td>{{pastTransfer.amount}}</td>
    
  </tr>
    </tbody>
    </table>
  
  
  <div class="oldTransfer">
    
  </div>
  
  </div>
  <div v-if="this.$store.state.showPending == true">
  <table id="statementTable">
    <thead>
    <tr>
      <th>To</th>
      <th>From</th>
      <th>Type</th>      
    </tr>
    </thead>
    <tbody>
      <tr v-for="pastTransfer in pendingTransfers"
  v-bind:key="pastTransfer.id">
    <td>{{pastTransfer.usernameTo}}</td>
    <td>{{pastTransfer.usernameFrom}}</td>
    <td>{{pastTransfer.transfer_type_desc}}</td>
    <td>{{pastTransfer.amount}}</td>
    
  </tr>
    </tbody>
    </table>
  
  </div> 

 
  
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
    balance: null,
    userName: null
   },
   listOfTransfers : [],
   pendingTransfers: [],
   
    }
},
// created() {
//   TransferService.balance().then((response) => {
//     this.user.userId = response.data.userId;
//     this.transfer.account_from = response.data.userId;
//     this.transfer.account_to = response.data.userId;
//     this.user.balance = response.data.balance;
//     this.user.userName = response.data.username;
   
//   },
//     )
// },
updated() {
  if(this.$store.state.showStatements == true) {
    this.listTransfers()
  }
  if(this.$store.state.showPending == true) {
    this.getPending()
  }
},
mounted() {
    
    TransferService.balance().then((response) => {
    this.user.userId = response.data.userId;
    this.transfer.account_from = response.data.userId;
    this.transfer.account_to = response.data.userId;
    this.user.balance = response.data.balance;
    this.user.userName = response.data.username;
   
  })
  
},
methods: {
    transferMoney() {
        TransferService.getUserId(this.toUserName).then((response) => {
            this.transfer.account_to = response.data;
            console.log(this.transfer)
            TransferService.send(this.transfer).then(() => {
            console.log("Money sent.");
        this.transfer.account_from = this.user.userId;
        this.transfer.account_to = 0;
        this.amount = 0;
        })
        })
        
        

},
  listTransfers() {
    TransferService.list().then((response) => {
      this.listOfTransfers = response.data;
     

    })
    
  },
  getPending() {
    TransferService.pending().then((response) => {
      this.pendingTransfers = response.data;
     
    })
  },
  
    // getId() {
    //     console.log(this.toUserName)
    //     TransferService.getUserId(this.toUserName).then((response) => {
    //         console.log(response.data);
    //         this.toUserName = "";
    //     })
    // },
    requestMoney() {
       this.transfer.account_to = this.user.userId
       TransferService.getUserId(this.toUserName).then((response) => {
            this.transfer.account_from = response.data;
            TransferService.request(this.transfer).then(() => {
            console.log("Money requested.");
            this.transfer.account_from = this.user.userId;
            this.transfer.account_to = 0;
            this.amount = 0;
        }) 
        
            
        })
        
    }},
    
}
</script>

<style>

</style>