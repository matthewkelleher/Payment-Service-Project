<template>
<div class="page">
 
<div v-if="this.$store.state.payClicked == true" class="payment-container">

  <p style="font-weight: 500; text-align: center">TEnmo | Pay & Request</p>
    <div class="autocomplete">
    <b-form-input class="pay-request-input" id="money-input" 
    onkeydown="this.style.minWidth = (this.value.length + 3) + 'ch'" placeholder="0" v-model="transfer.amount" type="number"></b-form-input>
    <b-form-input class="pay-request-input" placeholder="To" v-model="toUserName"></b-form-input>
    <b-form-textarea class="pay-request-input" id="note-input" placeholder="Note" v-model="transfer.note" rows="5" max-rows="5" no-resize></b-form-textarea>
    <b-button pill v-on:click="transferMoney()" class="pay-request-buttons">Pay</b-button> <b-button pill v-on:click="requestMoney()" class="pay-request-buttons">Request</b-button>
    </div>
    </div>    



  <div class="statements" v-if="this.$store.state.showStatements == true && this.listOfTransfers.length >= 1">
   <h3 id="completed-header">Completed Transactions</h3>
  
   
   
    <div class="transaction-container">
      <div v-for="pastTransfer in listOfTransfers"
  v-bind:key="pastTransfer.id">
  <div class="past-grid-container">
    <div class="past-statement"><span style="font-weight: bold">{{pastTransfer.usernameFrom}}</span>
     {{pastTransfer.transfer_type_desc}} {{pastTransfer.transfer_status_desc}}<span style="font-weight: bold">{{pastTransfer.usernameTo}}</span></div>
   
    <div class="past-amount">
      <span v-if="pastTransfer.usernameTo == user.userName" style="color: green">{{formatMoney(pastTransfer.amount)}}</span>
      <span v-else style="color: red">-{{formatMoney(pastTransfer.amount)}}</span></div>
    <div class="past-note">{{pastTransfer.note}}</div>
    <div class="past-image"><img :src="imageFinder(pastTransfer.account_from)" id="statement-image"/></div>
   
  </div>
    </div>
  </div>
    
    
  
  
  
  
  </div>
  <div class="statements" v-if="this.$store.state.showStatements == true && this.listOfTransfers.length ==0">Nothing here yet.</div>
  <div class="pending-statements" v-if="this.$store.state.showPending == true && this.pendingTransfers.length >= 1">
  
<h3 id="pending-header">Pending Transactions</h3>
  
      <div v-for="pastTransfer in pendingTransfers"
  v-bind:key="pastTransfer.id">
  <div class="grid-container">
    <div class="pending-statement"><span style="font-weight: bold">{{pastTransfer.usernameTo}}</span>
     {{pastTransfer.transfer_type_desc}} <span style="font-weight: bold">{{pastTransfer.usernameFrom}}</span></div>
   
    <div class="pending-amount">
      <span v-if="pastTransfer.usernameTo == user.userName" style="color: green">{{formatMoney(pastTransfer.amount)}}</span>
      <span v-else style="color: red">-{{formatMoney(pastTransfer.amount)}}</span>
    </div>
    <div class="pending-note">{{pastTransfer.note}}</div>
    <div class="pending-image"><img :src="imageFinder(pastTransfer.account_from)" id="statement-image"/></div>
    <div class="pending-button" v-if="pastTransfer.usernameTo != user.userName"><b-button pill class="pending" @click="approveTransaction(pastTransfer)">Approve</b-button></div>
    <div class="reject-button" v-if="pastTransfer.usernameTo != user.userName"><b-button pill class="reject" @click="rejectTransaction(pastTransfer)">Reject</b-button></div>
  </div>
  </div> 
  
</div>

<div v-if="this.$store.state.showPending == true && this.pendingTransfers.length == 0">Nothing to see here homie.</div>

  <div v-if="this.$store.state.showUserSearch == true" class="userSearch">
    <div>Search</div>
   
    <div><b-form-input placeholder="Username" v-model="search" class="theResults"><b-icon icon="search"></b-icon></b-form-input></div>
  
    <div v-for="userResult in userList" v-bind:key="userResult.id" class="usernameResult">
    <span>{{userResult.username}}</span>
    </div>
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
    amount: '',
    note: '',
    
    
   },
   user: {
    userId: null,
    balance: null,
    userName: null
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
    this.user.userId = response.data.userId;
    this.transfer.account_from = response.data.userId;
    this.transfer.account_to = response.data.userId;
    this.user.balance = response.data.balance;
    this.user.userName = response.data.username;
   
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
        if(this.transfer.amount <= this.user.balance) {
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



.past-transaction {
  display: grid;
  grid-template-columns: 4;
  grid-template-rows: 2;
  grid-template-areas:
  "a b b c"
  "a d d d";
  border-bottom: 1px solid lightgrey;
}

.inner-transaction {
  /* padding-top: 10px;
  padding-bottom: 10px; */
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
  height: 100x;
  width: 100px;
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

/* .input-dollar {
    position: relative;
    
}
.input-dollar input {
    padding-left:18px;
} */
/* .input-dollar:before {
    position: absolute;
    top: 5;
    content:"$";
    left: 5px;
} */

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
}


.userSearch {
  display: flex;
  flex-direction: column;
  margin-left: 5%;
  margin-right: 10%;
  margin-top: 25%;
}

.usernameResult {
  width: 50%;
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
  grid-template-columns: repeat(4, 1fr);
  padding-top: 5%;
  padding-bottom: 5%;
  padding-left: 5%;
 
}

.past-grid-container {
  display: grid;
  border-bottom: 1px solid lightgrey;
  grid-template-columns: repeat(3, 1fr);
  padding-top: 5%;
  padding-bottom: 5%;
  padding-left: 5%;
}

.past-image-container {

}

.past-statement {
 grid-column-start: 2;
  grid-column-end: 2;
  grid-row-start: 1;
  grid-row-end: 1;
  
}

.past-amount {
grid-column-start: 4;
  grid-column-end: 4;
  grid-row-start: 1;
  grid-row-end: 1;
 
}

.past-note {
grid-column-start: 2;
  grid-column-end: 4;
  grid-row-start: 2;
  grid-row-end: 2;
  
}

.past-image {
  grid-column-start: 1;
  grid-column-end: 2;
  
}
.pending-image {

  grid-column-start: 1;
  grid-column-end: 2;
  max-width: 100px;
}

.pending-statement {
  grid-column-start: 2;
  grid-column-end: 2;
  grid-row-start: 1;
  grid-row-end: 1;
  max-width: 200px;

}

.pending-amount {
  grid-column-start: 3;
  grid-column-end: 3;
  grid-row-start: 1;
  grid-row-end: 1;
  max-width: 100px;
}

.pending-note {
  grid-column-start: 2;
  grid-column-end: 2;
  grid-row-start: 2;
  grid-row-end: 2;
  max-width: 300px;
}

.pending-button {
  grid-column-start: 4;
  grid-column-end: 4;
  grid-row-start: 1;
  grid-row-start: 1;
  max-width: 100px;

}

.reject-button {
  grid-column-start: 4;
  grid-column-end: 4;
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