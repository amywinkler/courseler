import React from 'react';
import ReactDOM from 'react-dom';
import LoggedInView from './loggedInView.jsx';
import LoggedOutView from './loggedOutView.jsx';
import api from './api.jsx';

class App extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       loggedIn: api.isLoggedIn(),
     };
   }
   render() {
     if (this.state.loggedIn) {
       return <LoggedInView onLogOut={() => {this.setState({loggedIn: false})}} />;
     } else {
       return <LoggedOutView onLogIn={() => this.setState({loggedIn: true})} />;
     }
   }
}

ReactDOM.render(
  <App />,
  document.getElementById('app')
);
