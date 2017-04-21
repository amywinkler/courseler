import React from 'react';
import ReactDOM from 'react-dom';
import LoggedInView from './loggedInView.jsx';
import LoggedOutView from './loggedOutView.jsx';
import api from './api.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

class App extends React.Component {
  constructor(props) {
     super(props);
     window.onpopstate = (event) => {
       this.setState({route: currentRoute()});
     };
     this.state = {
       loggedIn: api.isLoggedIn(),
       route: currentRoute()
     };
   }
   render() {
     console.log(this.state.route)
     if (this.state.loggedIn) {
       return <LoggedInView onLogOut={() => {this.setState({loggedIn: false})}} route={this.state.route} />;
     } else {
       return <LoggedOutView onLogIn={() => this.setState({loggedIn: true})} route={this.state.route} />;
     }
   }
}

ReactDOM.render(
  <App />,
  document.getElementById('app')
);
