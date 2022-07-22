<template>
<div class="page">

<div v-if="this.$store.state.payClicked == false &&
this.$store.state.showStatements == false &&
this.$store.state.showPending == false &&
this.$store.state.showUserSearch == false">
<div class="landing-page-container text-center">
<p style="font-weight: bold">You can't use Tenmo alone, so make some friends (or enemies) by sending (or requesting) money!</p>
<br>
<p>Hit up your roommate, significant other, or anyone who might have some spare cash.</p>
<br>
<b-button pill class="pay-request-buttons" id="search-button" @click="goToSearch()">Find some people you know</b-button> </div>
 </div>
<div v-if="this.$store.state.payClicked == true" class="payment-container">
 
  <p style="position: relative; left: -5px; font-weight: 500; text-align: center">TEnmo | Pay & Request</p>
    <div class="autocomplete">
     <br>
  
    <input id="dollar" value="$" readonly/><input class="pay-request-input" id="money-input" style="border: none" @keypress="isNumber($event)" 
    onkeyup="this.style.minWidth = (this.value.length + 2) + 'ch'" placeholder="0" v-model="transfer.amount" type="text" maxlength="7" required/>

 
    <br>
    <br>   
    <br>
    <b-form-input id="username-input" class="pay-request-input" placeholder="To" v-model="toUserName" required></b-form-input>
    <br>
    <br>
    <b-form-textarea class="pay-request-input" id="note-input" placeholder="Note" v-model="transfer.note" rows="5" max-rows="5" maxlength="200" no-resize required></b-form-textarea>
    <br>
    <br>
   
 
    
    <div id="pay-request-button-container">
    <b-button pill type="submit" @click="transferMoney()" class="pay-request-buttons">Pay</b-button> <b-button pill type="submit" @click="requestMoney()" class="pay-request-buttons">Request</b-button>
    </div>
   
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
    <div class="past-image"><b-avatar variant="primary" size="4em" :src="imageFinder(pastTransfer.account_from)" :text="createAvatarText()"></b-avatar></div>
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
    <div class="past-image"><b-avatar variant="primary" size="4em" :src="imageFinder(pastTransfer.account_from)"></b-avatar></div>
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
    <div class="pending-image"><b-avatar variant="primary" size="4em" :src="imageFinder(pastTransfer.account_from)"></b-avatar></div>
    <div class="pending-button" v-if="pastTransfer.usernameTo != user.userName">
    <b-button v-if="user.balance >= pastTransfer.amount" pill size="sm" class="pending" @click="approveTransaction(pastTransfer)">Approve</b-button>
    <b-button v-else disabled pill size="sm" class="pending">Approve</b-button>
    </div>
    <div class="pending-button" v-if="pastTransfer.usernameTo == user.userName">
    <b-button pill variant="outline-primary" size="sm" disabled>Waiting</b-button>
    </div>
    <div class="reject-button" v-if="pastTransfer.usernameTo != user.userName"><b-button pill variant="danger" size="sm" class="reject" @click="rejectTransaction(pastTransfer)">Reject</b-button></div>
  </div>
  </div> 
  
</div>

<div v-if="this.$store.state.showPending == true && this.pendingTransfers.length == 0" class="pending-statements">
<h3 id="pending-header">Incomplete</h3>
<p>You have no pending transactions.</p></div>

  <div v-if="this.$store.state.showUserSearch == true" class="userSearch">
    <h3>Search</h3>
 
    <div>
      <b-input-group size="m" class="mb-2">
      <b-input-group-prepend>
        <b-icon icon="search" id="search-icon"></b-icon>
      </b-input-group-prepend><b-form-input placeholder="Name or Username" v-model="search" class="theResults"><b-icon icon="search"></b-icon></b-form-input>
  </b-input-group>
  </div>
  
    <div v-for="userResult in userList" v-bind:key="userResult.account_id" class="usernameResult" @click="pushToTransfer(userResult.username)">
    
    <span id="username-search-result" style="color: slategray">{{userResult.username}}</span>
    <span id="firstname-lastname-search-result">{{userResult.firstName}}&nbsp;{{userResult.lastName}}</span>
    <span id="userimage-search-result"><b-avatar variant="primary" id="avatar" size="4em" :src="imageFinder(userResult.account_id)"></b-avatar></span>
    </div>
  
  </div>
  <br>
 <div v-if="this.rejectedMessage != ''" class="alert alert-info" role="alert" style="text-align: center">{{rejectedMessage}}</div>
