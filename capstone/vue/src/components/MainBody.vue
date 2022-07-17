<template>
<div class="page">


 
<div v-if="this.$store.state.payClicked == true" class="payment-container">

  <p style="font-weight: 500; text-align: center">TEnmo | Pay & Request</p>
    <div class="autocomplete">
    <b-form-input class="pay-request-input" id="money-input" 
    onkeydown="this.style.minWidth = (this.value.length + 3) + 'ch'" placeholder="0" v-model="transfer.amount" type="number" required></b-form-input>
    <b-form-input class="pay-request-input" placeholder="To" v-model="toUserName" required></b-form-input>
    <b-form-textarea class="pay-request-input" id="note-input" placeholder="Note" v-model="transfer.note" rows="5" max-rows="5" no-resize></b-form-textarea>
    <b-button pill v-on:click="transferMoney()" class="pay-request-buttons">Pay</b-button> <b-button pill v-on:click="requestMoney()" class="pay-request-buttons">Request</b-button>
    </div>
    </div>    



  <div class="statements" v-if="this.$store.state.showStatements == true && this.listOfTransfers.length >= 1">
   <h3 id="completed-header">Completed Transactions</h3>
   
   
   
    <div class="transaction-container">

      <div v-for="pastTransfer in listOfTransfers"
  v-bind:key="pastTransfer.id">
  
    <div v-if="pastTransfer.transfer_status_desc == 'Rejected'">
    <div class="past-grid-container">
    <div class="past-statement"><span style="font-weight: bold">{{pastTransfer.nameFrom}}</span>
     rejected a request from <span style="font-weight: bold">{{pastTransfer.nameTo}}</span></div>
     <div class="past-amount">
      <span v-if="pastTransfer.usernameTo == user.userName" style="color: green; text-decoration: line-through">{{formatMoney(pastTransfer.amount)}}</span>
     
      <span v-else style="color: red; text-decoration: line-through">-{{formatMoney(pastTransfer.amount)}}</span></div>
    <div class="past-note">{{pastTransfer.note}}</div>
    <div class="past-image"><img :src="imageFinder(pastTransfer.account_from)" id="statement-image"/></div>
    </div>
    </div>
     
   <div v-else-if="pastTransfer.transfer_status_desc == 'Approved'">
   <div class="past-grid-container">
   <div class="past-statement"><span style="font-weight: bold">{{pastTransfer.nameFrom}}</span>
     paid <span style="font-weight: bold">{{pastTransfer.nameTo}}</span></div>
     <div class="past-amount">
      <span v-if="pastTransfer.usernameTo == user.userName" style="color: green">{{formatMoney(pastTransfer.amount)}}</span>
      <span v-else style="color: red">-{{formatMoney(pastTransfer.amount)}}</span></div>
    <div class="past-note">{{pastTransfer.note}}</div>
    <div class="past-image"><img :src="imageFinder(pastTransfer.account_from)" id="statement-image"/></div>
    </div>
    
   
    </div>
  </div>
  </div>
    
    
  
  
  
  
  </div>
  <div class="statements" v-if="this.$store.state.showStatements == true && this.listOfTransfers.length == 0">
  <h3 id="completed-header">Completed Transactions</h3>
   <p>Sorry, no transactions to display.</p>
  </div>
  <div class="pending-statements" v-if="this.$store.state.showPending == true && this.pendingTransfers.length >= 1">
  
<h3 id="pending-header">Incomplete</h3>
  
      <div v-for="pastTransfer in pendingTransfers"
  v-bind:key="pastTransfer.id">
  <div class="grid-container">
    <div class="pending-statement-container"><span style="font-weight: bold">{{pastTransfer.nameTo}}</span>
     requested payment from <span style="font-weight: bold">{{pastTransfer.nameFrom}}</span></div>
   
    <div class="pending-amount">
      <span v-if="pastTransfer.usernameTo == user.userName" style="color: green">{{formatMoney(pastTransfer.amount)}}</span>
      <span v-else style="color: red">-{{formatMoney(pastTransfer.amount)}}</span>
    </div>
    <div class="pending-note">{{pastTransfer.note}}</div>
    <div class="pending-image"><img :src="imageFinder(pastTransfer.account_from)" id="statement-image"/></div>
    <div class="pending-button" v-if="pastTransfer.usernameTo != user.userName"><b-button pill class="pending" @click="approveTransaction(pastTransfer)">Approve</b-button></div>
    <div class="reject-button" v-if="pastTransfer.usernameTo != user.userName"><b-button pill variant="danger" class="reject" @click="rejectTransaction(pastTransfer)">Reject</b-button></div>
  </div>
  </div> 
  
