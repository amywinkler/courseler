import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class LoggedOutView extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       showIntro: true,
       showSignup: false,
       email: '',
       password: '',
       error: null
     };
   }
  render() {
    if (this.state.showIntro) {
      return this.renderIntro();
    } else {
      return this.renderForms();
    }
  }
  
  renderIntro() {
    return (
      <div className='intro'>
        <img src='/img/logo.png' className='logo' />
        <div className='panels'>
            { this.renderSellingPoints() }
            <div className='mac2' />
        </div>
      </div>
    )
  }
  
  renderSellingPoints() {
    return (
      <div className='sellingPoints'>
        <div>
          <h1>Simplify your shopping period with <span>Courseler</span></h1>
          <p>Browse courses, get unique recommendations, and share your shopping period plans with friends. On the web and on your phone.</p>
          <div className='buttons'>
            <div onClick={ this.showSignup.bind(this) }>Sign up</div>
            <div onClick={ this.showLogin.bind(this) }>Log in</div>
          </div>
        </div>
      </div>
    )
  }
  
  showSignup() {
    this.setState({showIntro: false, showSignup: true});
  }
  
  showLogin() {
    this.setState({showIntro: false, showSignup: false});
  }
  
  renderForms() {
    // function to switch b/t login and signup
    let onSwitch = () => { this.setState({showSignup: !this.state.showSignup}) };
    
    let allowSubmit = !!this.state.email && !!this.state.password;
    
    let emailField = <input placeholder='Email Address' type='email' value={this.state.email} onChange={ (e) => {this.setState({email: e.target.value})} } />;
    let passwordField = <input placeholder='Password' type='password' value={this.state.password} onChange={ (e) => {this.setState({password: e.target.value})} } />;
    
    let buttonTitle = this.state.showSignup ? 'Sign Up' : 'Log In';
    let submitButton = <input type='submit' value={ buttonTitle } disabled={!allowSubmit} />;
    
    let switchButton;
    if (this.state.showSignup) {
      switchButton = <div className='switch'>Already have an account? <a href='#' onClick={ onSwitch }>Log In</a></div>;
    } else {
      switchButton = <div className='switch'>Don’t have an account? <a href='#' onClick={ onSwitch }>Sign Up</a></div>;
    }
    
    let error = null;
    if (this.state.error) error = <div className='error'>{this.state.error}</div>;
    
    let title = this.state.showSignup ? <h1>Sign up</h1> : <h1>Log in</h1>;
    
    return (
      <div className='LoggedOutView'>
        <div className='inner'>
          {title}
          <form onSubmit={ (e) => this.submit(e) } autoComplete='off'>
            {error}
            <div>
              <label>Email</label>
              {emailField}
            </div>
            <div>
                <label>Password</label>
                {passwordField}
            </div>
            {submitButton}
          </form>
          {switchButton}
        </div>
      </div>
    )
  }
  
  submit(e) {
    e.preventDefault();
    
    let handleLoginResult = (result) => {
      if (result.status === 'success') {
        this.props.onLogIn();
      } else if (result.status === 'unregistered') {
        this.setState({error: "There's no account with that email address!"});
      } else if (result.status === 'already_registered') {
        this.setState({error: "There's already an account with that email address!"});
      } else if (result.status === 'wrong_password') {
        this.setState({error: "Incorrect password."});
      } else {
        this.setState({error: "Unknown error :("});
      }
    };

    let handleIpValidation = (ipObj) => {
      console.log(ipObj.ip);
      let firstSeven = ipObj.ip.substring(0, 7);
      if(firstSeven == "128.148." ||
        firstSeven == "138.16." ||
        ipObj.ip == "0:0:0:0:0:0:0:1" ||
        ipObj.ip == "127.0.0.1") {
         api.signUp(this.state.email, this.state.password, handleLoginResult);
      } else {
        console.log("invalid ip");
        this.setState({error: "Please to connect to Brown Wifi so we can validate that you are part of the Brown community!"});
      }
    };
    
    if (this.state.showSignup) {
      //check validation for sign up
      api.getIpForValidation(handleIpValidation); 
    } else {
      api.logIn(this.state.email, this.state.password, handleLoginResult);
    }
  }
}
