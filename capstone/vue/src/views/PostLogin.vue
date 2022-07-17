<template>

  <div>

  <div v-if="this.$store.state.activeUser.hasProfile != true">
Please complete your profile

{{enterUser}}
<b-form-input placeholder="Firstname" v-model="enterUser.firstName"></b-form-input>
<b-form-input placeholder="Lastname" v-model="enterUser.lastName"></b-form-input>
<b-button pill v-on:click="setName()">Get Started</b-button>

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

</style>