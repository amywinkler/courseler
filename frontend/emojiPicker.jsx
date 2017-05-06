import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class EmojiPicker extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      emoji: null,
      category: null
    };
    this.loadEmoji();
  }
  loadEmoji() {
    $.get('emoji.json', (emoji) => {
      this.setState({emoji: JSON.parse(emoji)});
    });
  }
  render() {
    if (this.state.emoji) {
      let category = this.state.category || this.computeCategories(this.state.emoji)[0];
      return <EmojiPickerInner emoji={this.state.emoji} selectedCategory={category}/>;
    } else {
      return <div>haha</div>
    }
  }
  computeCategories(emoji) {
    let categories = [];
    let seen = {};
    emoji.forEach((emoji) => {
      if (!seen[emoji.category]) {
        let cat = emoji.category;
        categories.push(cat);
        seem[cat] = true;
      }
    })
    return categories;
  }
}

class EmojiPickerInner extends React.Component {
  
}