<div v-if="this.approvedMessage != ''" class="alert alert-success" role="alert" style="text-align: center">{{approvedMessage}}</div>
<div v-if="this.sentMessage != ''" class="alert alert-success" role="alert" style="text-align: center">{{sentMessage}}</div>
<div v-if="this.requestedMessage != ''" class="alert alert-info" role="alert" style="text-align: center">{{requestedMessage}}</div>
 

 
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
        errorMsg: '',
        
    transfer: {
    account_from: '',
    account_to: '',
    amount: '',
    note: '',
   
    
   },
   transferClicked: false,
   requestClicked: false,
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
   approvedMessage: '',
   rejectedMessage: '',
   sentMessage: '',
   requestedMessage: '',
   approved: false
    }

    
},
updated() {
  if(this.$store.state.showStatements == true) {
    this.listTransfers()
  }
  if(this.$store.state.showPending == true) {
    this.getPending()
  }

  if(this.$store.state.showUserSearch == false && this.search != '') {
    this.search = ''
  }

  if(this.$store.state.payClicked == false && (this.transfer.amount != '' || this.transfer.note != '' || this.toUserName != '')) {
    this.transfer.amount = '';
    this.transfer.note = '';
    this.toUserName = '';
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
      this.removeWindow()
      if(this.toUserName == '') {
        this.shakeWindow('username-input')
        return;
      }
      if(this.toUserName == this.user.userName) {
        this.transfer.account_to = 0;
        this.toUserName = '';
        this.shakeWindow('username-input')
        return;
      }
      if(this.transfer.amount < 0.01) {
        this.shakeWindow('money-input')
        return;
      }

      if(this.transfer.note == '') {
        this.shakeWindow('note-input')
        return;
      }

      if(this.transfer.amount > this.user.balance) {
        
        this.shakeWindow('money-input')
        return;
      }
      
        if(this.transfer.amount <= this.user.balance && this.transfer.amount >= 0.01) {
        TransferService.getUserId(this.toUserName).then((response) => {
            console.log(response.status)
            this.transfer.account_to = response.data;
           
            TransferService.send(this.transfer).then(() => {
            console.log("Money sent.");
        this.$store.commit("SUBTRACT_FUNDS", this.transfer.amount);
        this.sentMessage = 'You sent ' + '$' + this.transfer.amount + '.'
        setTimeout(() => this.sentMessage = '', 3000)
        this.transfer.account_from = this.user.userId;
        this.toUserName = '';
        this.transfer.account_to = '';
        this.amount = 0;
        this.transfer.amount = 0;
        this.transfer.amount = '';
        this.transfer.note = '';
        
            })
        },
        (error) => { if(error.response.status == 401) {
                this.shakeWindow('username-input')
            } })
}},
  listTransfers() {
    TransferService.list().then((response) => {
      this.listOfTransfers = response.data;
     

    })
    
  },
  goToSearch() {
    this.$store.commit("SHOW_USER_SEARCH", true);
  },

  shakeWindow(item) {
    var element = document.getElementById(item);
    element.classList.add('shake-window');
    
   },
  removeWindow() {
    var element = document.getElementById('username-input');
    var element2 = document.getElementById('money-input');
    var element3 = document.getElementById('note-input');
    element.classList.remove("shake-window");
    element2.classList.remove("shake-window");
    element3.classList.remove("shake-window");

   
  },
  createAvatarText() {

  },

  pushToTransfer(input) {
    this.toUserName = input;
    this.search = '';
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
      this.approved = true;
      this.approvedMessage = 'You sent ' + '$' + approved.amount + ' to ' + approved.nameTo + '.'
      setTimeout(() => this.approvedMessage = '', 3000)
    }) 
  } else {
    window.alert("You don't have enough money to approve this!")
  }
  },
  rejectTransaction(rejected) {
    TransferService.reject(rejected).then(() => {
      this.rejectedMessage = 'You rejected a request for ' + '$' + rejected.amount + ' from ' + rejected.nameFrom + '.'
      setTimeout(() => this.rejectedMessage = '', 3000)
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
  isNumber(evt) {
   
      evt = (evt) ? evt : window.event;
      var charCode = (evt.which) ? evt.which : evt.keyCode;
      if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
        evt.preventDefault();
      } else if(charCode === 46 && this.transfer.amount.includes(".")) {
        evt.preventDefault();
      } else if(this.transfer.amount.match(/^[0-9]*\.[0-9][0-9]$/)) {
          evt.preventDefault();
         } else {
          return true;
      }
  },
  getUserNames() {
    TransferService.getUsernames(this.search).then((response => {
      this.userList = response.data;
    }))
  },
 
 
    requestMoney() {
       this.removeWindow()
       if(this.toUserName == '') {
        this.shakeWindow('username-input')
        return; }
       this.transfer.account_to = this.user.userId
       if(this.toUserName == this.user.userName) {
        this.shakeWindow('username-input')
        
        this.transfer.account_to = 0;
        this.toUserName = '';
       return;
       } else if(this.transfer.amount < 0.01) {
        this.transfer.amount = '';
        this.shakeWindow('money-input')
        return;
       } else if(this.transfer.note == '') {
        this.shakeWindow('note-input')
        return;
       } else {
       TransferService.getUserId(this.toUserName).then((response) => {
            console.log(response.status);
            
            this.transfer.account_from = response.data;
           
            TransferService.request(this.transfer).then(() => {
            this.requestedMessage = 'You requested ' + '$' + this.transfer.amount + '.'
            setTimeout(() => this.requestedMessage = '', 3000)
            this.transfer.account_from = this.user.userId;
            this.transfer.account_to = '';
            this.transfer.amount = 0;            
            this.transfer.amount = '';
            this.transfer.note = '';
            this.toUserName = '';
            
        }) 
            },
            (error) => { if(error.response.status == 401) {
                this.shakeWindow('username-input')
            } }
        )
        
    }}},
    
}
</script>

