import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class AddCoursesScreen extends React.Component {
  render() {
    return <h1>add courses go here! <a href='#' onClick={() => this.props.onDone()}>done</a></h1>
  }
}
