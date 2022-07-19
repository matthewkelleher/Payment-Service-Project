<template>
  <div id="login-page">
    <div id="headline"><h2 style="text-align:center">Sign in to Tenmo</h2></div>
    <div v-if="this.invalidCredentials == true" class="alert alert-danger" style="text-align: center">Your username or password was incorrect.</div>
    <b-form @submit.prevent="login" class="login-form">
      <b-form-group
        id="username-input"
        label="Username"
        label-for="input-1"
        >
        <b-form-input 
          id="input-1"
          v-model="user.username"
          required>
        </b-form-input>
        </b-form-group>
     
      <b-form-group
        id="password-input"
        label="Password"
        label-for="input-2"
        
        >
        <b-form-input 
          id="input-2"
          type="password"
          v-model="user.password"
          required>
        </b-form-input>
        </b-form-group>
        <div id="lastline">
        <div><router-link :to="{ name: 'register' }" style="text-decoration: none">Need an account?</router-link></div>
        <b-button id="submit-button" type="submit">Sign In</b-button>
        </div>
      </b-form>

      <div>
      
      </div>
   
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$store.commit("SET_ACTIVE_USER", response.data.user)
            this.$store.commit("SET_ALL_FALSE");
            this.$store.commit("PROFILE_NEEDED");
            this.$router.push("/postlogin");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
<style scoped>

.login-form {
  height: 25%;
  width: 25%;
  margin-top: 5%;
  margin-left: auto;
  margin-right: auto;  
  width: 33%;
  height: 25%;
  background-color: rgb(243, 241, 241);
  vertical-align: middle;
  padding: 50px;
  box-sizing: border-box;
  outline: none;
  
  
}
#input-1 {
background-color: rgb(243, 241, 241);
border-top: none;
border-right: none;
border-left: none;
}

#input-1:focus {
  border-color: none !important;
  box-shadow: none;
}

#input-2:focus {
  border-color: none !important;
  box-shadow: none;
}


#input-2 {
background-color: rgb(243, 241, 241);
border-top: none;
border-right: none;
border-left: none;
}

#login-page {
  background-color: white;
 
  
 
  
}

h2 {
  vertical-align: middle;
}

#submit-button {
  font-family: serif;
  font-weight: bold;
  background-color: rgba(0,140,255);
  
}

#username-input {
font-variant: all-small-caps;

}

#password-input {
font-variant: all-small-caps;

}

#lastline {
  display: flex;
  justify-content: space-between;
}

#headline {
  padding-top: 5%;
}

</style>

