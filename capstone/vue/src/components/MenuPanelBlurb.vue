<template>
<div id="blah">
    <span id="image-here" :style="{'background-image':'url(' + image + ')'}">
     
       
  </span>
  <p id="welcome">Hi, {{this.firstname}}</p>
  
  <p id="display-username">{{username}}</p>
  </div>
</template>

<script>
export default {
name: 'menu-panel-blurb',
props: ['user'],
username: '',
firstname: '',
userId: 0,
image: '',

created() {
    this.firstname = this.$store.state.activeUser.firstname + "";
    this.userId = this.$store.state.activeUser.accountId;
    this.image = this.userId + '.jpg';
    this.username = this.$store.state.activeUser.userName + "";
   
}
}
</script>

<style>
#welcome {
    font-size: 24px;
    font-weight: 500;
    font-family: "Athletics",Helvetica,Arial,sans-serif;
    grid-area: b;
}
#image-here {
    height: 100px;
    width: 100px;
    background-color: white;
    border-radius: 50%;
  
    background: padding-box;
    border: 1px solid lightslategrey;
    text-align: center;
    grid-area: a;
    background-size: 100%;
}

#blah {
    display: grid;
    margin-bottom: 50px;
    grid-template-areas:
    "a b c d e"
    "a f f d e";
    margin-left: auto;
    margin-right: auto;
}

#display-username {
    grid-area: f;
    font-size: 18px;
    font-family: Helvetica;
    font-weight: bold;
    color: rgba(0,116,222);
}


</style>