</div>

<div v-if="this.$store.state.showPending == true && this.pendingTransfers.length == 0" class="pending-statements">
<h3 id="pending-header">Incomplete</h3></div>

  <div v-if="this.$store.state.showUserSearch == true" class="userSearch">
    <h3>Search</h3>
 
    <div>
      <b-input-group size="m" class="mb-2">
      <b-input-group-prepend is-text>
        <b-icon icon="search"></b-icon>
      </b-input-group-prepend><b-form-input placeholder="Name or Username" v-model="search" class="theResults"><b-icon icon="search"></b-icon></b-form-input>
  </b-input-group>
  </div>
  
    <div v-for="userResult in userList" v-bind:key="userResult.account_id" class="usernameResult" @click="pushToTransfer(userResult.username)">
    <span id="username-search-result">{{userResult.username}}</span>
    <span id="firstname-lastname-search-result">{{userResult.firstName}}&nbsp;{{userResult.lastName}}</span>
    <span id="userimage-search-result"><img id="statement-image" :src="imageFinder(userResult.account_id)"></span>
    </div>
  
  </div>
 
 

 
</div>
</template>

<script>

import TransferService from '../services/TransferService'
 export default {
   
data() {
    return {
        amount: 0,
        toUserName: "",
        username: "",
        
        
    transfer: {
    account_from: 0,
    account_to: 0,
    amount: '',
    note: '',
    
    
   },
   user: {
    userId: null,
    balance: null,
    userName: null,
    firstname: null,
    lastname: null
   },
   listOfTransfers : [],
   pendingTransfers: [],
   userList: [],
   search: '',

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
    console.log(response.data);
    this.user.userId = response.data.userId;
    this.transfer.account_from = response.data.userId;
    this.transfer.account_to = response.data.userId;
    this.user.balance = response.data.balance;
    this.user.userName = response.data.username;
    this.user.firstname = response.data.firstName;
    this.user.lastname = response.data.lastName;
   
  })
  
},
watch: {
  search: function() {
   if(this.search != '') {
    this.getUserNames()
  }
  if(this.search == '') {
    this.userList = [];
  }
}
},
methods: {
    
    transferMoney() {
      if(this.transfer.account_to == this.transfer.account_from) {
        this.transfer.account_to = 0;
        this.toUserName = '';
        window.alert("You cannot transfer to your own account.")
        
      }
        if(this.transfer.amount <= this.user.balance && this.transfer.amount > 0) {
        TransferService.getUserId(this.toUserName).then((response) => {
            this.transfer.account_to = response.data;
            console.log(this.transfer)
            TransferService.send(this.transfer).then(() => {
            console.log("Money sent.");
        this.$store.commit("SUBTRACT_FUNDS", this.transfer.amount);
        this.transfer.account_from = this.user.userId;
        this.transfer.account_to = 0;
        this.amount = 0;
        this.transfer.note = '';
        }) 
        })
        } else if(this.transfer.amount <= 0) {
          window.alert("Must transfer more than $0.")
        } else {
          window.alert("Trying to transfer more than you have.")
        }
        
        

},
  listTransfers() {
    TransferService.list().then((response) => {
      this.listOfTransfers = response.data;
     

    })
    
  },

  pushToTransfer(input) {
    this.toUserName = input;
    this.$store.state.showUserSearch = false;
    this.$store.state.payClicked = true;

  },
  getTheName(id) {
    TransferService.getName(id).then((response) => {
    return response});
  },
  approveTransaction(approved) {
    if(approved.amount <= this.user.balance) {
    TransferService.approve(approved).then(() => {
      this.$store.commit("SUBTRACT_FUNDS", approved.amount);
    })
  } else {
    window.alert("You don't have enough money to approve this!")
  }
  },
  rejectTransaction(rejected) {
    TransferService.reject(rejected).then(() => {
      console.log("Done.")
    })
  },
  imageFinder(id) {
    return id + ".jpg";
  },
  formatMoney(number) {
  return number.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
},
  getPending() {
    TransferService.pending().then((response) => {
      this.pendingTransfers = response.data;
     
    })
  },
  getUserNames() {
    TransferService.getUsernames(this.search).then((response => {
      this.userList = response.data;
    }))
  },
 
 
    requestMoney() {
       this.transfer.account_to = this.user.userId
       if(this.transfer.account_to == this.transfer.account_from) {
        this.transfer.account_to = 0;
        this.toUserName = '';
        window.alert("You cannot request money from yourself.")
       }
       TransferService.getUserId(this.toUserName).then((response) => {
            this.transfer.account_from = response.data;
            TransferService.request(this.transfer).then(() => {
            console.log("Money requested.");
            this.transfer.account_from = this.user.userId;
            this.transfer.account_to = 0;
            this.amount = 0;
            this.transfer.note = '';
        }) 
        
            
        })
        
    }},
    
}
</script>

