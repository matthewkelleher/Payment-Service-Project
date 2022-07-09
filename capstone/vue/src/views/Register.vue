<template>
  <div id="register" class="text-center">
    <div>
      <b-container fluid>
    <b-form @submit.prevent="register" class="register-form">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      
     
      <b-form-group id="username-group"
      label-for="username">
      <b-form-input
      
        id="username"
        placeholder="Username"
        v-model="user.username"
        required
        
      ></b-form-input>
      </b-form-group>
      
      <b-form-group
      label-for="password">
      <b-form-input
       
        id="password"
        
        placeholder="Password"
        v-model="user.password"
        required
        
      ></b-form-input>
      </b-form-group>
      <b-form-group
       label-for="confirm-password">
      <b-form-input
       
        id="confirm-password"
        
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
        
      ></b-form-input>
      </b-form-group>
     
      <b-button pill type="submit" variant="primary">
        Create Account
      </b-button>
    </b-form>
      </b-container>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>     
      <div class="alert alert-success" role="alert" v-if="registrationSuccess">
        {{ registrationSuccessMsg }}
      </div> 
    </div>
    <router-link :to="{ name: 'login' }">Have an account?</router-link>
   
  </div>
 
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationSuccess: false,
      registrationSuccessMsg: 'Registration successful. Please log in.',
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.registrationSuccess = true;
              setTimeout( () =>
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              }), 3000);
              
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style>
.register-form {
height: 25%;
  width: 25%;
  margin-top: 10%;
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

#register {
  background-color: white;
}

#username {

background-color: rgb(243, 241, 241);
border-top: none;
border-right: none;
border-left: none;

}

#password {
background-color: rgb(243, 241, 241);
border-top: none;
border-right: none;
border-left: none;
}

#confirm-password {
background-color: rgb(243, 241, 241);
border-top: none;
border-right: none;
border-left: none;
}

</style>
