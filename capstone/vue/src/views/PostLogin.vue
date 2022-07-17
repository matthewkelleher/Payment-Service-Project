<template>

  <div class="postlogin">

  <div v-if="this.$store.state.activeUser.hasProfile != true">
<div class="headline-container">
<span id="headline"><h2 style="text-align:center">Please complete your profile</h2></span>
</div>
<b-form id="username-form" @submit.prevent="setName">
  <b-form-group
        id="username-input"
        label="First name"
        label-for="input-1"
        description="This is how we'll refer to you and how you'll show up in the app."
        >
<b-form-input id="input-1" v-model="enterUser.firstName" required></b-form-input>
</b-form-group>
<b-form-group
        id="username-input"
        label="Last name"
        label-for="input-1"
        >
<b-form-input id="input-2" v-model="enterUser.lastName" required></b-form-input>
</b-form-group>
<b-button pill type="submit" class="get-going-button">Save</b-button>

</b-form>
</div>

<div v-if="user.firstname != null">
    {{pushIt()}}
</div>


</div>

</template>

<script>

import TransferService from '../services/TransferService'
export default {
data() {
    return {
    enterUser: {
        userId: null,
        accountId: null,
        balance: null,
        firstName: null,
        lastName: null,
        userImage: null
    },
    user: {
        firstname: null,
        lastname: null,
        userId: null
    }
}
},
created() {
    TransferService.balance().then((response) => {
    this.user.userId = response.data.userId;
    this.user.accountId = response.data.accountId;
    this.user.balance = response.data.balance;
    this.user.userName = response.data.username;
    this.user.firstname = response.data.firstName;
    this.user.lastname = response.data.lastName;
    
  })
},
mounted() {
TransferService.balance().then((response) => {
    this.user.userId = this.enterUser.userId = response.data.userId;
    this.user.accountId = this.enterUser.accountId = response.data.accountId;
    this.user.balance = this.enterUser.balance = response.data.balance;
    this.user.userName = this.enterUser.username = response.data.username;
    this.user.firstname = response.data.firstName;
    this.user.lastname = response.data.lastName;
    
  })
},
methods: {
setName() {
      TransferService.setName(this.enterUser).then(() => {
       this.$router.push("/mainpage")
      })
    },
pushIt() {
    this.$router.push("/mainpage")
},
restartIt() {
  this.$store.commit("LOGOUT");
    this.$router.push("/");
}
}
}
</script>

<style>

.postlogin {
  font-family: "Athletics";
  background-color: white!important;
}
#username-form {
  height: 25%;
  width: 25%;
  margin-top: 5%;
  margin-left: auto;
  margin-right: auto;  
  width: 33%;
  height: 25%;
  
  vertical-align: middle;
  padding: 50px;
  padding-top: 0;
  box-sizing: border-box;
  outline: none;
  
  
}

.get-going-button {
  width: 100%;
  background-color: rgba(0,140,225)!important;
  font-weight: bold!important;
}

.headline-container {
  vertical-align: middle;
  padding-top: 5%;
}
</style>