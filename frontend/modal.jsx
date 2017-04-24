import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class Modal extends React.Component {
  render() {
    return (
      <div className='Modal' onClick={this.clicked.bind(this)}>
        <div>{this.props.children}</div>
      </div>
    )
  }
  clicked(e) {
    if (e.currentTarget === e.target) {
      this.props.onDismiss();
    }
  }
}