<style>
.page {
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
  font-family: 'Courier New', Courier, monospace;
  height: 64px;
  font-size: 42px;
  font-weight: 600;
  border-style: hidden;
  
 
  
}

#money-input:focus {
 box-shadow: none;
 border-style: hidden;
 outline: none;


}




h3 {
  font-family: "Athletics"
}

.autocomplete {

 text-align: center;
 
 
}

.input-group-prepend .input-group-text {
  background-color: white;
  border: none!important;
}

.pay-request-buttons {
  background-color: rgba(0,140,255)!important;
  width: 200px;
  font-weight: bold!important;
  font-family: Helvetica;
}



.usernameResult:hover  {
  background-color: rgba(0,140,255);
  color: white!important;
  cursor: pointer;  
  border-radius: 10px;
}

.usernameResult:hover *  {
  background-color: rgba(0,140,255);
  color: white!important;
  cursor: pointer;  
  
}



.usernameResult {
  display: grid;
  grid-template-columns: repeat(3, 120px); 
  grid-template-rows: 50px;
  max-height: 75px;
  padding-left: 10%;
  
}
#username-search-result {
grid-column-start: 2;
grid-column-end: 2;
grid-row-start: 2;
grid-row-end: 2;

font-weight: bold;
}


#firstname-lastname-search-result {
grid-column-start: 2;
grid-column-end: 2;
grid-row-start: 1;
grid-row-end: 1;
font-weight: bold;
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
  
 
}

.pay-request-input:focus {
  box-shadow: none!important; 
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
  grid-column-end: 5;
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
  grid-column-end: 4;
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
  
 width:55%;
 height:500px;
 margin:0 auto;
 
 position:absolute;
 left:50%;
 top:50%;
 margin-left:-250px;
 margin-top:-250px;
}



.payment-container {
  margin-top: 10%;
  justify-content: center;
  align-items: center;
 
}

#cash-input {
  width: 1ch;
}

.shake-window {
  animation-name: shakeError;
  animation-fill-mode: forwards;
  animation-duration: 1s;
  animation-timing-function: ease-in-out;
  border: 3px solid red!important;

}

@keyframes shakeError {
  0% {
    transform: translateX(0); }
  15% {
    transform: translateX(0.375rem); }
  30% {
    transform: translateX(-0.375rem); }
  45% {
    transform: translateX(0.375rem); }
  60% {
    transform: translateX(-0.375rem); }
  75% {
    transform: translateX(0.375rem); }
  90% {
    transform: translateX(-0.375rem); }
  100% {
    transform: translateX(0); } }

#search-button {
  width: 300px;
}

.landing-page-container {
 width:50%;
 height:500px;
margin-left: auto;
margin-right: auto;
margin-top: 25%;
font-size: 20px;
 


  
}

#dollar {
  position: relative;
  text-decoration: none;
  width: 30px;
  font-size: 24px;
  box-shadow: none;
  outline: none;
  border-style: hidden;
  top: -5px;
  left: 5px;
}

#dollar:focus {
  border: none;
  box-shadow: none;
  outline: none;
}

#search-icon {
  position: relative;
  top: 8px;
  left: -5px;
}
</style>