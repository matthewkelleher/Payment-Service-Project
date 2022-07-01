<template>
<div>
 
<div v-if="this.$store.state.payClicked == true" style="text-align: left">

  <p style="font-weight: 500">TEnmo | Pay & Request</p>
    <div class="autocomplete">
    <span class="input-dollar"><b-form-input id="money-input" v-model="transfer.amount" placeholder="0" type="number" ></b-form-input></span>
    <b-form-input placeholder="To" v-model="toUserName"></b-form-input>
    <b-button pill v-on:click="transferMoney()" class="pay-request-buttons">Pay</b-button> <b-button pill v-on:click="requestMoney()" class="pay-request-buttons">Request</b-button>
    </div>
    


</div>
  <div class="statements" v-if="this.$store.state.showStatements == true">
   <h3 id="completed-header">Completed Transactions</h3>
   {{user.username}}
 
   
   
    <div class="transaction-container">
      <div v-for="pastTransfer in listOfTransfers"
  v-bind:key="pastTransfer.id" class="past-transaction">
  <div class="inner-transaction">
  <span style="font-weight: bold">{{pastTransfer.usernameFrom}}</span>
    <span> paid </span><span style="font-weight:bold">{{pastTransfer.usernameTo}}</span>
    <span v-if="pastTransfer.usernameFrom == user.userName" style="color: red"> -${{pastTransfer.amount}}</span>
    <span v-else style="color: green">&ensp;${{pastTransfer.amount}}</span>
    </div>
  </div>
    </div>
    
  
  
  
  
  </div>
  <div v-if="this.$store.state.showPending == true">
  {{pendingTransfers}}
  <table id="pendingTable">
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
    <td style="font-weight: bold">{{pastTransfer.usernameTo}}</td>
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
        username: "",
        
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
.past-transaction {
  border-bottom: 1px solid lightgrey;
  
 
 
}

.inner-transaction {
  padding-top: 10px;
  padding-bottom: 10px;
}

.statements {
 width:50%;
 height:500px;
 margin:0 auto;
 
 position:absolute;
 left:50%;
 top:50%;
 margin-left:-250px;
 margin-top:-250px;
}

#completed-header
{
  padding-bottom: 20px;
  
}
input[type=number]::-webkit-inner-spin-button, 
input[type=number]::-webkit-outer-spin-button { 
  -webkit-appearance: none; 
  margin: 0; 
}
#money-input {
  width: 100px;
  height: 100px;
  font-size: 32px;
}

.input-dollar {
    position: relative;
}
.input-dollar input {
    padding-left:18px;
}
.input-dollar:before {
    position: absolute;
    top: 0;
    content:"$";
    left: 5px;
}

.autocomplete {
 width:50%;
 height:500px;
 margin:0 auto;
 
 position:absolute;
 left:50%;
 top:50%;
 margin-left:0px;
 margin-top:-250px;
}

.pay-request-buttons {
  background-color: rgba(0,140,255)!important;
  width: 200px;
  font-weight: bold!important;
}
</style>