<style>

body {
  font-family: "Athletics";
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

#statement-image {
  height: 75px;
  width: 75px;
  border-radius: 50%;
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
  width: 64px!important;
 
  height: 100px;
  font-size: 36px;
}

h3 {
  font-family: "Athletics"
}

.autocomplete {

 text-align: center;
 
 
}

.pay-request-buttons {
  background-color: rgba(0,140,255)!important;
  width: 200px;
  font-weight: bold!important;
}



.usernameResult:hover {
  background-color: rgba(0,140,255);
  color: white;
  cursor: pointer;  
  border-radius: 10px;
}

.usernameResult {
  display: grid;
  grid-template-columns: repeat(3, 100px); 
  grid-template-rows: 50px;
  max-height: 75px;
  padding-left: 10%;
}
#username-search-result {
grid-column-start: 2;
grid-column-end: 2;
grid-row-start: 2;
grid-row-end: 2;
}

#firstname-lastname-search-result {
grid-column-start: 2;
grid-column-end: 2;
grid-row-start: 1;
grid-row-end: 1
}

#userimage-search-result {
grid-column-start: 1;
grid-column-end: 1;
grid-row-start: 1;
grid-row-end: 2;


}
.userSearch {
  display: flex;
  flex-direction: column;
  width:50%;
 height:500px;
 margin:0 auto;
 
 position:absolute;
 left:50%;
 top:50%;
 margin-left:-250px;
 margin-top:-250px;
}

.pay-request-input {
  width: 400px!important;
  margin: 20px;
  margin: 0 auto;
  float: none
}

.grid-container {
  display: grid;
  border-bottom: 1px solid lightgrey;
  grid-template-columns: repeat(7, 1fr);
  
  padding-top: 1%;
  padding-bottom: 1%;
  padding-left: 5%;
 
}

.past-grid-container {
  display: grid;
  border-bottom: 1px solid lightgrey;
  grid-template-columns: repeat(5, 1fr);
  padding-top: 1%;
  padding-bottom: 1%;
  padding-left: 5%;
  
}

.past-statement {
 grid-column-start: 2;
  grid-column-end: 6;
  grid-row-start: 1;
  grid-row-end: 1;
  
}

.past-amount {
grid-column-start: 5;
  grid-column-end: 5;
  grid-row-start: 1;
  grid-row-end: 1;
 
}

.past-note {
grid-column-start: 2;
  grid-column-end: 3;
  grid-row-start: 2;
  grid-row-end: 2;
  
}

.past-image {
  grid-column-start: 1;
  grid-column-end: 1;
  
}
.pending-image {

  grid-column-start: 1;
  grid-column-end: 2;
  max-width: 100px;
}

.pending-statement-container {
  grid-column-start: 2;
  grid-column-end: 6;
  grid-row-start: 1;
  grid-row-end: 1;
  max-width: 500px;

}

.pending-amount {
  grid-column-start: 6;
  grid-column-end: 6;
  grid-row-start: 1;
  grid-row-end: 1;
  max-width: 100px;
}

.pending-note {
  grid-column-start: 2;
  grid-column-end: 5;
  grid-row-start: 2;
  grid-row-end: 2;
  max-width: 500px;
}

.pending-button {
  grid-column-start: 7;
  grid-column-end: 7;
  grid-row-start: 1;
  grid-row-start: 1;
  max-width: 100px;
  margin: 0;

}

.reject-button {
  grid-column-start: 7;
  grid-column-end: 7;
  grid-row-start: 2;
  grid-row-end: 2;
  max-width: 100px;
  
 
}

.pending {
  background-color: rgba(0,140,255)!important;
  cursor: pointer;
}

#pending-header {
  padding-bottom: 20px;
}

.pending-statements {
  
 width:50%;
 height:500px;
 margin:0 auto;
 
 position:absolute;
 left:50%;
 top:50%;
 margin-left:-250px;
 margin-top:-250px;
}



.payment-container {
  margin-top: 50px;
  justify-content: center;
  align-items: center;
 
}

</style>