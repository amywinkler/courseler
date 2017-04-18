import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class LoggedOutView extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       showSignup: false,
       email: '',
       password: '',
       error: null
     };
   }
  render() {
    // function to switch b/t login and signup
    let onSwitch = () => { this.setState({showSignup: !this.state.showSignup}) };
    
    let allowSubmit = !!this.state.email && !!this.state.password;
    
    let emailField = <input type='email' value={this.state.email} onChange={ (e) => {this.setState({email: e.target.value})} } />;
    let passwordField = <input type='password' value={this.state.password} onChange={ (e) => {this.setState({password: e.target.value})} } />;
    
    let buttonTitle = this.state.showSignup ? 'Sign Up' : 'Log In';
    let submitButton = <input type='submit' value={ buttonTitle } disabled={!allowSubmit} />;
    
    let switchButton;
    if (this.state.showSignup) {
      switchButton = <div>Already have an account? <a href='#' onClick={ onSwitch }>Log In</a></div>;
    } else {
      switchButton = <div>Don't have an account? <a href='#' onClick={ onSwitch }>Sign Up</a></div>;
    }
    
    let error = null;
    if (this.state.error) error = <div className='error'>{this.state.error}</div>;
    
    let title = this.state.showSignup ? 'Sign Up' : 'Log In';
    
    return (
      <div className='LoggedOutView'>
        <h1>{title}</h1>
        <form onSubmit={ (e) => this.submit(e) }>
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
    
    if (this.state.showSignup) {
      api.signUp(this.state.email, this.state.password, handleLoginResult);
    } else {
      api.logIn(this.state.email, this.state.password, handleLoginResult);
    }
  }
}