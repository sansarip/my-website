module.exports = {
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
    ],
  },
  entry: './src/js/index.js',
  output: {
    filename: 'index.bundle.js'
  }
}