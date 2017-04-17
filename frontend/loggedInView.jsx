import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class LoggedInView extends React.Component {
  render() {
    return (
      <div className='LoggedInView'>
        <h1>you are logged in</h1>
        <button onClick={() => { this.logOut() }}>Log me out!</button>
      </div>
    )
  }
  logOut() {
    api.logOut();
    this.props.onLogOut();
  }
}
