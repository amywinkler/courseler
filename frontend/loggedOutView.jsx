import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class LoggedOutView extends React.Component {
  render() {
    return (
      <div className='LoggedInView'>
        <h1>you are logged out</h1>
      </div>
    )
  }
}
