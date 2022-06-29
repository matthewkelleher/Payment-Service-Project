<template>
<div id="blah">
    <span id="image-here"><span id="tricky">{{this.username.charAt(0)}}</span></span>
  <p id="welcome">Hi, {{this.username}} </p>
  </div>
</template>

<script>
export default {
name: 'menu-panel-blurb',
username: '',
created() {
    this.username = this.$store.state.user.username;
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
    padding-top: 30px;
    padding-bottom: 30px;
    
    border: 1px solid lightslategrey;
    text-align: center;
    grid-area: a;
}

#blah {
    display: grid;
    margin-bottom: 50px;
    grid-template-areas:
    "a b c d e"
    "a b c d e";
    margin-left: auto;
    margin-right: auto;
}

#tricky {
   margin-left: auto;
   margin-right: auto;
}
</style>