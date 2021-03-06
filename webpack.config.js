module.exports = {
  entry: './frontend/app.jsx',
  module: {
      loaders: [
        {
          test: /\.(js|jsx)$/,
          loaders: 'babel-loader',
          query: {
            presets: ['es2015', 'react', 'stage-1']
          }
        },
        
        {
          test: /\.json$/,
          loader: "json-loader"
        }
      ]
    },
  output: {
    filename: './src/main/resources/static/js/app.js'
  },
  devtool: 'source-map'
}
